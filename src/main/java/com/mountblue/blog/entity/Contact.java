package com.mountblue.blog.entity;

public class Contact {
    Integer phone;

    public Contact(Integer phone) {
        this.phone = phone;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phone=" + phone +
                '}';
    }
}
