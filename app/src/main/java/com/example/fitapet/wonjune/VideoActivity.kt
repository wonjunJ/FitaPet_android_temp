package com.example.fitapet.wonjune

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.fitapet.R
import com.example.fitapet.databinding.ActivityVideoBinding
import io.kakaoi.connectlive.ConnectLive
import io.kakaoi.connectlive.EventsCallback
import io.kakaoi.connectlive.RemoteParticipant
import io.kakaoi.connectlive.Room
import io.kakaoi.connectlive.entity.DisconnectedReason
import io.kakaoi.connectlive.media.LocalCamera
import io.kakaoi.connectlive.media.LocalVideo
import io.kakaoi.connectlive.media.RemoteVideo
import io.kakaoi.connectlive.utils.AudioHelper

class VideoActivity : AppCompatActivity() {
    lateinit var binding : ActivityVideoBinding
    private var camera : LocalCamera? = null
    private lateinit var room : Room
    private var localVideo: LocalVideo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test","들어옴 videoacti")
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀바 없애기
        Log.d("test","setcontentview_before")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video)
        Log.d("test","setcontentview_after")
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        Log.d("test","windowmange_after")
        //supportActionBar?.hide()
        var intent = intent
        val channelId : String? = intent.getStringExtra("channelId")
        //var dialog = AlertDialog.Builder(this@VideoActivity)
        //dialog.setTitle("에러")
        //dialog.setMessage("호출실패했습니다." + channelId)
        //dialog.show()

        Log.d("test","connectLive_before")
        ConnectLive.init(this)
        ConnectLive.signIn {
            serviceId = "UGD0S5KO4AC4"
            serviceSecret = "UGD0S5KO4AC4WI3U:aVfOmqIiGauVXKAU"
        }
        Log.d("test","connectLive_after")

        requestPermissionForActivateMedia.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            )
        )

        AudioHelper.acquireFocus(this)
        room = ConnectLive.createRoom(events = OnEventListener())
        room.connect(channelId!!)
        val localMedia = ConnectLive.createLocalMedia(preferFrontCamera = false).apply{
            video?.isEnabled = true
            audio?.isEnabled = true
        }
        room.publish(localMedia)

        binding.btnClose.setOnClickListener {
            room.disconnect()
        }



        //.serviceId("UGD0S5KO4AC4")
        //.key("UGD0S5KO4AC4WI3U:aVfOmqIiGauVXKAU")
        //.videoCodec("VP8")
        //.videoWidth(640)
        //.videoHeight(480)
        //.localView(binding.localCamera)
        //.remoteView(binding.remoteView)
        //.build()


    }

    private inner class OnEventListener : EventsCallback {
        override fun onConnecting(progress: Float) {
            // 접속중...
        }

        override fun onConnected(participants: List<RemoteParticipant>) {
            // 해당 Room 접속 완료
            // 기존에 참여중이던 사람들의 목록을 받습니다.
            // 예제에서는 처음 들어는 비디오만 bind 하여 확인 합니다.
            if (participants.isNotEmpty()) {
                val remoteVideo = participants.flatMap { it.videos.values }.first()
                binding.remoteView.bind(remoteVideo)
            }

        }

        override fun onDisconnected(reason: DisconnectedReason) {
            finish()
        }

        override fun onError(code: Int, message: String, isFatal: Boolean) {
            val errorMessage = "code: $code, message: $message, isFatal: $isFatal"
            Log.d("onError", errorMessage)
            if (isFatal)
                finish()
        }

        override fun onLocalVideoPublished(video: LocalVideo) {
            // 나의 video가 publish 되었을때 불리며, 내 video를 local에 렌더링
            localVideo = video
            binding.localCamera.bind(localVideo)
        }

        override fun onRemoteVideoPublished(participant: RemoteParticipant, video: RemoteVideo) {
            // 내가 참여 후 published 된 다른사람의 video를 VideoRenderer 에 bind
            binding.remoteView.bind(video)
        }

        override fun onRemoteVideoUnpublished(participant: RemoteParticipant, video: RemoteVideo) {
            // 다른 사람이 퇴장 혹은 video를 unPublish 했을때 unbind
            binding.remoteView.unbind()
        }

        override fun onParticipantLeft(participant: RemoteParticipant) {
            finish()
        }
    }


    private val requestPermissionForActivateMedia =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            result[Manifest.permission.CAMERA]?.let { cameraGranted ->
                if (cameraGranted && camera == null) {
                    camera = ConnectLive.createLocalCamera().apply { start() }
                    binding.localCamera.bind(camera)
                }
            }
        }


    override fun onDestroy() {
        super.onDestroy()
        camera?.dispose()
        localVideo?.dispose()
        AudioHelper.releaseFocus()
        ConnectLive.signOut()
    }
}