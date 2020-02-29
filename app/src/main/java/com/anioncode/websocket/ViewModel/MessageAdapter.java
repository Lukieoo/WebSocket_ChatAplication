package com.anioncode.websocket.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anioncode.websocket.R;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.HeroViewHolder> {

    Context mCtx;
    List<String>  List;
    String userName;


    public MessageAdapter(Context mCtx, List<String> List,String userName) {
        this.mCtx = mCtx;
        this.List = List;
        this.userName = userName;


    }
    public void setData(List<String> list) {

        if (list != null) {
            this.List.clear();
            this.List.addAll(list);
            System.out.println(list);
        } else {
            this.List = List;
            System.out.println(list);
        }
    }
    public  List<String> getData() {
      return this.List;
    }
    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_message, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        String tekst=List.get(position);
        String kindUser="";
        String message="";
        for(int i=0;i<tekst.length();i++){
            if(tekst.charAt(i)==':'){
                kindUser=tekst.substring(0,i);
                message=tekst.substring(i+1,tekst.length());
                break;
            }
        }
        if(kindUser.equals("first")){
            holder.RightLayout.setVisibility(View.GONE);
            holder.Textleft.setVisibility(View.GONE);
            holder.UserName.setText(message);
        }else {
            if (!kindUser.equals(userName)) {
                holder.RightLayout.setVisibility(View.GONE);
                holder.left.setVisibility(View.VISIBLE);

                holder.UserName.setText(kindUser + ":");
                holder.Textleft.setText(message);
            } else {
                holder.RightLayout.setVisibility(View.VISIBLE);
                holder.left.setVisibility(View.GONE);
                holder.TextRight.setText(message);
            }
        }


    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView UserName;
        TextView Textleft;
        TextView TextRight;
        CardView RightLayout;
        RelativeLayout left;

        public HeroViewHolder(View itemView) {
            super(itemView);

            UserName=itemView.findViewById(R.id.textNameleft) ;
            Textleft=itemView.findViewById(R.id.textLeft) ;
            TextRight=itemView.findViewById(R.id.textRight) ;
            RightLayout=itemView.findViewById(R.id.RightLayout) ;
            left=itemView.findViewById(R.id.left) ;

        }
    }

}