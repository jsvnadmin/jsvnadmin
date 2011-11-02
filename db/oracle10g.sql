/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2011/11/2 8:56:49                            */
/*==============================================================*/


alter table "pj_gr"
   drop constraint FK_PJ_GR_RELATIONS_PJ;

alter table "pj_gr_auth"
   drop constraint FK_PJ_GR_AU_REFERENCE_PJ_GR;

alter table "pj_gr_usr"
   drop constraint FK_PJ_GR_US_REFERENCE_PJ_GR;

alter table "pj_gr_usr"
   drop constraint FK_PJ_GR_US_REFERENCE_USR;

alter table "pj_usr"
   drop constraint FK_PJ_USR_REFERENCE_PJ;

alter table "pj_usr"
   drop constraint FK_PJ_USR_REFERENCE_USR;

alter table "pj_usr_auth"
   drop constraint FK_PJ_USR_A_REFERENCE_PJ;

alter table "pj_usr_auth"
   drop constraint FK_PJ_USR_A_REFERENCE_USR;

drop table "i18n" cascade constraints;

drop table "pj" cascade constraints;

drop table "pj_gr" cascade constraints;

drop table "pj_gr_auth" cascade constraints;

drop table "pj_gr_usr" cascade constraints;

drop table "pj_usr" cascade constraints;

drop table "pj_usr_auth" cascade constraints;

drop table "usr" cascade constraints;

/*==============================================================*/
/* Table: "i18n"                                                */
/*==============================================================*/
create table "i18n"  (
   "lang"               VARCHAR2(20)                    not null,
   "id"                 VARCHAR2(255)                   not null,
   "lbl"                VARCHAR2(255)                   not null,
   constraint PK_I18N primary key ("lang", "id")
);

/*==============================================================*/
/* Table: "pj"                                                  */
/*==============================================================*/
create table "pj"  (
   "pj"                 VARCHAR2(50)                    not null,
   "path"               VARCHAR2(255)                   not null,
   "url"                VARCHAR2(255)                   not null,
   "type"               VARCHAR2(10)                    not null,
   "des"                VARCHAR2(255),
   constraint PK_PJ primary key ("pj")
);

/*==============================================================*/
/* Table: "pj_gr"                                               */
/*==============================================================*/
create table "pj_gr"  (
   "pj"                 VARCHAR2(50)                    not null,
   "gr"                 VARCHAR2(50)                    not null,
   "des"                VARCHAR2(100),
   constraint PK_PJ_GR primary key ("pj", "gr")
);

/*==============================================================*/
/* Table: "pj_gr_auth"                                          */
/*==============================================================*/
create table "pj_gr_auth"  (
   "pj"                 VARCHAR2(50)                    not null,
   "gr"                 VARCHAR2(50)                    not null,
   "res"                VARCHAR2(255)                   not null,
   "rw"                 VARCHAR2(10)                    not null,
   constraint PK_PJ_GR_AUTH primary key ("pj", "res", "gr")
);

/*==============================================================*/
/* Table: "pj_gr_usr"                                           */
/*==============================================================*/
create table "pj_gr_usr"  (
   "pj"                 VARCHAR2(50)                    not null,
   "gr"                 VARCHAR2(50)                    not null,
   "usr"                VARCHAR2(50)                    not null,
   constraint PK_PJ_GR_USR primary key ("pj", "usr", "gr")
);

/*==============================================================*/
/* Table: "pj_usr"                                              */
/*==============================================================*/
create table "pj_usr"  (
   "pj"                 VARCHAR2(50)                    not null,
   "usr"                VARCHAR2(50)                    not null,
   "psw"                VARCHAR2(50)                    not null,
   constraint PK_PJ_USR primary key ("usr", "pj")
);

/*==============================================================*/
/* Table: "pj_usr_auth"                                         */
/*==============================================================*/
create table "pj_usr_auth"  (
   "pj"                 VARCHAR2(50)                    not null,
   "usr"                VARCHAR2(50)                    not null,
   "res"                VARCHAR2(255)                   not null,
   "rw"                 VARCHAR2(10)                    not null,
   constraint PK_PJ_USR_AUTH primary key ("pj", "res", "usr")
);

/*==============================================================*/
/* Table: "usr"                                                 */
/*==============================================================*/
create table "usr"  (
   "usr"                VARCHAR2(50)                    not null,
   "psw"                VARCHAR2(50)                    not null,
   "role"               VARCHAR2(10),
   constraint PK_USR primary key ("usr")
);

alter table "pj_gr"
   add constraint FK_PJ_GR_RELATIONS_PJ foreign key ("pj")
      references "pj" ("pj");

alter table "pj_gr_auth"
   add constraint FK_PJ_GR_AU_REFERENCE_PJ_GR foreign key ("pj", "gr")
      references "pj_gr" ("pj", "gr");

alter table "pj_gr_usr"
   add constraint FK_PJ_GR_US_REFERENCE_PJ_GR foreign key ("pj", "gr")
      references "pj_gr" ("pj", "gr");

alter table "pj_gr_usr"
   add constraint FK_PJ_GR_US_REFERENCE_USR foreign key ("usr")
      references "usr" ("usr");

alter table "pj_usr"
   add constraint FK_PJ_USR_REFERENCE_PJ foreign key ("pj")
      references "pj" ("pj");

alter table "pj_usr"
   add constraint FK_PJ_USR_REFERENCE_USR foreign key ("usr")
      references "usr" ("usr");

alter table "pj_usr_auth"
   add constraint FK_PJ_USR_A_REFERENCE_PJ foreign key ("pj")
      references "pj" ("pj");

alter table "pj_usr_auth"
   add constraint FK_PJ_USR_A_REFERENCE_USR foreign key ("usr")
      references "usr" ("usr");

