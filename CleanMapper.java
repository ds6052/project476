import java.io.IOException;
import java.util.*;

import javax.lang.model.type.NullType;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMapper extends Mapper<Object, Text, Text, Text>{

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        
        //Separate commas if that comma is not followed by space
        String[] line = value.toString().split("(,(?! ))"); 
 
        //reorder them in continent, ISO3 code, countries&territories, date, cases, deaths
        //order: 10,8,6,0,4,5

        String continent = line[10];
        String iso3 = line[8];
        String countries_and_territories = line[6].replaceAll("_"," ");
        String date = line[0].replaceAll("-","/"); //format date
        String cases = line[4];
        String deaths = line[5];

        //filter data
        if(!continent.equals("Other") && isNumeric(line[4]) && isNumeric(line[5])){

            double cases_num = Double.parseDouble(cases);
            double deaths_num = Double.parseDouble(deaths);

            //binary columns
            int bin_cases = 0;
            int bin_deaths = 0;
            if(cases_num != 0) bin_cases = 1;
            if(deaths_num != 0) bin_deaths = 1;

            context.write(new Text(continent + "," + iso3 + "," + countries_and_territories + ","
                + date + ","  + cases + "," + deaths + "," + bin_cases + "," + bin_deaths), new Text());
        }
    }

    private boolean isNumeric(String str) { 
        try{
            Double.parseDouble(str);  
            return true;
        }catch(NumberFormatException e){  
            return false;
        }
    }
}
