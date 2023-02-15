CREATE TABLE role
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE account_role
(
    id      INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (id, role_id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (id) REFERENCES users (id)
);

INSERT INTO role (name)
VALUES ('ROLE_ADMIN');
INSERT INTO role (name)
VALUES ('ROLE_USER');
INSERT INTO role (name)
VALUES ('ROLE_WORKER');
INSERT INTO role (name)
VALUES ('ROLE_OWNER');