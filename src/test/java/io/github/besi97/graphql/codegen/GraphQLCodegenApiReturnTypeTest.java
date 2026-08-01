package io.github.besi97.graphql.codegen;

import io.github.besi97.graphql.codegen.java.JavaGraphQLCodegen;
import io.github.besi97.graphql.codegen.model.GeneratedLanguage;
import io.github.besi97.graphql.codegen.model.MappingConfig;
import io.github.besi97.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static io.github.besi97.graphql.codegen.TestUtils.assertFileContainsElements;
import static java.util.Collections.singletonList;

class GraphQLCodegenApiReturnTypeTest {

    private final File outputBuildDir = new File("build/generated");
    private final File outputJavaClassesDir = new File("build/generated/io/github/besi97/graphql/test1");

    private MappingConfig mappingConfig;

    @BeforeEach
    void init() {
        mappingConfig = new MappingConfig();
        mappingConfig.setPackageName("io.github.besi97.graphql.test1");
        mappingConfig.setGeneratedLanguage(GeneratedLanguage.JAVA);
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_ApiReturnType_WithPlaceHolder() throws Exception {
        mappingConfig.setApiReturnType(
                "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<{{TYPE}}>>"
        );

        generate("src/test/resources/schemas/test.graphqls");

        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());

        String requireChildText = getChildText(
                "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult" +
                        "<java.util.List<EventProperty>>>"
        );
        assertFileContainsElements(
                files,
                "EventPropertyResolver.java",
                requireChildText
        );

        String requireParentText = getParentText(
                "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<Event>>"
        );
        assertFileContainsElements(files, "EventPropertyResolver.java",
                requireParentText
        );
    }

    @Test
    void generate_ApiReturnType_And_ApiReturnListType_WithPlaceHolder() throws Exception {
        mappingConfig.setApiReturnType(
                "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<{{TYPE}}>>"
        );
        mappingConfig.setApiReturnListType(
                "reactor.core.publisher.Mono<graphql.execution.DataFetcherResult<{{TYPE}}>>"
        );

        generate("src/test/resources/schemas/test.graphqls");

        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());

        assertFileContainsElements(
                files,
                "EventPropertyResolver.java",
                getChildText(
                        "reactor.core.publisher.Mono<graphql.execution.DataFetcherResult<EventProperty>>"
                )
        );

        assertFileContainsElements(
                files,
                "EventPropertyResolver.java",
                getParentText(
                        "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<Event>>"
                )
        );
    }

    private String getChildText(String returnType) {
        return returnType + " child(EventProperty eventProperty, Integer first, Integer last) throws Exception;";
    }

    private String getParentText(String returnType) {
        return returnType +
                " parent(EventProperty eventProperty, EventStatus withStatus, String createdAfter) throws Exception;";
    }

    private void generate(String path) throws IOException {
        new JavaGraphQLCodegen(singletonList(path), outputBuildDir, mappingConfig,
                TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();
    }

}
