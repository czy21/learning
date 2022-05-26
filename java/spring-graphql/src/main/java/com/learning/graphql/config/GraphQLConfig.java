package com.learning.graphql.config;

import com.learning.graphql.provider.GraphQLDataFetcher;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@EnableConfigurationProperties(GraphQLProperties.class)
@Configuration
public class GraphQLConfig {
    GraphQLProperties graphQLProperties;
    Map<String,DataFetcher<?>> dataFetcherMap;

    public GraphQLConfig(GraphQLProperties graphQLProperties,
                         ObjectProvider<List<DataFetcher<?>>> dataFetchers) {
        this.graphQLProperties = graphQLProperties;

    }

    @Bean
    public GraphQL graphQL(GraphQLDataFetcher graphQLDataFetcher) {
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        Resource[] schemaResources = graphQLProperties.resolveSchemaLocations();
        parseSchemaResources(typeRegistry, schemaParser, schemaResources);
        RuntimeWiring runtimeWiring = buildWiring(graphQLDataFetcher);
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    public void parseSchemaResources(TypeDefinitionRegistry typeDefinitionRegistry,
                                     SchemaParser schemaParser,
                                     Resource... schemaResources) {
        for (Resource r : schemaResources) {
            try {
                typeDefinitionRegistry.merge(schemaParser.parse(r.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private RuntimeWiring buildWiring(GraphQLDataFetcher graphQLDataFetcher) {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("books", graphQLDataFetcher.findAllBook())
                        .dataFetcher("bookById", graphQLDataFetcher.findBookById())
                )
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetcher.findAuthorById())
                )
                .build();
    }

}
