javac -classpath `yarn classpath` -d . Ppl2021Mapper.java

javac -classpath `yarn classpath`:. -d . Ppl2021.java

jar -cvf ppl2021.jar *.class
hadoop jar ppl2021.jar Ppl2021 project476/WPP2022_Demographic_Indicators_Medium.csv project476/out_ppl2021

hadoop fs -cat project476/out_ppl2021/part-r-00000 > ppl2021_Jan.csv

hdfs dfs -put ppl2021_Jan.csv project476