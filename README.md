# project476


Sources:
1. ecdc_cases.csv
Covid cases and deaths records of Jan 2020 - Dec 2020
https://learn.microsoft.com/en-us/azure/open-datasets/dataset-ecdc-covid-cases?tabs=azure-storage

2. WPP2022_Demographic_Indicators_Medium.csv (Complementary)
World Population Projections 2022 (For demographic population of Jan 2021)
https://population.un.org/wpp/Download/Standard/CSV/

------------------------------------------------------------------------------

MR Code running order:
1. Clean
2. Val
3. Cont
X. Ppl2021

Code Output Directory (dataproc):
- dirToShareAccessProject
	- join_val_ppl
		- 000000_0 table derived from hive commands
	- project476
		- WPP2022_Demographic_Indicators_Medium.csv
		- ecdc_cases.csv
		- out_clean //output of MR clean
		- ecdc_cases_clean.csv // convert clean output to csv
		- out_val //output of MR val
		- ecdc_cases_val.csv // convert val output to csv
		- out_cont //output of MR continent
		- out_ppl2021 // output of MR ppl2021
		- ppl2021_Jan.csv // convert ppl2021 output to csv

------------------------------------------------------------------------------

etl_code (Cleaning with some Profiling and Formatting included):

1. Clean.java, CleanMapper.java
	- Keep columns continent, ISO3 country code, countries_and_territories, date, cases, deaths.
	- Add binary columns bin_cases, bin_deaths with 1 indicating there are cases/deaths
	- Format date
	- replace "_" in countries_and_territories with " "
	Output file: ecdc_cases_clean.csv converted from part-r-00000 in out_clean

2. Ppl2021.java, Ppl2021Mapper.java (Complementary)
	- Select rows that represents country/area and time 2021
	- Keep columns ISO3 country code, countries_and_territories, population on Jan 2021
	Output file: ppl2021_Jan.csv converted from part-r-00000 in out_ppl2021

------------------------------------------------------------------------------

ana_code:

1. Val.java, ValMapper.java, ValReducer.java
	- Take continent, ISO3, countries_and_territories as keys (so grouped by country)
	- Take cases, deaths as values
	I. Count number of observations
	II. Count distinct case/death number of each day (not applicable in analyzing)
	III. Sum up all case/death through out 2020
	IV. Calculate mean case/death of each day in 2020
	V. Calculate median case/death number of 2020 (not applicable in analyzing)
	VI. Calculate mode case/death number of 2020 (not applicable in analyzing)
	VII. Calculate standard deviation of case/death number of 2020
	Output file: ecdc_cases_val.csv converted from part-r-00000 in out_val
	Column name of output:
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

2. Cont.java, ContMapper.java, ContReducer.java
	- Take continent as keys
	- Take ISO3 country code, countries&territories, sum and std of cases and deaths as values
	1. Find minimum and maximum sum cases
	2. Find minimum and maximum std cases
	Output: part-r-00000 in out_cont
	What output represent is written explicitly.

3. join_val_ppl.txt
	- Hive code that is based ppl2021_Jan.csv and ecdc_cases_val.csv to tables
	- Need to be separately run in hive

	Specific steps:
	1. Create and load external table ppl2021 from ppl2021_Jan.csv
	2. Create and load external table val from ecdc_cases_val.csv
	3. Inner join two table on iso3 country code
	4. Calculate case-to-population and death-to-population ratio and add as columns
	5. Sort rows in descending order of case-to-population ratio
	Column names:
		1. iso3
		2. country_area
		3. population ppl
		4. sum cases
		5. case-to-population ratio
		6. sum deaths
		7. death-to-population ratio
	Output file is written as 000000_0 to directory join_val_ppl

------------------------------------------------------------------------------

data_ingest
1. Clean.sh & CleanClear.sh (Shell script to execute/reset MR Clean)
2. Val.sh & ValClear.sh (Shell script to execute/reset MR Val)
3. Cont.sh & ContClear.sh (Shell script to execute/reset MR Cont)
4. Ppl2021.sh & Ppl2021Clear.sh (Shell script to execute/reset MR Ppl2021)
5. runAll.sh & runAllClear.sh (Shell script to run all/reset all MR jobs)
6. join_val_ppl.txt (Hive commands used to generate 000000_0 as result)

------------------------------------------------------------------------------

profiling_code

**Profiling is integrated in these files

1. Clean.java, CleanMapper.java
	- Keep columns continent, ISO3 country code, countries_and_territories, date, cases, deaths.
	- Add binary columns bin_cases, bin_deaths with 1 indicating there are cases/deaths
	- Format date
	- replace "_" in countries_and_territories with " "
	Output file: ecdc_cases_clean.csv converted from part-r-00000 in out_clean

2. Cont.java, ContMapper.java, ContReducer.java
	- Take continent as keys
	- Take ISO3 country code, countries&territories, sum and std of cases and deaths as values
	1. Find minimum and maximum sum cases
	2. Find minimum and maximum std cases
	Output: part-r-00000 in out_cont
	What output represent is written explicitly.

------------------------------------------------------------------------------

Screenshots
1. Cleaning & Profiling: Clean_1 and Clean2 showing MR clean works
2. Analyzing 1: Val_1 and Val_2 showing MR Val works
3. Analyzing 2: Cont_1 and Cont_2 showing MR Cont works
4. Analyzing 3: Hive_combine showing hive codes works and successfully generate table with calculated results
** I didn't include screenshot of Ppl2021 b/c it is a complementary dataset brought in to help analysis, since Analyzing 3 is successful, MR Ppl2021 works
