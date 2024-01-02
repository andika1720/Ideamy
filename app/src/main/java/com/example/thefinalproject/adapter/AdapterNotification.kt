package com.example.thefinalproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.R

class AdapterNotification (private val notification: List<com.example.thefinalproject.network.model.user.testNotif.Notification>): RecyclerView.Adapter<AdapterNotification.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_notifikasi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notification.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notifications = notification[position]
        val notip = notifications.createdAt

//        val bulanNotif = notip.substring(5,7)
        val bulanNotif = integerToMonthString(notip.substring(5,7).toInt())
        val tanggalNotif = notip.substring(8,10)
        val jamNotif = timeNotification(notip.substring(11,16))
//        val jamNotif = notip.substring(11,16)

        holder.titleText.text = notifications.title
        holder.messageText.text = notifications.message
        holder.tanggalNotification.text = "$tanggalNotif $bulanNotif, $jamNotif"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_pengumuman)
        val messageText: TextView = itemView.findViewById(R.id.tv_isiPesan)
        val tanggalNotification: TextView = itemView.findViewById(R.id.tv_tanggal)
    }
    private fun integerToMonthString(month: Int): String {
        return when (month) {
            1 -> "Januari"
            2 -> "Februari"
            3 -> "Maret"
            4 -> "April"
            5 -> "Mei"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "Agustus"
            9 -> "September"
            10 -> "Oktober"
            11 -> "November"
            12 -> "Desember"
            else -> "Bulan tidak valid"
        }
    }

    private fun timeNotification(waktu:String):String{
        val hours = waktu.substring(0,2).toInt()
        val seconds = waktu.substring(3).toInt()

        return if (hours >= 12 && seconds >= 0){

            "$waktu PM"
            // PM

        }else{
            "$waktu AM"
            //AM
        }
    }

}