SELECT AVG(listing.price)
FROM Listing,joining_amenities
JOIN Amenity
ON Amenity.amenity_id=joining_amenities.amenity_id 
WHERE joining_amenities.id=Listing.id
GROUP BY amenity.amenity_name
HAVING Amenity.amenity_name='Pool';

/* Find the average listing price of all listings with Pool
Output: 1 row
*/



SELECT COUNT(listing.id),calendar.price
FROM Listing
JOIN calendar
ON calendar.listing_id=Listing.id
WHERE calendar.date BETWEEN '2017-12-01' AND '2017-12-31'
GROUP BY calendar.price
HAVING calendar.price >= '500';

/* Find and count listings between 2017-12-01 and 2017-12-31 that cost more than 500$ and group them by their price
Output: 476 rows
*/



SELECT COUNT(amenity_id),Listing.id
FROM joining_amenities
JOIN Listing
ON Listing.id=joining_amenities.id
GROUP BY Listing.id
HAVING COUNT(amenity_id)>30;

/* Find all listings that have more than 30 amenities
Output: 23 rows
*/

SELECT MAX(host.total_listings_count),host.id
FROM host
WHERE host.identity_verified=true
GROUP BY host.total_listings_count,host.id
ORDER BY host.total_listings_count DESC
LIMIT 10;


/* Top 10 host with total listings count that are verified
Output: 10 rows
*/

SELECT AVG(host.response_rate)
FROM Host
JOIN Listing
ON host.id=listing.host_id
WHERE host.response_rate is not null AND host.city=listing.city;

/* Average host.response_rate of hosts that rent a house in the city they live in
Output: 1 row
*/


SELECT Host.is_superhost,Host.id,calendar.price,calendar.listing_id,Host.since,COUNT(calendar.price)
FROM Listing
JOIN calendar
ON Listing.id=calendar.listing_id
JOIN Host
ON Host.id=Listing.host_id
WHERE Host.is_superhost='t' AND Host.since BETWEEN '2012-12-31' AND '2014-12-31'
GROUP BY Host.is_superhost,Host.id,calendar.price,calendar.listing_id,Host.since
HAVING calendar.price BETWEEN 1000 AND 1500 
ORDER BY Host.since ASC;

/* Find all superhosts, print their registration date and the price of their listings during 2012-12-31 and 2014-12-31
Output: 136 rows
*/
