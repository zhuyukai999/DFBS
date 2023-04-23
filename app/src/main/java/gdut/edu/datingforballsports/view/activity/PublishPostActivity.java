package gdut.edu.datingforballsports.view.activity;

import static gdut.edu.datingforballsports.util.EmailUtils.p;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.presenter.LoginPresenter;
import gdut.edu.datingforballsports.presenter.PublishPostPresenter;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.PublishPostView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

public class PublishPostActivity extends BaseActivity implements PublishPostView {
    private static final int LOAD_SUCCEED = 1;
    private static final int LOAD_FAILED = 2;
    private PublishPostPresenter pPresenter;
    private ImageView image;
    private GridLayout showImagePanal;
    private EditText inMessage;
    private List<LocalMedia> images = new ArrayList();
    public final static int MULTIPLE = 0;
    private Intent intent;
    private int userId = -1;
    private String token;
    private String RCmsg;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Intent intent = new Intent();
                //改变UI
                switch (message.what) {
                    case LOAD_FAILED:
                        Toast.makeText(getApplicationContext(), RCmsg, Toast.LENGTH_LONG).show();
                        break;
                    case LOAD_SUCCEED:
                        // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.id.main_frame_layout, f1);
                        // transaction.add(R.id.main_frame_layout, f4);
                        Toast.makeText(getApplicationContext(), "发表成功", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        setData();
        setView();

    }

    private void setData() {
        this.pPresenter = new PublishPostPresenter(this);
        intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        token = intent.getStringExtra("token");
    }

    private void setView() {
        buttonClick(R.id.publish_post_publish_photo, view -> {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    PictureSelector.create(PublishPostActivity.this)
                            .openSystemGallery(SelectMimeType.ofImage())
                            .setSelectionMode(SelectModeConfig.MULTIPLE)
                            .setCompressEngine(new CompressFileEngine() {
                                @Override
                                public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
                                    Luban.with(context).load(source).ignoreBy(100).setCompressListener(new OnNewCompressListener() {
                                        @Override
                                        public void onStart() {

                                        }

                                        @Override
                                        public void onSuccess(String source, File compressFile) {
                                            if (call != null) {
                                                call.onCallback(source, compressFile.getAbsolutePath());
                                            }
                                        }

                                        @Override
                                        public void onError(String source, Throwable e) {
                                            if (call != null) {
                                                call.onCallback(source, null);
                                            }
                                        }
                                    }).launch();
                                }
                            })
                            .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(ArrayList<LocalMedia> result) {
                                    GridLayout gridLayout = findViewById(R.id.publish_post_gridLayout);
                                    while (images.size() < 3) {
                                        int k = images.size();
                                        images.addAll(result);
                                        for (int i = images.size(), j = result.size(); i <= 3; i++, j--) {
                                            images.add(result.get(result.size() - j));
                                        }
                                        while (k < 3) {
                                            ImageView imageView = new ImageView(getApplicationContext());
                                            imageView.setMaxWidth(getResources().getDimensionPixelOffset(R.dimen.dp_100));
                                            Glide.with(getApplicationContext()).load(images.get(k - 1).getCompressPath()).into(imageView);

                                            GridLayout.Spec rowSpec = GridLayout.spec(0, 1, 1f);
                                            GridLayout.Spec columnSpec = GridLayout.spec(k - 1, 1, 1f);
                                            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
                                            layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dp_100);
                                            layoutParams.width = getResources().getDimensionPixelOffset(R.dimen.dp_100);
                                            gridLayout.addView(imageView, layoutParams);
                                            k++;
                                        }
                                    }
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                }
            });
        });

        buttonClick(R.id.publish_post_contain_publish_button, view -> {
            int imageNum = images.size();
            List<String> imageCompressPaths = new ArrayList<>();
            for (LocalMedia localMedia : images) {
                imageCompressPaths.add(localMedia.getCompressPath());
            }
            Post post = new Post(userId, getContent(), 0, 0, null, imageCompressPaths);
            pPresenter.PublishPost(post, token);
        });
    }

    public String getContent() {
        EditText postContent = (EditText) findViewById(R.id.publish_post_content);
        return String.valueOf(postContent.getText());
    }

    @Override
    public void onLoginSuccess() {
        Message msg = Message.obtain();
        msg.what = LOAD_SUCCEED; // 消息标识
        mHandler.sendMessage(msg);
    }

    @Override
    public void onLoginFails(String RCmsg) {

    }
}