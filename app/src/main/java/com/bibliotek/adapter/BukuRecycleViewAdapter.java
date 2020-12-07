package com.bibliotek.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.api.bukuAPI;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.databinding.ItemBukuBinding;
import com.bibliotek.model.Buku;
import com.bibliotek.ui.buku.editBuku;
import com.bibliotek.ui.pengumuman.EditPengumuman;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class BukuRecycleViewAdapter extends RecyclerView.Adapter<BukuRecycleViewAdapter.BukuViewHolder> {
    private Context context;
    private List<Buku> listBuku;
    private BukuRecycleViewAdapter.deleteItemListener mListener;

    public BukuRecycleViewAdapter(Context context, List<Buku> userList, BukuRecycleViewAdapter.deleteItemListener mListener) {
        this.context = context;
        this.listBuku = userList;
        this.mListener = mListener;
        notifyDataSetChanged();
    }
    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public BukuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemBukuBinding binding = ItemBukuBinding.inflate(layoutInflater,
                parent, false);

        return new BukuRecycleViewAdapter.BukuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuViewHolder holder, int position) {
        Buku buku = listBuku.get(position);
        holder.myBinding(buku);

        holder.binding.btnEditBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), editBuku.class);
                mIntent.putExtra("id", buku.getId());
                ((Activity)context).startActivityForResult(mIntent, 10009);
            }
        });

        holder.binding.btnDeleteBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(v.getContext())
                        .setTitle("Hapus Data")
                        .setMessage("Yakin ingin hapus?")
                        .setCancelable(true)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteBuku(buku.getId());
                                Toast.makeText(v.getContext(), "Berhasil Dihapus!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBuku.size();
    }


    public class BukuViewHolder extends RecyclerView.ViewHolder {
        ItemBukuBinding binding;

        public BukuViewHolder(@NonNull ItemBukuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void myBinding(Buku buku) {
            binding.setBuku(buku);
            binding.executePendingBindings();
        }
    }

    //Fungsi menghapus data buku
    public void deleteBuku(String id){
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data buku");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(DELETE, bukuAPI.URL_DELETE + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(context, obj.optString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    mListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }
}
