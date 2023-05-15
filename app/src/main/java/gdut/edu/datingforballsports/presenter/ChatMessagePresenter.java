package gdut.edu.datingforballsports.presenter;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.dao.Impl.MessageDaoImpl;
import gdut.edu.datingforballsports.dao.MessageDao;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.model.ChatMessageModel;
import gdut.edu.datingforballsports.model.TrendsModel;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.ChatMessageView;
import gdut.edu.datingforballsports.view.TrendsView;
import gdut.edu.datingforballsports.view.View_;

public class ChatMessagePresenter extends BasePresenter {

    private MessageDao messageDao;

    public ChatMessagePresenter(ChatMessageView chatMessageView, Context context) {
        this.model = new ChatMessageModel();
        this.viewReference = new WeakReference<View_>(chatMessageView);
        messageDao = new MessageDaoImpl(context);
    }

    public void getList(int userId, String token) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                List<MessageBean> allMessageBeanList = messageDao.getAllMessageBean();
                ((TrendsView) viewReference.get()).onTrendsLoadSuccess(allMessageBeanList,"获取信息成功");
            }
        });
    }
}
