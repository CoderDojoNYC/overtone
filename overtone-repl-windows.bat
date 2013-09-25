@echo off
echo Starting Overtone REPL. Wait up!
set LEIN_JAR=leiningen-2.3.2-standalone.jar
set JAVA_CMD=jvm-windows/bin/java.exe
IF not exist "%USERPROFILE%\.m2" xcopy /e /y m2 "%USERPROFILE%\.m2"
lein.bat repl
