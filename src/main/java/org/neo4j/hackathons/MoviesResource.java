package org.neo4j.hackathons;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.util.UriEncoder;

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
        List<Person> people = new ArrayList<>(  );
        try(Statement stmt = connection.createStatement())
        {
            ResultSet rs = stmt.executeQuery("MATCH (p:Person) return p");
            while(rs.next())
            {
                Map<String, String> person = (Map<String, String>) rs.getObject( "p" );
                people.add(new Person(person.get("name")));
            }
        }

        return new PersonListView(people);
    }

    @GET
    @Path( "/person/{name}" )
    @Timed
    public PersonView person(@PathParam("name") String name) throws SQLException, ClassNotFoundException
    {
        String query =
                "    MATCH (p:Person) WHERE p.name = {1}\n" +
                "    OPTIONAL MATCH (p)-[:ACTED_IN]->(ma:Movie)\n" +
                "    OPTIONAL MATCH (p)-[:DIRECTED]->(md:Movie)\n" +
                "    RETURN p.name AS name, p.born AS born,\n" +
                "           collect(DISTINCT ma.title) AS movies_acted_in,\n" +
                "           collect(DISTINCT md.title) AS movies_directed";

        try(PreparedStatement stmt = connection.prepareStatement( query ))
        {
            stmt.setString( 1, name );

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                int born = rs.getInt( "born" );
                List<Movie> moviesActedIn = extractMovies( rs, "movies_acted_in" );
                List<Movie> moviesDirected = extractMovies( rs, "movies_directed" );

                return new PersonView(name, born, moviesActedIn, moviesDirected);
            }
        }

        throw new RuntimeException( "No person found - should be 404" );
    }

    @GET
    @Path( "/movie" )
    @Timed
    public MovieListView movies() throws SQLException, ClassNotFoundException
    {
        List<Movie> movies = new ArrayList<>(  );
        try(Statement stmt = connection.createStatement())
        {
            ResultSet rs = stmt.executeQuery("MATCH (m:Movie) return m");
            while(rs.next())
            {
                Map<String, String> person = (Map<String, String>) rs.getObject( "m" );
                movies.add( new Movie( person.get( "title" ) ) );
            }
        }

        return new MovieListView(movies);
    }

    @GET
    @Path( "/movie/{title}" )
    @Timed
    public MovieView movie(@PathParam("title") String title) throws SQLException, ClassNotFoundException
    {
        String query =
                "    MATCH (m:Movie) WHERE m.title = {1}\n" +
                        "    OPTIONAL MATCH (m)<-[:ACTED_IN]-(a:Person)\n" +
                        "    OPTIONAL MATCH (m)<-[:DIRECTED]-(d:Person)\n" +
                        "    RETURN m.title AS title, m.released AS released,\n" +
                        "           collect(a.name) AS actors, d.name AS director";

        try(PreparedStatement stmt = connection.prepareStatement( query ))
        {
            stmt.setString( 1, title );
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                int released = rs.getInt( "released" );
                String director = rs.getString( "director" );
                List<Person> actors = extractActors( rs );

                String commentsQuery = "MATCH (m:Movie)-[:COMMENT]->(r:Comment) WHERE m.title = {1}\n" +
                        "    RETURN r.name AS name, r.text AS text, r.date AS date\n" +
                        "    ORDER BY date DESC";

                try(PreparedStatement commentsStmt = connection.prepareStatement( commentsQuery )) {
                    commentsStmt.setString( 1, title );

                    ResultSet commentsResultSet = commentsStmt.executeQuery();

                    List<Comment> comments = new ArrayList<>(  );
                    while(commentsResultSet.next()) {
                        Comment comment = new Comment( commentsResultSet.getString( "name" ),
                                commentsResultSet.getString( "text" ), commentsResultSet.getLong( "date" ) );
                        comments.add( comment );
                    }
                    return new MovieView(title, released, actors, director, comments);
                }


            } else {
                throw new WebApplicationException(404);
            }
        }
    }

    private List<Person> extractActors( ResultSet rs ) throws SQLException
    {
        List<Person> actors = new ArrayList<>(  );

        for ( String actorName : (List<String>) rs.getObject( "actors" ) )
        {
            actors.add(new Person( actorName ));
        }
        return actors;
    }

    @POST
    @Path( "/movie/comment" )
    @Timed
    public MovieView addComment(@FormParam("title") String title, @FormParam("name") String name, @FormParam("text") String text) throws SQLException, ClassNotFoundException
    {
        String query =
                "MATCH (m:Movie) WHERE m.title = {1}\n" +
                        "    WITH m\n" +
                        "    CREATE (m)-[:COMMENT]->(r:Comment {name:{2},text:{3},date:{4}})";

        try(PreparedStatement stmt = connection.prepareStatement( query ))
        {
            stmt.setString( 1, title );
            stmt.setString( 2, name );
            stmt.setString( 3, text );
            stmt.setLong( 4, System.currentTimeMillis() );

            stmt.executeQuery();

            URI uri = URI.create( UriEncoder.encode( "/movie/" + title ) );
            Response response = Response.seeOther(uri).build();
            throw new WebApplicationException(response);
        }
    }

    private ArrayList<Movie> extractMovies( ResultSet rs, String movies_acted_in ) throws SQLException
    {
        List<String> moviesActedIn = (List<String>) rs.getObject( movies_acted_in );
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for ( String movieName : moviesActedIn )
        {
            movies.add( new Movie(movieName) );
        }
        return movies;
    }
}
