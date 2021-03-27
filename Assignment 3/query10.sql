SELECT listings.id,reviews.id,reviews.comments,listings.neighbourhood
FROM listings
JOIN reviews
ON listings.id=reviews.listing_id
WHERE listings.property_type='House' AND listings.bedrooms=2
ORDER BY listings.neighbourhood;

/*
	find all houses with 2 bedrooms reviews and sorts the houses by neighbourhood, prints out
	the house's and review's id and the review itself.
	Output: 16173 rows
*/