-- sample data for unit testing
INSERT INTO users(id, username, email, password)
VALUES(1,'thowl', 'prog3@th-owl.de', 'start') ;
INSERT INTO users(id, username, email, password)
VALUES(2,'proghx', 'prog3@th-owl.de', 'sonne') ;
INSERT INTO users(id, username, email, password)
VALUES(3,'admin', 'adm_p3@th-owl.de', 'nimda') ;
-- INSERT INTO categories(id, categoryName)
-- VALUES(1, 'Category 1') ;
-- INSERT INTO categories(id, categoryName)
-- VALUES(2, 'Category 2') ;
-- INSERT INTO categories(id, categoryName)
-- VALUES(3, 'Category 3') ;
-- INSERT INTO categories(id, categoryName)
-- VALUES(4, 'Category 4') ;

-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 4 ;
