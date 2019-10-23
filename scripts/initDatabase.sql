CREATE TABLE users
  (
    dn VARCHAR(200),
    last_name VARCHAR(50),
    first_name VARCHAR(50),
    organization VARCHAR(100),
    favorite_color VARCHAR(25),
    role VARCHAR(25)
  );

INSERT INTO users 
  VALUES ("wally", "Johnson", "Wallace", "U.S.Government",
    "blue","desk jockey");
