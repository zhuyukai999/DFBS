package gdut.edu.datingforballsports.dao.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import gdut.edu.datingforballsports.dao.MessageDao;
import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.ChatRoomMessage;
import gdut.edu.datingforballsports.domain.MessageBean;

public class MessageDaoImpl implements MessageDao {
    private SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;

    public MessageDaoImpl(Context context) {
        dbHelper = new SQLiteOpenHelper(context, "allMessage.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table messageBean(id INTEGER not null PRIMARY KEY autoincrement,type INTEGER not null,otherOrChatRoomId INTEGER not null,otherOrChatRoomName VARCHAR(20) not null,otherOrChatRoomLogo text not null)");
                db.execSQL("create table chatMessage(id INTEGER not null PRIMARY KEY autoincrement,otherOrChatRoomId INTEGER not null,content text not null,publishTime datetime not null,statue INTEGER not null,publisher INTEGER not null)");
                db.execSQL("create table chatRoomMessage(id INTEGER not null PRIMARY KEY autoincrement,otherOrChatRoomId INTEGER not null,content text not null,publishTime datetime not null,statue INTEGER not null,publisher INTEGER not null,publisherId INTEGER not null,publisherName VARCHAR(20) not null,publisherLogo text not null)");

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int i, int i1) {
                db.execSQL("DROP TABLE IF EXISTS " + "message.db");
                onCreate(db);
            }
        };
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public long insertMessageBean(MessageBean messageBean) {
        ContentValues newValue = new ContentValues();
        newValue.put("type", messageBean.getType());
        newValue.put("otherOrChatRoomId", messageBean.getOtherOrChatRoomId());
        newValue.put("otherOrChatRoomName", messageBean.getOtherOrChatRoomName());
        newValue.put("otherOrChatRoomLogo", messageBean.getOtherOrChatRoomLogo());
        return db.insert("messageBean", null, newValue);
    }

    @Override
    public long deleteMessageBean(int id) {
        return db.delete("messageBean", "id=" + id, null);
    }

    @Override
    public List<MessageBean> getAllMessageBean() {
        String sql = "select * from messageBean";
        Cursor cursor = db.rawQuery(sql, null);
        List<MessageBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getColumnIndex("id");
            int type = cursor.getColumnIndex("type");
            int otherOrChatRoomId = cursor.getColumnIndex("otherOrChatRoomId");
            int otherOrChatRoomName = cursor.getColumnIndex("otherOrChatRoomName");
            int otherOrChatRoomLogo = cursor.getColumnIndex("otherOrChatRoomLogo");
            list.add(new MessageBean(cursor.getInt(id), cursor.getInt(type), cursor.getInt(otherOrChatRoomId), cursor.getString(otherOrChatRoomName), cursor.getString(otherOrChatRoomLogo)));
        }
        cursor.close();
        return list;
    }

    @Override
    public long insertChatMessage(ChatMessage chatMessage) {
        ContentValues newValue = new ContentValues();
        newValue.put("content", chatMessage.getContent());
        newValue.put("publishTime", chatMessage.getPublishTime());
        return db.insert("chatMessage", null, newValue);
    }

    @Override
    public long deleteChatMessage(int otherId) {
        return db.delete("chatMessage", "id=" + otherId, null);
    }

    @Override
    public List<ChatMessage> getChatMessage(int otherId) {
        String sql = "select * from chatMessage where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(otherId)});
        List<ChatMessage> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getColumnIndex("id");
            int otherOrChatRoomId = cursor.getColumnIndex("otherOrChatRoomId");
            int content = cursor.getColumnIndex("content");
            int publishTime = cursor.getColumnIndex("publishTime");
            int statue = cursor.getColumnIndex("statue");
            int publisher = cursor.getColumnIndex("publisher");
            list.add(new ChatMessage(cursor.getInt(id),cursor.getInt(otherOrChatRoomId),
                    cursor.getString(content), cursor.getString(publishTime),
                    cursor.getInt(statue),cursor.getInt(publisher)));
        }
        cursor.close();
        return list;
    }

    @Override
    public long insertChatRoomMessage(ChatRoomMessage chatRoomMessage) {
        ContentValues newValue = new ContentValues();
        newValue.put("content", chatRoomMessage.getContent());
        newValue.put("publishTime", chatRoomMessage.getPublishTime());
        newValue.put("publisherId", chatRoomMessage.getPublisherId());
        newValue.put("publisherName", chatRoomMessage.getPublisherName());
        newValue.put("publisherLogo", chatRoomMessage.getPublisherLogo());
        return db.insert("chatRoomMessage", null, newValue);
    }

    @Override
    public long deleteChatRoomMessage(int chatRoomId) {
        return db.delete("chatRoomMessage", "id=" + chatRoomId, null);
    }

    @Override
    public List<ChatRoomMessage> getChatRoomMessage(int chatRoomId) {
        String sql = "select * from chatMessage where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(chatRoomId)});
        List<ChatRoomMessage> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getColumnIndex("id");
            int otherOrChatRoomId = cursor.getColumnIndex("otherOrChatRoomId");
            int content = cursor.getColumnIndex("content");
            int publishTime = cursor.getColumnIndex("publishTime");
            int statue = cursor.getColumnIndex("statue");
            int publisher = cursor.getColumnIndex("publisher");
            int publisherId = cursor.getColumnIndex("publisherId");
            int publisherName = cursor.getColumnIndex("publisherName");
            int publisherLogo = cursor.getColumnIndex("publisherLogo");
            list.add(new ChatRoomMessage(cursor.getInt(id),cursor.getInt(otherOrChatRoomId),
                    cursor.getString(content), cursor.getString(publishTime), cursor.getInt(statue),cursor.getInt(publisher),
                    cursor.getInt(publisherId), cursor.getString(publisherName), cursor.getString(publisherLogo)));
        }
        cursor.close();
        return list;
    }

    public void closeDb() {
        db.close();
    }
}
