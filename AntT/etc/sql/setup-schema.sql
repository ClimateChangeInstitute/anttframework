-- This file contains the necessary SQL to create the database schema.
-- Use the SQL 2011 standard but conform to the PostgeSQL standard if different.
-- PostgreSQL 9.4 https://www.postgresql.org/docs/9.4/static/features.html
 

BEGIN;

------------------------------------------------------------------------------
------------------------------------------------------------------------------
--------------------------- Start Tables section -----------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

CREATE TABLE countries(
	id SERIAL PRIMARY KEY,
	country TEXT);
	
CREATE TABLE regions(
	id SERIAL PRIMARY KEY,
	region TEXT,
	country_id INTEGER REFERENCES countries(id));

CREATE TABLE sub_regions(
	id SERIAL PRIMARY KEY,
	sregion TEXT,
	region_id INTEGER REFERENCES regions(id));
	
CREATE TABLE volcano_types(
	id SERIAL PRIMARY KEY,
	vtype TEXT);
	
CREATE TABLE rock_types(
	id SERIAL PRIMARY KEY,
	rtype TEXT);

CREATE TABLE tectonic_settings(
	id SERIAL PRIMARY KEY,
	setting TEXT);

CREATE TABLE volcanoes(
	volcano_number INT PRIMARY KEY,
	old_volcano_number TEXT,
	volcano_name TEXT,
	country_id INTEGER REFERENCES countries(id),
	primary_volcano_type_id INTEGER REFERENCES volcano_types(id),
	last_known_eruption TEXT,
	region_id INTEGER REFERENCES regions(id),
	subregion_id INTEGER REFERENCES sub_regions(id),
	latitude REAL CONSTRAINT valid_latitude_range CHECK (-90 <= latitude AND latitude <= 90),
	longitude REAL CONSTRAINT valid_longitude_range CHECK (-180 <= longitude AND longitude <= 180),
	elevation_m REAL,
	dominant_rock_type_id INTEGER REFERENCES rock_types(id),
	tectonic_setting INTEGER REFERENCES tectonic_settings(id));
	


------------------------------------------------------------------------------
------------------------------------------------------------------------------
----------------------------- End tables section  ----------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------



------------------------------------------------------------------------------
------------------------------------------------------------------------------
---------------------------- Start Views section -----------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

------------------------------------------------------------------------------
------------------------------------------------------------------------------
----------------------------- End views section ------------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------



------------------------------------------------------------------------------
------------------------------------------------------------------------------
-------------------------- Start Priveleges section --------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------


------------------------------------------------------------------------------
------------------------------------------------------------------------------
----------------------------- End priveleges section --------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

COMMIT;
