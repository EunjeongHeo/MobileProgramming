package com.example.myapplication.myapplication

import android.icu.text.NumberFormat
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


fun calculateTip(amount: Double,
                 tipPercent: Double,
                 roundUp: Boolean): Any {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}


@Composable
fun EditNumberField(
    value:String,
    onValueChange:(String)->Unit,
    label:Int,
    imageVector:ImageVector,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions?
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.padding(bottom = 32.dp),
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ?: KeyboardActions() //null이면 기본 KeyboardActions() 빈 객체 생성
    )
}


@Composable
fun RoundTheTipRow(checked:Boolean, onCheckedChange:(Boolean)->Unit) {
    Row (
        modifier = Modifier.padding(bottom = 32.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.round_up_tip))
        Spacer(modifier = Modifier.width(100.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}



@Composable
fun MainScreen() {

    var amountInput by remember{
        mutableStateOf("")
    }
    var tipInput by remember{
        mutableStateOf("")
    }
    var roundUp by remember{
        mutableStateOf(false)
    }

    val amount = amountInput.toDoubleOrNull() ?: 0.0 //null이라면 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0 //null이라면 0.0
    val tip = calculateTip(amount, tipPercent, roundUp)

    Column(
        modifier = Modifier.padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Text(
            text = stringResource(id = R.string.calculate_tip),
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 20.sp,
        )

        EditNumberField(
            amountInput,
            {amountInput = it},
            R.string.bill_amount,
            Icons.Default.Money,
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            null
        )
//        TextField(
//            value = amountInput,
//            onValueChange = { amountInput = it },
//            modifier = Modifier.padding(bottom = 32.dp),
//            label = { Text(text = stringResource(id = R.string.bill_amount)) },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Money,
//                    contentDescription = null
//                )
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
//        )



        EditNumberField(
            tipInput,
            {tipInput = it},
            R.string.how_was_the_service,
            Icons.Default.Percent,
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            KeyboardActions(
                onDone = {
                    keyboardController?.hide() // Done 액션이 호출됐을 때 키보드 숨김
                }
            )

        )
//        TextField(
//            value = tipInput,
//            onValueChange = { tipInput = it },
//            modifier = Modifier.padding(bottom = 32.dp),
//            label = { Text(text = stringResource(id = R.string.how_was_the_service)) },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Percent,
//                    contentDescription = null
//                )
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    keyboardController?.hide() // Done 액션이 호출됐을 때 키보드 숨김
//                }
//            )
//        )


        RoundTheTipRow(
            checked = roundUp,
            onCheckedChange = { roundUp = it }
        )
//        Row (
//            modifier = Modifier.padding(bottom = 32.dp, start = 10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = stringResource(id = R.string.round_up_tip))
//            Spacer(modifier = Modifier.width(100.dp))
//            Switch(checked = roundUp, onCheckedChange = { roundUp = it })
//        }

        Text(text = stringResource(id = R.string.tip_amount, tip),
            style = MaterialTheme.typography.headlineMedium
        )


    }


}

