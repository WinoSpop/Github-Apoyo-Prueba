#!/bin/sh
rm -rf out
javac -encoding UTF-8 -d out -sourcepath src src/pruebas/PruebasBase.java || exit 1
java -Dstdout.encoding=UTF-8 -cp out pruebas.PruebasBase
