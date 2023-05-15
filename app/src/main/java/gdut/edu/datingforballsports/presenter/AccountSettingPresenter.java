package gdut.edu.datingforballsports.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;

import gdut.edu.datingforballsports.model.AccountSettingModel;
import gdut.edu.datingforballsports.model.Listener.AccountSettingListener;
import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.util.TextUtils;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.AccountSettingView;
import gdut.edu.datingforballsports.view.View_;

public class AccountSettingPresenter extends BasePresenter {
    private Context context;

    public AccountSettingPresenter(AccountSettingView accountSettingView, Context context) {
        this.model = new AccountSettingModel();
        this.viewReference = new WeakReference<View_>(accountSettingView);
        this.context = context;
    }

    public void modify(int userId, String token, String text, String icon_uri) {
        if (this.model != null && this.viewReference != null && this.viewReference.get() != null) {
            AccountSettingView accountSettingView = (AccountSettingView) viewReference.get();
            if (TextUtils.isEmpty(text)) {
                accountSettingView.onSettingFails("用户名不能为空");
            }
        }
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                ((AccountSettingModel) model).modifyAccount(userId, token, text, icon_uri, new AccountSettingListener() {
                    @Override
                    public void onSuccess() {
                        String storePath = context.getFilesDir().getAbsolutePath() + "/user" + userId + "/icon" + "/user" + userId + ".png";
                        GlideEngine.createGlideEngine().saveImage(context, icon_uri, storePath);
                        ((AccountSettingView) viewReference.get()).onSettingSuccess();
                    }

                    @Override
                    public void onFails() {
                        ((AccountSettingView) viewReference.get()).onSettingFails("修改资料失败");
                    }
                });
            }
        });
    }
}
