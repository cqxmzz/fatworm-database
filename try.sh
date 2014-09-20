#!/bin/bash
#arno sort so slow
#ATIS mysql slow
#atisn mysql slow
#indexTest ok
#opt 
java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/basic1 -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/basic1 -o result.txt

diff ljc_result.txt result.txt > basic/basic1.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/basic2 -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/basic2 -o result.txt

diff ljc_result.txt result.txt > basic/basic2.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/infraTest -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/infraTest -o result.txt

diff ljc_result.txt result.txt > basic/infraTest.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/insert -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/insert -o result.txt

diff ljc_result.txt result.txt > basic/insert.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/naive -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/naive -o result.txt

diff ljc_result.txt result.txt > basic/naive.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/samples -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/samples -o result.txt

diff ljc_result.txt result.txt > basic/samples.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/simple -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/simple -o result.txt

diff ljc_result.txt result.txt > basic/simple.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/tstest -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/tstest -o result.txt

diff ljc_result.txt result.txt > basic/tstest.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/types -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/types -o result.txt

diff ljc_result.txt result.txt > basic/types.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/sample-imba -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/sample-imba -o result.txt

diff ljc_result.txt result.txt > basic/sample-imba.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/subqueries -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/basic/subqueries -o result.txt

diff ljc_result.txt result.txt > basic/subqueries.txt

java -jar ljc.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/advanced/indexTest -o ljc_result.txt

java -jar sis.jar -n /Users/chenqiming/Documents/workspace/FATWORM-SISTER/test/advanced/indexTest -o result.txt

diff ljc_result.txt result.txt > advanced/indexTest.txt
