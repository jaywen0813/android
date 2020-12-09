package com.dpad.telematicsclientapp.android;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dpad.telematicsclientapp.Constance;


public class MainActivity extends AppCompatActivity {

    TextView tv1;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.tv1);
        bt=findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 16) {//sdk大于等于16的动画方法:
                    ActivityOptionsCompat compat = ActivityOptionsCompat.
                            makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);


                    ARouter.getInstance()
                            .build(Constance.ACTIVITY_URL_SIMPLE,Constance.GROUP_HOME)
                            .withString("Awm","jay")
                            .withString("QQ","123456789")
                            .withOptionsCompat(compat)//跳转动画
                            .navigation(MainActivity.this,123);


                } else {
                    Toast.makeText(MainActivity.this, "API < 16,不支持新版本动画", Toast.LENGTH_SHORT).show();
                }



            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123 &&resultCode==123){

            if (data!=null){
                String ss=data.getStringExtra("bba");
                tv1.setText(ss);
            }


        }
    }
}
