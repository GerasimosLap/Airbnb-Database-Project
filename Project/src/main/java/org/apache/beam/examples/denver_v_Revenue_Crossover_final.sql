create table newlist4 as 
	select id,CASE 
			WHEN (calendar.price IS NULL) THEN listing.price
			ELSE calendar.price END AS betterprice,bedrooms,zipcode,date 
			from listing,calendar 
			where listing.zipcode in ('80231','80203','80205','80204','80216','80224','80223','80220','80209','80235','80238','80212','80247','80221','80211','80239','80210','80202','80206','80222','80219','80218','80246','80230','80123','80227','80214','80237','80236','80207','80249','80014')
			AND listing.id=calendar.listing_id
			AND listing.bedrooms>0 
			AND (listing.room_type NOT LIKE 'Entire home/apt') 
			AND listing.zipcode IS NOT NULL
			AND listing.bedrooms IS NOT NULL
			AND calendar.date IS NOT NULL
;

create materialized view denver_v_Revenue_Crossover_final AS

SELECT newlist4.date,newlist4.zipcode::integer,newlist4.bedrooms::integer,PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice)::float as airbnb_price_day ,zillow.price::float as zillow_price_month,(zillow.price/PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice))::float as crossover_pt
from newlist4
join zillow
on date_trunc('month', newlist4.date)::date = zillow.date
where newlist4.zipcode::integer=zillow.zipcode and newlist4.bedrooms=zillow.bedrooms 
group by newlist4.date,newlist4.zipcode,newlist4.bedrooms,zillow.price;


--4792 rows--








































































































