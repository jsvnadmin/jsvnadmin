/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2013/1/16 14:32:28                           */
/*==============================================================*/


if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_gr') and o.name = 'FK_PJ_GR_RELATIONS_PJ')
alter table pj_gr
   drop constraint FK_PJ_GR_RELATIONS_PJ
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_gr_auth') and o.name = 'FK_PJ_GR_AU_REFERENCE_PJ_GR')
alter table pj_gr_auth
   drop constraint FK_PJ_GR_AU_REFERENCE_PJ_GR
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_gr_usr') and o.name = 'FK_PJ_GR_US_REFERENCE_PJ_GR')
alter table pj_gr_usr
   drop constraint FK_PJ_GR_US_REFERENCE_PJ_GR
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_gr_usr') and o.name = 'FK_PJ_GR_US_REFERENCE_USR')
alter table pj_gr_usr
   drop constraint FK_PJ_GR_US_REFERENCE_USR
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_usr') and o.name = 'FK_PJ_USR_REFERENCE_PJ')
alter table pj_usr
   drop constraint FK_PJ_USR_REFERENCE_PJ
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_usr') and o.name = 'FK_PJ_USR_REFERENCE_USR')
alter table pj_usr
   drop constraint FK_PJ_USR_REFERENCE_USR
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_usr_auth') and o.name = 'FK_PJ_USR_A_REFERENCE_PJ')
alter table pj_usr_auth
   drop constraint FK_PJ_USR_A_REFERENCE_PJ
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('pj_usr_auth') and o.name = 'FK_PJ_USR_A_REFERENCE_USR')
alter table pj_usr_auth
   drop constraint FK_PJ_USR_A_REFERENCE_USR
go

/*==============================================================*/
/* Table: i18n                                                  */
/*==============================================================*/
create table i18n (
   lang                 varchar(20)          not null,
   id                   varchar(200)         not null,
   lbl                  varchar(200)         not null,
   constraint PK_I18N primary key nonclustered (lang, id)
)
go

/*==============================================================*/
/* Table: pj                                                    */
/*==============================================================*/
create table pj (
   pj                   varchar(50)          not null,
   path                 varchar(200)         not null,
   url                  varchar(200)         not null,
   type                 varchar(10)          not null,
   des                  varchar(200)         null,
   constraint PK_PJ primary key nonclustered (pj)
)
go

/*==============================================================*/
/* Table: pj_gr                                                 */
/*==============================================================*/
create table pj_gr (
   pj                   varchar(50)          not null,
   gr                   varchar(50)          not null,
   des                  varchar(100)         null,
   constraint PK_PJ_GR primary key nonclustered (pj, gr)
)
go

/*==============================================================*/
/* Table: pj_gr_auth                                            */
/*==============================================================*/
create table pj_gr_auth (
   pj                   varchar(50)          not null,
   gr                   varchar(50)          not null,
   res                  varchar(200)         not null,
   rw                   varchar(10)          null,
   constraint PK_PJ_GR_AUTH primary key nonclustered (pj, res, gr)
)
go

/*==============================================================*/
/* Table: pj_gr_usr                                             */
/*==============================================================*/
create table pj_gr_usr (
   pj                   varchar(50)          not null,
   gr                   varchar(50)          not null,
   usr                  varchar(50)          not null,
   constraint PK_PJ_GR_USR primary key nonclustered (pj, usr, gr)
)
go

/*==============================================================*/
/* Table: pj_usr                                                */
/*==============================================================*/
create table pj_usr (
   pj                   varchar(50)          not null,
   usr                  varchar(50)          not null,
   psw                  varchar(50)          not null,
   constraint PK_PJ_USR primary key nonclustered (usr, pj)
)
go

/*==============================================================*/
/* Table: pj_usr_auth                                           */
/*==============================================================*/
create table pj_usr_auth (
   pj                   varchar(50)          not null,
   usr                  varchar(50)          not null,
   res                  varchar(200)         not null,
   rw                   varchar(10)          null,
   constraint PK_PJ_USR_AUTH primary key nonclustered (pj, res, usr)
)
go

/*==============================================================*/
/* Table: usr                                                   */
/*==============================================================*/
create table usr (
   usr                  varchar(50)          not null,
   psw                  varchar(50)          not null,
   name                 varchar(50)          null,
   role                 varchar(10)          null,
   constraint PK_USR primary key nonclustered (usr)
)
go

alter table pj_gr
   add constraint FK_PJ_GR_RELATIONS_PJ foreign key (pj)
      references pj (pj)
go

alter table pj_gr_auth
   add constraint FK_PJ_GR_AU_REFERENCE_PJ_GR foreign key (pj, gr)
      references pj_gr (pj, gr)
go

alter table pj_gr_usr
   add constraint FK_PJ_GR_US_REFERENCE_PJ_GR foreign key (pj, gr)
      references pj_gr (pj, gr)
go

alter table pj_gr_usr
   add constraint FK_PJ_GR_US_REFERENCE_USR foreign key (usr)
      references usr (usr)
go

alter table pj_usr
   add constraint FK_PJ_USR_REFERENCE_PJ foreign key (pj)
      references pj (pj)
go

alter table pj_usr
   add constraint FK_PJ_USR_REFERENCE_USR foreign key (usr)
      references usr (usr)
go

alter table pj_usr_auth
   add constraint FK_PJ_USR_A_REFERENCE_PJ foreign key (pj)
      references pj (pj)
go

alter table pj_usr_auth
   add constraint FK_PJ_USR_A_REFERENCE_USR foreign key (usr)
      references usr (usr)
go

