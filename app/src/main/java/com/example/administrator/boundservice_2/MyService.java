package com.example.administrator.boundservice_2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    private int num1 = 0;
    private int num2 = 0;
    int sum;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    public class MyBinder extends Binder{

        public void setNumber(int num1, int num2) {
            MyService.this.num1 = num1;
            MyService.this.num2 = num2;
        }

        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service Conncted");

        new Thread(){
            @Override
            public void run() {
                super.run();

                while (true) {
                    if (callBack != null) {
                        callBack.result(sum);
                    } else {
                        System.out.println("llllllllllllllllllllllllllllllllllllllll");
                    }

                    sum = num1 + num2;
                    System.out.println(sum);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(num1);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service Disconnected");
    }

    private CallBack callBack = null;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public static interface CallBack {
        public void result(int sum);
    }
}
