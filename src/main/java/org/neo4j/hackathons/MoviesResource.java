package org.neo4j.hackathons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    public PersonListView person() throws SQLException, ClassNotFoundException
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
    @Path( "/movie" )
    @Timed
    public MovieListView movie() throws SQLException, ClassNotFoundException
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
}
