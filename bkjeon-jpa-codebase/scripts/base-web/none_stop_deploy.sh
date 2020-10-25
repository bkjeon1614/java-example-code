#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)

${ABSDIR}/stop.sh && ${ABSDIR}/start.sh && ${ABSDIR}/health.sh