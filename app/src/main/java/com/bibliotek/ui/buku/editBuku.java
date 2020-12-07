package com.bibliotek.ui.buku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.R;
import com.bibliotek.api.bukuAPI;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.model.Buku;
import com.bibliotek.ui.pengumuman.EditPengumuman;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

public class editBuku extends AppCompatActivity {
    Button btnKembali, btnUnggah, btnSimpan;
    EditText txtJudul, txtPengarang, txtPenerbit;
    ImageView gambarBuku;
    String selected;
    private Bitmap bitmap;
    private Uri selectedImage = null;
    private String encoded = "";
    String tempID;

    private static final int PERMISSION_CODE = 1000;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buku);

        tempID = getIntent().getStringExtra("id");
        gambarBuku = findViewById(R.id.ivGambarBukuEdit);
        txtJudul = findViewById(R.id.edtEditJudulBuku);
        txtPengarang = findViewById(R.id.edtEditPengarang);
        txtPenerbit = findViewById(R.id.edtEditPenerbit);

        getBuku();

        btnSimpan = findViewById(R.id.btnEditBukuSubmit);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtJudul.getText().length()<1 || txtPengarang.getText().length()<1 || txtPenerbit.getText().length()<1)
                {
                    if(txtJudul.getText().length()<1)
                        txtJudul.setError("Judul tidak boleh kosong");
                    if(txtPengarang.getText().length()<1)
                        txtPengarang.setError("Pengarang tidak boleh kosong");
                    if(txtPenerbit.getText().length()<1)
                        txtPenerbit.setError("Penerbit tidak boleh kosong");
                }
                else
                {
                    String judul = txtJudul.getText().toString();
                    String pengarang = txtPengarang.getText().toString();
                    String penerbit = txtPenerbit.getText().toString();

                    editingBuku(judul, pengarang, penerbit);
                }
            }
        });

        btnUnggah = findViewById(R.id.btnUnggahEdit);
        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
                View view1 = layoutInflater.inflate(R.layout.pilih_media, null);

                final AlertDialog alertD = new AlertDialog.Builder(view.getContext()).create();

                Button btnKamera = (Button) view1.findViewById(R.id.btnKamera);
                Button btnGaleri = (Button) view1.findViewById(R.id.btnGaleri);

                btnKamera.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selected="kamera";
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                        {
                            if(getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA)==
                                    PackageManager.PERMISSION_DENIED ||
                                    getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                            PackageManager.PERMISSION_DENIED){
                                String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission,PERMISSION_CODE);
                            }
                            else{
                                openCamera();
                            }
                        }
                        else{
                            openCamera();
                        }
                        alertD.dismiss();
                    }
                });

                btnGaleri.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selected="galeri";
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                        {
                            if(getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                    PackageManager.PERMISSION_DENIED){
                                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission,PERMISSION_CODE);
                            }
                            else{
                                openGallery();
                            }
                        }
                        else{
                            openGallery();
                        }
                        alertD.dismiss();
                    }
                });

                alertD.setView(view1);
                alertD.show();
            }
        });

        btnKembali = findViewById(R.id.btnKembaliBuku);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    if(selected.equals("kamera"))
                        openCamera();
                    else
                        openGallery();
                }else{
                    Toast.makeText(this ,"Permision denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1)
        {
            selectedImage = data.getData();
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            gambarBuku.setImageBitmap(bitmap);
            bitmap = getResizedBitmap(bitmap, 512);
        }
        else if(resultCode == RESULT_OK && requestCode == 2)
        {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            gambarBuku.setImageBitmap(bitmap);
            bitmap = getResizedBitmap(bitmap, 512);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width / (float) height;

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void getBuku() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data buku");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, bukuAPI.URL_SHOW + tempID
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                JSONObject obj = response.optJSONObject("data");
                String id           = obj.optString("id");
                String judul        = obj.optString("judul");
                String pengarang    = obj.optString("pengarang");
                String penerbit     = obj.optString("penerbit");
                String gambar       = obj.optString("gambar");

                txtJudul.setText(judul);
                txtPengarang.setText(pengarang);
                txtPenerbit.setText(penerbit);

                if (!gambar.equalsIgnoreCase("null")) {
                    Glide.with(editBuku.this)
                            .load(bukuAPI.URL_IMAGE + gambar)
                            .into(gambarBuku);
                } else {
                    Glide.with(editBuku.this)
                            .load(bukuAPI.URL_IMAGE + "no-image.png")
                            .into(gambarBuku);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(editBuku.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void editingBuku(final String judul, final String pengarang, final String penerbit){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengedit data buku");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(PUT, bukuAPI.URL_UPDATE + tempID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.optString("message").equals("Update buku Success"))
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
                params.put("pengarang", pengarang);
                params.put("penerbit", penerbit);

                if (!encoded.equalsIgnoreCase("")) {
                    params.put("gambar", encoded);
                }

                return params;
            }
        };
        queue.add(stringRequest);
    }
}