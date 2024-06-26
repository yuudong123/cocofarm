package com.cocofarm.andapp.home;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_NOTICE;
import static com.cocofarm.andapp.common.CodeTable.PRODUCT_CATEGORY_DEVICE;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardVO;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.FragmentHomeBinding;
import com.cocofarm.andapp.product.ProductVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<ProductVO> p_list = new ArrayList<>();
    HomeNoticeAdapter n_adapter;
    HomePdAdapter p_adapter;
    HomeEventAdapter e_adapter;
    ArrayList<BoardVO> e_list, n_list;
    int currentPage = 0;
    Thread thread = new Thread(new PagerRunnable());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.tvCocomall.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        binding.tvNotice.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        // 이벤트 배너
        CommonConn e_conn = new CommonConn(getContext(), "/board/eventbanner.and");
        e_conn.addParam("board_category_cd", BOARD_CATEGORY_EVENT);
        e_conn.onExcute((isResult, data) -> {
            if (!isResult) return;

            e_list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
            }.getType());
            e_adapter = new HomeEventAdapter(e_list, getContext());
            binding.viewPager.setAdapter(e_adapter);
            binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            binding.viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
            binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    currentPage = position;
                    binding.tvNumber.setText(getString(R.string.viewpager2_banner, (currentPage % e_list.size()) + 1, e_list.size()));
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
            thread.start();
        });

        // 인기 제품
        CommonConn p_conn = new CommonConn(getContext(), "/selectProductList.and");
        p_conn.addParam("category_cd", PRODUCT_CATEGORY_DEVICE);
        p_conn.onExcute((isResult, data) -> {
            if (!isResult) return;

            p_list = new Gson().fromJson(data, new TypeToken<ArrayList<ProductVO>>() {
            }.getType());
            p_adapter = new HomePdAdapter(p_list, getContext());
            LinearLayoutManager p_manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
            binding.recvContents.setAdapter(p_adapter);
            binding.recvContents.setLayoutManager(p_manager);
        });

        binding.tvCocomall.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.binding.bottomNav.setSelectedItemId(R.id.shop);
        });


        // 공지사항
        CommonConn n_conn = new CommonConn(getContext(), "/board/eventbanner.and");
        n_conn.addParam("board_category_cd", BOARD_CATEGORY_NOTICE);
        n_conn.onExcute((isResult, data) -> {
            if (!isResult) return;

            n_list = new Gson().fromJson(data, new TypeToken<ArrayList<BoardVO>>() {
            }.getType());
            n_adapter = new HomeNoticeAdapter(n_list, getContext());
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            binding.recvBoardList.setAdapter(n_adapter);
            binding.recvBoardList.setLayoutManager(manager);
        });
        binding.tvNotice.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.binding.bottomNav.setSelectedItemId(R.id.board);
        });

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        thread.interrupt();
    }

    class PagerRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2500);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    Log.d("interupt", "interupt발생");
                    return;
                }
            }
        }

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                setPage();
                super.handleMessage(msg);
            }
        };

        public void setPage() {
            if (currentPage == e_list.size()) {
                currentPage = 0;
            }
            if (binding != null) {
                binding.viewPager.setCurrentItem(currentPage, true);
                currentPage++;
            }
        }
    }
}
                        