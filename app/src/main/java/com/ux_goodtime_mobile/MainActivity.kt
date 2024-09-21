package com.ux_goodtime_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Scaffold (bottomBar = {
                    BottomNavigationBar(navController)
                }){
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController) }
                        composable("createAlarm") { CreateAlarmScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {

    BottomNavigation (
        backgroundColor = Color(0xFF006769), // Color de fondo personalizado (verde oscuro)
        contentColor = Color.White           // Color del contenido (íconos y texto)
    ){
        BottomNavigationItem(
            icon = { Icon(androidx.compose.material.icons.Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Configuración") },
            selected = false,
            onClick = { /* Acción de navegación */ },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
        BottomNavigationItem(
            icon = { Icon(androidx.compose.material.icons.Icons.Default.Notifications, contentDescription = "Estadísticas") },
            label = { Text("Estadísticas") },
            selected = false,
            onClick = { /* Acción de navegación */ },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
        BottomNavigationItem(
            icon = { Icon(androidx.compose.material.icons.Icons.Default.Add, contentDescription = "Alarmas") },
            label = { Text("Alarmas") },
            selected = true, // Marca la opción actual como seleccionada
            onClick = {
                navController.navigate("createAlarm")
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
    }
}


@Composable
fun HomeScreen(navController: androidx.navigation.NavHostController) {
    CenteredImageScreen()
    WelcomeText()
    AddAlarmButton(navController)
}

@Composable
fun CreateAlarmScreen() {
    // Aquí puedes agregar el diseño de la pantalla de alarmas
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = "Crear Alarma!",
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center
    )
}

// ***********************************************************
// Logo
// ***********************************************************
@Composable
fun CenteredImageScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.schedule),
            contentDescription = "Centered Image",
            modifier = Modifier
                .size(100.dp)
                .offset(y = (-250).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCenteredImageScreen() {
    CenteredImageScreen()
}

// ***********************************************************
// Welcome text
// ***********************************************************
@Composable
fun WelcomeText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 210.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Hola!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Te damos la",
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Text(
            text = "bienvenida a\n\nGoodTime",
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Aun no tienes \nalarmas",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color(0xFF424242)
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeText() {
    WelcomeText()
}


// ***********************************************************
// Add alarm button
// ***********************************************************
@Composable
fun AddAlarmButton(navController: androidx.navigation.NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 200.dp),  // Ajusta la posición vertical si es necesario
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = { navController.navigate("createAlarm") },
            modifier = Modifier
                .width(214.dp)  // Ajusta el ancho según necesites
                .height(69.dp), // Ajusta la altura según lo desees
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006769) // Color de fondo (#006769)
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(100.dp)  // Esquinas redondeadas
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "+ Agregar alarma",
                    fontSize = 14.sp,
                    color = Color.White  // Color del texto en blanco
                )
            }
        }
    }
}
