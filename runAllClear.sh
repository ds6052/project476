rm *.class

rm *.jar

rm ecdc_cases_clean.csv

rm ecdc_cases_val.csv

rm ppl2021_Jan.csv

hdfs dfs -rm -r project476/out_clean

hdfs dfs -rm project476/ecdc_cases_clean.csv

hdfs dfs -rm -r project476/out_val

hdfs dfs -rm project476/ecdc_cases_val.csv

hdfs dfs -rm -r project476/out_cont

hdfs dfs -rm -r project476/out_ppl2021

hdfs dfs -rm project476/ppl2021_Jan.csv