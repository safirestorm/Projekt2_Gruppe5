USE givmig;

    DROP TABLE users;
    DROP TABLE wishlists;
    DROP TABLE wishes;

CREATE TABLE users (
                       username varchar(50) UNIQUE PRIMARY KEY,
                       password varchar(50),
                       firstname varchar(50),
                       lastname varchar(50)
);

CREATE TABLE wishlists (
                           id int AUTO_INCREMENT PRIMARY KEY,
                           userID varchar(50), name varchar(100),
                           date DATE, description LONGTEXT,
                           CONSTRAINT fk_userID FOREIGN KEY (userID)
                               REFERENCES users(username)
);

CREATE TABLE wishes (
                        id int AUTO_INCREMENT PRIMARY KEY,
                        wishlistID int,
                        name varchar(200),
                        price int,
                        link varchar(500),
                        description LONGTEXT,
                        image varchar(100),
                        reservedstatus BOOLEAN,
                        CONSTRAINT fk_wishlistID FOREIGN KEY (wishlistID)
                            REFERENCES wishlists(id)
);

INSERT INTO users (username, password, firstname, lastname) VALUES
                                                                ('raseebach', 'engel', 'Rasmus', 'Seebach'),
                                                                ('joe_pass', 'jazzword', 'Joe', 'Pass'),
                                                                ('notbatman', 'batcave', 'Bruce', 'Wayne'),
                                                                ('testbruger', 'password', 'test', 'bruger');

INSERT INTO wishlists (userID, name, date, description) VALUES
                                                            ('raseebach', 'fødselsdag', '2025-11-03', 'Jeg har fødselsdag, og jeg vil gerne have en ny guitar og en kage'),
                                                            ('joe_pass', 'Festival desires', '2025-06-20', 'I want a nice new bass guitar and a cool car'),
                                                            ('notbatman', 'Totally a real party', '2025-05-09', 'Hr. joker, du er inviteret til fest på Wayne Gården. Kom iført fangedragt og håndjern.'),
                                                            ('notbatman', 'BATPARTY!!!', '2025-05-10', 'JOKEREN ER SIKKERT I FÆNGSEL NU, DET SKAL FEJRES!');

INSERT INTO wishes (wishlistID, name, price, link, description, image, reservedstatus) VALUES (1, 'ny flot guitar', 2400, 'Guitarbasen.dk', 'blue-present.png', false),
                                                                                              (1, 'Lækker kage', 200, 'lagkagehuset.dk', 'gold-present.png', false),
                                                                                              (2, 'Cool new bass guitar', 4000, 'guitarbasen.dk', 'A nice new bass for the jazz', 'green-present.png', true),
                                                                                              (2, 'Fast Ford Mustang', 400000, 'Ford.dk', 'Vroom Vroom, car go fast', 'purple-present.png', false),
                                                                                              (3, 'Black facepaint', 200, 'MakeupFormen.com', 'Jeg skal bruge noget sort ansigtsmaling... fordi det er sejt', 'red-present.png', false),
                                                                                              (3, 'Bat-boomerang', 5000, 'BatmanGear.com', 'En sej ting', 'blue-present.png', true),
                                                                                              (4, 'Klovne tema kage', 200, 'lagkagehuset.dk', 'HAHA, jokeren er i fængsel', 'green-present.png', true);