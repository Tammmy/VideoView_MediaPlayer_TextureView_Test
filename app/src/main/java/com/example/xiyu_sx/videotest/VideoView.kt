package com.example.xiyu_sx.videotest

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.Toast
import kotlinx.android.synthetic.main.video.*
import java.io.File

/**
 * Created by xiyu_sx on 2017/8/1.
 */
class VideoView : AppCompatActivity(){
    lateinit var mediacontroller: MediaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {     // lower api ,
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

        setContentView(R.layout.video)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        val chose=intent.getStringExtra("message")
        when (chose.toString()){
            "one"->{
                localSource()
            }
            "three"->{
                netSource()
            }
            "six"->{
                m3u8Source()
            }

        }
    }

    private fun netSource() {
        mediacontroller= MediaController(this)
        val uri= Uri.parse("http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com%20%20%20/D046015255134077DDB3ACA0D7E68D45.flv")
        video_view.setVideoURI(uri)
        video_view.setMediaController(mediacontroller)
        mediacontroller.setMediaPlayer(video_view)
        video_view.start()
    }

    fun m3u8Source(){
        mediacontroller= MediaController(this)
        val uri= Uri.parse("http://streaming.youku.com/live2play/klcd11.m3u8?auth_key=1527043875-0-0-ff81b5c5e9c04df7ab88b3f20ddba94e")
        video_view.setVideoURI(uri)
        video_view.setMediaController(mediacontroller)
        mediacontroller.setMediaPlayer(video_view)
        video_view.start()
    }

    fun localSource(){
        val file: File
        val path:String
        mediacontroller= MediaController(this)
        path= Environment.getExternalStorageDirectory().absolutePath+"/star.mp4"
        file= File(path)
        if(file.exists()) {
            video_view.setVideoPath(file.absolutePath)
            video_view.setMediaController(mediacontroller)
            mediacontroller.setMediaPlayer(video_view)
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            Log.e("asd",resourceId.toString() +"12345")
            mediacontroller.setPadding(0,0,0,this .windowManager.defaultDisplay.height-72-video_view.height-this.resources.getDimensionPixelSize(resourceId))
            video_view.start()
            video_view.requestFocus()
        }
        else{
            Toast.makeText(this,"没有指定的资源", Toast.LENGTH_SHORT).show()
        }
    }


}