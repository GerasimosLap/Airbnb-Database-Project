package org.apache.beam.examples;

import org.apache.beam.examples.common.ExampleUtils;
import org.apache.commons.csv.*;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Distribution;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import java.util.*;
import java.io.*;
 

// Η περιγραφή της λειτουργικότητας του κώδικα βρίσκεται στο Pdf, όλη η προεπεξεργασία έγινε με Java 

public class WordCount {
	static int z=0;
	static int k=0;
	static int l=0;
	static int nn=0;
	static int amenityCounter=0;
  static List<String[]> records = new ArrayList<String[]>();
  static List<String> primaryKeys=new ArrayList<String>();
  static List<String> hosts=new ArrayList<String>();
  static List<List<String>> changes = new ArrayList<List<String>>();
  static List<List<String>> amenityPairs = new ArrayList<List<String>>();
  
  public interface WordCountOptions extends PipelineOptions {
    @Description("Path of the file to read from")
    @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
    String getInputFile();

    void setInputFile(String value);

    @Description("Path of the file to write to")
    @Required
    String getOutput();

    void setOutput(String value);
  }

  ///////////////////////////////////// part 1 ///////////////////////////////////////////
  static String mutateHeader(String text){
    
    List<String> ok=Arrays.asList(text.split(","));
    String result="";
        for(int i=0;i<ok.size();i++){
          if(ok.get(i).equals("RegionName")) {result+="zipcode,"; continue;}
          if(ok.get(i).matches("(\\d{4})-(\\d{2})")){
            result=result+ok.get(i)+"-01";
          }else{
            result+=ok.get(i);
          }
          if(i==ok.size()-1)break;
          result+=",";
        }
    return result;
  }
  public static int findPrimaryKey(String header,String primary){
  	List<String> head=Arrays.asList(header.split(","));
  	for(int i=0;i<head.size();i++) if(head.get(i).equals(primary)) return i;
  	return 0;
  }

  public static boolean exists(List<String> primaries,String itsPrimary){
  	for(String i: primaries) { if(i.equals(itsPrimary)){return true;}}
  		return false;
  }
 
  public static int findIndexes(String text){
  		int ok1= 0;
  		List<String> separate= Arrays.asList(text.split(","));
  		for(int i=0;i<separate.size();i++){
  			if(separate.get(i).equals("2016-01-01")){ok1=i; break;}
  		}
  		return ok1;
  }
  ////////////////////////////////////////////////////////////////////////
  public static boolean existsHost(List<String> primaries,String itsPrimary){
  	for(String i: primaries) { if(i.equals(itsPrimary)){return true;}}
  		return false;
  }
  public static void validateHost(String filepath,CSVPrinter printer){
  			try{
  				Reader in = new FileReader(filepath);
    			Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
  				for(CSVRecord record:csvRecords){
    				if(!existsHost(hosts,record.get("id"))){
    					String[] s = toArray(record);
    					hosts.add(record.get("id"));
    					printerr(printer,s);
    				}else{
    					System.out.println(record.get("id"));
    				}
    			}
    		}catch(Exception e){}
  	}

  public static void fillHost(String filepath){
  	try{
 			Reader in = new FileReader(filepath);
    		Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
  
    		for(CSVRecord record:csvRecords){
    			hosts.add(record.get("id"));

    		}
    }catch(Exception e){}
  }

  //////////////////////////////////////////////////////////////////////////
   public static String[] toArray(CSVRecord rec) {
            String[] arr = new String[rec.size()];
            int i = 0;
            for (String str : rec) {
                arr[i++] = str;
            }
            return arr;
        }


    public static void printerr(CSVPrinter printer, String[] s) throws Exception {
        for (String val : s) {
            printer.print(val != null ? String.valueOf(val) : "");
        }
        printer.println();
    }


  public static int AmenityExists(String amenity){
  		for(int p=0; p<amenityPairs.size();p++)if(amenityPairs.get(p).get(0).toLowerCase().equals(amenity.toLowerCase())) return p;
  		return -1;
  }
  public static void checkAmenities(String amenityFile,String listingToAmenityFile,CSVPrinter printer){
 
  		try{
  			nn++;
 			Reader in = new FileReader(amenityFile);
    		Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    		List<List<String>> copy= new ArrayList<List<String>>();
    		for(int i=0;i<amenityPairs.size();i++){
    			copy.add(amenityPairs.get(i));
    		}
 			for (CSVRecord csvRecord : csvRecords) {
 				List<String> changes1 = new ArrayList<String>(); 
 				int indexOfExistance=AmenityExists(csvRecord.get("amenity_name"));
  				if(indexOfExistance==-1){
  					changes1.add(csvRecord.get("amenity_name"));
  					changes1.add(String.valueOf(++amenityCounter));
  					changes1.add("1");
  					
  					System.out.println(changes1.get(0)+" " + changes1.get(1)+ " "+changes1.get(2)+ " ooooooooooooook");
         			amenityPairs.add(changes1);
         			//changes.add(changes1);
         			changes1.add(csvRecord.get("amenity_id"));
         			copy.add(changes1);
  				
  			}else{
  				copy.get(indexOfExistance).remove(2);
  				copy.get(indexOfExistance).add("1");
  				copy.get(indexOfExistance).add(csvRecord.get("amenity_id"));

  			}
  		}
  			//for(int p=0; p<amenityPairs.size();p++)if(amenityPairs.get(p).get(2).equals("1"))System.out.println(amenityPairs.get(p).get(0)+" "+amenityPairs.get(p).get(1)+" "+amenityPairs.get(p).get(3));
  			Reader in2 = new FileReader(listingToAmenityFile);
  			Iterable<CSVRecord> l2Amenity = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in2);
  			for(CSVRecord record: l2Amenity){
  				if(Integer.valueOf(record.get("amenity_id"))==161){continue;}
  				String[] s = toArray(record);
  				//System.out.println(s[1]);
  				if(nn==2){if(record.get("amenity_id").equals("46")){ s[1]="53"; printerr(printer, s); continue; }}
  				if(nn==3){if(record.get("amenity_id").equals("44")){ s[1]="53"; printerr(printer, s); continue; }}
  				for(int p=0; p<copy.size();p++){
  					if(copy.get(p).get(2).equals("1") &&copy.get(p).get(3).equals(s[1])) 
  						{
  							//System.out.print(s[1]); 
  							s[1]=copy.get(p).get(1); 
  							break;
  							//System.out.println(s[1]);
  						} 
  					}
  				//System.out.println(s[1]);

  				printerr(printer, s);

  			}
  
  		}catch(Exception e){System.out.println(e+ "okkk");}

  }
  public static void scanAmenities(String filename){
  	try{
  		Reader in = new FileReader(filename);
    	Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
 		for(CSVRecord csv: csvRecords){
 			List<String> ok= new ArrayList<String>();
 			ok.add(csv.get("amenity_name"));
 			ok.add(csv.get("amenity_id"));
 			ok.add("0");
 			amenityPairs.add(ok);
 			amenityCounter++;
 		}
 		
 	}catch(Exception e){System.out.println(e);}

  }
  /////////////////////////////////////////////////////////////////////////
  public static void deleteColumn(String filepath,String text,String column){
		try{
	  	int ind=-1;
	  	List<String> separate= Arrays.asList(text.split(","));
	  	for(int i=0;i<separate.size();i++){
	  		if(separate.get(i).equals(column)){
	  			ind=i;
          break;
	  		}
	  	}
	  	Reader in = new FileReader(filepath);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		FileWriter writer= new FileWriter("newlisting.csv");
  		
  		CSVPrinter csvP =new CSVPrinter(writer,CSVFormat.RFC4180.withHeader("id,listing_url,scrape_id,last_scraped,name,summary,space,description,experiences_offered,neighborhood_overview,notes,transit,access,interaction,house_rules,thumbnail_url,medium_url,picture_url,xl_picture_url,host_id,street,neighborhood,neighbourhood_cleansed,city,state,zipcode,market,smart_location,country_code,country,latitude,longitude,is_location_exact,property_type,room_type,accommodates,bathrooms,bedrooms,beds,bed_type,square_feet,price,weekly_price,monthly_price,security_deposit,cleaning_fee,guests_included,extra_people,minimum_nights,maximum_nights,has_availability,availability_365,number_of_reviews,first_review,last_review,review_scores_rating,review_scores_accuracy,review_scores_cleanliness,review_scores_checkin,review_scores_communication,review_scores_location,review_scores_value,requires_license,license,jurisdiction_names,instant_bookable,cancellation_policy,require_guest_profile_picture,require_guest_phone_verification,reviews_per_month"));
  		for (CSVRecord record : records) {
			String[] temp = new String[record.size()-1];
		
		int i=-1;
		int y=0;
      	for(String rec:record){
      		i++;
			if(i==ind){
          		continue;
        	}else{
          		temp[y]=record.get(i); 
          		y++;
  			}
		}
		    		printerr(csvP, temp);
    	}
		writer.flush();  
		writer.close();
		}catch(Exception e){System.out.println(e);}
	}

	public static void newTable(String filename,String header,CSVPrinter printer){
		try{
		z++;
		List<String> splittedHeader= Arrays.asList(header.split(","));
		Reader in = new FileReader(filename);
    	Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    	for(CSVRecord record:csvRecords){
    		String[] s= toArray(record);
    		for(int i=0;i<25;i++){
    			String[] k=new String[9];
    			for(int kop=0;kop<=5;kop++){
    				k[kop]=record.get(kop);
    			}
    			k[6]=splittedHeader.get(6+i);
    			k[7]=record.get(6+i);
    			k[8]=String.valueOf(z);

    			printerr(printer,k);
    		}
    	}
 		}catch(Exception e){System.out.println(e);}
	}
	////////////////////////////////////////////////////////////////////// 
	public static void updateSumList(String filename,String neigh, CSVPrinter printer){
		try{
			List<List<String>> ok = getPairs(neigh);
 			Reader in = new FileReader(filename);
    		Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
 			for (CSVRecord csv : csvRecords) {
  				String[] s= toArray(csv);
  				//csv.get("zip_code").equals("Irvington")||csv.get("zip_code").equals("Sun Valley")||csv.get("zip_code").equals("College View - South Platte")||csv.get("zip_code").equals("Gateway - Green Valley Ranch")||csv.get("zip_code").equals("DIA")||csv.get("zip_code").equals("Sloan Lake")||csv.get("zip_code").equals("Civic Center")||csv.get("zip_code").equals("Union Station")
  				if(csv.get("id").equals("1810172")||csv.get("id").equals("6513924")||csv.get("id").equals("7093109")||csv.get("id").equals("14690527")){continue;}
  				if(csv.get("zip_code").equals("Longwood Medical Area")||csv.get("zip_code").equals("Bay Village")||csv.get("zip_code").equals("South Boston Waterfront")){continue;}
  				if(csv.get("zip_code").contains("Fenway")){s[4]="02446"; continue;}
  				if(csv.get("zip_code").contains("Brighton")||csv.get("zip_code").contains("Allston")){s[4]="02135"; continue;}
  				for(int i=0; i<ok.size();i++){
  					if(ok.get(i).get(0).equals(csv.get("zip_code"))){
  						s[4]=ok.get(i).get(1); 
  						break;
  					}
  				}
  				printerr(printer,s);
    		}
    	}catch(Exception e){System.out.println(e);}
	}


	public static List<List<String>> getPairs(String filename){
		List<List<String>> ok = new ArrayList<List<String>>();
		try{
			Reader in = new FileReader(filename);
    		Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
 			for (CSVRecord csv : csvRecords) {
  				List<String> k = new ArrayList<String>();
  				k.add(csv.get("neighbourhood_name"));
  				k.add(csv.get("zip_code"));
  				ok.add(k);
    		}
    	}catch(Exception e){System.out.println(e+"ok");}
    	return ok;
	}
  ////////////////////////////////////////////////////////////////////////
  public static void checkDuplicates(String filepath,CSVPrinter print){
 	try{
 	Reader in = new FileReader(filepath);
    Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
 	for (CSVRecord csvRecord : csvRecords) {
  		if(!exists(primaryKeys,csvRecord.get("listing_id"))){
            primaryKeys.add(csvRecord.get("listing_id"));
            //System.out.println(csvRecord);
            print.printRecord(csvRecord);
           l++;

        }else{
        	System.out.println(csvRecord);
        	 k++;
        }
    }
    }catch(Exception e){System.out.println(e);}
	

  }

  public static void checkDuplicatesWithBase(String filepath){
 	try{
 	Reader in = new FileReader(filepath);
    Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
 	for (CSVRecord csvRecord : csvRecords) {
  		if(exists(primaryKeys,csvRecord.get("id"))){
          	l--; 
        }
    }
    }catch(Exception e){System.out.println(e);}
	

  }
  static void runWordCount(WordCountOptions options) {
    Pipeline p = Pipeline.create(options);
    String head="RegionName,City,State,Metro,CountyName,SizeRank,2011-01,2011-02,2011-03,2011-04,2011-05,2011-06,2011-07,2011-08,2011-09,2011-10,2011-11,2011-12,2012-01,2012-02,2012-03,2012-04,2012-05,2012-06,2012-07,2012-08,2012-09,2012-10,2012-11,2012-12,2013-01,2013-02,2013-03,2013-04,2013-05,2013-06,2013-07,2013-08,2013-09,2013-10,2013-11,2013-12,2014-01,2014-02,2014-03,2014-04,2014-05,2014-06,2014-07,2014-08,2014-09,2014-10,2014-11,2014-12,2015-01,2015-02,2015-03,2015-04,2015-05,2015-06,2015-07,2015-08,2015-09,2015-10,2015-11,2015-12,2016-01,2016-02,2016-03,2016-04,2016-05,2016-06,2016-07,2016-08,2016-09,2016-10,2016-11,2016-12,2017-01,2017-02,2017-03,2017-04,2017-05,2017-06,2017-07,2017-08,2017-09,2017-10,2017-11,2017-12,2018-01";
    head=mutateHeader(head);
    int okk= findIndexes(head);
   	List<String> headsplit=Arrays.asList(head.split(","));
   	head="";
   	for(int i=0;i<headsplit.size();i++){
   		if(i>5 && i<okk){ System.out.println(i); continue;}
   		if(i==headsplit.size()-1) {head+=headsplit.get(i); break;}
   		head=head+headsplit.get(i)+ ",";
   	}
    p.apply("ReadLines", TextIO.read().from(options.getInputFile()))
    .apply("diplotipa",ParDo.of(new DoFn<String, String>() {
                @ProcessElement
                public void processElement(ProcessContext c) {
                	List<String> ok=Arrays.asList(c.element().split(","));
                	String k="";
                	for(int i=0;i<ok.size();i++){
   						if(i>5 && i<okk){ continue;}
   						if(i==headsplit.size()-1) {k+=ok.get(i); break;}
   						k=k+ok.get(i)+ ",";
   					}
   					c.output(k);
                	
                }
            }
                ))
    .apply("ok",TextIO.write().withNumShards(1).withShardNameTemplate("").to(options.getOutput())
            .withHeader(head).withSuffix(".csv"));
    
System.out.println(head);

    

    p.run().waitUntilFinish();
  }

  public static void main(String[] args) {
     try{
     		FileWriter writer1= new FileWriter("Zillow.csv");
  		CSVPrinter csvPrint1 =new CSVPrinter(writer1,CSVFormat.EXCEL.withHeader("zipcode,City,State,Metro,CountyName,SizeRank,Date,Price,Bedrooms"));
  		newTable("C:/Users/Jero/word-count-beam/1rooms.csv","zipcode,City,State,Metro,CountyName,SizeRank,2016-01-01,2016-02-01,2016-03-01,2016-04-01,2016-05-01,2016-06-01,2016-07-01,2016-08-01,2016-09-01,2016-10-01,2016-11-01,2016-12-01,2017-01-01,2017-02-01,2017-03-01,2017-04-01,2017-05-01,2017-06-01,2017-07-01,2017-08-01,2017-09-01,2017-10-01,2017-11-01,2017-12-01,2018-01-01",csvPrint1);
 		newTable("C:/Users/Jero/word-count-beam/2rooms.csv","zipcode,City,State,Metro,CountyName,SizeRank,2016-01-01,2016-02-01,2016-03-01,2016-04-01,2016-05-01,2016-06-01,2016-07-01,2016-08-01,2016-09-01,2016-10-01,2016-11-01,2016-12-01,2017-01-01,2017-02-01,2017-03-01,2017-04-01,2017-05-01,2017-06-01,2017-07-01,2017-08-01,2017-09-01,2017-10-01,2017-11-01,2017-12-01,2018-01-01",csvPrint1);
        newTable("C:/Users/Jero/word-count-beam/3rooms.csv","zipcode,City,State,Metro,CountyName,SizeRank,2016-01-01,2016-02-01,2016-03-01,2016-04-01,2016-05-01,2016-06-01,2016-07-01,2016-08-01,2016-09-01,2016-10-01,2016-11-01,2016-12-01,2017-01-01,2017-02-01,2017-03-01,2017-04-01,2017-05-01,2017-06-01,2017-07-01,2017-08-01,2017-09-01,2017-10-01,2017-11-01,2017-12-01,2018-01-01",csvPrint1);         
        newTable("C:/Users/Jero/word-count-beam/4rooms.csv","zipcode,City,State,Metro,CountyName,SizeRank,2016-01-01,2016-02-01,2016-03-01,2016-04-01,2016-05-01,2016-06-01,2016-07-01,2016-08-01,2016-09-01,2016-10-01,2016-11-01,2016-12-01,2017-01-01,2017-02-01,2017-03-01,2017-04-01,2017-05-01,2017-06-01,2017-07-01,2017-08-01,2017-09-01,2017-10-01,2017-11-01,2017-12-01,2018-01-01",csvPrint1);
        newTable("C:/Users/Jero/word-count-beam/5rooms.csv","zipcode,City,State,Metro,CountyName,SizeRank,2016-01-01,2016-02-01,2016-03-01,2016-04-01,2016-05-01,2016-06-01,2016-07-01,2016-08-01,2016-09-01,2016-10-01,2016-11-01,2016-12-01,2017-01-01,2017-02-01,2017-03-01,2017-04-01,2017-05-01,2017-06-01,2017-07-01,2017-08-01,2017-09-01,2017-10-01,2017-11-01,2017-12-01,2018-01-01",csvPrint1);
        writer1.flush();  
		writer1.close();
           	/*FileWriter writer= new FileWriter("new.csv");
  			CSVPrinter csvP =new CSVPrinter(writer,CSVFormat.DEFAULT.withHeader("id,listing_url,scrape_id,last_scraped,name,summary,space,description,experiences_offered,neighborhood_overview,notes,transit,access,interaction,house_rules,thumbnail_url,medium_url,picture_url,xl_picture_url,host_id,street,neighborhood,neighbourhood_cleansed,city,state,zipcode,market,smart_location,country_code,country,latitude,longitude,is_location_exact,property_type,room_type,accommodates,bathrooms,bedrooms,beds,bed_type,square_feet,price,weekly_price,monthly_price,security_deposit,cleaning_fee,guests_included,extra_people,minimum_nights,maximum_nights,has_availability,availability_365,number_of_reviews,first_review,last_review,review_scores_rating,review_scores_accuracy,review_scores_cleanliness,review_scores_checkin,review_scores_communication,review_scores_location,review_scores_value,requires_license,license,jurisdiction_names,instant_bookable,cancellation_policy,require_guest_profile_picture,require_guest_phone_verification,calculated_host_listings_count,reviews_per_month"));
  			FileWriter writer1= new FileWriter("new2.csv");
  			CSVPrinter csvPrint1 =new CSVPrinter(writer1,CSVFormat.EXCEL.withHeader("listing_id,amenity_id"));
     		FileWriter writer2= new FileWriter("new3.csv");
  			CSVPrinter csvPrint2 =new CSVPrinter(writer2,CSVFormat.EXCEL.withHeader("listing_id,amenity_id")); */
     		//FileWriter writer3= new FileWriter("new4.csv");
  			//CSVPrinter csvPrint3 =new CSVPrinter(writer3,CSVFormat.EXCEL.withHeader("id,name,host_id,host_name,zip_code,latitude,longitude,room_type,price,minimum_nights,number_of_reviews,last_review,reviews_per_month,calculated_host_listings_count,availability_365")); 
     	//updateSumList("C:/Users/Jero/Desktop/Project 1/boston_normalizedCSVs/summary_listing.csv","C:/Users/Jero/Desktop/Project 1/boston_normalizedCSVs/neighborhood.csv",csvPrint3);
     	//checkDuplicates("C:/Users/tinoa/word-count-beam/listing/boston_listing.csv",csvP);
     	//checkDuplicates("C:/Users/tinoa/word-count-beam/listing/port_listing.csv",csvP);
     	//checkDuplicates("C:/Users/Jero/Desktop/Project 1/portland_normalizedCSVs/calendar_summary.csv",csvPrint3);
     	//scanAmenities("C:/Users/tinoa/Desktop/amenity.csv");
     	//checkAmenities("C:/Users/tinoa/Desktop/data/boston_normalizedCSVs/amenity.csv","C:/Users/tinoa/Desktop/data/boston_normalizedCSVs/listing2amenity.csv",csvPrint3);
     	//checkAmenities("C:/Users/tinoa/Desktop/data/portland_normalizedCSVs/amenity.csv","C:/Users/tinoa/Desktop/data/portland_normalizedCSVs/listing2amenity.csv",csvPrint3);
     	//checkAmenities("C:/Users/tinoa/Desktop/data/denver_normalizedCSVs/amenity.csv","C:/Users/tinoa/Desktop/data/denver_normalizedCSVs/listing2amenity.csv",csvPrint3);
     	//deleteColumn("C:/Users/tinoa/Desktop/data/boston_normalizedCSVs/listing.csv","id,listing_url,scrape_id,last_scraped,name,summary,space,description,experiences_offered,neighborhood_overview,notes,transit,access,interaction,house_rules,thumbnail_url,medium_url,picture_url,xl_picture_url,host_id,street,neighborhood,neighbourhood_cleansed,city,state,zipcode,market,smart_location,country_code,country,latitude,longitude,is_location_exact,property_type,room_type,accommodates,bathrooms,bedrooms,beds,bed_type,square_feet,price,weekly_price,monthly_price,security_deposit,cleaning_fee,guests_included,extra_people,minimum_nights,maximum_nights,has_availability,availability_365,number_of_reviews,first_review,last_review,review_scores_rating,review_scores_accuracy,review_scores_cleanliness,review_scores_checkin,review_scores_communication,review_scores_location,review_scores_value,requires_license,license,jurisdiction_names,instant_bookable,cancellation_policy,require_guest_profile_picture,require_guest_phone_verification,calculated_host_listings_count,reviews_per_month","calculated_host_listings_count");
     	//writer3.flush();  
		//writer3.close(); 
     	/*writer1.flush();  
		writer1.close();
		writer2.flush();  
		writer2.close(); 
		writer3.flush();  
		writer3.close(); 
		for(int i=0;i<changes.size();i++){
     		for(int j=0;j<changes.get(i).size();j++){
     			System.out.println(changes.get(i).get(j)+ "     ok");
     		}
     		System.out.println();
     	} */
     	/*for(int i=0;i<amenityPairs.size();i++){
     		for(int j=0;j<amenityPairs.get(i).size();j++){
     			System.out.println(amenityPairs.get(i).get(j));
     		}
     		System.out.println();
     	} */
     	//checkDuplicatesWithBase("C:/Users/tinoa/word-count-beam/host/austin_host.csv");
     	//Writer1(records);
     	

     	/*FileWriter writer1= new FileWriter("newHost.csv");
  		CSVPrinter csvPrint1 =new CSVPrinter(writer1,CSVFormat.EXCEL.withHeader("id,url,name,since,about,response_time,response_rate,acceptance_rate,is_superhost,thumbnail_url,picture_url,neighbourhood,listings_count,total_listings_count,verifications,has_profile_pic,identity_verified,calculated_host_listings_count,city,state,country"));
     	fillHost("C:/Users/tinoa/word-count-beam/host/austin_host.csv");
     	validateHost("C:/Users/tinoa/Desktop/data/boston_normalizedCSVs/host.csv",csvPrint1);
     	validateHost("C:/Users/tinoa/Desktop/data/portland_normalizedCSVs/host.csv",csvPrint1);
     	validateHost("C:/Users/tinoa/Desktop/data/denver_normalizedCSVs/host.csv",csvPrint1); */
     	
     	////////////////////////////////////////////////////////////////////////////////////
     	/*WordCountOptions options =
       	PipelineOptionsFactory.fromArgs(args).withValidation().as(WordCountOptions.class);
		runWordCount(options); */

     	}catch(Exception e){System.out.println(e);}
  }
}