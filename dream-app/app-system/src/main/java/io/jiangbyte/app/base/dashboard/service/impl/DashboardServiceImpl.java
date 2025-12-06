package io.jiangbyte.app.base.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccount;
import io.jiangbyte.app.base.auths.account.mapper.AuthsAccountMapper;
import io.jiangbyte.app.base.auths.group.entity.AuthsGroup;
import io.jiangbyte.app.base.auths.group.mapper.AuthsGroupMapper;
import io.jiangbyte.app.base.auths.role.entity.AuthsRole;
import io.jiangbyte.app.base.auths.role.mapper.AuthsRoleMapper;
import io.jiangbyte.app.base.dashboard.dto.PaneItem;
import io.jiangbyte.app.base.dashboard.service.DashboardService;
import io.jiangbyte.app.base.users.info.mapper.UsersInfoMapper;
import io.jiangbyte.app.base.users.preference.mapper.UsersPreferenceMapper;
import io.jiangbyte.app.base.users.profile.mapper.UsersProfileMapper;
import io.jiangbyte.app.base.users.stats.mapper.UsersStatsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 26/11/2025
 * @description TODO
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final AuthsAccountMapper authsAccountMapper;
    private final UsersInfoMapper usersInfoMapper;
    private final UsersPreferenceMapper usersPreferenceMapper;
    private final UsersProfileMapper usersProfileMapper;
    private final UsersStatsMapper usersStatsMapper;
    private final AuthsGroupMapper authsGroupMapper;
    private final AuthsRoleMapper authsRoleMapper;

    @Override
    public List<PaneItem> getDashboardPaneItems() {
        List<PaneItem> dashboardPaneItems = new ArrayList<>();

        // 统计总用户数
        dashboardPaneItems.add(createTotalAccountItem());

        // 活跃用户数（近期登录的）
        dashboardPaneItems.add(createActiveAccountItem());

        // 角色数
        dashboardPaneItems.add(createRoleCountItem());

        // 用户组数
        dashboardPaneItems.add(createGroupCountItem());

        return dashboardPaneItems;
    }

    /**
     * 创建总用户数统计项
     */
    private PaneItem createTotalAccountItem() {
        Long totalAccount = authsAccountMapper.selectCount(new LambdaQueryWrapper<AuthsAccount>()
                .eq(AuthsAccount::getStatus, 0) // 0-正常
        );
        return PaneItem.builder()
                .icon("user")
                .title("总用户数")
                .value(BigDecimal.valueOf(totalAccount))
                .unit("人")
                .build();
    }

    /**
     * 创建活跃用户数统计项
     */
    private PaneItem createActiveAccountItem() {
        Long activeAccount = authsAccountMapper.selectCount(new LambdaQueryWrapper<AuthsAccount>()
                .ge(AuthsAccount::getLastLoginTime, System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000) // 7天
        );
        return PaneItem.builder()
                .icon("user-time")
                .title("活跃用户数")
                .value(BigDecimal.valueOf(activeAccount))
                .unit("人")
                .build();
    }

    /**
     * 创建角色数统计项
     */
    private PaneItem createRoleCountItem() {
        Long roleCount = authsRoleMapper.selectCount(new LambdaQueryWrapper<AuthsRole>());
        return PaneItem.builder()
                .icon("user-safety")
                .title("角色数")
                .value(BigDecimal.valueOf(roleCount))
                .unit("个")
                .build();
    }

    /**
     * 创建用户组数统计项
     */
    private PaneItem createGroupCountItem() {
        Long groupCount = authsGroupMapper.selectCount(new LambdaQueryWrapper<AuthsGroup>());
        return PaneItem.builder()
                .icon("usergroup")
                .title("用户组数")
                .value(BigDecimal.valueOf(groupCount))
                .unit("个")
                .build();
    }

}
