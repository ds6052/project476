javac -classpath `yarn classpath` -d . ValMapper.java

javac -classpath `yarn classpath` -d . ValReducer.java

javac -classpath `yarn classpath`:. -d . Val.java

jar -cvf val.jar *.class

hadoop jar val.jar Val project476/ecdc_cases_clean.csv project476/out_val

hadoop fs -cat project476/out_val/part-r-00000 > ecdc_cases_val.csv

hdfs dfs -put ecdc_cases_val.csv project476