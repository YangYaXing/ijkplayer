package com.example.studydemo.activity.Video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.studydemo.R;
import com.example.studydemo.MediaUtils;
import com.example.studydemo.listener.OnShowThumbnailListener;
import com.example.studydemo.widget.PlayStateParams;
import com.example.studydemo.widget.PlayerView;

/**
 * @Author Mr.Yang
 * 时  间：2019/5/14
 * 项目名：ijkPlayer
 *
 */
public class HPlayerActivity extends AppCompatActivity {

    private PowerManager.WakeLock wakeLock;
    private PlayerView player;
    View rootView;
    private Context mContext;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
//        setContentView(R.layout.activity_hplayer);
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_hplayer, null);
        setContentView(rootView);
        /**虚拟按键的隐藏方法*/
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //比较Activity根布局与当前布局的大小
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 100) {
                    //大小超过100时，一般为显示虚拟键盘事件
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                } else {
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                    rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                }
            }
        });

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        String url = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=16452&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss";

        player = new PlayerView(this,rootView) {
//            @Override
//            public PlayerView toggleProcessDurationOrientation() {
//                hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
//            }
//
//            @Override
//            public PlayerView setPlaySource(String list) {
//                return super.setPlaySource(list);
//            }
        }.setTitle("死侍3：糖果消消乐")
//                .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                .setScaleType(PlayStateParams.fillparent)
                .forbidTouch(false)
                .hideMenu(true)
//                .forbidTouch(false)
//                .hideSteam(true)
                .hideCenterPlayer(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load("http://img.kaiyanapp.com/441e7a9cec4e03ad1fd48ceed3455ade.jpeg?imageMogr2/quality/60/format/jpg")
                                .placeholder(R.color.address_blue)
                                .error(R.color.d_red)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(url)
                .setChargeTie(true,60)
                .onPause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!=null){
            player.onPause();
        }
        /**恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, false);
        /**激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
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
        /**恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }
}
