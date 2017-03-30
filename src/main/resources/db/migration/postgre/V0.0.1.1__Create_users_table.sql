CREATE TABLE users (
    id         SERIAL,
    mail       VARCHAR(255) NOT NULL,    
    created    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    createdby  VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);