package fr.epita.assistants.presentation.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloResponse {

    public String content;
    public HelloResponse(@JsonProperty("name") String name) {
        this.content = "hello " + name;
    }
}
