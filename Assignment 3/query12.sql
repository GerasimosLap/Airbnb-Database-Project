SELECT listings.id,listings.price,listings.neighbourhood,calendar.date
FROM listings
JOIN calendar
ON listings.id=calendar.listing_id
WHERE listings.review_scores_cleanliness='10' AND listings.cancellation_policy='flexible' AND calendar.available=true
ORDER BY listings.price
LIMIT 5000;

/* 
	find all houses that are extremely clean and are flexible for cancellation, print out the houses id,price and neighbourhood
	Output: 5000 rows
*/