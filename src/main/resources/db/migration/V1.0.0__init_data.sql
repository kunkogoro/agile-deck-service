-- table tbl_games;
INSERT INTO tbl_games (id, name, description) 
VALUES (1, 'Iterative - Incremental - Big Bang', 'A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban');

-- table tbl_answer_groups;
INSERT INTO tbl_answer_groups (id, name)
VALUES (1, 'Approach set');

-- table tbl_answers;
INSERT INTO tbl_answers (id, answer_content, number_order, answer_content_as_image, answer_group_id, game_id)
VALUES (1, 'Iterative', 1, 'iterative.png', 1, 1),
    (2, 'Incremental', 2, 'incremental.png', 1, 1),
    (3, 'Bigbang', 3, 'bigbang.png', 1, 1);

INSERT INTO public.tbl_game_boards
(id, code, game_id)
VALUES(1, 'b4661d5e-f296-4cf6-887d-cfa0f97d1f36', 1),
    (2, 'asd6gfga-f296-sdf3-0fn2-asf86gc1crt2', 1);

INSERT INTO public.tbl_players (id, name, game_board_id)
VALUES(1,'Strawberries', 1),
    (2,'Banana', 1),
    (3,'Orange', 1),
    (4,'Mango', 1),
    (5,'Durian', 1);

INSERT INTO public.tbl_answered_questions (id, game_board_id, question_content, playing)
VALUES(1,1,'Building a new highway', true),
    (2,1,'Learning to ride a horse', false),
    (3,1,'Sewing a patchwork quilt', false),
    (4,1,'Negotiating the release of a kidnapped person', false);


INSERT INTO public.tbl_questions (id, question_content, game_id)
VALUES(1,'Building a new highway', 1),
    (2,'Learning to ride a horse', 1),
    (3,'Sewing a patchwork quilt', 1),
    (4,'Negotiating the release of a kidnapped person', 1),
    (5,'Learning to speak in public', 1),
    (6,'Learning to dive', 1),
    (7,'Building a tree house', 1);

INSERT INTO public.tbl_answered_question_details
(id, answered_question_id, player_id, answer_content_as_image)
VALUES(1,1,1,'iterative.png'),
    (2,1,2,'bigbang.png'),
    (3,1,3,'incremental.png'),
    (4,1,4,'iterative.png'),
    (5,1,5, null);