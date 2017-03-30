CREATE TABLE jobs (
    id         INTEGER        GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
    user       INTEGER 		  NOT NULL,
    content    VARCHAR(65535) NOT NULL,    
    created    TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    createdby  VARCHAR(255)   NOT NULL,
    PRIMARY KEY(id)
);