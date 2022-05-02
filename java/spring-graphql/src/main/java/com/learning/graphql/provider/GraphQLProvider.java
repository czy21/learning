package com.learning.graphql.provider;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {
    GraphQLDataFetcher graphQLDataFetcher;

    public GraphQLProvider(GraphQLDataFetcher graphQLDataFetcher) {
        this.graphQLDataFetcher = graphQLDataFetcher;
    }

    @Bean
    public GraphQL graphQL() {
        try (InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("schema.graphqls")) {
            String sdl = schemaStream != null ? new String(schemaStream.readAllBytes(), StandardCharsets.UTF_8) : "";
            GraphQLSchema graphQLSchema = buildSchema(sdl);
            return GraphQL.newGraphQL(graphQLSchema).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
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