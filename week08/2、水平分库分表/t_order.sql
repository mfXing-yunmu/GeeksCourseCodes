-- # 订单表
CREATE TABLE IF NOT EXISTS `t_order`
(
  `order_id`          bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id`           bigint(20) NOT NULL COMMENT '用户ID',
  `create_time`       datetime            DEFAULT NULL COMMENT '创建时间',
  `update_time`       datetime            DEFAULT NULL COMMENT '更新时间',
  `create_pin`        varchar(50)         DEFAULT NULL COMMENT '创建用户',
  `update_pin`        varchar(50)         DEFAULT NULL COMMENT '更新用户',
  `yn`                tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `ts`                timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`order_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;