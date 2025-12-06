-- ============================================================
-- 系统字典表 (sys_dict)
-- 存储系统字典数据，支持类型-值的键值对配置
-- ============================================================
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    dict_type   VARCHAR(64)  NOT NULL,   -- 字典类型
    type_label  VARCHAR(64),             -- 类型标签
    dict_value  VARCHAR(255) NOT NULL,   -- 字典值
    dict_label  VARCHAR(255),            -- 字典标签
    sort        INTEGER   DEFAULT 0      -- 排序号
    -- 基础字段
    ,is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE sys_dict IS '系统字典表';
COMMENT ON COLUMN sys_dict.id IS '主键ID';
COMMENT ON COLUMN sys_dict.deleted_at IS '软删除时间';
COMMENT ON COLUMN sys_dict.delete_user IS '删除操作人';
COMMENT ON COLUMN sys_dict.created_at IS '创建时间';
COMMENT ON COLUMN sys_dict.create_user IS '创建人';
COMMENT ON COLUMN sys_dict.updated_at IS '更新时间';
COMMENT ON COLUMN sys_dict.update_user IS '更新人';
COMMENT ON COLUMN sys_dict.dict_type IS '字典类型';
COMMENT ON COLUMN sys_dict.type_label IS '类型标签';
COMMENT ON COLUMN sys_dict.dict_value IS '字典值';
COMMENT ON COLUMN sys_dict.dict_label IS '字典标签';
COMMENT ON COLUMN sys_dict.sort IS '排序号';

-- 创建系统字典表索引
CREATE UNIQUE INDEX uk_sys_dict_type_code ON sys_dict (dict_type, dict_value);
-- 类型和值的唯一索引

-- ============================================================
-- 系统活动/日志记录表 (sys_log)
-- 记录系统操作日志和用户活动
-- ============================================================
DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    user_id        VARCHAR(32),             -- 用户ID
    operation      VARCHAR(255),            -- 操作类型
    method         VARCHAR(255),            -- 请求方法
    params         TEXT,                    -- 请求参数
    ip             VARCHAR(255),            -- IP地址
    operation_time TIMESTAMP,               -- 操作时间
    category       VARCHAR(255),            -- 日志分类
    module         VARCHAR(255),            -- 操作模块
    description    VARCHAR(255),            -- 操作描述
    status         VARCHAR(255),            -- 操作状态
    message        TEXT                     -- 日志消息
    -- 基础字段
    ,is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE sys_log IS '系统活动日志记录表';
COMMENT ON COLUMN sys_log.id IS '主键ID';
COMMENT ON COLUMN sys_log.deleted_at IS '软删除时间';
COMMENT ON COLUMN sys_log.delete_user IS '删除操作人';
COMMENT ON COLUMN sys_log.created_at IS '创建时间';
COMMENT ON COLUMN sys_log.create_user IS '创建人';
COMMENT ON COLUMN sys_log.updated_at IS '更新时间';
COMMENT ON COLUMN sys_log.update_user IS '更新人';
COMMENT ON COLUMN sys_log.user_id IS '用户ID';
COMMENT ON COLUMN sys_log.operation IS '操作类型';
COMMENT ON COLUMN sys_log.method IS '请求方法';
COMMENT ON COLUMN sys_log.params IS '请求参数';
COMMENT ON COLUMN sys_log.ip IS 'IP地址';
COMMENT ON COLUMN sys_log.operation_time IS '操作时间';
COMMENT ON COLUMN sys_log.category IS '日志分类';
COMMENT ON COLUMN sys_log.module IS '操作模块';
COMMENT ON COLUMN sys_log.description IS '操作描述';
COMMENT ON COLUMN sys_log.status IS '操作状态';
COMMENT ON COLUMN sys_log.message IS '日志消息';

-- 创建系统日志表索引
CREATE INDEX idx_sys_log_user_id ON sys_log (user_id); -- 用户ID索引
CREATE INDEX idx_sys_log_operation_time ON sys_log (operation_time); -- 操作时间索引
CREATE INDEX idx_sys_log_category ON sys_log (category);
-- 日志分类索引

-- ============================================================
-- 菜单表 (sys_menu)
-- 管理系统菜单和路由配置信息
-- ============================================================
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    pid            VARCHAR(32) DEFAULT '0',   -- 父级ID
    name           VARCHAR(100),              -- 菜单名称
    path           VARCHAR(500),              -- 菜单路径
    component_path VARCHAR(500),              -- 组件路径
    redirect       VARCHAR(500),              -- 重定向路径
    -- 外链和跳转相关字段
    external_url   VARCHAR(1000),             -- 外部链接地址
    menu_type      INTEGER     DEFAULT 0,     -- 菜单类型：0-内部菜单 1-外链菜单 2-重定向菜单 3-iframe嵌入
    open_target    INTEGER     DEFAULT 0,     -- 打开方式：0-当前窗口 1-新窗口打开
    iframe_attrs   VARCHAR(500),              -- iframe属性
    -- Route Meta相关字段
    title          VARCHAR(100),              -- 菜单标题
    icon           VARCHAR(100),              -- 菜单图标
    sort           INTEGER     DEFAULT 0,     -- 排序
    keep_alive     BOOLEAN     DEFAULT FALSE, -- 缓存
    visible        BOOLEAN     DEFAULT TRUE,  -- 可见
    pined          BOOLEAN     DEFAULT FALSE, -- 钉钉
    without_tab    BOOLEAN     DEFAULT FALSE, -- 无标签页
    parameters     VARCHAR(500),              -- 头部参数
    extra_params   JSONB                      -- 路由参数
    -- 基础字段
    ,is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE sys_menu IS '菜单表';
COMMENT ON COLUMN sys_menu.id IS '主键ID';
COMMENT ON COLUMN sys_menu.deleted_at IS '软删除时间';
COMMENT ON COLUMN sys_menu.delete_user IS '删除操作人';
COMMENT ON COLUMN sys_menu.created_at IS '创建时间';
COMMENT ON COLUMN sys_menu.create_user IS '创建人';
COMMENT ON COLUMN sys_menu.updated_at IS '更新时间';
COMMENT ON COLUMN sys_menu.update_user IS '更新人';
COMMENT ON COLUMN sys_menu.pid IS '父级ID';
COMMENT ON COLUMN sys_menu.name IS '菜单名称';
COMMENT ON COLUMN sys_menu.path IS '菜单路径';
COMMENT ON COLUMN sys_menu.component_path IS '组件路径';
COMMENT ON COLUMN sys_menu.redirect IS '重定向路径';
COMMENT ON COLUMN sys_menu.external_url IS '外部链接地址';
COMMENT ON COLUMN sys_menu.menu_type IS '菜单类型：0-内部菜单 1-外链菜单 2-重定向菜单 3-iframe嵌入';
COMMENT ON COLUMN sys_menu.open_target IS '打开方式：0-当前窗口 1-新窗口打开';
COMMENT ON COLUMN sys_menu.iframe_attrs IS 'iframe属性';
COMMENT ON COLUMN sys_menu.title IS '菜单标题';
COMMENT ON COLUMN sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN sys_menu.sort IS '排序';
COMMENT ON COLUMN sys_menu.keep_alive IS '缓存';
COMMENT ON COLUMN sys_menu.visible IS '可见';
COMMENT ON COLUMN sys_menu.pined IS '钉钉';
COMMENT ON COLUMN sys_menu.without_tab IS '无标签页';
COMMENT ON COLUMN sys_menu.parameters IS '头部参数';
COMMENT ON COLUMN sys_menu.extra_params IS '路由参数';

-- 创建菜单表索引
CREATE INDEX idx_sys_menu_pid ON sys_menu (pid); -- 父级ID索引
CREATE INDEX idx_sys_menu_menu_type ON sys_menu (menu_type); -- 菜单类型索引
CREATE INDEX idx_sys_menu_sort ON sys_menu (sort); -- 排序索引