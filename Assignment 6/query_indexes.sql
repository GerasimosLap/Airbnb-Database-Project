CREATE INDEX indexforreviewid ON review (listing_id);

ALTER TABLE listing
ALTER COLUMN review_scores_rating type numeric USING cast(review_scores_rating as numeric),
ALTER COLUMN review_scores_accuracy type numeric USING cast(review_scores_accuracy as numeric),
ALTER COLUMN review_scores_cleanliness type numeric USING cast(review_scores_cleanliness as numeric),
ALTER COLUMN review_scores_checkin type numeric USING cast(review_scores_checkin as numeric),
ALTER COLUMN review_scores_communication type numeric USING cast(review_scores_communication as numeric),
ALTER COLUMN review_scores_location type numeric USING cast(review_scores_location as numeric),
ALTER COLUMN review_scores_value type numeric USING cast(review_scores_value as numeric);

CREATE INDEX tripletindex ON listing (guests_included,review_scores_rating,price)
WHERE  NOT(guests_included <= 5 and review_scores_rating <= 90 and price >= 300);

CREATE INDEX poolindex ON joining_amenities (amenity_id,id);

CREATE INDEX groupprices ON calendar (date,price)
WHERE NOT(date<'2017-12-01' AND date>'2017-12-31' AND price<'500'); 


CREATE INDEX identityindex ON host (identity_verified)
WHERE  (host.identity_verified=true);


CREATE INDEX cityindex ON listing (city);
CREATE INDEX hostindex ON host (city,response_rate nulls last);


CREATE INDEX bigindexpart1 ON Listing (host_id);

CREATE INDEX bigindexpart2 ON calendar (price)
WHERE NOT (price<1000 AND price>1500);

CREATE INDEX bigindexpart3 ON Host (since,is_superhost)
WHERE NOT (since<'2012-12-31' AND since>'2014-12-31' AND is_superhost='f');
















