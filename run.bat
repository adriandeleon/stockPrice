@echo off
setlocal
set "REQUIRED=25"

rem Ensure Java exists
where java >NUL 2>&1 || (
  echo ERROR: Java not found in PATH. Install JDK %REQUIRED%+ and try again.
  exit /b 1
)

rem Use PowerShell (present on Win10/11) to parse java.specification.version safely
powershell -NoLogo -NoProfile -Command " $maj = ((& java -XshowSettings:properties -version 2>&1) | Select-String 'java\.specification\.version').ToString().Split('=')[1].Trim(); if (-not $maj -or -not ($maj -match '^\d+$')) { exit 2 } elseif ([int]$maj -lt %REQUIRED%) { exit 3 } else { Write-Host ('Java {0} detected (OK).' -f $maj); exit 0 } "

if errorlevel 3 (
  echo ERROR: Java %REQUIRED%+ required.
  exit /b 1
) else if errorlevel 2 (
  echo ERROR: Could not parse Java version.
  exit /b 1
)

rem Run program
java --class-path "libs\*;src" src\Main.java