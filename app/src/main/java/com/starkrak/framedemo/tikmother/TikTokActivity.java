package com.starkrak.framedemo.tikmother;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.starkrak.framedemo.BaseActivity;
import com.starkrak.framedemo.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author caroline
 */
public class TikTokActivity extends BaseActivity {
    private static final String TAG = "TikTokActivity";
    private IjkVideoView mIjkVideoView;
    private TikTokController mTikTokController;
    private int mCurrentPosition;
    private RecyclerView mRecyclerView;
    private List<VideoBean> mVideoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiktok);
        setStatusBarTransparent();
        mIjkVideoView = new IjkVideoView(this);
        PlayerConfig config = new PlayerConfig.Builder().setLooping().build();
        mIjkVideoView.setPlayerConfig(config);
        mTikTokController = new TikTokController(this);
        mIjkVideoView.setVideoController(mTikTokController);
        mRecyclerView = findViewById(R.id.rv);

        mVideoList = DataUtil.getTikTokVideoList();
        TikTokAdapter tikTokAdapter = new TikTokAdapter(mVideoList, this);
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(tikTokAdapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
                startPlay(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurrentPosition == position) {
                    mIjkVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
            }
        });
    }

    private void startPlay(int position) {
        View itemView = mRecyclerView.getChildAt(0);
        FrameLayout frameLayout = itemView.findViewById(R.id.container);
        Glide.with(this)
                .load(mVideoList.get(position).getThumb())
                //.placeholder(android.R.color.white)
                .into(mTikTokController.getThumb());
        ViewParent parent = mIjkVideoView.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mIjkVideoView);
        }
        frameLayout.addView(mIjkVideoView);
        mIjkVideoView.setUrl(mVideoList.get(position).getUrl());
        mIjkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        mIjkVideoView.start();
    }

    /**
     * 把状态栏设成透明
     */
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = TikTokActivity.this.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIjkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIjkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIjkVideoView.release();
    }
}
