package ar.edu.itba.paw.model;

public class Rule {
    private long id;
    private String name;

    public Rule(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
