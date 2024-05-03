package com.example.myapplication.component

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.VocData
import com.example.myapplication.model.VocDataViewModel
import java.util.Locale

@Composable
fun VocList2(vocDataViewModel: VocDataViewModel = viewModel()) {

    val context = LocalContext.current

    var ttsReady by rememberSaveable {
        mutableStateOf(false)
    }
    var tts : TextToSpeech? by rememberSaveable {
        mutableStateOf(null)
    }

    DisposableEffect(LocalLifecycleOwner.current){
        tts = TextToSpeech(context){ status ->
            if(status == TextToSpeech.SUCCESS){
                ttsReady = true
                tts!!.language = Locale.US
            }
        }

        onDispose { // 반드시 오버라이딩 되어있어야 함 (tts 서비스 멈추가 하는 역할 수행)
            tts?.stop()
            tts?.shutdown()
        }
    }

    val speakWord = { vocData: VocData ->
        if(ttsReady){
            tts?.speak(vocData.word, TextToSpeech.QUEUE_ADD, null, null)
        }

    }

    LazyColumn{// 스크롤 가능한 Column
        itemsIndexed(vocDataViewModel.vocList){
        // itemsIndexed : index 정보와 item 정보를 둘 다 받아올 수 있음, 반복작업 함께 수행
            index: Int, item: VocData ->
            VocItem(vocData = item){
                speakWord(item)
                vocDataViewModel.changeOpenStatus(index)
            }
        }
    }
}


