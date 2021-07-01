/*
 *
 *  * ========================LICENSE_START=================================
 *  * Compose Forms
 *  * %%
 *  * Copyright (C) 2021 FHNW Technik
 *  * %%
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  * =========================LICENSE_END==================================
 *
 */

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