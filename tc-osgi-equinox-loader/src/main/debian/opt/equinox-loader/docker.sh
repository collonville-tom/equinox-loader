#!/bin/sh

cd /opt/equinox-loader
java -Xmn128m -Xss128m -Xmx512m -XX:MaxMetaspaceSize=64m -verbose:gc \
     -Dcom.sun.management.jmxremote \
     -Djava.rmi.server.hostname=$DOCKER_HOST_IP \
     -Dcom.sun.management.jmxremote.port=${jmx.port} \
     -Dcom.sun.management.jmxremote.rmi.port=${jmx.port} \
     -Dcom.sun.management.jmxremote.local.only=false \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false \
     -DJEMonitor=true \
     -jar ${project.artifactId}-${project.version}-assembly.jar \
     -configuration /var/equinox-loader/bundles/configuration -debug -console
     
# pour utiliser l'authentification par mot de passe il faut initialiser le fichier password du repertoire management du jre