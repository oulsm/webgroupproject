CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('keith', '{noop}keithpw');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users VALUES ('vanessa', '{noop}vanessapw');
INSERT INTO user_roles(username, role) VALUES ('vanessa', 'ROLE_ADMIN');

INSERT INTO users VALUES ('kevin', '{noop}kevinpw');
INSERT INTO user_roles(username, role) VALUES ('kevin', 'ROLE_USER');
CREATE TABLE foodlist (
    foodid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    foodname VARCHAR(50) NOT NULL,
    description VARCHAR(300) NOT NULL,
    price DOUBLE NOT NULL,
    noffood INT NOT NULL,
    PRIMARY KEY (foodid)
);

CREATE TABLE comments (
    foodid INTEGER NOT NULL ,
    floor INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    comment VARCHAR(300) NOT NULL,
    PRIMARY KEY (floor),
    FOREIGN KEY (foodid) REFERENCES foodlist(foodid)
);