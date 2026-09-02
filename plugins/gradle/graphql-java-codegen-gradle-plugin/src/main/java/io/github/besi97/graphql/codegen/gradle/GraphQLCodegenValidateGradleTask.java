package io.github.besi97.graphql.codegen.gradle;

import io.github.besi97.graphql.codegen.GraphQLCodegenValidate;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.inject.Inject;

/**
 * Gradle task for GraphQL code generation
 *
 * @author Besi97
 */
@CacheableTask
public class GraphQLCodegenValidateGradleTask extends DefaultTask {

    private List<String> graphqlSchemaPaths;
    private final Provider<RegularFile> validationResultFile;

    @Inject
    public GraphQLCodegenValidateGradleTask(ProjectLayout projectLayout) {
        this.validationResultFile = projectLayout.getBuildDirectory()
                .file("graphql-codegen-validate/validation.success");
    }

    @TaskAction
    public void validate() throws IOException {
        new GraphQLCodegenValidate(graphqlSchemaPaths).validate();
        Path resultFilePath = validationResultFile.get().getAsFile().toPath();
        Files.createDirectories(resultFilePath.getParent());
        Files.write(resultFilePath, "OK".getBytes(StandardCharsets.UTF_8));
    }

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    public List<String> getGraphqlSchemaPaths() {
        return graphqlSchemaPaths;
    }

    public void setGraphqlSchemaPaths(List<String> graphqlSchemaPaths) {
        this.graphqlSchemaPaths = graphqlSchemaPaths;
    }

    /**
     * Marker file that records a successful validation run.
     *
     * <p>A cacheable task must declare at least one output, otherwise Gradle silently
     * disables caching for it. This file contains no meaningful data; it only makes
     * the validation result incremental and cacheable.
     *
     * @return the validation result marker file property
     */
    @OutputFile
    public Provider<RegularFile> getValidationResultFile() {
        return validationResultFile;
    }
}
