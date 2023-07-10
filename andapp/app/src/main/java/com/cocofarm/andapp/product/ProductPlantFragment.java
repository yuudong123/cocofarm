package com.cocofarm.andapp.product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentProductPlantBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProductPlantFragment extends Fragment {

    FragmentProductPlantBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductPlantBinding.inflate(inflater, container, false);

        loadProduct();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void loadProduct() {
        CommonConn conn = new CommonConn(getContext(), "selectProductList.and");
        conn.addParam("category_cd", CodeTable.PRODUCT_CATEGORY_PLANT);
        conn.onExcute((isResult, data) -> {
            ArrayList<ProductVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ProductVO>>() {
            }.getType());
            Log.d("데이터emp", "onCreateView: " + list.size());
            PlantAdapter adapter = new PlantAdapter(list, getContext());
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.recvProductPlant.setAdapter(adapter);
            binding.recvProductPlant.setLayoutManager(manager);
        });
    }
}