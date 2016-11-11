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
-- sample_type must be I,B,L,M, or O
CREATE TABLE samples(
	sample_id TEXT PRIMARY KEY,
	long_name TEXT,
	sampled_by TEXT,
	collection_date DATE NOT NULL,
	comments TEXT,
	site_id TEXT REFERENCES sites(site_id) NOT NULL,
	iid TEXT REFERENCES instruments(iid) NOT NULL,
	sample_type CHAR NOT NULL);

-- A sample may have many refs, and a ref may be used by many samples.
CREATE TABLE samples_refs(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	doi TEXT REFERENCES refs(doi) NOT NULL,
	PRIMARY KEY (sample_id, doi));

CREATE TABLE grain_sizes(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	iid TEXT REFERENCES instruments(iid) NOT NULL,
	name TEXT NOT NULL,
	range TEXT NOT NULL,
	grain_date DATE NOT NULL,
	PRIMARY KEY (sample_id, iid));
	
-- A grain size may have many refs, and a ref may be used by many grain sizes.
CREATE TABLE grain_sizes_refs(
	sample_id TEXT NOT NULL,
	iid TEXT NOT NULL,
	doi TEXT REFERENCES refs(doi) NOT NULL,
	FOREIGN KEY (sample_id, iid) REFERENCES grain_sizes(sample_id, iid),
	PRIMARY KEY (sample_id, iid, doi));
	
CREATE TABLE images(
	image_id SERIAL PRIMARY KEY,
	comments TEXT,
	bytes BYTEA);

-- A sample may have many images, and an image may be used by many samples
CREATE TABLE samples_images(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	image_id INTEGER REFERENCES images(image_id) NOT NULL,
	PRIMARY KEY (sample_id, image_id));
	
-- Icecore samples
CREATE TABLE icecore_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	drilled_by TEXT NOT NULL,
	drilling_date DATE NOT NULL,
	core_diameter REAL NOT NULL,
	max_core_depth REAL NOT NULL,
	core_age REAL NOT NULL,
	core_age_range TEXT NOT NULL, -- years
	topdepth_m REAL NOT NULL,
	bottomdepth_m REAL NOT NULL,
	topyear_bp REAL NOT NULL,
	bottomyear_bp REAL NOT NULL);
	
	
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

CREATE TABLE core_types(
       core_type TEXT PRIMARY KEY);
	
CREATE TABLE lake_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	-- start inherited from aquatic samples
	core_type TEXT REFERENCES core_types(core_type),
	age TEXT,
	core_length_m REAL,
	sampling_date DATE,
	depth_m REAL,
	top_m REAL,
	thickness_cm REAL
	-- end inherited from aquatic samples
	);
	
	
-- Mostly the same (actually is for now!) as lake_samples
CREATE TABLE marine_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not require
	-- start inherited from aquatic samples
	core_type TEXT REFERENCES core_types(core_type),
	age TEXT,
	core_length_m REAL,
	sampling_date DATE,
	depth_m REAL,
	top_m REAL,
	thickness_cm REAL
	-- end inherited from aquatic samples
	);

	
CREATE TABLE outcrop_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number) NOT NULL -- Required by outcrop
	);

CREATE TABLE method_types(
       method_type TEXT PRIMARY KEY);
	
CREATE TABLE mm_elements(
	longsample_id TEXT PRIMARY KEY,
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	comments TEXT,	
	method_type TEXT REFERENCES method_types(method_type) NOT NULL,
	iid TEXT REFERENCES instruments(iid) NOT NULL,
	date_measured DATE,
	measured_by TEXT NOT NULL,
	number_of_measurements INTEGER NOT NULL,
	original_total REAL NOT NULL,
	calculated_total REAL NOT NULL,
	instrument_settings TEXT,
	-- 1. sio2 -------------------
	sio2 REAL,
	std_sio2 REAL,
	me_sio2 REAL,
	sio2_units TEXT,
	-- 2. tio2 -------------------
	tio2 REAL,
	std_tio2 REAL,
	me_tio2 REAL,
	tio2_units TEXT,
	-- 3. so2 -------------------
	so2 REAL,
	std_so2 REAL,
	me_so2 REAL,
	so2_units TEXT,
	-- 4. al2o3 -------------------
	al2o3 REAL,
	std_al2o3 REAL,
	me_al2o3 REAL,
	al2o3_units TEXT,
	-- 5. cr2o3 -------------------	
	cr2o3 REAL,
	std_cr2o3 REAL,
	me_cr2o3 REAL,
	cr2o3_units TEXT,
	-- 6. fe2o3 -------------------	
	fe2o3 REAL,
	std_fe2o3 REAL,
	me_fe2o3 REAL,
	fe2o3_units TEXT,
	-- 7. feo -------------------	
	feo REAL,
	std_feo REAL,
	me_feo REAL,
	feo_units TEXT,
	-- 8. mno -------------------	
	mno REAL,
	std_mno REAL,
	me_mno REAL,
	mno_units TEXT,
	-- 9. mgo -------------------	
	mgo REAL,
	std_mgo REAL,
	me_mgo REAL,
	mgo_units TEXT,
	-- 10. cao -------------------	
	cao REAL,
	std_cao REAL,
	me_cao REAL,
	cao_units TEXT,
	-- 11. na2o -------------------	
	na2o REAL,
	std_na2o REAL,
	me_na2o REAL,
	na2o_units TEXT,
	-- 12. k2o -------------------	
	k2o REAL,
	std_k2o REAL,
	me_k2o REAL,
	k2o_units TEXT,
	-- 13. p2o5 -------------------	
	p2o5 REAL,
	std_p2o5 REAL,
	me_p2o5 REAL,
	p2o5_units TEXT,
	-- 14. p2o5 -------------------
	f REAL,
	std_f REAL,
	me_f REAL,
	f_units TEXT,
	-- 15. cl -------------------
	cl REAL,
	std_cl REAL,
	me_cl REAL,
	cl_units TEXT,
	-- 16. co2 -------------------
	co2 REAL,
	std_co2 REAL,
	me_co2 REAL,
	co2_units TEXT,
	------------------------------
	h2o_plus REAL,
	h2o_minus REAL,
	loi REAL);
	


	
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
GRANT ALL PRIVILEGES ON grain_sizes_refs TO :ADMIN;

GRANT ALL PRIVILEGES ON images TO :ADMIN;
GRANT ALL PRIVILEGES ON samples_images TO :ADMIN;

GRANT ALL PRIVILEGES ON icecore_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON bia_samples TO :ADMIN;

GRANT ALL PRIVILEGES ON core_types TO :ADMIN;

GRANT ALL PRIVILEGES ON lake_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON marine_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON outcrop_samples TO :ADMIN;

GRANT ALL PRIVILEGES ON method_types TO :ADMIN;
GRANT ALL PRIVILEGES ON mm_elements TO :ADMIN;

------------------------------------------------------------------------------
------------------------------------------------------------------------------
----------------------------- End priveleges section --------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

COMMIT;
