package com.cocofarm.andapp.mydevice;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.databinding.ItemDeviceListBinding;

public class MyDeviceAdapter extends RecyclerView.Adapter<MyDeviceAdapter.ViewHolder> {

//    ArrayList<DeviceVO> list;
    ItemDeviceListBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemDeviceListBinding.inflate(inflater, parent, false);

        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemDeviceListBinding binding;

        public ViewHolder(ItemDeviceListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
