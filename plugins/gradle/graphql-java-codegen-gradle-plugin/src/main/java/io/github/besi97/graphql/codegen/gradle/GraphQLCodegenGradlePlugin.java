package io.github.besi97.graphql.codegen.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

import java.io.File;

/**
 * Gradle plugin for GraphQL code generation
 *
 * @author Besi97
 */
public class GraphQLCodegenGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();
        
        File defaultResourcesDir = project.getExtensions()
                .getByType(JavaPluginExtension.class)
                .getSourceSets()
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getResources()
                .getSourceDirectories()
                .getFiles()
                .stream()
                .findFirst()
                .orElse(null);
        
        tasks.withType(GraphQLCodegenGradleTask.class).configureEach(task -> {
            task.setDefaultResourcesDir(defaultResourcesDir);
        });
        
        tasks.create("graphqlCodegen", GraphQLCodegenGradleTask.class);
        tasks.create("graphqlCodegenValidate", GraphQLCodegenValidateGradleTask.class);
    }

}
