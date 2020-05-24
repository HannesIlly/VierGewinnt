package model;

public class Player {
    private String name;
    private boolean ready = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return this.ready;
    }
}
