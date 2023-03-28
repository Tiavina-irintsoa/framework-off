javac -d framework/build framework/src/*.java
cd framework/build
jar -cf ../../framework.jar etu1840
cd ../../
cp framework.jar test-framework/WEB-INF/lib
cd test-framework
jar -cvf ../test-framework.war .
cd ../
cp test-framework.war /home/tiavina/apache-tomcat-10.0.27/webapps/test-framework.war
