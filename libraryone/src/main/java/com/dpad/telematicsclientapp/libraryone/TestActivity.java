package com.dpad.telematicsclientapp.libraryone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path ="/app/TestActivity",group = "home_center")
public class TestActivity extends AppCompatActivity {

    @Autowired(name = "Awm")
    String aa;

    @Autowired(name = "QQ")
    String bb;

    TextView tv1,tv2;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ARouter.getInstance().inject(this);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        button=findViewById(R.id.button_back);

        tv1.setText(aa);
        tv2.setText(bb);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("bba","666777888");
                setResult(123,intent);
                finish();
            }
        });


    }
}
