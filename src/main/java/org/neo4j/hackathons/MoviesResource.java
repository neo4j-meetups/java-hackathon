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
        List<Person> people  = new ArrayList<>(  );
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(
                    "MATCH (p:Person) " +
                    "RETURN p " +
                    "ORDER BY p.name" );
            while(rs.next()) {
                Map<String, String> person = (Map<String, String>) rs.getObject( "p" );
                people.add( new Person(person.get( "name" )) );
            }
        }

        return new PersonListView( people );
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

        String query = "MATCH (p:Person {name: {1} }) " +
                       "OPTIONAL MATCH (p)-[:ACTED_IN]->(m) " +
                       "WITH p, m ORDER BY m.title " +
                       "WITH p, COLLECT(m.title) AS moviesActedIn " +
                       "OPTIONAL MATCH (p)-[:DIRECTED]->(m) " +
                       "WITH p, COLLECT(m.title) AS moviesDirected, moviesActedIn " +
                       "RETURN p.name AS name, p.born AS born, moviesDirected, moviesActedIn " +
                       "LIMIT 1";

        try(PreparedStatement stmt = connection.prepareStatement( query )) {
            stmt.setString( 1, name );
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                List<Movie> moviesDirected = extractMovies( rs, "moviesDirected" );
                List<Movie> moviesActedIn = extractMovies( rs, "moviesActedIn" );


                String collaboratorsQuery =
                        "MATCH (p:Person {name: {1} })-[:WORKED_WITH*2]-(potentialCollaborator) " +
                        "WHERE (p)-[:WORKED_WITH]-(potentialCollaborator) AND potentialCollaborator <> p " +
                        "RETURN potentialCollaborator.name AS name, COUNT(*) AS times " +
                        "ORDER BY times DESC";

                List<Collaborator> collaborators = new ArrayList<>(  );
                try(PreparedStatement cstmt = connection.prepareStatement( collaboratorsQuery )) {
                    cstmt.setString(1, name);
                    ResultSet crs = cstmt.executeQuery();

                    while(crs.next()) {
                        Person person = new Person( crs.getString( "name" ) );
                        Collaborator collaborator = new Collaborator( person, crs.getInt( "times" ) );
                        collaborators.add( collaborator ) ;
                    }
                }

                return new PersonView(
                        rs.getString( "name" ),
                        rs.getInt( "born" ),
                        moviesActedIn,
                        moviesDirected,
                        collaborators);

            } else {
                throw new RuntimeException( "cannot find person" );
            }
        }

    }

    private List<Movie> extractMovies( ResultSet rs, String column ) throws SQLException
    {
        List<Movie> moviesDirected = new ArrayList<>(  );
        for ( String title : (List<String>) rs.getObject( column ) )
        {
            moviesDirected.add( new Movie( title ) );
        }
        return moviesDirected;
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
