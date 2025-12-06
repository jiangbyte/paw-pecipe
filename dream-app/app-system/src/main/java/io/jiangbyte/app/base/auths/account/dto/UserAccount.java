package io.jiangbyte.app.base.auths.account.dto;

import io.jiangbyte.app.base.auths.account.entity.AuthsAccount;
import io.jiangbyte.app.base.users.info.entity.UsersInfo;
import io.jiangbyte.app.base.users.preference.entity.UsersPreference;
import io.jiangbyte.app.base.users.profile.entity.UsersProfile;
import io.jiangbyte.app.base.users.stats.entity.UsersStats;
import lombok.Data;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 25/11/2025
 * @description TODO
 */
@Data
public class UserAccount {
    private AuthsAccount account;
    private UsersInfo info;
    private UsersPreference preference;
    private UsersProfile profile;
    private UsersStats stats;
}
