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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.R;
import com.bibliotek.api.bukuAPI;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.model.Buku;
import com.bibliotek.model.Pengumuman;
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
    ImageView imgBuku1, imgBuku2;
    TextView tvJudul, tvDeskripsi;
    List<Pengumuman> listpengumuman = new ArrayList<>();
    View root;
    List<Buku> bukuList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        btnNav = root.findViewById(R.id.btnNavigasi);
        imgBuku1 = root.findViewById(R.id.buku1);
        imgBuku2 = root.findViewById(R.id.buku2);

        getBuku();

        tvJudul = root.findViewById(R.id.judulPengumumanBeranda);
        tvDeskripsi = root.findViewById(R.id.isiPengumumanBeranda);

        getPengumuman();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

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

    public void getPengumuman() {
        RequestQueue queue = Volley.newRequestQueue(root.getContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, pengumumanAPI.URL_INDEX
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listpengumuman.isEmpty())
                        listpengumuman.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String id                   =  jsonObject.optString("id");
                        String judul                = jsonObject.optString("judul");
                        String deskripsi            = jsonObject.optString("deskripsi");

                        Pengumuman pengumuman = new Pengumuman(id, judul, deskripsi);
                        listpengumuman.add(pengumuman);
                    }

                    int temp = listpengumuman.size();
                    String judul = listpengumuman.get(temp-1).getJudul();
                    String deskripsi = listpengumuman.get(temp-1).getDeskripsi();
                    tvJudul.setText(judul);
                    tvDeskripsi.setText(deskripsi);

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

    public void getBuku() {
        RequestQueue queue = Volley.newRequestQueue(root.getContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, bukuAPI.URL_INDEX
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!bukuList.isEmpty())
                        bukuList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String id                   =  jsonObject.optString("id");
                        String judul                = jsonObject.optString("judul");
                        String pengarang            = jsonObject.optString("pengarang");
                        String gambar               = jsonObject.optString("gambar");
                        String penerbit               = jsonObject.optString("penerbit");

                        if (gambar.equalsIgnoreCase("null")) {
                            gambar = "no-image.png";
                        }

                        Buku buku = new Buku(id, gambar, judul, pengarang, penerbit);
                        bukuList.add(buku);
                    }

                    int getUkuranBuku = bukuList.size();
                    String gambar1 = bukuList.get(getUkuranBuku-2).getImgURL();
                    String gambar2 = bukuList.get(getUkuranBuku-1).getImgURL();
                    Glide.with(root.getContext())
                            .load(bukuAPI.URL_IMAGE + gambar1)
                            .into(imgBuku1);
                    Glide.with(root.getContext())
                            .load(bukuAPI.URL_IMAGE + gambar2)
                            .into(imgBuku2);

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