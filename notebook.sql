
-- Database: `notebook`
--

CREATE DATABASE IF NOT EXISTS `notebook`; 
USE `notebook`;
-- --------------------------------------------------------

--
-- Table `user`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB;
CREATE FULLTEXT INDEX `usernameIndex` ON `users` (username);
-- --------------------------------------------------------

-- Create user 'notebook'
create user 'notebook'@'localhost' identified by 'notebook';
grant all on `notebook`.* to 'notebook'@'localhost';