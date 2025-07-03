-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Alunos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Alunos` (
  `ID_Aluno` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Idade` INT(2) NOT NULL,
  `Telefone` VARCHAR(45) NOT NULL,
  `table1_idtable1` INT NOT NULL,
  PRIMARY KEY (`ID_Aluno`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Livro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Livro` (
  `ID_Livro` INT NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `Genero` VARCHAR(45) NOT NULL,
  `Autor` VARCHAR(45) NOT NULL,
  `ISBN` INT(9) NOT NULL,
  `table1_idtable1` INT NOT NULL,
  PRIMARY KEY (`ID_Livro`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Emprestimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Emprestimo` (
  `Alunos_ID_Aluno` INT NOT NULL,
  `Livro_ID_Livro` INT NOT NULL,
  PRIMARY KEY (`Alunos_ID_Aluno`, `Livro_ID_Livro`),
  INDEX `fk_Alunos_has_Livro_Livro1_idx` (`Livro_ID_Livro` ASC) VISIBLE,
  INDEX `fk_Alunos_has_Livro_Alunos_idx` (`Alunos_ID_Aluno` ASC) VISIBLE,
  CONSTRAINT `fk_Alunos_has_Livro_Alunos`
    FOREIGN KEY (`Alunos_ID_Aluno`)
    REFERENCES `mydb`.`Alunos` (`ID_Aluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alunos_has_Livro_Livro1`
    FOREIGN KEY (`Livro_ID_Livro`)
    REFERENCES `mydb`.`Livro` (`ID_Livro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
