package com.learning.graphql.provider;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        try (InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("schema.graphqls")) {
            String sdl = schemaStream != null ? new String(schemaStream.readAllBytes(), StandardCharsets.UTF_8) : "";
            GraphQLSchema graphQLSchema = buildSchema(sdl);
            this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GraphQLSchema buildSchema(String sdl) {
        // TODO: we will create the schema here later
        return null;
    }
}