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
    PRIMARY KEY (user_role_id)
);  
CREATE TABLE foodlist (
    foodid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    foodname VARCHAR(50) NOT NULL,
    description VARCHAR(300) NOT NULL,
    price DOUBLE NOT NULL,
    noffood INT NOT NULL,
    PRIMARY KEY (foodid)
);
CREATE TABLE comments (
    floor INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    foodid INTEGER NOT NULL , 
    userid INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    body VARCHAR(300) NOT NULL,
    PRIMARY KEY (floor),
    FOREIGN KEY (userid) REFERENCES users(userid),
    FOREIGN KEY (foodid) REFERENCES foodlist(foodid)
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
CREATE TABLE shophist (
    cartid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userid INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    foodid INTEGER NOT NULL,
    foodname VARCHAR(50) NOT NULL,
    noffood INT NOT NULL,
    price DOUBLE NOT NULL,
    orderdate timestamp default CURRENT_TIMESTAMP,
    PRIMARY KEY (cartid)
);

CREATE TABLE favorite(
    cartid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userid INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    foodid INTEGER NOT NULL,
    foodname VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (cartid)
);



INSERT INTO  users(username,password , fullname, phonenumber, delivery_address) VALUES ('keith', '{noop}keithpw','keith Ip', 68865, 'ou computer');
INSERT INTO user_roles(userid, username, role) VALUES (1,'keith', 'ROLE_USER');
INSERT INTO user_roles(userid, username, role) VALUES (1,'keith', 'ROLE_ADMIN');

INSERT INTO  users(username,password , fullname, phonenumber, delivery_address) VALUES ('vanessa', '{noop}vanessapw','vanessa Ng', 8556723, 'ou science');
INSERT INTO user_roles(userid, username, role) VALUES (2,'vanessa', 'ROLE_ADMIN');

INSERT INTO  users(username,password , fullname, phonenumber, delivery_address) VALUES ('kevin', '{noop}kevinpw','kevin chu', 56789, 'ou computer science');
INSERT INTO user_roles(userid, username, role) VALUES (3,'kevin', 'ROLE_USER');

INSERT INTO  users(username,password , fullname, phonenumber, delivery_address) VALUES ('tim', '{noop}lui','tim lui', 7623456, 'SSP');
INSERT INTO user_roles(userid, username, role) VALUES (4,'tim', 'ROLE_ADMIN');

INSERT INTO  users(username,password , fullname, phonenumber, delivery_address)VALUES ('lui', '{noop}tim','lui tim', 923432, 'HMT');
INSERT INTO user_roles(userid, username, role) VALUES (5,'lui', 'ROLE_USER');

INSERT INTO  users(username,password , fullname, phonenumber, delivery_address)VALUES ('wolve', '{noop}wolvepw','handsome wolve', 68865, 'KT');
INSERT INTO user_roles(userid, username, role) VALUES (6,'wolve', 'ROLE_ADMIN');

INSERT INTO users(username,password , fullname, phonenumber, delivery_address) VALUES(	'wow',	'{noop}wow',	'wowow',	54534,	'net bean');
INSERT INTO user_roles(userid, username, role) VALUES (7,'wow', 'ROLE_USER');



INSERT INTO FOODLIST(FOODNAME, DESCRIPTION, PRICE, NOFFOOD) VALUES('Deluxe Breakfast','	Set yourself up in style with a protein-packed start of sizzling sausage, scrambled eggs and hash brown with mouth-watering toasted muffins to finish.',40.0,	100);
INSERT INTO FOODLIST(FOODNAME, DESCRIPTION, PRICE, NOFFOOD) VALUES('Sausage McMuffin with Egg',	'Marvel a moment at this classic combination of sausage and Fabulous fresh farm egg, served in a lightly toasted muffin then get stuck in!',	24.0,	500);
INSERT INTO FOODLIST(FOODNAME, DESCRIPTION, PRICE, NOFFOOD) VALUES('Grilled Chicken Twisty Pasta',	'Get a new twist on breakfast with a fresh and juicy grilled boneless chicken patty together with twisty pasta, hot chicken or tonkotsu broth and a healthy heap of vigorous veggies for the perfect start to any day.',	37.0,	50);
INSERT INTO FOODLIST(FOODNAME, DESCRIPTION, PRICE, NOFFOOD) VALUES('McSpicy Chicken Filet',	'Pep up your taste buds with this chicken thigh patty deep-fried in a crispy and spicy batter, served on a bed of fresh lettuce topped with mayonnaise, and bundled up in a sesame seed bun.',	36.7,	300);
INSERT INTO FOODLIST(FOODNAME, DESCRIPTION, PRICE, NOFFOOD) VALUES('Big Mac',	'You need a big bite of this mega-mouthwatering classic of two all beef patties with lashings of unbeatable sauce, set on a sea of green lettuce, swamped in tangy pickle and onions, served with delicious melted cheese â all sandwiched in a sesame seed bun!',	42.6,	0);

INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(5,	3,	'kevin' ,'yummy ');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  2,	3,	'kevin',	'the egg not fresh...');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  1,	3,	'kevin',	'I like it');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  4,	5,	'lui',	'tasty ');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  4,	4,	'tim',	'Chicken good!');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  5,	4,	'tim',	'same feel as you kevin.');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  4,	7,	'wow',	'nice');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(  5,	7,	'wow',	'sausage is the best');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES( 3,	7,	'wow',	'no one here?');
INSERT INTO COMMENTS(FOODID, USERID, USERNAME, BODY)   VALUES(3, 	7,	'wow',	'this so tasty~');
