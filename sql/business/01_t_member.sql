-- ----------------------------
-- 小程序用户表
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`
(
    `member_id`       bigint(20)   NOT NULL AUTO_INCREMENT  COMMENT '用户 id',
    `openid`          varchar(64) NOT NULL COMMENT '微信小程序 openid',
    `unionid`         varchar(64)  DEFAULT NULL COMMENT '微信开放平台 unionid',
    `nick_name`       varchar(64)  DEFAULT '' COMMENT '昵称',
    `avatar`          varchar(500) DEFAULT '' COMMENT '头像地址',
    `phone`           varchar(20)  DEFAULT NULL COMMENT '手机号（getPhoneNumber 授权绑定）',
    `gender`          tinyint(4)   DEFAULT 0                COMMENT '性别（0未知 1男 2女）',
    `status`          char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `del_flag`        char(1)      DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    `last_login_time` datetime     DEFAULT NULL COMMENT '最后登录时间',
    `create_by`       varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`          varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`member_id`),
    UNIQUE KEY `uk_openid` (`openid`),
    KEY               `idx_unionid` (`unionid`),
    KEY               `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序用户表';

