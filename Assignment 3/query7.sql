SELECT neighbourhoods.neighbourhood, calendar.listing_id,calendar.price,calendar.date
FROM neighbourhoods
JOIN calendar 
ON neighbourhoods.neighbourhood=78704
WHERE calendar.price<='200' and calendar.available=true and calendar.price IS NOT NULL and calendar.date BETWEEN '2017-12-01' and '2017-12-31';

/* Show all ids of residencies that are under 200$, have neighbourhood id 78704 and are available during the period of December 2017
Output: 41308
*/