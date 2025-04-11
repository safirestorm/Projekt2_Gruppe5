CREATE DATABASE givmig;

USE givmig;

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

-- Brugere
INSERT INTO users (username, password, firstname, lastname) VALUES
('laura.madsen', 'hemmelig123', 'Laura', 'Madsen'),
('emil.jensen', 'kodeord456', 'Emil', 'Jensen'),
('sofia.larsen', 'ønske789', 'Sofia', 'Larsen'),
('mikkel.andersen', 'jul2024', 'Mikkel', 'Andersen');

-- Ønskelister
INSERT INTO wishlists (userID, name, date, description) VALUES
('laura.madsen', 'Fødselsdagsønsker', '2025-07-14', 'Mine ønsker til fødselsdagen i juli'),
('emil.jensen', 'Jul 2025', '2025-12-24', 'Juleønsker og inspiration'),
('sofia.larsen', 'Flytte hjemmefra', '2025-05-01', 'Alt det jeg mangler til lejligheden'),
('mikkel.andersen', 'Bryllupsgaver', '2025-08-15', 'Liste til vores bryllup');

-- Ønsker
INSERT INTO wishes (wishlistID, name, price, link, description, image, reservedstatus) VALUES
(1, 'Airfryer', 799, 'https://eksempel.dk/airfryer', 'En lille og effektiv airfryer', 'airfryer.jpg', false),
(1, 'Gavekort til Matas', 300, 'https://matas.dk/gavekort', 'Til skønhedsprodukter og parfume', 'matas.jpg', false),
(2, 'Nintendo Switch', 2500, 'https://eksempel.dk/switch', 'Til hyggelige spilaftener', 'switch.jpg', false),
(2, 'Uldsokker', 120, 'https://eksempel.dk/uldsokker', 'Lækre varme sokker', 'sokker.jpg', true),
(3, 'Kaffemaskine', 699, 'https://eksempel.dk/kaffe', 'En lille kaffemaskine til køkkenet', 'kaffe.jpg', false),
(3, 'Skærebræt i træ', 150, 'https://eksempel.dk/skarebraet', 'Et flot, solidt skærebræt', 'braet.jpg', false),
(4, 'Vinkaraffel', 349, 'https://eksempel.dk/karaffel', 'Til vores kommende hjem', 'karaffel.jpg', false),
(4, 'Sengesæt i hør', 600, 'https://eksempel.dk/seng', 'Lækkert og blødt sengesæt', 'sengesæt.jpg', false);



