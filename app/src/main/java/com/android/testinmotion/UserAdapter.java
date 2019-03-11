package com.android.testinmotion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>
        implements Filterable  {
    private ArrayList<UserModel> userModels;
    private ArrayList<UserModel> userFilltersList;
    private UserFilters listener;
    private Context mContext;

    public UserAdapter(ArrayList<UserModel> userModels, UserFilters listener, Context mContext) {
        this.userModels = userModels;
        this.userFilltersList = userModels;
        this.listener = listener;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_adater_list,
                viewGroup,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int i) {
        holder.user.setText(userFilltersList.get(i).getLogin());
        Glide.with(holder.photo.getContext())
                .load(userFilltersList.get(i).getAvatar())
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return userFilltersList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView user;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo_user);
            user = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(view1 -> {
                // send selected contact in callback
                listener.onUserSelected(userFilltersList.get(getAdapterPosition()));
            });
        }


    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    userFilltersList = userModels;
                }else {
                    ArrayList<UserModel> filteredList = new ArrayList<>();
                    for (UserModel row : userModels){
                        if(row.getLogin().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
                    userFilltersList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userFilltersList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userFilltersList = (ArrayList<UserModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface UserFilters{
        void onUserSelected(UserModel userModel);
    }
}
