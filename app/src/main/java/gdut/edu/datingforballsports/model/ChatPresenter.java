package gdut.edu.datingforballsports.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.List;

import gdut.edu.datingforballsports.dao.Impl.MessageDaoImpl;
import gdut.edu.datingforballsports.dao.MessageDao;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.presenter.BasePresenter;
import gdut.edu.datingforballsports.util.JWebSocketClient;
import gdut.edu.datingforballsports.util.ThreadUtils;
import gdut.edu.datingforballsports.view.ChatView;
import gdut.edu.datingforballsports.view.Service.SocketService;
import gdut.edu.datingforballsports.view.View_;

public class ChatPresenter extends BasePresenter {
    private MessageDao messageDao;
    private Context context;
    private SocketService.JWebSocketClientBinder binder;
    private SocketService socketService;
    public JWebSocketClient client;
    private Gson gson;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务与活动成功绑定
            Log.e("MainActivity", "服务与活动成功绑定");
            binder = (SocketService.JWebSocketClientBinder) iBinder;
            socketService = binder.getService();
            client = socketService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };

    public ChatPresenter(ChatView chatView, Context context) {
        this.model = new ChatModel();
        this.viewReference = new WeakReference<View_>(chatView);
        messageDao = new MessageDaoImpl(context);
        gson = new Gson();
    }

    public void getList(int userId, String token, int otherId) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                List<ChatMessage> chatMessageList = messageDao.getChatMessage(otherId);
                ((ChatView) viewReference.get()).onChatMsgLoadSuccess(chatMessageList, "获取信息成功");
            }
        });
    }

    public void storeMessage(ChatMessage chatMessage, int otherId) {
        messageDao.insertChatMessage(chatMessage);
        if (messageDao.getMessageBeanCountByIdAndType(1, chatMessage.getOtherOrChatRoomId()) == 0) {
            messageDao.insertMessageBean(new MessageBean(1, chatMessage.getOtherOrChatRoomId(),
                    chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                    chatMessage.getPublishTime(), chatMessage.getContent()));
            Bundle bundle = new Bundle();
            bundle.putSerializable("chatMessage", chatMessage);
            Intent intent = new Intent();
            intent.setAction("gdut.edu.datingforballsports.servicecallback.chatContent");
            intent.putExtra("bundle", bundle);
            context.sendBroadcast(intent);
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray.put(0, 1);
                String json = gson.toJson(chatMessage);
                jsonArray.put(1, json);
                jsonArray.put(2, otherId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            client.send(jsonArray.toString());
        }
    }
}
