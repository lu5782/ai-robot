drop table if exists cyp_user_info;
create table cyp_user_info
(
    id          int(10)      auto_increment comment '主键',
    username    varchar(64)  default '' comment '用户昵称',
    password    varchar(64)  default '' comment '用户密码',
    phoneNum    varchar(64)  default '' comment '手机号',
    email       varchar(64)  default '' comment '邮箱地址',
    sex         tinyint(4)   default 0  comment '性别,0:保密,1:男,2:女',
    isDeleted   tinyint(4)   default 1  comment '是否删除,1:未删除,2:已删除',
    createBy    varchar(64)  default '' comment '创建人',
    createDate  timestamp    default current_timestamp comment '创建时间',
    updateBy    varchar(64)  default '' comment '更新人',
    updateDate  timestamp    default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique idx_username(username),
    unique idx_phoneNum(phoneNum),
    unique idx_email(email)
)engine=innodb default charset=utf8mb4 collate=utf8mb4_general_ci comment '用户表';

