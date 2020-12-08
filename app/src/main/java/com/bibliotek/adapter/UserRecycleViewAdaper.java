package com.bibliotek.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bibliotek.databinding.ItemPengumumanBinding;
import com.bibliotek.databinding.ItemUserBinding;
import com.bibliotek.model.Pengumuman;
import com.bibliotek.model.User;

import java.util.List;

public class UserRecycleViewAdaper extends RecyclerView.Adapter<UserRecycleViewAdaper.UserViewHolder>{
    private Context context;
    private List<User> userList;
    private UserRecycleViewAdaper.deleteItemListener mListener;

    public UserRecycleViewAdaper(Context context, List<User> result,
                                        UserRecycleViewAdaper.deleteItemListener mListener) {
        this.context = context;
        this.userList = result;
        this.mListener = mListener;
        notifyDataSetChanged();
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public UserRecycleViewAdaper.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemUserBinding binding = ItemUserBinding.inflate(layoutInflater,
                parent, false);

        return new UserRecycleViewAdaper.UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecycleViewAdaper.UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.myBinding(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder{
        ItemUserBinding binding;

        public UserViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void myBinding(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }
    }
}
