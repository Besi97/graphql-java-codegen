package io.github.besi97.graphql.codegen;

import io.github.besi97.graphql.codegen.java.JavaGraphQLCodegen;
import io.github.besi97.graphql.codegen.model.MappingConfig;
import io.github.besi97.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.github.besi97.graphql.codegen.TestUtils.assertSameTrimmedContent;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphQLCodegenMultiFilesTest {

    private final File outputBuildDir = new File("build/generated");
    private final File outputJavaClassesDir = new File("build/generated/io/github/besi97/graphql/multifiles");

    private GraphQLCodegen generator;

    @BeforeEach
    void init() {
        MappingConfig mappingConfig = new MappingConfig();
        mappingConfig.setPackageName("io.github.besi97.graphql.multifiles");
        List<String> schemas = Arrays.asList(
                "src/test/resources/schemas/multi1.graphqls",
                "src/test/resources/schemas/multi2.graphqls"
        );
        generator = new JavaGraphQLCodegen(schemas, outputBuildDir, mappingConfig,
                TestUtils.getStaticGeneratedInfo(mappingConfig));
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_CheckFiles() throws Exception {
        generator.generate();

        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());
        List<String> generatedFileNames = Arrays.stream(files).map(File::getName).sorted().collect(toList());
        assertEquals(Arrays.asList("MyUnion.java", "UnionMember1.java", "UnionMember2.java"), generatedFileNames);

        for (File file : files) {
            assertSameTrimmedContent(
                    new File(String.format("src/test/resources/expected-classes/%s.txt", file.getName())),
                    file);
        }
    }
}
