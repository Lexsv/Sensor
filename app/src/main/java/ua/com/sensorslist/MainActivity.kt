package ua.com.sensorslist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var sensorManager : SensorManager
    lateinit var sensorLight: Sensor
    lateinit var sensors: List<Sensor>
    lateinit var listenerLight : SensorEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listenerLight = object: SensorEventListener{
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }

            override fun onSensorChanged(p0: SensorEvent?) {
                tvText.text = p0!!.values[0].toString()
            }

        }
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    }
    fun onClickSensList(v: View?) {
        sensorManager.registerListener(
            listenerLight, sensorLight,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

     fun onClickSensLight(view: View){
        sensorManager.unregisterListener(listenerLight,sensorLight)
         val sb = StringBuilder()
         for (sensor in sensors) {
             sb.append("name = ").append(sensor.name)
                 .append(", type = ").append(sensor.type)
                 .append("\nvendor = ").append(sensor.vendor)
                 .append(" ,version = ").append(sensor.version)
                 .append("\nmax = ").append(sensor.maximumRange)
                 .append(", resolution = ").append(sensor.resolution)
                 .append("\n--------------------------------------\n")
         }
         tvText.text = sb
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }
}
