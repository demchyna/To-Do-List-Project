--
-- PostgreSQL database dump
--

-- Dumped from database version 12.3
-- Dumped by pg_dump version 12.3

-- Started on 2020-09-16 14:33:24

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

DROP DATABASE todolist;
--
-- TOC entry 2934 (class 1262 OID 17162)
-- Name: todolist; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE todolist WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Ukrainian_Ukraine.1251' LC_CTYPE = 'Ukrainian_Ukraine.1251';


\connect todolist

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
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 562 (class 1247 OID 17164)
-- Name: priority_type; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public.priority_type AS ENUM (
    'low',
    'medium',
    'high'
);


SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 17171)
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


--
-- TOC entry 204 (class 1259 OID 17174)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2935 (class 0 OID 0)
-- Dependencies: 204
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 205 (class 1259 OID 17176)
-- Name: states; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.states (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


--
-- TOC entry 206 (class 1259 OID 17179)
-- Name: states_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.states_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2936 (class 0 OID 0)
-- Dependencies: 206
-- Name: states_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.states_id_seq OWNED BY public.states.id;


--
-- TOC entry 207 (class 1259 OID 17181)
-- Name: tasks; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tasks (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    priority character varying(255) NOT NULL,
    todo_id integer NOT NULL,
    state_id integer DEFAULT 1
);


--
-- TOC entry 208 (class 1259 OID 17188)
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tasks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2937 (class 0 OID 0)
-- Dependencies: 208
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tasks_id_seq OWNED BY public.tasks.id;


--
-- TOC entry 209 (class 1259 OID 17190)
-- Name: tasks_todo_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tasks_todo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2938 (class 0 OID 0)
-- Dependencies: 209
-- Name: tasks_todo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tasks_todo_id_seq OWNED BY public.tasks.todo_id;


--
-- TOC entry 210 (class 1259 OID 17192)
-- Name: todo_collaborator; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.todo_collaborator (
    todo_id integer NOT NULL,
    collaborator_id integer NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 17195)
-- Name: todos; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.todos (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    owner_id integer NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 17198)
-- Name: todos_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.todos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2939 (class 0 OID 0)
-- Dependencies: 212
-- Name: todos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.todos_id_seq OWNED BY public.todos.id;


--
-- TOC entry 213 (class 1259 OID 17200)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying NOT NULL,
    role_id integer NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 17206)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2940 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 215 (class 1259 OID 17208)
-- Name: users_role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2941 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.users_role_id_seq OWNED BY public.users.role_id;


--
-- TOC entry 2761 (class 2604 OID 17210)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 2762 (class 2604 OID 17211)
-- Name: states id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.states ALTER COLUMN id SET DEFAULT nextval('public.states_id_seq'::regclass);


--
-- TOC entry 2764 (class 2604 OID 17212)
-- Name: tasks id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks ALTER COLUMN id SET DEFAULT nextval('public.tasks_id_seq'::regclass);


--
-- TOC entry 2765 (class 2604 OID 17213)
-- Name: tasks todo_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks ALTER COLUMN todo_id SET DEFAULT nextval('public.tasks_todo_id_seq'::regclass);


--
-- TOC entry 2766 (class 2604 OID 17214)
-- Name: todos id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.todos ALTER COLUMN id SET DEFAULT nextval('public.todos_id_seq'::regclass);


--
-- TOC entry 2767 (class 2604 OID 17215)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2768 (class 2604 OID 17216)
-- Name: users role_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users ALTER COLUMN role_id SET DEFAULT nextval('public.users_role_id_seq'::regclass);


--
-- TOC entry 2916 (class 0 OID 17171)
-- Dependencies: 203
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'USER');


--
-- TOC entry 2918 (class 0 OID 17176)
-- Dependencies: 205
-- Data for Name: states; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.states (id, name) VALUES (5, 'New');
INSERT INTO public.states (id, name) VALUES (6, 'Doing');
INSERT INTO public.states (id, name) VALUES (7, 'Verify');
INSERT INTO public.states (id, name) VALUES (8, 'Done');


--
-- TOC entry 2920 (class 0 OID 17181)
-- Dependencies: 207
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tasks (id, name, priority, todo_id, state_id) VALUES (6, 'Task #2', 'LOW', 7, 5);
INSERT INTO public.tasks (id, name, priority, todo_id, state_id) VALUES (5, 'Task #1', 'HIGH', 7, 8);
INSERT INTO public.tasks (id, name, priority, todo_id, state_id) VALUES (7, 'Task #3', 'MEDIUM', 7, 6);


--
-- TOC entry 2923 (class 0 OID 17192)
-- Dependencies: 210
-- Data for Name: todo_collaborator; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.todo_collaborator (todo_id, collaborator_id) VALUES (7, 5);
INSERT INTO public.todo_collaborator (todo_id, collaborator_id) VALUES (7, 6);
INSERT INTO public.todo_collaborator (todo_id, collaborator_id) VALUES (10, 6);
INSERT INTO public.todo_collaborator (todo_id, collaborator_id) VALUES (10, 4);
INSERT INTO public.todo_collaborator (todo_id, collaborator_id) VALUES (12, 5);
INSERT INTO public.todo_collaborator (todo_id, collaborator_id) VALUES (12, 4);


--
-- TOC entry 2924 (class 0 OID 17195)
-- Dependencies: 211
-- Data for Name: todos; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (7, 'Mike''s To-Do #1', '2020-09-16 14:00:04.810221', 4);
INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (8, 'Mike''s To-Do #2', '2020-09-16 14:00:11.480271', 4);
INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (9, 'Mike''s To-Do #3', '2020-09-16 14:00:16.351238', 4);
INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (10, 'Nick''s To-Do #1', '2020-09-16 14:14:54.532337', 5);
INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (11, 'Nick''s To-Do #2', '2020-09-16 14:15:04.707176', 5);
INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (12, 'Nora''s To-Do #1', '2020-09-16 14:15:32.464391', 6);
INSERT INTO public.todos (id, title, created_at, owner_id) VALUES (13, 'Nora''s To-Do #2', '2020-09-16 14:15:39.16246', 6);


--
-- TOC entry 2926 (class 0 OID 17200)
-- Dependencies: 213
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users (id, first_name, last_name, email, password, role_id) VALUES (5, 'Nick', 'Green', 'nick@mail.com', '$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC', 2);
INSERT INTO public.users (id, first_name, last_name, email, password, role_id) VALUES (6, 'Nora', 'White', 'nora@mail.com', '$2a$10$yYQaJrHzjOgD5wWCyelp0e1Yv1KEKeqUlYfLZQ1OQvyUrnEcX/rOy', 2);
INSERT INTO public.users (id, first_name, last_name, email, password, role_id) VALUES (4, 'Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);


--
-- TOC entry 2942 (class 0 OID 0)
-- Dependencies: 204
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- TOC entry 2943 (class 0 OID 0)
-- Dependencies: 206
-- Name: states_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.states_id_seq', 8, true);


--
-- TOC entry 2944 (class 0 OID 0)
-- Dependencies: 208
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tasks_id_seq', 7, true);


--
-- TOC entry 2945 (class 0 OID 0)
-- Dependencies: 209
-- Name: tasks_todo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tasks_todo_id_seq', 1, false);


--
-- TOC entry 2946 (class 0 OID 0)
-- Dependencies: 212
-- Name: todos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.todos_id_seq', 13, true);


--
-- TOC entry 2947 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- TOC entry 2948 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_role_id_seq', 1, false);


--
-- TOC entry 2771 (class 2606 OID 17218)
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- TOC entry 2774 (class 2606 OID 17220)
-- Name: states states_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.states
    ADD CONSTRAINT states_pk PRIMARY KEY (id);


--
-- TOC entry 2777 (class 2606 OID 17222)
-- Name: tasks tasks_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pk PRIMARY KEY (id);


--
-- TOC entry 2780 (class 2606 OID 17224)
-- Name: todos todos_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.todos
    ADD CONSTRAINT todos_pk PRIMARY KEY (id);


--
-- TOC entry 2783 (class 2606 OID 17226)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 2769 (class 1259 OID 17227)
-- Name: roles_id_uindex; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX roles_id_uindex ON public.roles USING btree (id);


--
-- TOC entry 2772 (class 1259 OID 17228)
-- Name: states_id_uindex; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX states_id_uindex ON public.states USING btree (id);


--
-- TOC entry 2775 (class 1259 OID 17229)
-- Name: tasks_id_uindex; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX tasks_id_uindex ON public.tasks USING btree (id);


--
-- TOC entry 2778 (class 1259 OID 17230)
-- Name: todos_id_uindex; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX todos_id_uindex ON public.todos USING btree (id);


--
-- TOC entry 2781 (class 1259 OID 17231)
-- Name: users_id_uindex; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);


--
-- TOC entry 2784 (class 2606 OID 17232)
-- Name: tasks task_state_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT task_state_fk FOREIGN KEY (state_id) REFERENCES public.states(id) NOT VALID;


--
-- TOC entry 2785 (class 2606 OID 17237)
-- Name: tasks task_todo_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT task_todo_fk FOREIGN KEY (todo_id) REFERENCES public.todos(id) NOT VALID;


--
-- TOC entry 2786 (class 2606 OID 17242)
-- Name: todo_collaborator todo_collaborator_todo_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.todo_collaborator
    ADD CONSTRAINT todo_collaborator_todo_fk FOREIGN KEY (todo_id) REFERENCES public.todos(id);


--
-- TOC entry 2787 (class 2606 OID 17247)
-- Name: todo_collaborator todo_collaborator_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.todo_collaborator
    ADD CONSTRAINT todo_collaborator_user_fk FOREIGN KEY (collaborator_id) REFERENCES public.users(id);


--
-- TOC entry 2788 (class 2606 OID 17252)
-- Name: todos todo_user_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.todos
    ADD CONSTRAINT todo_user_fk FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- TOC entry 2789 (class 2606 OID 17257)
-- Name: users user_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_role_fk FOREIGN KEY (role_id) REFERENCES public.roles(id) NOT VALID;


-- Completed on 2020-09-16 14:33:25

--
-- PostgreSQL database dump complete
--

