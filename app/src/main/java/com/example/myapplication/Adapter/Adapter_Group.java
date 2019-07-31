package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.myapplication.R;
import com.example.myapplication.model.Group;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Adapter_Group  extends RecyclerView.Adapter<Adapter_Group.MyViewHolder> {
private List<Group> list;
private Context context;
private FirebaseFirestore firebaseFirestore;

public Adapter_Group(List<Group> list, Context context, FirebaseFirestore firebaseFirestore) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_group,parent,false));
        }



    @Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        holder.name.setText(list.get(position).getName_group());
        //holder.email.setText(list.get(position).getName_group());
        }

@Override
public int getItemCount() {
        return list.size();
        }

class  MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    //public ImageView im_member;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name= itemView.findViewById( R.id.edit_group);

       // email= itemView.findViewById(R.id.tv_Email);
        // im_member=itemView.findViewById( R.id.imageView_item );
        //  im_member=itemView.findViewById(R.id.imageView);
    }
}


}

