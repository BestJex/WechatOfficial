create table activity(
id int(11) not null auto_increment comment '主键',
name varchar(64) not null default '' comment '活动名称',
description varchar(255)   comment '活动描述',
create_time datetime not null comment '创建时间',
update_time datetime not null comment '修改时间',
primary key(id)
)comment='活动表';

create table user(
id int(11) not null auto_increment comment '主键',
app_id varchar(32) not null default '' comment '属于那个公众号的用户',
open_id varchar(32) not null default '' comment '公众号平台openid',
union_id varchar(32) not null default '' comment '公众号开放平台unionid',
nickname varchar(32) not null default '' comment '昵称',
sex tinyint(4) not null default '0' comment '性别,0:未知,1:男,2:女',
country varchar(32) not null default '' comment '所在国家',
province varchar(32) not null default '' comment '所在省份',
city varchar(32) not null default '' comment '所在城市',
language varchar(16) not null default '' comment '用户语言',
head_img_url varchar(256) not null default '' comment '用户头像url',
subscribe_time int(11) not null default '0' comment '关注时间',
subscribe_scene varchar(32) not null default '' comment '关注来源',
status tinyint(4) not null default '0' comment '用户状态,1:已关注,2:已取关',
create_time datetime not null comment '创建时间',
update_time datetime not null comment '修改时间',
primary key(id)
)comment='公众号用户表';

alter table user add index appid_openid(app_id,open_id);
alter table user add index appid_unionid(app_id,union_id);

create table official(
id int(11) not null auto_increment comment '主键',
account varchar(32) not null comment '公众号原始id',
app_id varchar(32) not null comment '开发者ID(AppID)',
app_secret varchar(64) not null comment '开发者密码(AppSecret)',
token varchar(32) not null comment '令牌(Token)',
aes_key varchar(64) not null comment '消息加解密密钥',
create_time datetime not null comment '创建时间',
update_time datetime not null comment '修改时间',
primary key(id),
unique key(app_id)
)comment='公众号信息表';

create table orders(
id int(11) not null auto_increment comment '主键',
activity_id int(11) not null  comment '活动id',
open_id varchar(32) not null comment '用户openid',
order_id varchar(50) not null comment '订单id',
price int(11) not null comment '金额。单位:分',
name varchar(50) not null comment '收件人',
phone varchar(50) not null comment '收件电话',
address varchar(255) not null comment '收件地址',
shipping_code varchar(50) not null comment '快递单号',
status tinyint(4) not null default 1 comment '状态：1、未付款，2、已付款，3、未发货，4、已完成',
pay_type tinyint(4) not null default 0 comment '支付类型:0、未知；1、微信；2、支付宝',
trade_no varchar(50) not null comment '支付流水号',
payment_time datetime comment '付款时间',
remark varchar(255) not null comment '备注',
create_time datetime not null comment '创建时间',
update_time datetime not null comment '修改时间',
primary key(id),
unique key(order_id)
)comment='订单基本信息表';

create table admin_user(
id int(11) not null auto_increment comment '主键',
username varchar(50) not null  comment '用户名',
password char(128) not null comment '密码',
status tinyint(4) not null default 1 comment '用户状态',
remark varchar(255) not null default '' comment '备注',
create_time datetime not null comment '创建时间',
update_time datetime not null comment '修改时间',
primary key(id),
unique key(username)
)comment='管理后台用户表';

insert into admin_user(username,password, status,remark,create_time,update_time)
values('admin','d285e1b18aa2599e1157b82ea47879adafefdaa260a184420b4649f19e91cd0b','1','密码：123456',now(),now());

alter table official add column name varchar(50) not null comment '公众号名字' after id;
alter table activity add column official_id int(11) not null comment '公众号ID' after id;
create table `subscribe_reply`(
id int(11) not null auto_increment comment '主键',
official_id int(11) not null  comment '公众号ID',
content varchar(255) not null comment '回复内容',
create_time datetime not null comment '创建时间',
update_time datetime not null comment '修改时间',
primary key(id)
)comment='公众号关注回复表';

update admin_user set remark='' where remark is null;
alter table admin_user modify column remark varchar(255)  not null default '' comment '备注' ;

CREATE TABLE `keyword_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `official_id` int(11) NOT NULL DEFAULT '0' COMMENT '公众号id',
  `keyword` varchar(128) NOT NULL DEFAULT '' COMMENT '关键字',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '关键字回复内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
)COMMENT='关键字自动回复表';

create table payment(
id int(11) not null auto_increment comment '主键',
app_id varchar(32) not null comment '公众账号ID',
app_secret varchar(64) not null comment '开发者密码(AppSecret)',
mch_id varchar(32) not null comment '商户号',
pay_key varchar(64) not null comment '支付密钥',
notify_url varchar(64) not null comment '通知回调地址',
update_time datetime not null comment '修改时间',
primary key(id),
unique key(app_id)
)comment='微信支付配置表';