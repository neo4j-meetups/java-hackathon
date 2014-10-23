package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.views.View;

public class PersonListView extends View
{
    private List<Person> people;

    public PersonListView( List<Person> people ) {
        super("person_list.ftl");
        this.people = people;
    }

    public List<Person> getPeople() {
        return people;
    }
}