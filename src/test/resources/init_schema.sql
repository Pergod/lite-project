DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `age` INT(3) unsigned,
    `gender` INT(1) COMMENT '0:女,1:男',
    `company_id` INT(20) NOT NULL,
    `deparntment_id` INT(20) NOT NULL,
    `spare_day` INT(3),
    `role` INT(2) NOT NULL COMMENT '0:开发,1:测试,2:业务',
    `state` INT(1) NOT NULL DEFAULT 0 COMMENT '0:在职,1:离职',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='员工表';

DROP TABLE IF EXISTS `jira`;
CREATE TABLE `jira` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `jira_no` VARCHAR(30) NOT NULL,
    `jira_desc` VARCHAR(255),
    `developer_id` INT(20) UNSIGNED,
    `reporter_id` INT(20) UNSIGNED NOT NULL,
    `tester_id` INT(20) UNSIGNED,
    `company_id` INT(20) UNSIGNED NOT NULL,
    `department_id` INT(20) UNSIGNED NOT NULL,
    `publish_id` INT(20) UNSIGNED,
    `business_id` INT(20) UNSIGNED,
    `manpower_input` INT(3) UNSIGNED COMMENT '需投入人力，单位/人',
    `work_day` INT(3) UNSIGNED COMMENT '需投入天数，单位/天',
    `state` INT(3) UNSIGNED,
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `jira_unique` (`jira_no`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='需求表';

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `company_desc` VARCHAR(255) DEFAULT '',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `company_UNIQUE` (`company_desc`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='分公司表';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `company_id` INT(20) UNSIGNED NOT NULL,
    `department_desc` VARCHAR(255) DEFAULT '',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `department_UNIQUE` (`department_desc`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='部门表';

DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `department_id` INT(20) UNSIGNED NOT NULL,
    `team_desc` VARCHAR(255) DEFAULT '',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `team_UNIQUE` (`team_desc`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COMMENT='处室表';


DROP TABLE IF EXISTS `publish`;
CREATE TABLE `publish` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `publish_desc` VARCHAR(255) DEFAULT '',
    `attribute` INT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0:常规版本，1：紧急版本',
    `publish_date` DATE NOT NULL,
    `manager_id` INT(20) UNSIGNED NOT NULL COMMENT '负责人员',
    `verifier_id` INT(20) UNSIGNED NOT NULL COMMENT '验证人员',
    `deployer_id` INT(20) UNSIGNED NOT NULL COMMENT '部署人员',
    `state` INT(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0:未发布,1:已发布 ',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `publish_UNIQUE` (`publish_desc`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COMMENT='版本排期表';

DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `suite_name` VARCHAR(10) NOT NULL,
    `business_desc` VARCHAR(255) DEFAULT '',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `business_UNIQUE` (`business_desc`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COMMENT='条线表';

DROP TABLE IF EXISTS `changes`;
CREATE TABLE `changes` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `changes_desc` VARCHAR(255) DEFAULT '',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `changes_UNIQUE` (`changes_desc`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COMMENT='功能更改点表';

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `project_id` VARCHAR(20) NOT NULL,
    `project_desc` VARCHAR(255) DEFAULT '',
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `project_UNIQUE` (`project_desc`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COMMENT='项目表';

DROP TABLE IF EXISTS `map`;
CREATE TABLE `map` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `table_name` VARCHAR(30) NOT NULL,
    `table_attr` VARCHAR(30) NOT NULL,
    `before_value` INT(3),
    `after_value` VARCHAR(30) NOT NULL,
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='关系映射表';

DROP TABLE IF EXISTS `jira_project_changes`;
CREATE TABLE `jira_project_changes` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `jira_no` VARCHAR(30) NOT NULL,
    `jira_project_id` INT(20) UNSIGNED NOT NULL,
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`),
    UNIQUE KEY `jira_project_changes_UNIQUE` (`jira_no`,`jira_project_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='jira,change中间表';


DROP TABLE IF EXISTS `project_changes`;
CREATE TABLE `project_changes` (
    `id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `project_id` VARCHAR(20) NOT NULL,
    `changes_desc` VARCHAR(255) DEFAULT '',
    `changes_id` INT(20) UNSIGNED,
    `created_date` DATETIME,
    `updated_date` DATETIME,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='project,change中间表';