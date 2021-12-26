create table user (
email varchar(50) not null,
age int(3) not null,
name varchar(20) not null,
pwd varchar(50) not null,
 is_admin boolean default false,
nick_name varchar(50) not null,
avatar	varchar(100),
reg_at	datetime default now(),
following text,
follower text,
primary key (email));

create table post (
post_id bigint auto_increment,
writer varchar(50) not null,
like_cnt int(10) default 0,
reg_at datetime default now(),
mod_at datetime default now(),
read_cnt int(10) default 0,
files text,
content text not null,
primary key (post_id)
);

create table comment(
 cmt_id bigint auto_increment,
 writer varchar(50) not null,
 reg_at datetime default now(),
 mod_at datetime default now(),
 post_id  bigint,
 content text not null,
 primary key (cmt_id)
);

create table `like` (
 email varchar(50),
 post_id bigint
);
