import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ContReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        //Continent key
        //ISO3 code[0], countries&territories[1]
        //cases sum & std [2&3]
        //deaths sum & std [4&5]

        double neg1 = -1.0;
        List<Double> cases = new ArrayList<>();
        List<Double> deaths = new ArrayList<>();
        
        // Distinct cases and deaths pairs for each key
        String minSumCases = "";
        String maxSumCases = "";

        String minStdCases = "";
        String maxStdCases = "";

        String minSumDeaths = "";
        String maxSumDeaths = "";

        String minStdDeaths = "";
        String maxStdDeaths = "";

        double minSumC, minStdC, minSumD, minStdD;
        minSumC = minStdC = minSumD = minStdD = neg1;

        double maxSumC, maxStdC, maxSumD, maxStdD;
        maxSumC = maxStdC = maxSumD = maxStdD = neg1;


        for (Text value : values) {

            String val = value.toString();
            String[] v = val.split("(,(?! ))");

            double v2 = Double.parseDouble(v[2]);
            double v3 = Double.parseDouble(v[3]);
            double v4 = Double.parseDouble(v[4]);
            double v5 = Double.parseDouble(v[5]);

            cases.add(v2);
            deaths.add(v4);

            //sum cases [2]
            if(Double.compare(minSumC, neg1) == 0){
                minSumCases = val;
                minSumC = v2;
            }
            if(Double.compare(minSumC, v2) > 0){
                minSumCases = val;
                minSumC = v2;
            }

            if(Double.compare(maxSumC, neg1) == 0){
                maxSumCases = val;
                maxSumC = v2;
            }
            if(Double.compare(maxSumC, v2) < 0){
                maxSumCases = val;
                maxSumC = v2;
            }

            //std cases [3]
            if(Double.compare(minStdC, neg1) == 0){
                minStdCases = val;
                minStdC = v3;
            }
            if(Double.compare(minStdC, v3) > 0){
                minStdCases = val;
                minStdC = v3;
            }

            if(Double.compare(maxStdC, neg1) == 0){
                maxStdCases = val;
                maxStdC = v3;
            }
            if(Double.compare(maxStdC, v3) < 0){
                maxStdCases = val;
                maxStdC = v3;
            }

            //sum deaths [4]
            if(Double.compare(minSumD, neg1) == 0){
                minSumDeaths = val;
                minSumD = v4;
            }
            if(Double.compare(minSumD, v4) > 0){
                minSumDeaths = val;
                minSumD = v4;
            }

            if(Double.compare(maxSumD, neg1) == 0){
                maxSumDeaths = val;
                maxSumD = v4;
            }
            if(Double.compare(maxSumD, v4) < 0){
                maxSumDeaths = val;
                maxSumD = v4;
            }

            //std deaths [5]
            if(Double.compare(minStdD, neg1) == 0){
                minStdDeaths = val;
                minStdD = v5;
            }
            if(Double.compare(minStdD, v5) > 0){
                minStdDeaths = val;
                minStdD = v5;
            }

            if(Double.compare(maxStdD, neg1) == 0){
                maxStdDeaths = val;
                maxStdD = v5;
            }
            if(Double.compare(maxStdD, v5) < 0){
                maxStdDeaths = val;
                maxStdD = v5;
            }

        }

        //Continent key
        //ISO3 code[0], countries&territories[1]
        //cases sum & std [2&3]
        //deaths sum & std [4&5]

        context.write(key, new Text("Minimum sum cases: " + minSumCases));
        context.write(key, new Text("Maximum sum cases: " + maxSumCases));
        context.write(key, new Text("Minimum standard deviation cases: " + minStdCases));
        context.write(key, new Text("Maximum standard deviation cases: " + maxStdCases));
        context.write(key, new Text("Minimum sum deaths: " + minSumDeaths));
        context.write(key, new Text("Maximum sum deaths: " + maxSumDeaths));
        context.write(key, new Text("Minimum standard deviation deaths: " + minStdDeaths));
        context.write(key, new Text("Maximum standard deviation deaths: " + maxStdDeaths));

        context.write(key, new Text("Total cases: " + getSum(cases)));
        context.write(key, new Text("Total deaths: " + getSum(deaths)));
    }

    private double getSum(List<Double> values){
        double sum = 0;
        for(double value : values){
            sum += value;
        }
        return sum;
    }
}
