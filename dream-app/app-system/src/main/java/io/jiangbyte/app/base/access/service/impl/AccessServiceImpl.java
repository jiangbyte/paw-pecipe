package io.jiangbyte.app.base.access.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jiangbyte.app.base.access.dto.*;
import io.jiangbyte.app.base.access.service.AccessService;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccount;
import io.jiangbyte.app.base.auths.account.mapper.AuthsAccountMapper;
import io.jiangbyte.app.base.users.info.entity.UsersInfo;
import io.jiangbyte.app.base.users.info.mapper.UsersInfoMapper;
import io.jiangbyte.app.base.users.preference.entity.UsersPreference;
import io.jiangbyte.app.base.users.preference.mapper.UsersPreferenceMapper;
import io.jiangbyte.app.base.users.profile.entity.UsersProfile;
import io.jiangbyte.app.base.users.profile.mapper.UsersProfileMapper;
import io.jiangbyte.app.base.users.stats.entity.UsersStats;
import io.jiangbyte.app.base.users.stats.mapper.UsersStatsMapper;
import io.jiangbyte.framework.utils.IpUtil;
import io.jiangbyte.framework.email.EmailService;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.utils.PasswordStrengthEvaluator;
import io.jiangbyte.framework.utils.PasswordUtil;
import io.jiangbyte.framework.utils.UserInputValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 18/11/2025
 * @description TODO
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final AuthsAccountMapper authsAccountMapper;
    private final UsersInfoMapper usersInfoMapper;
    private final UsersPreferenceMapper usersPreferenceMapper;
    private final UsersProfileMapper usersProfileMapper;
    private final UsersStatsMapper usersStatsMapper;

    private final EmailService emailService;
    private final PasswordUtil passwordUtil;

    @Override
    public CaptchaResp captcha() {
        CaptchaResp captchaResult = new CaptchaResp();

        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 5);
        lineCaptcha.setGenerator(randomGenerator);
        String imageBase64Data = lineCaptcha.getImageBase64Data();

        captchaResult.setCaptchaImg(imageBase64Data);
        String uuid = IdUtil.fastSimpleUUID();
        captchaResult.setCaptchaId(uuid);

        // 存储验证码到Redis，5分钟有效期
        redisTemplate.opsForValue().set("captcha:" + uuid, lineCaptcha.getCode(), Duration.ofSeconds(5 * 60L));
        return captchaResult;
    }

    /**
     * 验证码校验方法
     */
    private void validateCaptcha(String captchaId, String captchaCode) {
        if (StrUtil.isBlank(captchaId) || StrUtil.isBlank(captchaCode)) {
            throw new BusinessException("验证码不能为空");
        }

        String redisKey = "captcha:" + captchaId;
        String storedCaptcha = (String) redisTemplate.opsForValue().get(redisKey);

        if (StrUtil.isBlank(storedCaptcha)) {
            throw new BusinessException("验证码已过期，请刷新验证码");
        }

        if (!storedCaptcha.equalsIgnoreCase(captchaCode.trim())) {
            throw new BusinessException("验证码错误");
        }

        // 验证成功后删除Redis中的验证码，防止重复使用
        redisTemplate.delete(redisKey);
    }

    @Override
    public LoginResp doLogin(LoginReq loginReq) {
        // 校验验证码
        validateCaptcha(loginReq.getCaptchaId(), loginReq.getCaptchaCode());

        // 校验用户名格式
        UserInputValidator.validateUsername(loginReq.getUsername()).throwIfFailed();

        // 数据库用户名是否存在
        AuthsAccount authAccount = authsAccountMapper.selectOne(new LambdaQueryWrapper<AuthsAccount>().eq(AuthsAccount::getUsername, loginReq.getUsername()));
        if (ObjectUtil.isEmpty(authAccount)) {
            throw new BusinessException("用户不存在");
        }

        // 密码解密与密码校验
        String decrypt = passwordUtil.decrypt(loginReq.getPassword());
        if (!BCrypt.checkpw(decrypt, authAccount.getPassword())) {
            throw new BusinessException("密码错误");
        }
        // 校验密码格式
        UserInputValidator.validatePassword(decrypt).throwIfFailed();

        // IP 记录
        String clientIp = IpUtil.getClientIp();
        authAccount.setLastLoginIp(clientIp);
        // 更新登录时间
        authAccount.setLastLoginTime(new Date());
        // 更新登录次数
        authAccount.setLoginCount(authAccount.getLoginCount() + 1);
        authsAccountMapper.updateById(authAccount);

        SaLoginModel loginModel = new SaLoginModel();
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("id", authAccount.getId());
        loginModel.setExtraData(extraData);
        StpUtil.login(authAccount.getId(), loginModel);

        LoginResp loginResp = new LoginResp();
        loginResp.setToken(StpUtil.getTokenValue());

        UserPublicAssociatedInfo userPublicAssociatedInfo = new UserPublicAssociatedInfo();
        // INFO
        UsersInfo usersInfo = usersInfoMapper.selectOne(new LambdaQueryWrapper<UsersInfo>().eq(UsersInfo::getAccountId, authAccount.getId()));
        BeanUtil.copyProperties(usersInfo, userPublicAssociatedInfo);
        // PROFILE
        UsersProfile usersProfile = usersProfileMapper.selectOne(new LambdaQueryWrapper<UsersProfile>().eq(UsersProfile::getAccountId, authAccount.getId()));
        BeanUtil.copyProperties(usersProfile, userPublicAssociatedInfo);
        // STATS
        UsersStats usersStats = usersStatsMapper.selectOne(new LambdaQueryWrapper<UsersStats>().eq(UsersStats::getAccountId, authAccount.getId()));
        BeanUtil.copyProperties(usersStats, userPublicAssociatedInfo);

        loginResp.setUser(userPublicAssociatedInfo);

        return loginResp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResp doRegister(RegisterReq registerReq) {
        // 校验验证码
        validateCaptcha(registerReq.getCaptchaId(), registerReq.getCaptchaCode());
        // 校验邮箱格式
        UserInputValidator.validateEmail(registerReq.getEmail()).throwIfFailed();
        // 校验昵称格式
        UserInputValidator.validateNickname(registerReq.getNickname()).throwIfFailed();
        // 校验用户名格式
        UserInputValidator.validateUsername(registerReq.getUsername()).throwIfFailed();

        // 用户名
        if (authsAccountMapper.exists(new LambdaQueryWrapper<AuthsAccount>()
                .eq(AuthsAccount::getUsername, registerReq.getUsername())
        )) {
            throw new BusinessException("用户名已存在");
        }
        // 邮箱
        if (authsAccountMapper.exists(new LambdaQueryWrapper<AuthsAccount>()
                .eq(AuthsAccount::getEmail, registerReq.getEmail())
        )) {
            throw new BusinessException("邮箱已存在");
        }

        // 创建账户
        AuthsAccount authsAccount = new AuthsAccount();
        authsAccount.setUsername(registerReq.getUsername());
        String password = passwordUtil.decrypt(registerReq.getPassword());
        // 校验密码格式
        UserInputValidator.validatePassword(password).throwIfFailed();
        authsAccount.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        authsAccount.setEmail(registerReq.getEmail());
        authsAccount.setPasswordStrength(PasswordStrengthEvaluator.evaluateStrength(password));
        authsAccountMapper.insert(authsAccount);

        // 创建用户信息
        UsersInfo usersInfo = new UsersInfo();
        usersInfo.setAccountId(authsAccount.getId());
        usersInfo.setNickname(registerReq.getNickname());
        usersInfo.setAvatar(null);
        usersInfo.setSignature(null);
        usersInfoMapper.insert(usersInfo);

        // 创建用户偏好
        UsersPreference entity = new UsersPreference();
        entity.setAccountId(authsAccount.getId());
        usersPreferenceMapper.insert(entity);

        // 创建用户统计
        UsersStats usersStats = new UsersStats();
        usersStats.setAccountId(authsAccount.getId());
        usersStatsMapper.insert(usersStats);

        // 创建用户档案
        UsersProfile usersProfile = new UsersProfile();
        usersProfile.setAccountId(authsAccount.getId());
        usersProfileMapper.insert(usersProfile);

        RegisterResp registerResp = new RegisterResp();
        registerResp.setUserId(authsAccount.getId());
        return registerResp;
    }

    @Override
    public void doLogout() {
        StpUtil.logout();
    }

    @Override
    public Boolean doResetPassword(ResetPasswordReq resetPasswordReq) {
        // 校验验证码
        validateCaptcha(resetPasswordReq.getCaptchaId(), resetPasswordReq.getCaptchaCode());

        // 校验邮箱是否存在
        AuthsAccount authAccount = authsAccountMapper.selectOne(
                new LambdaQueryWrapper<AuthsAccount>().eq(AuthsAccount::getEmail, resetPasswordReq.getEmail())
        );

        if (ObjectUtil.isEmpty(authAccount)) {
            // 出于安全考虑，不提示邮箱不存在，直接返回成功
            return true;
        }

        // 生成重置令牌
        String resetToken = IdUtil.fastSimpleUUID();

        // 将重置令牌存储到Redis，设置30分钟有效期
        String redisKey = "password_reset_token:" + resetToken;
        redisTemplate.opsForValue().set(
                redisKey,
                resetPasswordReq.getEmail(),
                Duration.ofMinutes(30)
        );

        try {
            // 发送密码重置邮件
            emailService.sendPasswordResetEmail(resetPasswordReq.getEmail(), resetToken);

            return true;

        } catch (Exception e) {
            log.error("密码重置邮件发送失败 - 邮箱: {}, 错误: {}", resetPasswordReq.getEmail(), e.getMessage());
            // 如果邮件发送失败，删除Redis中的令牌
            redisTemplate.delete(redisKey);
            throw new BusinessException("密码重置邮件发送失败，请稍后重试");
        }
    }

    @Override
    public Boolean validateResetPasswordToken(String token) {
        if (StrUtil.isBlank(token)) {
            return false;
        }

        // 从Redis中检查令牌是否存在且未过期
        String redisKey = "password_reset_token:" + token;
        String email = (String) redisTemplate.opsForValue().get(redisKey);

        if (StrUtil.isBlank(email)) {
            return false;
        }

        // 验证邮箱对应的用户是否存在
        return authsAccountMapper.exists(
                new LambdaQueryWrapper<AuthsAccount>().eq(AuthsAccount::getEmail, email)
        );
    }

    public Boolean confirmResetPassword(ResetPasswordConfirmReq confirmReq) {
        // 校验验证码
        validateCaptcha(confirmReq.getCaptchaId(), confirmReq.getCaptchaCode());

        String newPassword = passwordUtil.decrypt(confirmReq.getNewPassword());
        // 校验密码格式
        UserInputValidator.validatePassword(newPassword).throwIfFailed();
        String confirmPassword = passwordUtil.decrypt(confirmReq.getConfirmPassword());
        // 校验密码格式
        UserInputValidator.validatePassword(confirmPassword).throwIfFailed();

        // 校验新密码和确认密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException("新密码和确认密码不一致");
        }

        // 验证重置令牌
        String redisKey = "password_reset_token:" + confirmReq.getToken();
        String email = (String) redisTemplate.opsForValue().get(redisKey);

        if (StrUtil.isBlank(email)) {
            throw new BusinessException("重置令牌无效或已过期");
        }

        // 根据邮箱查找用户并更新密码
        AuthsAccount authAccount = authsAccountMapper.selectOne(
                new LambdaQueryWrapper<AuthsAccount>().eq(AuthsAccount::getEmail, email)
        );

        if (ObjectUtil.isEmpty(authAccount)) {
            throw new BusinessException("用户账户不存在");
        }

        authAccount.setPasswordStrength(PasswordStrengthEvaluator.evaluateStrength(confirmPassword));
        authAccount.setLastPasswordChange(new Date());
        // 更新密码
        authAccount.setPassword(BCrypt.hashpw(confirmPassword, BCrypt.gensalt()));
        int updateCount = authsAccountMapper.updateById(authAccount);

        if (updateCount > 0) {
            // 密码更新成功后，删除Redis中的重置令牌
            redisTemplate.delete(redisKey);
            return true;
        }

        return false;
    }
}
