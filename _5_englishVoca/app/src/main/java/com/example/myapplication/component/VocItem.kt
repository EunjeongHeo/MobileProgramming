package com.example.myapplication.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.VocData
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun VocItem(vocData: VocData, onItemClick: () -> Unit) {
    ElevatedCard( // 그림자 효과 등이 있는 Column 컴포넌트 (내부에 요소추가 가능)
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
    ) {
        Text(
            text = vocData.word,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        if(vocData.isOpen) { // vocData의 isOpen 속성이 true일 때만 보이게
            Text(
                text = vocData.meaning,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview
@Composable
fun VocItemPreview() {
    MyApplicationTheme {
        VocItem(VocData("apple","사과",true)){

        }
    }
}