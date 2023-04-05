package com.udemy.mytipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.udemy.mytipapp.ui.theme.MyTipAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                TopHeader()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    MyTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
        ) {
            content()
        }
    }
}
@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 134.9852){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total Per Person",
                style = MaterialTheme.typography.h5
                )
            Text(text = "$ $total",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyTipAppTheme {
       MyApp {
           Text(text = "Hello Again!")
       }
    }
}