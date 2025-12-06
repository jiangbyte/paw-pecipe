package io.jiangbyte.app.base.access.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordReq {
    @NotBlank(message = "邮箱不能为空")
    private String email;
    
    @NotBlank(message = "验证码ID不能为空")
    private String captchaId;
    
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}
