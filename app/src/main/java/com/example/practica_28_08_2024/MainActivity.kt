package com.example.practica_28_08_2024

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var intent: Intent
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlarma1: View = findViewById(R.id.btnAlarma1)
        val btnAlarma2: View = findViewById(R.id.btnAlarma2)
        val btnAlarma3: View = findViewById(R.id.btnAlarma3)
        val btnAlarma4: View = findViewById(R.id.btnAlarma4)
        val btnCancelar: View = findViewById(R.id.btnCancelar)

        intent = Intent(this, PendingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        btnAlarma1.setOnClickListener   {alarmaTiempoRealTranscurrido1()}
        btnAlarma2.setOnClickListener   {alarmaTiempoRealTranscurrido2()}
        btnAlarma3.setOnClickListener   {alarmaRelojEnTiempoReal1()}
        btnAlarma4.setOnClickListener   {alarmaRelojEnTiempoReal2()}
        btnCancelar.setOnClickListener  {cancelarAlarma()}

    }

    private fun alarmaTiempoRealTranscurrido1() {
        alarmManager.setInexactRepeating( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60 * 500, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)
        Toast.makeText(applicationContext, "Alarma repetitiva TRT lanzada", Toast.LENGTH_SHORT).show()
    }

    private fun alarmaTiempoRealTranscurrido2(){
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60 * 1000, pendingIntent)
        Toast.makeText(applicationContext, "Alarma TRT lanzada", Toast.LENGTH_SHORT).show()
    }

    private fun alarmaRelojEnTiempoReal1(){
        val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 22)
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)
        Toast.makeText(applicationContext, "Alarma RTR a una hora en especifico", Toast.LENGTH_SHORT).show()
    }

    private fun alarmaRelojEnTiempoReal2(){
        val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8, 28, 7, 25)
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 1000*60*1, pendingIntent)
        Toast.makeText(applicationContext, "Alarma RTR cada minuto", Toast.LENGTH_SHORT).show()
    }

    private fun cancelarAlarma(){
        alarmManager.cancel(pendingIntent)
        Toast.makeText(applicationContext, "Alarma cancelada", Toast.LENGTH_SHORT).show()
    }
}
