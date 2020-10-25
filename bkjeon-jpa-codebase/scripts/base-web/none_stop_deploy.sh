#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)

chmod +x ${ABSDIR}/stop.sh
chmod +x ${ABSDIR}/start.sh
chmod +x ${ABSDIR}/health.sh
${ABSDIR}/stop.sh && ${ABSDIR}/start.sh && ${ABSDIR}/health.sh