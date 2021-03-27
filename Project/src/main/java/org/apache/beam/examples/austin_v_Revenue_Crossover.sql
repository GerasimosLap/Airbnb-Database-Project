create table newlist1 as 
	select id,CASE 
			WHEN (calendar.price IS NULL) THEN listing.price
			ELSE calendar.price END AS betterprice,bedrooms,zipcode,date 
			from listing,calendar 
			where listing.zipcode in ('78701','78702','78703','78704','78705','78712','78717','78721','78722','78723','78726','78727','78729','78730','78731','78732','78733','78735','78736','78737','78739','78741','78744','78745','78746','78747','78748','78749','78750','78751','78752','78753','78756','78757','78758','78759')
			AND listing.id=calendar.listing_id
			AND listing.bedrooms>0 
			AND (listing.room_type NOT LIKE 'Entire home/apt') 
			AND listing.zipcode IS NOT NULL
			AND listing.bedrooms IS NOT NULL
			AND calendar.date IS NOT NULL
;

create materialized view austin_v_Revenue_Crossover_final AS

SELECT newlist1.date,newlist1.zipcode::integer,newlist1.bedrooms::integer,PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice)::float as airbnb_price_day ,zillow.price::float as zillow_price_month,(zillow.price/PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice))::float as crossover_pt
from newlist1
join zillow
on date_trunc('month', newlist1.date)::date = zillow.date
where newlist1.zipcode::integer=zillow.zipcode and newlist1.bedrooms=zillow.bedrooms 
group by newlist1.date,newlist1.zipcode,newlist1.bedrooms,zillow.price;

--14932 rows--