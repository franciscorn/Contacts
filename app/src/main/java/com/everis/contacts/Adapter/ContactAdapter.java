package com.everis.contacts.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.everis.contacts.Model.Contact;
import com.everis.contacts.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ContactHolder> implements Filterable {
    private List<Contact> contacts = new ArrayList<>();
    private List<Contact> contactListFull;
    private onItemClickListener listener;

    @Override
    public void submitList(@Nullable List<Contact> list) {
        this.contacts = list;
        contactListFull = new ArrayList<>(list);
        super.submitList(list);
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Contact item : contactListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contacts.clear();
            contacts.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public ContactAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getPhone().equals(newItem.getPhone());
        }
    };

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = getItem(position);
        holder.textViewName.setText(currentContact.getName());
        holder.textViewPhone.setText(currentContact.getPhone());
    }

    public Contact getContactAt(int position) {
        return getItem(position);
    }


    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewPhone;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_txt_name);
            textViewPhone = itemView.findViewById(R.id.item_txt_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }


    public interface onItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
