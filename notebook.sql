-- Database: `notebook`
CREATE DATABASE IF NOT EXISTS `notebook`; 
USE `notebook`;

-- Table `user` 
CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB;

-- Create index if exists
DROP PROCEDURE IF EXISTS create_index;
DELIMITER $
CREATE PROCEDURE create_index()  
BEGIN  
  SELECT COUNT(*) INTO @cnt FROM information_schema.statistics WHERE table_name='users' AND index_name='usernameIndex' ;  
  IF @cnt =0 THEN   
    CREATE INDEX `usernameIndex` ON `users` (username);
  END IF;
END 
$
DELIMITER ;
CALL create_index();

-- Create user 'notebook'
DROP PROCEDURE IF EXISTS create_user;
DELIMITER $
CREATE PROCEDURE create_user()  
BEGIN
	SELECT COUNT(*) INTO @cnt FROM mysql.user WHERE user = 'notebook' and host='localhost';
	IF @cnt =0 THEN
		create user 'notebook'@'localhost' identified by 'notebook';
		grant all on `notebook`.* to 'notebook'@'localhost';
	END IF;
END 
$
DELIMITER ;
CALL create_user();