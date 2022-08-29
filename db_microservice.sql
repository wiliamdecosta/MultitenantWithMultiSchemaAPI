--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pt-adaro; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA "pt-adaro";


ALTER SCHEMA "pt-adaro" OWNER TO postgres;

--
-- Name: pt-gudang-garam; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA "pt-gudang-garam";


ALTER SCHEMA "pt-gudang-garam" OWNER TO postgres;

--
-- Name: p_create_new_schema(character varying, character varying, character varying, character varying, character varying, character varying, boolean); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.p_create_new_schema(IN in_tenant_name character varying, IN in_tenant_address character varying, IN in_tenant_schema character varying, IN in_user_name character varying, IN in_user_password character varying, IN in_user_email character varying, IN in_user_enabled boolean, OUT out_message character varying, OUT out_success boolean, OUT out_create_user_success boolean, OUT out_create_tenant_success boolean)
    LANGUAGE plpgsql
    AS $_$ 
 DECLARE
	ln_count_schema integer;
	ln_tenant_id integer;
	ln_count_user integer;
  BEGIN
    EXECUTE FORMAT('SET search_path TO public;');
	
	SELECT count(1) into ln_count_schema FROM information_schema.schemata 
	WHERE schema_name = in_tenant_schema;
	
	--jika sudah ada schema, maka hanya tambahkan user
	IF(ln_count_schema >= 1) THEN
		
		-- out message
		out_message := 'pembuatan schema gagal. schema ' || in_tenant_schema || ' sudah ada.';
		out_success := false;
		
		-- cek if user exist
		SELECT count(1) into ln_count_user FROM users
		WHERE user_name = in_user_name OR user_email = in_user_email;
		
		IF(ln_count_user >= 1) THEN
			out_message := out_message || ' user sudah ada.';
			out_create_user_success := false;
			out_create_tenant_success := false;
		ELSE
			SELECT tenant_id INTO ln_tenant_id 
			FROM tenants
			WHERE tenant_schema = in_tenant_schema;
			
			IF(ln_tenant_id is null) THEN
				INSERT into tenants(tenant_name, tenant_schema,tenant_address)
				values(in_tenant_name,in_tenant_schema,in_tenant_address)
				returning tenant_id into ln_tenant_id;
				
				out_message := out_message || ' tenant berhasil dibuat.';
				out_create_tenant_success := true;
			ELSE
				out_create_tenant_success := false;
			END IF;
			
			--add new user
			INSERT INTO users(tenant_id, user_name, user_password, user_email, user_enabled)
			VALUES(ln_tenant_id, in_user_name, in_user_password, in_user_email, in_user_enabled);
			
			out_message := out_message || ' user berhasil dibuat.';
			out_create_user_success := true;
			
		END IF;
		
		
		-- insert into schema history
		INSERT INTO schema_history(sh_created_at, sh_desc, sh_schema, sh_status)
		VALUES(now(), out_message, in_tenant_schema, 'Failed');
		
	ELSE 
			
		EXECUTE FORMAT('CREATE SCHEMA IF NOT EXISTS %I;', $3);
		EXECUTE FORMAT('SET search_path TO %I;', $3);
			
		create table if not exists employee (
		   employee_id          serial               not null,
		   employee_name        varchar(50)          not null,
		   employee_nik         varchar(25)          not null,
		   employee_address     text                 null,
		   constraint pk_employee primary key (employee_id)
		);

		create unique index if not exists employee_pk on employee (
			employee_id
		);
		
		--change to public first
		EXECUTE FORMAT('SET search_path TO public;');
		
		out_message := 'schema dan table template berhasil dibuat.';
		out_success := true;
		
		
		-- cek if user exist
		SELECT count(1) into ln_count_user FROM users
		WHERE user_name = in_user_name OR user_email = in_user_email;
		
		IF(ln_count_user >= 1) THEN
			out_message := out_message || ' user sudah ada.';
			out_create_user_success := false;
			out_create_tenant_success := false;
		ELSE
			SELECT tenant_id INTO ln_tenant_id 
			FROM tenants
			WHERE tenant_schema = in_tenant_schema;
			
			IF(ln_tenant_id is null) THEN
				INSERT into tenants(tenant_name, tenant_schema,tenant_address)
				values(in_tenant_name,in_tenant_schema,in_tenant_address)
				returning tenant_id into ln_tenant_id;
				
				out_message := out_message || ' tenant berhasil dibuat.';
				out_create_tenant_success := true;
			ELSE
				out_create_tenant_success := false;
			END IF;
			
			--add new user
			INSERT INTO users(tenant_id, user_name, user_password, user_email, user_enabled)
			VALUES(ln_tenant_id, in_user_name, in_user_password, in_user_email, in_user_enabled);
			
			out_message := out_message || ' user berhasil dibuat.';
			out_create_user_success := true;
		END IF;
		
		
		INSERT INTO schema_history(sh_created_at, sh_desc, sh_schema, sh_status)
		VALUES(now(), out_message, in_tenant_schema, 'Success');
		
	END IF;
end;$_$;


ALTER PROCEDURE public.p_create_new_schema(IN in_tenant_name character varying, IN in_tenant_address character varying, IN in_tenant_schema character varying, IN in_user_name character varying, IN in_user_password character varying, IN in_user_email character varying, IN in_user_enabled boolean, OUT out_message character varying, OUT out_success boolean, OUT out_create_user_success boolean, OUT out_create_tenant_success boolean) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: employee; Type: TABLE; Schema: pt-adaro; Owner: postgres
--

CREATE TABLE "pt-adaro".employee (
    employee_id integer NOT NULL,
    employee_name character varying(50) NOT NULL,
    employee_nik character varying(25) NOT NULL,
    employee_address text
);


ALTER TABLE "pt-adaro".employee OWNER TO postgres;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE; Schema: pt-adaro; Owner: postgres
--

CREATE SEQUENCE "pt-adaro".employee_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "pt-adaro".employee_employee_id_seq OWNER TO postgres;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: pt-adaro; Owner: postgres
--

ALTER SEQUENCE "pt-adaro".employee_employee_id_seq OWNED BY "pt-adaro".employee.employee_id;


--
-- Name: employee; Type: TABLE; Schema: pt-gudang-garam; Owner: postgres
--

CREATE TABLE "pt-gudang-garam".employee (
    employee_id integer NOT NULL,
    employee_name character varying(50) NOT NULL,
    employee_nik character varying(25) NOT NULL,
    employee_address text
);


ALTER TABLE "pt-gudang-garam".employee OWNER TO postgres;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE; Schema: pt-gudang-garam; Owner: postgres
--

CREATE SEQUENCE "pt-gudang-garam".employee_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "pt-gudang-garam".employee_employee_id_seq OWNER TO postgres;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: pt-gudang-garam; Owner: postgres
--

ALTER SEQUENCE "pt-gudang-garam".employee_employee_id_seq OWNED BY "pt-gudang-garam".employee.employee_id;


--
-- Name: datasourceconfig; Type: TABLE; Schema: public; Owner: justclick
--

CREATE TABLE public.datasourceconfig (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    driverclassname character varying(100) NOT NULL,
    url character varying(500) NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    initialize boolean
);


ALTER TABLE public.datasourceconfig OWNER TO justclick;

--
-- Name: datasourceconfig_id_seq; Type: SEQUENCE; Schema: public; Owner: justclick
--

CREATE SEQUENCE public.datasourceconfig_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.datasourceconfig_id_seq OWNER TO justclick;

--
-- Name: datasourceconfig_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justclick
--

ALTER SEQUENCE public.datasourceconfig_id_seq OWNED BY public.datasourceconfig.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: justclick
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO justclick;

--
-- Name: schema_history; Type: TABLE; Schema: public; Owner: justclick
--

CREATE TABLE public.schema_history (
    sh_id integer NOT NULL,
    sh_created_at date NOT NULL,
    sh_desc character varying(255),
    sh_schema character varying(100) NOT NULL,
    sh_status character varying(25)
);


ALTER TABLE public.schema_history OWNER TO justclick;

--
-- Name: schema_history_sh_id_seq; Type: SEQUENCE; Schema: public; Owner: justclick
--

CREATE SEQUENCE public.schema_history_sh_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.schema_history_sh_id_seq OWNER TO justclick;

--
-- Name: schema_history_sh_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justclick
--

ALTER SEQUENCE public.schema_history_sh_id_seq OWNED BY public.schema_history.sh_id;


--
-- Name: tenants; Type: TABLE; Schema: public; Owner: justclick
--

CREATE TABLE public.tenants (
    tenant_id integer NOT NULL,
    tenant_name character varying(50) NOT NULL,
    tenant_address character varying(100),
    tenant_schema character varying(100)
);


ALTER TABLE public.tenants OWNER TO justclick;

--
-- Name: tenants_tenant_id_seq; Type: SEQUENCE; Schema: public; Owner: justclick
--

CREATE SEQUENCE public.tenants_tenant_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tenants_tenant_id_seq OWNER TO justclick;

--
-- Name: tenants_tenant_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justclick
--

ALTER SEQUENCE public.tenants_tenant_id_seq OWNED BY public.tenants.tenant_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: justclick
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    tenant_id integer,
    user_name character varying(25) NOT NULL,
    user_password character varying(255),
    user_email character varying(50),
    user_enabled boolean
);


ALTER TABLE public.users OWNER TO justclick;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: justclick
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO justclick;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justclick
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- Name: employee employee_id; Type: DEFAULT; Schema: pt-adaro; Owner: postgres
--

ALTER TABLE ONLY "pt-adaro".employee ALTER COLUMN employee_id SET DEFAULT nextval('"pt-adaro".employee_employee_id_seq'::regclass);


--
-- Name: employee employee_id; Type: DEFAULT; Schema: pt-gudang-garam; Owner: postgres
--

ALTER TABLE ONLY "pt-gudang-garam".employee ALTER COLUMN employee_id SET DEFAULT nextval('"pt-gudang-garam".employee_employee_id_seq'::regclass);


--
-- Name: datasourceconfig id; Type: DEFAULT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.datasourceconfig ALTER COLUMN id SET DEFAULT nextval('public.datasourceconfig_id_seq'::regclass);


--
-- Name: schema_history sh_id; Type: DEFAULT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.schema_history ALTER COLUMN sh_id SET DEFAULT nextval('public.schema_history_sh_id_seq'::regclass);


--
-- Name: tenants tenant_id; Type: DEFAULT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.tenants ALTER COLUMN tenant_id SET DEFAULT nextval('public.tenants_tenant_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- Data for Name: employee; Type: TABLE DATA; Schema: pt-adaro; Owner: postgres
--

COPY "pt-adaro".employee (employee_id, employee_name, employee_nik, employee_address) FROM stdin;
1	Budi Susanto	82837217	Jakarta
\.


--
-- Data for Name: employee; Type: TABLE DATA; Schema: pt-gudang-garam; Owner: postgres
--

COPY "pt-gudang-garam".employee (employee_id, employee_name, employee_nik, employee_address) FROM stdin;
1	Angga Gemilang	82837208	Bandung
\.


--
-- Data for Name: datasourceconfig; Type: TABLE DATA; Schema: public; Owner: justclick
--

COPY public.datasourceconfig (id, name, driverclassname, url, username, password, initialize) FROM stdin;
1	pt-adaro	org.postgresql.Driver	jdbc:postgresql://localhost:5433/db_microservice?escapeSyntaxCallMode=callIfNoReturn&currentSchema=pt-adaro	justclick	justclick123	t
2	pt-gudang-garam	org.postgresql.Driver	jdbc:postgresql://localhost:5433/db_microservice?escapeSyntaxCallMode=callIfNoReturn&currentSchema=pt-gudang-garam	justclick	justclick123	t
4	public	org.postgresql.Driver	jdbc:postgresql://localhost:5433/db_microservice?escapeSyntaxCallMode=callIfNoReturn&currentSchema=public	justclick	justclick123	t
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: justclick
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1.1	initialTable	SQL	1.0/V1_1__initialTable.sql	-106066027	justclick	2022-08-27 15:57:06.041808	345	t
\.


--
-- Data for Name: schema_history; Type: TABLE DATA; Schema: public; Owner: justclick
--

COPY public.schema_history (sh_id, sh_created_at, sh_desc, sh_schema, sh_status) FROM stdin;
5	2022-08-27	schema dan table template berhasil dibuat. user berhasil dibuat.	pt-adaro	Success
6	2022-08-27	pembuatan schema gagal. schema pt-adaro sudah ada. user sudah ada.	pt-adaro	Failed
7	2022-08-27	pembuatan schema gagal. schema pt-adaro sudah ada. user sudah ada.	pt-adaro	Failed
8	2022-08-28	pembuatan schema gagal. schema pt-adaro sudah ada. user & tenant berhasil dibuat.	pt-adaro	Failed
9	2022-08-28	schema dan table template berhasil dibuat. user sudah ada.	pt-gudang-garam	Success
10	2022-08-28	pembuatan schema gagal. schema pt-gudang-garam sudah ada. user sudah ada.	pt-gudang-garam	Failed
11	2022-08-28	pembuatan schema gagal. schema pt-gudang-garam sudah ada. tenant berhasil dibuat. user berhasil dibuat.	pt-gudang-garam	Failed
12	2022-08-28	pembuatan schema gagal. schema pt-gudang-garam sudah ada. user sudah ada.	pt-gudang-garam	Failed
\.


--
-- Data for Name: tenants; Type: TABLE DATA; Schema: public; Owner: justclick
--

COPY public.tenants (tenant_id, tenant_name, tenant_address, tenant_schema) FROM stdin;
5	PT.Adaro	Kalimantan	pt-adaro
6	PT.Gudang Garam	Jawa Timur	pt-gudang-garam
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: justclick
--

COPY public.users (user_id, tenant_id, user_name, user_password, user_email, user_enabled) FROM stdin;
4	5	wiliamdecosta	123456	wiliamdecosta@gmail.com	t
5	5	edissidabutar	123456	edistv@gmail.com	t
6	6	rudiyohanes	123456	rudiyohanes@gmail.com	t
\.


--
-- Name: employee_employee_id_seq; Type: SEQUENCE SET; Schema: pt-adaro; Owner: postgres
--

SELECT pg_catalog.setval('"pt-adaro".employee_employee_id_seq', 1, true);


--
-- Name: employee_employee_id_seq; Type: SEQUENCE SET; Schema: pt-gudang-garam; Owner: postgres
--

SELECT pg_catalog.setval('"pt-gudang-garam".employee_employee_id_seq', 1, true);


--
-- Name: datasourceconfig_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justclick
--

SELECT pg_catalog.setval('public.datasourceconfig_id_seq', 4, true);


--
-- Name: schema_history_sh_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justclick
--

SELECT pg_catalog.setval('public.schema_history_sh_id_seq', 12, true);


--
-- Name: tenants_tenant_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justclick
--

SELECT pg_catalog.setval('public.tenants_tenant_id_seq', 6, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justclick
--

SELECT pg_catalog.setval('public.users_user_id_seq', 6, true);


--
-- Name: employee pk_employee; Type: CONSTRAINT; Schema: pt-adaro; Owner: postgres
--

ALTER TABLE ONLY "pt-adaro".employee
    ADD CONSTRAINT pk_employee PRIMARY KEY (employee_id);


--
-- Name: employee pk_employee; Type: CONSTRAINT; Schema: pt-gudang-garam; Owner: postgres
--

ALTER TABLE ONLY "pt-gudang-garam".employee
    ADD CONSTRAINT pk_employee PRIMARY KEY (employee_id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: datasourceconfig pk_datasourceconfig; Type: CONSTRAINT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.datasourceconfig
    ADD CONSTRAINT pk_datasourceconfig PRIMARY KEY (id);


--
-- Name: schema_history pk_schema_history; Type: CONSTRAINT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.schema_history
    ADD CONSTRAINT pk_schema_history PRIMARY KEY (sh_id);


--
-- Name: tenants pk_tenants; Type: CONSTRAINT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.tenants
    ADD CONSTRAINT pk_tenants PRIMARY KEY (tenant_id);


--
-- Name: users pk_users; Type: CONSTRAINT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);


--
-- Name: employee_pk; Type: INDEX; Schema: pt-adaro; Owner: postgres
--

CREATE UNIQUE INDEX employee_pk ON "pt-adaro".employee USING btree (employee_id);


--
-- Name: employee_pk; Type: INDEX; Schema: pt-gudang-garam; Owner: postgres
--

CREATE UNIQUE INDEX employee_pk ON "pt-gudang-garam".employee USING btree (employee_id);


--
-- Name: datasourceconfig_pk; Type: INDEX; Schema: public; Owner: justclick
--

CREATE UNIQUE INDEX datasourceconfig_pk ON public.datasourceconfig USING btree (id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: justclick
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: relationship_1_fk; Type: INDEX; Schema: public; Owner: justclick
--

CREATE INDEX relationship_1_fk ON public.users USING btree (tenant_id);


--
-- Name: schema_history_pk; Type: INDEX; Schema: public; Owner: justclick
--

CREATE UNIQUE INDEX schema_history_pk ON public.schema_history USING btree (sh_id);


--
-- Name: tenants_pk; Type: INDEX; Schema: public; Owner: justclick
--

CREATE UNIQUE INDEX tenants_pk ON public.tenants USING btree (tenant_id);


--
-- Name: users_pk; Type: INDEX; Schema: public; Owner: justclick
--

CREATE UNIQUE INDEX users_pk ON public.users USING btree (user_id);


--
-- Name: users fk_users_relations_tenants; Type: FK CONSTRAINT; Schema: public; Owner: justclick
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_relations_tenants FOREIGN KEY (tenant_id) REFERENCES public.tenants(tenant_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

