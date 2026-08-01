package io.github.besi97.graphql.codegen;

import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateType;
import io.github.besi97.graphql.codegen.java.JavaMapperFactoryImpl;
import io.github.besi97.graphql.codegen.mapper.DataModelMapperFactory;
import io.github.besi97.graphql.codegen.mapper.MapperFactory;
import io.github.besi97.graphql.codegen.model.DataModelFields;
import io.github.besi97.graphql.codegen.model.GeneratedInformation;
import io.github.besi97.graphql.codegen.model.GeneratedLanguage;
import io.github.besi97.graphql.codegen.model.MappingConfig;
import io.github.besi97.graphql.codegen.model.MappingContext;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedDocument;
import io.github.besi97.graphql.codegen.model.exception.UnableToCreateFileException;
import io.github.besi97.graphql.codegen.parser.GraphQLDocumentParser;
import io.github.besi97.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GraphQLCodegenFileCreatorTest {

    public static final File OUTPUT_DIR = new File("build/dir");
    private static final MapperFactory MAPPER_FACTORY = new JavaMapperFactoryImpl();

    @AfterEach
    void cleanup() {
        Utils.deleteDir(OUTPUT_DIR);
    }

    @Test
    void generateFile() throws IOException {
        MappingConfig mappingConfig = new MappingConfig();
        mappingConfig.setGeneratedLanguage(GeneratedLanguage.JAVA);
        ExtendedDocument extendedDocument = GraphQLDocumentParser.getDocumentFromSchemas(
                mappingConfig, singletonList("src/test/resources/schemas/test.graphqls"));

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put(DataModelFields.CLASS_NAME, "Class1");
        dataModel.put(DataModelFields.ANNOTATIONS, Collections.emptyList());
        dataModel.put(DataModelFields.GENERATED_ANNOTATION, false);
        dataModel.put(DataModelFields.GENERATED_INFO, new GeneratedInformation(mappingConfig));

        MappingContext mappingContext = MappingContext.builder()
                .setMappingConfig(mappingConfig)
                .setDocument(extendedDocument)
                .setGeneratedInformation(new GeneratedInformation(mappingConfig))
                .setDataModelMapperFactory(new DataModelMapperFactory(MAPPER_FACTORY))
                .setOutputDirectory(OUTPUT_DIR)
                .build();

        FreeMarkerTemplateFilesCreator.create(mappingContext, FreeMarkerTemplateType.ENUM, dataModel);
        assertThrows(UnableToCreateFileException.class,
                () -> FreeMarkerTemplateFilesCreator.create(
                        mappingContext, FreeMarkerTemplateType.ENUM, dataModel));
    }
}
