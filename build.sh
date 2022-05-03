@echo off

javac -d classes -classpath "lib\*" src\com\meowmivice\game\*.java src\com\meowmivice\*.java

jar --create --file meowmivice-1.1.jar -C classes .