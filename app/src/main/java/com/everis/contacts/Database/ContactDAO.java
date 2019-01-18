package com.everis.contacts.Database;

import com.everis.contacts.Model.Contact;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactDAO {

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAllContacts();

    @Query("SELECT * FROM contact_table ORDER BY name")
    LiveData<List<Contact>> getAllContacts();

}
