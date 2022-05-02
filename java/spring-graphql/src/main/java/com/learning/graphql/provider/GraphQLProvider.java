package com.learning.graphql.provider;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {
    GraphQLDataFetcher graphQLDataFetcher;
    ApplicationContext applicationContext;

    public GraphQLProvider(GraphQLDataFetcher graphQLDataFetcher,
                           ApplicationContext applicationContext) {
        this.graphQLDataFetcher = graphQLDataFetcher;
        this.applicationContext = applicationContext;
    }

    @Bean
    public GraphQL graphQL() {
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        try {
            Resource[] resources = applicationContext.getResources("classpath*:" + "graphql/**.graphql");
            List<TypeDefinitionRegistry> typeDefinitionRegistries = Arrays.stream(resources)
                    .map(t -> {
                        try {
                            return schemaParser.parse(t.getInputStream());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
            typeDefinitionRegistries.forEach(typeRegistry::merge);
            RuntimeWiring runtimeWiring = buildWiring();
            GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
            return GraphQL.newGraphQL(graphQLSchema).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RuntimeWiring buildWiring() {
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