create table newlist5 as 
	select id,CASE 
			WHEN (calendar.price IS NULL) THEN listing.price
			ELSE calendar.price END AS betterprice,bedrooms,zipcode,date 
			from listing,calendar 
			where listing.zipcode in ('97217','97219','97214','97266','97210','97211','97213','97220','97232','97202','97218','97223','97215','97216','97236','97212','97205','97239','97201','97221','97230','97206','97203','97209','97227','97233','97229','97204','97231')
			AND listing.id=calendar.listing_id
			AND listing.bedrooms>0 
			AND (listing.room_type NOT LIKE 'Entire home/apt') 
			AND listing.zipcode IS NOT NULL
			AND listing.bedrooms IS NOT NULL
			AND calendar.date IS NOT NULL
;

create materialized view portland_v_Revenue_Crossover_final AS

SELECT newlist5.date,newlist5.zipcode::integer,newlist5.bedrooms::integer,PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice)::float as airbnb_price_day ,zillow.price::float as zillow_price_month,(zillow.price/PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice))::float as crossover_pt
from newlist5
join zillow
on date_trunc('month', newlist5.date)::date = zillow.date
where newlist5.zipcode::integer=zillow.zipcode and newlist5.bedrooms=zillow.bedrooms 
group by newlist5.date,newlist5.zipcode,newlist5.bedrooms,zillow.price;

--3737 rows--