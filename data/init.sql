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
    CONSTRAINT express_order_sender_instructor_check CHECK (((sender_instructor)::text = ANY (ARRAY[('IMMEDIATE_RETURN'::character varying)::text, ('CALL_SENDER'::character varying)::text, ('CANCEL'::character varying)::text, ('RETURN_BEFORE_DATE'::character varying)::text, ('RETURN_WHEN_STORAGE_EXPIRES'::character varying)::text]))),
    CONSTRAINT express_order_status_check CHECK (((status)::text = ANY (ARRAY[('POSTED'::character varying)::text, ('TRANSPORTING_FROM_SRC_TRANSACTION'::character varying)::text, ('TRANSPORTED_TO_SRC_GATHERING'::character varying)::text, ('TRANSPORTING_FROM_SRC_GATHERING'::character varying)::text, ('TRANSPORTED_TO_DES_GATHERING'::character varying)::text, ('TRANSPORTING_FROM_DES_GATHERING'::character varying)::text, ('TRANSPORTED_TO_DES_TRANSACTION'::character varying)::text, ('SHIPPING'::character varying)::text, ('DELIVERED'::character varying)::text, ('CANCELED'::character varying)::text, ('RETURNED'::character varying)::text]))),
    CONSTRAINT express_order_type_check CHECK (((type)::text = ANY (ARRAY[('DOCUMENT'::character varying)::text, ('GOOD'::character varying)::text])))
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
    CONSTRAINT transport_order_status_check CHECK (((status)::text = ANY (ARRAY[('SHIPPING'::character varying)::text, ('SHIPPED'::character varying)::text, ('CANCELED'::character varying)::text])))
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
-- Data for Name: commune; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.commune (id, name, district_id) FROM stdin;
2	Phường 14	2
3	Phường Thạch Thang	3
4	Phường Phú Hòa	4
5	Phường Hàng Bài	5
6	Phường 10	6
7	Phường Ngô Quyền	7
8	Phường Tam Hiệp	8
9	Phường Hòa Vang	9
10	Phường Yên Hòa	10
11	Phường DEF	11
12	Phường PQR	6
13	Xã An Bình	12
14	Xã An Phú	13
15	Phường Tràng Tiền	5
16	Phường Lý Thái Tổ	5
17	Phường 1	14
18	Phường 2	14
19	Phường 3	14
20	Phường Máy Tơ	15
29	Cổ Nhuế	18
30	Mỹ Đình	19
31	Cửa Nam	20
32	Trần Hưng Đạo	20
33	Phường Bến Thành	21
34	Phường Cửa Nam	20
21	Phường Lạch Tray	15
22	Phường Trung Dũng	8
23	Phường Xuân An	16
24	Xã Hòa Liên	17
25	Xã Hòa Nhơn	17
26	Phường Dịch Vọng	10
27	Phường Trung Hòa	10
28	Phường Nghĩa Đô	10
35	Phường Bến Nghé	21
36	Phường Cửa Đông	20
1	Phường Mễ Trì	1
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.customer (id, street, zipcode, name, phone, commune_id) FROM stdin;
12f14b2e-c554-405e-8c0d-3900917b2731	Số 8 Tôn Thất Thuyết	567890	Trần Thị Loan	0909234567	30
07019d62-98b3-4f5f-b742-6224a6ec1ddd	Số 15 Hoàng Diệu	543210	Trần Văn An	0978123456	31
f404ef1e-cf06-430f-b66b-8f9c6e59eaba	Số 20 Nguyễn Văn Cừ	765432	Nguyễn Thị Hằng	0909345678	32
68b5e640-b60d-4235-b545-66d5a1e0a89d	Số 10 Lý Thường Kiệt	123456	Nguyễn Thị Anh	0987123456	33
7cee4e47-6a3e-4062-bc34-564014781b50	Số 5 Lê Lợi	654321	Trần Văn Bình	0918234567	34
f7b6495e-ede8-4439-b696-748e061ea974	Số 20 Lý Thường Kiệt	123456	Nguyễn Thanh Tùng	0987654321	35
6481bf4a-6bab-4d74-aea8-0d452f79aa90	Số 15 Hàng Bài	654321	Trần Minh Anh	0912345678	36
\.


--
-- Data for Name: district; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.district (id, name, province_id) FROM stdin;
2	Quận Tân Bình	3
3	Quận Hải Châu	4
4	Thành phố Thủ Dầu Một	5
5	Quận Hoàn Kiếm	2
6	Quận Gò Vấp	3
7	Quận Hải Phòng	6
8	Thành phố Biên Hòa	7
9	Quận Hòa Vang	8
10	Quận Cầu Giấy	2
11	Quận Hải Châu	8
12	Huyện Thuận An	5
13	Huyện Dĩ An	5
14	Quận Gò Vấp	9
15	Quận Ngô Quyền	6
18	Bắc Từ Liêm	2
19	Nam Từ Liêm	2
20	Hoàn Kiếm	2
21	Quận 1	9
16	Thị xã Long Khánh	7
17	Huyện Hòa Vang	8
1	Quận Bắc Từ Liêm	2
\.


--
-- Data for Name: express_order; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.express_order (id, actual_weight, business_instructions, cod, other, totalcod, create_time, main_tax, other_tax, sub_tax, total_price, total_tax, transport_tax, received_time, send_time, sender_instructor, special_service, status, type, volumetric_weight, receiver_id, sender_id, point_id, source_point_id) FROM stdin;
b6822805-7049-4375-9cd6-8ad0ae1f5a60	1.5	Cẩn thận không làm mất	0	0	0	\N	5000	0	0	30000	15000	10000	\N	2023-12-29 15:08:33.357	IMMEDIATE_RETURN	INSURANCE	POSTED	DOCUMENT	2	12f14b2e-c554-405e-8c0d-3900917b2731	68b5e640-b60d-4235-b545-66d5a1e0a89d	11	11
2afbf242-918c-4df5-868e-f1f41779ffce	0.5	Gửi hàng cẩn thận, không gấp	0	0	0	\N	8000	0	0	43000	23000	15000	\N	2023-12-29 08:30:00	\N	INSURANCE	POSTED	DOCUMENT	0.5	f404ef1e-cf06-430f-b66b-8f9c6e59eaba	07019d62-98b3-4f5f-b742-6224a6ec1ddd	11	11
33dcce9b-3135-4112-814c-b260fa8a772b	0.5	Gửi hàng cẩn thận, không gấp	0	0	0	\N	8000	0	0	43000	23000	15000	\N	2023-12-29 08:30:00	\N	INSURANCE	POSTED	DOCUMENT	0.5	f404ef1e-cf06-430f-b66b-8f9c6e59eaba	07019d62-98b3-4f5f-b742-6224a6ec1ddd	11	11
c3d893bd-e0f8-4213-bb6a-fcd013f01d46	1.5	Giao hàng nhanh	0	0	0	\N	10000	0	0	45000	30000	20000	\N	2023-12-30 10:00:00	\N	FRAGILE	POSTED	GOOD	2	7cee4e47-6a3e-4062-bc34-564014781b50	68b5e640-b60d-4235-b545-66d5a1e0a89d	14	14
845c9ba2-fdbd-48a5-a5c9-b5cc67563b6b	2.8	Giao hàng cẩn thận, không gấp	0	0	0	\N	15000	0	0	16000000	45000	30000	\N	2023-12-31 14:00:00	\N	INSURANCE	POSTED	GOOD	3.2	6481bf4a-6bab-4d74-aea8-0d452f79aa90	f7b6495e-ede8-4439-b696-748e061ea974	38	38
\.


--
-- Data for Name: express_orders_transport_order; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.express_orders_transport_order (transport_order_id, express_orders_id) FROM stdin;
\.


--
-- Data for Name: gathering_point; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.gathering_point (id, street, zipcode, email, name, phone, total_receive_orders, total_send_orders, commune_id, gathering_leader_id) FROM stdin;
1	Số 10 Đường Lê Quang Đạo	\N	buucucbactuliem@example.com	Bưu Cục Bắc Từ Liêm	0987654321	0	0	1	1
2	Số 56 Đường Bàu Cát	\N	tramtanbinh@example.com	Trạm Vận Chuyển Tân Bình	0901234567	0	0	2	2
3	Số 30 Đường Trần Phú	\N	buucuchaichau@example.com	Bưu Cục Hải Châu	0976543210	0	0	3	3
4	Số 88 Đường 30/4	\N	ttgnbinhduong@example.com	Trung Tâm Giao Nhận Bình Dương	0912345678	0	0	4	4
5	Số 1 Đường Tràng Thi	\N	buucuchoankiem@example.com	Bưu Cục Hoàn Kiếm	0923456789	0	0	5	5
6	Số 125 Đường Nguyễn Oanh	\N	tramgovap@example.com	Trạm Vận Chuyển Gò Vấp	0965432109	0	0	6	6
7	Số 45 Đường Trần Phú	\N	buucuchp@example.com	Bưu Cục Hải Phòng	0932165478	0	0	7	7
8	Số 99 Đường 30/4	\N	ttgndongnai@example.com	Trung Tâm Giao Nhận Đồng Nai	0945678912	0	0	8	8
9	Số 15 Đường Nguyễn Văn Linh	\N	buucuchoavang@example.com	Bưu Cục Hòa Vang	0954321768	0	0	9	9
10	Số 20 Đường Trần Quốc Hoàn	\N	tramcaugiay@example.com	Trạm Vận Chuyển Cầu Giấy	0918765432	0	0	10	10
\.


--
-- Data for Name: good; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.good (express_order_id, attach_document, name, quantity, value) FROM stdin;
b6822805-7049-4375-9cd6-8ad0ae1f5a60	https://example.com/document.pdf	Tài liệu	1	10000
2afbf242-918c-4df5-868e-f1f41779ffce	https://example.com/document.pdf	Hợp đồng	1	20000
33dcce9b-3135-4112-814c-b260fa8a772b	https://example.com/document.pdf	Hợp đồng	1	20000
c3d893bd-e0f8-4213-bb6a-fcd013f01d46	\N	Gấu bông	2	150000
c3d893bd-e0f8-4213-bb6a-fcd013f01d46	\N	Đồ chơi Lego	1	300000
845c9ba2-fdbd-48a5-a5c9-b5cc67563b6b	\N	Laptop	1	15000000
845c9ba2-fdbd-48a5-a5c9-b5cc67563b6b	\N	Bàn phím	1	1000000
845c9ba2-fdbd-48a5-a5c9-b5cc67563b6b	\N	Chuột máy tính	1	500000
\.


--
-- Data for Name: hibernate_sequences; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.hibernate_sequences (sequence_name, next_val) FROM stdin;
default	100
\.


--
-- Data for Name: province; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.province (id, name) FROM stdin;
3	Thành phố Hồ Chí Minh
4	Thành phố Đà Nẵng
5	Bình Dương
6	Hải Phòng
7	Đồng Nai
8	Đà Nẵng
9	TP. Hồ Chí Minh
2	Hà Nội
\.


--
-- Data for Name: shipper; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.shipper (id, email, is_active, name, phone, transaction_point_id) FROM stdin;
ba8d6fa0-48ca-44f6-9130-c304f12910a1	tunguyen@example.com	t	Nguyễn Văn Tú	0909123456	11
05515f85-1c25-410c-83ca-34f54592d10e	huongtran@example.com	t	Trần Thị Hương	0909234567	11
eba6be1f-ae95-4623-b55f-4310611f986a	huongtran@example.com	t	Trần Thị Hương	0909234567	12
042f023d-35dc-4f57-bb8c-4594cf75a159	quynhpham@example.com	t	Phạm Thị Quỳnh	0909456789	12
287c9b81-5507-48e9-a486-e652c3ee52de	namle@example.com	t	Lê Văn Nam	0909345678	13
2355620b-ef5b-4fb0-b0cf-cde14a35cf52	quynhpham@example.com	t	Phạm Thị Quỳnh	0909456789	14
049840df-0702-4a7e-bced-e619d3d491a1	sonvu@example.com	t	Vũ Văn Sơn	0909567890	15
a7125fbe-9820-4643-a6a6-7fe31d149bf3	lannguyen@example.com	t	Nguyễn Thị Lan	0909678901	16
59bc1288-4b9f-4fc3-8a1f-b6ebfcda432f	minhtran@example.com	t	Trần Văn Minh	0909789012	17
db5573f7-08db-4be2-9e85-6bbb71560bb9	phuchoang@example.com	t	Hoàng Văn Phúc	0909901234	37
eb4a347e-baff-4eaf-94c2-704a7d543471	trucnguyen@example.com	t	Nguyễn Thị Trúc	0909012345	37
994b4d75-8e58-4c8a-97aa-338f3a857361	trucnguyen@example.com	t	Nguyễn Thị Trúc	0909012345	38
beec8e6e-71fd-4d1a-aadb-2a9e0077f6bd	hanhnguyen@example.com	t	Nguyễn Thị Hạnh	0909234567	38
242ddf68-b240-4ef1-a9ab-26594188f4f7	maithi@example.com	t	Trần Thị Mai	0909123456	38
cb49a9f9-6a26-42dd-910d-153a13a9d99a	tuyetle@example.com	t	Lê Thị Tuyết	0909345678	39
fe00fd98-1022-415e-b76a-db6ec79f8331	hongpham@example.com	t	Phạm Thị Hồng	0909456789	39
25b08af5-d66a-4ae9-b484-f2ac48c730d3	huevu@example.com	t	Vũ Thị Huệ	0909567890	39
\.


--
-- Data for Name: tracking_event; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.tracking_event (express_order_id, commune_id, street, zipcode, message, "timestamp") FROM stdin;
b6822805-7049-4375-9cd6-8ad0ae1f5a60	1	Số 12 Đường Lê Quang Đạo	100001	Order has been posted	2023-12-29 22:21:22.617923
2afbf242-918c-4df5-868e-f1f41779ffce	1	Số 12 Đường Lê Quang Đạo	100001	Order has been posted	2023-12-29 22:24:16.532329
33dcce9b-3135-4112-814c-b260fa8a772b	1	Số 12 Đường Lê Quang Đạo	100001	Order has been posted	2023-12-29 22:26:39.413475
c3d893bd-e0f8-4213-bb6a-fcd013f01d46	2	Số 56 Đường Bàu Cát	700001	Order has been posted	2023-12-29 22:27:49.359545
845c9ba2-fdbd-48a5-a5c9-b5cc67563b6b	27	Số 20 Trần Duy Hưng	100000	Order has been posted	2023-12-29 22:37:32.080292
\.


--
-- Data for Name: transaction_point; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.transaction_point (id, street, zipcode, email, name, phone, total_receive_orders, total_send_orders, commune_id, gathering_point_id, transaction_leader_id, cancel_orders, success_orders) FROM stdin;
39	Số 30 Hoàng Ngân	100000	diemcaugiayC@example.com	Điểm Giao Dịch Nghĩa Đô	0909345678	0	0	28	10	\N	0	0
12	Số 24 Đường Lê Quang Đạo	100002	diem2bacbaliem@example.com	Điểm Giao Dịch Bắc Từ Liêm 2	0978234567	0	0	1	1	21	0	0
13	Số 36 Đường Lê Quang Đạo	100003	diem3bacbaliem@example.com	Điểm Giao Dịch Bắc Từ Liêm 3	0967345678	0	0	1	1	22	0	0
15	Số 68 Đường Bàu Cát	700002	diem2tanbinh@example.com	Điểm Giao Dịch Tân Bình 2	0909234567	0	0	2	2	24	0	0
16	Số 80 Đường Bàu Cát	700003	diem3tanbinh@example.com	Điểm Giao Dịch Tân Bình 3	0909345678	0	0	2	2	25	0	0
17	Số 12 Đường ABC	550000	diem1haichau@example.com	Điểm Giao Dịch Hải Châu 1	0909123456	0	0	11	3	26	0	0
18	Số 24 Đường XYZ	550000	diem2haichau@example.com	Điểm Giao Dịch Hải Châu 2	0909234567	0	0	11	3	27	0	0
21	Số 10, Đường XYZ	750000	buucuc1binhduong@example.com	Bưu Cục Bình Dương 1	0909123456	0	0	13	4	28	0	0
22	Số 20, Đường ABC	750000	buucuc2binhduong@example.com	Bưu Cục Bình Dương 2	0909234567	0	0	14	4	29	0	0
24	Số 10 Tràng Tiền	100000	diemtrangtien@buucuchoankiem.vn	Điểm Giao Dịch Tràng Tiền	0909123456	0	0	15	5	30	0	0
25	Số 20 Hàng Bài	100000	diemhangbai@buucuchoankiem.vn	Điểm Giao Dịch Hàng Bài	0909234567	0	0	5	5	31	0	0
26	Số 30 Lý Thái Tổ	100000	diemlythaito@buucuchoankiem.vn	Điểm Giao Dịch Lý Thái Tổ	0909345678	0	0	16	5	32	0	0
27	Số 10 Phạm Văn Đồng	700000	diemphamvandong@example.com	Điểm Giao Dịch Phạm Văn Đồng	0909123456	0	0	17	6	33	0	0
28	Số 20 Quang Trung	700000	diemquangtrung@example.com	Điểm Giao Dịch Quang Trung	0909234567	0	0	18	6	34	0	0
29	Số 30 Nguyễn Oanh	700000	diemnguyenoanh@example.com	Điểm Giao Dịch Nguyễn Oanh	0909345678	0	0	19	6	35	0	0
30	Số 10 Ngô Quyền	180000	diemngoquyen@example.com	Điểm Giao Dịch Ngô Quyền	0909123456	0	0	20	7	36	0	0
31	Số 20 Lạch Tray	180000	diemlachtray@example.com	Điểm Giao Dịch Lạch Tray	0909234567	0	0	21	7	37	0	0
32	Số 10 Đỗ Quang	710000	diembienhoa@example.com	Điểm Giao Dịch Biên Hòa	0909345678	0	0	22	8	38	0	0
33	Số 20 Trần Hưng Đạo	730000	diemlongkhanh@example.com	Điểm Giao Dịch Long Khánh	0909456789	0	0	23	8	39	0	0
34	Số 10 Nguyễn Văn Linh	550000	diemhoavang1@example.com	Điểm Giao Dịch Hòa Vang 1	0909123456	0	0	24	9	40	0	0
35	Số 20 Trần Cao Vân	550000	diemhoavang2@example.com	Điểm Giao Dịch Hòa Vang 2	0909234567	0	0	25	9	41	0	0
37	Số 10 Duy Tân	100000	diemcaugiayA@example.com	Điểm Giao Dịch Duy Tân	0909123456	0	0	26	10	42	0	0
11	Số 12 Đường Lê Quang Đạo	100001	diem1bacbaliem@example.com	Điểm Giao Dịch Bắc Từ Liêm 1	0987123456	3	0	1	1	20	0	0
14	Số 56 Đường Bàu Cát	700001	diem1tanbinh@example.com	Điểm Giao Dịch Tân Bình 1	0909123456	1	0	2	2	23	0	0
38	Số 20 Trần Duy Hưng	100000	diemcaugiayB@example.com	Điểm Giao Dịch Trần Duy Hưng	0909234567	1	0	27	10	\N	0	0
\.


--
-- Data for Name: transport_order; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.transport_order (dtype, id, arrival_time, departure_time, status, from_point_id, shipper_id, to_point_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: magicpost
--

COPY public.users (role, id, email, password, phone, username, gathering_point_id, transaction_point_id) FROM stdin;
GATHERING_LEADER	1	huongnguyen@example.com	$2a$10$k53lUmfp4hV4Z5HnBXV3a.cxhrhK2EpTVBJJjlmRlivJppsh9bcKK	0909123456	Nguyễn Thị Hương	\N	\N
GATHERING_LEADER	2	anhtran@example.com	$2a$10$99mF.AG984uqYRzC1XLgzeHq3NMCR93bY0s2/FjsfGYxUccG8x5sC	0909234567	Trần Văn Anh	\N	\N
GATHERING_LEADER	3	yenle@example.com	$2a$10$0KnYOAwYGsmAwYLrqwAaY.K01rb6I5QHW0NOZIaQCgHabu9li0aqG	0909345678	Lê Thị Hải Yến	\N	\N
GATHERING_LEADER	4	tuanpham@example.com	$2a$10$1cd5dS3hMA1OhP1QJtnrXOf3KVPWy8ecreCHp1DNNl97gsrdh/l8K	0909456789	Phạm Minh Tuấn	\N	\N
GATHERING_LEADER	5	linhvuthi@example.com	$2a$10$nu9qUBKQry6PAKTNHwCiouFEkT4jpX9./RjfqlqyW35sLWvzxhuy.	0909567890	Vũ Thị Linh	\N	\N
GATHERING_LEADER	6	thangngo@example.com	$2a$10$4eX21NhILtBjgvD03sKHpOFjVfL7V8mDp6.d7vekZ0GVl7eXWVrWm	0909678901	Ngô Đình Thắng	\N	\N
GATHERING_LEADER	7	huongtran@example.com	$2a$10$muAHrwBALUWTN.b1YgrbE.H4PGXkB3wKEaM65PFIzWVu2iVItgP7a	0909789012	Trần Thanh Hương	\N	\N
GATHERING_LEADER	8	yendothi@example.com	$2a$10$WGsKuvEt3AS1QJwKap5iP.U6n.DKivBgTE491hyhpuydzcfK3Krd6	0909890123	Đỗ Thị Hải Yến	\N	\N
GATHERING_LEADER	9	tuanhoang@example.com	$2a$10$asSbCmQT652zow7diohGuOEAysEFWEc3/w/YzKCNApTceJ34XCGpG	0909901234	Hoàng Văn Tuấn	\N	\N
GATHERING_LEADER	10	huongnguyenthithu@example.com	$2a$10$kHMubJG07qkNHzUf7zOAUOQuVHAaV7TcS3YTjfD/ha8nQLUh3BG6O	0909012345	Nguyễn Thị Thu Hương	\N	\N
GATHERING_STAFF	11	annguyen@example.com	$2a$10$Me1Bslvk2p1oarzUct.X9./dUZP70tx.YdaYEh3X1jSpcIaPkcXda	0909123456	Nguyễn Văn An	1	\N
GATHERING_STAFF	12	binhtran@example.com	$2a$10$5cD49M2E7/ouaNiP3nwAJOzAvwQ3NmSoFdTTMivp4o8fXfI8OtCoC	0909234567	Trần Thị Bình	2	\N
GATHERING_STAFF	13	cuongle@example.com	$2a$10$Mb2BW2F4a.B2rGSHP4hPYuqAHuwMbM4zo73krv9CcwC/edbt3UaKG	0909345678	Lê Văn Cường	3	\N
GATHERING_STAFF	14	dungpham@example.com	$2a$10$NFRAo9n1IQ.dg.5J9xKyKeIpVSLQ1kRkBoEDrr2oaSmY0XA3l.jze	0909456789	Phạm Thị Dung	4	\N
GATHERING_STAFF	15	eovu@example.com	$2a$10$OrykEeXVH/kiEgwjnhzP7Old7ctxE0PD5.UVOhebO92UqX6ZrMtCW	0909567890	Vũ Văn Eo	5	\N
GATHERING_STAFF	16	phuongnguyen@example.com	$2a$10$MyBaWihqviVO5rs9ezJkrutVVZiP13hhefls7195zRl0tdydFRN8a	0909678901	Nguyễn Thị Phượng	6	\N
GATHERING_STAFF	17	giatran@example.com	$2a$10$nVaG3p7eniYghRekpBwlrOmzeQMhLrti6A2RBYSnMnE1.nu3OE3O2	0909789012	Trần Văn Gia	7	\N
GATHERING_STAFF	18	hado@example.com	$2a$10$uz0Gk8GNAMj6ieCRbCuRn.aw.9aJg3WWE.mCx0mN6K0xPV8Pbqzxy	0909890123	Đỗ Thị Hà	8	\N
GATHERING_STAFF	19	iahoang@example.com	$2a$10$zuM5oeib7aWyVN.CqH0eguD60zqwdaIj8RUO4OzAYrlKU1Qj/Lb2.	0909901234	Hoàng Văn Ia	9	\N
TRANSACTION_LEADER	20	binhtran@example.com	$2a$10$ZRAm2LINtFeWqvUZU.gZLuedtUJny4Mch83y31UuVXqUpwhWLQwXG	0909234567	Trần Thị Bình	\N	\N
TRANSACTION_LEADER	21	cuongle@example.com	$2a$10$AR1K0y2y5E4/BjsgZRMOQOCO2.LjrnwaBxJSSj2pGcIbRHTLfvaWK	0909345678	Lê Văn Cường	\N	\N
TRANSACTION_LEADER	22	dungpham@example.com	$2a$10$X4OSOLGvJIWAozcDPHoLBu3GyX0SOY/HhrZI9212M8Xfa/IQez7k6	0909456789	Phạm Thị Dung	\N	\N
TRANSACTION_LEADER	23	eovu@example.com	$2a$10$R2YjH/Qc3f3dhOmuh571OOCnfHQVtrx.Tm2/1BSMfzD.sN1KrDaqS	0909567890	Vũ Văn Eo	\N	\N
TRANSACTION_LEADER	24	phuongnguyen@example.com	$2a$10$o/XWaLVlZflbrOOcYMEwle6YREI9nArPP74eNGiO.FfwRf/XAjegq	0909678901	Nguyễn Thị Phượng	\N	\N
TRANSACTION_LEADER	25	giatran@example.com	$2a$10$rwoGNBgeJ2G9FVHSx4.ApOg5OsjpVrpoevVAWGOxMhdsa1PcGeHt6	0909789012	Trần Văn Gia	\N	\N
TRANSACTION_LEADER	26	hado@example.com	$2a$10$Y4coAeGmfH8JDPbOJ7fxheHp/IYeLy8RsRMPXzL/Bk0V4BeFZz.3q	0909890123	Đỗ Thị Hà	\N	\N
TRANSACTION_LEADER	27	lannguyen@example.com	$2a$10$yY4SgVOKm9QitgUgWf94s.5..9rxZp3rIEGCRauLNPjlqCONnmAAm	0909123456	Trương Văn Lân	\N	\N
TRANSACTION_LEADER	28	minhtran@example.com	$2a$10$lsJHLiUY4vPX2oFmJZGFw.G42.isqbKf44bdmp2xZerhG3K4odHMK	0909234567	Minh Thị Trần	\N	\N
TRANSACTION_LEADER	29	hungngo@example.com	$2a$10$Lz7zGhuiViTGg6mUEgPQX.vC0h0p0bBRZgqo/C8tGHahAcf1acP6G	0909345678	Ngô Chí Hùng	\N	\N
TRANSACTION_LEADER	30	manhlinh@example.com	$2a$10$Qz.dvlrX2yrNxg3VmcHh7.U9M7lG9qIEcltSu187OHdNkKJwApgmu	0909456789	Linh Văn Mạnh	\N	\N
TRANSACTION_LEADER	31	quynhhai@example.com	$2a$10$6PqNONWgHgrgJRDcY6FycujhKMmPyAebG8b2DIydA9SZ309xsV4d.	0909567890	Hải Thị Quỳnh	\N	\N
TRANSACTION_LEADER	32	sontran@example.com	$2a$10$0SaT05k6j/AYPvwrx4Avjuf.mPvViiLodTDeGlcVgS3DN/GoNVfjS	0909678901	Trần Văn Sơn	\N	\N
TRANSACTION_LEADER	33	thientam@example.com	$2a$10$ozBWXTP429gY9SbtHJbezOA59uE18OUX2pDX1bza7KqS0XVcDqRya	0909789012	Tâm Văn Thiên	\N	\N
TRANSACTION_LEADER	34	uyenluong@example.com	$2a$10$mITZQNXnUwK0qMHuyHiKbelMRM.63kxBqbNU/nN44Up2ZQLrFTeEC	0909890123	Lương Thị Uyên	\N	\N
TRANSACTION_LEADER	35	xuongviet@example.com	$2a$10$lSQa7FANVVAZAoJlDNz0LexZaM3IV2yaDzrQoEn6Yp/RmBI1VTc96	0909901234	Việt Văn Xương	\N	\N
TRANSACTION_LEADER	36	zuyyen@example.com	$2a$10$6aquYtwVgMLa53EevISeGOiw0PcdQl4MGlu4JeN4iSsbWirfS.IX2	0909012345	Yến Thị Zuy	\N	\N
TRANSACTION_LEADER	37	datphan@example.com	$2a$10$V59Lw7qkcywgb1v63nBUhuL1YfsgTTDMLdDyBO/JtbMQwJyuixH6m	0909123456	Phan Văn Đạt	\N	\N
TRANSACTION_LEADER	38	huongtrinh@example.com	$2a$10$6E0KBTxlskUGfN0dgHyeeOXI9V9pw1JPaRipn9yc5BHomzh55frzO	0909234567	Trịnh Thị Hương	\N	\N
TRANSACTION_LEADER	39	giangle@example.com	$2a$10$r4z7A046cSoz5XqeN1P.Ge7xO9iQ2nIiDmQzuQG3xHKVSLCiPPe16	0909345678	Lê Văn Giang	\N	\N
TRANSACTION_LEADER	40	hoanguyen@example.com	$2a$10$Elcb0IAQZNQ4DiVVaN2cKOqHV8mvaZG3kkUCfm/xtOpS32Xr5t1Du	0909456789	Nguyễn Thị Hoa	\N	\N
TRANSACTION_LEADER	41	khaidang@example.com	$2a$10$x9O8zMufILybo4SxUJNs5OoB4grc.MFhdlQGsvEpjwUBjvYWYRIe6	0909567890	Đặng Văn Khải	\N	\N
TRANSACTION_LEADER	42	lynguyen@example.com	$2a$10$VJkYBw0FUUMGB.NWWusSAeyTd5f2yxqM1jBmDq/cGmkm/IeX9Xf9S	0909678901	Nguyễn Thị Lý	\N	\N
TRANSACTION_STAFF	43	manhtran@example.com	$2a$10$8AxObSCqnv4nIM/uvSSWauH.Gom1lTDH/enLwPDm1m7RKHS/1jk2a	0909789012	Trần Văn Mạnh	\N	11
TRANSACTION_STAFF	44	nganguyen@example.com	$2a$10$lNEMr7D3VF0boYimvcO1ge0V/xHM6KgkctUv46M39.FExMOnZ6LBu	0909890123	Nguyễn Thị Nga	\N	12
TRANSACTION_STAFF	45	oanhle@example.com	$2a$10$Q0E/HngGCqaHeEA/AgjQ3.vEqczDLl3guZEIFIiQzyQlo3BQb7O2K	0909901234	Lê Văn Oanh	\N	13
TRANSACTION_STAFF	46	quynhpham@example.com	$2a$10$USK7Vwbisn6LlQDhLndIpu3rnXO295fK1KUrOctFSGcI74dGNpzJa	0909012345	Phạm Thị Quỳnh	\N	13
TRANSACTION_STAFF	47	sondo@example.com	$2a$10$eWss3UK4ioUJJhjP4zqvZeG1efe44lDUf0uwBK14hbcRCNH1S7Y.K	0909123456	Đỗ Văn Sơn	\N	14
TRANSACTION_STAFF	48	trangtruong@example.com	$2a$10$mTWIw3XJCA1ncegG4SoCa.bzEO5yCn6F9KdnBgtkfdx6wTtDnmEve	0909234567	Trương Thị Trang	\N	15
TRANSACTION_STAFF	49	uyvu@example.com	$2a$10$3BZrsKiNhnrV3tE3.KrFEuNez6pEfVp8YC7rLQftuSF/x6/DDtlF.	0909345678	Vũ Văn Uy	\N	16
TRANSACTION_STAFF	50	vannguyen@example.com	$2a$10$Wge9yNIKWkMoQXMyBMEQKOiDm.A7j.nAf6tYtVyKJDEVLXpSqoScS	0909456789	Nguyễn Thị Vân	\N	17
TRANSACTION_STAFF	51	xuongtran@example.com	$2a$10$qLGKMqrfBs3L3He3.QKFPO0gMNe1mjuqR3Pb8ryihyy4WldFjmmoO	0909567890	Trần Văn Xương	\N	37
COMPANY_LEADER	52	huydang2773@gmail.com	$2a$10$OzxO5lj/VoAY7G1iPLAZKu/jWitg/zMPdZxvdpkLiIBx/U3yHvdpO	0327614682	Đặng Quang Huy	\N	\N
\.


--
-- Name: commune_id_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.commune_id_seq', 36, true);


--
-- Name: commune_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.commune_seq', 1, false);


--
-- Name: district_id_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.district_id_seq', 21, true);


--
-- Name: district_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.district_seq', 1, false);


--
-- Name: point_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.point_seq', 1, false);


--
-- Name: province_id_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.province_id_seq', 9, true);


--
-- Name: province_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.province_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: magicpost
--

SELECT pg_catalog.setval('public.users_id_seq', 52, true);


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

