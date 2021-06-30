package com.cyberz.uartorangepi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.serialport.SerialPort;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class MainActivity extends AppCompatActivity {
    private Button btn_led_off;
    private Button btn_led_on;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_led_off = (Button)findViewById(R.id.btn_led_off);
        btn_led_on = (Button)findViewById(R.id.btn_led_on);
        SerialPort serialPort = null;
        File file = new File("/dev/ttyMT1");
        SerialPort.setSuPath("/system/xbin/su");
        try {
            serialPort = SerialPort //
                    .newBuilder(file, 9600) // 串口地址地址，波特率
                    .parity(0) // 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
                    .dataBits(8) // 数据位,默认8；可选值为5~8
                    .stopBits(1) // 停止位，默认1；1:1位停止位；2:2位停止位
                    .build();
            mSerialPort = serialPort;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: "+e);
        }


        btn_led_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mOutputStream = mSerialPort.getOutputStream();
                    mOutputStream.write(new String("off").getBytes());
                    mOutputStream.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_led_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mOutputStream = mSerialPort.getOutputStream();
                    mOutputStream.write(new String("on").getBytes());
                    mOutputStream.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
