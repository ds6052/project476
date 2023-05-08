import java.io.IOException;
import java.util.*;

import javax.lang.model.type.NullType;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Ppl2021Mapper extends Mapper<Object, Text, Text, Text>{

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        
        //Separate commas if that comma is not followed by space
        String[] line = value.toString().split("(,(?! ))"); 
 
        //reorder them in continent, ISO3 code, countries&territories, date, cases, deaths
        //ISO3 code [3], locType [7] == Country/Area, countries&territories [9], 
        // time [12] == 2021, population Jan [13] *1000
        //order: 3,9,13

        String iso3 = line[3];
        String locType = line[7];
        String countries_and_territories = line[9];
        String time = line[12];
        String pplJan2021 = line[13];

        //filter data
        if(locType.equals("Country/Area") && time.equals("2021") && isNumeric(pplJan2021)){

            double ppl = Double.parseDouble(pplJan2021) * 1000.0;

            context.write(new Text(iso3 + "," + countries_and_territories + "," + ppl), new Text());
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
