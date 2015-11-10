# simple-hdfs-du2

Simple disk usage utility for HDFS. Shows disk usage of directories in console and on a Pie Chart graph.Tested on Hadoop 2.2.X

### Dependencies

"/etc/hadoop/conf/core-site.xml"
"/etc/hadoop/conf/hdfs-site.xml"

Use App in Hadoop Client node or simply copy those configuration files from a node into a machine which you would
like to run app.

 - Xwindow
 - JFree Charts 1.0.13
 - Jcommons 1.0.16
 
###  Compile
mvn package

### Usage
java -jar  target/hdfsdu-0.0.1-SNAPSHOT-jar-with-dependencies.jar \<hdfs username\> \<hdfs directory\>

ex:
java -jar  target/hdfsdu-0.0.1-SNAPSHOT-jar-with-dependencies.jar hdfs /tmp

![] (https://raw.githubusercontent.com/lserinol/simple-hdfs-du/master/sample/Sample.png)

Note: tested on Hadoop 2.X
