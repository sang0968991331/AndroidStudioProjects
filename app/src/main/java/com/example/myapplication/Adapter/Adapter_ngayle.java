package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Info_member;
import com.example.myapplication.R;
import com.example.myapplication.model.NgayLe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.myapplication.MainActivity.userId;

public class Adapter_ngayle extends RecyclerView.Adapter<Adapter_ngayle.MyViewHolder> {
    private List<NgayLe> list;
    private Context context;
    private FirebaseFirestore firebaseFirestore;

    public Adapter_ngayle(List<NgayLe> list, Context context, FirebaseFirestore firebaseFirestore) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }

    @NonNull
    @Override
    public Adapter_ngayle.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_ngayle.MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_ngayle,parent,false));
    }




    @Override
    public void onBindViewHolder(@NonNull Adapter_ngayle.MyViewHolder holder, int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        final int itemPosition = position;
        final NgayLe ngayLe = list.get(itemPosition);
        holder.ngayle.setText(list.get(position).getNgayle());
        holder.noidung.setText(list.get(position).getNoidung());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ngayle,noidung;
        ImageView edit;
        ImageView delete;
        LinearLayout info;
        public ImageView im_member;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ngayle= itemView.findViewById( R.id.tv_ngayle);
            noidung= itemView.findViewById(R.id.tv_noidungle);
            // im_member=itemView.findViewById( R.id.img_item );
            //  im_member=itemView.findViewById(R.id.imageView);
        }
    }


}

