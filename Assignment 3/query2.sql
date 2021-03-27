SELECT listings.host_id, listings.neighbourhood
FROM listings
WHERE listings.host_neighbourhood=listings.neighbourhood;

/*find all hosts that offer houses in the same neighbourhood they live in
Output: 6651 rows
 */