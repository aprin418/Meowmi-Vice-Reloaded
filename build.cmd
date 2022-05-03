@echo off

javac -d classes -classpath "lib\*" src\com\meowmivice\game\*.java

jar --create --file meowmivice-1.1.jar -C classes .


