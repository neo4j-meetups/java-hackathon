package org.neo4j.hackathons;

public class Comment
{

    private String name;
    private String text;
    private int date;

    public Comment( String name, String text, int date )
    {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public String getText()
    {
        return text;
    }

    public int getDate()
    {
        return date;
    }
}
