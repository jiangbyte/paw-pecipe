-- ============================================================
-- 核心账户表 (auth_account)
-- 存储系统用户账户的基本信息，包括登录凭证和状态
-- ============================================================
DROP TABLE IF EXISTS auth_account;
CREATE TABLE auth_account
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    username             VARCHAR(64)  NOT NULL,   -- 用户名，登录标识
    password             VARCHAR(100) NOT NULL,   -- 加密后的密码
    email                VARCHAR(128),            -- 邮箱地址
    telephone            VARCHAR(20),             -- 手机号码
    group_id             VARCHAR(32),             -- 账户所属组ID
    status               SMALLINT  DEFAULT 0,     -- 账户状态：0-正常, 1-锁定, 2-禁用
    password_strength    SMALLINT  DEFAULT 0,     -- 密码强度等级：0-3
    last_password_change TIMESTAMP,               -- 最后修改密码的时间
    last_login_time      TIMESTAMP,               -- 最后登录时间
    last_login_ip        VARCHAR(64),             -- 最后登录IP地址
    login_count          INTEGER   DEFAULT 0,      -- 登录次数统计
    -- 基础字段
    is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE auth_account IS '核心账户表';
COMMENT ON COLUMN auth_account.id IS '主键ID';
COMMENT ON COLUMN auth_account.is_deleted IS '是否删除';
COMMENT ON COLUMN auth_account.deleted_at IS '软删除时间';
COMMENT ON COLUMN auth_account.delete_user IS '删除操作人';
COMMENT ON COLUMN auth_account.created_at IS '创建时间';
COMMENT ON COLUMN auth_account.create_user IS '创建人';
COMMENT ON COLUMN auth_account.updated_at IS '更新时间';
COMMENT ON COLUMN auth_account.update_user IS '更新人';
COMMENT ON COLUMN auth_account.username IS '用户名';
COMMENT ON COLUMN auth_account.password IS '加密后的密码';
COMMENT ON COLUMN auth_account.email IS '邮箱地址';
COMMENT ON COLUMN auth_account.telephone IS '手机号码';
COMMENT ON COLUMN auth_account.group_id IS '账户所属组ID';
COMMENT ON COLUMN auth_account.status IS '账户状态';
COMMENT ON COLUMN auth_account.password_strength IS '密码强度等级';
COMMENT ON COLUMN auth_account.last_password_change IS '最后修改密码的时间';
COMMENT ON COLUMN auth_account.last_login_time IS '最后登录时间';
COMMENT ON COLUMN auth_account.last_login_ip IS '最后登录IP地址';
COMMENT ON COLUMN auth_account.login_count IS '登录次数统计';

-- 创建核心账户表索引
CREATE UNIQUE INDEX idx_account_username ON auth_account (username); -- 用户名唯一索引
CREATE UNIQUE INDEX idx_account_email ON auth_account (email); -- 邮箱唯一索引
CREATE UNIQUE INDEX idx_account_telephone ON auth_account (telephone);
-- 手机号唯一索引

-- ============================================================
-- 账户-角色关联表 (auth_account_role)
-- 维护账户与角色之间的多对多关系
-- ============================================================
DROP TABLE IF EXISTS auth_account_role;
CREATE TABLE auth_account_role
(
    id         VARCHAR(32) PRIMARY KEY, -- 主键ID
    account_id VARCHAR(32) NOT NULL,    -- 账户ID
    role_id    VARCHAR(32) NOT NULL     -- 角色ID
);

COMMENT ON TABLE auth_account_role IS '账户角色关联表';
COMMENT ON COLUMN auth_account_role.id IS '主键ID';
COMMENT ON COLUMN auth_account_role.account_id IS '账户ID';
COMMENT ON COLUMN auth_account_role.role_id IS '角色ID';

-- 创建账户-角色关联表索引
CREATE INDEX idx_account_role_account ON auth_account_role (account_id); -- 账户ID索引
CREATE INDEX idx_account_role_role ON auth_account_role (role_id);
-- 角色ID索引

-- ============================================================
-- 用户组表 (auth_group)
-- 管理系统用户分组信息，支持层级结构
-- ============================================================
DROP TABLE IF EXISTS auth_group;
CREATE TABLE auth_group
(
    -- 基础字段
    id             VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    parent_id      VARCHAR(32),             -- 父级组ID
    name           VARCHAR(100),            -- 用户组名称
    code           VARCHAR(50),             -- 用户组编码
    description    VARCHAR(255),            -- 用户组描述
    sort           SMALLINT  DEFAULT 99,    -- 排序号，数字越小越靠前
    admin_id       VARCHAR(32),             -- 管理员ID
    max_user_count INTEGER,                 -- 最大用户数量限制
    is_system      BOOLEAN   DEFAULT FALSE,  -- 是否为系统预设组
    -- 基础字段
    is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE auth_group IS '用户组表';
COMMENT ON COLUMN auth_group.id IS '主键ID';
COMMENT ON COLUMN auth_group.is_deleted IS '是否删除';
COMMENT ON COLUMN auth_group.deleted_at IS '软删除时间';
COMMENT ON COLUMN auth_group.delete_user IS '删除操作人';
COMMENT ON COLUMN auth_group.created_at IS '创建时间';
COMMENT ON COLUMN auth_group.create_user IS '创建人';
COMMENT ON COLUMN auth_group.updated_at IS '更新时间';
COMMENT ON COLUMN auth_group.update_user IS '更新人';
COMMENT ON COLUMN auth_group.parent_id IS '父级组ID';
COMMENT ON COLUMN auth_group.name IS '用户组名称';
COMMENT ON COLUMN auth_group.code IS '用户组编码';
COMMENT ON COLUMN auth_group.description IS '用户组描述';
COMMENT ON COLUMN auth_group.sort IS '排序号';
COMMENT ON COLUMN auth_group.admin_id IS '管理员ID';
COMMENT ON COLUMN auth_group.max_user_count IS '最大用户数量限制';
COMMENT ON COLUMN auth_group.is_system IS '是否为系统预设组';

-- 创建用户组表索引
CREATE UNIQUE INDEX idx_group_code ON auth_group (code);
-- 用户组编码唯一索引

-- ============================================================
-- 角色表 (auth_role)
-- 定义系统角色及其数据权限范围
-- ============================================================
DROP TABLE IF EXISTS auth_role;
CREATE TABLE auth_role
(
    -- 基础字段
    id               VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    name             VARCHAR(255),            -- 角色名称
    code             VARCHAR(50),             -- 角色编码
    data_scope       VARCHAR(50),             -- 数据权限范围
    description      VARCHAR(255),            -- 角色描述
    assign_group_ids JSONB,                    -- 分配的用户组ID列表(JSON数组)
    -- 基础字段
    is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE auth_role IS '角色表';
COMMENT ON COLUMN auth_role.id IS '主键ID';
COMMENT ON COLUMN auth_role.is_deleted IS '是否删除';
COMMENT ON COLUMN auth_role.deleted_at IS '软删除时间';
COMMENT ON COLUMN auth_role.delete_user IS '删除操作人';
COMMENT ON COLUMN auth_role.created_at IS '创建时间';
COMMENT ON COLUMN auth_role.create_user IS '创建人';
COMMENT ON COLUMN auth_role.updated_at IS '更新时间';
COMMENT ON COLUMN auth_role.update_user IS '更新人';
COMMENT ON COLUMN auth_role.name IS '角色名称';
COMMENT ON COLUMN auth_role.code IS '角色编码';
COMMENT ON COLUMN auth_role.data_scope IS '数据权限范围';
COMMENT ON COLUMN auth_role.description IS '角色描述';
COMMENT ON COLUMN auth_role.assign_group_ids IS '分配的用户组ID列表';

-- 创建角色表索引
CREATE INDEX idx_role_name ON auth_role (name); -- 角色名称索引
CREATE INDEX idx_role_code ON auth_role (code); -- 角色编码索引
CREATE INDEX idx_role_data_scope ON auth_role (data_scope);
-- 数据权限范围索引

-- ============================================================
-- 角色-菜单关联表 (auth_role_menu)
-- 维护角色与菜单权限之间的多对多关系
-- ============================================================
DROP TABLE IF EXISTS auth_role_menu;
CREATE TABLE auth_role_menu
(
    id      VARCHAR(32) PRIMARY KEY, -- 主键ID
    role_id VARCHAR(32) NOT NULL,    -- 角色ID
    menu_id VARCHAR(32) NOT NULL     -- 菜单ID
);

COMMENT ON TABLE auth_role_menu IS '角色菜单关联表';
COMMENT ON COLUMN auth_role_menu.id IS '主键ID';
COMMENT ON COLUMN auth_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN auth_role_menu.menu_id IS '菜单ID';

-- 创建角色-菜单关联表索引
CREATE INDEX idx_role_menu_role ON auth_role_menu (role_id); -- 角色ID索引
CREATE INDEX idx_role_menu_menu ON auth_role_menu (menu_id);
-- 菜单ID索引

-- ============================================================
-- 密码重置表 (auth_password_reset)
-- 管理用户密码重置流程和令牌
-- ============================================================
DROP TABLE IF EXISTS auth_password_reset;
CREATE TABLE auth_password_reset
(
    -- 基础字段
    id          VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    account_id  VARCHAR(32)  NOT NULL,   -- 账户ID
    token       VARCHAR(100) NOT NULL,   -- 重置令牌
    email       VARCHAR(255) NOT NULL,   -- 接收重置邮件的邮箱
    expires_at  TIMESTAMP    NOT NULL,   -- 令牌过期时间
    used        BOOLEAN   DEFAULT FALSE,  -- 是否已使用
    -- 基础字段
    is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE auth_password_reset IS '密码重置表';
COMMENT ON COLUMN auth_password_reset.id IS '主键ID';
COMMENT ON COLUMN auth_password_reset.is_deleted IS '是否删除';
COMMENT ON COLUMN auth_password_reset.deleted_at IS '软删除时间';
COMMENT ON COLUMN auth_password_reset.delete_user IS '删除操作人';
COMMENT ON COLUMN auth_password_reset.created_at IS '创建时间';
COMMENT ON COLUMN auth_password_reset.create_user IS '创建人';
COMMENT ON COLUMN auth_password_reset.updated_at IS '更新时间';
COMMENT ON COLUMN auth_password_reset.update_user IS '更新人';
COMMENT ON COLUMN auth_password_reset.account_id IS '账户ID';
COMMENT ON COLUMN auth_password_reset.token IS '重置令牌';
COMMENT ON COLUMN auth_password_reset.email IS '接收重置邮件的邮箱';
COMMENT ON COLUMN auth_password_reset.expires_at IS '令牌过期时间';
COMMENT ON COLUMN auth_password_reset.used IS '是否已使用';

-- 创建密码重置表索引
CREATE UNIQUE INDEX idx_password_reset_token ON auth_password_reset (token); -- 令牌唯一索引
CREATE INDEX idx_password_reset_account ON auth_password_reset (account_id); -- 账户ID索引
CREATE INDEX idx_password_reset_expires ON auth_password_reset (expires_at); -- 过期时间索引