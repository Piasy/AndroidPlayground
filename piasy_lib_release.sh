#!/bin/bash
export IS_PIASY_LIB_RELEASE=true
./gradlew bintrayUpload
unset IS_PIASY_LIB_RELEASE
