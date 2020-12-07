package com.bibliotek.ui.pengumuman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.R;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.model.Pengumuman;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class TambahPengumuman extends AppCompatActivity {
    private TextInputEditText txtJudul, txtDeskripsi;
    private Button btnAdd, btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengumuman);

        txtJudul = findViewById(R.id.edtTambahJudulPengumuman);
        txtDeskripsi = findViewById(R.id.edtTambahDeskripsiPengumuman);

        btnKembali = findViewById(R.id.btnKembaliTambahPengumuman);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd = findViewById(R.id.btnAddPengumuman);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtJudul.getText().length()<1 || txtDeskripsi.getText().length()<1)
                {
                    if(txtJudul.getText().length()<1)
                        txtJudul.setError("Judul tidak boleh kosong");
                    if(txtDeskripsi.getText().length()<1)
                        txtDeskripsi.setError("Deskripsi tidak boleh kosong");
                }
                else
                {
                    String judul = txtJudul.getText().toString();
                    String deskripsi = txtDeskripsi.getText().toString();

                    addPengumuman(judul, deskripsi);
                }
            }
        });
    }

    public void addPengumuman(final String judul, final String deskripsi){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan data pengumuman");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, pengumumanAPI.URL_STORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("Add pengumuman Success"))
                    {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("judul", judul);
                params.put("deskripsi", deskripsi);

                return params;
            }
        };
        queue.add(stringRequest);
    }
}