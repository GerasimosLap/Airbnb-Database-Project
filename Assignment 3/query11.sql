SELECT listings.id,listings.property_type,listings.number_of_reviews,calendar.date
FROM listings
JOIN calendar
ON listings.id=calendar.listing_id
WHERE calendar.date BETWEEN '2017-12-22' AND '2017-12-27' AND listings.number_of_reviews>'20' AND calendar.available=true
ORDER BY listings.number_of_reviews desc ;

/*
	finds all available houses for christmas holidays that have more than 20 reviews, print each house's id 
	and property type and sort by number of reviews in descending order.
	Output: 5864 rows
*/