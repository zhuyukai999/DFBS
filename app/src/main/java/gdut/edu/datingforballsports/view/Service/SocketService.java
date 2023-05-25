package gdut.edu.datingforballsports.view.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import gdut.edu.datingforballsports.dao.Impl.MessageDaoImpl;
import gdut.edu.datingforballsports.dao.MessageDao;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;
import gdut.edu.datingforballsports.util.JWebSocketClient;
import gdut.edu.datingforballsports.util.ThreadUtils;

public class SocketService extends Service {
    private int userId;
    private URI uri;
    public JWebSocketClient client = new JWebSocketClient(uri) {
        @Override
        public void onOpen(ServerHandshake handshakedata) {

        }

        @Override
        public void onMessage(String message) {
            Gson gson = new Gson();
            ChatMessage chatMessage;
            JSONArray jsonArray = null;
            MessageDao messageDao = new MessageDaoImpl(getApplicationContext());
            Intent intent;
            try {
                JSONObject jsonObject = new JSONObject(message);
                chatMessage = gson.fromJson(String.valueOf(jsonObject), ChatMessage.class);
                switch (chatMessage.getType()) {
                    case 1:
                        messageDao.insertChatMessage(chatMessage);
                        if (messageDao.getMessageBeanCountByIdAndType(1, chatMessage.getOtherOrChatRoomId()) == 0) {
                            messageDao.insertMessageBean(new MessageBean(1, chatMessage.getOtherOrChatRoomId(),
                                    chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                                    chatMessage.getPublishTime(), chatMessage.getContent()));
                        }
                        break;
                    case 2:
                        messageDao.insertChatRoomMessage(chatMessage);
                        if (messageDao.getMessageBeanCountByIdAndType(2, chatMessage.getOtherOrChatRoomId()) == 0) {
                            messageDao.insertMessageBean(new MessageBean(2, chatMessage.getOtherOrChatRoomId(),
                                    chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                                    chatMessage.getPublishTime(), chatMessage.getContent()));
                        }
                        break;
                    case 3:
                        messageDao.insertMessageBean(new MessageBean(3, chatMessage.getOtherOrChatRoomId(),
                                chatMessage.getOtherOrChatRoomName(), chatMessage.getOtherOrChatRoomLogo(),
                                chatMessage.getPublishTime(), chatMessage.getContent()));
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("chatMessage", chatMessage);
                intent = new Intent();
                intent.setAction("gdut.edu.datingforballsports.servicecallback.chatContent");
                intent.putExtra("bundle", bundle);
                sendBroadcast(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {

        }

        @Override
        public void onError(Exception ex) {

        }
    };

    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();

    public class JWebSocketClientBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            userId = intent.getIntExtra("userId", -1);
            uri = URI.create(intent.getStringExtra("uri"));
            client.connectBlocking();
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    if (client.isClosed()) {
                        try {
                            //重连
                            client.reconnectBlocking();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
        super.onDestroy();
    }

}
