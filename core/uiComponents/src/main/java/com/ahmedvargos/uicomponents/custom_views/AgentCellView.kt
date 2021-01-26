package com.ahmedvargos.uicomponents.custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.uicomponents.R
import com.ahmedvargos.uicomponents.databinding.ViewAgentCellBinding
import com.ahmedvargos.uicomponents.utils.GlideApp
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class AgentCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 1
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewAgentCellBinding = ViewAgentCellBinding.inflate(
        LayoutInflater.from(context),
        this,
        false
    )

    init {
        // inflate binding and add as view
        addView(binding.root)
    }

    fun initDetails(agentInfo: AgentInfo, actionDelegate: AgentCellActionsDelegate) {
        binding.tvAgentTitle.text = agentInfo.displayName
        binding.tvAgentRole.text = agentInfo.role?.displayName
        // Display agent image
        GlideApp.with(context)
            .asBitmap()
            .load(agentInfo.bustPortrait)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let { bitmap ->
                        Palette.from(bitmap).generate {
                            // Use the dominant color in image
                            val dominantColor =
                                it?.getMutedColor(ContextCompat.getColor(context, R.color.white))
                                    ?: 0x000
                            binding.ivBackground.backgroundTintList =
                                ColorStateList.valueOf(dominantColor)
                        }
                    }
                    return false
                }
            }).into(binding.ivAgentPic)

        binding.root.setOnClickListener {
            actionDelegate.onCellClicked(agentInfo)
        }
    }

    interface AgentCellActionsDelegate {
        fun onCellClicked(agent: AgentInfo)
    }
}
