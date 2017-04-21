-------------------------------------------------------
--      创建库，表，约束，过程，用户，权限等脚本
-------------------------------------------------------
drop database webfood;
create database webfood character set utf8;

use webfood;

--创建管理员
create table resadmin(
	raid int primary key auto_increment,
	raname varchar(50),
	rapwd varchar(50)
)engine=MYISAM character set utf8;

--用户表
create table resuser(
	userid int primary key auto_increment,
	username varchar(50),
	pwd varchar(50),
	email varchar(500)
)engine=MYISAM character set utf8;

--normprice:原价
create table resfood(
	fid int primary key auto_increment,
	fname varchar(50),
	normprice numeric(8,2),
	realprice numeric(8,2),
	detail varchar(2000),
	fphoto varchar(1000)
)engine=MYISAM character set utf8;

--订单表

create table resorder(
	roid int primary key auto_increment,
	userid int,
	address varchar(500),
	tel varchar(100),
	ordertime date,
	deliverytime date,
	ps varchar(2000),
	status int
)engine=MYISAM character set utf8;
--订单表下的单人号与用户表中的客户编号有主外键关系
alter table resorder 
			add constraint fk_resorder
			     foreign key(userid) references resuser(userid);

--dealprice:成交价 roid：订单号 fid:商品号
create table resorderitem(
	roiid int primary key auto_increment,
	roid int ,
	fid int ,
	dealprice numeric(8,2),
	num int
)engine=MYISAM character set utf8;

alter table resorderitem
	 add constraint fk_resorderitem_roid
		foreign key(roid) references resorder(roid);

alter table resorderitem
	 add constraint fk_tbl_res_fid
		foreign key(fid) references resorder(fid);	
		
		
select * from resadmin;
select * from resuser;
select * from resfood;
select * from resorder;
select * from resorderitem;
-------------------------
--drop table 	resadmin;	
--drop table 	resuser;	
--drop table 	resfood;	
--drop table 	resorder;	
--drop table 	resorderitem;	
		
commit;




















