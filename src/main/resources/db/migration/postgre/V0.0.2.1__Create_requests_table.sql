CREATE TABLE requestlogs (
    id         SERIAL,
    username   VARCHAR(255),
    path       VARCHAR(255) NOT NULL,
    method     VARCHAR(7)   NOT NULL,
    parameter  VARCHAR(255),
    response   VARCHAR(255),
    duration   INTEGER      NOT NULL,
    exception  VARCHAR(255),
    requested  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);