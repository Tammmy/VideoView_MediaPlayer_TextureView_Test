package com.example.xiyu_sx.videotest
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.texture_view.*
import android.content.pm.ActivityInfo
import android.view.*
import android.view.TextureView
import android.widget.SeekBar
import kotlinx.android.synthetic.main.media_play.*
import com.example.xiyu_sx.videotest.R.id.seekBar
import kotlinx.android.synthetic.main.play_layout.*


/**
 * Created by xiyu_sx on 2017/7/28.
 */
class TextureView:AppCompatActivity(),TextureView.SurfaceTextureListener {

    lateinit var surface: Surface
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {     // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            View.SYSTEM_UI_FLAG_FULLSCREEN      // hide status bar
            View.SYSTEM_UI_FLAG_IMMERSIVE
            decorView.setSystemUiVisibility(uiOptions)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.texture_view)

        Log.e("tttttttttt", "tttttttttttt")
        textureView.setSurfaceTextureListener(this)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                text1.setText("Value:" + Integer.toString(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Log.e("------------", "开始滑动！")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Log.e("------------", "停止滑动！")
            }
        })
    }



    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
//        mediaPlayer.stop()
//        mediaPlayer.release()
        return true
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {

        System.out.println("onSurfaceTextureUpdated onSurfaceTextureUpdated")
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
        System.out.println("onSurfaceTextureUpdated onSurfaceTextureUpdated")
    }


    override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
        var surface = Surface(p0)
        val uri = Uri.parse("http://streaming.youku.com/live2play/klcd11.m3u8?auth_key=1527043875-0-0-ff81b5c5e9c04df7ab88b3f20ddba94e")
        val mediaPlayer = MediaPlayer()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, uri)
        mediaPlayer.setSurface(surface)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.prepare()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()

            if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

        }
    }

}


