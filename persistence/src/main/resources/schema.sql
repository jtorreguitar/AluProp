CREATE TABLE IF NOT EXISTS countries (
    id SERIAL PRIMARY KEY,
    name varchar(75)
);

CREATE TABLE IF NOT EXISTS provinces (
    id SERIAL PRIMARY KEY,
    name varchar(75),
    countryId INTEGER REFERENCES countries(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS cities (
    id SERIAL PRIMARY KEY,
    name varchar(75),
    countryId INTEGER REFERENCES countries(id) ON DELETE SET NULL,
    provinceId INTEGER REFERENCES countries(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS neighbourhoods (
    id SERIAL PRIMARY KEY,
    name varchar(75),
    cityId INTEGER REFERENCES cities(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id) ON DELETE CASCADE,
    image bytea
);

-- The statement "ADD COLUMN IF NOT EXISTS" was added to postgres in version 9.6, watch out for version to use in prod
-- ALTER TABLE properties ADD COLUMN IF NOT EXISTS mainImageId integer references images(id) ON DELETE SET NULL;

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
    passwordHash varchar(300),
    name varchar(50),
    lastName varchar(100),
    birthDate date,
    gender varchar(50),
    bio varchar(1000),
    contactNumber varchar(25),
    universityId INTEGER REFERENCES universities(id) ON DELETE SET NULL,
    careerId INTEGER REFERENCES careers(id) ON DELETE SET NULL,
    role varchar(50)
);

CREATE TABLE IF NOT EXISTS properties (
    id SERIAL PRIMARY KEY,
    description varchar(3000),
    caption varchar(250),
    propertyType varchar(100),
    neighbourhoodId integer references neighbourhoods(id) ON DELETE SET NULL,
    privacyLevel boolean,
    capacity integer,
    price float,
    mainImageId integer references images(id) ON DELETE SET NULL,
    ownerId INTEGER REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS interests (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id) ON DELETE CASCADE,
    userId INTEGER REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rules (
    id SERIAL PRIMARY KEY,
    name varchar(250)
);

CREATE TABLE IF NOT EXISTS propertyRules (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id) ON DELETE CASCADE,
    ruleId INTEGER REFERENCES rules(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    name varchar(100)
);

CREATE TABLE IF NOT EXISTS propertyServices (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id) ON DELETE CASCADE,
    serviceId INTEGER REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS proposals (
    id SERIAL PRIMARY KEY,
    propertyId INTEGER REFERENCES properties(id) ON DELETE CASCADE,
    creatorId INTEGER REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS userProposals (
    id SERIAL PRIMARY KEY,
    userId INTEGER REFERENCES users(id) ON DELETE CASCADE,
    proposalId INTEGER REFERENCES proposals(id) ON DELETE CASCADE,
    state integer
);
