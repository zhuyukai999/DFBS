package gdut.edu.datingforballsports.presenter;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.dao.Impl.MessageDaoImpl;
import gdut.edu.datingforballsports.dao.MessageDao;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.ChatRoomMessage;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.domain.User;
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

    public void getList() {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                List<MessageBean> allMessageBeanList = messageDao.getAllMessageBean();
                ((ChatMessageView) viewReference.get()).onLoadMessageBeanSuccess(allMessageBeanList, "获取信息成功");
            }
        });
    }

   /* public void storeMessage(String message) {
        Gson gson = new Gson();
        JSONObject detailMessage = null;
        try {
            detailMessage = new JSONObject(message);
            int type = detailMessage.getInt("type");
            switch (type) {
                case 1:
                    ChatMessage chatMessage = gson.fromJson(String.valueOf(detailMessage), ChatMessage.class);
                    messageDao.insertChatMessage(chatMessage);
                    break;
                case 2:
                    ChatMessage chaRoomtMessage = gson.fromJson(String.valueOf(detailMessage), ChatMessage.class);
                    messageDao.insertChatRoomMessage(chaRoomtMessage);
                    if (messageDao.getMessageBeanCountByIdAndType(2, chaRoomtMessage.getOtherOrChatRoomId()) == 0) {
                        messageDao.insertMessageBean(new MessageBean(type, chaRoomtMessage.getOtherOrChatRoomId(),
                                chaRoomtMessage.getOtherOrChatRoomName(), chaRoomtMessage.getOtherOrChatRoomLogo()));
                    }
                    break;
                case 3:
                    messageDao.insertMessageBean(new MessageBean(type, detailMessage.getInt("otherOrChatRoomId"),
                            detailMessage.getString("otherOrChatRoomName"), detailMessage.getString("otherOrChatRoomLogo")));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
