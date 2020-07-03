package com.loan.fengkongsdk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.bean.LocationInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String url = "https://riskdata.cash-sweet.com";
        String url = Constants.testUrl;
        WeithinkFengkong.getInstance().syncData("aaa.aa.aaa","1.0","012345","12345678901"
                ,new ArrayList<LocationInfo>(),"aaa","321",url);
    }
}
