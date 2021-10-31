package com.tawk.framework.mvvm.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.tawk.framework.mvvm.R
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL


object Utils {
    var note:String?=""
    private val coroutineContext = Dispatchers.IO + SupervisorJob()
    private val imageCoroutineScope = CoroutineScope(coroutineContext)
    @BindingAdapter("loadChannelImage")
    @JvmStatic
    fun bindChannel(view: ImageView, imageUrl: String?) {
        Log.e("imageUrl","imageUrl"+imageUrl)
        imageCoroutineScope.launch {
            withContext(Dispatchers.Default) {
                try {

                    val url = URL(imageUrl)
                    val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    var A: Int
                    var R: Int
                    var G: Int
                    var B: Int
                    var pixelColor: Int
                    // image size
                    val height = image.height
                    val width = image.width
                    var bmOut = Bitmap.createBitmap(image.width, image.height, image.config)
                    // scan through every pixel
                    for (y in 0 until height) {
                        for (x in 0 until width) {
                            // get one pixel
                            pixelColor = image.getPixel(x, y)
                            // saving alpha channel
                            A = Color.alpha(pixelColor)
                            // inverting byte for each R/G/B channel
                            R = 255 - Color.red(pixelColor)
                            G = 255 - Color.green(pixelColor)
                            B = 255 - Color.blue(pixelColor)
                            // set newly-inverted pixel to output image
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B))
                        }
                    }

                    //  val b=BitmapFactory.decodeByteArray(bmOut.by , 0, bmOut .length)

                    view.load(bmOut) {
                        transformations(CircleCropTransformation())
                        crossfade(false)

                    }
                } catch (e: IOException) {
                    println(e)
                }
            }

        }





    }


    @BindingAdapter("loadChannelImageOne")
    @JvmStatic
    fun loadChannelImageOne(view: ImageView, imageUrl: String?){

      view.load(imageUrl) {
                transformations(CircleCropTransformation())
                crossfade(false)
                fallback(R.drawable.ic_menu_profile)
                placeholder(R.drawable.ic_menu_profile)
            }
    }

    @BindingAdapter("loadNote")
    @JvmStatic
    fun loadNote(view: ImageView, text: String?){
        if (text.equals("")){
            view.visibility= View.GONE
        }
        else{
            view.visibility= View.VISIBLE
        }
    }
}

