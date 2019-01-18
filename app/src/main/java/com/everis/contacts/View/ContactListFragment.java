package com.everis.contacts.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.everis.contacts.Adapter.ContactAdapter;
import com.everis.contacts.Model.Contact;
import com.everis.contacts.R;
import com.everis.contacts.ViewModel.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.everis.contacts.Model.Constants.TAG_ADD_EDIT;

public class ContactListFragment extends Fragment {
    private ContactViewModel contactViewModel;
    private TextView date;
    private ContactAdapter adapter;
    private FloatingActionButton addContactButton;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        setHasOptionsMenu(true);

        showBackButton();

        recyclerView = view.findViewById(R.id.frag_contact_list_rv_contact);
        addContactButton = view.findViewById(R.id.frag_contact_list_add);
        date = view.findViewById(R.id.date);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);

        contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.submitList(contacts);
            }
        });

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callFragment(new ContactAddEditFragment());

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), getString(R.string.contact_deleted), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContactAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {

                ContactAddEditFragment contactAddEditFragment = new ContactAddEditFragment();

                Bundle parametros = new Bundle();
                parametros.putSerializable(TAG_ADD_EDIT, contact);
                contactAddEditFragment.setArguments(parametros);

                callFragment(contactAddEditFragment);
            }
        });
    }

    private void showBackButton() {
        if (getActivity() instanceof ContactActivity) {
            ((ContactActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }


    private void callFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                .replace(R.id.activity_contacts, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_contacts:
                contactViewModel.deleteAllContacts();
                Toast.makeText(getActivity(), getString(R.string.all_contacts_deleted), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
