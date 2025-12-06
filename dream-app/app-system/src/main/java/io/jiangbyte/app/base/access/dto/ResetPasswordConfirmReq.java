package io.jiangbyte.app.base.access.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordConfirmReq {
    @NotBlank(message = "重置令牌不能为空")
    private String token;
    
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
    
    @NotBlank(message = "验证码ID不能为空")
    private String captchaId;
    
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}