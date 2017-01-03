package com.example.administrator.boundservice_2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button boundService;
    Button stopService;
    Button updateData;
    EditText num1;
    EditText num2;
    TextView result;
    Intent intent;
    private MyService.MyBinder binder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boundService = (Button) findViewById(R.id.boundServiceBtn);
        stopService = (Button) findViewById(R.id.stopBoundBtn);
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        result = (TextView) findViewById(R.id.result);
        updateData = (Button) findViewById(R.id.Update);

        boundService.setOnClickListener(onClickListener);
        stopService.setOnClickListener(onClickListener);
        updateData.setOnClickListener(onClickListener);

        intent = new Intent(MainActivity.this,MyService.class);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.boundServiceBtn:
                    bindService(intent,connection,Context.BIND_AUTO_CREATE);
                    break;

                case R.id.stopBoundBtn:
                    unbindService(connection);
                    break;

                case R.id.Update:
                    if (binder != null) {
                        System.out.println("Success!");
                        binder.setNumber(Integer.parseInt(num1.getText().toString()),Integer.parseInt(num2.getText().toString()));
                    } else {
                        System.out.println("None Pointer");
                    }

                    break;
            }
        }
    };

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.MyBinder) service;

            binder.getService().setCallBack(new MyService.CallBack() {
                @Override
                public void result(int sum) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("data",Integer.toString(sum));
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            result.setText(msg.getData().getString("data"));
        }
    };




}

