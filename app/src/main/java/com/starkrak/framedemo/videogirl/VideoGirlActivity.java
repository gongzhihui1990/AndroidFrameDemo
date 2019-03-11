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
//    private String URL_VOD = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String URL_VOD = "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4";
//    private String URL_VOD = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
    //http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4
    //https://media.w3.org/2010/05/sintel/trailer.mp4

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ijkVideoView = findViewById(R.id.ijkVideoView);
        ijkVideoView.setUrl(URL_VOD);
        //设置视频地址
        ijkVideoView.setTitle("网易公开课-如何掌控你的自由时间");
        //设置视频标题
        StandardVideoController controller = new StandardVideoController(this);
        ijkVideoView.setVideoController(controller);
        //设置控制器，如需定制可继承 BaseVideoController
        ijkVideoView.start(); //开始播放，不调用则不自动播放

//高级设置（可选，须在 start()之前调用方可生效）
        PlayerConfig playerConfig = new PlayerConfig.Builder()
                .autoRotate() //启用重力感应自动进入/退出全屏功能
                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                .usingSurfaceView() //启用 SurfaceView 显示视频，不调用默认使用 TextureView
                .savingProgress() //保存播放进度
                .disableAudioFocus() //关闭 AudioFocusChange 监听
                .setLooping() //循环播放当前正在播放的视频
                .build();
        ijkVideoView.setPlayerConfig(playerConfig);
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
