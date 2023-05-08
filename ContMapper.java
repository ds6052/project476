import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ContMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        /* 
        continent [0]
        ISO3 [1]
        countries_and_territories [2]
        Number of observations [3]
        Number of distinct values [4]
        Cases sum [5]
        Cases mean [6]
        Cases median [7]
        Cases mode [8]
        Cases standard deviation [9]
        Deaths sum [10]
        Deaths mean [11]
        Deaths median [12]
        Deaths mode [13]
        Deaths standard deviation [14]
        */
        // Separate commas if that comma is not followed by space
        String[] line = value.toString().split("(,(?! ))"); 

        // keep continent, ISO3 code, countries&territories, cases sum & std [5&9], deaths sum & std [10&14]
        // discard others
        // Analyzing on continent bases
        // consider continent as keys
        // others as value
        if(line != null){
            context.write(new Text(line[0]), new Text(line[1] + "," + line[2] + "," 
                + line[5] + "," + line[9] + "," + line[10] + "," + line[14]));
        }
    }
}


