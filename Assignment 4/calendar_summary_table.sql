


SELECT DISTINCT id, calendar_updated, calendar_last_scraped,
availability_30, availability_60, availability_90 INTO Calendar_Summary
FROM Listings;


ALTER TABLE Calendar_Summary RENAME COLUMN id TO listing_id;

ALTER TABLE Calendar_Summary RENAME COLUMN calendar_last_scraped TO from_date;


ALTER TABLE Listings
DROP calendar_updated,DROP calendar_last_scraped,
DROP availability_30,DROP availability_60,DROP availability_90;


ALTER TABLE Calendar_Summary ADD PRIMARY KEY (listing_id);

ALTER TABLE Listings ADD FOREIGN KEY(id) references Calendar_Summary(listing_id);