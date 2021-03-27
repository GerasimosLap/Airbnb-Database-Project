SELECT Count(neighbourhoods.neighbourhood),listings.neighbourhood
FROM listings
JOIN neighbourhoods
ON listings.neighbourhood_cleansed=neighbourhoods.neighbourhood
Group BY listings.neighbourhood;

/* Counts and prints how many residencies are in each neighbourhood
Output:  82
*/