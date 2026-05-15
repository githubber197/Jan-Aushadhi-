@rem ##########################################################################
@rem  Gradle startup script for Windows — uses cached Gradle 8.13 directly
@rem ##########################################################################
@if "%DEBUG%"=="" @echo off
@rem Set local scope
setlocal
set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.

set GRADLE_HOME=C:\Users\Rajeev\.gradle\wrapper\dists\gradle-8.13-bin\5xuhj0ry160q40clulazy9h7d\gradle-8.13
set APP_HOME=%DIRNAME%

"%GRADLE_HOME%\bin\gradle.bat" %*
