-- table tbl_games;
INSERT INTO tbl_games (name, description) 
VALUES ('Iterative - Incremental - Big Bang', 'A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban');

-- table tbl_answer_groups;
INSERT INTO tbl_answer_groups (name)
VALUES ('Approach set');

-- table tbl_answers;
INSERT INTO tbl_answers (answer_content, number_order, answer_content_as_image, answer_group_id, game_id)
VALUES ('Iterative', 1, 'iterative.png', 1, 1),
    ('Incremental', 2, 'incremental.png', 1, 1),
    ('Bigbang', 3, 'bigbang.png', 1, 1);

INSERT INTO public.tbl_game_boards
(code, game_id)
VALUES('b4661d5e-f296-4cf6-887d-cfa0f97d1f36', 1),
    ('asd6gfga-f296-sdf3-0fn2-asf86gc1crt2', 1);


INSERT INTO public.tbl_players
(name, game_board_id)
VALUES('Strawberries', 1),
    ('Banana', 1),
    ('Orange', 1),
    ('Mango', 1),
    ('Durian', 1);

INSERT INTO public.tbl_answered_questions
(game_board_id, question_content, playing)
VALUES(1, 'Building a new highway', true),
    (1, 'Learning to ride a horse', false),
    (1, 'Sewing a patchwork quilt', false),
    (1, 'Negotiating the release of a kidnapped person', false);


INSERT INTO public.tbl_questions
(question_content, game_id)
VALUES('Building a new highway', 1),
    ('Learning to ride a horse', 1),
    ('Sewing a patchwork quilt', 1),
    ('Negotiating the release of a kidnapped person', 1),
    ('Learning to speak in public', 1),
    ('Learning to dive', 1),
    ('Building a tree house', 1);

INSERT INTO public.tbl_answered_question_details
(answered_question_id, player_id, answer_content_as_image)
VALUES(1,1,'iterative.png'),
    (1,2,'bigbang.png'),
    (1,3,'incremental.png'),
    (1,4,'iterative.png'),
    (1,5, null);