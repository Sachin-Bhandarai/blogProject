package com.mountblue.blog.entity;

import java.util.List;

public class Student {
    private String name="sachin";
    private String nickName="sagar";
    private int age;

    {
        age = 23;
    }

    private List<Contact> contacts;

    public Student() {
        System.out.println("student creatd");

    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student(String name, int age, List<Contact> contacts) {
        this.name = name;
        this.age = age;
        this.contacts = contacts;
    }

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
