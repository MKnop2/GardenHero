package com.example.gardenherocompose

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gardenherocompose.model.Plant
import com.example.gardenherocompose.repository.PlantRepository
import com.example.gardenherocompose.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.google.firebase.database.FirebaseDatabase

private val plantRepository = PlantRepository()
private val plantList = mutableStateListOf<Plant>()

class MainActivity : ComponentActivity() {

    //private val viewModel: MainViewModel by viewModel<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GardenHeroComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    val getAllData = plantRepository.getDataFromFirestore()

                    getAllData.observe(this) { plantList.swapList(it) }
                    Column() {
                        Text(modifier = Modifier.padding(12.dp,2.dp,12.dp,0.dp),text = "GardenHero Design 1.4", color = MaterialTheme.colors.primary, fontSize = MaterialTheme.typography.h5.fontSize, fontWeight = FontWeight.Bold)
                        LazyColumn(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                                .weight(14f),
                            contentPadding = PaddingValues(all = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        )  {

                                items(items = plantList) { plant ->
                                    ExpandableCard(plant = plant)
                            }

                        }
                        Row(modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround) {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .padding(20.dp, 15.dp, 10.dp, 15.dp),
                                contentAlignment = Alignment.Center
                            ){
                                GradientButtonLeft(
                                    text = "add plant",
                                    textColor = Color.White,
                                    gradient = Brush.horizontalGradient(
                                        colors = listOf(
                                            lushDark,
                                            lushLight
                                        )
                                    )
                                ) { }
                            }
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .padding(10.dp, 15.dp, 20.dp, 15.dp),
                                contentAlignment = Alignment.Center,
                            ){
                                GradientButtonRight(
                                    text = "delete plant",
                                    textColor = Color.White,
                                    gradient = Brush.horizontalGradient(
                                        colors = listOf(
                                            sinCityDark,
                                            sinCityLight
                                        )
                                    )
                                ) { }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GradientButtonLeft(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {

    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { setShowDialog(true) })
    {
        AddDialog(showDialog, setShowDialog)
        Box(modifier = Modifier
            .background(gradient)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                maxLines = 1,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                text = text,
                color = textColor)
        }
    }
}

@Composable
fun GradientButtonRight(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { setShowDialog(true) })
    {
        DeleteDialog(showDialog, setShowDialog)
        Box(modifier = Modifier
            .background(gradient)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                maxLines = 1,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                text = text,
                color = textColor)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit){
    val context = LocalContext.current
    if (showDialog) {
        var addSpecies by remember { mutableStateOf("") }
        var addName by remember { mutableStateOf("") }
        var addSensorName by remember { mutableStateOf("") }
        var addValve by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Add new plant")
            },
            confirmButton = {
                Row(modifier = Modifier.padding(all=10.dp), horizontalArrangement = Arrangement.Center) {
                    Button(modifier = Modifier,
                        onClick = {
                            var plant = Plant(species = addSpecies,
                                    name = addName,
                                    sensorName = addSensorName,
                                    valve = addValve.toInt())
                            val firestore = FirebaseFirestore.getInstance()

                            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
                            val document = firestore.collection("plants").document()
                            plant.id = document.id
                            when (plant.species) {
                                "Trockenpflanze"   -> {plant.minWaterLevel = 30; plant.maxWaterLevel = 50; plant.picture = R.drawable.pic_trockenpflanze}
                                "Feuchtpflanze"    -> {plant.minWaterLevel = 60; plant.maxWaterLevel = 80; plant.picture = R.drawable.pic_feuchtpflanze}
                                "Sumpfpflanze"     -> {plant.minWaterLevel = 90; plant.maxWaterLevel = 100; plant.picture = R.drawable.pic_sumpfpflanze}
                            }

                            val database = FirebaseDatabase.getInstance()
                            database.getReference("plants/config/${plant.name}/MessageTag").setValue(plant.sensorName)
                            database.getReference("plants/config/${plant.name}/Species").setValue(plant.species)
                            database.getReference("plants/config/${plant.name}/MinMoisture").setValue(plant.minWaterLevel)
                            database.getReference("plants/config/${plant.name}/MaxMoisture").setValue(plant.maxWaterLevel)
                            database.getReference("plants/config/${plant.name}/Valve").setValue(plant.valve)

                            database.getReference("plants/measure/${plant.name}/MessageTag").setValue(plant.sensorName)
                            database.getReference("plants/measure/${plant.name}/Moisture").setValue(plant.currentWaterLevel)

                            val handle = document.set(plant)
                            handle.addOnSuccessListener { Log.d("Firebase", "Document saved") }
                            handle.addOnFailureListener { Log.d("Firebase", "Save failed $it") }
                            Toast.makeText(
                                context,
                                "added $addName to list",
                                Toast.LENGTH_LONG
                            ).show()

                            plantList.add(plant)
                            // Change the state to close the dialog
                            setShowDialog(false)
                        },

                        ) {
                        Text("Confirm")
                    }
                }
            },
            dismissButton = {
                Row(modifier = Modifier.padding(all=10.dp), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            // Change the state to close the dialog
                            setShowDialog(false)
                        },
                    ) {
                        Text("Dismiss")
                    }
                }
            },
            text = {
                Column() {
                    Row() {
                        var mExpanded by remember { mutableStateOf(false) }
                        val mSpecies = listOf("Trockenpflanze", "Feuchtpflanze", "Sumpfpflanze")
                        var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
                        val icon = if (mExpanded)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown
                        OutlinedTextField(value = addSpecies, onValueChange = { newText ->
                            addSpecies = newText
                        },
                            modifier = Modifier
                                .onGloballyPositioned { coordiantes -> mTextFieldSize = coordiantes.size.toSize() },
                            placeholder = {Text(text = "Species: ")},
                            //label = { Text(stringResource(R.string.addSpecies))},
                            label = { Text(text = "Species: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            trailingIcon = {
                                Icon(icon,"contentDescription",
                                    Modifier.clickable { mExpanded = !mExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                        ) {
                            mSpecies.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    addSpecies = label
                                    mExpanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                    Row() {
                        OutlinedTextField(value = addName, onValueChange = { newText ->
                            addName = newText
                        },
                            placeholder = {Text(text = "Name: ")},
                            label = { Text(text = "Name: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }
                    Row() {
                        OutlinedTextField(value = addSensorName, onValueChange = { newText ->
                            addSensorName = newText
                        },
                            placeholder = {Text(text = "Sensor: ")},
                            label = { Text(text = "Sensor: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }
                    Row() {
                        OutlinedTextField(value = addValve, onValueChange = { newText ->
                            addValve = newText
                        },
                            placeholder = {Text(text = "Valve: ")},
                            label = { Text(text = "Valve: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun DeleteDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit){
    val context = LocalContext.current
    if (showDialog) {
        var deleteByName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("select plant")
            },
            confirmButton = {
                Row(modifier = Modifier.padding(all=10.dp), horizontalArrangement = Arrangement.Center) {
                    Button(modifier = Modifier,
                        onClick = {
                            val firestore = FirebaseFirestore.getInstance()
                            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
                            val database = FirebaseDatabase.getInstance()
                            database.getReference("plants/config/$deleteByName").removeValue()
                            database.getReference("plants/measure/$deleteByName").removeValue()

                            firestore.collection("plants").whereIn("name", listOf(deleteByName))
                                .get()
                                .addOnCompleteListener {
                                    if(it.isSuccessful) {
                                        val snapshots = it.result

                                        snapshots.forEach {
                                            document ->
                                                firestore.collection("plants").document(document.id).delete()
                                        }
                                        val targetIndex = plantList.indexOfFirst { it.name == deleteByName }
                                        plantList.removeAt(targetIndex)
                                    } else
                                        Log.d("Firestore", "FAILURE")
                                }



                            Toast.makeText(
                                context,
                                "deleted $deleteByName from list",
                                Toast.LENGTH_LONG
                            ).show()
                            //items.value!!.remove(item)
                            //items.value = items.value?.filter { it != item }?.toMutableList()

                            // Change the state to close the dialog
                            setShowDialog(false)
                        },

                        ) {
                        Text("Confirm")
                    }
                }
            },
            dismissButton = {
                Row(modifier = Modifier.padding(all=10.dp), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            // Change the state to close the dialog
                            setShowDialog(false)
                        },
                    ) {
                        Text("Dismiss")
                    }
                }
            },
            text = {
                Column() {
                    Row() {
                        OutlinedTextField(value = deleteByName, onValueChange = { newText ->
                            deleteByName = newText
                        },
                            placeholder = {Text(text = "Name: ")},
                            label = { Text(text = "Name: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }
                }
            },
        )
    }
}



@Composable
fun PlantForm() {
    val context = LocalContext.current
    val speciesValue = remember { mutableStateOf(TextFieldValue)}
    val nameValue = remember { mutableStateOf(TextFieldValue)}
    val sensorValue = remember { mutableStateOf(TextFieldValue)}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
GardenHeroComposeTheme {

    }
}

