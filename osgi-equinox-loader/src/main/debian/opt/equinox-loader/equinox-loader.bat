echo off
rem This srcipt must be used to launch equinox-loader application on stand alone service with yours rights
rem Don't modify it be copy them if the configuration must be modify.
rem EQUINOX-LOADER VERSION=${project.artifactId}-${project.version}

rem set java home variable if not define in environnemt 
rem set JAVA_HOME=


set USER_PARAM=${project.artifactId}
set INSTALL_DIR=F:\opt\equinox-loader
set CONFIG_DIR=%INSTALL_DIR%\bundles\configuration

rem Options 
set EQUINOX_OPTS=-configuration %CONFIG_DIR% -console -debug
set JAVA_OPTS=-Xmx512m -XX:MaxPermSize=64m -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=${jmx.port} -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false

rem Commande line
set CMD=%JAVA_OPTS% -jar osgi-${project.artifactId}-${project.version}-assembly.jar %EQUINOX_OPTS%

rem Execution 
cd %INSTALL_DIR%
if "%JAVA_HOME%" NEQ "" (goto thencase) else (goto elsecase)

:thencase
echo LOG: %JAVA_HOME%\bin\%CMD%
"%JAVA_HOME%\bin\java" %CMD%
goto fin
  
:elsecase
echo JAVA_HOME not set, please intall java jre, trying to use java cmd
echo LOG: %CMD%
java %CMD%

:fin
rmdir %CONFIG_DIR%\org.eclipse.osgi
echo LOG: rmdir %CONFIG_DIR%\org.eclipse.osgi
cd %WHERE_AM_I%