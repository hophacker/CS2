/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : cloud

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2012-07-19 05:26:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `csarray`
-- ----------------------------
DROP TABLE IF EXISTS `csarray`;
CREATE TABLE `csarray` (
  `userName` varchar(45) NOT NULL,
  `sArray` mediumblob,
  `dArray` mediumblob,
  `sLen` int(11) DEFAULT NULL,
  `dLen` int(11) DEFAULT NULL,
  `sTable` mediumblob,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of csarray
-- ----------------------------

-- ----------------------------
-- Table structure for `file`
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `userName` varchar(45) NOT NULL,
  `id` int(11) NOT NULL,
  `summary` text NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`userName`,`id`),
  KEY `username` (`userName`),
  CONSTRAINT `username` FOREIGN KEY (`userName`) REFERENCES `user` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'Wanghui');
INSERT INTO `user` VALUES ('d', 'ddd');
INSERT INTO `user` VALUES ('fengjie', 'fengjie');
INSERT INTO `user` VALUES ('jokerfeng', 'jokerfeng');
INSERT INTO `user` VALUES ('Wanghui', 'ddd');
INSERT INTO `user` VALUES ('创建数据库指定数据库的字符集', 'fengjie');

-- ----------------------------
-- Procedure structure for `checkCS2Array`
-- ----------------------------
DROP PROCEDURE IF EXISTS `checkCS2Array`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkCS2Array`(IN `name` varchar(50))
BEGIN
	#Routine body goes here...
	if EXISTS(select * from csarray where userName=name) THEN
		DELETE FROM csarray where userName=name;
	end if;
END
;;
DELIMITER ;
