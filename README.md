# EduSho Live Api Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/com.edusoho.live/eslive-api-java-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.edusoho.live%22%20AND%20a:%22eslive-api-java-sdk%22)

EduSoho 大班课直播，开发文档参见： [https://live.edusoho.com/developer/](https://live.edusoho.com/developer/) 。


## Release

STEP 1: 修改 pom.xml 版本号 `<version>...</version>`;

STEP 2: 修改 `EsliveApiClient.userAgent` 版本号；

STEP 3: Release
```shell
./mvnw clean deploy -P release
```