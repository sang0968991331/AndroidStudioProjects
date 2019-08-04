package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.CallBackGroup;
import com.example.myapplication.R;
import com.example.myapplication.model.Group;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Adapter_Group  extends RecyclerView.Adapter<Adapter_Group.MyViewHolder> {
private List<Group> list;
private Context context;
private FirebaseFirestore firebaseFirestore;
CallBackGroup callBackGroup;


public Adapter_Group(List<Group> list, Context context, FirebaseFirestore firebaseFirestore,CallBackGroup callBackGroup) {
        this.list = list;
        this.context = context;
        this.firebaseFirestore = firebaseFirestore;
        this.callBackGroup = callBackGroup;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder( LayoutInflater.from(context).inflate( R.layout.item_group,parent,false));
        }



    @Override
public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //holder.im_member.setId(list.get(position).getImg()  );
        holder.name.setText(list.get(position).getName_group());
        holder.img_group.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackGroup.NameGroup( list.get( position ).getName_group() );




            }
        } );
        //holder.email.setText(list.get(position).getName_group());
        }

@Override
public int getItemCount() {
        return list.size();
        }

class  MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView img_group;
    //public ImageView im_member;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name= itemView.findViewById( R.id.edit_group);
        img_group=itemView.findViewById( R.id.img_group );

       // email= itemView.findViewById(R.id.tv_Email);
        // im_member=itemView.findViewById( R.id.imageView_item );
        //  im_member=itemView.findViewById(R.id.imageView);
    }
}


}

