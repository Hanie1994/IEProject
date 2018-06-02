if [ -z "$CATALINA_HOME" ]; then
    echo "The environment variable CATALINA_HOME must be set to the root of the Tomcat installation directory"
    exit 1
fi  

rm -rf target/*
mkdir target
mkdir target/WEB-INF
mkdir target/WEB-INF/classes

cp -r web/* target/
#cp web/*/* target/*
javac -sourcepath src -classpath gson-2.6.2.jar:$CATALINA_HOME/lib/servlet-api.jar -d target/WEB-INF/classes src/*/*.java
#cp web/WEB-INF/web.xml target/WEB-INF
#cp web/WEB-INF/lib/*.jar target/WEB-INF/lib
#cp pages/* target/