-- ============================================================
-- 核心账户表 (auths_account)
-- 存储系统用户账户的基本信息，包括登录凭证和状态
-- ============================================================
DROP TABLE IF EXISTS auths_account;
CREATE TABLE auths_account
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    username             VARCHAR(64)  NOT NULL COMMENT '用户名，登录标识',
    password             VARCHAR(100) NOT NULL COMMENT '加密后的密码',
    email                VARCHAR(128) COMMENT '邮箱地址',
    telephone            VARCHAR(20) COMMENT '手机号码',
    status               SMALLINT DEFAULT 0 COMMENT '账户状态：0-正常, 1-锁定, 2-禁用',

    password_strength    SMALLINT DEFAULT 0 COMMENT '密码强度等级：0-3',
    last_password_change TIMESTAMP COMMENT '最后修改密码的时间',
    last_login_time      TIMESTAMP COMMENT '最后登录时间',
    last_login_ip        VARCHAR(64) COMMENT '最后登录IP地址',
    login_count          INTEGER  DEFAULT 0 COMMENT '登录次数统计',

    -- 审计记录基础字段
    is_deleted           BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at           DATETIME COMMENT '逻辑删除时间',
    deleted_by           VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at           DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by           VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at           DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by           VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_account_username (username) USING BTREE,
    INDEX idx_account_email (email) USING BTREE,
    INDEX idx_account_telephone (telephone) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='核心账户表';

-- ============================================================
-- 账户-角色关联表 (auths_account_role)
-- 维护账户与角色之间的多对多关系
-- ============================================================
DROP TABLE IF EXISTS auths_account_role;
CREATE TABLE auths_account_role
(
    id         VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    account_id VARCHAR(32) NOT NULL COMMENT '账户ID',
    role_id    VARCHAR(32) NOT NULL COMMENT '角色ID',
    -- 索引创建
    INDEX idx_account_role_account (account_id) USING BTREE,
    INDEX idx_account_role_role (role_id) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='账户角色关联表';

-- ============================================================
-- 用户组表 (auths_group)
-- 管理系统用户分组信息，支持层级结构
-- ============================================================
DROP TABLE IF EXISTS auths_group;
CREATE TABLE auths_group
(
    -- 基础字段
    id             VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    parent_id      VARCHAR(32) COMMENT '父级组ID',
    name           VARCHAR(100) COMMENT '用户组名称',
    code           VARCHAR(50) COMMENT '用户组编码',
    description    VARCHAR(255) COMMENT '用户组描述',
    sort           SMALLINT DEFAULT 99 COMMENT '排序号，数字越小越靠前',
    admin_id       VARCHAR(32) COMMENT '管理员ID',
    max_user_count INTEGER COMMENT '最大用户数量限制',

    -- 审计记录基础字段
    is_deleted     BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at     DATETIME COMMENT '逻辑删除时间',
    deleted_by     VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by     VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by     VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_group_code (code) USING BTREE
) COMMENT ='用户组表';

-- ============================================================
-- 账户-用户组关联表 (auths_account_group)
-- 维护账户与用户组之间的多对多关系
-- ============================================================
DROP TABLE IF EXISTS auths_account_group;
CREATE TABLE auths_account_group
(
    id         VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    account_id VARCHAR(32) NOT NULL COMMENT '账户ID',
    group_id   VARCHAR(32) NOT NULL COMMENT '角色ID',
    -- 索引创建
    INDEX idx_account_role_account (account_id) USING BTREE,
    INDEX idx_account_role_group (group_id) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='账户用户组关联表';


-- ============================================================
-- 角色表 (auths_role)
-- 定义系统角色及其数据权限范围
-- ============================================================
DROP TABLE IF EXISTS auths_role;
CREATE TABLE auths_role
(
    -- 基础字段
    id               VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    name             VARCHAR(255) COMMENT '角色名称',
    code             VARCHAR(50) COMMENT '角色编码',
    data_scope       VARCHAR(50) COMMENT '数据权限范围',
    description      VARCHAR(255) COMMENT '角色描述',
    assign_group_ids JSON COMMENT '分配的用户组ID列表(JSON数组)',

    -- 审计记录基础字段
    is_deleted       BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at       DATETIME COMMENT '逻辑删除时间',
    deleted_by       VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by       VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by       VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_role_code (code) USING BTREE
) COMMENT ='角色表';

-- ============================================================
-- 角色-菜单关联表 (auths_role_menu)
-- 维护角色与菜单权限之间的多对多关系
-- ============================================================
DROP TABLE IF EXISTS auths_role_menu;
CREATE TABLE auths_role_menu
(
    id      VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    role_id VARCHAR(32) NOT NULL COMMENT '角色ID',
    menu_id VARCHAR(32) NOT NULL COMMENT '菜单ID',
    -- 索引创建
    INDEX idx_role_menu_role (role_id) USING BTREE,
    INDEX idx_role_menu_menu (menu_id) USING BTREE
) COMMENT ='角色菜单关联表';

-- ============================================================
-- 密码重置表 (auths_password_reset)
-- 管理用户密码重置流程和令牌
-- ============================================================
DROP TABLE IF EXISTS auths_password_reset;
CREATE TABLE auths_password_reset
(
    -- 基础字段
    id         VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    account_id VARCHAR(32)  NOT NULL COMMENT '账户ID',
    token      VARCHAR(100) NOT NULL COMMENT '重置令牌',
    email      VARCHAR(255) NOT NULL COMMENT '接收重置邮件的邮箱',
    expires_at TIMESTAMP    NOT NULL COMMENT '令牌过期时间',
    used       BOOLEAN  DEFAULT FALSE COMMENT '是否已使用',

    -- 审计记录基础字段
    is_deleted BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at DATETIME COMMENT '逻辑删除时间',
    deleted_by VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_password_reset_token (token) USING BTREE,
    INDEX idx_password_reset_account (account_id) USING BTREE
) COMMENT ='密码重置表';
