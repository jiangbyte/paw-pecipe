-- ----------------------------
-- Table structure for recipe
-- ----------------------------
DROP TABLE IF EXISTS `biz_recipe`;
CREATE TABLE `biz_recipe`
(
    `id`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '主键ID',
    `recognition_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '识别ID',
    `title`          VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜谱标题',
    `cover`          VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '封面图URL',
    `author`         VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '作者名',
    `avatar`         VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '作者头像URL',
    `duration`       INT UNSIGNED                                                  NULL     DEFAULT NULL COMMENT '制作时长', -- （如"15"），单位分钟
    `difficulty`     VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '难度等级', -- 简单、中等、困难
    `likes`          INT UNSIGNED                                                  NOT NULL DEFAULT 0 COMMENT '点赞数',
    `collects`       INT UNSIGNED                                                  NOT NULL DEFAULT 0 COMMENT '收藏数',
    `views`          INT UNSIGNED                                                  NOT NULL DEFAULT 0 COMMENT '浏览量',
    `share`          INT UNSIGNED                                                  NOT NULL DEFAULT 0 COMMENT '分享量',
    `category_id`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '分类ID',
    `description`    TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '描述',
    `ingredients`    JSON                                                          NULL COMMENT '配料列表',
    `steps`          JSON                                                          NULL COMMENT '步骤列表',
    `tips`           JSON                                                          NULL COMMENT '小贴士列表',
    `is_public`      tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '是否公开',
    `is_deleted`     tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at`     datetime                                                      NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '删除人',
    `created_at`     datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at`     datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '菜谱表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for recipe
-- ----------------------------
DROP TABLE IF EXISTS `biz_recipe_category`;
CREATE TABLE `biz_recipe_category`
(
    `id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '主键ID',
    `name`       VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜谱分类',
    `is_deleted` tinyint(1)                                                    NULL DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at` datetime                                                      NULL DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL DEFAULT NULL COMMENT '删除人',
    `created_at` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL DEFAULT NULL COMMENT '创建人',
    `updated_at` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '菜谱分类表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for recipe
-- ----------------------------
DROP TABLE IF EXISTS `biz_product_category`;
CREATE TABLE `biz_product_category`
(
    `id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '主键ID',
    `name`       VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜谱分类',
    `is_deleted` tinyint(1)                                                    NULL DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at` datetime                                                      NULL DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL DEFAULT NULL COMMENT '删除人',
    `created_at` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL DEFAULT NULL COMMENT '创建人',
    `updated_at` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `biz_product`;
CREATE TABLE `biz_product`
(
    `id`          VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '商品ID',
    `title`       VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品标题',
    `cover`       VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '封面图URL',
    `images`      JSON                                                          NULL COMMENT '商品轮播图URL列表',
    `brand`       VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '品牌',
    `category_id` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '分类ID',
    `origin`      VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '产地',
    `description` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '商品描述',
    `details`     JSON                                                          NULL COMMENT '商品详情列表',           -- （规格、保质期等）
    `service`     JSON                                                          NULL COMMENT '服务保障列表',
    `status`      TINYINT(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否上架', -- （1=上架，0=下架）

    -- 通用字段
    `is_deleted`  TINYINT(1)                                                    NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at`  DATETIME                                                      NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by`  VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '删除人',
    `created_at`  DATETIME                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`  VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at`  DATETIME                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by`  VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '最后更新人',

    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '商品主表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `biz_product_sku`;
CREATE TABLE `biz_product_sku`
(
    `id`             VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SKU ID',
    `product_id`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联商品ID',

    `original_price` DECIMAL(10, 2)                                               NOT NULL COMMENT '原价',
    `discount_price` DECIMAL(10, 2)                                               NOT NULL COMMENT '折扣价',
    `discount`       VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '折扣描述',  -- 如"5折"
    `limit_per_user` INT UNSIGNED                                                 NOT NULL DEFAULT 0 COMMENT '每人限购数量', -- 0表示不限购
    `tags`           JSON                                                         NULL     DEFAULT NULL COMMENT '促销标签',  -- 如"限时特惠"
    `total_stock`    INT UNSIGNED                                                 NOT NULL DEFAULT 0 COMMENT '现有库存',
    `status`         TINYINT(1)                                                   NOT NULL DEFAULT 1 COMMENT '是否上架',     -- （1=上架，0=下架）

    -- 通用字段
    `is_deleted`     TINYINT(1)                                                   NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at`     DATETIME                                                     NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '删除人',
    `created_at`     DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at`     DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '商品日常销售SKU表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `biz_flash_sale`;
CREATE TABLE `biz_flash_sale`
(
    `id`             VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '抢购活动ID',
    `product_id`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联商品ID',

    `original_price` DECIMAL(10, 2)                                               NOT NULL COMMENT '原价',
    `discount_price` DECIMAL(10, 2)                                               NOT NULL COMMENT '抢购价',
    `discount`       VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '折扣描述',  -- 如"5折"
    `limit_per_user` INT UNSIGNED                                                 NOT NULL DEFAULT 1 COMMENT '每人限购数量', -- 0表示不限购
    `tags`           JSON                                                         NULL     DEFAULT NULL COMMENT '促销标签',  -- 如"限时特惠"
    `total_stock`    INT UNSIGNED                                                 NOT NULL DEFAULT 0 COMMENT '现有库存',
    `status`         TINYINT(1)                                                   NOT NULL DEFAULT 1 COMMENT '是否上架',     -- （1=上架，0=下架）

    `start_time`     DATETIME                                                     NOT NULL COMMENT '抢购开始时间',
    `end_time`       DATETIME                                                     NOT NULL COMMENT '抢购结束时间',

    -- 通用字段
    `is_deleted`     TINYINT(1)                                                   NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at`     DATETIME                                                     NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '删除人',
    `created_at`     DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at`     DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '限时抢购活动表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `biz_flash_sale_record`;
CREATE TABLE `biz_flash_sale_record`
(
    `id`         VARCHAR(64)                                                  NOT NULL,
    `flash_id`   VARCHAR(64)                                                  NOT NULL COMMENT '秒杀活动ID',
    `user_id`    VARCHAR(64)                                                  NOT NULL COMMENT '用户ID',
    `product_id` VARCHAR(64)                                                  NOT NULL,
    `quantity`   INT UNSIGNED                                                 NOT NULL DEFAULT 1,
    `status`     TINYINT(1)                                                   NOT NULL DEFAULT 0 COMMENT '0=已抢到未下单, 1=已下单, 2=已支付, 3=已失效',
    `order_id`   VARCHAR(64)                                                  NULL COMMENT '关联订单ID（下单后填充）',
    `expired_at` DATETIME                                                     NOT NULL COMMENT '资格过期时间（如5分钟内必须下单）',

    -- 通用字段
    `is_deleted` TINYINT(1)                                                   NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at` DATETIME                                                     NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '删除人',
    `created_at` DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at` DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '秒杀资格记录表'
  ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS `biz_product_specs`;
CREATE TABLE `biz_product_specs`
(
    `id`         VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '抢购活动ID',
    `product_id` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联商品ID',
    `name`       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格名称',
    `price`      DECIMAL(10, 2)                                               NOT NULL COMMENT '规格价格',
    `stock`      INT UNSIGNED                                                 NOT NULL DEFAULT 0 COMMENT '规格库存',
    `status`     TINYINT(1)                                                   NOT NULL DEFAULT 1 COMMENT '是否上架',

    -- 通用字段
    `is_deleted` TINYINT(1)                                                   NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at` DATETIME                                                     NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '删除人',
    `created_at` DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at` DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '规格表'
  ROW_FORMAT = Dynamic;



# =============================================================
-- ----------------------------
-- Table structure for biz_cart
-- ----------------------------
DROP TABLE IF EXISTS `biz_cart`;
CREATE TABLE `biz_cart`
(
    `id`         VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
    `user_id`    VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
    `product_id` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品ID',
    `spec_id`    VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品规格ID',
    `sku_id`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT 'SKU ID（可为空，用于普通商品）',
    `flash_id`   VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '限时抢购活动ID（若来自抢购）',
    `quantity`   INT UNSIGNED                                                 NOT NULL DEFAULT 1 COMMENT '数量',
    `selected`   TINYINT(1)                                                   NOT NULL DEFAULT 1 COMMENT '是否选中（用于结算）',

    -- 通用字段
    `is_deleted` TINYINT(1)                                                   NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at` DATETIME                                                     NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '删除人',
    `created_at` DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at` DATETIME                                                     NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '购物车表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_order
-- ----------------------------
DROP TABLE IF EXISTS `biz_order`;
CREATE TABLE `biz_order`
(
    `id`               VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '订单ID',
    `order_no`         VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '订单编号（唯一，如 ORD20251203123456）',
    `user_id`          VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户ID',

    `total_amount`     DECIMAL(12, 2)                                                NOT NULL COMMENT '订单总金额（含运费）',
    `actual_amount`    DECIMAL(12, 2)                                                NOT NULL COMMENT '实付金额（优惠后）',
    `discount_amount`  DECIMAL(12, 2)                                                NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
    `freight_amount`   DECIMAL(10, 2)                                                NOT NULL DEFAULT 0.00 COMMENT '运费',

    `status`           TINYINT(2)                                                    NOT NULL DEFAULT 0 COMMENT '订单状态：0=待支付，1=已支付，2=已发货，3=已完成，4=已取消，5=已退款',
    `pay_type`         VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '支付方式：wechat, alipay, balance 等',
    `pay_time`         DATETIME                                                      NULL     DEFAULT NULL COMMENT '支付时间',

    -- 收货信息（可冗余，避免用户地址变更影响历史订单）
    `receiver_name`    VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '收货人姓名',
    `receiver_phone`   VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '收货人电话',
    `receiver_address` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细地址',
    `province`         VARCHAR(50)                                                   NULL     DEFAULT NULL COMMENT '省',
    `city`             VARCHAR(50)                                                   NULL     DEFAULT NULL COMMENT '市',
    `district`         VARCHAR(50)                                                   NULL     DEFAULT NULL COMMENT '区',

    `remark`           VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户备注',
    `timeout_minutes`  INT UNSIGNED                                                  NOT NULL DEFAULT 30 COMMENT '支付超时分钟数',
    `expired_at`       DATETIME                                                      NOT NULL COMMENT '订单过期时间（用于自动取消）',

    -- 通用字段
    `is_deleted`       TINYINT(1)                                                    NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at`       DATETIME                                                      NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by`       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '删除人',
    `created_at`       DATETIME                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at`       DATETIME                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by`       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '订单主表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for biz_order_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_order_item`;
CREATE TABLE `biz_order_item`
(
    `id`             VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '主键ID',
    `order_id`       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '订单ID',
    `product_id`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '商品ID',
    `sku_id`         VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT 'SKU ID',
    `flash_id`       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '限时抢购ID',

    `product_title`  VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品标题（快照）',
    `cover`          VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '商品封面（快照）',
    `specs`          JSON                                                          NULL     DEFAULT NULL COMMENT '规格快照（如颜色、尺寸）',

    `original_price` DECIMAL(10, 2)                                                NOT NULL COMMENT '下单时原价',
    `discount_price` DECIMAL(10, 2)                                                NOT NULL COMMENT '下单时实际单价',
    `quantity`       INT UNSIGNED                                                  NOT NULL DEFAULT 1 COMMENT '购买数量',
    `total_amount`   DECIMAL(12, 2)                                                NOT NULL COMMENT '小计金额 = 单价 × 数量',

    -- 通用字段
    `is_deleted`     TINYINT(1)                                                    NULL     DEFAULT 0 COMMENT '逻辑删除标识',
    `deleted_at`     DATETIME                                                      NULL     DEFAULT NULL COMMENT '逻辑删除时间',
    `deleted_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '删除人',
    `created_at`     DATETIME                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '创建人',
    `updated_at`     DATETIME                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `updated_by`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '最后更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT = '订单明细表'
  ROW_FORMAT = Dynamic;

