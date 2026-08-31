package com.perabru.sensornivelamento

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.atan2

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var acelerometro: Sensor

    lateinit var txtAngulo: TextView
    lateinit var txtEstado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtAngulo = findViewById(R.id.txtAngulo)
        txtEstado = findViewById(R.id.txtEstado)

        sensorManager =
            getSystemService(SENSOR_SERVICE) as SensorManager

        acelerometro =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        sensorManager.registerListener(
            this,
            acelerometro,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent) {

        val x = event.values[0]
        val y = event.values[1]

        val angulo =
            Math.toDegrees(atan2(x.toDouble(), y.toDouble()))

        txtAngulo.text = "%.1f°".format(angulo)

        if (angulo > -2 && angulo < 2) {
            txtEstado.text = "NIVELADO"
        } else {
            txtEstado.text = "DESNIVELADO"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}