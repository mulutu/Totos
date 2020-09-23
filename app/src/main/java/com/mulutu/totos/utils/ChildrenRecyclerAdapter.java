package com.mulutu.totos.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mulutu.totos.R;
import com.mulutu.totos.models.Child;

import java.util.List;

public class ChildrenRecyclerAdapter extends RecyclerView.Adapter<ChildrenRecyclerAdapter.MyViewHolder> {

    private List<Child> childrenList;

    public ChildrenRecyclerAdapter(List<Child> childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public ChildrenRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_card_view, parent, false);

        return new ChildrenRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildrenRecyclerAdapter.MyViewHolder holder, int position) {
        Child child = childrenList.get(position);
        holder.listFarmName.setText(child.getFirst_name());
        //holder.listFarmDescription.setText(farm.getDescription());
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView listFarmDescription;
        public TextView listFarmName;
        //public ImageView farmImage;

        public MyViewHolder(View view) {
            super(view);
            //farmImage = (ImageView)itemView.findViewById(R.id.list_farm_image);
            listFarmName = (TextView) view.findViewById(R.id.list_farm_name);
            //listFarmDescription = (TextView) view.findViewById(R.id.list_farm_description);
        }
    }
}
