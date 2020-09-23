package com.mulutu.totos.models;

public class Child {
    String id;
    String first_name;
    String last_name;
    String parent_id;

    public Child() {
    }

    public String get_id() {
        return id;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Child{" +
                " id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
