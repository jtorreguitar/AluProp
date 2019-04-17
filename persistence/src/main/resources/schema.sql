CREATE TABLE IF NOT EXISTS properties (
    id SERIAL PRIMARY KEY,
    area varchar(250),
    description varchar(3000),
    caption varchar(250),
    image varchar(300)
);

CREATE TABLE IF NOT EXISTS interests (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id),
    description varchar(300),
    email varchar(300)
);