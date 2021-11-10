#!/bin/bash

(cd docker && docker build -t cljs-phidays .)

docker run \
       -v $(pwd):/home \
       -v $(pwd)/.m2:/root/.m2 \
       -v $(pwd)/.npm:/root/.npm \
       -p 8000:8000 \
       -p 9630:9630 \
       -it cljs-phidays:latest \
       bash -c "(cd /home && ./start.sh)"
