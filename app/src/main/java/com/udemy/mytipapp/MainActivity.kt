package com.udemy.mytipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.udemy.mytipapp.components.InputFild
import com.udemy.mytipapp.ui.theme.MyTipAppTheme
import com.udemy.mytipapp.util.calculateTotalPerPerson
import com.udemy.mytipapp.util.calculateTotalTip
import com.udemy.mytipapp.widgets.RoundIconButton
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                //TopHeader()
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MyTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
        ) {
            content()
        }
    }
}

//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 134.9852) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(start = 24.dp, end = 24.dp, top = 15.dp, bottom = 15.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$ $total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent() {
    BillForm() { billAmt ->
        Log.d("AMT", "Main Content: $billAmt")

    }

}


@ExperimentalComposeUiApi
@Composable
fun BillForm(modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {

    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()

    val splitByState = remember {
        mutableStateOf(3)
    }

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }


    val range = IntRange(start = 1, endInclusive = 100)

    Column() {
        TopHeader(totalPerPerson = totalPerPersonState.value)

        Surface(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                InputFild(
                    valueState = totalBillState,
                    labelId = "Enter Bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!validState) return@KeyboardActions

                        onValueChange(totalBillState.value.trim())

                        keyboardController?.hide()
                    })

                if (validState) {
                    Row(
                        modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Split",
                            modifier = Modifier.align(
                                alignment = Alignment.CenterVertically
                            )
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 3.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            RoundIconButton(
                                imageVector = Icons.Default.Remove,
                                onClick = {
                                    splitByState.value =
                                        if (splitByState.value > 1) splitByState.value - 1
                                        else 1
                                    totalPerPersonState.value = calculateTotalPerPerson(
                                        totalBill = totalBillState.value.toDouble(),
                                        tipPercentage = tipPercentage,
                                        splitBy = splitByState.value)
                                })
                            Text(
                                text = "${splitByState.value}", modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 9.dp, end = 9.dp)
                            )
                            RoundIconButton(
                                imageVector = Icons.Default.Add,
                                onClick = {
                                    if (splitByState.value < range.last) {
                                        splitByState.value = splitByState.value + 1
                                    }
                                    totalPerPersonState.value = calculateTotalPerPerson(
                                        totalBill = totalBillState.value.toDouble(),
                                        tipPercentage = tipPercentage,
                                        splitBy = splitByState.value)
                                })

                        }

                        //Tip Row
                    }
                    Row(modifier = Modifier) {
                        Text(
                            text = "Text",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(200.dp))
                        Text(
                            text = "$ ${tipAmountState.value}",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "${tipPercentage}%", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(14.dp))

                        //Slider
                        Slider(
                            value = sliderPositionState.value,
                            onValueChange = { newVal ->
                                sliderPositionState.value = newVal
                                tipAmountState.value = calculateTotalTip(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage
                                )
                                totalPerPersonState.value = calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy = splitByState.value
                                )
                            },
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            //steps = 5
                        )
                    }
                } else {
                    Box() {}
                }
            }
        }
    }
}



