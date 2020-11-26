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
    (2, 'Sewing a patchwork quilt', false),
    (2, 'Negotiating the release of a kidnapped person', true);


INSERT INTO public.tbl_questions
(question_content, game_id)
VALUES('Building a new highway', 1),
    ('Learning to ride a horse', 1),
    ('Sewing a patchwork quilt', 1),
    ('Negotiating the release of a kidnapped person', 1),
    ('Learning to speak in public', 1),
    ('Learning to dive', 1),
    ('Building a tree house', 1),
    ('Organizing a party', 1),
    ('Laying a pavement', 1),
    ('Adopting a child', 1),
    ('Knitting a scarf', 1),
    ('Moving house', 1),
    ('Learning a new language', 1),
    ('Growing a beard', 1),
    ('Wring a wedding speech', 1),
    ('Making wine', 1),
    ('Converting a loft', 1),
    ('Making a table', 1),
    ('Writing a song', 1),
    ('Solving an equation', 1),
    ('Pruning a tree', 1),
    ('Making a clay vase', 1),
    ('Building a bridge', 1),
    ('Renovating a house', 1),
    ('Creating a website to sell pet products', 1),
    ('Training to run a marathon', 1),
    ('Writing a book', 1),
    ('Making a Hollywood blockbuster', 1),
    ('Losing weight', 1),
    ('Cooking Sunday lunch', 1),
    ('Building a new house', 1),
    ('Building an extension', 1),
    ('Building a nuclear submarine', 1),
    ('Putting a person on the moon', 1),
    ('Laying a new lawn', 1),
    ('Raising money for charity', 1),
    ('Becoming a bodybuilder', 1),
    ('Learning to play the guitar', 1),
    ('Planning a wedding', 1),
    ('Growing a vegetable patch', 1),
    ('Designing a poster', 1),
    ('Landscaping a garden', 1),
    ('Decorating a house', 1),
    ('Having a baby', 1),
    ('Building a car', 1);

INSERT INTO public.tbl_answered_question_details
(answered_question_id, player_id, answer_content_as_image)
VALUES(1,1,'iterative.png'),
    (1,2,'bigbang.png'),
    (1,3,'incremental.png'),
    (1,4,'iterative.png'),
    (1,5, null);