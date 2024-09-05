package com.learning.graphql.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@ConfigurationProperties(prefix = GraphQLProperties.GRAPHQL_PREFIX)
public class GraphQLProperties {

    public static final String GRAPHQL_PREFIX = "graphql";

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private String[] schemaLocations;

    public String[] getSchemaLocations() {
        return schemaLocations;
    }

    public void setSchemaLocations(String[] schemaLocations) {
        this.schemaLocations = schemaLocations;
    }

    public Resource[] resolveSchemaLocations() {
        return Stream.of(Optional.ofNullable(this.schemaLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }
}
