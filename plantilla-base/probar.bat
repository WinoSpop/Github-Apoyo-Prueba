@echo off
chcp 65001 >nul
if exist out rmdir /s /q out
javac -encoding UTF-8 -d out -sourcepath src src\pruebas\PruebasBase.java
if errorlevel 1 exit /b 1
java -Dstdout.encoding=UTF-8 -cp out pruebas.PruebasBase
