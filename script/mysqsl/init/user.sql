-- ============================================================
-- 用户基本信息表 (users_info)
-- 存储用户的基本身份信息和展示信息
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS users_info;
CREATE TABLE users_info
(
    -- 基础字段
    id         VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    account_id VARCHAR(32)  NOT NULL COMMENT '账户ID',
    nickname   VARCHAR(128) NOT NULL COMMENT '昵称',
    avatar     VARCHAR(255) COMMENT '头像',
    gender     SMALLINT DEFAULT 0 COMMENT '性别：0-未知 1-男 2-女',
    birthday   DATE COMMENT '生日',
    signature  VARCHAR(500) COMMENT '个性签名',
    background VARCHAR(255) COMMENT '个人背景图片',
    interests  JSON COMMENT '兴趣标签', -- MySQL 5.7+用JSON，8.0+兼容JSONB
    website    VARCHAR(255) COMMENT '个人网站',
    github     VARCHAR(100) COMMENT 'GitHub',
    gitee      VARCHAR(100) COMMENT 'GitTee',
    blog       VARCHAR(255) COMMENT '博客',
    -- 审计记录基础字段
    is_deleted BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at DATETIME COMMENT '逻辑删除时间',
    deleted_by VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_users_info_account (account_id) USING BTREE,
    INDEX idx_users_info_nickname (nickname) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户基本信息表';

-- ============================================================
-- 用户档案详情表 (users_profile)
-- 存储用户的详细档案信息，包括教育职业和地理位置
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS users_profile;
CREATE TABLE users_profile
(
    -- 基础字段
    id            VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    account_id    VARCHAR(32) NOT NULL COMMENT '账户ID',
    -- 教育职业信息
    real_name     VARCHAR(64) COMMENT '真实姓名',
    school        VARCHAR(100) COMMENT '学校',
    major         VARCHAR(100) COMMENT '专业',
    student_id    VARCHAR(50) COMMENT '学号',
    company       VARCHAR(100) COMMENT '公司',
    job_title     VARCHAR(100) COMMENT '职位',
    industry      VARCHAR(100) COMMENT '行业',
    -- 地理位置
    country       VARCHAR(50) COMMENT '国家',
    province      VARCHAR(50) COMMENT '省份',
    city          VARCHAR(50) COMMENT '城市',
    location      VARCHAR(100) COMMENT '详细地址',
    -- 社交信息
    qq            VARCHAR(20) COMMENT 'QQ',
    wechat        VARCHAR(50) COMMENT '微信',
    -- 隐私设置
    show_birthday BOOLEAN  DEFAULT FALSE COMMENT '是否显示生日',
    show_location BOOLEAN  DEFAULT TRUE COMMENT '是否显示地理位置',
    -- 审计记录基础字段
    is_deleted    BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at    DATETIME COMMENT '逻辑删除时间',
    deleted_by    VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by    VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by    VARCHAR(64) COMMENT '最后更新人（账号/ID）'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户档案详情表';

-- 用户档案详情表索引
CREATE UNIQUE INDEX idx_users_profile_account ON users_profile (account_id) COMMENT '账户ID唯一索引';
CREATE INDEX idx_users_profile_location ON users_profile (country, province, city) COMMENT '地理位置联合索引';

-- ============================================================
-- 用户偏好设置表 (users_preference)
-- 存储用户的个性化偏好和系统设置
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS users_preference;
CREATE TABLE users_preference
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    account_id           VARCHAR(32) NOT NULL COMMENT '账户ID',
    -- 界面设置
    theme                VARCHAR(50) DEFAULT 'light' COMMENT '主题',
    language             VARCHAR(10) DEFAULT 'zh-CN' COMMENT '系统语言',
    -- 通知设置
    email_notifications  BOOLEAN     DEFAULT TRUE COMMENT '邮件通知',
    push_notifications   BOOLEAN     DEFAULT TRUE COMMENT '推送通知',
    -- 隐私与展示
    allow_direct_message BOOLEAN     DEFAULT TRUE COMMENT '允许私信',

    -- 审计记录基础字段
    is_deleted           BOOLEAN     DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at           DATETIME COMMENT '逻辑删除时间',
    deleted_by           VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at           DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by           VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at           DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by           VARCHAR(64) COMMENT '最后更新人（账号/ID）'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户偏好设置表';

-- 用户偏好设置表索引
CREATE UNIQUE INDEX idx_users_preference_account ON users_preference (account_id) COMMENT '账户ID唯一索引';

-- ============================================================
-- 用户统计信息表 (users_stats)
-- 存储用户的等级、经验和活跃度等统计信息
-- MySQL 8.0+ / 5.7+ 兼容版本
-- ============================================================
DROP TABLE IF EXISTS users_stats;
CREATE TABLE users_stats
(
    -- 基础字段
    id                    VARCHAR(32) PRIMARY KEY COMMENT '主键ID',
    -- 业务字段
    account_id            VARCHAR(32) NOT NULL COMMENT '账户ID',
    -- 等级与经验
    level                 INTEGER  DEFAULT 1 COMMENT '等级',
    exp                   BIGINT   DEFAULT 0 COMMENT '经验值',
    total_exp             BIGINT   DEFAULT 0 COMMENT '累计经验值',
    -- 活跃统计
    login_days            INTEGER  DEFAULT 0 COMMENT '登录天数',
    continuous_login_days INTEGER  DEFAULT 0 COMMENT '连续登录天数',
    -- 内容统计
    post_count            BIGINT   DEFAULT 0 COMMENT '发帖数',
    comment_count         BIGINT   DEFAULT 0 COMMENT '评论数',
    like_count            BIGINT   DEFAULT 0 COMMENT '获赞数',
    follow_count          BIGINT   DEFAULT 0 COMMENT '关注数',
    fans_count            BIGINT   DEFAULT 0 COMMENT '粉丝数',

    -- 审计记录基础字段
    is_deleted            BOOLEAN  DEFAULT FALSE COMMENT '逻辑删除标识：0-未删除 1-已删除',
    deleted_at            DATETIME COMMENT '逻辑删除时间',
    deleted_by            VARCHAR(64) COMMENT '删除人（账号/ID）',
    created_at            DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    created_by            VARCHAR(64) COMMENT '创建人（账号/ID）',
    updated_at            DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    updated_by            VARCHAR(64) COMMENT '最后更新人（账号/ID）',
    -- 索引创建
    INDEX idx_users_stats_level (level) USING BTREE,
    INDEX idx_users_stats_exp (exp) USING BTREE,
    INDEX idx_users_stats_total_exp (total_exp) USING BTREE,
    INDEX idx_users_stats_login_days (login_days) USING BTREE,
    INDEX idx_users_stats_continuous_login_days (continuous_login_days) USING BTREE,
    INDEX idx_users_stats_post_count (post_count) USING BTREE,
    INDEX idx_users_stats_comment_count (comment_count) USING BTREE,
    INDEX idx_users_stats_like_count (like_count) USING BTREE,
    INDEX idx_users_stats_follow_count (follow_count) USING BTREE,
    INDEX idx_users_stats_fans_count (fans_count) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户统计信息表';