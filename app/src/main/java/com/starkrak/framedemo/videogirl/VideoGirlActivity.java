package com.starkrak.framedemo.videogirl;

import android.os.Bundle;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.starkrak.framedemo.BaseActivity;
import com.starkrak.framedemo.LayoutID;
import com.starkrak.framedemo.R;

import org.jetbrains.annotations.Nullable;


/**
 * @author caroline
 */
@LayoutID(R.layout.activity_video_girl)
public class VideoGirlActivity extends BaseActivity {
    IjkVideoView ijkVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ijkVideoView = findViewById(R.id.ijkVideoView);

        //播放设置
        PlayerConfig playerConfig = new PlayerConfig.Builder()
                .autoRotate() //启用重力感应自动进入/退出全屏功能
                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                //.usingSurfaceView() //启用 SurfaceView 显示视频，不调用默认使用 TextureView
                .savingProgress() //保存播放进度
                .disableAudioFocus() //关闭 AudioFocusChange 监听
                .setLooping() //循环播放当前正在播放的视频
                .build();
        ijkVideoView.setPlayerConfig(playerConfig);
        StandardVideoController controller = new StandardVideoController(this);
        //设置控制器，如需定制可继承 BaseVideoController
        ijkVideoView.setVideoController(controller);
        //设置视频地址
        ijkVideoView.setUrl("http://119.23.216.4:8089/familyst/afda53b5-6b05-4329-bc74-cb28dad0f05e.mp4");
//        ijkVideoView.setUrl("rtmp://10.10.10.166:1935/oflaDemo/SampleVideo.flv");
        //ijkVideoView.setUrl("rtmp://10.10.10.166:1935/oflaDemo/Avengers2.mp4");
        //设置视频标题
        ijkVideoView.setTitle("测试视频");
        //开始播放，不调用则不自动播放
        ijkVideoView.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
