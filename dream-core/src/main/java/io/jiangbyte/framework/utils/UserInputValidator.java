package io.jiangbyte.framework.utils;

import cn.hutool.core.util.StrUtil;
import io.jiangbyte.framework.exception.BusinessException;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * 用户输入校验工具类
 */
@UtilityClass
public class UserInputValidator {

    // 用户名正则：只能英文和数字，3-20位
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{3,20}$";
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

    // 密码正则：只能英文、数字、-_@符号，6-32位
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9\\-_@]{6,32}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    // 邮箱正则
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // 昵称正则：只能英文、数字、-_，2-30位
    private static final String NICKNAME_REGEX = "^[a-zA-Z0-9\\-_]{2,30}$";
    private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REGEX);

    // 中文检测正则
    private static final String CHINESE_REGEX = ".*[\\u4e00-\\u9fa5].*";
    private static final Pattern CHINESE_PATTERN = Pattern.compile(CHINESE_REGEX);

    /**
     * 校验用户名
     *
     * @param username 用户名
     * @return 校验结果
     */
    public static ValidationResult validateUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return ValidationResult.failure("用户名不能为空");
        }

        // 检查是否包含中文
        if (containsChinese(username)) {
            return ValidationResult.failure("用户名不能包含中文");
        }

        // 检查格式
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            return ValidationResult.failure("用户名只能包含英文和数字，长度为3-20位");
        }

        return ValidationResult.success();
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @return 校验结果
     */
    public static ValidationResult validatePassword(String password) {
        if (StrUtil.isBlank(password)) {
            return ValidationResult.failure("密码不能为空");
        }

        // 检查是否包含中文
        if (containsChinese(password)) {
            return ValidationResult.failure("密码不能包含中文");
        }

        // 检查格式
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return ValidationResult.failure("密码只能包含英文、数字、-_@符号，长度为6-32位");
        }

        return ValidationResult.success();
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return 校验结果
     */
    public static ValidationResult validateEmail(String email) {
        if (StrUtil.isBlank(email)) {
            return ValidationResult.failure("邮箱不能为空");
        }

        // 检查格式
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ValidationResult.failure("邮箱格式不正确");
        }

        return ValidationResult.success();
    }

    /**
     * 校验昵称
     *
     * @param nickname 昵称
     * @return 校验结果
     */
    public static ValidationResult validateNickname(String nickname) {
        if (StrUtil.isBlank(nickname)) {
            return ValidationResult.failure("昵称不能为空");
        }

        // 检查是否包含中文
        if (containsChinese(nickname)) {
            return ValidationResult.failure("昵称不能包含中文");
        }

        // 检查格式
        if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
            return ValidationResult.failure("昵称只能包含英文、数字、-_符号，长度为2-30位");
        }

        return ValidationResult.success();
    }

    /**
     * 批量校验注册信息
     *
     * @param username 用户名
     * @param email    邮箱
     * @param password 密码
     * @return 校验结果
     */
    public static ValidationResult validateRegisterInfo(String username, String email, String password) {
        // 校验用户名
        ValidationResult usernameResult = validateUsername(username);
        if (!usernameResult.isSuccess()) {
            return usernameResult;
        }

        // 校验邮箱
        ValidationResult emailResult = validateEmail(email);
        if (!emailResult.isSuccess()) {
            return emailResult;
        }

        // 校验密码
        ValidationResult passwordResult = validatePassword(password);
        if (!passwordResult.isSuccess()) {
            return passwordResult;
        }

        return ValidationResult.success();
    }

    /**
     * 检查字符串是否包含中文
     *
     * @param input 输入字符串
     * @return 是否包含中文
     */
    private static boolean containsChinese(String input) {
        return CHINESE_PATTERN.matcher(input).matches();
    }

    /**
     * 校验结果类
     */
    public static class ValidationResult {
        private final boolean success;
        private final String message;

        private ValidationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public static ValidationResult success() {
            return new ValidationResult(true, "校验成功");
        }

        public static ValidationResult failure(String message) {
            return new ValidationResult(false, message);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        /**
         * 如果校验失败，抛出业务异常
         */
        public void throwIfFailed() {
            if (!success) {
                throw new BusinessException(message);
            }
        }
    }
}