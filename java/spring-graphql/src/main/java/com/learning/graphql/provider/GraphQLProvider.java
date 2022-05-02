package com.learning.graphql.provider;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
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
        try {
            Resource[] resources = applicationContext.getResources("classpath*:" + "graphql/**.graphql");
            List<TypeDefinitionRegistry> typeDefinitionRegistries = Arrays.stream(resources)
                    .map(t -> {
                        try {
                            return new SchemaParser().parse(t.getInputStream());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
            GraphQLSchema graphQLSchema = buildSchema(typeDefinitionRegistries);
            return GraphQL.newGraphQL(graphQLSchema).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GraphQLSchema buildSchema(List<TypeDefinitionRegistry> typeDefinitionRegistries) {
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        typeDefinitionRegistries.forEach(typeRegistry::merge);
        RuntimeWiring runtimeWiring = buildWiring();
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring);
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