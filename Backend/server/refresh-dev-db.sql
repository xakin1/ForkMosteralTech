

CREATE TABLE IF NOT EXISTS AppUser (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  surname VARCHAR(50),
  location VARCHAR(50)
);

CREATE TYPE IF NOT EXISTS State AS ENUM ('new', 'almost new', 'second hand');


CREATE TABLE IF NOT EXISTS Product (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  location VARCHAR(50),
  state State,
  user_id INTEGER REFERENCES AppUser(id)
);

CREATE TYPE IF NOT EXISTS ActionType AS ENUM ('purchase', 'sale', 'rent');

CREATE TABLE IF NOT EXISTS Transactions (
  id SERIAL PRIMARY KEY,
  date TIMESTAMP,
  action ActionType,
  user_id INTEGER REFERENCES AppUser(id),
  product_id INTEGER REFERENCES Product(id)
);

CREATE TABLE IF NOT EXISTS Favourites (
  id SERIAL PRIMARY KEY,
  date TIMESTAMP,
  user_id INTEGER REFERENCES AppUser(id),
  product_id INTEGER REFERENCES Product(id)
);