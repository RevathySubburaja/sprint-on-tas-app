DROP DATABASE IF EXISTS catalog_db;
create database catalog_db;
grant all privileges on database catalog_db to postgres;
alter database catalog_db owner to postgres;


DROP DATABASE IF EXISTS deployment_db;
create database deployment_db;
grant all privileges on database deployment_db to postgres;
alter database deployment_db owner to postgres;



DROP DATABASE IF EXISTS approval_db;
create database approval_db;
grant all privileges on database approval_db to postgres;
alter database approval_db owner to postgres;


DROP DATABASE IF EXISTS provision_db;
create database provision_db;
grant all privileges on database provision_db to postgres;
alter database provision_db owner to postgres;