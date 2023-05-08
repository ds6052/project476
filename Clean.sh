javac -classpath `yarn classpath` -d . CleanMapper.java

javac -classpath `yarn classpath`:. -d . Clean.java

jar -cvf clean.jar *.class
hadoop jar clean.jar Clean project476/ecdc_cases.csv project476/out_clean

hadoop fs -cat project476/out_clean/part-r-00000 > ecdc_cases_clean.csv

hdfs dfs -put ecdc_cases_clean.csv project476