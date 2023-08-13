CREATE TABLE user (
       id BIGINT primary key not null AUTO_INCREMENT,
       username varchar(255),
       password varchar(255),
       enabled boolean #Dependiento de la base de datos que se use, en este proyecto es mariadb
);

CREATE TABLE roles (
       user_id BIGINT NOT NULL,
       role VARCHAR(50),
       descripcion VARCHAR(200),
       CONSTRAINT fk_roles_users FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE UNIQUE INDEX ix_roles_users on roles(user_id, role);

INSERT INTO roles (user_id, role) VALUES (1, "ADMIN");