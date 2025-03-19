-- sample data for unit testing
INSERT INTO users(id, username, email, password)
VALUES(1,'thowl', 'prog3@th-owl.de', '$2a$10$DIlJiPEPJfNJEEfyT0yVcur1zOJfOJD.5zz0s2lasIwMVJG/VgdVS') ; -- start
INSERT INTO users(id, username, email, password)
VALUES(2,'proghx', 'prog3@th-owl.de', '$2a$10$dPqbeLy3UZ4jqg7nU2H70OrQx73zFG6Q6cy7mcWHIR/V4CBZsrNMu') ; -- sonne
INSERT INTO users(id, username, email, password)
VALUES(3,'admin', 'adm_p3@th-owl.de', '$2a$10$2R6RSTLgq7Drt32n1piJLOzFYTXnmMzZU8YHeMlbtRqiGVAFwI3/6') ; -- nimda
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
