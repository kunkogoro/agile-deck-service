-- table tbl_games;
INSERT INTO tbl_games (id, name, description) 
VALUES (1, 'Iterative - Incremental - Big Bang', 'A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban');

-- table tbl_answer_groups;
INSERT INTO tbl_answer_groups (id, name, game_id)
VALUES (1, 'Approach set', 1);

-- table tbl_answers;
INSERT INTO tbl_answers (id, content, content_as_image, answer_group_id)
VALUES (1, 'Iterative', 'iterative.png', 1),
    (2, 'Incremental', 'incremental.png', 1),
    (3, 'Bigbang', 'bigbang.png', 1);