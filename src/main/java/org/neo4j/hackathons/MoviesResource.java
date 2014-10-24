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
        /* TODO: Execute a Cypher query to retrieve all nodes with
                 the `Person` label and pass them into the template
                 ordered by `name` property */
        return new PersonListView();
    }

    @GET

    @Path( "/person/{name}" )
    @Timed
    public PersonView person(@PathParam("name") String name) throws SQLException, ClassNotFoundException
    {
        /* TODO: Execute a Cypher query to retrieve all details for
                 a specific named person, including `name` and year
                 of birth (`born`) as well as a list of all movies
                 in which they have been involved;
        */
        return new PersonView();
    }

    @GET
    @Path( "/movie" )
    @Timed
    public MovieListView movies() throws SQLException, ClassNotFoundException
    {
        /* TODO: Execute a Cypher query to retrieve all nodes with
                 the `Movie` label and pass them into the template
                 ordered by `title` property */
        return new MovieListView();
    }

    @GET
    @Path( "/movie/{title}" )
    @Timed
    public MovieView movie(@PathParam("title") String title) throws SQLException, ClassNotFoundException
    {
        /* TODO: Execute a Cypher query to retrieve all details for
                 a specific titled movie, including `title`, year
                 of release (`released`), name of the director and
                 name of all actors in the movie's cast */
        return new MovieView();
    }
}
