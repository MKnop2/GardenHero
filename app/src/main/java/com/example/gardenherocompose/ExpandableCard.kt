package com.example.gardenherocompose

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.gardenherocompose.model.Plant
import com.example.gardenherocompose.ui.theme.Shapes
import com.example.gardenherocompose.ui.theme.light_grey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(
    plant: Plant,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    shape: CornerBasedShape = Shapes.medium,
    padding: Dp = 3.dp
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if(expandedState) 180f else 0f)


    Card(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,
        backgroundColor = light_grey,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(padding)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = androidx.compose.ui.Modifier
                    .weight(2.6f)
                    .heightIn(0.dp, 70.dp),
                ) {
                    /*Image(
                        painter = painterResource(id = plant.picture),
                        contentDescription = "Plant Picture")*/
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = plant.picture),
                        contentDescription = "Plant Picture")
                }
                Spacer(modifier = androidx.compose.ui.Modifier.width(4.dp))
                Box(
                    modifier = androidx.compose.ui.Modifier.weight(10f),

                    ) {
                    Column(
                        modifier = androidx.compose.ui.Modifier
                            .height(70.dp)
                            .padding(0.dp, 5.dp, 0.dp , 5.dp)
                    ) {
                        Text(
                            modifier = androidx.compose.ui.Modifier
                                .weight(1f),
                            text = plant.species,
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = androidx.compose.ui.Modifier
                                .weight(1.5f),
                            text = plant.name,
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                        /*Row() {
                            Text(
                                modifier = androidx.compose.ui.Modifier
                                    .weight(1.5f),
                                text = "Min:" + plant.minWaterLevel.toString(),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = androidx.compose.ui.Modifier
                                    .weight(1.5f),
                                text = "Max:" + plant.maxWaterLevel.toString(),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = androidx.compose.ui.Modifier
                                    .weight(1.5f),
                                text = "Now: " + plant.currentWaterLevel.toString(),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }*/
                        var progress = plant.currentWaterLevel.toString().toFloat()

                        when (plant.species) {
                            "Trockenpflanze"   -> {progress = ((progress-10))/20}
                            "Feuchtpflanze"    -> {progress = ((progress-40))/20}
                            "Sumpfpflanze"     -> {progress = ((progress-70))/20}
                        }
                        Log.d("UPDATE", "progress from ${plant.name} is: $progress")
                        if (progress <= 0.0f){
                            progress = 0.0f
                        }
                        else if (progress >= 1.0f){
                            progress = 1.0f
                        }
                        when (plant.species) {
                            "Trockenpflanze"   -> {LinearProgressIndicator(progress = progress, modifier = Modifier.height(7.dp).weight(0.75f))}
                            "Feuchtpflanze"    -> {LinearProgressIndicator(progress = progress, modifier = Modifier.height(7.dp).weight(0.75f))}
                            "Sumpfpflanze"     -> {LinearProgressIndicator(progress = progress, modifier = Modifier.height(7.dp).weight(0.75f))}
                        }
                    }
                }
                Spacer(modifier = androidx.compose.ui.Modifier.width(4.dp))
                Box(
                    modifier = androidx.compose.ui.Modifier.weight(1.5f),

                ) {
                    IconButton(
                        modifier = androidx.compose.ui.Modifier
                            .alpha(ContentAlpha.medium)
                            .rotate(rotationState),
                        onClick = {
                            expandedState = !expandedState
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }
                }
            }
            if(expandedState){
                val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
                Column(modifier = androidx.compose.ui.Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text( modifier = androidx.compose.ui.Modifier.weight(7f),
                            text = "Name: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(12f),
                            text = plant.name,
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        IconButton( modifier = androidx.compose.ui.Modifier
                            .weight(2f)
                            .height(20.dp),
                            onClick = { setShowDialog(true) }) {
                            EditDialog(showDialog, setShowDialog, plant = plant)
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit-Button")
                            }
                    }
                    Row() {
                        Text( modifier = androidx.compose.ui.Modifier.weight(3f),
                            text = "Species: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(6f),
                            text = plant.species,
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = androidx.compose.ui.Modifier.weight(3f),
                            text = "Water level: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(6f),
                            text = plant.currentWaterLevel.toString() + "% (Range: " + plant.minWaterLevel.toString() + "% bis " + plant.maxWaterLevel.toString() + "%)",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = androidx.compose.ui.Modifier.weight(3f),
                            text = "Hinzugefügt: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(6f),
                            text = plant.addedDate.toString(),
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = Modifier.weight(3f),
                            text = "Sensor: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = Modifier.weight(6f),
                            text = plant.sensorName,
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = androidx.compose.ui.Modifier.weight(3f),
                            text = "Valve: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(6f),
                            text = plant.valve.toString(),
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Composable
fun EditDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit, plant: Plant){
    if (showDialog) {
        var species by remember { mutableStateOf(plant.species) }
        var name by remember { mutableStateOf(plant.name) }
        var sensor by remember { mutableStateOf(plant.sensorName) }
        var valve by remember { mutableStateOf(plant.valve.toString()) }
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("edit plant")
            },
            confirmButton = {
                Row(modifier = Modifier.padding(all=10.dp), horizontalArrangement = Arrangement.Center) {
                    Button(modifier = Modifier,
                        onClick = {
                            val oldName = plant.name
                            plant.species = species
                            plant.name = name
                            plant.sensorName = sensor
                            plant.valve = valve.toInt()

                            val firestore = FirebaseFirestore.getInstance()
                            val database = FirebaseDatabase.getInstance()

                            when (plant.species) {
                                "Trockenpflanze"   -> {plant.minWaterLevel = 10; plant.maxWaterLevel = 30; plant.currentWaterLevel = 11; plant.picture = R.drawable.pic_trockenpflanze}
                                "Feuchtpflanze"    -> {plant.minWaterLevel = 40; plant.maxWaterLevel = 60; plant.currentWaterLevel = 41; plant.picture = R.drawable.pic_feuchtpflanze}
                                "Sumpfpflanze"     -> {plant.minWaterLevel = 70; plant.maxWaterLevel = 90; plant.currentWaterLevel = 71; plant.picture = R.drawable.pic_sumpfpflanze}
                            }

                            firestore.collection("plants").whereIn("name", listOf(oldName))
                                .get()
                                .addOnCompleteListener {
                                    if(it.isSuccessful) {
                                        val snapshots = it.result
                                        Log.d("Ausgabe", plant.name)
                                        snapshots.forEach {
                                                document ->
                                            Log.d("Ausgabe", "TRUE")

                                            firestore.collection("plants").document(document.id).update(
                                                mapOf("species" to plant.species,
                                                    "name" to plant.name,
                                                    "sensor" to plant.sensorName,
                                                    "valve" to plant.valve,
                                                    "maxWaterLevel" to plant.maxWaterLevel,
                                                    "minWaterLevel" to plant.minWaterLevel,
                                                    "picture" to plant.picture)
                                            )

                                            database.getReference("plants/config/${plant.name}/MessageTag").setValue(plant.sensorName)
                                            database.getReference("plants/config/${plant.name}/Species").setValue(plant.species)
                                            database.getReference("plants/config/${plant.name}/MinMoisture").setValue(plant.minWaterLevel)
                                            database.getReference("plants/config/${plant.name}/MaxMoisture").setValue(plant.maxWaterLevel)
                                            database.getReference("plants/config/${plant.name}/Valve").setValue(plant.valve)

                                            database.getReference("plants/configStatus/isDirty").setValue(true)
                                            database.getReference("plants/configStatus/MessageTag").setValue(plant.name)
                                            database.getReference("plants/configStatus/toDelete").setValue(false)
                                        }
                                    } else
                                        Log.d("Firestore", "FAILURE")
                                }

                            firestore.collection("plants").document()

                            // Change the state to close the dialog
                            setShowDialog(false)
                        },
                    ) { Text("Confirm") }
                }
            },
            dismissButton = {
                Row(modifier = Modifier.padding(all=10.dp), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            // Change the state to close the dialog
                            setShowDialog(false)
                        },
                    ) { Text("Dismiss") }
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
                        OutlinedTextField(value = species, onValueChange = { newText ->
                            species = newText
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
                                    species = label
                                    mExpanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                    Row() {
                        OutlinedTextField(value = name, onValueChange = { newText ->
                            name = newText
                        },
                            placeholder = {Text(text = plant.name)},
                            label = { Text(text = "Name: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }
                    Row() {
                        OutlinedTextField(value = sensor, onValueChange = { newText ->
                            sensor = newText
                        },
                            placeholder = {Text(text = plant.sensorName)},
                            label = { Text(text = "Sensor: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
                    }
                    Row() {
                        OutlinedTextField(value = valve, onValueChange = { newText ->
                            valve = newText
                        },
                            placeholder = {Text(text = plant.valve.toString())},
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
@Preview
fun ExpandableCardPreview(){
    ExpandableCard(
        plant = Plant(
            id = "0",
            picture = R.drawable.ic_launcher_background,
            name = "Björn",
            species = "Cannabis",
            minWaterLevel = 60,
            maxWaterLevel = 85,
            currentWaterLevel = 62,
            addedDate = Date(),
            sensorName = "Sensor1",
            valve = 1
        ),
    )
}