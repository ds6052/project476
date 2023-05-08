javac -classpath `yarn classpath` -d . ContMapper.java

javac -classpath `yarn classpath` -d . ContReducer.java

javac -classpath `yarn classpath`:. -d . Cont.java

jar -cvf cont.jar *.class

hadoop jar cont.jar Cont project476/ecdc_cases_val.csv project476/out_cont

hdfs dfs -head project476/out_cont/part-r-00000