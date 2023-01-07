create table sys_user
(
    id           varchar(64)       not null comment '用户id'
        primary key,
    username     varchar(50)       not null comment '账户',
    password     varchar(200)      not null comment '用户密码密文',
    phone        varchar(20)       null comment '手机号码',
    real_name    varchar(60)       null comment '真实名称',
    nick_name    varchar(60)       null comment '昵称',
    email        varchar(50)       null comment '邮箱(唯一)',
    status       tinyint default 1 null comment '账户状态(1.正常 2.锁定 )',
    sex          tinyint default 1 null comment '性别(1.男 2.女)',
    deleted      tinyint default 1 null comment '是否删除(1未删除；0已删除)',
    create_id    varchar(64)       null comment '创建人',
    update_id    varchar(64)       null comment '更新人',
    create_where tinyint default 1 null comment '创建来源(1.web 2.android 3.ios )',
    create_time  datetime          null comment '创建时间',
    update_time  datetime          null comment '更新时间',
    constraint unique_username
        unique (username) comment '用户名唯一'
)
    comment '用户表' charset = utf8mb3;

create table stock_market_index_info
(
    id            varchar(20)    not null comment '主键字段（无业务意义）'
        primary key,
    mark_Id       char(12)       not null comment '大盘ID',
    cur_time      datetime       not null comment '当前时间',
    mark_name     varchar(20)    null comment '指数名称',
    cur_point     decimal(18, 2) null comment '当前点数',
    current_price decimal(18, 2) null comment '当前价格',
    updown_rate   decimal(18, 3) null comment '涨跌率',
    trade_account bigint         null comment '成交量（多少手）',
    trade_volume  bigint         null comment '成交额(万元)',
    constraint unique_id_time
        unique (mark_Id, cur_time)
)
    comment '股票大盘数据详情表' charset = utf8mb3;

create table stock_market_log_price
(
    id              varchar(20)    not null comment '主键ID'
        primary key,
    market_code     char(12)       not null comment '大盘编码',
    cur_date        date           not null comment '当前日期',
    pre_close_price decimal(10, 2) null comment '前收盘价格',
    open_price      decimal(10, 2) null comment '开盘价格',
    constraint unique_mcode_date
        unique (market_code, cur_date) comment '大盘id与日期组成唯一索引'
)
    comment '股票大盘 开盘价与前收盘价流水表' collate = utf8mb4_general_ci;