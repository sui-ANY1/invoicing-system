-- ----------------------------
-- 横切支撑：站内消息、统一附件
-- ----------------------------

-- 1. 站内消息
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`
(
    `message_id`   bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '消息 id',
    `member_id`    bigint(20)   NOT NULL                 COMMENT '接收用户 id',
    `message_type` char(1)      DEFAULT '1' COMMENT '消息类型（1系统通知 2开票进度 3红字进度 4注册进度）',
    `title`        varchar(100) NOT NULL COMMENT '标题',
    `content`      varchar(500) DEFAULT NULL COMMENT '内容正文',
    `biz_type`     char(1)      DEFAULT '9' COMMENT '跳转业务类型（1开票 2红字 3注册 4认证 9通用）',
    `biz_id`       bigint(20)   DEFAULT NULL             COMMENT '跳转业务 id',
    `is_read`      char(1)      DEFAULT '0' COMMENT '是否已读（0未读 1已读）',
    `read_time`    datetime     DEFAULT NULL COMMENT '已读时间',
    `del_flag`     char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`message_id`),
    KEY            `idx_member_read` (`member_id`,`is_read`),
    KEY            `idx_biz` (`biz_type`,`biz_id`),
    KEY            `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站内消息';

-- 2. 统一附件（全局，所有业务文件统一管理）
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment`
(
    `attachment_id` bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '附件 id',
    `biz_type`      varchar(30)  NOT NULL COMMENT '业务类型（enterprise 主体、invoice_apply 开票、red_apply 红字、reg_apply 注册收件、reg_complete 注册完成凭证、manual_result 人工回填、temp 未关联临时上传等）',
    `biz_id`        bigint(20)   NOT NULL                 COMMENT '业务 id（未关联业务时为 0）',
    `material_type` varchar(30)  DEFAULT NULL COMMENT '材料类型（如 BUSINESS_LICENSE 营业执照、LEGAL_PERSON_ID_CARD_FRONT 法人身份证正面、SHAREHOLDER_ID_CARD_FRONT 股东身份证正面、OPERATOR_ID_CARD_BACK 经营者身份证反面、RESIDENCE_PROOF 住所证明、KINSHIP_PROOF 亲属关系证明、PREMISES_PROOF 场所使用证明、PROXY_BOOK 受托委托材料、OTHER_PERMIT 其他许可材料）',
    `ref_key`       varchar(64)  DEFAULT NULL COMMENT '关联对象键（同一业务下多份同类材料的人员标识，如股东姓名或序号；不适用为空）',
    `file_type`     varchar(20)  DEFAULT NULL COMMENT '文件类型（pdf/ofd/xml/jpg/png 等）',
    `file_name`     varchar(200) DEFAULT NULL COMMENT '文件名（原始文件名）',
    `file_url`      varchar(500) NOT NULL COMMENT '文件访问 url',
    `file_size`     bigint(20)   DEFAULT 0                COMMENT '文件大小（字节）',
    `upload_by`     varchar(64)  DEFAULT '' COMMENT '上传人（小程序会员 id 或账号）',
    `del_flag`      char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_time`   datetime     DEFAULT NULL COMMENT '上传时间',
    PRIMARY KEY (`attachment_id`),
    KEY             `idx_biz` (`biz_type`,`biz_id`),
    KEY             `idx_material_type` (`material_type`),
    KEY             `idx_file_url` (`file_url`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一附件';
