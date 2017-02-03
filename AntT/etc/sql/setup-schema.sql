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

	
CREATE TABLE sites(
	site_id TEXT PRIMARY KEY,
	long_name TEXT NOT NULL,
	latitude REAL CONSTRAINT valid_latitude_range CHECK (-90 <= latitude AND latitude <= 90),
	longitude REAL CONSTRAINT valid_longitude_range CHECK (-180 <= longitude AND longitude <= 180),
	elevation_m REAL NOT NULL,
	comment TEXT);

-- Represents collections of sites.  For example, Erebus.
CREATE TABLE areas(
	area TEXT PRIMARY KEY,
	comment TEXT);

-- Areas may have many sites.  If areas overlap, a site may be in multiple areas.
CREATE TABLE areas_sites(
	area TEXT REFERENCES areas(area) NOT NULL,
	site_id TEXT REFERENCES sites(site_id) NOT NULL,
	PRIMARY KEY (area, site_id));
	

-- Categories are collections of samples
-- For example, a category might be an icecore.
CREATE TABLE categories(
	category_id TEXT PRIMARY KEY,
	site_id TEXT REFERENCES sites(site_id) NOT NULL,
	category_type CHAR NOT NULL);

CREATE TABLE bia_categories(
	category_id TEXT PRIMARY KEY REFERENCES categories(category_id),
	deep TEXT,
	thickness_cm TEXT,
	trend TEXT);

CREATE TABLE icecore_categories(
	category_id TEXT PRIMARY KEY REFERENCES categories(category_id),
	drilled_by TEXT,
	drilling_dates TEXT,
	core_diameter TEXT,
	max_core_depth TEXT,
	core_age_range TEXT);
	
-- The type of machine that created the core
CREATE TABLE corer_types(
       corer_type TEXT PRIMARY KEY);
	
CREATE TABLE lake_categories(
	category_id TEXT PRIMARY KEY REFERENCES categories(category_id),
	corer_type TEXT REFERENCES corer_types(corer_type),
	age TEXT,
	core_length_m REAL,
	collection_date DATE);
	
CREATE TABLE marine_categories(
	category_id TEXT PRIMARY KEY REFERENCES categories(category_id),
	corer_type TEXT REFERENCES corer_types(corer_type),
	age TEXT,
	core_length_m REAL,
	collection_date DATE);
	
-- Outcrop categories don't have any additional columns, but might in the future
CREATE TABLE outcrop_categories(
	category_id TEXT PRIMARY KEY REFERENCES categories(category_id));
	
CREATE TABLE instruments(
	iid TEXT PRIMARY KEY, -- instrument id
	long_name TEXT,
	lab_id TEXT,
	comments TEXT);
	
-- Can't name the table references because it's a reserved word!
-- TODO This table will likely need additional info columns
CREATE TABLE refs(
	doi TEXT PRIMARY KEY,
	ref TEXT,
	bibtex TEXT); 
	
-- Represents all samples
-- sample_type must be I,B,L,M, or O
-- Join this table with bia_samples, icecore_samples, lake_samples, 
-- marine_samples, or outcrop_samples to get all sample information. 
CREATE TABLE samples(
	sample_id TEXT PRIMARY KEY,
	secondary_id TEXT,
	sampled_by TEXT,
	collection_date DATE NOT NULL,
	comments TEXT,
	category_id TEXT REFERENCES categories(category_id) NOT NULL,
	iid TEXT REFERENCES instruments(iid),
	sample_type CHAR NOT NULL);

-- A sample may have many refs, and a ref may be used by many samples.
CREATE TABLE samples_refs(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	doi TEXT REFERENCES refs(doi) NOT NULL,
	PRIMARY KEY (sample_id, doi));

CREATE TABLE grain_sizes(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	iid TEXT REFERENCES instruments(iid),
	comments TEXT,
	range TEXT NOT NULL,
	sample_date TEXT,
	PRIMARY KEY (sample_id, iid));
	
-- A grain size may have many refs, and a ref may be used by many grain sizes.
CREATE TABLE grain_sizes_refs(
	sample_id TEXT NOT NULL,
	iid TEXT NOT NULL,
	doi TEXT REFERENCES refs(doi) NOT NULL,
	FOREIGN KEY (sample_id, iid) REFERENCES grain_sizes(sample_id, iid),
	PRIMARY KEY (sample_id, iid, doi));
	
CREATE TABLE images(
	image_id TEXT PRIMARY KEY, -- This is the file name
	comments TEXT,
	bytes BYTEA,
	thumbBytes BYTEA);

-- A sample may have many images, and an image may be used by many samples
CREATE TABLE samples_images(
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	image_id TEXT REFERENCES images(image_id) NOT NULL,
	PRIMARY KEY (sample_id, image_id));
	
-- Icecore samples
CREATE TABLE icecore_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not required
	topdepth_m REAL NOT NULL,
	bottomdepth_m REAL NOT NULL,
	topyear_bp REAL NOT NULL,
	bottomyear_bp REAL NOT NULL);
	
	
-- Blue Ice Area samples
CREATE TABLE bia_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number) -- Volcano not required
);	

-- Lake and Marine could be combined into aquatic table?

       
CREATE TABLE lake_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not required
	-- start inherited from aquatic samples
	depth_m REAL,
	thickness_cm REAL
	-- end inherited from aquatic samples
	);

	
-- Mostly the same (actually is for now!) as lake_samples
CREATE TABLE marine_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number), -- Volcano not required
	-- start inherited from aquatic samples
	depth_m REAL,
	thickness_cm REAL
	-- end inherited from aquatic samples
	);

	
CREATE TABLE outcrop_samples(
	sample_id TEXT PRIMARY KEY REFERENCES samples(sample_id),
	volcano_number INTEGER REFERENCES volcanoes(volcano_number) NOT NULL -- Required by outcrop
	);

CREATE TABLE method_types(
       method_type TEXT PRIMARY KEY);
	
-- Major/Minor Elements
CREATE TABLE mm_elements(
	mm_element_id TEXT PRIMARY KEY,
	sample_id TEXT REFERENCES samples(sample_id) NOT NULL,
	comments TEXT,	
	method_type TEXT REFERENCES method_types(method_type) NOT NULL,
	iid TEXT REFERENCES instruments(iid),
	date_measured DATE,
	measured_by TEXT NOT NULL,
	number_of_measurements INTEGER NOT NULL,
	original_total REAL NOT NULL,
	calculated_total REAL NOT NULL,
	instrument_settings TEXT);

--  ***** Example elements *****
--	sio2,tio2,so2,al2o3,cr2o3,fe2o3,feo,mno,mgo,cao,na2o,k2o,p2o5,p2o5,cl,co2
-- atomic number,atomic symbol,element name,atomic mass
CREATE TABLE chemistries(
	symbol TEXT PRIMARY KEY, -- Example, He
	name TEXT NOT NULL, -- Example, Helium
	format TEXT NOT NULL, -- HTML formatted string
	molecular_mass REAL,
	atomic_number INTEGER -- Only for elements
);

CREATE TABLE units(
	unit TEXT PRIMARY KEY
);

-- Major/Minor Elements Data
CREATE TABLE mm_elements_data(
	mm_element_id TEXT REFERENCES mm_elements(mm_element_id) NOT NULL,
	symbol TEXT REFERENCES chemistries(symbol) NOT NULL,
	unit TEXT REFERENCES units(unit) NOT NULL,
	val REAL NOT NULL,
	std REAL,
	me REAL,
	PRIMARY KEY(mm_element_id, symbol, unit));

	
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

GRANT ALL PRIVILEGES ON categories  TO :ADMIN;
GRANT ALL PRIVILEGES ON bia_categories  TO :ADMIN;
GRANT ALL PRIVILEGES ON icecore_categories TO :ADMIN;

GRANT ALL PRIVILEGES ON corer_types TO :ADMIN;

GRANT ALL PRIVILEGES ON lake_categories TO :ADMIN;
GRANT ALL PRIVILEGES ON marine_categories TO :ADMIN;
GRANT ALL PRIVILEGES ON outcrop_categories TO :ADMIN;

GRANT ALL PRIVILEGES ON sites TO :ADMIN;

GRANT ALL PRIVILEGES ON areas TO :ADMIN;
GRANT ALL PRIVILEGES ON areas_sites TO :ADMIN;

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


GRANT ALL PRIVILEGES ON lake_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON marine_samples TO :ADMIN;
GRANT ALL PRIVILEGES ON outcrop_samples TO :ADMIN;

GRANT ALL PRIVILEGES ON method_types TO :ADMIN;

GRANT ALL PRIVILEGES ON mm_elements TO :ADMIN;
GRANT ALL PRIVILEGES ON chemistries TO :ADMIN;
GRANT ALL PRIVILEGES ON units TO :ADMIN;
GRANT ALL PRIVILEGES ON mm_elements_data TO :ADMIN;
------------------------------------------------------------------------------
------------------------------------------------------------------------------
----------------------------- End priveleges section --------------------------
------------------------------------------------------------------------------
------------------------------------------------------------------------------

COMMIT;
