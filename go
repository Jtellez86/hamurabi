#!/bin/bash

set -e

function help {
    echo "Usage: ./go <command>"
    echo ""
    echo "Available commands are:"
    echo "    build           Compile and run unit tests"
    echo "    clean           Remove files generated from build"
    echo "    precommit       Run travis lint and build"
    echo "    generateJar     Generate an executable jar"
}

function clean {
  echo '******Cleaning*******'
  ./gradlew clean;
}

function build {
  echo '******Building*******'
  ./gradlew build;
}

function precommit {
  echo '******Checking travis config syntax*******'
  travis lint;
  build;
}

function generateJar {
  clean;
  build;
  echo '******Generating Jar*******'
  ./gradlew fatJar
}


[[ $@ ]] || { help; exit 1; }

case "$1" in
    build) build
    ;;
    clean) clean
    ;;
    precommit) precommit
    ;;
    generateJar) generateJar
    ;;
esac
