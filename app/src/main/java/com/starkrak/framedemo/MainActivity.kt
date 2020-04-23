package com.starkrak.framedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater
import com.starkrak.framedemo.camerax.CameraXActivity
import com.starkrak.framedemo.logboy.LogBoyActivity
import com.starkrak.framedemo.playgame.PlayActivity
import com.starkrak.framedemo.postboy.PostBoyActivity
import com.starkrak.framedemo.tikmother.TikTokActivity
import com.starkrak.framedemo.videogirl.VideoGirlActivity
import com.starkrak.framedemo.viewpager.FragmentStatePagerSupport
import com.starkrak.framedemo.widget.MaterialHeader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

@LayoutID(R.layout.activity_main)
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val creator = DefaultRefreshHeaderCreater { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            MaterialHeader(context).setPercentCallback { percent ->
                val percentAcc = 5 * percent
                blurLayout.alpha = if (percentAcc > 1) {
                    0F
                } else {
                    1F - percentAcc
                }
            }
        }
        smartRefreshLayout.refreshHeader = creator.createRefreshHeader(context, smartRefreshLayout)


        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        val images = arrayOf(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547632366691&di=0cdab09090f0056fd939a907f278f2c0&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F91e9ebb3jw1ea3gqi52u0j21hc0xc4qp.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547632366690&di=624441572650ed0c8e7c664a46684bd6&imgtype=0&src=http%3A%2F%2Fwww.sucaitianxia.com%2FPhoto%2Fpic%2F200910%2Fnbzbs32.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547632366689&di=21fdf009f79b7a6761fdf74097045c7d&imgtype=0&src=http%3A%2F%2Fpic12.photophoto.cn%2F20090720%2F0036036365966496_b.jpg")
        banner.setImages(images.toMutableList())
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(3000)
        banner.start()
        funView1.setOnClickListener { startActivity(Intent(this, PlayActivity::class.java)) }
        funView2.setOnClickListener { startActivity(Intent(this, PostBoyActivity::class.java)) }
        funView3.setOnClickListener { startActivity(Intent(this, LogBoyActivity::class.java)) }
        funView4.setOnClickListener { startActivity(Intent(this, VideoGirlActivity::class.java)) }
        funView5.setOnClickListener { startActivity(Intent(this, TikTokActivity::class.java)) }
        funView6.setOnClickListener { startActivity(Intent(this, CameraXActivity::class.java)) }
        funView7.setOnClickListener { startActivity(Intent(this, FragmentStatePagerSupport::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        blurLayout.startBlur()
    }

    override fun onPause() {
        super.onPause()
        blurLayout.pauseBlur()
    }

    private inner class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load((path as String))/*.fitCenter()*/.into(imageView)
        }
    }
}
