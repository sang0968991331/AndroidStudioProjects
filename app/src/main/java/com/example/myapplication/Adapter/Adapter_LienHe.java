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

import com.example.myapplication.List_Lien_He;
import com.example.myapplication.R;
import com.example.myapplication.model.LienHe;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.myapplication.List_Lien_He.idLH;
import static com.example.myapplication.MainActivity.userId;

public class Adapter_LienHe extends RecyclerView.Adapter<Adapter_LienHe.MyViewHolder> {
    private List<LienHe> list;
    private Context context;
    private FirebaseFirestore firebaseFirestore;

    public Adapter_LienHe(List<LienHe> list, Context context, FirebaseFirestore firebaseFirestore) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }

    @NonNull
    @Override
    public Adapter_LienHe.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_LienHe.MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_lienhe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_LienHe.MyViewHolder holder, int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        final int itemPosition = position;
        final LienHe lienHe = list.get(itemPosition);

        holder.ngaygoi.setText(list.get(position).getNgaygoi());
        holder.giogoi.setText(list.get(position).getGiogoi());
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateNote(member);
//            }
//        });
//
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(lienHe.getId(), itemPosition,idLH);
            }
        });
//        holder.im_member.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("AAA","clicdk");
//                listLienHe( lienHe );
//            }
//        } );
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ngaygoi,giogoi;
        ImageView delete;
        ImageView cuocgoi;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ngaygoi= itemView.findViewById( R.id.tv_ngaygoi);
            giogoi= itemView.findViewById(R.id.tv_giogoi);
            cuocgoi=itemView.findViewById( R.id.btn_cuocgoi );
            delete = itemView.findViewById(R.id.iv_Delelienhe);
           // im_member=itemView.findViewById( R.id.imitem_cuocgoi );
            //  im_member=itemView.findViewById(R.id.imageView);
        }
    }
//    private void listLienHe(Member member) {
//        Intent intent = new Intent(context, List_Lien_He.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("nameLienhe", member.getName());
//        intent.putExtra("sdtLienhe", member.getSdt());
//
//        context.startActivity(intent);
//    }

    private void deleteNote(String id, final int position,String idmember) {

        firebaseFirestore.collection("user").document( userId ).collection( "Lismember").document(idmember).collection( "LienHe" )
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
