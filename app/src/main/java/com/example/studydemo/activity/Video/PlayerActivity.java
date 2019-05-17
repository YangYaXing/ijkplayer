package com.example.studydemo.activity.Video;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.studydemo.R;
import com.example.studydemo.listener.OnPlayerBackListener;
import com.example.studydemo.listener.OnShowThumbnailListener;
import com.example.studydemo.widget.PlayStateParams;
import com.example.studydemo.widget.PlayerView;


/**
 * @Author Mr.Yang
 * 时  间：2019/5/14
 * 项目名：ijkPlayer
 *
 */
public class PlayerActivity extends Activity {

    private PlayerView player;
    private Context mContext;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_players);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_players, null);
        setContentView(rootView);
        String url = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=153326&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss";
        player = new PlayerView(this, rootView)
                .setTitle("超燃！漫威女英雄高能踩点混剪")
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .hideMenu(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load("http://img.kaiyanapp.com/7adeb0922a09f7e34c740f4a84ff262d.jpeg?imageMogr2/quality/60/format/jpg")
                                .placeholder(R.color.reg_bg)
                                .error(R.color.order_bg2)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(url)
                .setPlayerBackListener(new OnPlayerBackListener() {
                    @Override
                    public void onPlayerBack() {
                        //这里可以简单播放器点击返回键
                        finish();
                    }
                })
                .startPlay().onPause();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
