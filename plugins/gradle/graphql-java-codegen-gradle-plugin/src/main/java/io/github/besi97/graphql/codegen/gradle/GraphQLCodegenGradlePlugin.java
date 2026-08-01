package io.github.besi97.graphql.codegen.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * Gradle plugin for GraphQL code generation
 *
 * @author Besi97
 */
public class GraphQLCodegenGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();
        tasks.create("graphqlCodegen", GraphQLCodegenGradleTask.class);
        tasks.create("graphqlCodegenValidate", GraphQLCodegenValidateGradleTask.class);
    }

}
