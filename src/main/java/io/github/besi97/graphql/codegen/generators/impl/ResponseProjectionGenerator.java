package io.github.besi97.graphql.codegen.generators.impl;

import io.github.besi97.graphql.codegen.generators.FilesGenerator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateType;
import io.github.besi97.graphql.codegen.mapper.DataModelMapperFactory;
import io.github.besi97.graphql.codegen.mapper.RequestResponseDefinitionToDataModelMapper;
import io.github.besi97.graphql.codegen.model.MappingContext;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedDefinition;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedInterfaceTypeDefinition;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedObjectTypeDefinition;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedUnionTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generates files for response projections
 */
public class ResponseProjectionGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final RequestResponseDefinitionToDataModelMapper requestResponseDefinitionMapper;

    public ResponseProjectionGenerator(MappingContext mappingContext,
                                       DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.requestResponseDefinitionMapper = dataModelMapperFactory.getRequestResponseDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        if (!Boolean.TRUE.equals(mappingContext.getGenerateClient())) {
            return Collections.emptyList();
        }
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedInterfaceTypeDefinition definition : mappingContext.getDocument().getInterfaceDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        for (ExtendedObjectTypeDefinition definition : mappingContext.getDocument().getTypeDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        for (ExtendedUnionTypeDefinition definition : mappingContext.getDocument().getUnionDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedDefinition<?, ?> definition) {
        Map<String, Object> responseProjDataModel = requestResponseDefinitionMapper.mapResponseProjection(
                mappingContext, definition);
        return FreeMarkerTemplateFilesCreator.create(
                mappingContext, FreeMarkerTemplateType.RESPONSE_PROJECTION, responseProjDataModel);
    }

}
