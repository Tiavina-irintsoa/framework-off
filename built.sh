project_name="test-framework"
project_directory="/home/tiavina/S4/MrNaina/gitframework/test-framework";
temp_directory="/home/tiavina/S4/MrNaina/temp";
server_directory="/home/tiavina/apache-tomcat-10.0.27/webapps/";
build="/home/tiavina/S4/MrNaina/gitframework/framework/build";
fw_src="/home/tiavina/S4/MrNaina/gitframework/framework/src/*.java";
jar_path="/home/tiavina/S4/MrNaina/gitframework/framework.jar";
lib_path="/home/tiavina/S4/MrNaina/gitframework/test-framework/WEB-INF/lib/framework.jar";

#compilation des classes du framework

javac -d $build $fw_src

#creation du fichier .jar
cd $build
jar -cf $jar_path etu1840


# copie du fichier .jar dans le repertoire lib du test framework
cp $jar_path $lib_path
cd $project_directory

#creation du repertoire tempoaire
mkdir $temp_directory

#copie du projet dans un repertoire temporaire
cp -R $project_directory $temp_directory

#compilation des classes dans le dossier temporaire
javac  -d  $temp_directory/$project_name/WEB-INF/classes -parameters  $temp_directory/$project_name/src/*.java 

#creation du fichier .war
cd $temp_directory/$project_name
jar -cf $temp_directory/$project_name.war .

#copie du fichier .war dans tomcat
cp $temp_directory/$project_name.war $server_directory/$project_name.war

#suppression du repertoire temporaire
rm -R $temp_directory