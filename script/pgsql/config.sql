-- ============================================================
-- 配置分组表 (config_group)
-- 管理系统配置的分组信息
-- ============================================================
DROP TABLE IF EXISTS config_group;
CREATE TABLE config_group
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    name        VARCHAR(100) NOT NULL,   -- 分组名称
    code        VARCHAR(100) NOT NULL,   -- 分组代码
    description VARCHAR(500),            -- 分组描述
    sort        INTEGER   DEFAULT 0,     -- 排序
    is_system   BOOLEAN   DEFAULT FALSE  -- 是否系统分组
    -- 基础字段
    ,is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE config_group IS '配置分组表';
COMMENT ON COLUMN config_group.id IS '主键ID';
COMMENT ON COLUMN config_group.deleted_at IS '软删除时间';
COMMENT ON COLUMN config_group.delete_user IS '删除操作人';
COMMENT ON COLUMN config_group.created_at IS '创建时间';
COMMENT ON COLUMN config_group.create_user IS '创建人';
COMMENT ON COLUMN config_group.updated_at IS '更新时间';
COMMENT ON COLUMN config_group.update_user IS '更新人';
COMMENT ON COLUMN config_group.name IS '分组名称';
COMMENT ON COLUMN config_group.code IS '分组代码';
COMMENT ON COLUMN config_group.description IS '分组描述';
COMMENT ON COLUMN config_group.sort IS '排序';
COMMENT ON COLUMN config_group.is_system IS '是否系统分组';

-- 创建配置分组表索引
CREATE UNIQUE INDEX idx_config_group_code ON config_group (code);
-- 分组代码唯一索引

-- ============================================================
-- 系统配置表 (config_item)
-- 存储系统各项配置参数
-- ============================================================
DROP TABLE IF EXISTS config_item;
CREATE TABLE config_item
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    group_code       VARCHAR(32)  NOT NULL,   -- 分组编码
    name           VARCHAR(255) NOT NULL,   -- 配置项名称
    code           VARCHAR(255) NOT NULL,   -- 配置项代码
    value          VARCHAR(255) NOT NULL,   -- 配置值
    component_type VARCHAR(255),            -- 组件类型
    description    VARCHAR(255),            -- 配置描述
    sort           INTEGER   DEFAULT 0      -- 排序
    -- 基础字段
    ,is_deleted           BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at           TIMESTAMP,               -- 软删除时间
    delete_user          VARCHAR(32),             -- 删除操作人
    created_at           TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user          VARCHAR(32),             -- 创建人
    updated_at           TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user          VARCHAR(32)             -- 更新人
);

COMMENT ON TABLE config_item IS '系统配置表';
COMMENT ON COLUMN config_item.id IS '主键ID';
COMMENT ON COLUMN config_item.deleted_at IS '软删除时间';
COMMENT ON COLUMN config_item.delete_user IS '删除操作人';
COMMENT ON COLUMN config_item.created_at IS '创建时间';
COMMENT ON COLUMN config_item.create_user IS '创建人';
COMMENT ON COLUMN config_item.updated_at IS '更新时间';
COMMENT ON COLUMN config_item.update_user IS '更新人';
COMMENT ON COLUMN config_item.group_code IS '分组Code';
COMMENT ON COLUMN config_item.name IS '配置项名称';
COMMENT ON COLUMN config_item.code IS '配置项代码';
COMMENT ON COLUMN config_item.value IS '配置值';
COMMENT ON COLUMN config_item.component_type IS '组件类型';
COMMENT ON COLUMN config_item.description IS '配置描述';
COMMENT ON COLUMN config_item.sort IS '排序';

-- 创建系统配置表索引
CREATE UNIQUE INDEX idx_config_item_code ON config_item (code); -- 配置项代码唯一索引