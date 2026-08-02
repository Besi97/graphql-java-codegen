package io.github.besi97.graphql.codegen.generators.impl;

import io.github.besi97.graphql.codegen.generators.FilesGenerator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateType;
import io.github.besi97.graphql.codegen.mapper.DataModelMapperFactory;
import io.github.besi97.graphql.codegen.mapper.EnumDefinitionToDataModelMapper;
import io.github.besi97.graphql.codegen.model.MappingContext;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedEnumTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates files for enums
 */
public class EnumsGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final EnumDefinitionToDataModelMapper enumDefinitionMapper;

    public EnumsGenerator(MappingContext mappingContext,
                          DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.enumDefinitionMapper = dataModelMapperFactory.getEnumDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedEnumTypeDefinition definition : mappingContext.getDocument().getEnumDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedEnumTypeDefinition definition) {
        Map<String, Object> dataModel = enumDefinitionMapper.map(mappingContext, definition);
        return FreeMarkerTemplateFilesCreator.create(mappingContext, FreeMarkerTemplateType.ENUM, dataModel);
    }

}
