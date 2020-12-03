--
-- TOC entry 185 (class 1259 OID 16491)
-- Name: tbl_answer_groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_answer_groups (
    id bigint NOT NULL,
    name character varying(255)
);


--
-- TOC entry 186 (class 1259 OID 16494)
-- Name: tbl_answered_question_details; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_answered_question_details (
    id bigint NOT NULL,
    answered_question_id bigint,
    player_id bigint,
    answer_content character varying(255),
    answer_content_as_image character varying(255)
);


--
-- TOC entry 187 (class 1259 OID 16500)
-- Name: tbl_answered_questions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_answered_questions (
    id bigint NOT NULL,
    game_board_id bigint NOT NULL,
    question_content character varying(255),
    question_content_as_image character varying(255),
    answer_content character varying(255),
    answer_content_as_image character varying(255),
    playing boolean
);


--
-- TOC entry 188 (class 1259 OID 16506)
-- Name: tbl_answers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_answers (
    id bigint NOT NULL,
    answer_content character varying(255),
    number_order bigint NOT NULL,
    answer_content_as_image character varying(255),
    answer_group_id bigint NOT NULL,
    game_id bigint,
    question_id bigint
);


--
-- TOC entry 189 (class 1259 OID 16512)
-- Name: tbl_game_boards; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_game_boards (
    id bigint NOT NULL,
    code character varying(50) NOT NULL,
    game_id bigint NOT NULL
);


--
-- TOC entry 190 (class 1259 OID 16515)
-- Name: tbl_games; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_games (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255),
    game_as_image character varying(255)
);


--
-- TOC entry 191 (class 1259 OID 16521)
-- Name: tbl_players; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_players (
    id bigint NOT NULL,
    game_board_id bigint NOT NULL,
    avatar character varying(255),
    name character varying(255) NOT NULL
);


--
-- TOC entry 192 (class 1259 OID 16527)
-- Name: tbl_questions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tbl_questions (
    id bigint NOT NULL,
    question_content character varying(255),
    question_content_as_image character varying(255),
    game_id bigint
);


--
-- TOC entry 2154 (class 0 OID 16491)
-- Dependencies: 185
-- Data for Name: tbl_answer_groups; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_answer_groups VALUES (1, 'Approach set');


--
-- TOC entry 2155 (class 0 OID 16494)
-- Dependencies: 186
-- Data for Name: tbl_answered_question_details; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_answered_question_details VALUES (1, 1, 1, NULL, 'iterative.png');
INSERT INTO public.tbl_answered_question_details VALUES (2, 1, 2, NULL, 'bigbang.png');
INSERT INTO public.tbl_answered_question_details VALUES (3, 1, 3, NULL, 'incremental.png');
INSERT INTO public.tbl_answered_question_details VALUES (4, 1, 4, NULL, 'iterative.png');
INSERT INTO public.tbl_answered_question_details VALUES (5, 1, 5, NULL, NULL);


--
-- TOC entry 2156 (class 0 OID 16500)
-- Dependencies: 187
-- Data for Name: tbl_answered_questions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_answered_questions VALUES (1, 1, 'Building a new highway', NULL, NULL, NULL, true);
INSERT INTO public.tbl_answered_questions VALUES (2, 1, 'Learning to ride a horse', NULL, NULL, NULL, false);
INSERT INTO public.tbl_answered_questions VALUES (3, 2, 'Sewing a patchwork quilt', NULL, NULL, NULL, false);
INSERT INTO public.tbl_answered_questions VALUES (4, 2, 'Negotiating the release of a kidnapped person', NULL, NULL, NULL, true);


--
-- TOC entry 2157 (class 0 OID 16506)
-- Dependencies: 188
-- Data for Name: tbl_answers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_answers VALUES (1, 'Iterative', 1, 'iterative.png', 1, 1, NULL);
INSERT INTO public.tbl_answers VALUES (2, 'Incremental', 2, 'incremental.png', 1, 1, NULL);
INSERT INTO public.tbl_answers VALUES (3, 'Bigbang', 3, 'bigbang.png', 1, 1, NULL);


--
-- TOC entry 2158 (class 0 OID 16512)
-- Dependencies: 189
-- Data for Name: tbl_game_boards; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_game_boards VALUES (1, 'b4661d5e-f296-4cf6-887d-cfa0f97d1f36', 1);
INSERT INTO public.tbl_game_boards VALUES (2, 'asd6gfga-f296-sdf3-0fn2-asf86gc1crt2', 1);


--
-- TOC entry 2159 (class 0 OID 16515)
-- Dependencies: 190
-- Data for Name: tbl_games; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_games VALUES (1, 'A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban', 'Iterative - Incremental - Big Bang', NULL);


--
-- TOC entry 2160 (class 0 OID 16521)
-- Dependencies: 191
-- Data for Name: tbl_players; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_players VALUES (1, 1, NULL, 'Strawberries');
INSERT INTO public.tbl_players VALUES (2, 1, NULL, 'Banana');
INSERT INTO public.tbl_players VALUES (3, 1, NULL, 'Orange');
INSERT INTO public.tbl_players VALUES (4, 1, NULL, 'Mango');
INSERT INTO public.tbl_players VALUES (5, 1, NULL, 'Durian');


--
-- TOC entry 2161 (class 0 OID 16527)
-- Dependencies: 192
-- Data for Name: tbl_questions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tbl_questions VALUES (1, 'Building a new highway', NULL, 1);
INSERT INTO public.tbl_questions VALUES (2, 'Learning to ride a horse', NULL, 1);
INSERT INTO public.tbl_questions VALUES (3, 'Sewing a patchwork quilt', NULL, 1);
INSERT INTO public.tbl_questions VALUES (4, 'Negotiating the release of a kidnapped person', NULL, 1);
INSERT INTO public.tbl_questions VALUES (5, 'Learning to speak in public', NULL, 1);
INSERT INTO public.tbl_questions VALUES (6, 'Learning to dive', NULL, 1);
INSERT INTO public.tbl_questions VALUES (7, 'Building a tree house', NULL, 1);
INSERT INTO public.tbl_questions VALUES (8, 'Organizing a party', NULL, 1);
INSERT INTO public.tbl_questions VALUES (9, 'Laying a pavement', NULL, 1);
INSERT INTO public.tbl_questions VALUES (10, 'Adopting a child', NULL, 1);
INSERT INTO public.tbl_questions VALUES (11, 'Knitting a scarf', NULL, 1);
INSERT INTO public.tbl_questions VALUES (12, 'Moving house', NULL, 1);
INSERT INTO public.tbl_questions VALUES (13, 'Learning a new language', NULL, 1);
INSERT INTO public.tbl_questions VALUES (14, 'Growing a beard', NULL, 1);
INSERT INTO public.tbl_questions VALUES (15, 'Wring a wedding speech', NULL, 1);
INSERT INTO public.tbl_questions VALUES (16, 'Making wine', NULL, 1);
INSERT INTO public.tbl_questions VALUES (17, 'Converting a loft', NULL, 1);
INSERT INTO public.tbl_questions VALUES (18, 'Making a table', NULL, 1);
INSERT INTO public.tbl_questions VALUES (19, 'Writing a song', NULL, 1);
INSERT INTO public.tbl_questions VALUES (20, 'Solving an equation', NULL, 1);
INSERT INTO public.tbl_questions VALUES (21, 'Pruning a tree', NULL, 1);
INSERT INTO public.tbl_questions VALUES (22, 'Making a clay vase', NULL, 1);
INSERT INTO public.tbl_questions VALUES (23, 'Building a bridge', NULL, 1);
INSERT INTO public.tbl_questions VALUES (24, 'Renovating a house', NULL, 1);
INSERT INTO public.tbl_questions VALUES (25, 'Creating a website to sell pet products', NULL, 1);
INSERT INTO public.tbl_questions VALUES (26, 'Training to run a marathon', NULL, 1);
INSERT INTO public.tbl_questions VALUES (27, 'Writing a book', NULL, 1);
INSERT INTO public.tbl_questions VALUES (28, 'Making a Hollywood blockbuster', NULL, 1);
INSERT INTO public.tbl_questions VALUES (29, 'Losing weight', NULL, 1);
INSERT INTO public.tbl_questions VALUES (30, 'Cooking Sunday lunch', NULL, 1);
INSERT INTO public.tbl_questions VALUES (31, 'Building a new house', NULL, 1);
INSERT INTO public.tbl_questions VALUES (32, 'Building an extension', NULL, 1);
INSERT INTO public.tbl_questions VALUES (33, 'Building a nuclear submarine', NULL, 1);
INSERT INTO public.tbl_questions VALUES (34, 'Putting a person on the moon', NULL, 1);
INSERT INTO public.tbl_questions VALUES (35, 'Laying a new lawn', NULL, 1);
INSERT INTO public.tbl_questions VALUES (36, 'Raising money for charity', NULL, 1);
INSERT INTO public.tbl_questions VALUES (37, 'Becoming a bodybuilder', NULL, 1);
INSERT INTO public.tbl_questions VALUES (38, 'Learning to play the guitar', NULL, 1);
INSERT INTO public.tbl_questions VALUES (39, 'Planning a wedding', NULL, 1);
INSERT INTO public.tbl_questions VALUES (40, 'Growing a vegetable patch', NULL, 1);
INSERT INTO public.tbl_questions VALUES (41, 'Designing a poster', NULL, 1);
INSERT INTO public.tbl_questions VALUES (42, 'Landscaping a garden', NULL, 1);
INSERT INTO public.tbl_questions VALUES (43, 'Decorating a house', NULL, 1);
INSERT INTO public.tbl_questions VALUES (44, 'Having a baby', NULL, 1);
INSERT INTO public.tbl_questions VALUES (45, 'Building a car', NULL, 1);


-- Completed on 2020-12-03 16:27:24

--
-- PostgreSQL database dump complete
--

