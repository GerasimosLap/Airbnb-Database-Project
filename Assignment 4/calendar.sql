
UPDATE Calendar
SET 
	price=replace(price,'$','');
UPDATE Calendar
SET 
	price=replace(price,',','');

ALTER TABLE Calendar ALTER COLUMN price TYPE numeric USING price::numeric;