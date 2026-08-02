#!/bin/bash

RELEASE_VERSION=$1
RELEASE_VERSION_ESCAPED=${RELEASE_VERSION//./\\.}

set_version_in_file() {
  sed -i "s/$2[A-Za-z0-9.\-]*/$2$RELEASE_VERSION_ESCAPED/g" "$1"
  echo "Updated version in $1"
}

set_version_in_file "build.gradle" "def graphqlCodegenVersion = '"

set_version_in_file "plugins/gradle/graphql-java-codegen-gradle-plugin/build.gradle" "def graphqlCodegenGradlePluginVersion = '"

set_version_in_file "plugins/gradle/example-server/build.gradle" "io.github.besi97.graphql.codegen\" version \""

set_version_in_file "plugins/gradle/example-client/build.gradle" "implementation \"io.github.besi97:graphql-java-codegen:"
set_version_in_file "plugins/gradle/example-client/build.gradle" "io.github.besi97.graphql.codegen\" version \""

set_version_in_file "plugins/gradle/example-client-kotlin/build.gradle" "id \"io.github.besi97.graphql.codegen\" version \""
set_version_in_file "plugins/gradle/example-client-kotlin/build.gradle" "def graphqlCodegenClientKotlinVersion = '"
set_version_in_file "plugins/gradle/example-client-kotlin/build.gradle" "implementation \"io.github.besi97:graphql-java-codegen:"

# Exit clean
exit 0
