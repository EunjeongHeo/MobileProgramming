package com.example.myapplication.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.VocData
import com.example.myapplication.model.VocDataViewModel

@Composable
fun VocList(vocDataViewModel: VocDataViewModel = viewModel()) {


    LazyColumn{// 스크롤 가능한 Column
        itemsIndexed(vocDataViewModel.vocList){
        // itemsIndexed : index 정보와 item 정보를 둘 다 받아올 수 있음, 반복작업 함께 수행
            index: Int, item: VocData ->
            VocItem(vocData = item){
                vocDataViewModel.changeOpenStatus(index)
            }
        }
    }
}

