INSERT INTO client(name) VALUES ('Javier Tomás');
INSERT INTO client(name) VALUES ('Llorenç Alfonso');
INSERT INTO client(name) VALUES ('Alejandro Rodríguez');
INSERT INTO client(name) VALUES ('Vicente Monfort');
INSERT INTO client(name) VALUES ('Rodrigo de Melo Pernambuco');

INSERT INTO game(title, age, category_id, author_id) VALUES ('Monopoly', 8, 1, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Catan', 10, 2, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Uno', 7, 3, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Risk', 10, 4, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Pandemic', 12, 5, 5);

INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (1, 1, '2023-10-01', '2023-10-15');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (2, 2, '2023-10-05', '2023-10-20');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (3, 3, '2023-10-10', '2023-10-25');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (4, 4, '2023-10-15', '2023-10-30');
INSERT INTO loan(game_id, client_id, loan_date, return_date) VALUES (5, 5, '2023-10-20', '2023-11-05');