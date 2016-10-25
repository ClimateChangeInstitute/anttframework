-- This contains some default data for the database.

BEGIN;

-- Default data values should be added during the transaction.

--
-- Data for countries
--

INSERT INTO countries (country) VALUES ('Afghanistan');
INSERT INTO countries (country) VALUES ('Algeria');
INSERT INTO countries (country) VALUES ('Algeria-Niger');
INSERT INTO countries (country) VALUES ('Antarctica');
INSERT INTO countries (country) VALUES ('Argentina');
INSERT INTO countries (country) VALUES ('Armenia');
INSERT INTO countries (country) VALUES ('Armenia-Azerbaijan');
INSERT INTO countries (country) VALUES ('Australia');
INSERT INTO countries (country) VALUES ('Bolivia');
INSERT INTO countries (country) VALUES ('Brazil');
INSERT INTO countries (country) VALUES ('Burma (Myanmar)');
INSERT INTO countries (country) VALUES ('Cameroon');
INSERT INTO countries (country) VALUES ('Canada');
INSERT INTO countries (country) VALUES ('Cape Verde');
INSERT INTO countries (country) VALUES ('Chad');
INSERT INTO countries (country) VALUES ('Chile');
INSERT INTO countries (country) VALUES ('Chile-Argentina');
INSERT INTO countries (country) VALUES ('Chile-Bolivia');
INSERT INTO countries (country) VALUES ('Chile-Peru');
INSERT INTO countries (country) VALUES ('China');
INSERT INTO countries (country) VALUES ('China-North Korea');
INSERT INTO countries (country) VALUES ('Colombia');
INSERT INTO countries (country) VALUES ('Colombia-Ecuador');
INSERT INTO countries (country) VALUES ('Comoros');
INSERT INTO countries (country) VALUES ('Costa Rica');
INSERT INTO countries (country) VALUES ('Djibouti');
INSERT INTO countries (country) VALUES ('Dominica');
INSERT INTO countries (country) VALUES ('DR Congo');
INSERT INTO countries (country) VALUES ('DR Congo-Rwanda');
INSERT INTO countries (country) VALUES ('Ecuador');
INSERT INTO countries (country) VALUES ('El Salvador');
INSERT INTO countries (country) VALUES ('El Salvador-Guatemala');
INSERT INTO countries (country) VALUES ('Equatorial Guinea');
INSERT INTO countries (country) VALUES ('Eritrea');
INSERT INTO countries (country) VALUES ('Eritrea-Djibouti');
INSERT INTO countries (country) VALUES ('Ethiopia');
INSERT INTO countries (country) VALUES ('Ethiopia-Djibouti');
INSERT INTO countries (country) VALUES ('Ethiopia-Eritrea');
INSERT INTO countries (country) VALUES ('Ethiopia-Eritrea-Djibouti');
INSERT INTO countries (country) VALUES ('Ethiopia-Kenya');
INSERT INTO countries (country) VALUES ('Fiji');
INSERT INTO countries (country) VALUES ('France');
INSERT INTO countries (country) VALUES ('Georgia');
INSERT INTO countries (country) VALUES ('Germany');
INSERT INTO countries (country) VALUES ('Greece');
INSERT INTO countries (country) VALUES ('Grenada');
INSERT INTO countries (country) VALUES ('Guatemala');
INSERT INTO countries (country) VALUES ('Guatemala-El Salvador');
INSERT INTO countries (country) VALUES ('Honduras');
INSERT INTO countries (country) VALUES ('Iceland');
INSERT INTO countries (country) VALUES ('India');
INSERT INTO countries (country) VALUES ('Indonesia');
INSERT INTO countries (country) VALUES ('Iran');
INSERT INTO countries (country) VALUES ('Iran-Azerbaijan');
INSERT INTO countries (country) VALUES ('Italy');
INSERT INTO countries (country) VALUES ('Japan');
INSERT INTO countries (country) VALUES ('Japan - administered by Russia');
INSERT INTO countries (country) VALUES ('Kenya');
INSERT INTO countries (country) VALUES ('Libya');
INSERT INTO countries (country) VALUES ('Madagascar');
INSERT INTO countries (country) VALUES ('Malaysia');
INSERT INTO countries (country) VALUES ('Mali');
INSERT INTO countries (country) VALUES ('Mexico');
INSERT INTO countries (country) VALUES ('Mexico-Guatemala');
INSERT INTO countries (country) VALUES ('Mongolia');
INSERT INTO countries (country) VALUES ('Netherlands');
INSERT INTO countries (country) VALUES ('New Zealand');
INSERT INTO countries (country) VALUES ('Nicaragua');
INSERT INTO countries (country) VALUES ('Niger');
INSERT INTO countries (country) VALUES ('Nigeria');
INSERT INTO countries (country) VALUES ('North Korea');
INSERT INTO countries (country) VALUES ('North Korea-South Korea');
INSERT INTO countries (country) VALUES ('Norway');
INSERT INTO countries (country) VALUES ('Pakistan');
INSERT INTO countries (country) VALUES ('Panama');
INSERT INTO countries (country) VALUES ('Papua New Guinea');
INSERT INTO countries (country) VALUES ('Peru');
INSERT INTO countries (country) VALUES ('Philippines');
INSERT INTO countries (country) VALUES ('Portugal');
INSERT INTO countries (country) VALUES ('Russia');
INSERT INTO countries (country) VALUES ('Saint Kitts and Nevis');
INSERT INTO countries (country) VALUES ('Saint Lucia');
INSERT INTO countries (country) VALUES ('Saint Vincent and the Grenadines');
INSERT INTO countries (country) VALUES ('Samoa');
INSERT INTO countries (country) VALUES ('Sao Tome and Principe');
INSERT INTO countries (country) VALUES ('Saudi Arabia');
INSERT INTO countries (country) VALUES ('Solomon Islands');
INSERT INTO countries (country) VALUES ('South Africa');
INSERT INTO countries (country) VALUES ('South Korea');
INSERT INTO countries (country) VALUES ('Spain');
INSERT INTO countries (country) VALUES ('Sudan');
INSERT INTO countries (country) VALUES ('Syria');
INSERT INTO countries (country) VALUES ('Taiwan');
INSERT INTO countries (country) VALUES ('Tanzania');
INSERT INTO countries (country) VALUES ('Tonga');
INSERT INTO countries (country) VALUES ('Turkey');
INSERT INTO countries (country) VALUES ('Uganda');
INSERT INTO countries (country) VALUES ('Uganda-Rwanda');
INSERT INTO countries (country) VALUES ('Undersea Features');
INSERT INTO countries (country) VALUES ('United Kingdom');
INSERT INTO countries (country) VALUES ('United States');
INSERT INTO countries (country) VALUES ('Vanuatu');
INSERT INTO countries (country) VALUES ('Vietnam');
INSERT INTO countries (country) VALUES ('Yemen');


--
-- Data for subregions
--


INSERT INTO subregions (subregion, country) VALUES ('Admiralty Islands', 'Papua New Guinea');
INSERT INTO subregions (subregion, country) VALUES ('Africa (central)', 'DR Congo');
INSERT INTO subregions (subregion, country) VALUES ('Africa (central)', 'DR Congo-Rwanda');
INSERT INTO subregions (subregion, country) VALUES ('Africa (central)', 'Uganda');
INSERT INTO subregions (subregion, country) VALUES ('Africa (central)', 'Uganda-Rwanda');
INSERT INTO subregions (subregion, country) VALUES ('Africa (eastern)', 'Kenya');
INSERT INTO subregions (subregion, country) VALUES ('Africa (eastern)', 'Tanzania');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Djibouti');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Eritrea');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Eritrea-Djibouti');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Ethiopia');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Ethiopia-Djibouti');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Ethiopia-Eritrea');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Ethiopia-Eritrea-Djibouti');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Ethiopia-Kenya');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northeastern) and Red Sea', 'Yemen');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Algeria');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Algeria-Niger');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Chad');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Libya');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Mali');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Niger');
INSERT INTO subregions (subregion, country) VALUES ('Africa (northern)', 'Sudan');
INSERT INTO subregions (subregion, country) VALUES ('Africa (western)', 'Cameroon');
INSERT INTO subregions (subregion, country) VALUES ('Africa (western)', 'Equatorial Guinea');
INSERT INTO subregions (subregion, country) VALUES ('Africa (western)', 'Nigeria');
INSERT INTO subregions (subregion, country) VALUES ('Africa (western)', 'Sao Tome and Principe');
INSERT INTO subregions (subregion, country) VALUES ('Alaska (eastern)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Alaska Peninsula', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Alaska (southwestern)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Alaska (western)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Aleutian Islands', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Andaman Islands', 'India');
INSERT INTO subregions (subregion, country) VALUES ('Antarctica and South Sandwich Islands', 'Antarctica');
INSERT INTO subregions (subregion, country) VALUES ('Antarctica and South Sandwich Islands', 'United Kingdom');
INSERT INTO subregions (subregion, country) VALUES ('Arctic Ocean', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (central)', 'Brazil');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (central)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (central)', 'United Kingdom');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (Jan Mayen)', 'Norway');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (northern)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (southern)', 'Norway');
INSERT INTO subregions (subregion, country) VALUES ('Atlantic Ocean (southern)', 'United Kingdom');
INSERT INTO subregions (subregion, country) VALUES ('Australia', 'Australia');
INSERT INTO subregions (subregion, country) VALUES ('Azores', 'Portugal');
INSERT INTO subregions (subregion, country) VALUES ('Banda Sea', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Borneo', 'Malaysia');
INSERT INTO subregions (subregion, country) VALUES ('Bougainville and Solomon Islands', 'Papua New Guinea');
INSERT INTO subregions (subregion, country) VALUES ('Bougainville and Solomon Islands', 'Solomon Islands');
INSERT INTO subregions (subregion, country) VALUES ('Canada', 'Canada');
INSERT INTO subregions (subregion, country) VALUES ('Canary Islands', 'Spain');
INSERT INTO subregions (subregion, country) VALUES ('Cape Verde Islands', 'Cape Verde');
INSERT INTO subregions (subregion, country) VALUES ('Central Chile and Argentina', 'Argentina');
INSERT INTO subregions (subregion, country) VALUES ('Central Chile and Argentina', 'Chile');
INSERT INTO subregions (subregion, country) VALUES ('Central Chile and Argentina', 'Chile-Argentina');
INSERT INTO subregions (subregion, country) VALUES ('Central Philippines', 'Philippines');
INSERT INTO subregions (subregion, country) VALUES ('China (eastern)', 'China');
INSERT INTO subregions (subregion, country) VALUES ('China (eastern)', 'China-North Korea');
INSERT INTO subregions (subregion, country) VALUES ('China (western)', 'China');
INSERT INTO subregions (subregion, country) VALUES ('Colombia', 'Colombia');
INSERT INTO subregions (subregion, country) VALUES ('Colombia', 'Colombia-Ecuador');
INSERT INTO subregions (subregion, country) VALUES ('Costa Rica', 'Costa Rica');
INSERT INTO subregions (subregion, country) VALUES ('Ecuador', 'Ecuador');
INSERT INTO subregions (subregion, country) VALUES ('El Salvador and Honduras', 'El Salvador');
INSERT INTO subregions (subregion, country) VALUES ('El Salvador and Honduras', 'El Salvador-Guatemala');
INSERT INTO subregions (subregion, country) VALUES ('El Salvador and Honduras', 'Honduras');
INSERT INTO subregions (subregion, country) VALUES ('Fiji Islands', 'Fiji');
INSERT INTO subregions (subregion, country) VALUES ('Galápagos Islands', 'Ecuador');
INSERT INTO subregions (subregion, country) VALUES ('Greece', 'Greece');
INSERT INTO subregions (subregion, country) VALUES ('Guatemala', 'Guatemala');
INSERT INTO subregions (subregion, country) VALUES ('Guatemala', 'Guatemala-El Salvador');
INSERT INTO subregions (subregion, country) VALUES ('Halmahera', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Hawaiian Islands', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Hokkaido', 'Japan');
INSERT INTO subregions (subregion, country) VALUES ('Honshu', 'Japan');
INSERT INTO subregions (subregion, country) VALUES ('Iceland (northeastern)', 'Iceland');
INSERT INTO subregions (subregion, country) VALUES ('Iceland (southeastern)', 'Iceland');
INSERT INTO subregions (subregion, country) VALUES ('Iceland (southern)', 'Iceland');
INSERT INTO subregions (subregion, country) VALUES ('Iceland (southwestern)', 'Iceland');
INSERT INTO subregions (subregion, country) VALUES ('Iceland (western)', 'Iceland');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (eastern)', 'India');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (southern)', 'Australia');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (southern)', 'France');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (southern)', 'South Africa');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (western)', 'Comoros');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (western)', 'France');
INSERT INTO subregions (subregion, country) VALUES ('Indian Ocean (western)', 'Madagascar');
INSERT INTO subregions (subregion, country) VALUES ('Italy', 'Italy');
INSERT INTO subregions (subregion, country) VALUES ('Izu, Volcano, and Mariana Islands', 'Japan');
INSERT INTO subregions (subregion, country) VALUES ('Izu, Volcano, and Mariana Islands', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Java', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Kamchatka Peninsula', 'Russia');
INSERT INTO subregions (subregion, country) VALUES ('Kermadec Islands', 'New Zealand');
INSERT INTO subregions (subregion, country) VALUES ('Korea', 'North Korea');
INSERT INTO subregions (subregion, country) VALUES ('Korea', 'North Korea-South Korea');
INSERT INTO subregions (subregion, country) VALUES ('Korea', 'South Korea');
INSERT INTO subregions (subregion, country) VALUES ('Krakatau', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Kuril Islands', 'Japan - administered by Russia');
INSERT INTO subregions (subregion, country) VALUES ('Kuril Islands', 'Russia');
INSERT INTO subregions (subregion, country) VALUES ('Lesser Sunda Islands', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Luzon', 'Philippines');
INSERT INTO subregions (subregion, country) VALUES ('México', 'Mexico');
INSERT INTO subregions (subregion, country) VALUES ('México', 'Mexico-Guatemala');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (eastern)', 'Iran');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (eastern)', 'Iran-Azerbaijan');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (eastern)', 'Saudi Arabia');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (eastern)', 'Syria');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (southern)', 'Saudi Arabia');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (southern)', 'Yemen');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (western)', 'Afghanistan');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (western)', 'Iran');
INSERT INTO subregions (subregion, country) VALUES ('Middle East (western)', 'Pakistan');
INSERT INTO subregions (subregion, country) VALUES ('Mindanao', 'Philippines');
INSERT INTO subregions (subregion, country) VALUES ('Mongolia', 'Mongolia');
INSERT INTO subregions (subregion, country) VALUES ('New Britain', 'Papua New Guinea');
INSERT INTO subregions (subregion, country) VALUES ('New Guinea and D''Entrecasteaux Islands', 'Papua New Guinea');
INSERT INTO subregions (subregion, country) VALUES ('New Ireland', 'Papua New Guinea');
INSERT INTO subregions (subregion, country) VALUES ('New Zealand', 'New Zealand');
INSERT INTO subregions (subregion, country) VALUES ('Nicaragua', 'Nicaragua');
INSERT INTO subregions (subregion, country) VALUES ('Northeast of New Guinea', 'Papua New Guinea');
INSERT INTO subregions (subregion, country) VALUES ('Northern Chile, Bolivia & Argentina', 'Argentina');
INSERT INTO subregions (subregion, country) VALUES ('Northern Chile, Bolivia & Argentina', 'Bolivia');
INSERT INTO subregions (subregion, country) VALUES ('Northern Chile, Bolivia & Argentina', 'Chile');
INSERT INTO subregions (subregion, country) VALUES ('Northern Chile, Bolivia & Argentina', 'Chile-Argentina');
INSERT INTO subregions (subregion, country) VALUES ('Northern Chile, Bolivia & Argentina', 'Chile-Bolivia');
INSERT INTO subregions (subregion, country) VALUES ('Northern Chile, Bolivia & Argentina', 'Chile-Peru');
INSERT INTO subregions (subregion, country) VALUES ('North of Iceland', 'Iceland');
INSERT INTO subregions (subregion, country) VALUES ('North of Luzon', 'Philippines');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (central)', 'France');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (central)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (Chilean Islands)', 'Chile');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (eastern)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (northern)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (northern)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (southern)', 'New Zealand');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (southern)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (southwestern)', 'France');
INSERT INTO subregions (subregion, country) VALUES ('Pacific Ocean (southwestern)', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Panamá', 'Panama');
INSERT INTO subregions (subregion, country) VALUES ('Perú', 'Peru');
INSERT INTO subregions (subregion, country) VALUES ('Russia (southeastern)', 'Russia');
INSERT INTO subregions (subregion, country) VALUES ('Ryukyu Islands and Kyushu', 'Japan');
INSERT INTO subregions (subregion, country) VALUES ('Samoan and Wallis Islands', 'France');
INSERT INTO subregions (subregion, country) VALUES ('Samoan and Wallis Islands', 'Samoa');
INSERT INTO subregions (subregion, country) VALUES ('Samoan and Wallis Islands', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Sangihe Islands', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Santa Cruz Islands', 'Solomon Islands');
INSERT INTO subregions (subregion, country) VALUES ('Southeast Asia', 'Burma (Myanmar)');
INSERT INTO subregions (subregion, country) VALUES ('Southeast Asia', 'China');
INSERT INTO subregions (subregion, country) VALUES ('Southeast Asia', 'Vietnam');
INSERT INTO subregions (subregion, country) VALUES ('Southern Chile and Argentina', 'Argentina');
INSERT INTO subregions (subregion, country) VALUES ('Southern Chile and Argentina', 'Chile');
INSERT INTO subregions (subregion, country) VALUES ('Southern Chile and Argentina', 'Chile-Argentina');
INSERT INTO subregions (subregion, country) VALUES ('Sulawesi', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Sulu Islands', 'Philippines');
INSERT INTO subregions (subregion, country) VALUES ('Sumatra', 'Indonesia');
INSERT INTO subregions (subregion, country) VALUES ('Taiwan', 'Taiwan');
INSERT INTO subregions (subregion, country) VALUES ('Tonga Islands', 'New Zealand');
INSERT INTO subregions (subregion, country) VALUES ('Tonga Islands', 'Tonga');
INSERT INTO subregions (subregion, country) VALUES ('Tonga Islands', 'Undersea Features');
INSERT INTO subregions (subregion, country) VALUES ('Turkey', 'Turkey');
INSERT INTO subregions (subregion, country) VALUES ('USA (Arizona)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (California)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Colorado)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Idaho)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Nevada)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (New Mexico)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Oregon)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Utah)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Washington)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('USA (Wyoming)', 'United States');
INSERT INTO subregions (subregion, country) VALUES ('Vanuatu', 'Vanuatu');
INSERT INTO subregions (subregion, country) VALUES ('Western Asia', 'Armenia');
INSERT INTO subregions (subregion, country) VALUES ('Western Asia', 'Armenia-Azerbaijan');
INSERT INTO subregions (subregion, country) VALUES ('Western Asia', 'Georgia');
INSERT INTO subregions (subregion, country) VALUES ('Western Asia', 'Russia');
INSERT INTO subregions (subregion, country) VALUES ('Western Europe', 'France');
INSERT INTO subregions (subregion, country) VALUES ('Western Europe', 'Germany');
INSERT INTO subregions (subregion, country) VALUES ('Western Europe', 'Spain');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'Dominica');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'France');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'Grenada');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'Netherlands');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'Saint Kitts and Nevis');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'Saint Lucia');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'Saint Vincent and the Grenadines');
INSERT INTO subregions (subregion, country) VALUES ('West Indies', 'United Kingdom');



COMMIT;