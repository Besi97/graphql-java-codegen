# Agents Guide

This document provides guidance for AI agents working with the graphql-java-codegen repository.

## GitHub CLI (gh) Configuration

### Repository Specification

When using the `gh` CLI tool, you must always specify the repository explicitly using the `--repo` flag:

```bash
gh pr create --repo Besi97/graphql-java-codegen --title "PR title" --body "PR description"
```

**Reason:** This repository has two remotes configured:
- `origin` → `Besi97/graphql-java-codegen` (your fork)
- `upstream` → `kobylynskyi/graphql-java-codegen` (the original repository)

When `gh` auto-detects the repository, it defaults to the upstream remote. However, authentication tokens typically only have permission to create PRs on your fork (`Besi97/graphql-java-codegen`), not the upstream repository. This causes permission errors like:

```
GraphQL: Resource not accessible by personal access token (createPullRequest)
```

### Alternative: Set Default Repository

You can set the default repository to avoid specifying `--repo` every time:

```bash
gh repo set-default Besi97/graphql-java-codegen
```

After running this command, `gh` will use your fork by default for all operations.

## Workflow

- Always create feature branches from `main`
- Push to your fork (`origin`) and create PRs against the upstream repository (`kobylynskyi/graphql-java-codegen`) when contributing to the main project
