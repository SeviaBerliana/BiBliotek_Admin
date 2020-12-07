package com.bibliotek.ui.pengumuman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.R;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.model.Pengumuman;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class EditPengumuman extends AppCompatActivity {
    Button btnKembali, btnEdit;
    EditText txtJudul, txtDeskripsi;
    String tempID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pengumuman);

        tempID = getIntent().getStringExtra("id");
        txtJudul = findViewById(R.id.edtEditJudulPengumuman);
        txtDeskripsi = findViewById(R.id.edtEditDeskripsiPengumuman);

        getPengumuman();

        btnKembali = findViewById(R.id.btnKembaliEditPengumuman);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEdit = findViewById(R.id.btnSubmitEditPengumuman);
        btnEdit.setOnClickListener(new View.OnClickListener() {
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

                    editPengumuman(judul, deskripsi);
                }
            }
        });
    }

    public void getPengumuman() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data pengumuman");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, pengumumanAPI.URL_SHOW + tempID
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                    JSONObject obj = response.optJSONObject("data");
                    String id                   = obj.optString("id");
                    String judul                = obj.optString("judul");
                    String deskripsi            = obj.optString("deskripsi");

                    txtJudul.setText(judul);
                    txtDeskripsi.setText(deskripsi);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditPengumuman.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void editPengumuman(final String judul, final String deskripsi){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengedit data pengumuman");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(PUT, pengumumanAPI.URL_UPDATE + tempID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("Update pengumuman Success"))
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