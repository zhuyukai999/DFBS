package gdut.edu.datingforballsports.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.view.adapter.CommonAdapter;

public class FriendFragment extends BaseFragment{
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;

    private View view;
    private List<Post> list = new ArrayList<>();
    private CommonAdapter<Post> mCommonAdapter;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;

    public FriendFragment() {
    }

    public static FriendFragment newInstance(){
        FriendFragment fragment = new FriendFragment();
        return fragment;
    }
}
