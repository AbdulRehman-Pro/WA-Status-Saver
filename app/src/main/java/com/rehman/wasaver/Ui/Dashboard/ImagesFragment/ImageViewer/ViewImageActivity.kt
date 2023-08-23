package com.rehman.wasaver.Ui.Dashboard.ImagesFragment.ImageViewer

import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.rehman.wasaver.databinding.ActivityViewImageBinding

class ViewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImageBinding
    private lateinit var fileUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.back.setOnClickListener { onBackPressed() }


        fileUri = intent.getStringExtra("fileUri").toString()
        Glide.with(applicationContext).load(fileUri).into(binding.imageView)



        backgroundAnimation()


    }

    private fun backgroundAnimation() {
        val uri = Uri.parse(fileUri)

        try {
            val inputStream = this.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)



            Palette.Builder(bitmap).maximumColorCount(15).generate { palette ->
                val mutedColor = palette!!.getMutedColor(0)
                val lightVibrantColor = palette.getLightVibrantColor(0)
                val vibrantColor = palette.getLightMutedColor(0);


                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP,
                    intArrayOf(mutedColor, lightVibrantColor, vibrantColor)
                )

                binding.root.background = gradientDrawable

                val colorAnimation =
                    ValueAnimator.ofArgb(mutedColor, lightVibrantColor, vibrantColor)
                colorAnimation.duration = 10000
                colorAnimation.repeatCount = ValueAnimator.INFINITE
                colorAnimation.repeatMode = ValueAnimator.REVERSE
                colorAnimation.addUpdateListener { animator ->
                    val animatedColor = animator.animatedValue as Int
                    gradientDrawable.colors = intArrayOf(lightVibrantColor, animatedColor)
                    binding.root.background = gradientDrawable
                }
                colorAnimation.start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}