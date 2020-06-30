package com.loan.fengkongsdk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.bean.LocationInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WeithinkFengkong.getInstance().syncData("aaa","1.0","abc","aaa"
        ,"12345678901","http://baidu.com",new ArrayList<LocationInfo>(),"asea",
                "123","seae");
    }
}
