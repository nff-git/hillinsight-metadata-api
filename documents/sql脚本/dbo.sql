/*
 Navicat Premium Data Transfer

 Source Server         : 开发环境-10.29.4.122
 Source Server Type    : SQL Server
 Source Server Version : 15002000
 Source Host           : 10.29.4.122:1433
 Source Catalog        : metadata_db_test
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 15002000
 File Encoding         : 65001

 Date: 12/01/2021 11:34:52
*/


-- ----------------------------
-- Table structure for t_metadata_developer
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_metadata_developer]') AND type IN ('U'))
	DROP TABLE [dbo].[t_metadata_developer]
GO

CREATE TABLE [dbo].[t_metadata_developer] (
  [id] int  IDENTITY NOT NULL,
  [developer_id] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [developer_name] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [group_id] int  NULL
)
GO

ALTER TABLE [dbo].[t_metadata_developer] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'开发者id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_developer',
'COLUMN', N'developer_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'开发者名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_developer',
'COLUMN', N'developer_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分组id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_developer',
'COLUMN', N'group_id'
GO


-- ----------------------------
-- Table structure for t_metadata_dict
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_metadata_dict]') AND type IN ('U'))
	DROP TABLE [dbo].[t_metadata_dict]
GO

CREATE TABLE [dbo].[t_metadata_dict] (
  [id] int  IDENTITY(1,1) NOT NULL,
  [code] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [dict_path] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [parent_id] int  NULL,
  [remark] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [order_num] int  NULL
)
GO

ALTER TABLE [dbo].[t_metadata_dict] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典值code',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典翻译名',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典路径',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'dict_path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'父级id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict',
'COLUMN', N'order_num'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元数据字典表',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_dict'
GO


-- ----------------------------
-- Table structure for t_metadata_field_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_metadata_field_info]') AND type IN ('U'))
	DROP TABLE [dbo].[t_metadata_field_info]
GO

CREATE TABLE [dbo].[t_metadata_field_info] (
  [id] int  IDENTITY NOT NULL,
  [field_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_show_cn] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_show_en] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_type_code] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_paraphrase_cn] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_paraphrase_en] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [data_owner_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [data_owner_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [filling_explanation] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [status] int  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL,
  [object_id] int  NULL,
  [field_type_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[t_metadata_field_info] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段名',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段显示名中文',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_show_cn'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段显示名英文',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_show_en'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段类型',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_type_code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段释义中文',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_paraphrase_cn'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段释义英文',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_paraphrase_en'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据所有者id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'data_owner_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据所有者名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'data_owner_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'填写说明',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'filling_explanation'
GO

EXEC sp_addextendedproperty
'MS_Description', N'1使用 0禁用',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元数据对象表id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'object_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段类型名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info',
'COLUMN', N'field_type_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元数据字段信息表',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_field_info'
GO


-- ----------------------------
-- Table structure for t_metadata_group
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_metadata_group]') AND type IN ('U'))
	DROP TABLE [dbo].[t_metadata_group]
GO

CREATE TABLE [dbo].[t_metadata_group] (
  [id] int  IDENTITY NOT NULL,
  [page_group] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [source_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [source_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [describe] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [status] int  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL
)
GO

ALTER TABLE [dbo].[t_metadata_group] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面分组',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_group',
'COLUMN', N'page_group'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_group',
'COLUMN', N'source_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_group',
'COLUMN', N'source_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'描述',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_group',
'COLUMN', N'describe'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段状态1 启用 0禁用',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_group',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面配置组表',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_group'
GO


-- ----------------------------
-- Table structure for t_metadata_object_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_metadata_object_info]') AND type IN ('U'))
	DROP TABLE [dbo].[t_metadata_object_info]
GO

CREATE TABLE [dbo].[t_metadata_object_info] (
  [show_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [object_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [describe] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL,
  [status] int  NULL,
  [parent_id] int  NULL,
  [id] int  IDENTITY NOT NULL
)
GO

ALTER TABLE [dbo].[t_metadata_object_info] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'对象显示名',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'show_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对象名',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'object_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'描述',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'describe'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建人id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'creator_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建人名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'creator_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'creator_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改人id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'updator_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改人名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'updator_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'1使用 0禁用',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'父级id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元数据对象表',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_object_info'
GO


-- ----------------------------
-- Table structure for t_metadata_page
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_metadata_page]') AND type IN ('U'))
	DROP TABLE [dbo].[t_metadata_page]
GO

CREATE TABLE [dbo].[t_metadata_page] (
  [id] int  IDENTITY NOT NULL,
  [page_key] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [page_title] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [page_explain] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [group_id] int  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL,
  [source_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [source_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [status] int  NULL
)
GO

ALTER TABLE [dbo].[t_metadata_page] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'pagekey',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'page_key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面标题',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'page_title'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面说明',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'page_explain'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分组id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'group_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源id',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'source_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源名称',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'source_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'1使用0删除',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面配置页表',
'SCHEMA', N'dbo',
'TABLE', N't_metadata_page'
GO


-- ----------------------------
-- Table structure for t_page_template
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_page_template]') AND type IN ('U'))
	DROP TABLE [dbo].[t_page_template]
GO

CREATE TABLE [dbo].[t_page_template] (
  [id] int  IDENTITY NOT NULL,
  [third_obj_id] int  NULL,
  [third_obj_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [third_field_id] int  NULL,
  [third_field_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [page_id] int  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL,
  [is_custom_field] int  NULL,
  [field_paraphrase_cn] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_paraphrase_en] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[t_page_template] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务对象id',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'third_obj_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务对象名称',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'third_obj_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务字段id',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'third_field_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务字段名称',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'third_field_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面配置主键id',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'page_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否为自定义字段 1是 0否',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'is_custom_field'
GO

EXEC sp_addextendedproperty
'MS_Description', N'自定义字段中文',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'field_paraphrase_cn'
GO

EXEC sp_addextendedproperty
'MS_Description', N'英文',
'SCHEMA', N'dbo',
'TABLE', N't_page_template',
'COLUMN', N'field_paraphrase_en'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面配置模板表',
'SCHEMA', N'dbo',
'TABLE', N't_page_template'
GO


-- ----------------------------
-- Table structure for t_third_field_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_third_field_info]') AND type IN ('U'))
	DROP TABLE [dbo].[t_third_field_info]
GO

CREATE TABLE [dbo].[t_third_field_info] (
  [id] int  IDENTITY NOT NULL,
  [field_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [source_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [source_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [field_type_code] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [show_name_cn] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [show_name_en] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [metadata_field_id] int  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL,
  [status] int  NULL,
  [third_object_id] int  NULL,
  [field_type_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [is_extension] int  NULL,
  [extension_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[t_third_field_info] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务方字段名称-相当于id',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'field_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源id',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'source_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源名称',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'source_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段类型 code值',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'field_type_code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段显示名cn',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'show_name_cn'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段显示名en',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'show_name_en'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元数据字段表id',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'metadata_field_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'1启用 0禁用',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务对象id',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'third_object_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段类型名称',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'field_type_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'1true  0false',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'is_extension'
GO

EXEC sp_addextendedproperty
'MS_Description', N'例如：extensio_1',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info',
'COLUMN', N'extension_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务对象字段信息表',
'SCHEMA', N'dbo',
'TABLE', N't_third_field_info'
GO


-- ----------------------------
-- Table structure for t_third_object_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[t_third_object_info]') AND type IN ('U'))
	DROP TABLE [dbo].[t_third_object_info]
GO

CREATE TABLE [dbo].[t_third_object_info] (
  [id] int  IDENTITY NOT NULL,
  [object_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [show_name_cn] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [show_name_en] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [parent_id] int  NULL,
  [describe] varchar(1000) COLLATE Chinese_PRC_CI_AS  NULL,
  [metadata_object_id] int  NULL,
  [creator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [creator_time] datetime  NULL,
  [updator_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [updator_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [update_time] datetime  NULL,
  [status] int  NULL,
  [source_id] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [source_name] varchar(100) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[t_third_object_info] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'对象名（业务对象id）',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'object_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对象显示名中文',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'show_name_cn'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对象显示名英文',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'show_name_en'
GO

EXEC sp_addextendedproperty
'MS_Description', N'父级id',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'描述',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'describe'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元数据对象id',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'metadata_object_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'1启用0禁用',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源id',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'source_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统来源名称',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info',
'COLUMN', N'source_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'第三方业务对象表',
'SCHEMA', N'dbo',
'TABLE', N't_third_object_info'
GO


-- ----------------------------
-- Auto increment value for t_metadata_developer
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_metadata_developer]', RESEED, 56)
GO


-- ----------------------------
-- Primary Key structure for table t_metadata_developer
-- ----------------------------
ALTER TABLE [dbo].[t_metadata_developer] ADD CONSTRAINT [PK__t_metada__3213E83F7AF86BBF] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_metadata_dict
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_metadata_dict]', RESEED, 18)
GO


-- ----------------------------
-- Primary Key structure for table t_metadata_dict
-- ----------------------------
ALTER TABLE [dbo].[t_metadata_dict] ADD CONSTRAINT [PK__t_metada__3213E83FAF86121C] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_metadata_field_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_metadata_field_info]', RESEED, 605)
GO


-- ----------------------------
-- Primary Key structure for table t_metadata_field_info
-- ----------------------------
ALTER TABLE [dbo].[t_metadata_field_info] ADD CONSTRAINT [PK__t_metada__3213E83F7B26E14A] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_metadata_group
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_metadata_group]', RESEED, 20)
GO


-- ----------------------------
-- Primary Key structure for table t_metadata_group
-- ----------------------------
ALTER TABLE [dbo].[t_metadata_group] ADD CONSTRAINT [PK__t_metada__3213E83F18B18518] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_metadata_object_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_metadata_object_info]', RESEED, 28)
GO


-- ----------------------------
-- Primary Key structure for table t_metadata_object_info
-- ----------------------------
ALTER TABLE [dbo].[t_metadata_object_info] ADD CONSTRAINT [PK__t_metada__3213E83F20D477FF] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_metadata_page
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_metadata_page]', RESEED, 39)
GO


-- ----------------------------
-- Primary Key structure for table t_metadata_page
-- ----------------------------
ALTER TABLE [dbo].[t_metadata_page] ADD CONSTRAINT [PK__t_metada__3213E83FEAD7D377] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_page_template
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_page_template]', RESEED, 247)
GO


-- ----------------------------
-- Primary Key structure for table t_page_template
-- ----------------------------
ALTER TABLE [dbo].[t_page_template] ADD CONSTRAINT [PK__t_page_t__3213E83F28381F35] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_third_field_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_third_field_info]', RESEED, 118)
GO


-- ----------------------------
-- Primary Key structure for table t_third_field_info
-- ----------------------------
ALTER TABLE [dbo].[t_third_field_info] ADD CONSTRAINT [PK__t_third___3213E83FECB5C475] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for t_third_object_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[t_third_object_info]', RESEED, 26)
GO


-- ----------------------------
-- Primary Key structure for table t_third_object_info
-- ----------------------------
ALTER TABLE [dbo].[t_third_object_info] ADD CONSTRAINT [PK__t_third___3213E83F7C3E5E5B] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

