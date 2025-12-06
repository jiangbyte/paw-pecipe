-- 必须先重置事务
ROLLBACK;

-- 网站配置初始化脚本
BEGIN;

-- 插入配置分组
INSERT INTO config_group (id, name, code, description, sort, is_system, created_at, updated_at)
VALUES ('1', '网站配置', 'WEBSITE', '网站基础信息配置', 1, TRUE, NOW(), NOW());

-- 插入整理后的配置项（只保留代码中实际使用的字段）
INSERT INTO config_item (id, group_code, name, code, value, component_type, description, sort, created_at, updated_at)
VALUES
    -- 网站信息
    ('1', 'WEBSITE', '网站名称', 'WEBSITE_NAME', 'Galaxy 内容管理系统', 'input', '网站的名称', 1, NOW(), NOW()),
    ('2', 'WEBSITE', '网站Logo', 'WEBSITE_LOGO', '/static/images/logo.png', 'upload', '网站的Logo图片路径', 2, NOW(), NOW()),
    ('3', 'WEBSITE', '网站描述', 'WEBSITE_DESCRIPTION', '一个现代化的内容管理系统，基于 Go 语言开发', 'textarea', '网站的简要描述', 3, NOW(), NOW()),
    ('4', 'WEBSITE', '网站关键字', 'WEBSITE_KEYWORDS', 'CMS,内容管理,Go,Golang,Galaxy', 'input', '网站SEO关键字，用逗号分隔', 4, NOW(), NOW()),
    ('5', 'WEBSITE', '网站作者', 'WEBSITE_AUTHOR', 'Galaxy Team', 'input', '网站的作者或开发团队', 5, NOW(), NOW()),
    ('6', 'WEBSITE', '版权信息', 'WEBSITE_COPYRIGHT', '© 2025 Galaxy CMS. All Rights Reserved.', 'input', '网站版权信息', 6, NOW(), NOW()),
    ('7', 'WEBSITE', '网站版本', 'WEBSITE_VERSION', '1.1.0', 'input', '网站的版本号', 7, NOW(), NOW()),

    -- 联系信息
    ('8', 'WEBSITE', 'QQ联系方式', 'CONTACT_QQ', '', 'input', 'QQ联系方式', 8, NOW(), NOW()),
    ('9', 'WEBSITE', '联系邮箱', 'CONTACT_EMAIL', 'contact@galaxy-cms.com', 'input', '网站联系邮箱', 9, NOW(), NOW()),
    ('10', 'WEBSITE', '微信联系方式', 'CONTACT_WECHAT', '', 'input', '微信联系方式', 10, NOW(), NOW()),

    -- 社交链接
    ('11', 'WEBSITE', '社交媒体链接', 'SOCIAL_LINKS', '[{"image": "/static/images/github.png", "title": "GitHub", "url": "https://github.com/galaxy-cms"}]', 'json-editor', '社交媒体链接配置，JSON格式', 11, NOW(), NOW());



-- 插入默认菜单数据
insert into public.sys_menu (id, pid, name, path, component_path, redirect, external_url, menu_type, open_target, iframe_attrs, title, icon, sort, keep_alive, visible, pined, without_tab, parameters, extra_params, is_deleted, deleted_at, delete_user, created_at, create_user, updated_at, update_user)
values  ('1', '0', 'dashboard', '/dashboard', '/dashboard/index.vue', null, null, 0, 0, null, '仪表盘', 'dashboard', 1, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('2', '0', 'system', '/system', null, null, null, 0, 0, null, '系统管理', 'setting', 2, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('6', '0', 'config', '/config', null, null, null, 0, 0, null, '配置管理', 'setting', 3, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('9', '0', 'auth', '/auth', null, null, null, 0, 0, null, '权限管理', 'user-safety', 4, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('19', '0', 'authentication', '/authentication', null, null, null, 0, 0, null, '认证相关', 'user-locked', 6, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('20', '19', 'login', '/authentication/login', '/authentication/login.vue', null, null, 0, 0, null, '登录', 'login', 1, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('21', '19', 'register', '/authentication/register', '/authentication/register.vue', null, null, 0, 0, null, '注册', 'user-add', 2, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('22', '19', 'forget', '/authentication/forget', '/authentication/forget.vue', null, null, 0, 0, null, '忘记密码', 'user-password', 3, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('23', '19', 'reset', '/authentication/reset', '/authentication/reset.vue', null, null, 0, 0, null, '重置密码', 'user-password', 4, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('24', '0', 'code', '/code', null, null, null, 0, 0, null, '错误页面', 'error-circle', 7, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('25', '24', '404', '/code/404', '/code/404.vue', null, null, 0, 0, null, '404页面', 'file-unknown', 1, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('13', '9', 'authpasswordreset', '/auth/password/reset', '/auth/authpasswordreset/index.vue', null, null, 0, 0, null, '密码重置', 'user-password', 4, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('10', '9', 'authaccount', '/auth/account', '/auth/authaccount/index.vue', null, null, 0, 0, null, '账号管理', 'user', 1, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('12', '9', 'authgroup', '/auth/group', '/auth/authgroup/index.vue', null, null, 0, 0, null, '用户组管理', 'usergroup-add', 3, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('11', '9', 'authrole', '/auth/role', '/auth/authrole/index.vue', null, null, 0, 0, null, '角色管理', 'user-checked', 2, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('4', '2', 'sysdict', '/system/dict', '/system/sysdict/index.vue', null, null, 0, 0, null, '数据字典', 'book', 2, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('3', '2', 'sysmenu', '/system/menu', '/system/sysmenu/index.vue', null, null, 0, 0, null, '菜单管理', 'menu', 1, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('5', '2', 'syslog', '/system/log', '/system/syslog/index.vue', null, null, 0, 0, null, '操作日志', 'system-log', 3, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('8', '6', 'configitem', '/config/item', '/config/configitem/index.vue', null, null, 0, 0, null, '配置项管理', 'setting', 2, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('7', '6', 'configgroup', '/config/group', '/config/configgroup/index.vue', null, null, 0, 0, null, '配置分组', 'setting', 1, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('18', '14', 'userstats', '/user/stats', '/user/userstats/index.vue', null, null, 0, 0, null, '数据统计', 'chart-bar', 4, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('16', '14', 'userinfo', '/user/info', '/user/userinfo/index.vue', null, null, 0, 0, null, '账户信息', 'info-circle', 2, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('17', '14', 'userpreference', '/user/preference', '/user/userpreference/index.vue', null, null, 0, 0, null, '偏好设置', 'setting', 3, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('14', '0', 'user', '/user', null, null, null, 0, 0, null, '用户中心', 'user', 5, false, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null),
        ('15', '14', 'userprofile', '/user/profile', '/user/userprofile/index.vue', null, null, 0, 0, null, '用户信息', 'user-marked', 1, true, true, false, false, null, null, false, null, null, '2025-11-19 02:41:46.956364', null, '2025-11-19 02:41:46.956364', null);

-- 插入默认角色数据
INSERT INTO auth_role (id, name, code, data_scope, description, created_at, updated_at)
VALUES ('1', '超级管理员', 'SUPER_ADMIN', 'ALL', '系统超级管理员，拥有所有权限', NOW(), NOW()),
       ('2', '系统管理员', 'ADMIN', 'GROUP_AND_CHILD', '系统管理员，拥有大部分管理权限', NOW(), NOW()),
       ('3', '部门管理员', 'GROUP_ADMIN', 'GROUP', '部门管理员，管理本部门用户和权限', NOW(), NOW()),
       ('4', '普通用户', 'USER', 'SELF', '普通用户，只能查看和操作自己的数据', NOW(), NOW());

-- 插入角色菜单关联
INSERT INTO auth_role_menu (id, role_id, menu_id)
VALUES
-- 超级管理员拥有所有菜单权限 (1-25)
('1', '1', '1'),
('2', '1', '2'),
('3', '1', '3'),
('4', '1', '4'),
('5', '1', '5'),
('6', '1', '6'),
('7', '1', '7'),
('8', '1', '8'),
('9', '1', '9'),
('10', '1', '10'),
('11', '1', '11'),
('12', '1', '12'),
('13', '1', '13'),
('14', '1', '14'),
('15', '1', '15'),
('16', '1', '16'),
('17', '1', '17'),
('18', '1', '18'),
('19', '1', '19'),
('20', '1', '20'),
('21', '1', '21'),
('22', '1', '22'),
('23', '1', '23'),
('24', '1', '24'),
('25', '1', '25'),

-- 系统管理员排除认证相关和错误页面菜单 (1-18)
('26', '2', '1'),
('27', '2', '2'),
('28', '2', '3'),
('29', '2', '4'),
('30', '2', '5'),
('31', '2', '6'),
('32', '2', '7'),
('33', '2', '8'),
('34', '2', '9'),
('35', '2', '10'),
('36', '2', '11'),
('37', '2', '12'),
('38', '2', '13'),
('39', '2', '14'),
('40', '2', '15'),
('41', '2', '16'),
('42', '2', '17'),
('43', '2', '18'),

-- 部门管理员拥有基础管理和个人中心权限 (1, 14-18, 部分系统管理)
('44', '3', '1'),  -- 仪表盘
('45', '3', '14'),
('46', '3', '15'),
('47', '3', '16'),
('48', '3', '17'),
('49', '3', '18'), -- 个人中心
('50', '3', '9'),
('51', '3', '10'), -- 权限管理中的账号管理
('52', '3', '6'),
('53', '3', '7'),
('54', '3', '8'),  -- 配置管理

-- 普通用户只拥有个人中心相关权限 (1, 14-18)
('55', '4', '1'),  -- 仪表盘
('56', '4', '14'),
('57', '4', '15'),
('58', '4', '16'),
('59', '4', '17'),
('60', '4', '18');
-- 个人中心

-- 定义常量并插入数据
DO
$$
    DECLARE
common_password CONSTANT TEXT := '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi';
        common_avatar   CONSTANT TEXT := 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0';
BEGIN

        -- ============= 超级管理员 =============
        -- 插入默认用户数据
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('1', 'superadmin', common_password, 'jiangbyte@163.com', NOW(), NOW());
-- 插入用户信息
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('1', '1', '超级管理员', common_avatar, NOW(), NOW());
-- 插入用户档案
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('1', '1', NOW(), NOW());
-- 插入用户偏好设置
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('1', '1', NOW(), NOW());
-- 插入用户统计
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('1', '1', NOW(), NOW());
-- 插入用户角色关联
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('1', '1', '1');

-- ============= 系统管理员 =============
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('2', 'admin', common_password, 'admin@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('2', '2', '系统管理员', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('2', '2', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('2', '2', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('2', '2', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('2', '2', '2');

-- ============= 部门管理员 =============
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('3', 'deptadmin', common_password, 'dept_admin@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('3', '3', '技术部经理', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('3', '3', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('3', '3', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('3', '3', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('3', '3', '3');

-- ============= 普通用户 =============
-- 用户4
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('4', 'zhangsan', common_password, 'zhangsan@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('4', '4', '张三', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('4', '4', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('4', '4', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('4', '4', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('4', '4', '4');

-- 用户5
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('5', 'lisi', common_password, 'lisi@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('5', '5', '李四', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('5', '5', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('5', '5', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('5', '5', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('5', '5', '4');

-- 用户6
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('6', 'wangwu', common_password, 'wangwu@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('6', '6', '王五', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('6', '6', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('6', '6', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('6', '6', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('6', '6', '4');

-- 用户7
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('7', 'zhaoliu', common_password, 'zhaoliu@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('7', '7', '赵六', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('7', '7', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('7', '7', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('7', '7', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('7', '7', '4');

-- 用户8
INSERT INTO auth_account (id, username, password, email, created_at, updated_at)
VALUES ('8', 'sunqi', common_password, 'sunqi@company.com', NOW(), NOW());
INSERT INTO user_info (id, account_id, nickname, avatar, created_at, updated_at)
VALUES ('8', '8', '孙七', common_avatar, NOW(), NOW());
INSERT INTO user_profile (id, account_id, created_at, updated_at)
VALUES ('8', '8', NOW(), NOW());
INSERT INTO user_preference (id, account_id, created_at, updated_at)
VALUES ('8', '8', NOW(), NOW());
INSERT INTO user_stats (id, account_id, created_at, updated_at)
VALUES ('8', '8', NOW(), NOW());
INSERT INTO auth_account_role (id, account_id, role_id)
VALUES ('8', '8', '4');

END
$$;


COMMIT;


