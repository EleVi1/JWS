package fr.epita.assistants.presentation.rest;

import fr.epita.assistants.presentation.rest.request.ReverseRequest;
import fr.epita.assistants.presentation.rest.response.HelloResponse;
import fr.epita.assistants.presentation.rest.response.ReverseResponse;
import io.vertx.core.json.Json;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON) // return json
@Consumes(MediaType.APPLICATION_JSON) // get json
@Path("/")
public class Endpoints{

    @GET
    @Path("/hello/{name}")
    public HelloResponse hello(@PathParam("name") String name)
    {
        HelloResponse rep = new HelloResponse(name);
        return rep;
    }

    @POST
    @Path("/reverse")
    public ReverseResponse reverse(ReverseRequest response)
    {
        ReverseResponse rep = new ReverseResponse(response);
        return rep;
    }
}
