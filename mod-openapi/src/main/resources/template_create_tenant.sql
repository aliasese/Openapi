-- Script to be run at the _tenant call, when module is enabled for a tenant
-- The values 'myuniversity' and 'mymodule' will be replaced with the tenant
-- and module names.

-- create tenant user in db
-- DROP USER IF EXISTS myuniversity;

-- remove access to public schema to all
REVOKE CREATE ON SCHEMA public FROM PUBLIC;

CREATE USER myuniversity_mymodule WITH ENCRYPTED PASSWORD 'myuniversity';
GRANT myuniversity_mymodule TO CURRENT_USER;

-- remove this
-- GRANT ALL PRIVILEGES ON DATABASE postgres TO myuniversity;

-- create table space per tenant
-- CREATE TABLESPACE ts_myuniversity OWNER myuniversity LOCATION '${tablespace_dir}/myuniversity/module_to/module_from';
-- SET default_tablespace = ts_myuniversity;

-- DROP SCHEMA IF EXISTS myuniversity CASCADE;
-- The schema user wil be the schema name since not given
CREATE SCHEMA  IF NOT EXISTS  myuniversity_mymodule AUTHORIZATION myuniversity_mymodule;
CREATE SCHEMA  IF NOT EXISTS  public_mymodule AUTHORIZATION CURRENT_USER;
-- CREATE SCHEMA  IF NOT EXISTS  public_mymodule AUTHORIZATION myuniversity_mymodule;

-- for uuid generator -> gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Set the new schema first so that we dont have to namespace when creating tables
-- add the postgres to the search path so that we can use the pgcrypto extension
-- CREATE TABLE IF NOT EXISTS public_mymodule.mod_config_type (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
-- CREATE TABLE IF NOT EXISTS public_mymodule.mod_configuration (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);

-- CREATE EXTENSION pgcrypto;
-- SELECT gen_random_uuid();
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_openapi (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_clientkey (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_openapidoc (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_tenantapply (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_openapiapply (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_unauthtenants (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.log_operations (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS myuniversity_mymodule.tb_exception (
	"id" uuid DEFAULT gen_random_uuid () NOT NULL,
	"jsonb" jsonb NOT NULL,
	"openapi_id" uuid NOT NULL,
	CONSTRAINT "tb_exception_pkey" PRIMARY KEY ("id"),
	CONSTRAINT "fk_tb_exception_reference_tb_openapi" FOREIGN KEY ("openapi_id") REFERENCES myuniversity_mymodule."tb_openapi" ("id") ON DELETE CASCADE ON UPDATE RESTRICT
) WITH (OIDS = FALSE);

CREATE TABLE IF NOT EXISTS public_mymodule.tb_openapi (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.tb_clientkey (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.tb_openapidoc (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.tb_tenantapply (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.tb_openapiapply (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.tb_unauthtenants (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.log_operations (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), jsonb JSONB NOT NULL);
CREATE TABLE IF NOT EXISTS public_mymodule.tb_exception (
	"id" uuid DEFAULT gen_random_uuid () NOT NULL,
	"jsonb" jsonb NOT NULL,
	"openapi_id" uuid NOT NULL,
	CONSTRAINT "tb_exception_pkey" PRIMARY KEY ("id"),
	CONSTRAINT "fk_tb_exception_reference_tb_openapi" FOREIGN KEY ("openapi_id") REFERENCES public_mymodule."tb_openapi" ("id") ON DELETE CASCADE ON UPDATE RESTRICT
) WITH (OIDS = FALSE);



-- give the user PRIVILEGES after everything is created by script
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA myuniversity_mymodule TO myuniversity_mymodule;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public_mymodule TO CURRENT_USER;
