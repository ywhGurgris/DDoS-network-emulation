use ddos;

drop table if exists c_dos_node;
create table c_dos_node (
  c_id varchar(40),
  c_name varchar(50),
  c_ip varchar(50),
  c_port int,
  c_status varchar(50),
  primary key (c_id)
);

drop table if exists c_ddos_config;
create table c_ddos_config (
  c_ip varchar(50),
  c_port int,
  c_startup_delay int,
  c_interval int,
  c_count int,
  c_duration int,
  c_video_url varchar(500),
  c_scene_url varchar(500)
);

drop table if exists c_bhr_node;
create table c_bhr_node (
  c_id varchar(40),
  c_name varchar(50),
  c_ip varchar(50),
  c_port int,
  c_target_ip varchar(50),
  c_status varchar(50),
  primary key (c_id)
);

drop table if exists c_attack_path;
create table c_attack_path (
  c_src_id varchar(50),
  c_dst_id varchar(50),
  c_src_ip varchar(50),
  c_dst_ip varchar(50),
  c_mid_id varchar(50),
  c_mid_ip varchar(50),
  c_start_time datetime,
  c_end_time datetime,
  c_action varchar(100)
);

drop table if exists c_access_attack_config;
create table c_access_attack_config (
  c_target_ip varchar(50),
  c_target_port int,
  c_threshold int
);

drop table if exists c_bhr_config;
create table c_bhr_config (
  c_scene_url varchar(500)
);