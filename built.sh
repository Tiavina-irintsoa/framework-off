#compilation des classes du framework
javac -d framework/build framework/src/*.java

#creation du fihicer .jar
cd framework/build
jar -cf ../../framework.jar etu1840
cd ../../

#copie du fichier .jar dans le repertoire lib du test framework
cp framework.jar test-framework/WEB-INF/lib
cd test-framework

#compilation des classes du test framework
javac  -d  WEB-INF/classes -parameters  src/*.java 

#creation du fichier .war
jar -cf ../test-framework.war .
cd ../

#copie du fichier .war dans tomcat
cp test-framework.war /home/tiavina/apache-tomcat-10.0.27/webapps/test-framework.war
