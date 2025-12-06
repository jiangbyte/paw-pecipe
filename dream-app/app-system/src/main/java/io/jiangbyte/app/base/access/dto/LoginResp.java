package io.jiangbyte.app.base.access.dto;

import lombok.Data;

@Data
public class LoginResp {
    private String token;
    private UserPublicAssociatedInfo user;
}