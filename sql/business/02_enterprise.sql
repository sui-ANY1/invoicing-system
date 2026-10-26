-- ----------------------------
-- 主体认证域：开票企业主体档案、经办人信息、主体级税务凭据
-- ----------------------------

-- 1. 开票企业主体档案
DROP TABLE IF EXISTS `t_enterprise`;
CREATE TABLE `t_enterprise`
(
    `enterprise_id`    bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '主体 id',
    `member_id`        bigint(20)   NOT NULL                 COMMENT '归属小程序用户 id',
    `enterprise_type`  char(1)      DEFAULT '1' COMMENT '主体类型（1公司 2个体工商户）',
    `enterprise_name`  varchar(100) NOT NULL COMMENT '主体名称（营业执照名称）',
    `tax_number`       varchar(30)  NOT NULL COMMENT '统一社会信用代码／税号',
    `register_address` varchar(255) DEFAULT NULL COMMENT '主体登记信息（地址等，按营业执照填写）',
    `business_license` varchar(500) DEFAULT NULL COMMENT '营业执照附件 url',
    `auth_status`      char(1)      DEFAULT '1' COMMENT '认证状态（0/1核验中 2已认证 3认证不通过）',
    `audit_by`         varchar(64)  DEFAULT '' COMMENT '审核人',
    `audit_time`       datetime     DEFAULT NULL COMMENT '审核时间',
    `audit_remark`     varchar(500) DEFAULT NULL COMMENT '审核意见／不通过原因',
    `status`           char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `del_flag`         char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`        varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`           varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`enterprise_id`),
    KEY                `idx_member` (`member_id`),
    KEY                `idx_tax_number` (`tax_number`),
    KEY                `idx_auth_status` (`auth_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开票企业主体档案';

-- 2. 经办人信息
DROP TABLE IF EXISTS `t_enterprise_contact`;
CREATE TABLE `t_enterprise_contact`
(
    `contact_id`       bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '经办人 id',
    `enterprise_id`    bigint(20)   NOT NULL                 COMMENT '所属主体 id',
    `member_id`        bigint(20)   DEFAULT NULL             COMMENT '归属小程序用户 id',
    `contact_identity` char(1)      DEFAULT '1' COMMENT '办理身份（1本人：法定代表人/经营者 2受托经办人）',
    `contact_name`     varchar(50) NOT NULL COMMENT '姓名',
    `id_type`          char(1)      DEFAULT '1' COMMENT '证件类型（1居民身份证 2其他）',
    `id_card`          varchar(30)  DEFAULT NULL COMMENT '证件号码',
    `phone`            varchar(20) NOT NULL COMMENT '本人手机号',
    `id_card_url`      varchar(500) DEFAULT NULL COMMENT '身份证明附件 url',
    `proxy_book_url`   varchar(500) DEFAULT NULL COMMENT '委托书附件 url（受托经办人必填，本人为空）',
    `auth_scope`       varchar(500) DEFAULT NULL COMMENT '授权事项',
    `auth_start_date`  date         DEFAULT NULL COMMENT '授权有效期起',
    `auth_end_date`    date         DEFAULT NULL COMMENT '授权有效期止',
    `del_flag`         char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`        varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`           varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`contact_id`),
    KEY                `idx_enterprise` (`enterprise_id`),
    KEY                `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开票企业经办人信息';

-- 3. 主体级税务凭据
DROP TABLE IF EXISTS `t_tax_credential`;
CREATE TABLE `t_tax_credential`
(
    `credential_id`      bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '税务凭据 id',
    `enterprise_id`      bigint(20)   NOT NULL                 COMMENT '所属主体 id',
    `tax_account`        varchar(100) DEFAULT NULL COMMENT '税务平台账号（展示时脱敏）',
    `tax_password`       varchar(500) DEFAULT NULL COMMENT '税务平台密码密文（AES 加密后 Base64，不存明文）',
    `secret_key_version` varchar(20)  DEFAULT NULL COMMENT '加密密钥版本（密钥独立存放，不入库）',
    `auth_scope`         varchar(500) DEFAULT NULL COMMENT '授权范围（如开票、取票）',
    `auth_start_date`    date         DEFAULT NULL COMMENT '授权有效期起',
    `auth_end_date`      date         DEFAULT NULL COMMENT '授权有效期止',
    `credential_version` int(11)      DEFAULT 1                COMMENT '凭据版本（每次更新密码递增）',
    `usable_status`      char(1)      DEFAULT '0' COMMENT '可用状态（0可用 1已撤销 2已过期）',
    `last_verify_time`   datetime     DEFAULT NULL COMMENT '最后验证时间',
    `revoke_by`          varchar(64)  DEFAULT '' COMMENT '撤销人',
    `revoke_time`        datetime     DEFAULT NULL COMMENT '撤销时间',
    `del_flag`           char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`          varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`        datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`          varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`        datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`             varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`credential_id`),
    KEY                  `idx_enterprise` (`enterprise_id`),
    KEY                  `idx_usable_status` (`usable_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主体级税务凭据';
