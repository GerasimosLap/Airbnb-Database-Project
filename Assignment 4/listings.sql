UPDATE Listings
SET 
 street=split_part(street,',',1),
 price=replace(price,'$',''),
 weekly_price=replace(weekly_price,'$',''),
 monthly_price=replace(monthly_price,'$',''),
 security_deposit=replace(security_deposit,'$',''),
 cleaning_fee=replace(cleaning_fee,'$','');
 
UPDATE Listings
SET
 price=replace(price,',',''),
 weekly_price=replace(weekly_price,',',''),
 monthly_price=replace(monthly_price,',',''),
 security_deposit=replace(security_deposit,',',''),
 cleaning_fee=replace(cleaning_fee,',','');
 
ALTER TABLE Listings
ALTER COLUMN price TYPE numeric USING price::numeric,
ALTER COLUMN weekly_price TYPE numeric USING weekly_price::numeric,
ALTER COLUMN monthly_price TYPE numeric USING monthly_price::numeric,
ALTER COLUMN security_deposit TYPE numeric USING security_deposit::numeric,
ALTER COLUMN cleaning_fee TYPE numeric USING cleaning_fee::numeric;
