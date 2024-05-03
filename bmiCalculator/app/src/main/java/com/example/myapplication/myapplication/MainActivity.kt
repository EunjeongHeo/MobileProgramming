package com.example.myapplication.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun SelectTheUnitRow(checked:Boolean, onCheckedChange:(Boolean)->Unit) {
    Row (
        modifier = Modifier.padding(bottom = 32.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.input_height_unit))
        Spacer(modifier = Modifier.width(5.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun EditNumberField(
    value:String,
    onValueChange:(String)->Unit,
    label:Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions?
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.padding(bottom = 32.dp),
        label = { Text(text = stringResource(id = label)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ?: KeyboardActions() //null이면 기본 KeyboardActions() 객체 생성
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {


    var isMeter by remember{
        mutableStateOf(false)
    }
    var heightInput by remember{
        mutableStateOf("")
    }
    var weightInput by remember{
        mutableStateOf("")
    }

    val heightUnit = if (isMeter) R.string.height_m else R.string.height_cm

    val height = heightInput.toDoubleOrNull() ?: 0.0 //null이라면 0.0
    val weight = weightInput.toDoubleOrNull() ?: 0.0 //null이라면 0.0
    val bmi = calculateBMI(height, weight, isMeter)

    Column(
        modifier = Modifier.padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Text("202111388 허은정")

        val keyboardController = LocalSoftwareKeyboardController.current

        SelectTheUnitRow(
            checked = isMeter,
            onCheckedChange = {isMeter = it }
        )


        EditNumberField(
            heightInput,
            {heightInput = it},
            heightUnit,
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            null
        )

        EditNumberField(
            weightInput,
            {weightInput = it},
            R.string.weight,
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        Text( bmi, style = MaterialTheme.typography.headlineMedium )
    }
}

fun calculateBMI(height: Double,
                 weight: Double,
                 isMeter: Boolean): String
{
    val heightInMeters = if (isMeter) height else height / 100
    val bmi = weight / (heightInMeters * heightInMeters)

    if(weight == 0.0 || height == 0.0){
        return "BMI 체크"
    }

    return when {
        bmi < 18.5 -> "저체중"
        bmi < 25 -> "정상"
        bmi < 30 -> "과체중"
        else -> "비만"
    }
}
