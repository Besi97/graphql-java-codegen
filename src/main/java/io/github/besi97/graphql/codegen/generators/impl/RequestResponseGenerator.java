package io.github.besi97.graphql.codegen.generators.impl;

import io.github.besi97.graphql.codegen.generators.FilesGenerator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import io.github.besi97.graphql.codegen.generators.FreeMarkerTemplateType;
import io.github.besi97.graphql.codegen.mapper.DataModelMapperFactory;
import io.github.besi97.graphql.codegen.mapper.RequestResponseDefinitionToDataModelMapper;
import io.github.besi97.graphql.codegen.model.MappingContext;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedFieldDefinition;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedObjectTypeDefinition;
import graphql.language.FieldDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Generates files for requests and responses
 */
public class RequestResponseGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final RequestResponseDefinitionToDataModelMapper requestResponseDefinitionMapper;

    public RequestResponseGenerator(MappingContext mappingContext,
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
        for (ExtendedObjectTypeDefinition definition : mappingContext.getDocument().getOperationDefinitions()) {
            generatedFiles.addAll(generate(definition));
        }
        return generatedFiles;
    }

    private List<File> generate(ExtendedObjectTypeDefinition definition) {
        List<File> generatedFiles = new ArrayList<>();
        List<String> fieldNames = definition.getFieldDefinitions().stream().map(FieldDefinition::getName)
                .collect(toList());
        for (ExtendedFieldDefinition operationDef : definition.getFieldDefinitions()) {
            Map<String, Object> requestDataModel = requestResponseDefinitionMapper
                    .mapRequest(mappingContext, operationDef, definition.getName(), fieldNames);
            generatedFiles.add(FreeMarkerTemplateFilesCreator
                    .create(mappingContext, FreeMarkerTemplateType.REQUEST, requestDataModel));

            Map<String, Object> responseDataModel = requestResponseDefinitionMapper
                    .mapResponse(mappingContext, operationDef, definition.getName(), fieldNames);
            generatedFiles.add(FreeMarkerTemplateFilesCreator
                    .create(mappingContext, FreeMarkerTemplateType.RESPONSE, responseDataModel));
        }
        return generatedFiles;
    }

}
