drop table if exists cpy_user;
create table cpy_user
(
    id       int primary key auto_increment not null comment '主键',
    username varchar(64) not null comment '用户昵称',
    password varchar(64) not null comment '用户密码'
);

