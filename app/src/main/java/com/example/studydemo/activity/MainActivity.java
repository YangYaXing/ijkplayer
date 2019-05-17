package com.example.studydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.studydemo.R;
import com.example.studydemo.activity.Video.HPlayerActivity;
import com.example.studydemo.activity.Video.PlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Mr.Yang
 * 时  间：2019/5/14
 * 项目名：ijkPlayer
 *
 */
public class MainActivity extends AppCompatActivity {



    @BindView(R.id.bt_3)
    Button bt3;
    @BindView(R.id.bt_4)
    Button bt4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_3, R.id.bt_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_3:
                startActivity(new Intent(MainActivity.this, HPlayerActivity.class));
                break;
            case R.id.bt_4:
                startActivity(new Intent(MainActivity.this, PlayerActivity.class));
                break;
        }
    }

}
