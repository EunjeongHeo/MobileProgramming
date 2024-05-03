package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun DollImage(Imgid: Int){
    Image(
        painter = painterResource(id = Imgid),
        contentDescription = "이미지",
        Modifier.size(300.dp)
    )
}

@Composable
fun CheckBoxWithText(checkedState: Boolean, onCheckedChange: (Boolean) -> Unit, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text)
    }
}



@Composable
fun MainScreen(){

    var armsCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var earsCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var eyebrowsCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var eyesCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var glassesCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var hatCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var mouthCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var mustacheCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var noseCheckedState by rememberSaveable {
        mutableStateOf(false)
    }
    var shoesCheckedState by rememberSaveable {
        mutableStateOf(false)
    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(50.dp))

        Box {
            DollImage(R.drawable.body)
            if(armsCheckedState) { DollImage(R.drawable.arms) }
            if(earsCheckedState) { DollImage(R.drawable.ears) }
            if(eyebrowsCheckedState) { DollImage(R.drawable.eyebrows) }
            if(eyesCheckedState) { DollImage(R.drawable.eyes) }
            if(glassesCheckedState) { DollImage(R.drawable.glasses) }
            if(hatCheckedState) { DollImage(R.drawable.hat) }
            if(mouthCheckedState) { DollImage(R.drawable.mouth) }
            if(mustacheCheckedState) { DollImage(R.drawable.mustache) }
            if(noseCheckedState) { DollImage(R.drawable.nose) }
            if(shoesCheckedState) { DollImage(R.drawable.shoes) }
        }

        Spacer(modifier = Modifier.size(40.dp))

        Row {
            Column {
                CheckBoxWithText(armsCheckedState, { armsCheckedState = it }, "arms")
                CheckBoxWithText(eyebrowsCheckedState, { eyebrowsCheckedState = it }, "eyebrows")
                CheckBoxWithText(glassesCheckedState, { glassesCheckedState = it }, "glasses")
                CheckBoxWithText(mouthCheckedState, { mouthCheckedState = it }, "mouth")
                CheckBoxWithText(noseCheckedState, { noseCheckedState = it }, "nose")
            }
            Spacer(modifier = Modifier.size(40.dp))
            Column {
                CheckBoxWithText(earsCheckedState, { earsCheckedState = it }, "ears")
                CheckBoxWithText(eyesCheckedState, { eyesCheckedState = it }, "eyes")
                CheckBoxWithText(hatCheckedState, { hatCheckedState = it }, "hat")
                CheckBoxWithText(mustacheCheckedState, { mustacheCheckedState = it }, "mustache")
                CheckBoxWithText(shoesCheckedState, { shoesCheckedState = it }, "shoes")
            }
        }

        Spacer(modifier = Modifier.size(40.dp))

    }
}