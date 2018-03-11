@file:JvmName("Hoge")
package example

import java.io.File
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

data class OnsenProgram(val type: String, val thumbnailPath: String, val moviePath: MoviePath, val title: String, val personality: String, val guest: String, val update: String, val count: String, val schedule: String, val optionText: String, val mail: String, val copyright: String, val url: String, val link: Array<String>, val recommendGoods: Array<String>, val recommendMovie: Array<String>, val cm: Array<String>, val allowExpand: String)
data class MoviePath(val pc: String?, val iPhone: String?, val Android: String?)

fun main(args: Array<String>){
    /*
    val timeout = 5000 // milliseconds
    val timeoutRead = 50000 //milliseconds
    val res = "https://www.onsen.ag/data/api/getMovieInfo/wug".httpGet().timeout(timeout).timeoutRead(timeoutRead).response()
    */
    val res = "https://www.onsen.ag/data/api/getMovieInfo/llss".httpGet().response()
    val raw = String(res.second.data)
    val json = raw.substring(9, raw.length-3)
    val mapper = jacksonObjectMapper()
    val program = mapper.readValue<OnsenProgram>(json)
    val path: String? = program.moviePath.pc
    var file = File("test.mp3")
    if (path != null) {
        Fuel.download(path).destination { _, _ ->
            file
        }.progress { readBytes, totalBytes ->
            val progress = 100*readBytes.toFloat() / totalBytes.toFloat()
            print("Progress: $progress %\r")
        }.response ()
    }
}
