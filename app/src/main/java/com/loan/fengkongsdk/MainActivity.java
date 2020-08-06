package com.loan.fengkongsdk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.bean.LocationInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeithinkFengkong.getInstance().autoSyncData("119");
//        WeithinkFactory.getLogger().setLogLevel(LogLevel.SUPRESS,true);
    }
}
