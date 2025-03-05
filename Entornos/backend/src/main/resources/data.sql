INSERT INTO client(name) VALUES ('Javier Tomás');
INSERT INTO client(name) VALUES ('Llorenç Alfonso');
INSERT INTO client(name) VALUES ('Alejandro Rodríguez');
INSERT INTO client(name) VALUES ('Vicente Monfort');
INSERT INTO client(name) VALUES ('Rodrigo de Melo Pernambuco');
INSERT INTO client(name) VALUES ('Carlos Pérez');
INSERT INTO client(name) VALUES ('María López');
INSERT INTO client(name) VALUES ('Ana García');
INSERT INTO client(name) VALUES ('Luis Fernández');
INSERT INTO client(name) VALUES ('Elena Martínez');

INSERT INTO game(title, age, category_id, author_id) VALUES ('Monopoly', 8, 1, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Catan', 10, 2, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Uno', 7, 3, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Risk', 10, 4, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Pandemic', 12, 5, 5);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Chess', 6, 1, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Scrabble', 10, 2, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Clue', 8, 3, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Ticket to Ride', 10, 4, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Carcassonne', 7, 5, 5);

INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (1, 1, '2023-10-01', '2023-10-15');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (2, 2, '2023-10-05', '2023-10-20');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (3, 3, '2023-10-10', '2023-10-25');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (4, 4, '2025-03-01', '2025-03-10');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (5, 5, '2025-03-02', '2025-03-09');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (6, 6, '2025-03-01', '2025-03-11'); 
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (7, 7, '2025-03-5', '2025-03-15'); 
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (8, 8, '2025-03-01', '2025-03-11'); 
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (9, 9, '2023-10-05', '2023-10-15'); 
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (10, 10, '2023-10-10', '2023-10-20'); 

INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (3, 1, '2023-11-01', '2023-11-15');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (2, 2, '2023-11-05', '2023-11-20');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (4, 3, '2023-11-10', '2023-11-25');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (4, 4, '2025-04-01', '2025-04-10');