package com.cocofarm.andapp.board;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.cocofarm.andapp.common.CodeTable.MEMBER_TYPE_ADMIN;
import static com.cocofarm.andapp.common.CommonVal.HHmmss;
import static com.cocofarm.andapp.common.CommonVal.Md;
import static com.cocofarm.andapp.common.CommonVal.isToday;
import static com.cocofarm.andapp.common.CommonVal.loginMember;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocofarm.andapp.R;
import com.cocofarm.andapp.conn.CommonConn;
import com.cocofarm.andapp.databinding.ItemReplyBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {
    ItemReplyBinding binding;

    Context context;
    Activity activity;
    FragmentManager manager;

    int board_no;
    ArrayList<ReplyVO> list;

    public ReplyAdapter(Context context, Activity activity, FragmentManager manager, int board_no, ArrayList<ReplyVO> list) {
        this.context = context;
        this.activity = activity;
        this.manager = manager;
        this.board_no = board_no;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReplyBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ReplyVO replyVO = list.get(i);
        holder.binding.tvNickname.setText(replyVO.getNickname());
        holder.binding.tvContent.setText(replyVO.getContent());
        SimpleDateFormat sdf;
        if (isToday(replyVO.getRegdate())) {
            sdf = HHmmss;
        } else {
            sdf = Md;
        }
        holder.binding.tvRegdate.setText(sdf.format(replyVO.getRegdate()));
        holder.binding.btnReplyModifyConfirm.setOnClickListener(btn -> {
            if (binding.edtReplyModify.getText().toString().equals("")) {
                Toast.makeText(context, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            CommonConn conn = new CommonConn(null, "reply/updatereply.and");
            conn.addParam("reply_no", replyVO.getReply_no());
            conn.addParam("content", holder.binding.edtReplyModify.getText().toString());
            conn.onExcute((isResult, data) -> {
                if (isResult) {
                    Toast.makeText(context, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new BoardReadReplyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("board_no", board_no);
                    fragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
                }
            });
        });
        binding.btnReplyModifyCancel.setOnClickListener(btn -> {
            binding.itemReplyModifyBar.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edtReplyModify.getWindowToken(), 0);
            binding.edtReplyModify.setText("");
        });
        if (loginMember.getMember_no() == replyVO.getMember_no() || loginMember.getMember_type_cd() == MEMBER_TYPE_ADMIN) {
            holder.binding.btnSeemore.setVisibility(View.VISIBLE);
            holder.binding.btnSeemore.setOnClickListener(v -> {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.getMenuInflater().inflate(R.menu.reply_seemore, menu.getMenu());
                menu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menuReplySeemoreModify:
                            holder.binding.itemReplyModifyBar.setVisibility(View.VISIBLE);
                            break;
                        case R.id.menuReplySeemoreDelete:
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("댓글 삭제").setMessage("삭제하면 다시 복구할 수 없습니다. 정말 삭제하시겠습니까?").setCancelable(false)
                                    .setPositiveButton("확인", (dialogInterface, i1) -> {
                                        deleteReply(replyVO.getReply_no());
                                    })
                                    .setNegativeButton("취소", (dialogInterface, i1) -> {
                                    }).create().show();
                            break;
                        default:
                            break;
                    }
                    return false;
                });
                menu.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemReplyBinding binding;

        public ViewHolder(ItemReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    protected void deleteReply(int reply_no) {
        CommonConn conn = new CommonConn(null, "reply/deletereply.and");
        conn.addParam("reply_no", reply_no);
        conn.onExcute((isResult, data) -> {
            if (isResult) {
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        Fragment fragment = new BoardReadReplyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("board_no", board_no);
        fragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.containerBoardRead, fragment).commit();
    }
}
