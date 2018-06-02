1-
	./build.sh
2-
	./deploy.sh

3-
    http://localhost:8080/ca8/api/load
4-
    http://localhost:8080/ca8/Main.html

*
	$CATALINA_HOME/bin/startup.sh 			   ---> turn tomcat on
**
	$CATALINA_HOME/bin/shutdown.sh             ---> turn tomcat off
***
	tail -f $CATALINA_HOME/logs/catalina.out   ---> see logs

+
	sudo apt-get autoremove tomcat7
++
	http://www.liquidweb.com/kb/how-to-install-apache-tomcat-8-on-ubuntu-14-04/

	tomcat 8 with jdk 7 (1.7)


Database::
2) Start HSQL Server
	cd db
	java -cp ../hsqldb.jar org.hsqldb.server.Server
3) Start HSQL Database Manager
	java -cp hsqldb.jar org.hsqldb.util.DatabaseManagerSwing