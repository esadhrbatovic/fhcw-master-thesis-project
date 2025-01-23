@echo off
:: Enable delayed expansion for variable manipulation within loops
setlocal enabledelayedexpansion

:: Save the current directory
set "current_dir=%cd%"

:: Loop through all subdirectories
for /d %%d in (*) do (
    echo ===================================================================
    echo Entering directory: %%d
    echo ===================================================================
    :: Change into the directory
    cd "%%d"

    :: Execute the Maven command
    echo Running: gradlew nativeCompile
    gradlew nativeCompile
    if errorlevel 1 (
        echo [ERROR] Maven build failed in %%d
        pause
        goto :END
    )
    
    :: Return to the original directory
    cd "%current_dir%"
)

:END
echo ===================================================================
echo All tasks completed!
pause
