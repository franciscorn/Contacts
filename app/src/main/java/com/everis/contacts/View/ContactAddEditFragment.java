package com.everis.contacts.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.everis.contacts.Model.Contact;
import com.everis.contacts.R;
import com.everis.contacts.ViewModel.ContactViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.everis.contacts.Model.Constants.NO_ID;
import static com.everis.contacts.Model.Constants.TAG_ADD_EDIT;

public class ContactAddEditFragment extends Fragment {
    private ContactViewModel contactViewModel;
    private EditText editTextName;
    private EditText editTextPhone;
    private Bundle parametros;
    private TextView textViewTitle;
    private int contactId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_add, container, false);
        setHasOptionsMenu(true);

        showBackButton();

        parametros = getArguments();

        textViewTitle = view.findViewById(R.id.frag_contact_add_title);
        editTextName = view.findViewById(R.id.frag_contact_add_name);
        editTextPhone = view.findViewById(R.id.frag_contact_add_phone);

        return view;
    }

    private void showBackButton() {
        if (getActivity() instanceof ContactActivity) {
            ((ContactActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);

        if (parametros != null) {
            Contact contact = (Contact) parametros.getSerializable(TAG_ADD_EDIT);

            textViewTitle.setText(getString(R.string.edit_contact));
            editTextName.setText(contact.getName());
            editTextPhone.setText(contact.getPhone());
            contactId = contact.getId();

        } else {
            contactId = NO_ID;
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add_contact, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_contact:
                saveContact();
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveContact() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();


        if (name.trim().isEmpty() || phone.trim().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.warning_fields), Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                Contact contact = new Contact(name, phone);
                if (contactId == NO_ID) {
                    contactViewModel.insert(contact);
                } else {
                    contact.setId(contactId);
                    contactViewModel.update(contact);
                }

                Toast.makeText(getActivity(), getString(R.string.contact_saved), Toast.LENGTH_SHORT).show();

                getFragmentManager().popBackStack();
            } catch (Exception e) {
                Toast.makeText(getActivity(), getString(R.string.error_saving), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
