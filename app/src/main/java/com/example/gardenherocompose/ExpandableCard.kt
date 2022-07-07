package com.example.gardenherocompose

import android.widget.ImageButton
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter.State.Empty.painter
import com.example.gardenherocompose.model.Plant
import com.example.gardenherocompose.ui.theme.Shapes
import com.example.gardenherocompose.ui.theme.light_grey
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(
    plant: Plant,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    //descriptionMaxLines: Int = 4,
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
                    ) {
                        Text(
                            modifier = androidx.compose.ui.Modifier
                                .weight(1f),
                            text = plant.species,
                            color = Color.Black,
                            fontSize = 12.sp,
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
                        Row() {
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
                                text = "Now:" + plant.currentWaterLevel.toString(),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
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
                            text = plant.currentWaterLevel.toString() + "%",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = androidx.compose.ui.Modifier.weight(3f),
                            text = "Last pour: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(6f),
                            text = plant.lastPour.toString(),
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = androidx.compose.ui.Modifier.weight(3f),
                            text = "Next pour: ",
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                        Text( modifier = androidx.compose.ui.Modifier.weight(6f),
                            text = plant.nextPour.toString(),
                            fontSize = descriptionFontSize,
                            fontWeight = descriptionFontWeight,
                            //maxLines = descriptionMaxLines,
                            overflow = TextOverflow.Ellipsis)
                    }
                    Row() {
                        Text( modifier = Modifier.weight(3f),
                            text = "Sensor name: ",
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
                            plant.species = species
                            plant.name = name
                            plant.sensorName = sensor
                            plant.valve = valve.toInt()

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
                        OutlinedTextField(value = species, onValueChange = { newText ->
                            species = newText
                        },
                            placeholder = {Text(text = plant.species)},
                            label = { Text(text = "Species: ")},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )
                        )
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
            lastPour = Date(),
            nextPour = Date(),
            sensorName = "Sensor1",
            valve = 1
        ),
    )
}