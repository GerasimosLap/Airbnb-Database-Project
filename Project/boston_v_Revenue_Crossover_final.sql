create table newlist3 as 
	select id,CASE 
			WHEN (calendar.price IS NULL) THEN listing.price
			ELSE calendar.price END AS betterprice,bedrooms,zipcode,date 
			from listing,calendar 
			where listing.zipcode in ('02108','02109','02110','02111','02113','02114','02115','02116','02118','02119','02120','02121','02122','02124','02125','02126','02127','02128','02129','02130','02131','02132','02134','02135','02136','02138','02139','02141','02143','02145','02163','02210','02215','02445','02446','02467')
			AND listing.id=calendar.listing_id
			AND listing.bedrooms>0 
			AND (listing.room_type NOT LIKE 'Entire home/apt') 
			AND listing.zipcode IS NOT NULL
			AND listing.bedrooms IS NOT NULL
			AND calendar.date IS NOT NULL
;

create materialized view boston_v_Revenue_Crossover_final AS

SELECT newlist3.date,newlist3.zipcode::integer,newlist3.bedrooms::integer,PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice)::float as airbnb_price_day ,zillow.price::float as zillow_price_month,(zillow.price/PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY betterprice))::float as crossover_pt
from newlist3
join zillow
on date_trunc('month', newlist3.date)::date = zillow.date
where newlist3.zipcode::integer=zillow.zipcode and newlist3.bedrooms=zillow.bedrooms 
group by newlist3.date,newlist3.zipcode,newlist3.bedrooms,zillow.price;


--7670 rows--


























































































