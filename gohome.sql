CREATE DATABASE gohome
USE gohome


# 所有用户信息
CREATE TABLE IF NOT EXISTS user_message(
	user_id INT UNSIGNED NOT NULL AUTO_INCREMENT,#主键
	user_password VARCHAR(68) NOT NULL,   #16位密码
	telephone VARCHAR(16) DEFAULT NULL,   #手机号码
	address VARCHAR(100) NOT NULL,#地址
	user_name VARCHAR(33) NOT NULL,   #用户名，8位
	gender INT DEFAULT 2,   #性别，0为男，1为女，2为未知
 	user_type INT DEFAULT 0,  #用户类型，0为普通用户，1为组员a，2为地区组织管理人
	protrait VARCHAR(255) DEFAULT NULL,# 头像url
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE(user_id),
	PRIMARY KEY(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;




# 领养信息
CREATE TABLE IF NOT EXISTS adopt_message(
	adopt_id INT UNSIGNED NOT NULL AUTO_INCREMENT,#主键
	pet_name VARCHAR(33) NOT NULL,   #d动物名字
	pet_type INT NOT NULL, #动物类型,0为猫，1位狗，2为小鸟
	gender INT NOT NULL,#性别，0男，1女，2未知
	age VARCHAR(32) DEFAULT NULL,
	handle_id INT UNSIGNED,      #领养信息发布人id
	pictures VARCHAR(255) DEFAULT NULL,   #图片集
	state INT NOT NULL DEFAULT 0,#领养状态，默认为0
	description TINYTEXT DEFAULT NULL, #动物描述
	address VARCHAR(100) NOT NULL,    #地址
	vaccinate BOOLEAN NOT NULL,  #疫苗情况
	steriled BOOLEAN NOT NULL,   #绝育情况
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE(adopt_id),
	PRIMARY KEY(adopt_id),
	FOREIGN KEY (handle_id) REFERENCES user_message(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 领养申请信息
CREATE TABLE IF NOT EXISTS adopt_appliment(
	appliment_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	adopt_id INT UNSIGNED,# 领养信息id
	user_id INT UNSIGNED,# 用户id
	apply_name VARCHAR(49) NOT NULL,# 申请人姓名
	telephone VARCHAR(16) NOT NULL,# 申请人手机号
	address VARCHAR(100) NOT NULL,# 申请人地址
	job VARCHAR(32) NOT NULL,# 申请人职业
	desciption TINYTEXT DEFAULT NULL,# 申请人个人描述
	state INT DEFAULT 0,# 申请状态，0待审核，1处理中，2已完成
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(appliment_id),
	FOREIGN KEY(adopt_id) REFERENCES adopt_message(adopt_id),
	FOREIGN KEY(user_id) REFERENCES user_message(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 领养申请对接
CREATE TABLE IF NOT EXISTS adopt_handle_info(
	info_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	handle_id INT UNSIGNED,# 对接人id
	appliment_id INT UNSIGNED,# 申请信息id
	state INT DEFAULT 0,# 对接状态，0处理中，1已完成
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(info_id),
	FOREIGN KEY(handle_id) REFERENCES user_message(user_id),
	FOREIGN KEY(appliment_id) REFERENCES adopt_appliment(appliment_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 领养申请对接操作记录
CREATE TABLE IF NOT EXISTS adopt_handle_operation(
	operation_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	info_id INT UNSIGNED,# 领养对接id
	description TINYTEXT DEFAULT NULL,# 操作描述
	state INT DEFAULT 0,# 当前对接状态，0处理中，1已完成
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(operation_id),
	FOREIGN KEY(info_id) REFERENCES adopt_handle_info(info_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;




# 地区管理者信息
CREATE TABLE IF NOT EXISTS area_organizer(
	area_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	user_id INT UNSIGNED,# 管理者id
	organizer_name VARCHAR(100) NOT NULL,# 组织名称
	address VARCHAR(100) NOT NULL,# 地址
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(area_id),
	FOREIGN KEY(user_id) REFERENCES user_message(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



# 组织成员信息
CREATE TABLE IF NOT EXISTS member_message(
	message_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	user_id INT UNSIGNED ,# 成员人id
	area_id INT UNSIGNED,# 地区id
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   #通过时间
	PRIMARY KEY(message_id),
	FOREIGN KEY(user_id) REFERENCES user_message(user_id),
	FOREIGN KEY(area_id) REFERENCES area_organizer(area_id)	
)ENGINE=INNODB DEFAULT CHARSET=utf8;

# 申请加入
CREATE TABLE IF NOT EXISTS join_appliment(
	appliment_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	user_id INT UNSIGNED,# 申请人id
	area_id INT UNSIGNED,   #地址id
	apply_date DATETIME NOT NULL,# 申请时间
	apply_name VARCHAR(49) NOT NULL,# 姓名
	telephone VARCHAR(16) NOT NULL,   #s手机号
	description TINYTEXT DEFAULT NULL,   #申请描述
	state INT DEFAULT 0, #申请状态，0为为通过，1为通过，2为拒绝
	operator_time DATETIME DEFAULT NULL, #地区组织管理员A操作时间
	operation_state INT DEFAULT NULL,  #操作状态
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(appliment_id),
	FOREIGN KEY(user_id) REFERENCES user_message(user_id),
	FOREIGN KEY(area_id) REFERENCES area_organizer(area_id)	
)ENGINE=INNODB DEFAULT CHARSET=utf8;


# 救助申请
CREATE TABLE IF NOT EXISTS help_appliment(
	appliment_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	user_id INT UNSIGNED, # 救助申请人id
	apply_name VARCHAR(49) NOT NULL, # 姓名
	telephone VARCHAR(16) NOT NULL,# 手机号码
	area_id INT UNSIGNED,  #区域地址
	description TINYTEXT DEFAULT NULL,# 申请描述
	apply_date DATETIME NOT NULL,# 申请时间
	state INT DEFAULT 0,# 申请状态，0为待审核，1为处理中，2为已完成
	pet_name VARCHAR(33) DEFAULT NULL,# 动物姓名
	pet_type INT NOT NULL,# 动物类型，0为猫，1为狗，2为小鸟等
	gender INT NOT NULL,# 性别，0为男，1为女，2为未知
	age VARCHAR(32) DEFAULT NULL,   #年龄
	steriled BOOLEAN NOT NULL,   #绝育情况，
	vaccinate BOOLEAN NOT NULL,  #疫苗情况
	pictures VARCHAR(255) DEFAULT NULL,  #图片集
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(appliment_id),
	FOREIGN KEY (user_id) REFERENCES user_message(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



# 救助对接
CREATE TABLE IF NOT EXISTS help_handle_info(
	info_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	appliment_id INT UNSIGNED,# 申请信息id
	handle_id INT UNSIGNED,# 处理对接人id
	state INT DEFAULT 0,# 当前对接状态,0为处理中，1为已完成
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(info_id),
	FOREIGN KEY(appliment_id) REFERENCES help_appliment(appliment_id),
	FOREIGN KEY(handle_id) REFERENCES user_message(user_id)	
)ENGINE=INNODB DEFAULT CHARSET=utf8;



# 救助对接操作记录
CREATE TABLE IF NOT EXISTS help_handle_operation(
	operatioin_id INT UNSIGNED NOT NULL AUTO_INCREMENT,# 主键
	info_id INT UNSIGNED,# 救助对接信息
	description TINYTEXT DEFAULT NULL,# 操作描述
	state INT DEFAULT 0,# 当前对接状态,0为处理中，1为已完成
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(operatioin_id),
	FOREIGN KEY(info_id) REFERENCES help_handle_info(info_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;




