package com.example.educationsupport

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")
        val userId = intent.getStringExtra("userId")

        val ref = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationBuilder = NotificationCompat.Builder(context, "notification_channel")
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.baseline_notifications_active_24)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    notificationManager.notify(0, notificationBuilder.build())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}