package ar.edu.itba.paw.model;

import java.util.Collection;

public class Career {
    private long id;
    private String name;
    private Collection<User> users;

    public Career(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Career(long id, String name, Collection<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
