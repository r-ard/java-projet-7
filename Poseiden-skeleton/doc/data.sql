CREATE DATABASE  IF NOT EXISTS `poseidon` DEFAULT ENCRYPTION='N';
USE `poseidon`;

DROP TABLE IF EXISTS `bidlist`;

CREATE TABLE `bidlist` (
  id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DOUBLE,
  askQuantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidListDate TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `trade`;

CREATE TABLE `trade` (
  id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `curvepoint`;

CREATE TABLE `curvepoint` (
  id INT NOT NULL AUTO_INCREMENT,
  curveId tinyint,
  asOfDate TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creationDate TIMESTAMP ,

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `rating`;

CREATE TABLE rating (
  id INT NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber tinyint,

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `rulename`;

CREATE TABLE `rulename` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (id)
);

insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2y$10$bAAINv7KttN9AAHtb7T6WeSF2uuZQo8/2vYRXLFS6rMMlX2qGs29K", "ADMIN");
insert into Users(fullname, username, password, role) values("User", "user", "$2y$10$WOJeTI4wmj7ptvvKvo23DOGS9qglR/m6NMdoobqNDin.I36yzownG", "USER");