UPDATE Host 
SET response_rate=replace(response_rate,'%','');
UPDATE Host 
SET response_rate = CASE WHEN response_rate ='N/A' THEN NULL ELSE response_rate END;
ALTER TABLE Host
ALTER COLUMN response_rate TYPE numeric USING response_rate::numeric;

ALTER TABLE Host 
ADD  city varchar,
ADD  state varchar,
ADD country varchar;

UPDATE Host
SET city=split_part(location,',',1);

UPDATE Host
SET state=split_part(location,',',2);

UPDATE Host
SET country=split_part(location,',',3);

ALTER TABLE Host 
DROP COLUMN location;


UPDATE Listing
SET amenities=replace(amenities,'"','');
UPDATE Listing
SET amenities=replace(amenities,'{','');
UPDATE Listing
SET amenities=replace(amenities,'}','');
CREATE TABLE Amenity AS (
	SELECT DISTINCT regexp_split_to_table(amenities, ',')
	from Listing
	where amenities!=''
);

ALTER TABLE Amenity ADD COLUMN amenity_id serial;
ALTER TABLE Amenity rename regexp_split_to_table to amenity_name;
ALTER TABLE Amenity ADD PRIMARY KEY (amenity_id);


CREATE TABLE joining_amenities as(
SELECT Amenity.amenity_id, Listing.id from Amenity, Listing 
WHERE Amenity.amenity_name=any(regexp_split_to_array(Listing.amenities,',')::varchar[])
);


ALTER TABLE joining_amenities ADD PRIMARY KEY(amenity_id,id);

ALTER TABLE Listing DROP COLUMN amenities;