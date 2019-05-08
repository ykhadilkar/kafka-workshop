package io.pivotal;

public class User2 {

    private String name;

    public User2() {
        super();
    }

    public User2(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User2 [name=" + this.name + "]";
    }

}
