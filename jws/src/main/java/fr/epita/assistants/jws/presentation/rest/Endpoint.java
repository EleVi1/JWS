package fr.epita.assistants.jws.presentation.rest;

import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.service.GameListService;
import fr.epita.assistants.jws.presentation.rest.request.CreateGameRequest;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class Endpoint {
    @GET
    @Path("/games")
    public Response getAllGames()
    {
        List<GameEntity> entities = new GameListService().getAll(); // list entities
        List<GameListResponse> games =  new ArrayList<>();
        for (GameEntity entity: entities)
        {
            games.add(new GameListResponse(entity.id, entity.starttime, entity.state));
        }
        return Response.ok(games).build();
    }

//    @GET
//    @Path("/games/{gameId}")
//    public Game getGameInfo(@PathParam("gameId") long gameId)
//    {
//        //TODO
//    }


    @POST
    @Path("/games/")
    public Response createGame(CreateGameRequest request)
    {
//        CreateGameResponse rep =
//        return Response.ok(rep, MediaType.APPLICATION_JSON_TYPE).build();
        return null;
    }

//    @POST
//    @Path("/games/{gameId}")
//    public Response joinGame(@PathParam("gameId") long gameId)
//    {
//        //TODO
//    }

//    @PATCH
//    @Path("/games/{gameId}/start")
//    public Response startGame(@PathParam("gameId") long gameId)
//    {
//        //TODO
//    }

//    @POST
//    @Path("/reverse")
//    public Response reverse(ReverseRequest response)
//    {
//        if (response == null || response.content == null ||response.content.isEmpty() || response.content.compareTo("") == 0)
//        {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//        ReverseResponse rep = new ReverseResponse(response);
//        if (rep == null || rep.original == null || rep.original.isEmpty())
//        {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//        return Response.ok(rep, MediaType.APPLICATION_JSON_TYPE).build();
//    }
}