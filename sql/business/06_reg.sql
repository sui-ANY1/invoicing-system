-- ----------------------------
-- 注册收件域：收件主表、有限公司资料、股东信息、个体户资料
-- ----------------------------

-- 1. 收件主表
DROP TABLE IF EXISTS `t_reg_apply`;
CREATE TABLE `t_reg_apply`
(
    `reg_apply_id`    bigint(20)  NOT NULL AUTO_INCREMENT  COMMENT '收件 id',
    `reg_apply_no`    varchar(30) NOT NULL COMMENT '收件编号（唯一）',
    `subject_type`    char(1)      DEFAULT '1' COMMENT '主体类型（1有限责任公司 2个体工商户）',
    `contact_name`    varchar(50)  DEFAULT NULL COMMENT '申请联系人姓名',
    `contact_phone`   varchar(20)  DEFAULT NULL COMMENT '申请联系人手机号',
    `member_id`       bigint(20)  DEFAULT NULL             COMMENT '提交人（小程序用户 id）',
    `complete_status` char(1)      DEFAULT '0' COMMENT '完成状态（0待完成 1已完成）',
    `complete_by`     varchar(64)  DEFAULT '' COMMENT '完成人',
    `complete_time`   datetime     DEFAULT NULL COMMENT '完成时间',
    `complete_remark` varchar(500) DEFAULT NULL COMMENT '完成备注／失败原因',
    `del_flag`        char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`       varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`     datetime     DEFAULT NULL COMMENT '提交时间',
    `update_by`       varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`          varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`reg_apply_id`),
    UNIQUE KEY `uk_reg_apply_no` (`reg_apply_no`),
    KEY               `idx_member` (`member_id`),
    KEY               `idx_subject_type` (`subject_type`),
    KEY               `idx_complete_status` (`complete_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='注册收件主表';

-- 2. 有限公司资料（与收件 1:1）
DROP TABLE IF EXISTS `t_reg_company`;
CREATE TABLE `t_reg_company`
(
    `company_id`           bigint(20)    NOT NULL AUTO_INCREMENT  COMMENT '公司资料 id',
    `reg_apply_id`         bigint(20)    NOT NULL                 COMMENT '所属收件 id',
    `proposed_name`        varchar(100)   DEFAULT NULL COMMENT '拟用公司名称',
    `alternate_names`      varchar(500)   DEFAULT NULL COMMENT '备选名称（多个，逗号分隔）',
    `registered_capital`   decimal(16, 2) DEFAULT NULL COMMENT '注册资本（单位：万元）',
    `business_scope`       varchar(1000)  DEFAULT NULL COMMENT '经营范围',
    `legal_person_name`    varchar(50)    DEFAULT NULL COMMENT '法定代表人姓名',
    `legal_person_id_type` char(1)        DEFAULT '1' COMMENT '法人证件类型（1居民身份证 2其他）',
    `legal_person_id_card` varchar(30)    DEFAULT NULL COMMENT '法人证件号码',
    `legal_person_phone`   varchar(20)    DEFAULT NULL COMMENT '法人本人手机号',
    `create_time`          datetime       DEFAULT NULL COMMENT '创建时间',
    `update_time`          datetime       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`company_id`),
    KEY                    `idx_reg_apply` (`reg_apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='有限公司注册资料';

-- 3. 股东信息（与收件 1:N）
DROP TABLE IF EXISTS `t_reg_shareholder`;
CREATE TABLE `t_reg_shareholder`
(
    `shareholder_id`    bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '股东 id',
    `reg_apply_id`      bigint(20)   NOT NULL                 COMMENT '所属收件 id',
    `shareholder_type`  char(1)       DEFAULT '1' COMMENT '股东类型（1自然人 2机构）',
    `shareholder_name`  varchar(100) NOT NULL COMMENT '股东姓名／机构名称',
    `contact_phone`     varchar(20)   DEFAULT NULL COMMENT '手机号（机构为联系人电话）',
    `hold_ratio`        decimal(5, 2) DEFAULT NULL COMMENT '持股比例（百分数，如 51.00，多名股东合计 100）',
    `qualification_url` varchar(500)  DEFAULT NULL COMMENT '机构股东资格材料 url',
    `sort_no`           int(11)      DEFAULT 0                COMMENT '排序号',
    `create_time`       datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`       datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`shareholder_id`),
    KEY                 `idx_reg_apply` (`reg_apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='注册股东信息';

-- 4. 个体工商户资料（与收件 1:1）
DROP TABLE IF EXISTS `t_reg_sole`;
CREATE TABLE `t_reg_sole`
(
    `sole_id`          bigint(20)    NOT NULL AUTO_INCREMENT  COMMENT '个体户资料 id',
    `reg_apply_id`     bigint(20)    NOT NULL                 COMMENT '所属收件 id',
    `handle_identity`  char(1)       DEFAULT '1' COMMENT '办理身份（1经营者本人 2受托经办人）',
    `operator_name`    varchar(50)   DEFAULT NULL COMMENT '经营者姓名',
    `operator_id_type` char(1)       DEFAULT '1' COMMENT '证件类型（1居民身份证 2其他）',
    `operator_id_card` varchar(30)   DEFAULT NULL COMMENT '证件号码',
    `operator_phone`   varchar(20)   DEFAULT NULL COMMENT '经营者手机号',
    `operator_address` varchar(255)  DEFAULT NULL COMMENT '经营者住所（与经营场所分开）',
    `business_form`    char(1)       DEFAULT '1' COMMENT '经营形式（1个人经营 2家庭经营）',
    `family_members`   varchar(500)  DEFAULT NULL COMMENT '家庭成员及关系（家庭经营填写，多个）',
    `proposed_name`    varchar(100)  DEFAULT NULL COMMENT '拟用名称（允许无名称，可空）',
    `business_scope`   varchar(1000) DEFAULT NULL COMMENT '经营范围',
    `premises_type`    char(1)       DEFAULT '1' COMMENT '经营场所类型（1实体场所 2符合条件的网络场所）',
    `premises_address` varchar(255)  DEFAULT NULL COMMENT '场所地址或网络平台信息',
    `create_time`      datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`sole_id`),
    KEY                `idx_reg_apply` (`reg_apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个体工商户注册资料';
