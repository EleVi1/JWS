package fr.epita.assistants.presentation.rest.request;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class ReverseRequest {
    public String content;

    public ReverseRequest(String content) {
        this.content = content;
    }
}
