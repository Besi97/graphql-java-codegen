#!/bin/bash

CURRENT_VERSION=$(grep "def graphqlCodegenVersion = '" build.gradle | sed "s/.*'\([^']*\)'.*/\1/")

MAJOR=$(echo "$CURRENT_VERSION" | cut -d. -f1)
MINOR=$(echo "$CURRENT_VERSION" | cut -d. -f2)
PATCH=$(echo "$CURRENT_VERSION" | cut -d. -f3)

NEXT_PATCH=$((PATCH + 1))
NEXT_VERSION="$MAJOR.$MINOR.$NEXT_PATCH-SNAPSHOT"

echo "Current version: $CURRENT_VERSION"
echo "Next SNAPSHOT version: $NEXT_VERSION"

./scripts/update-release-version-in-build-files.sh "$NEXT_VERSION"

exit 0
