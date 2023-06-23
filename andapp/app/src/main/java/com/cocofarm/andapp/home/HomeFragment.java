package com.cocofarm.andapp.home;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardFragment;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.board.NoticeFragment;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentHomeBinding;
import com.cocofarm.andapp.product.ProductFragment;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import lombok.val;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<ProductVO> p_list = new ArrayList<>();
    HomeNoticeAdapter n_adapter;
    HomePdAdapter p_adapter;
    HomeEventAdapter e_adapter;
    FragmentTransaction transaction;
    ArrayList<BoardVO> e_list, n_list;
    int currentPage = 0;

    final long DELAY_MS = 3000;
    final long PERIOD_MS = 3000;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.tvCocomall.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        binding.tvNotice.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        // 이벤트 배너
        CommonConn e_conn = new CommonConn(getContext(), "/board/eventbanner.and");
        e_conn.addParam("board_category_cd", 204);
        e_conn.onExcute((isResult, data) -> {
                if (!isResult) {
                    return;
                } else {
                    e_list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>(){}.getType());
                    e_adapter = new HomeEventAdapter(e_list, getContext());
                    binding.viewPager.setAdapter(e_adapter);
                    binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


//                    binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//                        @Override
//                        public void onPageSelected(int position) {
//                            super.onPageSelected(position);
//
//                        }
//                    });
//                    binding.viewPager.setOffscreenPageLimit(3);
//                    float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//                    float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//                    binding.viewPager.setPageTransformer((page, position) -> {
//                        float myOffset = position * -(2 * pageOffset + pageMargin);
//                        if (position < -1) {
//                            page.setTranslationX(-myOffset);
//                        } else if (position <= 1) {
//                            float scaleFactor = Math.max(0.85f, 1 - Math.abs(position));
//                            page.setAlpha(scaleFactor);
//                            page.setScaleY(scaleFactor);
//                            page.setTranslationX(myOffset);
//                        } else {
//                            page.setAlpha(0);
//                            page.setTranslationX(myOffset);
//                        }
//
//                    });
                }
            });
        // timer = new Timer();

        // timer.schedule(new TimerTask() {
        //     @Override
        //     public void run() {
        //         try {
        //             if (currentPage == e_list.size()) {
        //                 currentPage = 0;
        //             }
        //             binding.viewPager.setCurrentItem(currentPage, true);
        //             currentPage++;

        //         } catch (Exception e) {
        //             Log.e("오류", "run: ", e);
        //             Log.e("오류", "run: " + e.getMessage(), e);
        //         }
        //     }
        // }, DELAY_MS, PERIOD_MS);

//        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                // 사용자가 수동으로 슬라이드를 넘길 때마다 현재 아이템의 인덱스를 업데이트합니다.
//                currentPage = position;
//                binding.tvNumber.setText("aaaaa" + getString(R.string.viewpager2_banner,position + 1, e_list.size()));
//            }
//        };
//        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback);


        // 인기 제품
        CommonConn p_conn = new CommonConn(getContext(), "/selectProductList.and");
        p_conn.addParam("category_cd", 401);
        p_conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            } else {
                p_list = new Gson().fromJson(data, new TypeToken<ArrayList<ProductVO>>() {
                }.getType());
                p_adapter = new HomePdAdapter(p_list, getContext());
                LinearLayoutManager p_manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.recvContents.setAdapter(p_adapter);
                binding.recvContents.setLayoutManager(p_manager);
            }
        });

        binding.tvCocomall.setOnClickListener(v->{
          MainActivity activity =  (MainActivity) getActivity();
          activity.binding.bottomNav.setSelectedItemId(R.id.shop);
        });


        // 공지사항
        CommonConn n_conn = new CommonConn(getContext(), "/board/eventbanner.and");
        n_conn.addParam("board_category_cd", 202);
        n_conn.onExcute((isResult, data) -> {
            if (!isResult) {
                return;
            } else {
                n_list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
                }.getType());
                n_adapter = new HomeNoticeAdapter(n_list, getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                binding.recvBoardList.setAdapter(n_adapter);
                binding.recvBoardList.setLayoutManager(manager);
            }
        });
        binding.tvNotice.setOnClickListener(v->{
            MainActivity activity =  (MainActivity) getActivity();
            activity.binding.bottomNav.setSelectedItemId(R.id.board);
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
                        