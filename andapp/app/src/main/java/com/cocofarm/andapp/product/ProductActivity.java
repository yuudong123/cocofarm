package com.cocofarm.andapp.product;

import static com.cocofarm.andapp.common.CommonVal.loginMember;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.common.CodeTable;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ActivityProductBinding;
import com.cocofarm.andapp.databinding.BtnSheetProductBinding;
import com.cocofarm.andapp.image.ImageDTO;
import com.cocofarm.andapp.image.ImageUtil;
import com.cocofarm.andapp.order.CartActivity;
import com.cocofarm.andapp.order.CartDTO;
import com.cocofarm.andapp.order.OrderActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    BtnSheetProductBinding bindingSheet;

    BottomSheetDialog bottomSheetDialog;
    boolean isSheetVisible = false;
    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProductVO productVO = (ProductVO) getIntent().getSerializableExtra("productVO");
        binding.tvProductAName.setText(productVO.getName());
        binding.tvProductAPrice.setText("￦" + productVO.getPrice());

        CommonConn conn = new CommonConn(this, "selectproductimagelist.and");
        conn.addParam("product_id", productVO.getProduct_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<ImageDTO> list = new Gson().fromJson(data, new TypeToken<ArrayList<ImageDTO>>() {
            }.getType());

            ProductImgAdapter adapter = new ProductImgAdapter(list);
            LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.recvProductImg.setAdapter(adapter);
            binding.recvProductImg.setLayoutManager(manager);
            Log.d("데이터emp", "onCreateView: " + list.size());
        });

        Fragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_content", productVO.getContent());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_detail_container, fragment)
                .commit();
        binding.productDetailMenu.addTab(binding.productDetailMenu.newTab().setText("상세정보"));
        binding.productDetailMenu.addTab(binding.productDetailMenu.newTab().setText("리뷰"));
        binding.productDetailMenu.addTab(binding.productDetailMenu.newTab().setText("QnA"));

        binding.productDetailMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                Fragment fragment = null;
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", productVO.getProduct_id());
                if (i == 0) {
                    fragment = new ProductDetailFragment();
                    bundle.putString("product_content", productVO.getContent());
                    fragment.setArguments(bundle);
                } else if (i == 1) {
                    fragment = new ProductReviewFragment();
                    fragment.setArguments(bundle);
                } else if (i == 2) {
                    fragment = new ProductQnAFragment();
                    bundle.putSerializable("productVO",productVO);
                    fragment.setArguments(bundle);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.product_detail_container, fragment)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        bindingSheet = BtnSheetProductBinding.inflate(getLayoutInflater(), null, false);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bindingSheet.getRoot());

        binding.btnProductBuy.setOnClickListener(v -> {
            toggleBottomSheet();
        });
        bindingSheet.btnDrop.setOnClickListener(v -> {
            toggleBottomSheet();
        });

        ImageUtil.load(bindingSheet.ivSheetProduct1, productVO.getFilename());
        bindingSheet.tvSheetProductName.setText(productVO.getName());
        bindingSheet.tvSheetOrderPrice.setText(productVO.getPrice()+"");

        bindingSheet.btnMinus.setOnClickListener(view -> {
            if (number > 0) {
                number--;
            } else {
                Toast.makeText(this, "수량은 0보다 작을 수 없습니다.", Toast.LENGTH_LONG).show();
            }
            bindingSheet.tvProductBuyAmount.setText(number + "");
            allPrice();
        });
        bindingSheet.btnPlus.setOnClickListener(view -> {
            number++;
            bindingSheet.tvProductBuyAmount.setText(number + "");
            allPrice();
        });

        bindingSheet.btnGoCart.setOnClickListener(v -> {
            if (number <= 0) {
                Toast.makeText(this, "수량을 추가해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            //사용자가 선택한 상품 장바구니에 넣기.
            CommonConn conn1 = new CommonConn(this,"insertcart.and");
            conn1.addParam("member_no",loginMember.getMember_no());
            conn1.addParam("product_id",productVO.getProduct_id());
            conn1.addParam("amount", Integer.parseInt(bindingSheet.tvProductBuyAmount.getText()+""));
            conn1.onExcute((isResult, data) -> {
                Log.d("장바구니", "onCreate: "+isResult);
            });


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("").setMessage("장바구니에 상품을 담았습니다. 장바구니로 이동하시겠습니까?").setCancelable(false)
                    .setPositiveButton("확인", (dialogInterface, i1) -> {
                        Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("취소", (dialogInterface, i1) -> {

                    }).create().show();
            // 장바구니로 이동하는 로직
        });
        bindingSheet.btnGoBuy.setOnClickListener(v -> {
            if (number <= 0) {
                Toast.makeText(this, "수량을 추가해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ProductActivity.this, OrderActivity.class);
            startActivity(intent);
        });
        allPrice();
    }

    // 바인딩시트 열고 닫기 토글 버튼으로 만들기.
    private void toggleBottomSheet() {
        if (isSheetVisible) {
            bottomSheetDialog.dismiss();
            isSheetVisible = false;
        } else {
            bottomSheetDialog.show();
            isSheetVisible = true;
        }
    }

    public void allPrice() {
        int value1 = number;
        int value2 = Integer.parseInt(bindingSheet.tvSheetOrderPrice.getText().toString());
        int result = value1 * value2;
        bindingSheet.tvAllPrice.setText(String.valueOf(result));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
