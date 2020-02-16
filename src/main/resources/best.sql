/*
用户表
 */
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	username VARCHAR(30) NOT NULL UNIQUE COMMENT '用户名',
	password VARCHAR(255) NOT NULL COMMENT '密码',
    account_non_expired bit(1) NOT NULL COMMENT '账户是否过期,过期无法验证',
    account_non_locked bit(1) NOT NULL COMMENT '指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证',
    credentials_non_expired bit(1) NOT NULL COMMENT '指示是否已过期的用户的凭据(密码),过期的凭据防止认证',
    enabled bit(1) NOT NULL COMMENT '是否被禁用,禁用的用户不能身份验证',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    nickname VARCHAR(30) NULL DEFAULT NULL COMMENT '昵称',
	sex int(11) NULL DEFAULT NULL COMMENT '性别',
    birthday DATE NULL DEFAULT NULL COMMENT '生日',
    avatar VARCHAR(255) NULL DEFAULT NULL COMMENT '头像',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	phone_number VARCHAR(11) NULL DEFAULT NULL COMMENT '手机号码',
	address VARCHAR(255) NULL DEFAULT NULL COMMENT '地址',
	introduction VARCHAR(255) NULL DEFAULT NULL COMMENT '个人介绍',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);


/*
角色表
 */
DROP TABLE IF EXISTS sys_role;

CREATE TABLE sys_role
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NOT NULL COMMENT '名称',
	identification VARCHAR(30) NOT NULL UNIQUE COMMENT '标识名称',
    description VARCHAR(255) COMMENT '描述',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
菜单表
 */
DROP TABLE IF EXISTS sys_route;

CREATE TABLE sys_route
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NOT NULL COMMENT '菜单名称',
    path VARCHAR(255) COMMENT '菜单路由',
    pid BIGINT(20) NULL DEFAULT NULL COMMENT '上级ID',
	redirect VARCHAR(255) COMMENT '重定向路径',
	title VARCHAR(255) COMMENT '显示名称',
	icon VARCHAR(255) COMMENT '图标',
    priority INT(11) NULL DEFAULT NULL COMMENT '排序等级',
    hidden bit(1) NOT NULL DEFAULT 0 COMMENT '菜单是否隐藏',
    always_show bit(1) NOT NULL DEFAULT 0 COMMENT '菜单是否总是显示',
    no_cache bit(1) NOT NULL DEFAULT 0 COMMENT '是否不打开缓存',
    affix bit(1) NOT NULL DEFAULT 0 COMMENT '是否打开固钉',
    breadcrumb bit(1) NOT NULL DEFAULT 1 COMMENT '是否打开面包屑',
    props VARCHAR(255) COMMENT '参数',
    description VARCHAR(255) COMMENT '菜单描述',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
资源表
 */
DROP TABLE IF EXISTS sys_resource;

CREATE TABLE sys_resource
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NOT NULL COMMENT '资源名称',
    path VARCHAR(255) COMMENT '资源路由',
    type INT(11) NOT NULL COMMENT '资源类型',
    pid BIGINT(20) NULL DEFAULT NULL COMMENT '上级ID',
    priority INT(11) NULL DEFAULT NULL COMMENT '排序等级',
	description VARCHAR(255) COMMENT '资源描述',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
用户-角色关系表
 */
DROP TABLE IF EXISTS sys_user_role;

CREATE TABLE sys_user_role
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	user_id BIGINT(20) NOT NULL COMMENT '用户ID',
	role_id BIGINT(20) NOT NULL COMMENT '角色ID',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
角色-菜单关系表
 */
DROP TABLE IF EXISTS sys_role_route;

CREATE TABLE sys_role_route
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	role_id BIGINT(20) NOT NULL COMMENT '角色ID',
	route_id BIGINT(20) NOT NULL COMMENT '权限ID',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
角色-资源关系表
 */
DROP TABLE IF EXISTS sys_role_resource;

CREATE TABLE sys_role_resource
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	role_id BIGINT(20) NOT NULL COMMENT '角色ID',
	resource_id BIGINT(20) NOT NULL COMMENT '权限ID',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
定时任务表
 */
DROP TABLE IF EXISTS sys_dynamic_scheduled_task;

CREATE TABLE sys_dynamic_scheduled_task
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NOT NULL COMMENT '任务名称',
    cron VARCHAR(255) COMMENT 'cron表达式',
    bean_name VARCHAR(255) COMMENT 'bean名称',
    method_info VARCHAR(255) COMMENT '方法名',
    params VARCHAR(255) COMMENT '参数',
    open bit(1) NOT NULL COMMENT '开启状态',
	description VARCHAR(255) COMMENT '描述',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
部门表
 */
DROP TABLE IF EXISTS sys_department;

CREATE TABLE sys_department
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NOT NULL COMMENT '部门名称',
    pid BIGINT(20) NOT NULL COMMENT '上级ID',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);

/*
字典表
 */
DROP TABLE IF EXISTS sys_dictionary;

CREATE TABLE sys_dictionary
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	name VARCHAR(30) NOT NULL COMMENT '名称',
    pid BIGINT(20) NOT NULL COMMENT '上级ID',
	create_by BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    created_date datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by BIGINT(20) NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (id)
);


/*
初始数据插入
 */

/**
用户表初始数据
*/
INSERT INTO `best`.`sys_user`(`id`, `username`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`, `name`, `nickname`, `sex`, `birthday`, `avatar`, `email`, `phone_number`, `address`, `introduction`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (1, 'admin', '$2a$10$ZHloNREZMCnmeSqGlPL4tudSt4QdR4JnFwODJnVsXoWoxAkNMaqda', b'1', b'1', b'1', b'1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

/**
角色表基础数据
 */
INSERT INTO `best`.`sys_role`(`id`, `name`, `identification`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (1, '开发者', 'developer', '开发者', NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role`(`id`, `name`, `identification`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (2, '管理员', 'admin', '管理员', NULL, NULL, NULL, NULL);

/**
用户-角色关联表基础数据
 */
INSERT INTO `best`.`sys_user_role`(`id`, `user_id`, `role_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (1, 1, 1, NULL, NULL, NULL, NULL);

/**
菜单表基础数据
 */
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (1, 'Permission', '/permission', NULL, '/permission/page', '权限控制', 'lock', 1, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (2, 'PagePermission', 'page', 1, NULL, '角色切换', NULL, 1, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (3, 'DirectivePermission', 'directive', 1, NULL, '权限指令', NULL, 2, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (4, 'RoutePermission', 'route', 1, NULL, '路由管理', NULL, 3, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (5, 'ResourcePermission', 'resource', 1, NULL, '资源管理', NULL, 4, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (6, 'RolePermission', 'role', 1, NULL, '角色权限', NULL, 5, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (7, 'SystemConfig', '/config', null, '/config/task', '系统配置', 'lock', 2, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (8, 'ScheduledTask', 'task', 7, NULL, '定时任务', NULL, 1, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (9, 'Iframe', '/iframe', NULL, 'noRedirect', '开发者工具', 'link', 99, b'0', b'0', b'0', b'0', b'1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (10, 'Druid', 'druid', 9, NULL, 'Druid', 'link', 1, b'0', b'0', b'0', b'0', b'1', '{ \"redirect\": \"http://localhost:8080/best/druid\" }', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_route`(`id`, `name`, `path`, `pid`, `redirect`, `title`, `icon`, `priority`, `hidden`, `always_show`, `no_cache`, `affix`, `breadcrumb`, `props`, `description`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (11, 'Swagger', 'swagger', 9, NULL, 'Swagger', 'link', 2, b'0', b'0', b'0', b'0', b'1', '{ \"redirect\": \"http://localhost:8080/best/swagger-ui.html\" }', NULL, NULL, NULL, NULL, NULL);

/**
角色-菜单关联表基础数据
 */
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (1, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (2, 1, 2, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (3, 1, 3, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (4, 1, 4, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (5, 1, 5, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (6, 1, 6, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (7, 1, 7, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (8, 1, 8, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (9, 1, 9, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (10, 1, 10, NULL, NULL, NULL, NULL);
INSERT INTO `best`.`sys_role_route`(`id`, `role_id`, `route_id`, `create_by`, `created_date`, `last_modified_by`, `last_modified_date`) VALUES (11, 1, 11, NULL, NULL, NULL, NULL);
