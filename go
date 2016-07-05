#!/bin/bash

set -e

function help {
    echo "Usage: ./go <command>"
    echo ""
    echo "Available commands are:"
    echo "    build           Compile and run unit tests"
    echo "    clean           Remove files generated from build"
}

function clean {
  echo '******Cleaning*******'
  gradle clean;
}

function build {
  echo '******Checking travis config syntax*******'
  travis lint;
  echo '******Building*******'
  gradle build;
}

[[ $@ ]] || { help; exit 1; }

case "$1" in
    build) build
    ;;
    clean) clean
    ;;
esac
