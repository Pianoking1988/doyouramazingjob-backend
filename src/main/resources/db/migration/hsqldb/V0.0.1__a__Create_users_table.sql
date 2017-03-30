CREATE TABLE users (
    id         INTEGER      GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
    mail       VARCHAR(255) NOT NULL,    
    created    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    createdby  VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);