-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema trivuna
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `trivuna` ;

-- -----------------------------------------------------
-- Schema trivuna
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trivuna` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
SHOW WARNINGS;
USE `trivuna` ;

-- -----------------------------------------------------
-- Table `trivuna`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trivuna`.`roles` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `trivuna`.`roles` (
  `rol_id` INT NOT NULL AUTO_INCREMENT,
  `rol_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`rol_id`))
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `rol_name_UNIQUE` ON `trivuna`.`roles` (`rol_name` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `trivuna`.`courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trivuna`.`courses` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `trivuna`.`courses` (
  `course_id` INT NOT NULL AUTO_INCREMENT,
  `course_name` VARCHAR(45) NOT NULL,
  `course_score` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`course_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `trivuna`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trivuna`.`categories` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `trivuna`.`categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `categoriascol_UNIQUE` ON `trivuna`.`categories` (`category_name` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `trivuna`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trivuna`.`users` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `trivuna`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` CHAR(60) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `avatar_url` VARCHAR(255) NULL DEFAULT 'https://randomuser.me/api/portraits/lego/2.jpg',
  `name` VARCHAR(45) NULL,
  `surname1` VARCHAR(45) NULL,
  `surname2` VARCHAR(45) NULL,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_score` INT NOT NULL DEFAULT 0,
  `roles_rol_id` INT NOT NULL,
  `courses_course_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_users_roles1`
    FOREIGN KEY (`roles_rol_id`)
    REFERENCES `trivuna`.`roles` (`rol_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_courses1`
    FOREIGN KEY (`courses_course_id`)
    REFERENCES `trivuna`.`courses` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `email_UNIQUE` ON `trivuna`.`users` (`email` ASC) VISIBLE;

SHOW WARNINGS;
CREATE UNIQUE INDEX `username_UNIQUE` ON `trivuna`.`users` (`username` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_users_roles1_idx` ON `trivuna`.`users` (`roles_rol_id` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_users_courses1_idx` ON `trivuna`.`users` (`courses_course_id` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `trivuna`.`questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trivuna`.`questions` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `trivuna`.`questions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `question_text` LONGTEXT NOT NULL,
  `incorrect_answer1` VARCHAR(255) NOT NULL,
  `incorrect_answer2` VARCHAR(255) NOT NULL,
  `incorrect_answer3` VARCHAR(255) NOT NULL,
  `respuesta_correcta` VARCHAR(255) NOT NULL,
  `is_approved` TINYINT(1) NOT NULL DEFAULT 0,
  `categories_category_id` INT NOT NULL,
  `users_user_id` INT NOT NULL,
  PRIMARY KEY (`question_id`),
  CONSTRAINT `fk_questions_categorias`
    FOREIGN KEY (`categories_category_id`)
    REFERENCES `trivuna`.`categories` (`category_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_questions_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `trivuna`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_questions_categorias_idx` ON `trivuna`.`questions` (`categories_category_id` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_questions_users1_idx` ON `trivuna`.`questions` (`users_user_id` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `trivuna`.`users_answers_questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trivuna`.`users_answers_questions` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `trivuna`.`users_answers_questions` (
  `users_user_id` INT NOT NULL,
  `questions_question_id` INT NOT NULL,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `selected_answer` VARCHAR(45) NULL,
  PRIMARY KEY (`users_user_id`, `questions_question_id`),
  CONSTRAINT `fk_users_has_questions_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `trivuna`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_questions_questions1`
    FOREIGN KEY (`questions_question_id`)
    REFERENCES `trivuna`.`questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_users_has_questions_questions1_idx` ON `trivuna`.`users_answers_questions` (`questions_question_id` ASC) VISIBLE;

SHOW WARNINGS;
CREATE INDEX `fk_users_has_questions_users1_idx` ON `trivuna`.`users_answers_questions` (`users_user_id` ASC) VISIBLE;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
