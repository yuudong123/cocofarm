package com.cocofarm.andapp.board;

import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_EVENT;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_NOTICE;
import static com.cocofarm.andapp.common.CodeTable.BOARD_CATEGORY_QNA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.databinding.FragmentBoardBinding;
import com.google.android.material.tabs.TabLayout;

public class BoardFragment extends Fragment {

    FragmentBoardBinding binding;
    private int selected = 0;
    public static CriteriaDTO cri = new CriteriaDTO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("공지사항"));
        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("이벤트"));
        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("QnA"));
        binding.boardSelectMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selected = tab.getPosition();
                cri.setPage(1);
                loadTab();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selected = tab.getPosition();
                cri.setPage(1);
                loadTab();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        cri.setPage(1);
        loadTab();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void loadTab() {
        Fragment fragment = null;
        if (selected == 0) {
            cri.setCode(BOARD_CATEGORY_NOTICE);
            fragment = new NoticeFragment();
        } else if (selected == 1) {
            cri.setCode(BOARD_CATEGORY_EVENT);
            fragment = new EventFragment();
        } else if (selected == 2) {
            cri.setCode(BOARD_CATEGORY_QNA);
            fragment = new QnAFragment();
        }
        if(fragment!=null) {
            getChildFragmentManager().beginTransaction().replace(R.id.containerBoard, fragment).commit();
        }

    }
}