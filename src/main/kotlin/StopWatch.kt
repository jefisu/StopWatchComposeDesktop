import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class StopWatch {

    var formattedTime by mutableStateOf("00:00:000")

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false
    private var time = 0L
    private var lastTimestamp = 0L

    fun start() {
        if (isActive) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true
            while (this@StopWatch.isActive) {
                delay(10L)
                time += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(time)
            }
        }
    }

    fun pause() {
        isActive = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        time = 0L
        lastTimestamp = 0L
        formattedTime = "00:00:000"
        isActive = false
    }


    private fun formatTime(timeMillis: Long): String {
        val time = SimpleDateFormat("mm:ss:SSS", Locale.getDefault())
        return time.format(timeMillis)
    }
}