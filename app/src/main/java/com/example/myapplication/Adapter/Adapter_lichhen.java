package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Info_lich_hen;
import com.example.myapplication.Info_member;
import com.example.myapplication.R;
import com.example.myapplication.model.LichHen;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.myapplication.MainActivity.userId;

public class Adapter_lichhen extends RecyclerView.Adapter<Adapter_lichhen.MyViewHolder>{

    private List<LichHen> list;
    private Context context;
    private FirebaseFirestore firebaseFirestore;

    public Adapter_lichhen(List<LichHen> list, Context context, FirebaseFirestore firebaseFirestore) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }

    @NonNull
    @Override
    public Adapter_lichhen.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_lichhen.MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_lichhen,parent,false));
    }


    public void onBindViewHolder(@NonNull Adapter_lichhen.MyViewHolder holder, int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        final int itemPosition = position;

        final LichHen lichHen = list.get(itemPosition);
        holder.noidung.setText(list.get(position).getNoidung());
        holder.ngayhen.setText(list.get(position).getNgayhen());
        holder.giohen.setText(list.get(position).getGiohen());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(lichHen);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AAA",lichHen.getId());
                delete_LH(lichHen.getId(), itemPosition);
            }
        });
        holder.im_member.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AAA","clicdk");
                updateNote( lichHen );
            }
        } );
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        public TextView noidung,ngayhen,giohen;
        ImageView edit;
        ImageView delete;
        LinearLayout info;
        public ImageView im_member;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noidung= itemView.findViewById( R.id.tv_noidung);
            ngayhen= itemView.findViewById(R.id.tv_ngayhen);
            giohen=itemView.findViewById( R.id.tv_giohen );
            edit = itemView.findViewById(R.id.ivEditLH);
            info=itemView.findViewById( R.id.lin_item );
            delete = itemView.findViewById(R.id.ivDeleteLH);
            im_member=itemView.findViewById( R.id.img_item );
            //  im_member=itemView.findViewById(R.id.imageView);
        }
    }
    private void updateNote(LichHen lichHen) {
        Intent intent = new Intent(context, Info_lich_hen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("infolichhen", lichHen.getId());
        intent.putExtra("infoemail", lichHen.getNgayhen());
        context.startActivity(intent);
    }

    private void delete_LH(String id, final int position) {
        firebaseFirestore.collection("user").document( userId ).collection( "Lichhen")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                        Toast.makeText(context, "Đã xóa! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
