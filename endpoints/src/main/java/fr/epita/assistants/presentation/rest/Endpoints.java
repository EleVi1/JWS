package fr.epita.assistants.presentation.rest;

import fr.epita.assistants.presentation.rest.request.ReverseRequest;
import fr.epita.assistants.presentation.rest.response.HelloResponse;
import fr.epita.assistants.presentation.rest.response.ReverseResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON) // return json
@Consumes(MediaType.APPLICATION_JSON) // get json
@Path("/")
public class Endpoints{

    @GET
    @Path("/hello/{name}")
    public HelloResponse hello(@PathParam("name") String name)
    {
        HelloResponse response = new HelloResponse(name);
        return response;
    }

    @POST
    @Path("/reverse")
    public Response reverse(ReverseRequest response)
    {
        if (response == null || response.content == null ||response.content.isEmpty() || response.content.compareTo("") == 0)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ReverseResponse rep = new ReverseResponse(response);
        if (rep == null || rep.original == null || rep.original.isEmpty())
        {
            System.out.println("Bad");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(rep, MediaType.APPLICATION_JSON_TYPE).build();
    }
}
