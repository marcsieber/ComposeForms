package server

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skija.Image
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class QRCodeService {


    fun getQRCode(text: String, size: Int = 500, onSuccess: (bitmap: ImageBitmap) -> Unit ){
        downloadBitmapFromURL("https://api.qrserver.com/v1/create-qr-code/", text,  size, onSuccess)
    }

    fun downloadBitmapFromURL(url:       String,
                              text:      String,
                              size:      Int,
                              onSuccess: (bitmap: ImageBitmap) -> Unit = {},
                              onError:   (exception: Exception) -> Unit = {}) {
        val fullURL = "$url?size=${size}x${size}&data=$text"
        with(URL(fullURL).openConnection() as HttpsURLConnection) {
            setRequestProperty("User-Agent", "Compose Forms")
            try {
                println("Downloading")
                connect()
                onSuccess.invoke(bitmap())
            } catch (e: Exception) {
                onError.invoke(e)
            }
        }
    }

    private fun HttpsURLConnection.bitmap(): ImageBitmap {
        val allBytes = inputStream.readBytes()
        return Image.makeFromEncoded(allBytes).asImageBitmap()

    }
}