SELECT neighbourhoods.neighbourhood_group,neighbourhoods.neighbourhood,listings.neighbourhood
FROM listings
FULL OUTER JOIN neighbourhoods
ON neighbourhoods.neighbourhood=listings.neighbourhood_cleansed
WHERE listings.neighbourhood IS NOT NULL
ORDER BY listings.neighbourhood ,  neighbourhoods.neighbourhood;

/* Show all legit neighbourhoods and their corresponding id 
Output: 7331
*/