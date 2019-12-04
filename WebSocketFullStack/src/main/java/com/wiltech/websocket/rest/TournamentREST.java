package com.wiltech.websocket.rest;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wiltech.websocket.StarterService;
import com.wiltech.websocket.TennisMatch;

@Path("/tournament")
public class TournamentREST {

    @Inject StarterService ejb;

    @GET
    @Path("/lives")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<TennisMatch> getMatchesOnLive() {
        return ejb.getMatches().values();
    }
}
