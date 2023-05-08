import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ValMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // continent, ISO3 code, countries&territories, date, cases, deaths, cases_bin, deaths_bin
        // Separate commas if that comma is not followed by space
        String[] line = value.toString().split("(,(?! ))"); 

        // keep continent, ISO3 code, countries&territories, cases, deaths
        // discard date, cases_bin, deaths_bin
        // Analyzing on country&territories bases
        // consider continent, ISO3 code, countries&territories as keys
        // cases and deaths as values
        if(line != null && isNumeric(line[4]) && isNumeric(line[5])){
            context.write(new Text(line[0] + "," + line[1] + "," + line[2]), new Text(line[4] + "," + line[5]));
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


