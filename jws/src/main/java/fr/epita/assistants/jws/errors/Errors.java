package fr.epita.assistants.jws.errors;

import javax.ws.rs.core.Response;

public class Errors {

    public static Response sendBadRequest()
    {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public static Response sendNotFound()
    {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    public static Response sendTooManyRequest()
    {

        return Response.status(Response.Status.TOO_MANY_REQUESTS).build();
    }
}
