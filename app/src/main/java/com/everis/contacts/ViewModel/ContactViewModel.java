package com.everis.contacts.ViewModel;

import android.app.Application;

import com.everis.contacts.Database.ContactRepository;
import com.everis.contacts.Model.Contact;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;
    private LiveData<List<Contact>> allContacts;
    //Usar para compartilhar info entre Fragments
    // private MutableLiveData<List<Contact>> text = new MutableLiveData<>();

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    public void insert(Contact contact) {
        repository.insert(contact);
    }

    public void update(Contact contact) {
        repository.update(contact);
    }

    public void delete(Contact contact) {
        Contact contactDeleted = new Contact(contact.getName(), contact.getPhone());
        int position = contactDeleted.getId();

        repository.delete(contact);
    }

    public void deleteAllContacts() {
        repository.deleteAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }
}
