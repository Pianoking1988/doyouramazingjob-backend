CREATE TABLE requestlogs (
    id         INTEGER      GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
    username   VARCHAR(255),
    path       VARCHAR(255) NOT NULL,
    method     VARCHAR(7)   NOT NULL,
    content    VARCHAR(255),
    response   VARCHAR(255),
    duration   INTEGER      NOT NULL,
    exception  VARCHAR(255),
    requested  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);