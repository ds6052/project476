javac -classpath `yarn classpath` -d . CleanMapper.java

javac -classpath `yarn classpath`:. -d . Clean.java

jar -cvf clean.jar *.class
hadoop jar clean.jar Clean project476/ecdc_cases.csv project476/out_clean

hadoop fs -cat project476/out_clean/part-r-00000 > ecdc_cases_clean.csv

hdfs dfs -put ecdc_cases_clean.csv project476



javac -classpath `yarn classpath` -d . ValMapper.java

javac -classpath `yarn classpath` -d . ValReducer.java

javac -classpath `yarn classpath`:. -d . Val.java

jar -cvf val.jar *.class

hadoop jar val.jar Val project476/ecdc_cases_clean.csv project476/out_val

hadoop fs -cat project476/out_val/part-r-00000 > ecdc_cases_val.csv

hdfs dfs -put ecdc_cases_val.csv project476



javac -classpath `yarn classpath` -d . ContMapper.java

javac -classpath `yarn classpath` -d . ContReducer.java

javac -classpath `yarn classpath`:. -d . Cont.java

jar -cvf cont.jar *.class

hadoop jar cont.jar Cont project476/ecdc_cases_val.csv project476/out_cont



javac -classpath `yarn classpath` -d . Ppl2021Mapper.java

javac -classpath `yarn classpath`:. -d . Ppl2021.java

jar -cvf ppl2021.jar *.class
hadoop jar ppl2021.jar Ppl2021 project476/WPP2022_Demographic_Indicators_Medium.csv project476/out_ppl2021

hadoop fs -cat project476/out_ppl2021/part-r-00000 > ppl2021_Jan.csv

hdfs dfs -put ppl2021_Jan.csv project476
