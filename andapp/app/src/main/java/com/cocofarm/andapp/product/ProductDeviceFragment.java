package com.cocofarm.andapp.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentProductDeviceBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProductDeviceFragment extends Fragment {
    //판매페이지에 관리기기 물품 판매 플래그먼트 페이지
    FragmentProductDeviceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentProductDeviceBinding.inflate(inflater, container, false);

        loadProduct();

        return binding.getRoot();
    }


    protected void loadProduct() {
        CommonConn conn = new CommonConn(getContext(), "selectProductList.and");
        conn.addParam("category_cd", CodeTable.PRODUCT_CATEGORY_DEVICE);
        conn.onExcute((isResult, data) -> {
            ArrayList<ProductVO> list = new Gson().fromJson(data,
                    new TypeToken<ArrayList<ProductVO>>(){}.getType());
            Log.d("데이터emp", "onCreateView: "+list.size());

            PlantAdapter adapter = new PlantAdapter(list,getContext());
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            binding.recvProductDevice.setAdapter(adapter);
            binding.recvProductDevice.setLayoutManager(manager);
        });
    }
}