-- ----------------------------
-- 审核办理域：认证审核记录、人工结果回填（均为留痕表，只增不改）
-- ----------------------------

-- 1. 认证审核记录
DROP TABLE IF EXISTS `t_audit_record`;
CREATE TABLE `t_audit_record`
(
    `audit_record_id` bigint(20)    NOT NULL AUTO_INCREMENT  COMMENT '审核记录 id',
    `biz_type`        char(1)       DEFAULT '1' COMMENT '业务类型（1主体认证 2开票申请 3红字申请）',
    `biz_id`          bigint(20)    NOT NULL                 COMMENT '业务 id（如主体 id）',
    `biz_version`     int(11)       DEFAULT 1                COMMENT '业务版本号（提交时版本，防止审核旧版本）',
    `audit_by`        varchar(64) NOT NULL COMMENT '审核人',
    `audit_result`    char(1)       DEFAULT '1' COMMENT '审核结论（1通过 2要求补充 3不通过）',
    `audit_opinion`   varchar(1000) DEFAULT NULL COMMENT '审核意见／不通过原因',
    `audit_time`      datetime    NOT NULL COMMENT '审核时间',
    `create_time`     datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`audit_record_id`),
    KEY               `idx_biz` (`biz_type`,`biz_id`),
    KEY               `idx_audit_by` (`audit_by`),
    KEY               `idx_audit_time` (`audit_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证审核记录';

-- 2. 人工结果回填
DROP TABLE IF EXISTS `t_manual_result`;
CREATE TABLE `t_manual_result`
(
    `result_id`     bigint(20)     NOT NULL AUTO_INCREMENT  COMMENT '回填记录 id',
    `biz_type`      char(1)       DEFAULT '1' COMMENT '业务类型（1红字 2蓝字）',
    `biz_id`        bigint(20)     NOT NULL                 COMMENT '业务 id（如红字申请 id）',
    `fill_by`       varchar(64) NOT NULL COMMENT '回填人',
    `actual_result` char(1)       DEFAULT '1' COMMENT '实际结果（1已交付 2办理失败 9其他）',
    `voucher_url`   varchar(500)  DEFAULT NULL COMMENT '原始凭证附件 url（如红票文件）',
    `fill_note`     varchar(1000) DEFAULT NULL COMMENT '回填说明',
    `fill_time`     datetime    NOT NULL COMMENT '回填时间',
    `create_time`   datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`result_id`),
    KEY             `idx_biz` (`biz_type`,`biz_id`),
    KEY             `idx_fill_by` (`fill_by`),
    KEY             `idx_fill_time` (`fill_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人工结果回填';
