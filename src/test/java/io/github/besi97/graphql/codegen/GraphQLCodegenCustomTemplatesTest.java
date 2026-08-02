package io.github.besi97.graphql.codegen;

import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateType;
import io.github.besi97.graphql.codegen.java.JavaGraphQLCodegen;
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

class GraphQLCodegenCustomTemplatesTest {

    private final File outputBuildDir = new File("build/generated");
    private final File outputJavaClassesDir = new File("build/generated/io/github/besi97/graphql/test1");

    private MappingConfig mappingConfig;

    @BeforeEach
    void init() {
        mappingConfig = new MappingConfig();
        mappingConfig.setPackageName("io.github.besi97.graphql.test1");
        mappingConfig.setGenerateClient(true);
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_CustomTemplates_Type() throws Exception {
        mappingConfig.putCustomTemplatesIfAbsent(
            FreeMarkerTemplateType.TYPE.name(),
            "src/test/resources/template/record_type.ftl"
        );

        generate("src/test/resources/schemas/test.graphqls");

        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());
        assertFileContainsElements(files, "Event.java",
                "public record Event (");
    }

    private void generate(String path) throws IOException {
        new JavaGraphQLCodegen(singletonList(path),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig))
                .generate();
    }

}
