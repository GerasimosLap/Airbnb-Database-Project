SELECT listings.host_identity_verified,listings.host_name, listings.id,reviews.id
FROM listings
JOIN reviews
ON listings.id=reviews.listing_id
WHERE listings.host_identity_verified=true;

/* Find all reviews on residencies whose host has been verfied 
Output: 118151
*/