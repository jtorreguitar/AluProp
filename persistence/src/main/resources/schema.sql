CREATE TABLE IF NOT EXISTS countries (
    id SERIAL PRIMARY KEY,
    name varchar(75)
);

CREATE TABLE IF NOT EXISTS provinces (
     id SERIAL PRIMARY KEY,
     name varchar(75),
     countryId INTEGER REFERENCES countries(id)
);

CREATE TABLE IF NOT EXISTS cities (
    id SERIAL PRIMARY KEY,
    name varchar(75),
    countryId INTEGER REFERENCES countries(id) null,
    provinceId INTEGER REFERENCES countries(id) null
);

CREATE TABLE IF NOT EXISTS neighbourhoods (
    id SERIAL PRIMARY KEY,
    name varchar(75),
    cityId INTEGER REFERENCES cities(id) null
);

CREATE TABLE IF NOT EXISTS properties (
    id SERIAL PRIMARY KEY,
    area varchar(250),
    description varchar(3000),
    caption varchar(250),
    image varchar(300),
    propertyType integer,
    neighbourhoodId integer references neighbourhoods(id),
    privacyLevel boolean,
    capacity integer,
    price float
);

CREATE TABLE IF NOT EXISTS universities (
    id SERIAL PRIMARY KEY,
    name varchar(75)
);

CREATE TABLE IF NOT EXISTS careers (
    id SERIAL PRIMARY KEY,
    name varchar(75)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email varchar(250),
    username varchar(250),
    passwordHash varchar(300),
    name varchar(50),
    lastName varchar(100),
    birthDate date,
    gender integer,
    bio varchar(1000),
    contactNumber varchar(25),
    universityId INTEGER REFERENCES universities(id),
    careerId INTEGER REFERENCES careers(id)
);

CREATE TABLE IF NOT EXISTS interests (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id),
    userId INTEGER REFERENCES users(id)
);