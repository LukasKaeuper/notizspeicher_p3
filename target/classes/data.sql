-- sample data for unit testing
INSERT INTO users(id, username, email, password)
VALUES(1,'thowl', 'prog3@th-owl.de', '$2a$10$DIlJiPEPJfNJEEfyT0yVcur1zOJfOJD.5zz0s2lasIwMVJG/VgdVS') ; -- start
INSERT INTO users(id, username, email, password)
VALUES(2,'proghx', 'prog@th-owl.de', '$2a$10$dPqbeLy3UZ4jqg7nU2H70OrQx73zFG6Q6cy7mcWHIR/V4CBZsrNMu') ; -- sonne
INSERT INTO users(id, username, email, password)
VALUES(3,'admin', 'adm_p3@th-owl.de', '$2a$10$2R6RSTLgq7Drt32n1piJLOzFYTXnmMzZU8YHeMlbtRqiGVAFwI3/6') ; -- nimda
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(1,1, '#80ffff', 'Uni') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(2,1, '#ff80ff', 'Arbeit') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(3,1, '#f9f9a9', 'Freizeit') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(4,2, '#80ffff', 'Uni') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(5,2, '#ff80ff', 'Arbeit') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(6,2, '#f9f9a9', 'Freizeit') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(7,3, '#80ffff', 'Uni') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(8,3, '#ff80ff', 'Arbeit') ;
INSERT INTO categories(ID, USER_ID, CATEGORY_COLOUR, CATEGORY_NAME)
VALUES(9,3, '#f9f9a9', 'Freizeit') ;

-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 53 ;
ALTER SEQUENCE categories_seq RESTART WITH 59 ;
