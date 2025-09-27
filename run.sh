#!/usr/bin/env sh
set -eu
REQUIRED=25

command -v java >/dev/null 2>&1 || {
  echo "ERROR: Java not found in PATH. Install JDK $REQUIRED+ and try again." >&2
  exit 1
}

MAJOR=$(java -XshowSettings:properties -version 2>&1 \
  | awk -F'= ' '/java\.specification\.version/ {gsub(/^[ \t]+/,"",$2); print $2; exit}')
case "${MAJOR:-}" in ''|*[!0-9]*) echo "ERROR: Could not parse Java version." >&2; exit 1 ;; esac
[ "$MAJOR" -ge "$REQUIRED" ] || { echo "ERROR: Java $REQUIRED+ required, found $MAJOR." >&2; exit 1; }

DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)
CP="$DIR/libs/*:$DIR/src"

# Forward all CLI args from the launcher to Main.java
exec java --class-path "$CP" "$DIR/src/Main.java" "$@"