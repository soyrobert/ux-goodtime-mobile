package com.ux_goodtime_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import androidx.compose.material.TopAppBar
import androidx.compose.foundation.background
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share


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
                        composable("createAlarm") { CreateAlarmScreen(navController) }
                        composable("listOfAlarms") { AlarmScreen(navController) }
                        composable("alarmDetail") { AlarmDetailScreen() }
                        composable("historyStatistics") { HistoryStatisticsScreen(navController) }
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
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Configuración") },
            selected = false,
            onClick = { /* Acción de navegación */ },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Estadísticas") },
            label = { Text("Estadísticas") },
            selected = false,
            onClick = {
                navController.navigate("historyStatistics")
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "Alarmas") },
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
            shape = RoundedCornerShape(100.dp)  // Esquinas redondeadas
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

@Composable
fun CreateAlarmScreen(navController: androidx.navigation.NavHostController) {
    // Aquí puedes agregar el diseño de la pantalla de alarmas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Añadido padding a la columna principal
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // Sección: Título y configuración de hora
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Padding para las secciones internas
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Hora de alarma",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xFF424242)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Añadir componentes como TextFields o controles de hora aquí más tarde
                Column {
                    Row {
                        SquareTextField()
                        Column {
                            Text(text = ":",
                                fontSize = 36.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .wrapContentHeight(Alignment.CenterVertically))

                        }
                        SquareTextField()
                    }
                }
                Column {
                    AmPmRadioButtons()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Row {
                    AlarmNameWithPhotoButton()
                }
                Row {
                    AlarmOptions(navController)
                }
            }

        }
    }

}

@Composable
fun SquareTextField() {
    val textState = remember { mutableStateOf(TextFieldValue("00")) }

    Box(
        modifier = Modifier
            .size(width = 81.dp, height = 85.dp) // Tamaño del cuadrado
            .border(1.dp, Color.Gray) // Borde opcional para visualizar el cuadrado
            .padding(8.dp),
        contentAlignment = Alignment.Center // Centramos el contenido
    ) {
        BasicTextField(
            value = textState.value,
            onValueChange = { newText ->
                // Validamos si la longitud es 2 para mantener solo 2 caracteres como "00"
                if (newText.text.length <= 2) {
                    textState.value = newText
                }
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 36.sp, // Tamaño de la fuente
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically),
        )
    }
}

@Composable
fun AmPmRadioButtons() {
    var selectedOption by remember { mutableStateOf("AM") } // Inicialmente "AM" seleccionado

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding alrededor de la columna
        horizontalAlignment = Alignment.Start // Alinea los elementos a la izquierda
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (selectedOption == "AM"),
                onClick = {
                    selectedOption = "AM"
                }
            )
            Text(
                text = "AM",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp) // Espaciado entre RadioButton y texto
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre AM y PM

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (selectedOption == "PM"),
                onClick = {
                    selectedOption = "PM"
                }
            )
            Text(
                text = "PM",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp) // Espaciado entre RadioButton y texto
            )
        }
    }
}


@Composable
fun AlarmNameWithPhotoButton() {
    var textState by remember { mutableStateOf(TextFieldValue("")) } // Estado del campo de texto

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Campo de texto con placeholder
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text("Nombre de la alarma", color = Color.Gray)
            },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF006769), // Color del borde cuando está enfocado
                unfocusedBorderColor = Color(0xFF006769) // Color del borde cuando no está enfocado
            )
        )

        // Texto pequeño debajo del campo de texto
        Text(
            text = "Ingresa un nombre que puedas recordar",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp) // Espacio pequeño entre el campo de texto y la ayuda
        )

        // Botón de seleccionar foto, alineado a la izquierda
        Button(
            onClick = { /* Acción para seleccionar foto */ },
            modifier = Modifier
                .align(Alignment.Start) // Alineado a la izquierda
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006769) // Color de fondo (#006769)
            ),// Espacio pequeño entre el texto y el botón
        ) {
            Text("Seleccionar foto")
        }
    }
}


@Composable
fun AlarmOptions(navController: androidx.navigation.NavHostController) {
    // Estados para los checkboxes
    var vibrateChecked by remember { mutableStateOf(false) }
    var repeatChecked by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Checkbox para Vibrar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp), // Espacio entre los checkboxes
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Alinea a la izquierda
        ) {
            Checkbox(
                checked = vibrateChecked,
                onCheckedChange = { vibrateChecked = it }
            )
            Text(text = "Vibrar", fontSize = 18.sp)
        }

        // Checkbox para Repetir
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp), // Espacio entre los checkboxes
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Alinea a la izquierda
        ) {
            Checkbox(
                checked = repeatChecked,
                onCheckedChange = { repeatChecked = it }
            )
            Text(text = "Repetir", fontSize = 18.sp)
        }
        // Botón Seleccionar
        Button(
            onClick = { /* Acción al presionar el botón */ },
            modifier = Modifier.align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006769) // Color de fondo (#006769)
            ),
        ) {
            Text(text = "Seleccionar (Clasic)")
        }
        // Texto pequeño debajo del campo de texto
        Text(
            text = "Selecciona tu sonido favorito",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp) // Espacio pequeño entre el campo de texto y la ayuda
        )

        // Campo de texto para descripción
        Column(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color(0xFF006769)), RoundedCornerShape(8.dp))
                    .padding(16.dp),
                singleLine = true,

                )
            Text(
                text = "La descripción le ayudará a recordar mejor el propósito de la alarma.",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }

        // Botones Salir y Guardar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navController.navigate("home")
                },
                colors = ButtonDefaults.run { val buttonColors =
                    buttonColors(Color(0xFF66BB6A))
                    buttonColors
                }, // Verde claro
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(50) // Esquinas redondeadas
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Salir")
                Text(text = " Salir", color = Color.White)
            }

            Button(
                onClick = {
                    showDialog = true
                },
                colors = ButtonDefaults.run {
                    val buttonColors = buttonColors(Color(0xFF006769))
                    buttonColors
                }, // Color específico
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(50) // Esquinas redondeadas
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Guardar")
                Text(text = " Guardar", color = Color.White)
            }


            // Muestra el Popup si showDialog es true
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            "",
                            color = Color(0xFF006769)
                        )
                    },
                    text = {
                        Text(
                            text = "Alarma Agregada.",
                            color = Color(0xFF006769),
                            fontSize = 20.sp
                        )},
                    confirmButton = {}
                )

                // Esto es para cerrar el popup automaticamente.
                LaunchedEffect(Unit) {
                    delay(2000)
                    showDialog = false
                    navController.navigate("listOfAlarms")
                }

            }

        }

    }
}


@Composable
fun AlarmScreen(navController: androidx.navigation.NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp) // Padding general
    ) {
        Spacer(modifier = Modifier.height(30.dp)) // Espacio superior
        Text(
            text = "Tus alarmas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp) // Espacio inferior del título
        )
        AlarmList(navController)
    }
}

@Composable
fun AlarmList(navController: androidx.navigation.NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AlarmItem(
            imageRes = R.drawable.image1, // Reemplazar por la imagen correcta
            title = "Despertar",
            navController = navController,
            time = "06:00 AM"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmItem(
            imageRes = R.drawable.image2, // Reemplazar por la imagen correcta
            title = "Preparar almuerzo",
            navController = navController,
            time = "11:00 AM"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmItem(
            imageRes = R.drawable.image3, // Reemplazar por la imagen correcta
            title = "Recoger a Felipe del jardín",
            navController = navController,
            time = "02:00 PM"
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmItem(
            imageRes = R.drawable.image4, // Reemplazar por la imagen correcta
            title = "Reunión de proyecto",
            navController = navController,
            time = "03:30 PM"
        )
    }
}

@Composable
fun AlarmItem(imageRes: Int, title: String, time: String, navController: androidx.navigation.NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = time,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    TextButton(onClick = { /* Acción compartir */ }) {
                        Text(text = "Compartir", fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { /* Acción editar */ }) {
                        Text(text = "Editar", fontSize = 12.sp)
                    }
                }
            }
            IconButton(onClick = { navController.navigate("alarmDetail") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right), // Reemplazar con el icono correcto
                    contentDescription = null,
                    modifier = Modifier.size(24.dp) // Tamaño del icono ajustado
                )
            }
        }
    }
}

// Detalle de alarma


class AlarmDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmDetailScreen()
        }
    }
}

@Composable
fun AlarmDetailScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Despertar", fontSize = 22.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("listOfAlarms") { // Especifica la ruta de la pantalla
                            popUpTo("listOfAlarms") { inclusive = true } // Elimina las pantallas intermedias
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                },
                backgroundColor = Color(0xFF006769),
                contentColor = Color.White,
                modifier = Modifier.height(72.dp)
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Acción de editar */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006769),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar", color = Color.White)
                }
            }
            // Hora
            Text(
                text = "Hora",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF292929),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "06:00 PM",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF292929)
            )

            // Imagen y título
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.despertar),
                    contentDescription = "Alarm Image",
                    modifier = Modifier
                        .height(200.dp)
                        .width(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Despertar",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        IconButton(onClick = { /* Acción compartir */ }) {
                            Icon(Icons.Default.Share, contentDescription = "Compartir", tint = Color(0xFF292929)) // Icono de compartir
                        }
                        Text(
                            text = "Compartir",
                            fontSize = 14.sp,
                            color = Color(0xFF006769)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = "A despertar! Vamos con Toda!!!",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )


            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            // Sección de opciones (Vibrar, Repetir, Clásico)
            AlarmOptionsSection()

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAB0130))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.borrar),
                    contentDescription = "Eliminar",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Eliminar", color = Color.White)
            }
        }
    }
}

@Composable
fun AlarmOptionsSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.check),
                contentDescription = null,
                tint = Color(0xFF292929),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Vibrar", fontSize = 18.sp, color = Color(0xFF292929))
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Opción Repetir (icono ajustado)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.check),
                contentDescription = null,
                tint = Color(0xFF292929),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Repetir", fontSize = 18.sp, color = Color(0xFF292929))
        }

        Spacer(modifier = Modifier.width(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.nota),
                contentDescription = null,
                tint = Color(0xFF292929),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Clásico", fontSize = 18.sp, color = Color(0xFF292929))
        }
    }
}

// ***********************************************************
// Diseño de la pantalla de estadisticas
// ***********************************************************

@Composable
fun HistoryStatisticsScreen(navController: androidx.navigation.NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Historial y estadísticas",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color(0xFF006769) // Color personalizado de la barra superior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), // Añade un padding para los componentes internos
            verticalArrangement = Arrangement.SpaceBetween // Posiciona los componentes, dejando espacio entre ellos
        ) {
            Column {
                // History Button
                Button(
                    onClick = { /* Acción para navegar al historial */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006769) // Color de fondo
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Historial",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Historial",
                            tint = Color.White
                        )
                    }
                }

                // Statistics Button
                Button(
                    onClick = { /* Acción para navegar a las estadísticas */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006769) // Color de fondo
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Estadísticas",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Estadísticas",
                            tint = Color.White
                        )
                    }
                }
            }

            // Cancel Button at the bottom
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally) // Alinea el botón al centro horizontal
                    .padding(vertical = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006769)
                ),
                shape = RoundedCornerShape(50) // Esquinas redondeadas
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Icono de flecha atrás
                    contentDescription = "Cancelar",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el icono y el texto
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

