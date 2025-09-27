@ECHO OFF
SETLOCAL
SET "REQUIRED=25"

REM Ensure Java exists
WHERE java >NUL 2>&1 || (
  ECHO ERROR: Java not found in PATH. Install JDK %REQUIRED%+ and try again.
  EXIT /B 1
)

REM Java version check via PowerShell (single line; avoids CMD quoting issues)
powershell -NoLogo -NoProfile -Command " $maj = ((& java -XshowSettings:properties -version 2>&1) | Select-String 'java\.specification\.version').ToString().Split('=')[1].Trim(); if (-not $maj -or -not ($maj -match '^\d+$')) { exit 2 } elseif ([int]$maj -lt %REQUIRED%) { exit 3 } else { Write-Host ('Java {0} detected (OK).' -f $maj); exit 0 } "

IF ERRORLEVEL 3 (
  ECHO ERROR: Java %REQUIRED%+ required.
  EXIT /B 1
) ELSE IF ERRORLEVEL 2 (
  ECHO ERROR: Could not parse Java version.
  EXIT /B 1
)

REM Run program; forward any args given to this launcher
java --class-path "libs\*;src" src\Main.java %*