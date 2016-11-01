-- This file contains the necessary SQL to create the database schema.
-- Use the SQL 2011 standard but conform to the PostgeSQL standard if different.
-- PostgreSQL 9.4 https://www.postgresql.org/docs/9.4/static/features.html
--
-- This file should be passed an administrator user as
-- psql -f setup-schema.sql -v ADMIN=YOURADMIN 

BEGIN;

------------------------------------------------------------------------------
------------------------------------------------------------------------------
--------------------------- Start Tables section -----------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

------------------------------------------------------------------------------
-- Start Volcano Related Tables
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
	dominant_rock_type TEXT REFERENCES rock_types(rock_type),
	tectonic_setting TEXT REFERENCES tectonic_settings(tectonic_setting),
	FOREIGN KEY (subregion, country) REFERENCES subregions(subregion, country),
	FOREIGN KEY (region, subregion, country) REFERENCES regions(region, subregion, country));
	
------------------------------------------------------------------------------
-- End Volcano Related Tables
------------------------------------------------------------------------------

------------------------------------------------------------------------------
-- Start Sample Related Tables
------------------------------------------------------------------------------

CREATE TABLE site_types(
	site_type TEXT PRIMARY KEY);
	
CREATE TABLE sites(
	site_id TEXT PRIMARY KEY,
	site_type TEXT REFERENCES site_types(site_type) NOT NULL,
	latitude REAL NOT NULL CONSTRAINT valid_latitude_range CHECK (-90 <= latitude AND latitude <= 90),
	longitude REAL NOT NULL CONSTRAINT valid_longitude_range CHECK (-180 <= longitude AND longitude <= 180),
	elevation_m REAL NOT NULL,
	comment TEXT);

CREATE TABLE instruments(
	iid TEXT PRIMARY KEY, -- instrument id
	long_name TEXT,
	location TEXT,
	comments TEXT);
	
-- Can't name the table references because it's a reserved word!
CREATE TABLE refs(
	doi TEXT PRIMARY KEY); -- TODO this table will need additional info columns 
	
-- Represents all samples
CREATE TABLE samples(
	sample_id TEXT PRIMARY KEY,
	long_name TEXT,
	sampled_by TEXT,
	collection_dates TEXT,
	comments TEXT,
	site_id TEXT REFERENCES sites(site_id) NOT NULL,
	site_type TEXT REFERENCES site_types(site_type) NOT NULL,
	iid TEXT REFERENCES instruments(iid) NOT NULL);

-- A sample may have many refs, and a ref may be used by many samples.
CREATE TABLE samples_refs(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	doi TEXT REFERENCES refs(doi) NOT NULL);

CREATE TABLE grain_sizes(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	iid TEXT REFERENCES instruments(iid) NOT NULL,
	name TEXT NOT NULL,
	range TEXT NOT NULL,
	grain_date DATE NOT NULL,
	PRIMARY KEY (sample_id, iid));
	
-- Icecore samples
CREATE TABLE icecore_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	drilled_by TEXT,
	drilling_dates TEXT,
	core_diameter REAL,
	max_core_depth REAL,
	core_age REAL,
	core_age_range TEXT, -- years
	topdepth_m REAL,
	bottomdepth_m REAL,
	topyear_bp REAL,
	bottomyear_bp REAL);
	
	
-- Blue Ice Area samples
CREATE TABLE bia_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	deep TEXT,
	sample_description TEXT,
	sample_media TEXT,
	unit_name TEXT,
	thickness_cm TEXT,
	trend TEXT);	

-- Lake and Marine could be combined into aquatic table?
	
CREATE TABLE lake_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	-- start inherited from aquatic samples
	core_type TEXT, -- TODO Is this really needed?
	age TEXT,
	core_length_m REAL,
	sampling_dates TEXT,
	depth_m REAL,
	top_m REAL,
	thickness REAL
	-- end inherited from aquatic samples
	);
	
	
-- Mostly the same (actually is for now!) as lake_samples
CREATE TABLE marine_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	-- start inherited from aquatic samples
	core_type TEXT, -- TODO Is this really needed?
	age TEXT,
	core_length_m REAL,
	sampling_dates TEXT,
	depth_m REAL,
	top_m REAL,
	thickness REAL
	-- end inherited from aquatic samples
	);

	
CREATE TABLE outcrop_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number) NOT NULL -- Required by outcrop
	);


	
------------------------------------------------------------------------------
-- End Sample Related Tables
------------------------------------------------------------------------------


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

GRANT ALL PRIVILEGES ON countries TO :ADMIN;
GRANT ALL PRIVILEGES ON subregions TO :ADMIN;
GRANT ALL PRIVILEGES ON regions TO :ADMIN;
GRANT ALL PRIVILEGES ON volcano_types TO :ADMIN;
GRANT ALL PRIVILEGES ON rock_types TO :ADMIN;
GRANT ALL PRIVILEGES ON tectonic_settings TO :ADMIN;
GRANT ALL PRIVILEGES ON volcanoes TO :ADMIN;

GRANT ALL PRIVILEGES ON site_types TO :ADMIN;
GRANT ALL PRIVILEGES ON sites TO :ADMIN;
GRANT ALL PRIVILEGES ON instruments TO :ADMIN;

GRANT ALL PRIVILEGES ON refs TO :ADMIN;

GRANT ALL PRIVILEGES ON samples TO :ADMIN;
GRANT ALL PRIVILEGES ON samples_refs TO :ADMIN;

GRANT ALL PRIVILEGES ON grain_sizes TO :ADMIN;

GRANT ALL PRIVILEGES ON icecore_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON bia_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON lake_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON marine_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON outcrop_samples TO :ADMIN;

------------------------------------------------------------------------------
------------------------------------------------------------------------------
----------------------------- End priveleges section --------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

COMMIT;
