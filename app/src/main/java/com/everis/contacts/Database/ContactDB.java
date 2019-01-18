package com.everis.contacts.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.everis.contacts.Model.Contact;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.everis.contacts.Model.Constants.INSTANCE_NAME;

@Database(entities = {Contact.class}, version = 1, exportSchema = true)
public abstract class ContactDB extends RoomDatabase {

    private static ContactDB instance;

    public abstract ContactDAO contactDAO();

    //Sync para não acessar mais de um ao mesmo tempo
    public static synchronized ContactDB getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), ContactDB.class, INSTANCE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDAO contactDAO;

        private PopulateDbAsyncTask(ContactDB db) {
            contactDAO = db.contactDAO();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            contactDAO.insert(new Contact("Francisco Junior", "975049436"));
            contactDAO.insert(new Contact("Nayara", "988592757"));
            contactDAO.insert(new Contact("Antonio", "998538846"));
            contactDAO.insert(new Contact("Joao", "975049436"));
            contactDAO.insert(new Contact("Maria", "988592757"));
            contactDAO.insert(new Contact("Pedro", "998538846"));
            contactDAO.insert(new Contact("Gustavo", "975049436"));
            contactDAO.insert(new Contact("Carlos", "988592757"));
            contactDAO.insert(new Contact("Andre", "998538846"));
            contactDAO.insert(new Contact("Joao", "975049436"));
            contactDAO.insert(new Contact("Maria", "988592757"));
            contactDAO.insert(new Contact("Pedro", "998538846"));
            contactDAO.insert(new Contact("Gustavo", "975049436"));
            contactDAO.insert(new Contact("Carlos", "988592757"));
            contactDAO.insert(new Contact("Andre", "998538846"));
            return null;
        }
    }

}
