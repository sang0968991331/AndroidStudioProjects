package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.myapplication.Add_LichHen;
import com.example.myapplication.Info_member;
import com.example.myapplication.List_Lien_He;
import com.example.myapplication.R;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.MainActivity.userId;

public class Adapter_cuocgoi extends RecyclerView.Adapter<Adapter_cuocgoi.MyViewHolder> {
    private List<Member> list;
    private Context context;
    private FirebaseFirestore firebaseFirestore;

    public Adapter_cuocgoi(List<Member> list, Context context, FirebaseFirestore firebaseFirestore) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }

    @NonNull
    @Override
    public Adapter_cuocgoi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_cuocgoi.MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_cuocgoi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_cuocgoi.MyViewHolder holder, int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        final int itemPosition = position;
        final Member member = list.get( itemPosition );
        holder.name.setText( list.get( position ).getName() );
        holder.email.setText( list.get( position ).getSdt() );
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
        holder.cuocgoi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goi_dien( member.getId() );
                goi( member );
            }
        } );

        holder.im_member.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AAA","clicdk");
                listLienHe( member );
            }
        } );
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,email;
        ImageView edit;
        ImageView delete;
        ImageView cuocgoi;
        LinearLayout info;
        public ImageView im_member;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById( R.id.tv_Name);
            email= itemView.findViewById(R.id.tv_Email);
            cuocgoi=itemView.findViewById( R.id.btn_cuocgoi );
            edit = itemView.findViewById(R.id.ivEdit);
            im_member=itemView.findViewById( R.id.imitem_cuocgoi );
            //  im_member=itemView.findViewById(R.id.imageView);
        }
    }
    private void listLienHe(Member member) {
        Intent intent = new Intent(context, List_Lien_He.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idLienhe", member.getId());
        intent.putExtra("nameLienhe", member.getName());
        intent.putExtra("sdtLienhe", member.getSdt());
        context.startActivity(intent);
    }
    private void goi(Member member) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData( Uri.parse("tel:"+member.getSdt()));
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
    private void goi_dien(String id){
        Calendar calendar = Calendar.getInstance();
       String ngay =  calendar.get( Calendar.DATE ) + "/" +(calendar.get( Calendar.MONTH ) + 1)+"/"+calendar.get(Calendar.YEAR);
        String gio = calendar.get(Calendar.HOUR) +":"+ calendar.get(Calendar.MINUTE) +":"+ calendar.get(Calendar.SECOND);
                        Map<String, String> userMap = new HashMap<>();
                        userMap.put( "ngay", ngay );
                        userMap.put( "gio", gio );
                        firebaseFirestore.collection("user").document( userId ).collection( "Lismember").document(id).collection( "LienHe" ).add( userMap ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
//                                    Intent intent = new Intent( Add_member.this, Info_member.class );
//                                    intent.putExtra( "name",hoten );
//                                    intent.putExtra( "email",email );
//                                    intent.putExtra( "sdt",sdt );
//                                    intent.putExtra( "ngay sinh",ngaysinh );
//                                    intent.putExtra( "dia chi",diachi );
//                                    intent.putExtra( "cong ty",congty );
//                                    intent.putExtra( "thong tin khac",thongtin );
//                                    startActivity( intent );

                             //   finish();
                            }
                        } ).addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String error = e.getMessage();
                            }
                        } );
                    }
        }



