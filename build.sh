mvn clean package -Dmaven.test.skip=true
cd target/deploy/
mkdir coolpeng
cd coolpeng
jar -xvf ../coolpeng.war
