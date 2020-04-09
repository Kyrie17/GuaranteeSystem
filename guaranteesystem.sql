/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : guaranteesystem

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2020-04-09 11:04:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `a_id` varchar(11) NOT NULL,
  `a_phone` varchar(11) DEFAULT NULL,
  `a_password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `a_major` int(11) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for admin_chat
-- ----------------------------
DROP TABLE IF EXISTS `admin_chat`;
CREATE TABLE `admin_chat` (
  `username` varchar(255) DEFAULT NULL,
  `roomid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`roomid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for build
-- ----------------------------
DROP TABLE IF EXISTS `build`;
CREATE TABLE `build` (
  `id` int(11) NOT NULL,
  `building` varchar(20) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for commidity
-- ----------------------------
DROP TABLE IF EXISTS `commidity`;
CREATE TABLE `commidity` (
  `commidity_id` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for evaluateform
-- ----------------------------
DROP TABLE IF EXISTS `evaluateform`;
CREATE TABLE `evaluateform` (
  `e_ordernumber` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` varchar(11) DEFAULT NULL,
  `r_ordernumber` int(11) DEFAULT NULL,
  `e_level` int(11) DEFAULT NULL,
  `e_mess` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`e_ordernumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for noticeinform
-- ----------------------------
DROP TABLE IF EXISTS `noticeinform`;
CREATE TABLE `noticeinform` (
  `UpLoader` varchar(255) NOT NULL,
  `FilePath` varchar(255) NOT NULL,
  `FileName` varchar(255) NOT NULL,
  `DownLoadNum` int(11) NOT NULL,
  `UpLoadDate` varchar(255) NOT NULL,
  PRIMARY KEY (`FilePath`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for repairform
-- ----------------------------
DROP TABLE IF EXISTS `repairform`;
CREATE TABLE `repairform` (
  `r_ordernumber` int(11) NOT NULL AUTO_INCREMENT,
  `r_sertype` int(11) NOT NULL,
  `r_seradd` varchar(50) DEFAULT NULL,
  `r_serinform` varchar(70) DEFAULT NULL,
  `r_sertime` varchar(30) DEFAULT NULL,
  `r_detailtime` varchar(30) DEFAULT NULL,
  `r_judgestate` int(11) DEFAULT NULL,
  `r_filepath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `s_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `s_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `a_id` varchar(11) DEFAULT NULL,
  `r_userconfirm` int(11) DEFAULT '0',
  `r_submittime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '维修人员确定维修单完成',
  PRIMARY KEY (`r_ordernumber`)
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `r_ordernumber` int(11) NOT NULL,
  `satisfied` varchar(20) DEFAULT NULL,
  `suggest` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `flag` int(5) DEFAULT '0',
  `repairman` varchar(50) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `build` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `room` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` int(255) DEFAULT NULL,
  `timestamp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for studentpurchase
-- ----------------------------
DROP TABLE IF EXISTS `studentpurchase`;
CREATE TABLE `studentpurchase` (
  `stu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `commidity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `commidity_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for student_chat
-- ----------------------------
DROP TABLE IF EXISTS `student_chat`;
CREATE TABLE `student_chat` (
  `username` varchar(255) DEFAULT NULL,
  `roomid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `num` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for worker
-- ----------------------------
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker` (
  `worker_id` varchar(20) NOT NULL,
  `worker_password` varchar(50) NOT NULL,
  `ser_type` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
