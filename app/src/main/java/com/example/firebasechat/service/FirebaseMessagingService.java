package com.example.firebasechat.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.firebasechat.R;
import com.example.firebasechat.data.ChatRoom;
import com.example.firebasechat.view.chat.ChatRoomActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.



        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            if(ChatRoomActivity.CHATROOM_ID.equals(data.get("cid"))){
                Log.d("!!!!!", "IT IS SAME");
                return;
            }
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            FirebaseFirestore.getInstance().collection("chatRooms")
                    .document(data.get("cid"))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                ChatRoom chatRoom = task.getResult().toObject(ChatRoom.class);
                                Log.d("!!!!!", "ChatRoom id is "+chatRoom.getId());
                                Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                                intent.putExtra("data", chatRoom);
                                intent.putExtra("name", data.get("name"));
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                                stackBuilder.addNextIntentWithParentStack(intent);
                                Intent parentIntent = stackBuilder.editIntentAt(0);
                                parentIntent.putExtra("menu", R.id.menu_chat);
                                PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                                NotificationCompat.Builder notiBuilder = new NotificationCompat
                                        .Builder(getApplicationContext(),"default_notification_channel_id")
                                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_person_add))
                                        .setContentInfo(data.get("name"))
                                        .setPriority(NotificationCompat.PRIORITY_MAX    )
                                        .setWhen(System.currentTimeMillis())
                                        .setShowWhen(true)
                                        .setSubText(data.get("name"))
                                        .setAutoCancel(true)
                                        .setContentTitle(data.get("name"))
                                        .setContentText(data.get("content"))
                                        .setContentIntent(pendingIntent)
                                        .setDefaults(Notification.DEFAULT_VIBRATE)
                                        .setSound(uri);


                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    // Create channel to show notifications.
                                    String channelName = getString(R.string.default_notification_channel_id);
                                    NotificationChannel channel = new NotificationChannel("default_notification_channel_id", channelName, NotificationManager.IMPORTANCE_HIGH);
                                    notificationManager.createNotificationChannel(channel);
                                }

                                notificationManager.notify(0, notiBuilder.build());
                            }
                        }
                    });

        }
    }

    @Override
    public void onNewToken(String s) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> data = new HashMap<>();
        data.put("uid", uid);
        data.put("fcmId",s);
        Log.d("!!!!!", uid+": "+s);
        FirebaseFirestore.getInstance().collection("fcmIDs")
                .document(uid).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    return;
                }

            }
        });


    }

    public FirebaseMessagingService() {

    }

}
