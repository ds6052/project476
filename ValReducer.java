import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ValReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        List<String> distinct = new ArrayList<>();
        List<Double> cases = new ArrayList<>();
        List<Double> deaths = new ArrayList<>();

        // Distinct cases and deaths pairs for each key
        for (Text value : values) {
            
            // Store Distinct Value
            String distinctVal = value.toString();
            if (!distinct.contains(distinctVal)) {
                distinct.add(distinctVal);
            }

            // Store cases and deaths separately
            String[] c_d = value.toString().split(",");
            cases.add(Double.parseDouble(c_d[0]));
            deaths.add(Double.parseDouble(c_d[1]));
        }

        Collections.sort(cases);
        Collections.sort(deaths);

        double casesSum = getSum(cases);
        double deathsSum = getSum(deaths);
        
        double casesMean = getMean(cases);
        double deathsMean = getMean(deaths);

        double casesMedian = getMedian(cases);
        double deathsMedian = getMedian(deaths);

        double casesMode = getMode(cases);
        double deathsMode = getMode(deaths);

        double casesStd = getStd(cases);
        double deathsStd = getStd(deaths);

        // in order of size, distinct values, 
        // cases sum, cases mean, cases median, cases mode, cases std, 
        // deaths sum, deaths mean, deaths median, deaths mode, deaths std
        context.write(new Text(key + "," + cases.size() + "," + distinct.size() + "," +
            casesSum + "," + casesMean + "," + casesMedian + "," + casesMode + "," + casesStd + "," +
            deathsSum + "," + deathsMean + "," + deathsMedian + "," + deathsMode + "," + deathsStd), new Text());
    }

    private double getSum(List<Double> values){
        double sum = 0;
        for(double value : values){
            sum += value;
        }
        return sum;
    }

    private double getMean(List<Double> values){
        return Math.round(getSum(values)/values.size() * 100.0)/100.0;
    }

    private double getMedian(List<Double> values) {
        int size = values.size();
        if (size % 2 != 0) {
            return values.get(size / 2);
        } else {
            return (values.get((size / 2) - 1) + values.get(size / 2)) / 2;
        }
    }

    private double getMode(List<Double> values) {
        int size = values.size();
        double maxValue = 0;
        int maxCount = 0;

        for(int i = 0; i < size; ++i){
            int count = 0;
            for(int j = 0; j < size; ++j){
                if(values.get(j) == values.get(i)){
                    ++count;
                }
            }

            if(count > maxCount){
                maxCount = count;
                maxValue = values.get(i);
            }
        }

        return maxValue;
    }

    private double getStd(List<Double> values){
        int size = values.size();
        double mean = getMean(values);
        double squareSum = 0;

        for (int i = 0; i < size; i++){
            squareSum += Math.pow(values.get(i) - mean, 2);
        }
        double std = Math.sqrt((squareSum) / (size - 1));

        return Math.round(std * 100.0)/100.0;
    }
}
