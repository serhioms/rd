call rdenv.bat

@echo on
%JVM_EXE% %MEMORY_OPTS% %PARALLEL_GC% %COMMON_OPTS% %SUN_RMI_OPTS% %RD_OPTS% -Dmain=%MAIN_CLASS% -jar %RD_HOME%/dist/rdstarter.jar %1 %2 %3 %4 %5 %6 %7 %8 %9