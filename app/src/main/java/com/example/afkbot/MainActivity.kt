package com.example.afkbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinecraftAfkBotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AfkBotApp(this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun AfkBotApp(context: android.content.Context) {
    var serverAddress by remember { mutableStateOf("play.example.com:25565") }
    var username by remember { mutableStateOf("Steve") }
    var isRunning by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf("Idle walk") }
    var actionLog by remember { mutableStateOf(listOf<String>()) }
    var walkInterval by remember { mutableStateOf("3000") }
    var turnInterval by remember { mutableStateOf("5000") }
    var jumpInterval by remember { mutableStateOf("10000") }

    val afkController = remember { AfkController(context) }
    val coroutineScope = rememberCoroutineScope()

    val startAfk = {
        isRunning = true
        coroutineScope.launch {
            val config = AfkConfig(
                mode = selectedMode,
                walkInterval = walkInterval.toLongOrNull() ?: 3000,
                turnInterval = turnInterval.toLongOrNull() ?: 5000,
                jumpInterval = jumpInterval.toLongOrNull() ?: 10000
            )
            afkController.startAFK(config)
            actionLog = afkController.getActionLog()
        }
    }

    val stopAfk = {
        afkController.stopAFK()
        isRunning = false
        actionLog = afkController.getActionLog()
    }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            actionLog = afkController.getActionLog()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Minecraft AFK Bot",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
                Text(
                    text = "Local helper app for AFK loops",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        // Connection settings
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Server Connection",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = serverAddress,
                        onValueChange = { if (!isRunning) serverAddress = it },
                        label = { Text("Server Address") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isRunning,
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { if (!isRunning) username = it },
                        label = { Text("Player Name") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isRunning,
                        singleLine = true
                    )
                }
            }
        }

        // AFK Mode selection
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "AFK Mode",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    val modes = listOf("Idle walk", "Mining", "Farming")
                    modes.forEach { mode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedMode == mode),
                                onClick = { if (!isRunning) selectedMode = mode },
                                enabled = !isRunning
                            )
                            Text(
                                text = mode,
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

        // Timing Configuration
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Timing Configuration (ms)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = walkInterval,
                        onValueChange = { if (!isRunning) walkInterval = it },
                        label = { Text("Walk Interval") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isRunning,
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = turnInterval,
                        onValueChange = { if (!isRunning) turnInterval = it },
                        label = { Text("Turn Interval") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isRunning,
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = jumpInterval,
                        onValueChange = { if (!isRunning) jumpInterval = it },
                        label = { Text("Jump Interval") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isRunning,
                        singleLine = true
                    )
                }
            }
        }

        // Control Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = startAfk,
                    enabled = !isRunning,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start AFK")
                }

                Button(
                    onClick = stopAfk,
                    enabled = isRunning,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F)
                    )
                ) {
                    Icon(Icons.Default.Stop, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Stop AFK")
                }
            }
        }

        // Status Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isRunning) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Status",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isRunning) "● RUNNING" else "○ STOPPED",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isRunning) Color(0xFF2E7D32) else Color.Gray
                        )
                    }

                    Divider()

                    Text(
                        text = "Server: ${serverAddress.ifBlank { "Not set" }}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Player: ${username.ifBlank { "Anonymous" }}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Mode: $selectedMode",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        // Activity Log
        item {
            Text(
                text = "Activity Log (${actionLog.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                if (actionLog.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No activity yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        reverseLayout = true
                    ) {
                        items(actionLog) { log ->
                            Text(
                                text = log,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = { afkController.clearLog() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear Log")
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "⚠ Legal Notice",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100)
                    )
                    Text(
                        text = "This app is a helper utility for local use only. Do not use on servers you don't own or don't have explicit permission to automate.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp),
                        color = Color(0xFF3E2723)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
