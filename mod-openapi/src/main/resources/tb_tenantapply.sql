CREATE SEQUENCE "diku_openapi_mod_openapi"."tb_tenantapply_id_seq"
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

DROP TABLE if EXISTS "diku_openapi_mod_openapi"."tb_tenantapply";
CREATE TABLE "diku_openapi_mod_openapi"."tb_tenantapply" (
  "id" int4 DEFAULT nextval('"diku_openapi_mod_openapi".tb_tenantapply_id_seq'::regclass) NOT NULL,
  "jsonb" jsonb NOT NULL,
  CONSTRAINT "tb_tenantapply_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "diku_openapi_mod_openapi"."tb_tenantapply" OWNER TO "diku_openapi_mod_openapi";