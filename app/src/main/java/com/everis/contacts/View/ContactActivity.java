package com.everis.contacts.View;

import android.os.Bundle;
import android.view.Menu;

import com.everis.contacts.R;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_contacts, new ContactListFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
