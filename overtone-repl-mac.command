#!/bin/sh
cd "$(dirname "$0")"
mkdir -p ~/.lein/self-installs
cp -n leiningen-2.3.2-standalone.jar ~/.lein/self-installs
cp -Rn m2 ~/.m2
./lein repl
