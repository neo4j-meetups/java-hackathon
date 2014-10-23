package org.neo4j.hackathons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

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
    public PersonListView people() throws SQLException, ClassNotFoundException
    {
        return new PersonListView();
    }

    @GET
    @Path( "/person/{name}" )
    @Timed
    public PersonView person(@PathParam("name") String name) throws SQLException, ClassNotFoundException
    {
        return new PersonView();
    }

    @GET
    @Path( "/movie" )
    @Timed
    public MovieListView movies() throws SQLException, ClassNotFoundException
    {
        return new MovieListView();
    }

    @GET
    @Path( "/movie/{title}" )
    @Timed
    public MovieView movie(@PathParam("title") String title) throws SQLException, ClassNotFoundException
    {
                return new MovieView();

    }
}
