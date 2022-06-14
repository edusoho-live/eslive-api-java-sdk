## Release

STEP 1: 修改 pom.xml 版本号 `<version>...</version>`;

STEP 2: 修改 `EsliveApiClient.userAgent` 版本号；

STEP 3: Release
```shell
./mvnw clean deploy -Dmaven.test.skip=true -P release
```