package io.jiangbyte.app.base.access.dto;

import lombok.Data;

@Data
public class CaptchaResp {
    private String captchaId;
    private String captchaImg;
}