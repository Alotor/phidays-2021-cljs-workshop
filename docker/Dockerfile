FROM ubuntu:20.04

ARG DEBIAN_FRONTEND=noninteractive

ENV NODE_VERSION=v14.17.6 \
    CLOJURE_VERSION=1.10.3.967 \
    LANG=en_US.UTF-8 \
    LC_ALL=en_US.UTF-8

RUN set -ex; \
    apt-get -qq update; \
    apt-get -qqy install --no-install-recommends \
        locales \
        gnupg2 \
        ca-certificates \
        wget \
        sudo \
        curl \
        bash \
        git \
        rlwrap \
        unzip \
        fakeroot \
        netcat \
        build-essential \
    ; \
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen; \
    locale-gen; \
    rm -rf /var/lib/apt/lists/*;

RUN set -ex; \
    curl -LfsSo /tmp/openjdk.tar.gz https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download/jdk-16.0.1%2B9/OpenJDK16U-jdk_x64_linux_hotspot_16.0.1_9.tar.gz; \
    mkdir -p /usr/lib/jvm/openjdk16; \
    cd /usr/lib/jvm/openjdk16; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
    rm -rf /tmp/openjdk.tar.gz;

ENV PATH="/usr/lib/jvm/openjdk16/bin:/usr/local/nodejs/bin:$PATH" \
    JAVA_HOME=/usr/lib/jvm/openjdk16

RUN set -ex; \
    curl -LfsSo /tmp/clojure.sh https://download.clojure.org/install/linux-install-$CLOJURE_VERSION.sh; \
    chmod +x /tmp/clojure.sh; \
    /tmp/clojure.sh; \
    rm -rf /tmp/clojure.sh;

RUN set -ex; \
    curl -LfsSo /tmp/nodejs.tar.xz https://nodejs.org/dist/$NODE_VERSION/node-$NODE_VERSION-linux-x64.tar.xz; \
    mkdir -p /usr/local/nodejs; \
    cd /usr/local/nodejs; \
    tar -xf /tmp/nodejs.tar.xz --strip-components=1; \
    chown -R root /usr/local/nodejs; \
    PATH="$PATH:/usr/local/nodejs/bin"; \
    /usr/local/nodejs/bin/npm install -g yarn; \
    /usr/local/nodejs/bin/npm install -g svgo; \
    rm -rf /tmp/nodejs.tar.xz;

EXPOSE 8000
EXPOSE 9630
