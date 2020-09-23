package com.mulutu.totos.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mulutu.totos.R;
import com.mulutu.totos.models.Clinic;

import java.util.List;

public class ClinicsRecyclerAdapter extends RecyclerView.Adapter<ClinicsRecyclerAdapter.MyViewHolder> {
    private List<Clinic> clinicsList;

    public ClinicsRecyclerAdapter(List<Clinic> list_of_clinics) {
        this.clinicsList = list_of_clinics;
    }

    @Override
    public ClinicsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clinic_card_view, parent, false);

        return new ClinicsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClinicsRecyclerAdapter.MyViewHolder holder, int position) {
        Clinic clinic = clinicsList.get(position);
        holder.list_clinic_name.setText(clinic.getClinic_name());
        holder.clinic_description.setText(clinic.getDescription());
    }

    @Override
    public int getItemCount() {
        return clinicsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView clinic_description;
        public TextView list_clinic_name;
        //public ImageView farmImage;

        public MyViewHolder(View view) {
            super(view);
            //farmImage = (ImageView)itemView.findViewById(R.id.list_farm_image);
            list_clinic_name = (TextView) view.findViewById(R.id.list_clinic_name);
            clinic_description = (TextView) view.findViewById(R.id.clinic_description);
        }
    }
}
