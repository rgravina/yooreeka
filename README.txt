------------------------------------------------------------------------------
Quick start for running book examples
------------------------------------------------------------------------------

----------------
Linux, Mac OS X
----------------

1. Ensure java and ant are installed.

> which java; which ant
/usr/bin/java
/usr/bin/ant

2. Build using ant

> cd build
> ant

Buildfile: build.xml

init:
 ...
BUILD SUCCESSFUL
Total time: 10 seconds

3. Run Beanshell

> cd ../deploy/bin/
> ./bsc.sh
Start ClassPath Mapping
 ...
BeanShell 2.0b4 - by Pat Niemeyer (pat@pat.net)
bsh % 

--------
Windows
--------

1. Verify your environment

1.1. Make sure that you've placed all distribution files under C:\iWeb2\ 

1.2. Start windows command line interpreter (cmd.exe) and confirm that you 
can run java and ant from command line on your system. 

From windows command prompt execute:

java -version
ant -version

If you get an error see step 2. Otherwise skip to step 3.

2. Configure your java environment

You can skip this step if you already have JDK and Ant configured on your 
system to run from command line. Assuming that java jdk is in C:\jdk1.5.0_12 and 
Ant is in C:\apache-ant-1.7.0 use the following commands:

SET JAVA_HOME=C:\jdk1.5.0_12
SET ANT_HOME=C:\apache-ant-1.7.0
SET PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%PATH%

At this point you should be able to run java and ant from command line without 
errors. If you've only configured environment for your current command line 
interpreter make sure that you perform steps 3 and 4 in the same instance of 
interpreter.

3. Run ant build file for the project: 

From command prompt execute:

cd /D C:\iWeb2\build
ant

Ant will execute default target from C:\iWeb2\build\build.xml build file. It 
will build all source code and will prepare 'C:\iWeb2\deploy' directory. 

4. Start beanshell

From command prompt execute:

C:\iWeb2\deploy\bin\bsc.bat

You are ready to run book examples!!!