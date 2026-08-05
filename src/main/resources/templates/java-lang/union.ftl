<#if package?has_content>
package ${package};

</#if>

<#if javaDoc?has_content>
/**
<#list javaDoc as javaDocLine>
 * ${javaDocLine}
</#list>
 */
</#if>
<#if generatedAnnotation && generatedInfo.getGeneratedType()?has_content>
@${generatedInfo.getGeneratedType()}(
    value = "io.github.besi97.graphql.codegen.GraphQLCodegen"<#if generatedInfo.getDateTime()??>,
    date = "${generatedInfo.getDateTime()}"</#if>
)
</#if>
<#list annotations as annotation>
@${annotation}
</#list>
public interface ${className} {

}
