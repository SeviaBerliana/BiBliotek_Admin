package com.bibliotek.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.R;
import com.bibliotek.adapter.PengumumanRecycleViewAdapter;
import com.bibliotek.adapter.UserRecycleViewAdaper;
import com.bibliotek.api.UserAPI;
import com.bibliotek.api.bukuAPI;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.databinding.FragmentHomeBinding;
import com.bibliotek.databinding.FragmentPengumumanBinding;
import com.bibliotek.model.Buku;
import com.bibliotek.model.Pengumuman;
import com.bibliotek.model.User;
import com.bibliotek.ui.maps.Maps;
import com.bibliotek.ui.pengumuman.EditPengumuman;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button btnNav;
    List<User> listUser = new ArrayList<>();
    View root;
    UserRecycleViewAdaper adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        root = binding.getRoot();

        getUser();

        btnNav = root.findViewById(R.id.btnNavigasi);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        adapter = new UserRecycleViewAdaper(root.getContext(), listUser, new UserRecycleViewAdaper.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete){
                    getUser();
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.userRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        binding.userRv.setHasFixedSize(true);
        binding.setData(adapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), Maps.class));
            }
        });
    }

    public void getUser() {
        RequestQueue queue = Volley.newRequestQueue(root.getContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, UserAPI.URL_INDEX
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listUser.isEmpty())
                        listUser.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String email               =  jsonObject.optString("email");
                        String nama                = jsonObject.optString("nama_lengkap");

                        User user = new User(nama, email);
                        listUser.add(user);
                    }
                    System.out.println("NIH" + listUser.size());
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}