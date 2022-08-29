
/*==============================================================*/
/* Table: schema_history                                        */
/*==============================================================*/
create table schema_history (
   sh_id                serial               not null,
   sh_created_at        date                 not null,
   sh_desc              varchar(255)         null,
   sh_schema            varchar(100)         not null,
   sh_status            varchar(25)          null,
   constraint pk_schema_history primary key (sh_id)
);

/*==============================================================*/
/* Index: schema_history_pk                                     */
/*==============================================================*/
create unique index schema_history_pk on schema_history (
sh_id
);

/*==============================================================*/
/* Table: tenants                                               */
/*==============================================================*/
create table tenants (
   tenant_id            serial               not null,
   tenant_name          varchar(50)          not null,
   tenant_address        varchar(100)         null,
   tenant_schema        varchar(100)         null,
   constraint pk_tenants primary key (tenant_id)
);

/*==============================================================*/
/* Index: tenants_pk                                            */
/*==============================================================*/
create unique index tenants_pk on tenants (
tenant_id
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users (
   user_id              serial               not null,
   tenant_id            int4                 null,
   user_name            varchar(25)          not null,
   user_password        varchar(255)         null,
   user_email           varchar(50)          null,
   user_enabled         bool                 null,
   constraint pk_users primary key (user_id)
);

/*==============================================================*/
/* Index: users_pk                                              */
/*==============================================================*/
create unique index users_pk on users (
user_id
);

/*==============================================================*/
/* Index: relationship_1_fk                                     */
/*==============================================================*/
create  index relationship_1_fk on users (
tenant_id
);

alter table users
   add constraint fk_users_relations_tenants foreign key (tenant_id)
      references tenants (tenant_id)
      on delete restrict on update restrict;