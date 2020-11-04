CREATE TABLE tbl_games(
    id INT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY(id)
);

-- CREATE TABLE tbl_answer_groups(
--     id INT NOT NULL,
--     name VARCHAR(255),
--     game_id INT NOT NULL,
--     PRIMARY KEY(id),
--     FOREIGN KEY (game_id) REFERENCES tbl_games(id)
-- );

-- CREATE TABLE tbl_answers(
--     id INT NOT NULL,
--     content VARCHAR(255),
--     image VARCHAR(255),
--     answer_group_id INT NOT NULL,
--     PRIMARY KEY(id),
--     FOREIGN KEY (answer_group_id) REFERENCES tbl_answer_groups(id)
-- );

-- CREATE TABLE tbl_questions(
--     id INT NOT NULL,
--     content VARCHAR(255),
--     image VARCHAR(255),
--     game_id INT NOT NULL,
--     answer_group_id INT NOT NULL,
--     PRIMARY KEY(id),
--     FOREIGN KEY (game_id) REFERENCES tbl_games(id),
--     FOREIGN KEY (answer_group_id) REFERENCES tbl_answer_groups(id)
-- );

-- CREATE TABLE tbl_game_boards(
--     id INT NOT NULL,
--     code VARCHAR(255),
--     game_id INT NOT NULL,
--     PRIMARY KEY(id),
--     FOREIGN KEY (game_id) REFERENCES tbl_games(id)
-- );

-- CREATE TABLE tbl_answered_questions(
--     id INT NOT NULL,
--     content VARCHAR(255),
--     image VARCHAR(255),
--     game_board_id INT NOT NULL,
--     answer_group_id INT NOT NULL,
--     prefered_answer INT NOT NULL,
--     PRIMARY KEY(id),
--     FOREIGN KEY (game_board_id) REFERENCES tbl_game_boards(id),
--     FOREIGN KEY (answer_group_id) REFERENCES tbl_answer_groups(id),
--     FOREIGN KEY (prefered_answer) REFERENCES tbl_answers(id)
-- );

-- CREATE TABLE tbl_players(
--     id INT NOT NULL,
--     name VARCHAR(255),
--     image VARCHAR(255),
--     PRIMARY KEY(id)
-- );

-- CREATE TABLE tbl_answered_question_details(
--     id INT NOT NULL,
--     answered_question_id INT NOT NULL,
--     answer_id INT NOT NULL,
--     player_id INT NOT NULL,
--     PRIMARY KEY(id),
--     FOREIGN KEY (answered_question_id) REFERENCES tbl_answered_questions(id),
--     FOREIGN KEY (answer_id) REFERENCES tbl_answers(id),
--     FOREIGN KEY (player_id) REFERENCES tbl_players(id)
-- );

-- CREATE TABLE tbl_game_board_players(
--     game_board_id INT NOT NULL,
--     player_id INT NOT NULL,
--     FOREIGN KEY (game_board_id) REFERENCES tbl_game_boards(id),
--     FOREIGN KEY (player_id) REFERENCES tbl_players(id)
-- );

INSERT INTO tbl_games(id, name, description)
VALUES (1,'Big bang', 'This ia an Agile Deck game');

