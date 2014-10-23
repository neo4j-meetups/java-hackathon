package org.neo4j.hackathons;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.views.View;

public class PersonView extends View
{
    private List<Person> people;

    public PersonView(List<Person> people) {
        super("person.ftl");
        this.people = people;
    }

    public List<Person> getPeople() {
        return people;
    }
}
