package com.everis.contacts.Model;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.everis.contacts.Model.Constants.TABLE_CONTACT;

@Entity(tableName = TABLE_CONTACT)
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String phone;


    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
