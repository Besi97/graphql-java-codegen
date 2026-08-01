package io.github.besi97.graphql.codegen.supplier;

import io.github.besi97.graphql.codegen.model.MappingConfig;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeableMappingConfigSupplierTest {

    @Test
    void loadCorrect() {
        MappingConfig externalMappingConfig = new MergeableMappingConfigSupplier(new ArrayList<String>() {
            {
                add("src/test/resources/json/mappingconfig.json");
                add("src/test/resources/json/mappingconfig2.json");
            }
        }).get();
        assertEquals("io.github.besi97.graphql.testconfigjson", externalMappingConfig.getPackageName());
        assertTrue(externalMappingConfig.getGenerateApis());
        assertEquals("java.math.BigDecimal", externalMappingConfig.getCustomTypesMapping().get("Price.amount"));
        assertNull(externalMappingConfig.getApiPackageName());
    }

    @Test
    void loadNull() {
        assertNull(new MergeableMappingConfigSupplier(null).get());
    }

    @Test
    void loadInvalid() {
        MergeableMappingConfigSupplier mergeableMappingConfigSupplier = new MergeableMappingConfigSupplier(
                Collections.unmodifiableList(new ArrayList<String>() {
                    {
                        add("blah.json");
                    }
                }));
        // empty object
        assertNull(mergeableMappingConfigSupplier.get().getGenerateApis());
    }

    @Test
    void loadInvalid_file_suffix() {
        MergeableMappingConfigSupplier mergeableMappingConfigSupplier = new MergeableMappingConfigSupplier(
                Collections.unmodifiableList(new ArrayList<String>() {
                    {
                        add("blah.xx");
                    }
                }));
        // empty object
        assertNull(mergeableMappingConfigSupplier.get().getGenerateApis());
    }

    @Test
    void loadValid_with_multi_type() {
        MergeableMappingConfigSupplier mergeableMappingConfigSupplier = new MergeableMappingConfigSupplier(
                Collections.unmodifiableList(new ArrayList<String>() {
                    {
                        add("src/test/resources/json/mappingconfig6.conf");
                    }
                }));
        MappingConfig c = mergeableMappingConfigSupplier.get();
        assert (c.getCustomTypesMapping().size() == 2 && c.getCustomAnnotationsMapping().size() == 2);
    }
}