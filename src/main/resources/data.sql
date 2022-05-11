INSERT INTO role (id, name)
VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

INSERT INTO user (username, password, registered, first_name, last_name, date_of_birth, sex, phone)
VALUES
    ('niko@mail.ru', 'FKJHKYEUYFyfuuyfFFdK2IwjyWefYkMpiBEFlpBwDH.fg0K', '2022-03-04', 'Nick',   'Mort', '1986-09-11', 'm', '+7 555 555 55 55'),
    ('ben@mail.ru', 'FKJHKYEUYFyfuuyfFFdK2IwjyWefYkMpiBEFlpBwDH.fg0K', '2022-03-20', 'Ben',   'Smith', '1980-05-16', 'm', '+7 444 444 44 44'),
    ('tom@mail.ru', 'FKJHKYEUYFyfuuyfFFdK2IwjyWefYkMpiBEFlpBwDH.fg0K', '2022-03-20', 'Tom',   'Smith', '1990-03-14', 'm', '+7 555 444 44 44'),
    ('sam@mail.ru', 'FKJHKYEUYFyfuuyfFFdK2IwjyWefYkMpiBEFlpBwDH.fg0K', '2022-03-11', 'sam',   'Smith', '1967-03-14', 'm', '+7 666 444 44 44'),
    ('jenny@mail.ru', 'FKJHKYEUYFyfuuyfFFdK2IwjyWefYkMpiBEFlpBwDH.fg0K', '2022-03-01', 'jenny',   'Smith', '1988-03-14', 'm', '+7 777 444 44 44');


INSERT INTO user_role (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);

INSERT INTO friendship (id, user_sender, user_receiver, accepted)
VALUES
    (null, 1,  2, true),
    (null, 1,  3, false);

INSERT INTO messages (id, time, sender, receiver, message)
VALUES
    (null, '2018-07-09 10:11:12', 1, 2, 'Привет! Как дела?'),
    (null, '2018-07-10 10:11:12', 2, 1, 'Привет! Спасибо, хорошо=)');