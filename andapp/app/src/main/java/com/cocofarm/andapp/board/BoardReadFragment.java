package com.cocofarm.andapp.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cocofarm.andapp.databinding.FragmentBoardReadBinding;
import com.cocofarm.andapp.image.ImageUtil;

public class BoardReadFragment extends Fragment {

    FragmentBoardReadBinding binding;
    private BoardVO boardVO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardReadBinding.inflate(inflater, container, false);
        boardVO = (BoardVO) getArguments().getSerializable("BoardVO");
        if (boardVO.getMainimage() != null) {
            binding.ivMainImage.setImageBitmap(ImageUtil.load(boardVO.getMainimage()));
        }
        binding.tvContent.setText(boardVO.getContent());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}