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

import com.example.myapplication.Info_member;
import com.example.myapplication.R;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.myapplication.MainActivity.userId;

public class Adapter_Sinhnhat extends RecyclerView.Adapter<Adapter_Sinhnhat.MyViewHolder> {
    private List<Member> list;
    private Context context;
    private FirebaseFirestore firebaseFirestore;

    public Adapter_Sinhnhat(List<Member> list, Context context, FirebaseFirestore firebaseFirestore) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }

    @NonNull
    @Override
    public Adapter_Sinhnhat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_Sinhnhat.MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_sinhnhat,parent,false));
    }




    @Override
    public void onBindViewHolder(@NonNull Adapter_Sinhnhat.MyViewHolder holder, int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        final int itemPosition = position;
        final Member member = list.get(itemPosition);
        holder.ngaysinh.setText(list.get(position).getNgaysinh());
        holder.hoten.setText(list.get(position).getName());
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateNote(member);
//            }
//        });
//
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteNote(member.getId(), itemPosition);
//            }
//        });
//        holder.im_member.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("AAA","clicdk");
//                updateNote( member );
//            }
//        } );
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ngaysinh,hoten;
        ImageView edit;
        ImageView delete;
        LinearLayout info;
        public ImageView im_member;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ngaysinh= itemView.findViewById( R.id.tv_sinhnhat);
            hoten= itemView.findViewById(R.id.tv_tensn);
           // im_member=itemView.findViewById( R.id.img_item );
            //  im_member=itemView.findViewById(R.id.imageView);
        }
    }
    private void updateNote(Member member) {
        Intent intent = new Intent(context, Info_member.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("infoname", member.getName());
        intent.putExtra("infoemail", member.getEmail());

        context.startActivity(intent);
    }

    private void deleteNote(String id, final int position) {

        firebaseFirestore.collection("user").document( userId ).collection( "Lismember")
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
