package com.cocofarm.andapp.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cocofarm.andapp.MainActivity;
import com.cocofarm.andapp.R;
import com.cocofarm.andapp.board.BoardFragment;
import com.cocofarm.andapp.board.NoticeFragment;
import com.cocofarm.andapp.board.QnAWriteActivity;
import com.cocofarm.andapp.databinding.ActivityCsCenterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CsCenterActivity extends AppCompatActivity {

    ActivityCsCenterBinding binding;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCsCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnQna.setOnClickListener(v->{
            Intent intent = new Intent(CsCenterActivity.this, QnAWriteActivity.class);
            startActivity(intent);
        });

        binding.btnCall.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678"));
            startActivity(intent);
        });

        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

    }
}