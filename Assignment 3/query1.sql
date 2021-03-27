SELECT listings.id, reviews.reviewer_name
FROM listings
INNER JOIN reviews
ON listings.id = reviews.listing_id
WHERE reviews.date = '2015-02-15';

/*find all reviews that took place on 2015-02-15 and return the house's id and name of the reviewer
Output: 89 rows
 */