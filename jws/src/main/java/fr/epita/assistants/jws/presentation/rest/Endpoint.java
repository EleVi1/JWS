package fr.epita.assistants.jws.presentation.rest;

import fr.epita.assistants.jws.domain.service.CreateGameService;
import fr.epita.assistants.jws.domain.service.JoinGameService;
import fr.epita.assistants.jws.domain.service.GameInfoService;
import fr.epita.assistants.jws.domain.service.GameListService;
import fr.epita.assistants.jws.presentation.rest.request.CreateGameRequest;
import fr.epita.assistants.jws.presentation.rest.response.CreateGameResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameInfoResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;
import fr.epita.assistants.jws.presentation.rest.response.JoinGameResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Endpoint {

    @Inject
    CreateGameService gameService;

    @Inject
    GameListService ListService;

    @Inject
    GameInfoService gameInfo;
    @Inject
    JoinGameService joinGameService;

    @GET
    @Path("/games")
    public Response getAllGames()
    {
        List<GameListResponse> games = ListService.getAll();
        return Response.ok(games).build();
    }

    @POST
    @Path("/games")
    public Response createGame(CreateGameRequest request)
    {
        if (request == null || request.name == null || request.name.isEmpty())
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String name = request.name;
        CreateGameResponse response = gameService.create(name);

        return Response.ok(response).build();
    }

    @GET
    @Path("/games/{gameId}")
    public Response getGameInfo(@PathParam("gameId") long gameId)
    {
        if (gameId < 0)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        GameInfoResponse infos = gameInfo.getInfo(gameId);
        if (infos == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(infos).build();
    }

    @POST
    @Path("/games/{gameId}")
    public Response joinGame(@PathParam("gameId") long gameId, CreateGameRequest request)
    {
        if (request == null || request.name == null || request.name.isEmpty() || gameId <= 0)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return joinGameService.joinGame(gameId, request.name);

    }

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