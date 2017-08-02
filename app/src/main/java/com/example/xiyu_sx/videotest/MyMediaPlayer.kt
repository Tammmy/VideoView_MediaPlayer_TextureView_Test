package com.example.xiyu_sx.videotest

import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.media_play.*



/**
 * Created by xiyu_sx on 2017/7/25.
 */
class MyMediaPlayer : AppCompatActivity() {
    var flag =1
    var mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api 19:Android 4.4
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            View.SYSTEM_UI_FLAG_IMMERSIVE
            decorView.setSystemUiVisibility(uiOptions)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.media_play)



        surfaceView.setOnClickListener {
            if(flag==1) {
                flag=2
                mediaPlayer.pause()
                bt_play.setVisibility(View.VISIBLE)
            }

        }
        bt_play.setOnClickListener {
            if(flag==1) {
                flag=2
                mediaPlayer.pause()
            }
            else if(flag==2) {
                flag=1
                mediaPlayer.start()
                playPic()
            }
        }

        val chose=intent.getStringExtra("message")
        when (chose.toString()){
            "two"->{
                localSource()
            }
            "four"->{
                netSource()
            }
            "five"->{
                me3uSource()
            }
            }
        playPic()

    }

    fun localSource() {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().absolutePath + "/star.mp4")
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        var holder=surfaceView.holder
        holder.addCallback(MyCallBack())
        mediaPlayer.prepare()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
         //   mediaPlayer.setDisplay(surfaceView.holder)
        }
    }
    fun netSource(){
        val uri= Uri.parse("http://220.194.199.176/7/z/h/t/x/zhtxzqhavcpxefedqpmeujscdjwuqx/hc.yinyuetai.com/6899015D81C74332A7D4F0F81E9B8CF3.mp4?sc=a4aca31f34d67f5b")
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this,uri)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        Log.i("qw","qw")
        var holder=surfaceView.holder
        holder.addCallback(MyCallBack())
        mediaPlayer.prepare()
        Log.i("qw","qw")
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
         //   mediaPlayer.setDisplay(surfaceView.holder)
        }
    }
    fun me3uSource(){

        Log.e("m3u8","mmmmmmmmmmmmmmmm")
        /*http://streaming.youku.com/live2play/klcd11.m3u8?auth_key=1527043875-0-0-ff81b5c5e9c04df7ab88b3f20ddba94e*/
        val uri=Uri.parse("http://streaming.youku.com/live2play/klcd11.m3u8?auth_key=1527043875-0-0-ff81b5c5e9c04df7ab88b3f20ddba94e")
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this,uri)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        Log.i("qw","qw")
        var holder=surfaceView.holder
        holder.addCallback(MyCallBack())
        mediaPlayer.prepare()
        Log.i("qw","qw")
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            //   mediaPlayer.setDisplay(surfaceView.holder)
            /*live_jia_public/_LC_RE_non_360H087656115010745441497565_CX/index.m3u8*/
        }
    }

    fun playPic(){ Handler().postDelayed(Runnable { bt_play.setVisibility(View.GONE) },500)}
    fun playPic2(){ Handler().postDelayed(Runnable { bt_play.setVisibility(View.VISIBLE) },1000)}
    private inner class MyCallBack : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            Log.i("qw","qw")
            mediaPlayer.setDisplay(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
                mediaPlayer.release()
                surfaceView.destroyDrawingCache()
        }
    }
}
