#!/bin/bash



which java | grep –q /usr/bin/java



if [ $? = 0 ]



then echo “Instalado”

	cd Cliente_JAVA/Projeto-PNEU/target

	java -jar Projeto-PNEU-1.0-SNAPSHOT-jar-with-dependencies.jar



else echo “Não instalado”

        sudo apt install default-jdk

	cd Cliente_JAVA/Projeto-PNEU/target

	java -jar Projeto-PNEU-1.0-SNAPSHOT-jar-with-dependencies.jar

fi
