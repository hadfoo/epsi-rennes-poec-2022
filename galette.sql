CREATE USER 'antoine_galette'@'localhost' IDENTIFIED BY 'galette';

CREATE database galette;

GRANT ALL PRIVILEGES ON galette.* TO 'antoine_galette'@'localhost';

USE galette;


CREATE TABLE user
(
    username VARCHAR(255) PRIMARY KEY NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE galette
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    libelle VARCHAR(255) UNIQUE
);

CREATE TABLE ingredient
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    libelle VARCHAR(255) UNIQUE,
    nbCalories INTEGER,
    prix FLOAT
);

CREATE TABLE galette_has_ingredient
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    galetteId INTEGER NOT NULL,
    ingredientId INTEGER NOT NULL,
    FOREIGN KEY(galetteId) REFERENCES galette(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(ingredientId) REFERENCES ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE commande
(
    numero INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    emailClient VARCHAR(255)
);

CREATE TABLE panier
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    dateCreation DATETIME
);

CREATE TABLE panier_has_galette
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    panierId INTEGER,
    galetteId INTEGER,
    FOREIGN KEY(galetteId) REFERENCES galette(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(panierId) REFERENCES panier(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE role
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    libelle VARCHAR(255) UNIQUE
);

CREATE TABLE user_has_role
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    roleId INTEGER NOT NULL,
    dateModification DATETIME NOT NULL,
    FOREIGN KEY(username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(roleId) REFERENCES role(id) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO ingredient (libelle, nbCalories, prix) VALUES ("beurre", 14, 1.5), ("oeuf", 76, 2.1), ("fromage", 76, 2.1), ("jambon", 58, 3.1), ("champignon", 18, 2.5);

DELETE FROM galette;

ALTER TABLE galette AUTO_INCREMENT = 1;

INSERT INTO galette (libelle) VALUES ("beurre"), ("oeuf"), ("fromage"), ("jambon-oeuf"), ("complète"), ("forestière");

DELETE FROM galette_has_ingredient;

ALTER TABLE galette_has_ingredient AUTO_INCREMENT = 1;

INSERT INTO galette_has_ingredient (galetteId, ingredientId) 
VALUES (1, 1),
(2, 1), (2, 2), 
(3, 1), (3, 3),
(4, 1), (4, 2), (4, 4),
(5, 1), (5, 2), (5, 3), (5, 4),
(6, 1), (6, 2), (6, 3), (6, 4), (6, 5);
