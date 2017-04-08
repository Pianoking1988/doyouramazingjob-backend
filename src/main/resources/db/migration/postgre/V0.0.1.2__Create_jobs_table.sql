CREATE TABLE jobs (
    id         SERIAL,
    userid     INTEGER 		NOT NULL,    
    content    TEXT         NOT NULL,    
    created    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    createdby  VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);