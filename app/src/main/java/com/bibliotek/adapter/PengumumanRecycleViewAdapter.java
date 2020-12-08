package com.bibliotek.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bibliotek.BR;
import com.bibliotek.R;
import com.bibliotek.api.pengumumanAPI;
import com.bibliotek.databinding.ItemPengumumanBinding;
import com.bibliotek.model.Pengumuman;
import com.bibliotek.ui.pengumuman.EditPengumuman;
import com.bibliotek.ui.pengumuman.PengumumanFragment;
import com.bibliotek.ui.pengumuman.TambahPengumuman;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.AccessController;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;
import static com.bibliotek.BR.pengumuman;

public class PengumumanRecycleViewAdapter extends RecyclerView.Adapter<PengumumanRecycleViewAdapter.PengumumanViewHolder> {
    private Context context;
    private List<Pengumuman> pengumumanList;
    private PengumumanRecycleViewAdapter.deleteItemListener mListener;

    public PengumumanRecycleViewAdapter(Context context, List<Pengumuman> result,
                                        PengumumanRecycleViewAdapter.deleteItemListener mListener) {
        this.context = context;
        this.pengumumanList = result;
        this.mListener = mListener;
        notifyDataSetChanged();
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public PengumumanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemPengumumanBinding binding = ItemPengumumanBinding.inflate(layoutInflater,
                parent, false);

        return new PengumumanRecycleViewAdapter.PengumumanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PengumumanViewHolder holder, int position) {
        Pengumuman pengumuman = pengumumanList.get(position);
        holder.myBinding(pengumuman);

        holder.binding.btnEditPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), EditPengumuman.class);
                mIntent.putExtra("id", pengumuman.getIdPengumuman());
                ((Activity)context).startActivityForResult(mIntent, 10001);
            }
        });

        holder.binding.btnHapusPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(v.getContext())
                            .setTitle("Hapus Data")
                            .setMessage("Yakin ingin hapus?")
                            .setCancelable(true)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deletePengumuman(pengumuman.getIdPengumuman());
                                    Toast.makeText(v.getContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
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
        return pengumumanList.size();
    }


    public class PengumumanViewHolder extends RecyclerView.ViewHolder{
        ItemPengumumanBinding binding;

        public PengumumanViewHolder(@NonNull ItemPengumumanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void myBinding(Pengumuman pengumuman) {
            binding.setPengumuman(pengumuman);
            binding.executePendingBindings();
        }
    }

    public void deletePengumuman(String id){
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data pengumuman");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(DELETE, pengumumanAPI.URL_DELETE + id, new Response.Listener<String>() {
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
