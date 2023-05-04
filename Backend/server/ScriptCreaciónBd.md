CREATE TABLE AppUser (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  surname VARCHAR(50),
  location VARCHAR(50)
);

CREATE TYPE State AS ENUM ('new', 'almost new', 'second hand');


CREATE TABLE Product (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  location VARCHAR(50),
  state State,
  user_id INTEGER REFERENCES AppUser(id)
);

CREATE TYPE ActionType AS ENUM ('purchase', 'sale', 'rent');

CREATE TABLE Transactions (
  id SERIAL PRIMARY KEY,
  date TIMESTAMP,
  action ActionType,
  user_id INTEGER REFERENCES AppUser(id),
  product_id INTEGER REFERENCES Product(id)
);

CREATE TABLE Favourites (
  id SERIAL PRIMARY KEY,
  date TIMESTAMP,
  user_id INTEGER REFERENCES AppUser(id),
  product_id INTEGER REFERENCES Product(id)
);