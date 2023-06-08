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
    int selected=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("공지사항"));
        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("이벤트"));
        binding.boardSelectMenu.addTab(binding.boardSelectMenu.newTab().setText("QnA"));
        binding.boardSelectMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                loadTab(tab.getPosition());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadTab(selected);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void loadTab(int i) {
        selected = i;
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
}