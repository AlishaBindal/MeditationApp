package com.example.alishameditationapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.alishameditationapp.data.model.MeditationData
import com.example.alishameditationapp.databinding.FragmentLayoutBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util

class CommonFragment : Fragment(), Player.Listener {
    private var meditationData: MeditationData? = null
    private var _binding: FragmentLayoutBinding? = null
    private var playerView: PlayerControlView? = null
    private var videoUrl: Uri? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var player: ExoPlayer? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageLink = meditationData?.subCollection?.collectionData?.imageLink
        Glide.with(requireActivity()).load(imageLink).into(binding.ivBackground)
        initializePlayer()
    }

    fun newInstance(data: MeditationData): CommonFragment {
        val commonFragment = CommonFragment()
        commonFragment.meditationData = data
        return commonFragment
    }

    private fun initializePlayer() {
        playerView = binding.videoView
        val videoUri = meditationData?.subCollection?.collectionData?.link
        videoUrl = Uri.parse(videoUri)
        if (player == null) {
            val trackSelector = DefaultTrackSelector(requireContext())
            trackSelector.setParameters(
                trackSelector.buildUponParameters().setMaxVideoSizeSd()
            )
            player = ExoPlayer.Builder(requireContext())
                .setTrackSelector(trackSelector)
                .build()
        }
        playerView?.player = player

        val mediaItem: MediaItem = MediaItem.Builder()
            .setUri(videoUri)
            .setMimeType(MimeTypes.VIDEO_MP4)
            .build()
        player?.setMediaItem(mediaItem)

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.addListener(this)
        player?.prepare()
        playerView?.showTimeoutMs = 0
        playerView?.show()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String
        when (playbackState) {
            ExoPlayer.STATE_IDLE -> stateString = "ExoPlayer.STATE_IDLE      -"
            ExoPlayer.STATE_BUFFERING -> {
                binding.spinnerVideoDetails.visibility = View.VISIBLE
                stateString = "ExoPlayer.STATE_BUFFERING -"
            }
            ExoPlayer.STATE_READY -> {
                binding.spinnerVideoDetails.visibility = View.GONE
                stateString = "ExoPlayer.STATE_READY     -"
            }
            ExoPlayer.STATE_ENDED -> stateString = "ExoPlayer.STATE_ENDED     -"
            else -> stateString = "UNKNOWN_STATE             -"
        }

    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player?.currentPosition!!
            currentWindow = player?.currentMediaItemIndex!!
            playWhenReady = player?.playWhenReady == true
            player?.removeListener(this)
            player?.release()
            player = null
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            if (player != null) {
                player?.pause()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}