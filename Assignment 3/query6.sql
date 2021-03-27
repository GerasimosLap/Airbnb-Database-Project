SELECT neighbourhoods.neighbourhood,reviews.id
FROM neighbourhoods
JOIN reviews
ON neighbourhoods.neighbourhood=78704
LIMIT 1000;
/* Show all review ids on neighbourhood with id 78704 and the reviewr's id 
Output: 134550
*/