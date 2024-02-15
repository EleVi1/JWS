package fr.epita.assistants.jws.presentation.rest;

import fr.epita.assistants.jws.domain.service.*;
import fr.epita.assistants.jws.presentation.rest.request.CreateGameRequest;
import fr.epita.assistants.jws.presentation.rest.request.MovePlayerRequest;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;

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
    @Inject
    StartGameService startGameService;

    @Inject
    MovePlayerService moveService;
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
        GameDetailResponse response = gameService.create(name);

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
        GameDetailResponse infos = gameInfo.getInfo(gameId);
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

    @PATCH
    @Path("/games/{gameId}/start")
    public Response startGame(@PathParam("gameId") long gameId) {
        if (gameId <= 0)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        GameDetailResponse resp = startGameService.startGame(gameId);
        if (resp == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(resp).build();
    }

    @POST
    @Path("/games/{gameId}/players/{playerId}/move")
    public Response move(@PathParam("gameId") long gameId, @PathParam("playerId") long playerId,
                             MovePlayerRequest request)
    {
        if (request == null || request.posX < 0 || request.posX >= 17
                || request.posY < 0 ||  request.posY >= 15 || gameId <= 0)
        {
            return Response.status(Response.Status.BAD_REQUEST).build(); // 400
        }
        return moveService.move(gameId, playerId, request.posX, request.posY);
    }
}