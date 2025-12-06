package io.jiangbyte.app.config;

import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展 
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {
//    private final PermissionService permissionService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
//        List<String> permissionList = permissionService.getPermissionList((String) loginId, loginType);
//        log.info("permissionList: {}", JSONUtil.toJsonStr(permissionList));
//        return permissionList;

        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return List.of();
    }

}
