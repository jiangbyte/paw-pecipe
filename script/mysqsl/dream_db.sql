/*
 Navicat Premium Dump SQL

 Source Server         : 101.201.174.43
 Source Server Type    : MySQL
 Source Server Version : 90500 (9.5.0)
 Source Host           : 101.201.174.43:3306
 Source Schema         : dream_db

 Target Server Type    : MySQL
 Target Server Version : 90500 (9.5.0)
 File Encoding         : 65001

 Date: 03/12/2025 10:31:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auths_account
-- ----------------------------
DROP TABLE IF EXISTS `auths_account`;
CREATE TABLE `auths_account`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名，登录标识',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '加密后的密码',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` smallint NULL DEFAULT 0 COMMENT '账户状态：0-正常, 1-锁定, 2-禁用',
  `password_strength` smallint NULL DEFAULT 0 COMMENT '密码强度等级：0-3',
  `last_password_change` timestamp NULL DEFAULT NULL COMMENT '最后修改密码的时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后登录IP地址',
  `login_count` int NULL DEFAULT 0 COMMENT '登录次数统计',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_username`(`username` ASC) USING BTREE,
  INDEX `idx_account_email`(`email` ASC) USING BTREE,
  INDEX `idx_account_telephone`(`telephone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '核心账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auths_account
-- ----------------------------
INSERT INTO `auths_account` VALUES ('1', 'superadmin', '$2a$10$GO4/.3q0l/d/HWYaGKM2d.C4H.ZJmF4RhKVpFlC3sLuzFddlyucmK', 'jiangbyte@163.com', NULL, 0, 1, '2025-11-26 14:08:50', '2025-11-26 17:17:55', '127.0.0.1', 24, 0, NULL, NULL, '2025-11-25 09:22:21', NULL, '2025-11-26 17:17:55', NULL);
INSERT INTO `auths_account` VALUES ('1993255249205997569', 'menus', '$2a$10$wvXNOmOZ8fW1ipstwjgu4OC2IM1/cJLNV0rTHnENtveDOEUcr26Cm', '3317229064@qq.com', NULL, 0, 0, NULL, '2025-11-25 17:47:54', NULL, 0, 0, NULL, NULL, '2025-11-25 17:47:39', NULL, '2025-11-25 17:47:54', NULL);
INSERT INTO `auths_account` VALUES ('2', 'admin', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'admin@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:23', NULL, '2025-11-25 09:22:23', NULL);
INSERT INTO `auths_account` VALUES ('3', 'deptadmin', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'dept_admin@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:27', NULL, '2025-11-25 09:22:27', NULL);
INSERT INTO `auths_account` VALUES ('4', 'zhangsan', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'zhangsan@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:32', NULL, '2025-11-25 09:22:32', NULL);
INSERT INTO `auths_account` VALUES ('5', 'lisi', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'lisi@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:36', NULL, '2025-11-25 09:22:36', NULL);
INSERT INTO `auths_account` VALUES ('6', 'wangwu', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'wangwu@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:41', NULL, '2025-11-25 09:22:41', NULL);
INSERT INTO `auths_account` VALUES ('7', 'zhaoliu', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'zhaoliu@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:45', NULL, '2025-11-25 09:22:45', NULL);
INSERT INTO `auths_account` VALUES ('8', 'sunqi', '$2a$10$eE2quG/tVgyPBeHfA7EQyOYBd36hpxBEVK6wh/HdAV9Nd9Wh.MjGi', 'sunqi@company.com', NULL, 0, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, '2025-11-25 09:22:49', NULL, '2025-11-25 09:22:49', NULL);

-- ----------------------------
-- Table structure for auths_account_group
-- ----------------------------
DROP TABLE IF EXISTS `auths_account_group`;
CREATE TABLE `auths_account_group`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `account_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户ID',
  `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_role_account`(`account_id` ASC) USING BTREE,
  INDEX `idx_account_role_group`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '账户用户组关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auths_account_group
-- ----------------------------

-- ----------------------------
-- Table structure for auths_account_role
-- ----------------------------
DROP TABLE IF EXISTS `auths_account_role`;
CREATE TABLE `auths_account_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `account_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_account_role_account`(`account_id` ASC) USING BTREE,
  INDEX `idx_account_role_role`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '账户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auths_account_role
-- ----------------------------
INSERT INTO `auths_account_role` VALUES ('1', '1', '1');
INSERT INTO `auths_account_role` VALUES ('2', '2', '2');
INSERT INTO `auths_account_role` VALUES ('3', '3', '3');
INSERT INTO `auths_account_role` VALUES ('4', '4', '4');
INSERT INTO `auths_account_role` VALUES ('5', '5', '4');
INSERT INTO `auths_account_role` VALUES ('6', '6', '4');
INSERT INTO `auths_account_role` VALUES ('7', '7', '4');
INSERT INTO `auths_account_role` VALUES ('8', '8', '4');

-- ----------------------------
-- Table structure for auths_group
-- ----------------------------
DROP TABLE IF EXISTS `auths_group`;
CREATE TABLE `auths_group`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '父级组ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户组名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户组编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户组描述',
  `sort` smallint NULL DEFAULT 99 COMMENT '排序号，数字越小越靠前',
  `admin_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员ID',
  `max_user_count` int NULL DEFAULT NULL COMMENT '最大用户数量限制',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auths_group
-- ----------------------------
INSERT INTO `auths_group` VALUES ('1993559342860390401', NULL, '默认用户组', 'DEFAULT', '默认用户组', 0, NULL, NULL, 0, NULL, NULL, '2025-11-26 13:56:01', NULL, '2025-11-26 13:56:01', NULL);

-- ----------------------------
-- Table structure for auths_role
-- ----------------------------
DROP TABLE IF EXISTS `auths_role`;
CREATE TABLE `auths_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色编码',
  `data_scope` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据权限范围',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色描述',
  `assign_group_ids` json NULL COMMENT '分配的用户组ID列表(JSON数组)',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auths_role
-- ----------------------------
INSERT INTO `auths_role` VALUES ('1', '超级管理员', 'SUPER_ADMIN', 'ALL', '系统超级管理员，拥有所有权限', NULL, 0, NULL, NULL, '2025-11-25 09:22:19', NULL, '2025-11-25 09:22:19', NULL);
INSERT INTO `auths_role` VALUES ('2', '系统管理员', 'ADMIN', 'GROUP_AND_CHILD', '系统管理员，拥有大部分管理权限', NULL, 0, NULL, NULL, '2025-11-25 09:22:19', NULL, '2025-11-25 09:22:19', NULL);
INSERT INTO `auths_role` VALUES ('3', '部门管理员', 'GROUP_ADMIN', 'GROUP', '部门管理员，管理本部门用户和权限', NULL, 0, NULL, NULL, '2025-11-25 09:22:19', NULL, '2025-11-25 09:22:19', NULL);
INSERT INTO `auths_role` VALUES ('4', '普通用户', 'USER', 'SELF', '普通用户，只能查看和操作自己的数据', NULL, 0, NULL, NULL, '2025-11-25 09:22:19', NULL, '2025-11-25 09:22:19', NULL);

-- ----------------------------
-- Table structure for auths_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `auths_role_menu`;
CREATE TABLE `auths_role_menu`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_menu_role`(`role_id` ASC) USING BTREE,
  INDEX `idx_role_menu_menu`(`menu_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auths_role_menu
-- ----------------------------
INSERT INTO `auths_role_menu` VALUES ('1', '1', '1');
INSERT INTO `auths_role_menu` VALUES ('10', '1', '10');
INSERT INTO `auths_role_menu` VALUES ('11', '1', '11');
INSERT INTO `auths_role_menu` VALUES ('12', '1', '12');
INSERT INTO `auths_role_menu` VALUES ('13', '1', '13');
INSERT INTO `auths_role_menu` VALUES ('14', '1', '14');
INSERT INTO `auths_role_menu` VALUES ('15', '1', '15');
INSERT INTO `auths_role_menu` VALUES ('16', '1', '16');
INSERT INTO `auths_role_menu` VALUES ('17', '1', '17');
INSERT INTO `auths_role_menu` VALUES ('18', '1', '18');
INSERT INTO `auths_role_menu` VALUES ('19', '1', '19');
INSERT INTO `auths_role_menu` VALUES ('2', '1', '2');
INSERT INTO `auths_role_menu` VALUES ('20', '1', '20');
INSERT INTO `auths_role_menu` VALUES ('21', '1', '21');
INSERT INTO `auths_role_menu` VALUES ('22', '1', '22');
INSERT INTO `auths_role_menu` VALUES ('23', '1', '23');
INSERT INTO `auths_role_menu` VALUES ('24', '1', '24');
INSERT INTO `auths_role_menu` VALUES ('25', '1', '25');
INSERT INTO `auths_role_menu` VALUES ('26', '2', '1');
INSERT INTO `auths_role_menu` VALUES ('27', '2', '2');
INSERT INTO `auths_role_menu` VALUES ('28', '2', '3');
INSERT INTO `auths_role_menu` VALUES ('29', '2', '4');
INSERT INTO `auths_role_menu` VALUES ('3', '1', '3');
INSERT INTO `auths_role_menu` VALUES ('30', '2', '5');
INSERT INTO `auths_role_menu` VALUES ('31', '2', '6');
INSERT INTO `auths_role_menu` VALUES ('32', '2', '7');
INSERT INTO `auths_role_menu` VALUES ('33', '2', '8');
INSERT INTO `auths_role_menu` VALUES ('34', '2', '9');
INSERT INTO `auths_role_menu` VALUES ('35', '2', '10');
INSERT INTO `auths_role_menu` VALUES ('36', '2', '11');
INSERT INTO `auths_role_menu` VALUES ('37', '2', '12');
INSERT INTO `auths_role_menu` VALUES ('38', '2', '13');
INSERT INTO `auths_role_menu` VALUES ('39', '2', '14');
INSERT INTO `auths_role_menu` VALUES ('4', '1', '4');
INSERT INTO `auths_role_menu` VALUES ('40', '2', '15');
INSERT INTO `auths_role_menu` VALUES ('41', '2', '16');
INSERT INTO `auths_role_menu` VALUES ('42', '2', '17');
INSERT INTO `auths_role_menu` VALUES ('43', '2', '18');
INSERT INTO `auths_role_menu` VALUES ('44', '3', '1');
INSERT INTO `auths_role_menu` VALUES ('45', '3', '14');
INSERT INTO `auths_role_menu` VALUES ('46', '3', '15');
INSERT INTO `auths_role_menu` VALUES ('47', '3', '16');
INSERT INTO `auths_role_menu` VALUES ('48', '3', '17');
INSERT INTO `auths_role_menu` VALUES ('49', '3', '18');
INSERT INTO `auths_role_menu` VALUES ('5', '1', '5');
INSERT INTO `auths_role_menu` VALUES ('50', '3', '9');
INSERT INTO `auths_role_menu` VALUES ('51', '3', '10');
INSERT INTO `auths_role_menu` VALUES ('52', '3', '6');
INSERT INTO `auths_role_menu` VALUES ('53', '3', '7');
INSERT INTO `auths_role_menu` VALUES ('54', '3', '8');
INSERT INTO `auths_role_menu` VALUES ('55', '4', '1');
INSERT INTO `auths_role_menu` VALUES ('56', '4', '14');
INSERT INTO `auths_role_menu` VALUES ('57', '4', '15');
INSERT INTO `auths_role_menu` VALUES ('58', '4', '16');
INSERT INTO `auths_role_menu` VALUES ('59', '4', '17');
INSERT INTO `auths_role_menu` VALUES ('6', '1', '6');
INSERT INTO `auths_role_menu` VALUES ('60', '4', '18');
INSERT INTO `auths_role_menu` VALUES ('7', '1', '7');
INSERT INTO `auths_role_menu` VALUES ('8', '1', '8');
INSERT INTO `auths_role_menu` VALUES ('9', '1', '9');

-- ----------------------------
-- Table structure for config_group
-- ----------------------------
DROP TABLE IF EXISTS `config_group`;
CREATE TABLE `config_group`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分组名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分组代码',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分组描述',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `is_system` tinyint(1) NULL DEFAULT 0 COMMENT '是否系统分组',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_config_group_code`(`code` ASC) USING BTREE COMMENT '分组代码唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '配置分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_group
-- ----------------------------
INSERT INTO `config_group` VALUES ('1', '网站配置', 'WEBSITE', '网站基础信息配置', 1, 1, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);

-- ----------------------------
-- Table structure for config_item
-- ----------------------------
DROP TABLE IF EXISTS `config_item`;
CREATE TABLE `config_item`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `group_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分组编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置项名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置项代码',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置值',
  `component_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '配置描述',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_config_item_group_code`(`group_code` ASC) USING BTREE,
  INDEX `idx_config_item_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_item
-- ----------------------------
INSERT INTO `config_item` VALUES ('1', 'WEBSITE', '网站名称', 'WEBSITE_NAME', 'Dream Framework', 'input', '网站的名称', 1, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 14:39:02', NULL);
INSERT INTO `config_item` VALUES ('10', 'WEBSITE', '微信联系方式', 'CONTACT_WECHAT', '', 'input', '微信联系方式', 10, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('11', 'WEBSITE', '社交媒体链接', 'SOCIAL_LINKS', '[{\"image\": \"/static/images/github.png\", \"title\": \"GitHub\", \"url\": \"https://github.com/galaxy-cms\"}]', 'json-editor', '社交媒体链接配置，JSON格式', 11, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('2', 'WEBSITE', '网站Logo', 'WEBSITE_LOGO', '/static/images/logo.png', 'upload', '网站的Logo图片路径', 2, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('3', 'WEBSITE', '网站描述', 'WEBSITE_DESCRIPTION', '一个个人用的基础脚手架，基于 Java/Go/Python 语言开发', 'textarea', '网站的简要描述', 3, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 15:19:21', NULL);
INSERT INTO `config_item` VALUES ('4', 'WEBSITE', '网站关键字', 'WEBSITE_KEYWORDS', 'CMS,内容管理,Go,Golang,Galaxy', 'input', '网站SEO关键字，用逗号分隔', 4, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('5', 'WEBSITE', '网站作者', 'WEBSITE_AUTHOR', 'Galaxy Team', 'input', '网站的作者或开发团队', 5, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('6', 'WEBSITE', '版权信息', 'WEBSITE_COPYRIGHT', '© 2025 Charlie Zhang. All Rights Reserved.', 'input', '网站版权信息', 6, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 14:38:18', NULL);
INSERT INTO `config_item` VALUES ('7', 'WEBSITE', '网站版本', 'WEBSITE_VERSION', '1.1.0', 'input', '网站的版本号', 7, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('8', 'WEBSITE', 'QQ联系方式', 'CONTACT_QQ', '', 'input', 'QQ联系方式', 8, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);
INSERT INTO `config_item` VALUES ('9', 'WEBSITE', '联系邮箱', 'CONTACT_EMAIL', 'contact@galaxy-cms.com', 'input', '网站联系邮箱', 9, 0, NULL, NULL, '2025-11-25 09:22:17', NULL, '2025-11-25 09:22:17', NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `dict_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型',
  `type_label` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典值',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字典标签',
  `sort` int NULL DEFAULT 0 COMMENT '排序号',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1991796032439787522', 'SYS_BOOLEAN', '布尔', 'true', '是', 1, 0, NULL, NULL, '2025-11-21 17:09:14', '1', '2025-11-21 17:36:08', '1');
INSERT INTO `sys_dict` VALUES ('1991798667876143106', 'SYS_BOOLEAN', '布尔', 'false', '否', 2, 0, NULL, NULL, '2025-11-21 17:19:42', '1', '2025-11-21 17:36:15', '1');
INSERT INTO `sys_dict` VALUES ('1991806439812411393', 'SYS_GENDER', '性别', '0', '未知', 1, 0, NULL, NULL, '2025-11-21 17:50:36', '1', '2025-11-21 17:50:36', '1');
INSERT INTO `sys_dict` VALUES ('1991806502106214402', 'SYS_GENDER', '性别', '1', '男', 2, 0, NULL, NULL, '2025-11-21 17:50:51', '1', '2025-11-21 17:50:51', '1');
INSERT INTO `sys_dict` VALUES ('1991806537300619265', 'SYS_GENDER', '性别', '2', '女', 3, 0, NULL, NULL, '2025-11-21 17:50:59', '1', '2025-11-21 17:50:59', '1');
INSERT INTO `sys_dict` VALUES ('1991807086897049601', 'SYS_MENU', '菜单类型', '0', '内部菜单', 1, 0, NULL, NULL, '2025-11-21 17:53:10', '1', '2025-11-21 17:53:10', '1');
INSERT INTO `sys_dict` VALUES ('1991807169059270657', 'SYS_MENU', '菜单类型', '1', '外部菜单', 2, 0, NULL, NULL, '2025-11-21 17:53:30', '1', '2025-11-21 17:53:30', '1');
INSERT INTO `sys_dict` VALUES ('1991807231617314818', 'SYS_MENU', '菜单类型', '2', '重定向菜单', 3, 0, NULL, NULL, '2025-11-21 17:53:45', '1', '2025-11-21 17:53:45', '1');
INSERT INTO `sys_dict` VALUES ('1991807311690772481', 'SYS_MENU', '菜单类型', '3', 'Iframe嵌入', 4, 0, NULL, NULL, '2025-11-21 17:54:04', '1', '2025-11-21 17:54:04', '1');
INSERT INTO `sys_dict` VALUES ('1991807706479636481', 'SYS_OPEN_METHOD', '打开方式', '0', '当前窗口', 1, 0, NULL, NULL, '2025-11-21 17:55:38', '1', '2025-11-21 17:55:38', '1');
INSERT INTO `sys_dict` VALUES ('1991807767821332482', 'SYS_OPEN_METHOD', '打开方式', '1', '新窗口打开', 2, 0, NULL, NULL, '2025-11-21 17:55:53', '1', '2025-11-21 17:55:53', '1');
INSERT INTO `sys_dict` VALUES ('1993294975594102785', 'SYS_ACCOUNT_STATUS', '账户状态', '0', '正常', 1, 0, NULL, NULL, '2025-11-25 20:25:30', NULL, '2025-11-25 20:25:30', NULL);
INSERT INTO `sys_dict` VALUES ('1993295048461746177', 'SYS_ACCOUNT_STATUS', '账户状态', '1', '锁定', 2, 0, NULL, NULL, '2025-11-25 20:25:48', NULL, '2025-11-25 20:25:48', NULL);
INSERT INTO `sys_dict` VALUES ('1993295131907424258', 'SYS_ACCOUNT_STATUS', '账户状态', '2', '禁用', 3, 0, NULL, NULL, '2025-11-25 20:26:08', NULL, '2025-11-25 20:26:13', NULL);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作类型',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求参数',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `operation_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志分类',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作模块',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作描述',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作状态',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '日志消息',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统活动日志记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `pid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '父级ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '重定向路径',
  `external_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '外部链接地址',
  `menu_type` int NULL DEFAULT 0 COMMENT '菜单类型：0-内部菜单 1-外链菜单 2-重定向菜单 3-iframe嵌入',
  `open_target` int NULL DEFAULT 0 COMMENT '打开方式：0-当前窗口 1-新窗口打开',
  `iframe_attrs` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'iframe属性',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '缓存',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '可见',
  `pined` tinyint(1) NULL DEFAULT 0 COMMENT '钉钉',
  `without_tab` tinyint(1) NULL DEFAULT 0 COMMENT '无标签页',
  `parameters` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头部参数',
  `extra_params` json NULL COMMENT '路由参数',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_menu_name`(`name` ASC) USING BTREE,
  INDEX `idx_sys_menu_path`(`path` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', 'dashboard', '/dashboard', '/dashboard/index.vue', NULL, NULL, 0, 0, NULL, '仪表盘', 'dashboard', 1, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('10', '9', 'authaccount', '/auth/account', '/auth/authaccount/index.vue', NULL, NULL, 0, 0, NULL, '账号管理', 'user', 1, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('11', '9', 'authrole', '/auth/role', '/auth/authrole/index.vue', NULL, NULL, 0, 0, NULL, '角色管理', 'user-checked', 2, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('12', '9', 'authgroup', '/auth/group', '/auth/authgroup/index.vue', NULL, NULL, 0, 0, NULL, '用户组管理', 'usergroup-add', 3, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('14', '0', 'user', '/user', NULL, NULL, NULL, 0, 0, NULL, '用户中心', 'user', 5, 0, 1, 0, 0, NULL, NULL, 1, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-25 21:30:24', NULL);
INSERT INTO `sys_menu` VALUES ('15', '14', 'userprofile', '/user/profile', '/user/userprofile/index.vue', NULL, NULL, 0, 0, NULL, '用户信息', 'user-marked', 1, 1, 1, 0, 0, NULL, NULL, 1, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-25 21:30:24', NULL);
INSERT INTO `sys_menu` VALUES ('16', '14', 'userinfo', '/user/info', '/user/userinfo/index.vue', NULL, NULL, 0, 0, NULL, '账户信息', 'info-circle', 2, 1, 1, 0, 0, NULL, NULL, 1, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-25 21:30:24', NULL);
INSERT INTO `sys_menu` VALUES ('17', '14', 'userpreference', '/user/preference', '/user/userpreference/index.vue', NULL, NULL, 0, 0, NULL, '偏好设置', 'setting', 3, 1, 1, 0, 0, NULL, NULL, 1, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-25 21:30:24', NULL);
INSERT INTO `sys_menu` VALUES ('18', '14', 'userstats', '/user/stats', '/user/userstats/index.vue', NULL, NULL, 0, 0, NULL, '数据统计', 'chart-bar', 4, 1, 1, 0, 0, NULL, NULL, 1, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-25 21:30:24', NULL);
INSERT INTO `sys_menu` VALUES ('2', '0', 'system', '/system', NULL, NULL, NULL, 0, 0, NULL, '系统管理', 'setting', 2, 0, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('24', '0', 'code', '/code', NULL, NULL, NULL, 0, 0, NULL, '错误页面', 'error-circle', 7, 0, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('25', '24', '404', '/code/404', '/code/404.vue', NULL, NULL, 0, 0, NULL, '404页面', 'file-unkCURRENT_TIMESTAMPn', 1, 0, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('3', '2', 'sysmenu', '/system/menu', '/system/sysmenu/index.vue', NULL, NULL, 0, 0, NULL, '菜单管理', 'menu', 1, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('4', '2', 'sysdict', '/system/dict', '/system/sysdict/index.vue', NULL, NULL, 0, 0, NULL, '数据字典', 'book', 2, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('5', '2', 'syslog', '/system/log', '/system/syslog/index.vue', NULL, NULL, 0, 0, NULL, '操作日志', 'system-log', 3, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('6', '0', 'config', '/config', NULL, NULL, NULL, 0, 0, NULL, '配置管理', 'setting', 3, 0, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('7', '6', 'configgroup', '/config/group', '/config/configgroup/index.vue', NULL, NULL, 0, 0, NULL, '配置分组', 'setting', 1, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('8', '6', 'configitem', '/config/item', '/config/configitem/index.vue', NULL, NULL, 0, 0, NULL, '配置项管理', 'setting', 2, 1, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);
INSERT INTO `sys_menu` VALUES ('9', '0', 'auth', '/auth', NULL, NULL, NULL, 0, 0, NULL, '权限管理', 'user-safety', 4, 0, 1, 0, 0, NULL, NULL, 0, NULL, NULL, '2025-11-19 02:41:47', NULL, '2025-11-19 02:41:47', NULL);

-- ----------------------------
-- Table structure for users_info
-- ----------------------------
DROP TABLE IF EXISTS `users_info`;
CREATE TABLE `users_info`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `account_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户ID',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` smallint NULL DEFAULT 0 COMMENT '性别：0-未知 1-男 2-女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `signature` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个性签名',
  `background` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个人背景图片',
  `interests` json NULL COMMENT '兴趣标签',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个人网站',
  `github` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'GitHub',
  `gitee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'GitTee',
  `blog` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '博客',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_users_info_account`(`account_id` ASC) USING BTREE,
  INDEX `idx_users_info_nickname`(`nickname` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_info
-- ----------------------------
INSERT INTO `users_info` VALUES ('1', '1', '超级管理员', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:21', NULL, '2025-11-25 09:22:21', NULL);
INSERT INTO `users_info` VALUES ('1993255249856114689', '1993255249205997569', 'menus', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 17:47:39', NULL, '2025-11-25 17:47:39', NULL);
INSERT INTO `users_info` VALUES ('2', '2', '系统管理员', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:24', NULL, '2025-11-25 09:22:24', NULL);
INSERT INTO `users_info` VALUES ('3', '3', '技术部经理', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:28', NULL, '2025-11-25 09:22:28', NULL);
INSERT INTO `users_info` VALUES ('4', '4', '张三', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:33', NULL, '2025-11-25 09:22:33', NULL);
INSERT INTO `users_info` VALUES ('5', '5', '李四', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:37', NULL, '2025-11-25 09:22:37', NULL);
INSERT INTO `users_info` VALUES ('6', '6', '王五', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:41', NULL, '2025-11-25 09:22:41', NULL);
INSERT INTO `users_info` VALUES ('7', '7', '赵六', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:46', NULL, '2025-11-25 09:22:46', NULL);
INSERT INTO `users_info` VALUES ('8', '8', '孙七', 'https://ts1.tc.mm.bing.net/th/id/R-C.6a6d08e2769800fe626bdf3ccbd7b2bf?rik=f2hCf09zz%2f9k3w&riu=http%3a%2f%2fpic.imeitou.com%2fuploads%2fallimg%2f240318%2f10-24031Q62553.jpg&ehk=xiruSwoAcF8DeN%2bzMk5g%2bRv%2baNwdPsxj4YUV4CLEe2A%3d&risl=&pid=ImgRaw&r=0', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2025-11-25 09:22:50', NULL, '2025-11-25 09:22:50', NULL);

-- ----------------------------
-- Table structure for users_preference
-- ----------------------------
DROP TABLE IF EXISTS `users_preference`;
CREATE TABLE `users_preference`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `account_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户ID',
  `theme` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'light' COMMENT '主题',
  `language` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'zh-CN' COMMENT '系统语言',
  `email_notifications` tinyint(1) NULL DEFAULT 1 COMMENT '邮件通知',
  `push_notifications` tinyint(1) NULL DEFAULT 1 COMMENT '推送通知',
  `allow_direct_message` tinyint(1) NULL DEFAULT 1 COMMENT '允许私信',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_users_preference_account`(`account_id` ASC) USING BTREE COMMENT '账户ID唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户偏好设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_preference
-- ----------------------------
INSERT INTO `users_preference` VALUES ('1', '1', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:22', NULL, '2025-11-25 09:22:22', NULL);
INSERT INTO `users_preference` VALUES ('1993255250665615361', '1993255249205997569', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 17:47:39', NULL, '2025-11-25 17:47:39', NULL);
INSERT INTO `users_preference` VALUES ('2', '2', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:25', NULL, '2025-11-25 09:22:25', NULL);
INSERT INTO `users_preference` VALUES ('3', '3', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:30', NULL, '2025-11-25 09:22:30', NULL);
INSERT INTO `users_preference` VALUES ('4', '4', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:34', NULL, '2025-11-25 09:22:34', NULL);
INSERT INTO `users_preference` VALUES ('5', '5', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:39', NULL, '2025-11-25 09:22:39', NULL);
INSERT INTO `users_preference` VALUES ('6', '6', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:43', NULL, '2025-11-25 09:22:43', NULL);
INSERT INTO `users_preference` VALUES ('7', '7', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:47', NULL, '2025-11-25 09:22:47', NULL);
INSERT INTO `users_preference` VALUES ('8', '8', 'light', 'zh-CN', 1, 1, 1, 0, NULL, NULL, '2025-11-25 09:22:51', NULL, '2025-11-25 09:22:51', NULL);

-- ----------------------------
-- Table structure for users_profile
-- ----------------------------
DROP TABLE IF EXISTS `users_profile`;
CREATE TABLE `users_profile`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `account_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户ID',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `school` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学校',
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业',
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学号',
  `company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公司',
  `job_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职位',
  `industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '行业',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信',
  `show_birthday` tinyint(1) NULL DEFAULT 0 COMMENT '是否显示生日',
  `show_location` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示地理位置',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_users_profile_account`(`account_id` ASC) USING BTREE COMMENT '账户ID唯一索引',
  INDEX `idx_users_profile_location`(`country` ASC, `province` ASC, `city` ASC) USING BTREE COMMENT '地理位置联合索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户档案详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_profile
-- ----------------------------
INSERT INTO `users_profile` VALUES ('1', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:22', NULL, '2025-11-25 09:22:22', NULL);
INSERT INTO `users_profile` VALUES ('1993255252259450881', '1993255249205997569', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 17:47:40', NULL, '2025-11-25 17:47:40', NULL);
INSERT INTO `users_profile` VALUES ('2', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:24', NULL, '2025-11-25 09:22:24', NULL);
INSERT INTO `users_profile` VALUES ('3', '3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:29', NULL, '2025-11-25 09:22:29', NULL);
INSERT INTO `users_profile` VALUES ('4', '4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:34', NULL, '2025-11-25 09:22:34', NULL);
INSERT INTO `users_profile` VALUES ('5', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:38', NULL, '2025-11-25 09:22:38', NULL);
INSERT INTO `users_profile` VALUES ('6', '6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:42', NULL, '2025-11-25 09:22:42', NULL);
INSERT INTO `users_profile` VALUES ('7', '7', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:46', NULL, '2025-11-25 09:22:46', NULL);
INSERT INTO `users_profile` VALUES ('8', '8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, NULL, NULL, '2025-11-25 09:22:51', NULL, '2025-11-25 09:22:51', NULL);

-- ----------------------------
-- Table structure for users_stats
-- ----------------------------
DROP TABLE IF EXISTS `users_stats`;
CREATE TABLE `users_stats`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `account_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户ID',
  `level` int NULL DEFAULT 1 COMMENT '等级',
  `exp` bigint NULL DEFAULT 0 COMMENT '经验值',
  `total_exp` bigint NULL DEFAULT 0 COMMENT '累计经验值',
  `login_days` int NULL DEFAULT 0 COMMENT '登录天数',
  `continuous_login_days` int NULL DEFAULT 0 COMMENT '连续登录天数',
  `post_count` bigint NULL DEFAULT 0 COMMENT '发帖数',
  `comment_count` bigint NULL DEFAULT 0 COMMENT '评论数',
  `like_count` bigint NULL DEFAULT 0 COMMENT '获赞数',
  `follow_count` bigint NULL DEFAULT 0 COMMENT '关注数',
  `fans_count` bigint NULL DEFAULT 0 COMMENT '粉丝数',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除 1-已删除',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '逻辑删除时间',
  `deleted_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '删除人（账号/ID）',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人（账号/ID）',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `updated_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后更新人（账号/ID）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_users_stats_level`(`level` ASC) USING BTREE,
  INDEX `idx_users_stats_exp`(`exp` ASC) USING BTREE,
  INDEX `idx_users_stats_total_exp`(`total_exp` ASC) USING BTREE,
  INDEX `idx_users_stats_login_days`(`login_days` ASC) USING BTREE,
  INDEX `idx_users_stats_continuous_login_days`(`continuous_login_days` ASC) USING BTREE,
  INDEX `idx_users_stats_post_count`(`post_count` ASC) USING BTREE,
  INDEX `idx_users_stats_comment_count`(`comment_count` ASC) USING BTREE,
  INDEX `idx_users_stats_like_count`(`like_count` ASC) USING BTREE,
  INDEX `idx_users_stats_follow_count`(`follow_count` ASC) USING BTREE,
  INDEX `idx_users_stats_fans_count`(`fans_count` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户统计信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_stats
-- ----------------------------
INSERT INTO `users_stats` VALUES ('1', '1', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:22', NULL, '2025-11-25 09:22:22', NULL);
INSERT INTO `users_stats` VALUES ('1993255251559002113', '1993255249205997569', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 17:47:40', NULL, '2025-11-25 17:47:40', NULL);
INSERT INTO `users_stats` VALUES ('2', '2', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:26', NULL, '2025-11-25 09:22:26', NULL);
INSERT INTO `users_stats` VALUES ('3', '3', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:31', NULL, '2025-11-25 09:22:31', NULL);
INSERT INTO `users_stats` VALUES ('4', '4', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:35', NULL, '2025-11-25 09:22:35', NULL);
INSERT INTO `users_stats` VALUES ('5', '5', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:39', NULL, '2025-11-25 09:22:39', NULL);
INSERT INTO `users_stats` VALUES ('6', '6', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:43', NULL, '2025-11-25 09:22:43', NULL);
INSERT INTO `users_stats` VALUES ('7', '7', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:48', NULL, '2025-11-25 09:22:48', NULL);
INSERT INTO `users_stats` VALUES ('8', '8', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL, '2025-11-25 09:22:52', NULL, '2025-11-25 09:22:52', NULL);

SET FOREIGN_KEY_CHECKS = 1;
