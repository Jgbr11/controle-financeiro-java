@REM Maven Wrapper script for Windows
@echo off
@setlocal

set WRAPPER_JAR="%~dp0\.mvn\wrapper\maven-wrapper.jar"

@REM Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
goto error

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
if exist "%JAVA_EXE%" goto execute
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
goto error

:execute
for %%i in ("%~dp0\.") do set PROJECT_BASEDIR=%%~fi

"%JAVA_EXE%" ^
  "-Dmaven.multiModuleProjectDirectory=%PROJECT_BASEDIR%" ^
  -classpath %WRAPPER_JAR% ^
  org.apache.maven.wrapper.MavenWrapperMain %*

if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%
cmd /C exit /B %ERROR_CODE%
