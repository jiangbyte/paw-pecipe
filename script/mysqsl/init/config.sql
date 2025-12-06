-- ============================================================
-- 配置分组表 (config_group)
-- 管理系统配置的分组信息
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS config_group;
CREATE TABLE config_group
(
    -- 基础字段
    id          VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    name        VARCHAR(100) NOT NULL COMMENT '分组名称',
    code        VARCHAR(100) NOT NULL COMMENT '分组代码',
    description VARCHAR(500) COMMENT '分组描述',
    sort        INTEGER  DEFAULT 0 COMMENT '排序',
    is_system   BOOLEAN  DEFAULT FALSE COMMENT '是否系统分组',
    -- 审计记录基础字段
    is_deleted  BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at  DATETIME COMMENT '逻辑删除时间',
    deleted_by  VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by  VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by  VARCHAR(64) COMMENT '最后更新人（账号/ID）'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='配置分组表';

-- 分组代码唯一索引
CREATE UNIQUE INDEX idx_config_group_code ON config_group (code) COMMENT '分组代码唯一索引';

-- ============================================================
-- 系统配置表 (config_item)
-- 存储系统各项配置参数
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS config_item;
CREATE TABLE config_item
(
    -- 基础字段
    id             VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    group_code     VARCHAR(32)  NOT NULL COMMENT '分组编码',
    name           VARCHAR(255) NOT NULL COMMENT '配置项名称',
    code           VARCHAR(255) NOT NULL COMMENT '配置项代码',
    value          VARCHAR(255) NOT NULL COMMENT '配置值',
    component_type VARCHAR(255) COMMENT '组件类型',
    description    VARCHAR(255) COMMENT '配置描述',
    sort           INTEGER  DEFAULT 0 COMMENT '排序',
    -- 审计记录基础字段
    is_deleted     BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at     DATETIME COMMENT '逻辑删除时间',
    deleted_by     VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by     VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by     VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_config_item_group_code (group_code) USING BTREE,
    INDEX idx_config_item_code (code) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='系统配置表';