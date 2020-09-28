package com.mulutu.totos.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mulutu.totos.R;
import com.mulutu.totos.models.Category;
import com.mulutu.totos.models.Clinic;

import java.util.List;

public class TestCategoriesRecyclerAdapter extends RecyclerView.Adapter<TestCategoriesRecyclerAdapter.MyViewHolder> {
    private List<Category> categoryList;

    public TestCategoriesRecyclerAdapter(List<Category> list_of_categories ){
        this.categoryList = list_of_categories;
    }

    @Override
    public TestCategoriesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_card_view, parent, false);

        return new TestCategoriesRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestCategoriesRecyclerAdapter.MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.list_category_name.setText(category.getCategory_name());
        //holder.clinic_description.setText(clinic.getDescription());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView clinic_description;
        public TextView list_category_name;
        //public ImageView farmImage;

        public MyViewHolder(View view) {
            super(view);
            //farmImage = (ImageView)itemView.findViewById(R.id.list_farm_image);
            list_category_name = (TextView) view.findViewById(R.id.list_category_name);
            //clinic_description = (TextView) view.findViewById(R.id.clinic_description);
        }
    }
}
