--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0 (Debian 16.0-1.pgdg120+1)
-- Dumped by pg_dump version 16.0 (Debian 16.0-1.pgdg120+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: commune; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.commune (
    id bigint NOT NULL,
    name character varying(255),
    district_id bigint
);


ALTER TABLE public.commune OWNER TO magicpost;

--
-- Name: commune_id_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.commune_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.commune_id_seq OWNER TO magicpost;

--
-- Name: commune_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: magicpost
--

ALTER SEQUENCE public.commune_id_seq OWNED BY public.commune.id;


--
-- Name: commune_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.commune_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.commune_seq OWNER TO magicpost;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.customer (
    id uuid NOT NULL,
    street character varying(255),
    zipcode character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    commune_id bigint
);


ALTER TABLE public.customer OWNER TO magicpost;

--
-- Name: district; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.district (
    id bigint NOT NULL,
    name character varying(255),
    province_id bigint
);


ALTER TABLE public.district OWNER TO magicpost;

--
-- Name: district_id_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.district_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.district_id_seq OWNER TO magicpost;

--
-- Name: district_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: magicpost
--

ALTER SEQUENCE public.district_id_seq OWNED BY public.district.id;


--
-- Name: district_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.district_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.district_seq OWNER TO magicpost;

--
-- Name: express_order; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.express_order (
    id uuid NOT NULL,
    actual_weight double precision,
    business_instructions character varying(255),
    cod double precision,
    other double precision,
    totalcod double precision,
    create_time timestamp(6) without time zone,
    main_tax double precision,
    other_tax double precision,
    sub_tax double precision,
    total_price double precision,
    total_tax double precision,
    transport_tax double precision,
    received_time timestamp(6) without time zone,
    send_time timestamp(6) without time zone,
    sender_instructor character varying(255),
    special_service character varying(255),
    status character varying(255),
    type character varying(255),
    volumetric_weight double precision,
    receiver_id uuid,
    sender_id uuid,
    point_id bigint,
    source_point_id bigint,
    CONSTRAINT express_order_sender_instructor_check CHECK (((sender_instructor)::text = ANY ((ARRAY['IMMEDIATE_RETURN'::character varying, 'CALL_SENDER'::character varying, 'CANCEL'::character varying, 'RETURN_BEFORE_DATE'::character varying, 'RETURN_WHEN_STORAGE_EXPIRES'::character varying])::text[]))),
    CONSTRAINT express_order_status_check CHECK (((status)::text = ANY ((ARRAY['POSTED'::character varying, 'TRANSPORTING_FROM_SRC_TRANSACTION'::character varying, 'TRANSPORTED_TO_SRC_GATHERING'::character varying, 'TRANSPORTING_FROM_SRC_GATHERING'::character varying, 'TRANSPORTED_TO_DES_GATHERING'::character varying, 'TRANSPORTING_FROM_DES_GATHERING'::character varying, 'TRANSPORTED_TO_DES_TRANSACTION'::character varying, 'SHIPPING'::character varying, 'DELIVERED'::character varying, 'CANCELED'::character varying, 'RETURNED'::character varying])::text[]))),
    CONSTRAINT express_order_type_check CHECK (((type)::text = ANY ((ARRAY['DOCUMENT'::character varying, 'GOOD'::character varying])::text[])))
);


ALTER TABLE public.express_order OWNER TO magicpost;

--
-- Name: express_orders_transport_order; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.express_orders_transport_order (
    transport_order_id uuid,
    express_orders_id uuid NOT NULL
);


ALTER TABLE public.express_orders_transport_order OWNER TO magicpost;

--
-- Name: gathering_point; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.gathering_point (
    id bigint NOT NULL,
    street character varying(255),
    zipcode character varying(255),
    email character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255),
    total_receive_orders bigint,
    total_send_orders bigint,
    commune_id bigint,
    gathering_leader_id bigint
);


ALTER TABLE public.gathering_point OWNER TO magicpost;

--
-- Name: good; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.good (
    express_order_id uuid NOT NULL,
    attach_document character varying(255),
    name character varying(255),
    quantity bigint,
    value double precision
);


ALTER TABLE public.good OWNER TO magicpost;

--
-- Name: hibernate_sequences; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.hibernate_sequences (
    sequence_name character varying(255) NOT NULL,
    next_val bigint
);


ALTER TABLE public.hibernate_sequences OWNER TO magicpost;

--
-- Name: point_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.point_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.point_seq OWNER TO magicpost;

--
-- Name: province; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.province (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.province OWNER TO magicpost;

--
-- Name: province_id_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.province_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.province_id_seq OWNER TO magicpost;

--
-- Name: province_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: magicpost
--

ALTER SEQUENCE public.province_id_seq OWNED BY public.province.id;


--
-- Name: province_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.province_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.province_seq OWNER TO magicpost;

--
-- Name: shipper; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.shipper (
    id uuid NOT NULL,
    email character varying(255),
    is_active boolean,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    transaction_point_id bigint
);


ALTER TABLE public.shipper OWNER TO magicpost;

--
-- Name: tracking_event; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.tracking_event (
    express_order_id uuid NOT NULL,
    commune_id bigint,
    street character varying(255),
    zipcode character varying(255),
    message character varying(255),
    "timestamp" timestamp(6) without time zone
);


ALTER TABLE public.tracking_event OWNER TO magicpost;

--
-- Name: transaction_point; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.transaction_point (
    id bigint NOT NULL,
    street character varying(255),
    zipcode character varying(255),
    email character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255),
    total_receive_orders bigint,
    total_send_orders bigint,
    commune_id bigint,
    gathering_point_id bigint,
    transaction_leader_id bigint,
    cancel_orders bigint,
    success_orders bigint
);


ALTER TABLE public.transaction_point OWNER TO magicpost;

--
-- Name: transport_order; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.transport_order (
    dtype character varying(31) NOT NULL,
    id uuid NOT NULL,
    arrival_time timestamp(6) without time zone,
    departure_time timestamp(6) without time zone,
    status character varying(255),
    from_point_id bigint,
    shipper_id uuid,
    to_point_id bigint,
    CONSTRAINT transport_order_status_check CHECK (((status)::text = ANY ((ARRAY['SHIPPING'::character varying, 'SHIPPED'::character varying, 'CANCELED'::character varying])::text[])))
);


ALTER TABLE public.transport_order OWNER TO magicpost;

--
-- Name: users; Type: TABLE; Schema: public; Owner: magicpost
--

CREATE TABLE public.users (
    role character varying(31) NOT NULL,
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255),
    phone character varying(255),
    username character varying(255) NOT NULL,
    gathering_point_id bigint,
    transaction_point_id bigint
);


ALTER TABLE public.users OWNER TO magicpost;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: magicpost
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO magicpost;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: magicpost
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: commune id; Type: DEFAULT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.commune ALTER COLUMN id SET DEFAULT nextval('public.commune_id_seq'::regclass);


--
-- Name: district id; Type: DEFAULT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.district ALTER COLUMN id SET DEFAULT nextval('public.district_id_seq'::regclass);


--
-- Name: province id; Type: DEFAULT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.province ALTER COLUMN id SET DEFAULT nextval('public.province_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: commune commune_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.commune
    ADD CONSTRAINT commune_pkey PRIMARY KEY (id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: district district_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT district_pkey PRIMARY KEY (id);


--
-- Name: express_order express_order_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_order
    ADD CONSTRAINT express_order_pkey PRIMARY KEY (id);


--
-- Name: express_orders_transport_order express_orders_transport_order_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_orders_transport_order
    ADD CONSTRAINT express_orders_transport_order_pkey PRIMARY KEY (express_orders_id);


--
-- Name: gathering_point gathering_point_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.gathering_point
    ADD CONSTRAINT gathering_point_pkey PRIMARY KEY (id);


--
-- Name: hibernate_sequences hibernate_sequences_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.hibernate_sequences
    ADD CONSTRAINT hibernate_sequences_pkey PRIMARY KEY (sequence_name);


--
-- Name: province province_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.province
    ADD CONSTRAINT province_pkey PRIMARY KEY (id);


--
-- Name: shipper shipper_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.shipper
    ADD CONSTRAINT shipper_pkey PRIMARY KEY (id);


--
-- Name: transaction_point transaction_point_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transaction_point
    ADD CONSTRAINT transaction_point_pkey PRIMARY KEY (id);


--
-- Name: transport_order transport_order_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transport_order
    ADD CONSTRAINT transport_order_pkey PRIMARY KEY (id);


--
-- Name: gathering_point uk_55vfqciqybp60r64dxg08d8pl; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.gathering_point
    ADD CONSTRAINT uk_55vfqciqybp60r64dxg08d8pl UNIQUE (name);


--
-- Name: transaction_point uk_mx7d1dt9vim71b1wa75w0sxum; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transaction_point
    ADD CONSTRAINT uk_mx7d1dt9vim71b1wa75w0sxum UNIQUE (transaction_leader_id);


--
-- Name: customer uk_o3uty20c6csmx5y4uk2tc5r4m; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT uk_o3uty20c6csmx5y4uk2tc5r4m UNIQUE (phone);


--
-- Name: gathering_point uk_pxnifedfehvutjy70gxnv67kl; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.gathering_point
    ADD CONSTRAINT uk_pxnifedfehvutjy70gxnv67kl UNIQUE (gathering_leader_id);


--
-- Name: transaction_point uk_r2dtsf4bdejw9um7m927612j0; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transaction_point
    ADD CONSTRAINT uk_r2dtsf4bdejw9um7m927612j0 UNIQUE (name);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: district fk276utu38g5lgqeth6pwfm3rw2; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT fk276utu38g5lgqeth6pwfm3rw2 FOREIGN KEY (province_id) REFERENCES public.province(id);


--
-- Name: express_order fk422xchsvlit8hku6peya4g3u1; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_order
    ADD CONSTRAINT fk422xchsvlit8hku6peya4g3u1 FOREIGN KEY (receiver_id) REFERENCES public.customer(id);


--
-- Name: shipper fk4yc2gre93dmyqch9t9dasxhcs; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.shipper
    ADD CONSTRAINT fk4yc2gre93dmyqch9t9dasxhcs FOREIGN KEY (transaction_point_id) REFERENCES public.transaction_point(id);


--
-- Name: customer fk5l5vtctm87k12jf9qtddixfum; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fk5l5vtctm87k12jf9qtddixfum FOREIGN KEY (commune_id) REFERENCES public.commune(id);


--
-- Name: tracking_event fk9n9ribacks0rfxhbcr2ac4pyq; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.tracking_event
    ADD CONSTRAINT fk9n9ribacks0rfxhbcr2ac4pyq FOREIGN KEY (commune_id) REFERENCES public.commune(id);


--
-- Name: gathering_point fk_1hw8239phpi61ttct10y3867s; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.gathering_point
    ADD CONSTRAINT fk_1hw8239phpi61ttct10y3867s FOREIGN KEY (commune_id) REFERENCES public.commune(id);


--
-- Name: transaction_point fk_7rx2fu9u38k5yxapt8619rvf6; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transaction_point
    ADD CONSTRAINT fk_7rx2fu9u38k5yxapt8619rvf6 FOREIGN KEY (commune_id) REFERENCES public.commune(id);


--
-- Name: express_orders_transport_order fkaydfye3rw2n0q3pcjxg2sdusy; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_orders_transport_order
    ADD CONSTRAINT fkaydfye3rw2n0q3pcjxg2sdusy FOREIGN KEY (express_orders_id) REFERENCES public.express_order(id);


--
-- Name: express_order fkbxnvmgdy79va9jv3h89911ypj; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_order
    ADD CONSTRAINT fkbxnvmgdy79va9jv3h89911ypj FOREIGN KEY (source_point_id) REFERENCES public.transaction_point(id);


--
-- Name: users fkfeefd5nd4s7m7stlclo5j6a7x; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkfeefd5nd4s7m7stlclo5j6a7x FOREIGN KEY (gathering_point_id) REFERENCES public.gathering_point(id);


--
-- Name: commune fkjhf1rxvyl4a736j0xjyuqywdr; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.commune
    ADD CONSTRAINT fkjhf1rxvyl4a736j0xjyuqywdr FOREIGN KEY (district_id) REFERENCES public.district(id);


--
-- Name: gathering_point fkk0f42ye6guniw9fcv9vwkd93s; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.gathering_point
    ADD CONSTRAINT fkk0f42ye6guniw9fcv9vwkd93s FOREIGN KEY (gathering_leader_id) REFERENCES public.users(id);


--
-- Name: express_orders_transport_order fklv3683xwl5ad85otndiaxfjll; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_orders_transport_order
    ADD CONSTRAINT fklv3683xwl5ad85otndiaxfjll FOREIGN KEY (transport_order_id) REFERENCES public.transport_order(id);


--
-- Name: express_order fklw59gm6iygjugq35edn0vro8n; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.express_order
    ADD CONSTRAINT fklw59gm6iygjugq35edn0vro8n FOREIGN KEY (sender_id) REFERENCES public.customer(id);


--
-- Name: tracking_event fknlnhpnautfrcod0khv9nrhflr; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.tracking_event
    ADD CONSTRAINT fknlnhpnautfrcod0khv9nrhflr FOREIGN KEY (express_order_id) REFERENCES public.express_order(id);


--
-- Name: users fknryhnypdsxcl737wsxsxjlq3k; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fknryhnypdsxcl737wsxsxjlq3k FOREIGN KEY (transaction_point_id) REFERENCES public.transaction_point(id);


--
-- Name: transaction_point fkpsdtpbmajiq9pxmhiw9it5a3v; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transaction_point
    ADD CONSTRAINT fkpsdtpbmajiq9pxmhiw9it5a3v FOREIGN KEY (transaction_leader_id) REFERENCES public.users(id);


--
-- Name: transport_order fkqpr2dxt11hbdawnnva0hn3piv; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transport_order
    ADD CONSTRAINT fkqpr2dxt11hbdawnnva0hn3piv FOREIGN KEY (shipper_id) REFERENCES public.shipper(id);


--
-- Name: good fktenblufvx186ticqrptfkldwm; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.good
    ADD CONSTRAINT fktenblufvx186ticqrptfkldwm FOREIGN KEY (express_order_id) REFERENCES public.express_order(id);


--
-- Name: transaction_point fkx9yld4flun92hbhrihfb5fhg; Type: FK CONSTRAINT; Schema: public; Owner: magicpost
--

ALTER TABLE ONLY public.transaction_point
    ADD CONSTRAINT fkx9yld4flun92hbhrihfb5fhg FOREIGN KEY (gathering_point_id) REFERENCES public.gathering_point(id);


--
-- PostgreSQL database dump complete
--

