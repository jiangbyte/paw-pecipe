-- ============================================================
-- 用户基本信息表 (user_info)
-- 存储用户的基本身份信息和展示信息
-- ============================================================
DROP TABLE IF EXISTS user_info;
CREATE TABLE user_info
(
    -- 基础字段
    id          VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    account_id  VARCHAR(32)  NOT NULL,   -- 账户ID
    nickname    VARCHAR(128) NOT NULL,   -- 昵称
    avatar      VARCHAR(255),            -- 头像
    gender      SMALLINT  DEFAULT 0,     -- 性别 0: 未知 1: 男 2: 女
    birthday    DATE,                    -- 生日
    signature   VARCHAR(500),            -- 个性签名
    background  VARCHAR(255),            -- 个人背景图片
    interests   JSONB,                   -- 兴趣标签
    website     VARCHAR(255),            -- 个人网站
    github      VARCHAR(100),            -- GitHub
    gitee       VARCHAR(100),            -- GitTee
    blog        VARCHAR(255)             -- 博客
    -- 基础字段
    ,
    is_deleted  BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at  TIMESTAMP,               -- 软删除时间
    delete_user VARCHAR(32),             -- 删除操作人
    created_at  TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user VARCHAR(32),             -- 创建人
    updated_at  TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user VARCHAR(32)              -- 更新人
);

COMMENT ON TABLE user_info IS '用户基本信息表';
COMMENT ON COLUMN user_info.id IS '主键ID';
COMMENT ON COLUMN user_info.deleted_at IS '软删除时间';
COMMENT ON COLUMN user_info.delete_user IS '删除操作人';
COMMENT ON COLUMN user_info.created_at IS '创建时间';
COMMENT ON COLUMN user_info.create_user IS '创建人';
COMMENT ON COLUMN user_info.updated_at IS '更新时间';
COMMENT ON COLUMN user_info.update_user IS '更新人';
COMMENT ON COLUMN user_info.account_id IS '账户ID';
COMMENT ON COLUMN user_info.nickname IS '昵称';
COMMENT ON COLUMN user_info.avatar IS '头像';
COMMENT ON COLUMN user_info.gender IS '性别：0-未知 1-男 2-女';
COMMENT ON COLUMN user_info.birthday IS '生日';
COMMENT ON COLUMN user_info.signature IS '个性签名';
COMMENT ON COLUMN user_info.background IS '个人背景图片';
COMMENT ON COLUMN user_info.interests IS '兴趣标签';
COMMENT ON COLUMN user_info.website IS '个人网站';
COMMENT ON COLUMN user_info.github IS 'GitHub';
COMMENT ON COLUMN user_info.gitee IS 'GitTee';
COMMENT ON COLUMN user_info.blog IS '博客';

-- 创建用户基本信息表索引
CREATE UNIQUE INDEX idx_user_info_account ON user_info (account_id); -- 账户ID唯一索引
CREATE INDEX idx_user_nickname ON user_info (nickname);
-- 昵称索引

-- ============================================================
-- 用户档案详情表 (user_profile)
-- 存储用户的详细档案信息，包括教育职业和地理位置
-- ============================================================
DROP TABLE IF EXISTS user_profile;
CREATE TABLE user_profile
(
    -- 基础字段
    id            VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    account_id    VARCHAR(32) NOT NULL,    -- 账户ID
    -- 教育职业信息
    real_name     VARCHAR(64),             -- 真实姓名
    school        VARCHAR(100),            -- 学校
    major         VARCHAR(100),            -- 专业
    student_id    VARCHAR(50),             -- 学号
    company       VARCHAR(100),            -- 公司
    job_title     VARCHAR(100),            -- 职位
    industry      VARCHAR(100),            -- 行业
    -- 地理位置
    country       VARCHAR(50),             -- 国家
    province      VARCHAR(50),             -- 省份
    city          VARCHAR(50),             -- 城市
    location      VARCHAR(100),            -- 详细地址
    -- 社交信息
    qq            VARCHAR(20),             -- QQ
    wechat        VARCHAR(50),             -- 微信
    -- 隐私设置
    show_birthday BOOLEAN   DEFAULT FALSE, -- 是否显示生日
    show_location BOOLEAN   DEFAULT TRUE   -- 是否显示地理位置
    -- 基础字段
    ,
    is_deleted    BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at    TIMESTAMP,               -- 软删除时间
    delete_user   VARCHAR(32),             -- 删除操作人
    created_at    TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user   VARCHAR(32),             -- 创建人
    updated_at    TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user   VARCHAR(32)              -- 更新人
);

COMMENT ON TABLE user_profile IS '用户档案详情表';
COMMENT ON COLUMN user_profile.id IS '主键ID';
COMMENT ON COLUMN user_profile.deleted_at IS '软删除时间';
COMMENT ON COLUMN user_profile.delete_user IS '删除操作人';
COMMENT ON COLUMN user_profile.created_at IS '创建时间';
COMMENT ON COLUMN user_profile.create_user IS '创建人';
COMMENT ON COLUMN user_profile.updated_at IS '更新时间';
COMMENT ON COLUMN user_profile.update_user IS '更新人';
COMMENT ON COLUMN user_profile.account_id IS '账户ID';
COMMENT ON COLUMN user_profile.real_name IS '真实姓名';
COMMENT ON COLUMN user_profile.school IS '学校';
COMMENT ON COLUMN user_profile.major IS '专业';
COMMENT ON COLUMN user_profile.student_id IS '学号';
COMMENT ON COLUMN user_profile.company IS '公司';
COMMENT ON COLUMN user_profile.job_title IS '职位';
COMMENT ON COLUMN user_profile.industry IS '行业';
COMMENT ON COLUMN user_profile.country IS '国家';
COMMENT ON COLUMN user_profile.province IS '省份';
COMMENT ON COLUMN user_profile.city IS '城市';
COMMENT ON COLUMN user_profile.location IS '详细地址';
COMMENT ON COLUMN user_profile.qq IS 'QQ';
COMMENT ON COLUMN user_profile.wechat IS '微信';
COMMENT ON COLUMN user_profile.show_birthday IS '是否显示生日';
COMMENT ON COLUMN user_profile.show_location IS '是否显示地理位置';

-- 创建用户档案详情表索引
CREATE UNIQUE INDEX idx_user_profile_account ON user_profile (account_id);
-- 账户ID唯一索引

-- ============================================================
-- 用户偏好设置表 (user_preference)
-- 存储用户的个性化偏好和系统设置
-- ============================================================
DROP TABLE IF EXISTS user_preference;
CREATE TABLE user_preference
(
    -- 基础字段
    id                   VARCHAR(32) PRIMARY KEY,     -- 主键ID
    -- 业务字段
    account_id           VARCHAR(32) NOT NULL,        -- 账户ID
    -- 界面设置
    theme                VARCHAR(50) DEFAULT 'light', -- 主题
    language             VARCHAR(10) DEFAULT 'zh-CN', -- 系统语言
    -- 通知设置
    email_notifications  BOOLEAN     DEFAULT TRUE,    -- 邮件通知
    push_notifications   BOOLEAN     DEFAULT TRUE,    -- 推送通知
    -- 隐私与展示
    allow_direct_message BOOLEAN     DEFAULT TRUE     -- 允许私信
    -- 基础字段
    ,
    is_deleted           BOOLEAN     DEFAULT FALSE,   -- 是否删除
    deleted_at           TIMESTAMP,                   -- 软删除时间
    delete_user          VARCHAR(32),                 -- 删除操作人
    created_at           TIMESTAMP   DEFAULT NOW(),   -- 创建时间
    create_user          VARCHAR(32),                 -- 创建人
    updated_at           TIMESTAMP   DEFAULT NOW(),   -- 更新时间
    update_user          VARCHAR(32)                  -- 更新人
);

COMMENT ON TABLE user_preference IS '用户偏好设置表';
COMMENT ON COLUMN user_preference.id IS '主键ID';
COMMENT ON COLUMN user_preference.deleted_at IS '软删除时间';
COMMENT ON COLUMN user_preference.delete_user IS '删除操作人';
COMMENT ON COLUMN user_preference.created_at IS '创建时间';
COMMENT ON COLUMN user_preference.create_user IS '创建人';
COMMENT ON COLUMN user_preference.updated_at IS '更新时间';
COMMENT ON COLUMN user_preference.update_user IS '更新人';
COMMENT ON COLUMN user_preference.account_id IS '账户ID';
COMMENT ON COLUMN user_preference.theme IS '主题';
COMMENT ON COLUMN user_preference.language IS '系统语言';
COMMENT ON COLUMN user_preference.email_notifications IS '邮件通知';
COMMENT ON COLUMN user_preference.push_notifications IS '推送通知';
COMMENT ON COLUMN user_preference.allow_direct_message IS '允许私信';

-- 创建用户偏好设置表索引
CREATE UNIQUE INDEX idx_user_preference_account ON user_preference (account_id);
-- 账户ID唯一索引

-- ============================================================
-- 用户统计信息表 (user_stats)
-- 存储用户的等级、经验和活跃度等统计信息
-- ============================================================
DROP TABLE IF EXISTS user_stats;
CREATE TABLE user_stats
(
    -- 基础字段
    id                    VARCHAR(32) PRIMARY KEY, -- 主键ID
    -- 业务字段
    account_id            VARCHAR(32) NOT NULL,    -- 账户ID
    -- 等级与经验
    level                 INTEGER   DEFAULT 1,     -- 等级
    exp                   BIGINT    DEFAULT 0,     -- 经验值
    total_exp             BIGINT    DEFAULT 0,     -- 累计经验值
    -- 活跃统计
    login_days            INTEGER   DEFAULT 0,     -- 登录天数
    continuous_login_days INTEGER   DEFAULT 0,     -- 连续登录天数
    -- 内容统计
    post_count            BIGINT    DEFAULT 0,     -- 发帖数
    comment_count         BIGINT    DEFAULT 0,     -- 评论数
    like_count            BIGINT    DEFAULT 0,     -- 获赞数
    follow_count          BIGINT    DEFAULT 0,     -- 关注数
    fans_count            BIGINT    DEFAULT 0      -- 粉丝数
    -- 基础字段
    ,
    is_deleted            BOOLEAN   DEFAULT FALSE, -- 是否删除
    deleted_at            TIMESTAMP,               -- 软删除时间
    delete_user           VARCHAR(32),             -- 删除操作人
    created_at            TIMESTAMP DEFAULT NOW(), -- 创建时间
    create_user           VARCHAR(32),             -- 创建人
    updated_at            TIMESTAMP DEFAULT NOW(), -- 更新时间
    update_user           VARCHAR(32)              -- 更新人
);

COMMENT ON TABLE user_stats IS '用户统计信息表';
COMMENT ON COLUMN user_stats.id IS '主键ID';
COMMENT ON COLUMN user_stats.deleted_at IS '软删除时间';
COMMENT ON COLUMN user_stats.delete_user IS '删除操作人';
COMMENT ON COLUMN user_stats.created_at IS '创建时间';
COMMENT ON COLUMN user_stats.create_user IS '创建人';
COMMENT ON COLUMN user_stats.updated_at IS '更新时间';
COMMENT ON COLUMN user_stats.update_user IS '更新人';
COMMENT ON COLUMN user_stats.account_id IS '账户ID';
COMMENT ON COLUMN user_stats.level IS '等级';
COMMENT ON COLUMN user_stats.exp IS '经验值';
COMMENT ON COLUMN user_stats.total_exp IS '累计经验值';
COMMENT ON COLUMN user_stats.login_days IS '登录天数';
COMMENT ON COLUMN user_stats.continuous_login_days IS '连续登录天数';
COMMENT ON COLUMN user_stats.post_count IS '发帖数';
COMMENT ON COLUMN user_stats.comment_count IS '评论数';
COMMENT ON COLUMN user_stats.like_count IS '获赞数';
COMMENT ON COLUMN user_stats.follow_count IS '关注数';
COMMENT ON COLUMN user_stats.fans_count IS '粉丝数';

-- 创建用户统计信息表索引
CREATE UNIQUE INDEX idx_user_stats_account ON user_stats (account_id); -- 账户ID唯一索引
CREATE INDEX idx_user_level ON user_stats (level); -- 等级索引