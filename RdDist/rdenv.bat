rem RD Home
rem ------------------------------
if not "%RD_HOME%"=="" goto rdhome
set RD_HOME=.
:rdhome

rem Here are RD parameters
rem ------------------------------
set RD_OPTS=-DDEBUG -Dprop=prop -Dlib=dist;lib -Dhome=%RD_HOME% -Dlog4j.configuration=file:///%RD_HOME%/prop/log4j.properties

rem JVM
rem ------------------------------
set JVM_EXE=java.exe

rem Here are common RMI parameters
rem ------------------------------
set SUN_RMI_OPTS=-Dsun.rmi.transport.tcp.handshakeTimeout=1200000 -Dsun.rmi.dgc.client.gcInterval=9223372036854775807 -Dsun.rmi.dgc.server.gcInterval=9223372036854775807

rem Here are two GC concurrent and parallel (default)
rem -------------------------------------------------
set CONCURRENT_GC=-XX:+DisableExplicitGC -Xnoclassgc -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
set PARALLEL_GC=-XX:+DisableExplicitGC -Xnoclassgc -XX:+UseParallelGC

rem Here are common JVM parameters
rem ------------------------------
set COMMON_OPTS=-server -XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=15 -XX:+UseMembar -XX:-UseThreadPriorities 

rem Here are memory related JVM parameters
rem -----------------------------
set MEMORY_OPTS=-Xms64m -Xmn32m -Xmx128m -XX:MaxPermSize=32m -XX:PermSize=16m 

set path=%JAVA_HOME%\bin;%path%