package com.mountblue.blog.entity;

import java.util.List;

public class Student {
    private String name;
    private List<Contact> contacts;

    public Student(String name, List<Contact> contacts) {
        this.name = name;
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
