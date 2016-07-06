@echo off & setlocal enabledelayedexpansion

set LIB_JARS=""
cd ..\lib
for %%i in (*) do set LIB_JARS=!LIB_JARS!;..\lib\%%i
cd ..\bin


@if "%1" NEQ "" @set lin=%1
goto start

:input
@set /p in=input file name:
@set lin=%in&
@if "%lin&"=="" goto input

:start
java -Xms64m -Xmx1024m -XX:MaxPermSize=64M -classpath ..\conf;%LIB_JARS% com.horizon.component.container.Main %lin%
goto end


:end
pause