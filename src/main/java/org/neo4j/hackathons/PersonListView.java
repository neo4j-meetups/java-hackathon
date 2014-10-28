package org.neo4j.hackathons;


import java.util.ArrayList;
import java.util.List;

public class PersonListView extends io.dropwizard.views.View
{
    private List<Person> people;

    public PersonListView( List<Person> people )
    {
        super( "person_list.ftl" );
        this.people = people;
    }

    public List<Person> getPeople() {
        return people;
    }
}
