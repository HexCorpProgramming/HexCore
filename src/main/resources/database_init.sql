CREATE DATABASE IF NOT EXISTS hexcore;

USE hexcore;

-- Drone Table
CREATE TABLE IF NOT EXISTS `drone` (
  `id` VARCHAR(255) NOT NULL,
  `drone_id` INT(11) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  `battery_status` TINYINT(1) NOT NULL DEFAULT 0,
  `id_prepend` TINYINT(1) NOT NULL DEFAULT 0,
  `speech_optimization` TINYINT(1) NOT NULL DEFAULT 0,
  `text_glitch` TINYINT(1) NOT NULL DEFAULT 0,
  `battery_capacity` INT(11) NOT NULL DEFAULT 100,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = latin1;

-- Consent Table
CREATE TABLE IF NOT EXISTS `consent` (
  `uuid` binary(16) NOT NULL,
  `id` VARCHAR(255) NOT NULL,
  `droneId` INT(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = latin1;