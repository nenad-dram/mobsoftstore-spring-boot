CREATE TABLE users (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   role VARCHAR(255) NOT NULL
);