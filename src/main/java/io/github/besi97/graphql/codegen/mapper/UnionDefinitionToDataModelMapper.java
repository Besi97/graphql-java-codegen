package io.github.besi97.graphql.codegen.mapper;

import io.github.besi97.graphql.codegen.model.MappingContext;
import io.github.besi97.graphql.codegen.model.builders.JavaDocBuilder;
import io.github.besi97.graphql.codegen.model.definitions.ExtendedUnionTypeDefinition;

import java.util.HashMap;
import java.util.Map;

import static io.github.besi97.graphql.codegen.model.DataModelFields.ANNOTATIONS;
import static io.github.besi97.graphql.codegen.model.DataModelFields.CLASS_NAME;
import static io.github.besi97.graphql.codegen.model.DataModelFields.GENERATED_ANNOTATION;
import static io.github.besi97.graphql.codegen.model.DataModelFields.GENERATED_INFO;
import static io.github.besi97.graphql.codegen.model.DataModelFields.GENERATE_SEALED_INTERFACES;
import static io.github.besi97.graphql.codegen.model.DataModelFields.JAVA_DOC;
import static io.github.besi97.graphql.codegen.model.DataModelFields.PACKAGE;

/**
 * Map union definition to a Freemarker data model
 *
 * @author Besi97
 */
public class UnionDefinitionToDataModelMapper {

    private final AnnotationsMapper annotationsMapper;
    private final DataModelMapper dataModelMapper;

    public UnionDefinitionToDataModelMapper(MapperFactory mapperFactory) {
        this.annotationsMapper = mapperFactory.getAnnotationsMapper();
        this.dataModelMapper = mapperFactory.getDataModelMapper();
    }

    /**
     * Map union definition to a Freemarker data model
     *
     * @param mappingContext Global mapping context
     * @param definition     Definition of union type including base definition and its extensions
     * @return Freemarker data model of the GraphQL union
     */
    public Map<String, Object> map(MappingContext mappingContext, ExtendedUnionTypeDefinition definition) {
        Map<String, Object> dataModel = new HashMap<>();
        // type/enum/input/interface/union classes do not require any imports
        dataModel.put(PACKAGE, DataModelMapper.getModelPackageName(mappingContext));
        dataModel.put(CLASS_NAME, dataModelMapper.getModelClassNameWithPrefixAndSuffix(mappingContext, definition));
        dataModel.put(ANNOTATIONS, annotationsMapper.getAnnotations(mappingContext, definition));
        dataModel.put(JAVA_DOC, JavaDocBuilder.build(definition));
        dataModel.put(GENERATED_ANNOTATION, mappingContext.getAddGeneratedAnnotation());
        dataModel.put(GENERATED_INFO, mappingContext.getGeneratedInformation());
        dataModel.put(GENERATE_SEALED_INTERFACES, mappingContext.isGenerateSealedInterfaces());
        return dataModel;
    }

}
