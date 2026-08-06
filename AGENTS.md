# Agents Guide

This document provides guidance for AI agents working with the graphql-java-codegen repository.

## GitHub CLI (gh) Configuration

### Repository Specification

When using the `gh` CLI tool, you must always specify the repository explicitly using the `--repo` flag:

```bash
gh pr create --repo Besi97/graphql-java-codegen --title "PR title" --body "PR description"
```

**Reason:** This repository has two remotes configured:
- `origin` → `Besi97/graphql-java-codegen` (the active repository)
- `upstream` → `kobylynskyi/graphql-java-codegen` (abandoned)

When `gh` auto-detects the repository, it defaults to the upstream remote. The upstream repository is abandoned, so all PRs must be created against `Besi97/graphql-java-codegen`.

### Alternative: Set Default Repository

You can set the default repository to avoid specifying `--repo` every time:

```bash
gh repo set-default Besi97/graphql-java-codegen
```

After running this command, `gh` will use your fork by default for all operations.

## Workflow

- Always create feature branches from `main`
- Push to your fork (`origin`) and create PRs against `Besi97/graphql-java-codegen`
