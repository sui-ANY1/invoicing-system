-- ----------------------------
-- 发票红字域：发票信息（蓝/红统一）、红字申请、红字明细
-- ----------------------------

-- 1. 发票信息（蓝票、红票统一存储）
DROP TABLE IF EXISTS `t_invoice`;
CREATE TABLE `t_invoice`
(
    `invoice_id`        bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '发票 id',
    `invoice_no`        varchar(50) NOT NULL COMMENT '发票号码（唯一发票标识，数电票一般 20 位）',
    `invoice_code`      varchar(50)    DEFAULT NULL COMMENT '发票代码（数电票可空）',
    `invoice_kind`      char(1)        DEFAULT '1' COMMENT '发票种类（1蓝字 2红字）',
    `apply_id`          bigint(20)   DEFAULT NULL             COMMENT '关联开票申请 id（蓝票）',
    `red_apply_id`      bigint(20)   DEFAULT NULL             COMMENT '关联红字申请 id（红票）',
    `enterprise_id`     bigint(20)   DEFAULT NULL             COMMENT '销售方主体 id',
    `seller_name`       varchar(100)   DEFAULT NULL COMMENT '销售方名称（票面快照）',
    `seller_tax_number` varchar(30)    DEFAULT NULL COMMENT '销售方税号（票面快照）',
    `buyer_name`        varchar(100)   DEFAULT NULL COMMENT '购买方名称（票面快照）',
    `buyer_tax_number`  varchar(30)    DEFAULT NULL COMMENT '购买方税号（票面快照）',
    `total_amount`      decimal(16, 2) DEFAULT NULL COMMENT '票面金额合计（不含税，票面回传）',
    `total_tax`         decimal(16, 2) DEFAULT NULL COMMENT '税额合计（票面回传）',
    `total_price_tax`   decimal(16, 2) DEFAULT NULL COMMENT '价税合计（票面回传）',
    `invoice_date`      date           DEFAULT NULL COMMENT '开票日期',
    `red_status`        char(1)        DEFAULT '0' COMMENT '红冲状态（0未红冲 1部分红冲 2全额红冲）',
    `pdf_url`           varchar(500)   DEFAULT NULL COMMENT 'PDF 文件 url',
    `ofd_url`           varchar(500)   DEFAULT NULL COMMENT 'OFD 文件 url',
    `xml_url`           varchar(500)   DEFAULT NULL COMMENT 'XML 文件 url',
    `file_status`       char(1)        DEFAULT '0' COMMENT '文件状态（0待文件 1已获取 2获取失败）',
    `del_flag`          char(1)        DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`         varchar(64)    DEFAULT '' COMMENT '创建者',
    `create_time`       datetime       DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(64)    DEFAULT '' COMMENT '更新者',
    `update_time`       datetime       DEFAULT NULL COMMENT '更新时间',
    `remark`            varchar(500)   DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`invoice_id`),
    UNIQUE KEY `uk_invoice_no` (`invoice_no`),
    KEY                 `idx_apply` (`apply_id`),
    KEY                 `idx_red_apply` (`red_apply_id`),
    KEY                 `idx_enterprise` (`enterprise_id`),
    KEY                 `idx_kind` (`invoice_kind`),
    KEY                 `idx_red_status` (`red_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发票信息（蓝/红统一）';

-- 2. 红字申请
DROP TABLE IF EXISTS `t_red_apply`;
CREATE TABLE `t_red_apply`
(
    `red_apply_id`        bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '红字申请 id',
    `red_apply_no`        varchar(30) NOT NULL COMMENT '红字申请单号（唯一）',
    `original_invoice_id` bigint(20)   NOT NULL                 COMMENT '原蓝票发票 id',
    `member_id`           bigint(20)   DEFAULT NULL             COMMENT '申请人（小程序用户 id）',
    `red_reason`          char(1)      DEFAULT '1' COMMENT '红字原因（1开票有误 2退货折让 3服务中止 9其他）',
    `red_range`           char(1)      DEFAULT '1' COMMENT '处理范围（1全部 2部分）',
    `red_status`          char(1)      DEFAULT '0' COMMENT '红字状态（0已提交 1待税务/对方确认 2确认到期 3处理中 4已开红票待文件 5已交付 6明确失败 7已停止）',
    `confirm_status`      char(1)      DEFAULT '0' COMMENT '红字确认单状态（0未发起 1已发起待确认 2已确认 3已失效）',
    `confirm_start_time`  datetime     DEFAULT NULL COMMENT '确认单发起时间',
    `confirm_expire_time` datetime     DEFAULT NULL COMMENT '确认单失效时间（发起后 72 小时）',
    `description`         varchar(500) DEFAULT NULL COMMENT '情况说明',
    `proof_url`           varchar(500) DEFAULT NULL COMMENT '相关依据附件 url',
    `fail_reason`         varchar(500) DEFAULT NULL COMMENT '失败原因',
    `del_flag`            char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`           varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`         datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`         datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`red_apply_id`),
    UNIQUE KEY `uk_red_apply_no` (`red_apply_no`),
    KEY                   `idx_original_invoice` (`original_invoice_id`),
    KEY                   `idx_member` (`member_id`),
    KEY                   `idx_red_status` (`red_status`),
    KEY                   `idx_confirm_status` (`confirm_status`),
    KEY                   `idx_confirm_expire` (`confirm_expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='红字申请';

-- 3. 红字明细（部分红冲时填写；金额类按票面回传，红字一般为负）
DROP TABLE IF EXISTS `t_red_item`;
CREATE TABLE `t_red_item`
(
    `red_item_id`      bigint(20)    NOT NULL AUTO_INCREMENT  COMMENT '红字明细 id',
    `red_apply_id`     bigint(20)    NOT NULL                 COMMENT '所属红字申请 id',
    `original_item_id` bigint(20)    DEFAULT NULL             COMMENT '原开票明细 id',
    `item_name`        varchar(200)   DEFAULT NULL COMMENT '项目名称',
    `quantity`         decimal(16, 4) DEFAULT NULL COMMENT '数量（按票面，红字一般为负）',
    `invoice_amount`   decimal(16, 2) DEFAULT NULL COMMENT '票面金额（票面回传）',
    `tax_rate`         decimal(8, 4)  DEFAULT NULL COMMENT '税率（票面回传）',
    `tax_amount`       decimal(16, 2) DEFAULT NULL COMMENT '税额（票面回传）',
    `sort_no`          int(11)       DEFAULT 0                COMMENT '排序号',
    `create_time`      datetime       DEFAULT NULL COMMENT '创建时间',
    `update_time`      datetime       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`red_item_id`),
    KEY                `idx_red_apply` (`red_apply_id`),
    KEY                `idx_original_item` (`original_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='红字明细';
