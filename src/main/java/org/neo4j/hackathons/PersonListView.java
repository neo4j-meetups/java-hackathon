package org.neo4j.hackathons;


import java.util.ArrayList;
import java.util.List;

public class PersonListView extends io.dropwizard.views.View
{

    protected PersonListView(  )
    {
        super( "person_list.ftl" );
    }

    public List<Person> getPeople() {
        return new ArrayList<>(  );
    }
}
