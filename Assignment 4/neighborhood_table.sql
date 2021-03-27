SELECT DISTINCT  neighbourhood,neighbourhood_cleansed INTO Neighborhood
FROM Listings;

ALTER TABLE Neighborhood RENAME COLUMN neighbourhood TO neighborhood_name;

DELETE FROM Neighborhood WHERE neighborhood_name IS NULL;


ALTER TABLE Neighborhood RENAME COLUMN neighbourhood_cleansed TO zip_code;

DELETE FROM Neighborhood WHERE zip_code IS NULL;

ALTER TABLE Listings
DROP COLUMN neighbourhood_group_cleansed;

DROP TABLE Neighbourhoods;

ALTER TABLE Listings RENAME COLUMN neighbourhood TO neighborhood;

ALTER TABLE Neighborhood ADD PRIMARY KEY (neighborhood_name,zip_code);

ALTER TABLE Listings ADD FOREIGN KEY(neighborhood,neighbourhood_cleansed) references Neighborhood(neighborhood_name,zip_code);


ALTER TABLE Summary_Listings RENAME COLUMN neighbourhood TO zip_code;


ALTER TABLE Summary_Listings DROP COLUMN neighbourhood_group;



