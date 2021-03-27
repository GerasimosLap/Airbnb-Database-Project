SELECT DISTINCT host_id, host_url, host_name, host_since, host_location, host_about,
host_response_time, host_response_rate, host_acceptance_rate, host_is_superhost,
host_thumbnail_url, host_picture_url, host_neighbourhood, host_listings_count,
host_total_listings_count, host_verifications, host_has_profile_pic, host_identity_verified,
calculated_host_listings_count INTO Host
FROM Listings;




ALTER TABLE Listings
DROP host_url, DROP host_name, DROP host_since, DROP host_location, DROP host_about, DROP
	host_response_time, DROP host_response_rate, DROP host_acceptance_rate, DROP host_is_superhost, DROP
	host_thumbnail_url, DROP host_picture_url, DROP host_neighbourhood, DROP host_listings_count, DROP
	host_total_listings_count, DROP host_verifications, DROP host_has_profile_pic, DROP host_identity_verified, DROP
	calculated_host_listings_count;

	
	
	
	
	
ALTER TABLE host
RENAME COLUMN host_id TO id ;
ALTER TABLE host
RENAME COLUMN host_url TO url;
ALTER TABLE host  
RENAME COLUMN host_name TO name ; 
ALTER TABLE host 
RENAME COLUMN host_since TO since ;
ALTER TABLE host  
RENAME COLUMN host_location TO location;
ALTER TABLE host  
RENAME COLUMN host_about TO about;  
ALTER TABLE host
RENAME COLUMN host_response_time TO response_time ;  
ALTER TABLE host
RENAME COLUMN host_response_rate TO response_rate  ;  
ALTER TABLE host
RENAME COLUMN host_acceptance_rate TO acceptance_rate ; 
ALTER TABLE host 
RENAME COLUMN host_is_superhost TO is_superhost ;  
ALTER TABLE host
RENAME COLUMN host_thumbnail_url TO thumbnail_url ;  
ALTER TABLE host
RENAME COLUMN host_picture_url TO picture_url  ;  
ALTER TABLE host
RENAME COLUMN host_neighbourhood TO neighbourhood ;  
ALTER TABLE host
RENAME COLUMN host_listings_count TO listings_count ;  
ALTER TABLE host
RENAME COLUMN host_total_listings_count TO total_listings_count  ; 
ALTER TABLE host 
RENAME COLUMN host_verifications TO verifications;  
ALTER TABLE host
RENAME COLUMN host_has_profile_pic TO has_profile_pic  ;  
ALTER TABLE host
RENAME COLUMN host_identity_verified TO identity_verified  ;
ALTER TABLE host  
RENAME COLUMN calculated_host_listings_count TO calculated_listings_count;




ALTER TABLE Host ADD PRIMARY KEY (id);

ALTER TABLE Listings ADD FOREIGN KEY(host_id) references Host(id);