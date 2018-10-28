CREATE TABLE `usuarios` 
(
  `id` INT NOT NULL AUTO_INCREMENT,
  `apodo` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(255) NOT NULL,
  UNIQUE INDEX `apodo_UNIQUE` (`apodo` ASC),
  PRIMARY KEY (`id`)
);