package com.example.myapplication.component

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.VocData
import com.example.myapplication.model.VocDataViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocList3(vocDataViewModel: VocDataViewModel = viewModel()) {

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

        onDispose { // 반드시 오버라이딩 되어있어야 함 (tts 서비스 멈추게 하는 역할 수행)
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
        itemsIndexed(vocDataViewModel.vocList,
            key = {_, voc -> voc.word}
            ){
        // itemsIndexed : index 정보와 item 정보를 둘 다 받아올 수 있음, 반복작업 함께 수행
            index: Int, item: VocData ->

            val state = rememberDismissState(
                confirmValueChange = {
                    if(it == DismissValue.DismissedToStart){
                        vocDataViewModel.vocList.remove(item)
                        true
                    }else {
                        false
                    }
                }
            )

            SwipeToDismiss(state = state,
                background = {
                    val color = when (state.dismissDirection) {
                        DismissDirection.EndToStart -> Color.LightGray
                        else -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                },
                dismissContent = {//어디에 스와이프 기능 적용할건지
                    VocItem(vocData = item){
                        speakWord(item)
                        vocDataViewModel.changeOpenStatus(index)
                    }
                }
            )
        }
    }
}


