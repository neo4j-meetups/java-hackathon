package org.neo4j.hackathons;

import java.sql.Connection;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class MoviesResource
{
    private Connection connection;

    public MoviesResource( Connection connection )
    {
        this.connection = connection;
    }

    @GET
    @Timed
    public HomeView index()
    {
        return new HomeView();
    }

    @GET
    @Path( "/person" )
    @Timed
    public PersonView person()
    {
        return new PersonView();
    }
}
