@echo off
echo Starting Overtone REPL. Wait up!
echo Temporarily changing USERPROFILE from '%USERPROFILE%' to '%cd%'
set USERPROFILE=%cd%
echo %USERPROFILE%

set JAVA_CMD=%USERPROFILE%\jvm-windows\bin\java.exe
start editors\npp.6.4.5.bin.minimalist\notepad++.exe -llisp src\coderdojonyc\core.clj
lein.bat repl
