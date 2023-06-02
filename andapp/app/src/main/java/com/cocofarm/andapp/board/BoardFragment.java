package com.cocofarm.andapp.board;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("공지사항"));
        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("이벤트"));
        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("QnA"));
        binding.boardSelectMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                Fragment fragment = null;
                if (i==0){
                    fragment = new NoticeFragment();
                } else if (i==1) {
                    fragment = new EventFragment();
                } else if (i==2) {
                    fragment = new QnAFragment();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.containerBoard, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}