/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-03-06 18:57:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL COMMENT '资源id',
  `name` varchar(250) NOT NULL COMMENT '资源名称',
  `perms` varchar(250) NOT NULL,
  `url` varchar(250) NOT NULL COMMENT '资料链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', '主页', 'perms[user:index]', '/index.jsp');
INSERT INTO `resources` VALUES ('2', '管理页', 'perms[user:admin]', '/admin.jsp');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL COMMENT '自增id',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'admin');
INSERT INTO `roles` VALUES ('2', 'user');

-- ----------------------------
-- Table structure for role_resources
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `roleid` int(11) NOT NULL,
  `resourcesid` int(11) NOT NULL,
  PRIMARY KEY (`roleid`,`resourcesid`),
  KEY `resourcesid` (`resourcesid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resources
-- ----------------------------
INSERT INTO `role_resources` VALUES ('1', '1');
INSERT INTO `role_resources` VALUES ('2', '1');
INSERT INTO `role_resources` VALUES ('1', '2');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account` varchar(250) NOT NULL COMMENT '用户名称',
  `password` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', 'admin');
INSERT INTO `users` VALUES ('2', 'user', 'user');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `userid` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`userid`,`roleid`),
  KEY `roleid` (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '1');
INSERT INTO `user_roles` VALUES ('2', '2');
