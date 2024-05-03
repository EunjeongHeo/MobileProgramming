package com.example.myapplication.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.component.MainTitle
import com.example.myapplication.component.VocList4

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // 단어장 만들기
    Column {
        MainTitle()

        Text("202111388 허은정")

//        VocList()
//        VocList2()
//        VocList3()
        VocList4()
    }
}
