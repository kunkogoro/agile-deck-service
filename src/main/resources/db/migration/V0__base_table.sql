-- tbl_games definition

-- Drop table

-- DROP TABLE tbl_games;

CREATE TABLE tbl_games (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	description varchar(255) NULL,
	"name" varchar(255) NULL,
	CONSTRAINT tbl_games_pkey PRIMARY KEY (id)
);


-- tbl_players definition

-- Drop table

-- DROP TABLE tbl_players;

CREATE TABLE tbl_players (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	image varchar(255) NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT tbl_players_pkey PRIMARY KEY (id)
);


-- tbl_answer_groups definition

-- Drop table

-- DROP TABLE tbl_answer_groups;

CREATE TABLE tbl_answer_groups (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"name" varchar(255) NULL,
	CONSTRAINT tbl_answer_groups_pkey PRIMARY KEY (id)
);


-- tbl_questions definition

-- Drop table

-- DROP TABLE tbl_questions;

CREATE TABLE tbl_questions (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	question_content varchar(255) NULL,
	question_content_as_image varchar(255) NULL,	
	game_id int8 NULL,
	CONSTRAINT tbl_questions_pkey PRIMARY KEY (id),
	CONSTRAINT fk4wvlmdl3jags539kdh2wns30i FOREIGN KEY (game_id) REFERENCES tbl_games(id)	
);

-- tbl_answers definition

-- Drop table

-- DROP TABLE tbl_answers;

CREATE TABLE tbl_answers (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	answer_content varchar(255) NULL,
	answer_content_as_image varchar(255) NULL,
	answer_group_id int8 NOT NULL,
	game_id int8 NULL,
	question_id int8 NULL,
	CONSTRAINT tbl_answers_pkey PRIMARY KEY (id),
	CONSTRAINT fkbdr5m82d7su30wvty1x0jm18y FOREIGN KEY (answer_group_id) REFERENCES tbl_answer_groups(id),
	CONSTRAINT fkg1buo16vfos9osw2ly84u310n FOREIGN KEY (game_id) REFERENCES tbl_games(id),
	CONSTRAINT fk_tbl_answers_tbl_questions FOREIGN KEY (question_id) REFERENCES tbl_questions(id)
);


-- tbl_game_boards definition

-- Drop table

-- DROP TABLE tbl_game_boards;

CREATE TABLE tbl_game_boards (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	code varchar(50) NOT NULL,
	game_id int8 NOT NULL,
	CONSTRAINT tbl_game_boards_pkey PRIMARY KEY (id),
	CONSTRAINT uk_pp3nijdebq9jd016eyh2gm8cq UNIQUE (code),
	CONSTRAINT fk1ylk50aruc8mai4lp94mo53t6 FOREIGN KEY (game_id) REFERENCES tbl_games(id)
);

-- tbl_answered_questions definition

-- Drop table

-- DROP TABLE tbl_answered_questions;

CREATE TABLE tbl_answered_questions (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	game_board_id int8 NOT NULL,
	question_content varchar(255) NULL,
	question_content_as_image varchar(255) NULL,	
	answer_group_id int8 NOT NULL,
	answer_content varchar(255) NULL,
	answer_content_as_image varchar(255) NULL,
	CONSTRAINT tbl_answered_questions_pkey PRIMARY KEY (id),
	CONSTRAINT fkkrwp7x0nk1uhxvkw7dv9f7xol FOREIGN KEY (game_board_id) REFERENCES tbl_game_boards(id)
);



-- tbl_answered_question_details definition

-- Drop table

-- DROP TABLE tbl_answered_question_details;

CREATE TABLE tbl_answered_question_details (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,	
	answered_question_id int8 NULL,
	player_id int8 NULL,
	answer_content varchar(255) NULL,
	answer_content_as_image varchar(255) NULL,	
	CONSTRAINT tbl_answered_question_details_pkey PRIMARY KEY (id),
	CONSTRAINT fk2blai0harpa4dfiecxyl7d238 FOREIGN KEY (answered_question_id) REFERENCES tbl_answered_questions(id),
	CONSTRAINT fkgygrv6fcvhr9e78xp1btatm6m FOREIGN KEY (player_id) REFERENCES tbl_players(id)
);