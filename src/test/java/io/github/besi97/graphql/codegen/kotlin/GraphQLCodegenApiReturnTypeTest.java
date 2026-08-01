package io.github.besi97.graphql.codegen.kotlin;

import io.github.besi97.graphql.codegen.TestUtils;
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
        mappingConfig.setGeneratedLanguage(GeneratedLanguage.KOTLIN);
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

        String requireChildText = getChildFunction(
                "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<List<EventProperty?>?>>"
        );
        assertFileContainsElements(
                files,
                "EventPropertyResolver.kt",
                requireChildText
        );

        String requireParentText = getParentFunction(
                "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<Event?>>"
        );
        assertFileContainsElements(
                files,
                "EventPropertyResolver.kt",
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
                "EventPropertyResolver.kt",
                getChildFunction(
                        "reactor.core.publisher.Mono<graphql.execution.DataFetcherResult<EventProperty?>>?"
                )
        );

        assertFileContainsElements(
                files,
                "EventPropertyResolver.kt",
                getParentFunction(
                        "java.util.concurrent.CompletionStage<graphql.execution.DataFetcherResult<Event?>>"
                )
        );
    }

    private String getChildFunction(String returnType) {
        return "fun child(eventProperty: EventProperty, first: Int?, last: Int?)" +
                ": " + returnType;
    }

    private String getParentFunction(String returnType) {
        return "fun parent(eventProperty: EventProperty, withStatus: EventStatus?, createdAfter: String?)" +
                ": " + returnType;
    }

    private void generate(String path) throws IOException {
        new KotlinGraphQLCodegen(singletonList(path), outputBuildDir, mappingConfig,
                TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();
    }

}
