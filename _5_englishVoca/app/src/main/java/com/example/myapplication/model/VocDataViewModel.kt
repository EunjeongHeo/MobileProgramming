package com.example.myapplication.model

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.R
import java.util.Scanner

class VocDataViewModel(private val application: Application) : AndroidViewModel(application){

    /*단어 파일 불러와 리스트에 저장하기*/

    var vocList = mutableStateListOf<VocData>()
        //mutableListOf : 수정 가능한 리스트, 수정에 대한 상태 변경을 감지하지 않음
        //mutableStateListOf : 수정 가능한 리스트, 수정에 대한 상태 변경을 감지함
        private set // vocList를 수정 못하게 막아두는 기능

    init{ // viewModel이 생성될 때, 딱 한 번만 실행되는 초기화 구문
        vocList.addAll(readWordFile())
    }

    private fun readWordFile():MutableList<VocData>{
        val context = application.applicationContext
        val scan = Scanner(context.resources.openRawResource(R.raw.words))
                                //raw 디렉토리에 넣으면 id만으로 쉽게 접근이 가능하다
        val wordList = mutableListOf<VocData>()
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            wordList.add(VocData(word, meaning))
        }
        scan.close()
        return wordList
    }

    /*open 된 상태에 대한 이벤트 처리*/

    fun changeOpenStatus(index:Int){
       vocList[index] = vocList[index].copy(isOpen = !vocList[index].isOpen) // 상태 변경 감지를 위해 copy
    }

}

