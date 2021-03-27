CREATE OR REPLACE FUNCTION trigger_number_of_reviews()
 RETURNS trigger AS
$$
BEGIN
if(TG_OP='INSERT') THEN
    UPDATE listing
        SET number_of_reviews = number_of_reviews+1
        WHERE
         id=NEW.listing_id;
END IF;
if(TG_OP='DELETE') THEN
        UPDATE listing
        SET number_of_reviews = number_of_reviews-1
        WHERE
         id=OLD.listing_id;
END IF;
return new;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER last_name_changes
 AFTER INSERT OR DELETE 
 ON review
 FOR EACH ROW
 EXECUTE PROCEDURE trigger_number_of_reviews();




 CREATE OR REPLACE FUNCTION trigger_date_of_reviews()
 RETURNS trigger AS
$$
BEGIN
if NEW.date > CURRENT_DATE then 
    RAISE EXCEPTION 'date cannot be greater than current date';

else 
    return new;
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER date_addition
 AFTER INSERT 
 ON review
 for each row
 EXECUTE PROCEDURE trigger_date_of_reviews();

 /* Trigger that raises exception when a review is trying to be added when the date is greater than today's date */

