-- This script should contain all example data and has to be always the script with the highest version such that it is
-- executed at last. If you do not want to execute this script, change the attribute "flyway.location" in your vm
-- arguments.

INSERT INTO users (mail, created, createdby)
VALUES('pianoking@gmx.de', '2017-04-01 12:59:59', 'pianoking@gmx.de');

INSERT INTO users (mail, created, createdby)
VALUES('oliver.schmitz.informatik@googlemail.com', '2017-04-01 13:59:59', 'pianoking@gmx.de');