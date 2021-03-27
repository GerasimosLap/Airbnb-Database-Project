SELECT DISTINCT calendar.date,reviews.listing_id,calendar.price
FROM calendar
FULL OUTER JOIN reviews
ON calendar.listing_id=reviews.listing_id
WHERE reviews.listing_id = 1068507 and calendar.available=true
ORDER BY calendar.date;

/* Outer joins calendar and reviews to find a specific house's availability dates and prints oredered dates and prices
Output:  275
*/