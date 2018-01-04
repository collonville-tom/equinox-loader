#/bin/bash
# This srcipt must be used to launch equinox-loader application on stand alone service with yours rights
# Don't modify it be copy them if the configuration must be modify.
# Probably you must launch this script with the following cmd-line=
# sudo -u equinox-loader /opt/equinox-loader/equinox-loader.sh


# EQUINOX-LOADER VERSION= ${project.artifactId}-${project.version}
# set java home variable if not define in environnemt 
# JAVA_HOME=

USER_PARAM="$1"
JAVA_EXE="java"


INSTALL_DIR="/opt/equinox-loader"

if [ -z "$USER_PARAM" ];
then
  USER_PARAM="equinox-loader"
  CONFIG_DIR="$INSTALL_DIR/bundles/configuration"
else
  CONFIG_DIR="$HOME/equinox-loader/bundles/configuration"
fi


if [ "$OSTYPE" == "cygwin" ] 
then
  PRIVILEGE=""
  INSTALL_DIR="/opt/equinox-loader"
  CONFIG_DIR="$INSTALL_DIR/bundles/configuration"
  JAVA_EXE="$JAVA_EXE.exe"
else
  PRIVILEGE="sudo -u $USER_PARAM"	
  
fi 

### Options -os ${target.os} -ws ${target.ws} -arch ${target.arch} -nl ${target.nl} -consoleLog
# -os win32 -ws win32 -arch x86_64 -nl fr_FR -consoleLog
# 
EQUINOX_OPTS="-configuration $CONFIG_DIR -console -debug"
JAVA_OPTS="-Xmx512m -XX:MaxPermSize=64m -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -DJEMonitor=true"

#Commande line
CMD="$JAVA_EXE $JAVA_OPTS -jar ${project.artifactId}-${project.version}-assembly.jar $EQUINOX_OPTS"

# execution
pushd $INSTALL_DIR
if [ -n "$JAVA_HOME" ]
then
  echo "LOG: $PRIVILEGE $JAVA_HOME/bin/$CMD"
  bash -c "$PRIVILEGE  $JAVA_HOME/bin/$CMD"
else
  echo "JAVA_HOME not set, please intall java jre, trying to use java cmd"
  echo "LOG: $PRIVILEGE $CMD"
  bash -c "$PRIVILEGE $CMD"
fi


bash -c "$PRIVILEGE rm -fr $CONFIG_DIR/org.eclipse.osgi"
echo "LOG: $PRIVILEGE rm -r $CONFIG_DIR/org.eclipse.osgi"
popd
