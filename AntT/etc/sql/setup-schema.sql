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
	country TEXT PRIMARY KEY);

CREATE TABLE subregions(
	subregion TEXT NOT NULL,
	country TEXT REFERENCES countries(country),
	PRIMARY KEY(subregion, country));
	
CREATE TABLE regions(
	region TEXT NOT NULL,
	subregion TEXT,
	country TEXT,
	FOREIGN KEY (subregion, country) REFERENCES subregions(subregion, country),
	PRIMARY KEY(region, subregion, country));
	
CREATE TABLE volcano_types(
	volcano_type TEXT PRIMARY KEY);
	
CREATE TABLE rock_types(
	rock_type TEXT PRIMARY KEY);

CREATE TABLE tectonic_settings(
	tectonic_setting TEXT PRIMARY KEY);

CREATE TABLE volcanoes(
	volcano_number INTEGER PRIMARY KEY,
	old_volcano_number TEXT,
	volcano_name TEXT NOT NULL,
	country TEXT REFERENCES countries(country) NOT NULL,
	primary_volcano_type TEXT REFERENCES volcano_types(volcano_type) NOT NULL,
	last_known_eruption TEXT,
	region TEXT NOT NULL,
	subregion TEXT NOT NULL,
	latitude REAL NOT NULL CONSTRAINT valid_latitude_range CHECK (-90 <= latitude AND latitude <= 90),
	longitude REAL NOT NULL CONSTRAINT valid_longitude_range CHECK (-180 <= longitude AND longitude <= 180),
	elevation_m REAL NOT NULL,
	dominant_rock_type TEXT REFERENCES rock_types(rock_type) NOT NULL,
	tectonic_setting TEXT REFERENCES tectonic_settings(tectonic_setting) NOT NULL,
	FOREIGN KEY (subregion, country) REFERENCES regions(subregion, country),
	FOREIGN KEY (region, subregion, country) REFERENCES regions(region, subregion, country));
	


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
