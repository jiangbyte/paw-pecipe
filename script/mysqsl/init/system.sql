-- ============================================================
-- 系统字典表 (sys_dict)
-- 存储系统字典数据，支持类型-值的键值对配置
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict
(
    -- 基础字段
    id         VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    dict_type  VARCHAR(64)  NOT NULL COMMENT '字典类型',
    type_label VARCHAR(64) COMMENT '类型标签',
    dict_value VARCHAR(255) NOT NULL COMMENT '字典值',
    dict_label VARCHAR(255) COMMENT '字典标签',
    sort       INTEGER  DEFAULT 0 COMMENT '排序号',
    -- 审计记录基础字段
    is_deleted BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at DATETIME COMMENT '逻辑删除时间',
    deleted_by VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_dict_type (dict_type) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统字典表';

-- ============================================================
-- 系统活动/日志记录表 (sys_log)
-- 记录系统操作日志和用户活动
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log
(
    -- 基础字段
    id             VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    user_id        VARCHAR(32) COMMENT '用户ID',
    operation      VARCHAR(255) COMMENT '操作类型',
    method         VARCHAR(255) COMMENT '请求方法',
    params         TEXT COMMENT '请求参数',
    ip             VARCHAR(255) COMMENT 'IP地址',
    operation_time TIMESTAMP COMMENT '操作时间',
    category       VARCHAR(255) COMMENT '日志分类',
    module         VARCHAR(255) COMMENT '操作模块',
    description    VARCHAR(255) COMMENT '操作描述',
    status         VARCHAR(255) COMMENT '操作状态',
    message        TEXT COMMENT '日志消息',
    -- 审计记录基础字段
    is_deleted     BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at     DATETIME COMMENT '逻辑删除时间',
    deleted_by     VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by     VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by     VARCHAR(64) COMMENT '最后更新人（账号/ID）'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统活动日志记录表';

-- ============================================================
-- 菜单表 (sys_menu)
-- 管理系统菜单和路由配置信息
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu
(
    -- 基础字段
    id             VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    pid            VARCHAR(32) DEFAULT '0' COMMENT '父级ID',
    name           VARCHAR(100) COMMENT '菜单名称',
    path           VARCHAR(500) COMMENT '菜单路径',
    component_path VARCHAR(500) COMMENT '组件路径',
    redirect       VARCHAR(500) COMMENT '重定向路径',
    external_url   VARCHAR(1000) COMMENT '外部链接地址',
    menu_type      INTEGER     DEFAULT 0 COMMENT '菜单类型：0-内部菜单 1-外链菜单 2-重定向菜单 3-iframe嵌入',
    open_target    INTEGER     DEFAULT 0 COMMENT '打开方式：0-当前窗口 1-新窗口打开',
    iframe_attrs   VARCHAR(500) COMMENT 'iframe属性',
    title          VARCHAR(100) COMMENT '菜单标题',
    icon           VARCHAR(100) COMMENT '菜单图标',
    sort           INTEGER     DEFAULT 0 COMMENT '排序',
    keep_alive     BOOLEAN     DEFAULT FALSE COMMENT '缓存',
    visible        BOOLEAN     DEFAULT TRUE COMMENT '可见',
    pined          BOOLEAN     DEFAULT FALSE COMMENT '钉钉',
    without_tab    BOOLEAN     DEFAULT FALSE COMMENT '无标签页',
    parameters     VARCHAR(500) COMMENT '头部参数',
    extra_params   JSON COMMENT '路由参数', -- MySQL 5.7+用JSON，8.0+兼容JSONB
    -- 审计记录基础字段
    is_deleted     BOOLEAN     DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at     DATETIME COMMENT '逻辑删除时间',
    deleted_by     VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at     DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by     VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at     DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by     VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_sys_menu_name (name) USING BTREE,
    INDEX idx_sys_menu_path (path) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜单表';
