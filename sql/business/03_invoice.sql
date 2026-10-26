-- ----------------------------
-- 开票办理域：开票申请主表、开票明细、短信验证记录、内部 RPA 任务
-- ----------------------------

-- 1. 开票申请主表（销售方为已认证企业，购买方为客户填写）
DROP TABLE IF EXISTS `t_invoice_apply`;
CREATE TABLE `t_invoice_apply`
(
    `apply_id`            bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '开票申请 id',
    `apply_no`            varchar(30)  NOT NULL COMMENT '申请单号（业务单号，唯一）',
    `member_id`           bigint(20)   NOT NULL                 COMMENT '申请人（小程序用户 id）',
    `enterprise_id`       bigint(20)   NOT NULL                 COMMENT '销售主体 id（已认证开票企业）',
    `invoice_type`        char(1)      DEFAULT '1' COMMENT '发票类型（1普通发票 2增值税专用发票）',
    `buyer_type`          char(1)      DEFAULT '1' COMMENT '购买方类型（1企业/组织 2个人）',
    `buyer_name`          varchar(100) NOT NULL COMMENT '购买方名称／姓名',
    `buyer_tax_number`    varchar(30)  DEFAULT NULL COMMENT '购买方税号（条件必填）',
    `buyer_address_phone` varchar(255) DEFAULT NULL COMMENT '购买方地址、电话（条件填写）',
    `buyer_bank`          varchar(255) DEFAULT NULL COMMENT '购买方开户行及账号（条件填写）',
    `deal_proof`          varchar(500) DEFAULT NULL COMMENT '交易证明（合同/订单）主附件 url，多个走附件表',
    `receive_email`       varchar(100) DEFAULT NULL COMMENT '发票接收邮箱',
    `remark`              varchar(500) DEFAULT NULL COMMENT '备注',
    `apply_status`        char(1)      DEFAULT '0' COMMENT '开票状态（0已提交 1待税务验证 2开票处理中 3结果核对中 4已开票待文件 5交付完成 6明确失败 7停止处理中）',
    `fail_reason`         varchar(500) DEFAULT NULL COMMENT '失败原因',
    `del_flag`            char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `create_by`           varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`         datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`         datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`apply_id`),
    UNIQUE KEY `uk_apply_no` (`apply_no`),
    KEY                   `idx_member` (`member_id`),
    KEY                   `idx_enterprise` (`enterprise_id`),
    KEY                   `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开票申请主表';

-- 2. 开票明细（原始数据客户填写；票面金额类字段回传后回填，可空只读）
DROP TABLE IF EXISTS `t_invoice_item`;
CREATE TABLE `t_invoice_item`
(
    `item_id`        bigint(20)    NOT NULL AUTO_INCREMENT  COMMENT '明细 id',
    `apply_id`       bigint(20)    NOT NULL                 COMMENT '所属开票申请 id',
    `item_name`      varchar(200) NOT NULL COMMENT '项目名称（商品或服务）',
    `category`       varchar(100)   DEFAULT NULL COMMENT '业务类目／税收分类',
    `spec`           varchar(100)   DEFAULT NULL COMMENT '规格型号',
    `quantity`       decimal(16, 4) DEFAULT NULL COMMENT '数量（客户原始提供）',
    `unit`           varchar(20)    DEFAULT NULL COMMENT '单位',
    `original_price` decimal(16, 6) DEFAULT NULL COMMENT '原始单价（交易约定单价）',
    `price_caliber`  char(1)        DEFAULT '1' COMMENT '计价口径（1含税 2不含税）',
    `agreed_amount`  decimal(16, 2) DEFAULT NULL COMMENT '原合同约定金额（原始录入，非系统计算）',
    `invoice_amount` decimal(16, 2) DEFAULT NULL COMMENT '票面金额（票面回传，可空只读）',
    `tax_rate`       decimal(8, 4)  DEFAULT NULL COMMENT '税率（票面回传，如 0.1300）',
    `tax_amount`     decimal(16, 2) DEFAULT NULL COMMENT '税额（票面回传，可空只读）',
    `sort_no`        int(11)       DEFAULT 0                COMMENT '排序号',
    `create_time`    datetime       DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`item_id`),
    KEY              `idx_apply` (`apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开票明细';

-- 3. 短信验证记录（本期仅短信；验证码走 Redis，不落表）
DROP TABLE IF EXISTS `t_tax_verify`;
CREATE TABLE `t_tax_verify`
(
    `verify_id`      bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '验证记录 id',
    `biz_type`       char(1)     DEFAULT '1' COMMENT '业务类型（1蓝字开票 2红字）',
    `biz_id`         bigint(20)   NOT NULL                 COMMENT '关联业务 id（申请/红字 id）',
    `verify_type`    char(1)     DEFAULT '1' COMMENT '验证方式（1短信，本期仅支持短信）',
    `target_account` varchar(50) DEFAULT NULL COMMENT '接收账号（手机号，脱敏存储）',
    `verify_key`     varchar(64) DEFAULT NULL COMMENT '验证标识（验证会话/RPA 标识）',
    `verify_status`  char(1)     DEFAULT '0' COMMENT '状态（0已发起待验证 1已提交待代填 2验证通过 3验证失败/过期）',
    `expire_time`    datetime    DEFAULT NULL COMMENT '过期时间',
    `create_time`    datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`verify_id`),
    KEY              `idx_biz` (`biz_type`,`biz_id`),
    KEY              `idx_verify_key` (`verify_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='税务短信验证记录';

-- 4. 内部 RPA 任务（无独立 PC 页面，供调度与状态轮询）
DROP TABLE IF EXISTS `t_rpa_task`;
CREATE TABLE `t_rpa_task`
(
    `task_id`         bigint(20)  NOT NULL AUTO_INCREMENT  COMMENT 'RPA 任务 id',
    `task_no`         varchar(30) NOT NULL COMMENT '平台任务号（唯一）',
    `biz_type`        char(1)      DEFAULT '1' COMMENT '业务类型（1蓝字 2红字）',
    `biz_id`          bigint(20)  NOT NULL                 COMMENT '关联业务 id（开票申请/红字申请 id）',
    `rpa_task_no`     varchar(64)  DEFAULT NULL COMMENT 'RPA 系统侧任务号',
    `task_status`     char(1)      DEFAULT '0' COMMENT '任务状态（0待下发 1待用户验证 2执行中 3结果待核对 4已开票待文件 5已完成 6明确失败 7已停止）',
    `next_query_time` datetime     DEFAULT NULL COMMENT '下次查询时间（轮询用）',
    `fail_reason`     varchar(500) DEFAULT NULL COMMENT '失败原因',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`          varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`task_id`),
    UNIQUE KEY `uk_task_no` (`task_no`),
    KEY               `idx_biz` (`biz_type`,`biz_id`),
    KEY               `idx_rpa_task_no` (`rpa_task_no`),
    KEY               `idx_next_query_time` (`next_query_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内部 RPA 任务';
