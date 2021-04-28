CREATE TABLE users (
    userid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    fullname VARCHAR(50) ,
    phonenumber INT ,
    delivery_address VARCHAR(50) ,
    PRIMARY KEY (userid)
);


CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userid INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (userid) REFERENCES users(userid)
);  
CREATE TABLE shopcart (
    cartid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userid INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    foodid INTEGER NOT NULL,
    foodname VARCHAR(50) NOT NULL,
    noffood INT NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (cartid),
    FOREIGN KEY (foodid) REFERENCES foodlist(foodid),
    FOREIGN KEY (userid) REFERENCES users(userid)
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
    body VARCHAR(300) NOT NULL,
    PRIMARY KEY (floor),
    FOREIGN KEY (foodid) REFERENCES foodlist(foodid)
);


#SQL STATMENT
#SELECT Users.username
#FROM users
#INNER JOIN user_roles ON users.username = user_roles.username and user_roles."ROLE"# = 'ROLE_USER';
#UPDATE USERS SET USERNAME ='full', PASSWORD = '{noop}full', FULLNAME ='fullname', PHONENUMBER =546546, DELIVERY_ADDRESS = 'fulladd' WHERE userid = 6;