#/bin/bash
# This srcipt must be used to launch equinox-loader application on stand alone service with yours rights
# Don't modify it be copy them if the configuration must be modify.
# Probably you must launch this script with the following cmd-line=
# sudo -u equinox-loader /opt/equinox-loader/equinox-loader.sh


# EQUINOX-LOADER VERSION= ${project.artifactId}-${project.version}
# set java home variable if not define in environnemt 
# JAVA_HOME=

buildHelp()
{
	echo "aide:
		--user permet de specifier un autre utilisateur
		--debug active le debug osgi
		--console active la console
		--jmx active le connecteur jmx
    --xm permet de modifier les parametres java
	"
}


#$1 user
#$2 cmd
launch()
{
  if [ -n "$JAVA_HOME" ]
  then
    echo "LOG: sudo -u $1 $JAVA_HOME/bin/$2"
    bash -c "sudo -u $1  $JAVA_HOME/bin/$2"
  else
    echo "JAVA_HOME not set, please intall java jre, trying to use java cmd"
    echo "LOG: sudo -u $1 $2"
    bash -c "sudo -u $1 $2"
  fi
}

#$1 user
cleanConfDir()
{
	bash -c "sudo -u $1 rm -fr $CONFIG_DIR/org.eclipse.osgi"
	echo "LOG: sudo -u $1 rm -r $CONFIG_DIR/org.eclipse.osgi"
}




INSTALL_DIR="/opt/equinox-loader"
USER_PARAM="equinox-loader"
CONFIG_DIR="$INSTALL_DIR/bundles/configuration"
OPT_ARGS=""
JMX_PORT=9010
JMX=""
XM="-Xmx512m -XX:MaxPermSize=64m"

while [ -n "$1" ]
do
  case $1 in
    --user)    USER_PARAM="$2";shift;;
    --debug)   OPT_ARGS="$OPT_ARGS -debug";shift;;
	  --console) OPT_ARGS="$OPT_ARGS -console";shift;;
    --jmx)     JMX="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -DJEMonitor=true";shift;;
    --xm)	     XM="$2";shift;;
    -h)        HELP="true";buildHelp;;
    --help)    HELP="true";buildHelp;;
    *)        ;;
  esac
  shift
done


HERE=$(pwd)
cd $INSTALL_DIR

#on regarde sous quel utilisateur on va lance equinox-loader si non specifier on le lance sous equinox-loader
if [ "$USER_PARAM" != "equinox-loader" ];
then
  CONFIG_DIR="$HOME/equinox-loader/bundles/configuration"
fi


### Options -os ${target.os} -ws ${target.ws} -arch ${target.arch} -nl ${target.nl} -consoleLog
# -os win32 -ws win32 -arch x86_64 -nl fr_FR -consoleLog

EQUINOX_OPTS="-configuration $CONFIG_DIR $OPT_ARGS"
JAVA_OPTS="$XM $JMX"

#Commande line
CMD="java $JAVA_OPTS -jar ${project.artifactId}-${project.version}-assembly.jar $EQUINOX_OPTS"


launch "$USER_PARAM" "$CMD"
cleanConfDir "$USER_PARAM"


cd $HERE





















