SELECT listings.id,listings.host_name,listings.host_id,calendar.date
FROM listings
INNER JOIN calendar
ON listings.host_is_superhost=true
WHERE calendar.listing_id=listings.id;

/* Find all reviews on residencies that are owned by superhost
Output: 635100
*/