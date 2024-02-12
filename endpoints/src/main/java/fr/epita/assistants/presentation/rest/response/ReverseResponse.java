package fr.epita.assistants.presentation.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.epita.assistants.presentation.rest.request.ReverseRequest;

public class ReverseResponse {

    public String original;
    public String reversed;
    public ReverseResponse(ReverseRequest response) {
        original = response.content;
        reversed = new StringBuilder(original).reverse().toString();
    }
}
