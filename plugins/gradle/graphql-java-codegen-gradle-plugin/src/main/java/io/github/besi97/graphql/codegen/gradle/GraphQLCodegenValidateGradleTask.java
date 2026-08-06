package io.github.besi97.graphql.codegen.gradle;

import io.github.besi97.graphql.codegen.GraphQLCodegenValidate;
import org.gradle.api.DefaultTask;
import org.gradle.work.DisableCachingByDefault;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.util.List;

/**
 * Gradle task for GraphQL code generation
 *
 * @author Besi97
 */
@DisableCachingByDefault(because = "Validation task does not produce output files")
public class GraphQLCodegenValidateGradleTask extends DefaultTask {

    private List<String> graphqlSchemaPaths;

    @TaskAction
    public void validate() throws IOException {
        new GraphQLCodegenValidate(graphqlSchemaPaths).validate();
    }

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    public List<String> getGraphqlSchemaPaths() {
        return graphqlSchemaPaths;
    }

    public void setGraphqlSchemaPaths(List<String> graphqlSchemaPaths) {
        this.graphqlSchemaPaths = graphqlSchemaPaths;
    }
}
