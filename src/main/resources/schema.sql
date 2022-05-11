DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS user;
CREATE TABLE role
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    registered DATE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE NOT NULL,
    sex VARCHAR(1) NOT NULL,
    phone VARCHAR(255),
    interests VARCHAR(255)
);

CREATE TABLE user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);




CREATE TABLE friendship
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_sender INT NOT NULL,
    user_receiver INT NOT NULL,
    accepted BOOLEAN DEFAULT(0) NOT NULL,
    FOREIGN KEY (user_sender) REFERENCES user (id),
    FOREIGN KEY (user_receiver) REFERENCES user (id)
);


CREATE TABLE messages
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    time TIMESTAMP NOT NULL,
    message VARCHAR(255),
    sender INT NOT NULL,
    receiver INT NOT NULL,
    FOREIGN KEY (sender) REFERENCES user (id),
    FOREIGN KEY (receiver) REFERENCES user (id)
);