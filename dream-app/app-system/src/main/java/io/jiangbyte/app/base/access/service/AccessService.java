package io.jiangbyte.app.base.access.service;

import io.jiangbyte.app.base.access.dto.*;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 18/11/2025
 * @description TODO
 */
public interface AccessService {
    CaptchaResp captcha();

    LoginResp doLogin(LoginReq loginReq);

    RegisterResp doRegister(RegisterReq registerReq);

    void doLogout();

    // 密码相关

    Boolean doResetPassword(ResetPasswordReq resetPasswordReq);

    Boolean validateResetPasswordToken(String token);

    Boolean confirmResetPassword(ResetPasswordConfirmReq confirmReq);
}
