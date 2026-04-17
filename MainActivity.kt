package student.example.digitalsignature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalSignatureApp()
        }
    }
}

@Composable
fun DigitalSignatureApp() {
    var inputText by remember { mutableStateOf("") }
    var signature by remember { mutableStateOf("") }
    var verificationResult by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "✍️ Digital Signature App",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Enter message to sign") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(onClick = {
                    signature = signMessage(inputText)
                }) {
                    Text("Sign Message")
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Display Signature
                if (signature.isNotEmpty()) {
                    Text("📝 Digital Signature:", color = Color.Gray, fontSize = 16.sp)
                    Text(signature, color = Color.Blue, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Verify Button
                Button(onClick = {
                    verificationResult = if (verifySignature(inputText, signature)) {
                        "✅ Signature is VALID!"
                    } else {
                        "❌ Signature is INVALID!"
                    }
                }) {
                    Text("Verify Signature")
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Verification Result
                if (verificationResult.isNotEmpty()) {
                    Text(verificationResult, color = if (verificationResult.contains("VALID")) Color.Green else Color.Red, fontSize = 18.sp)
                }
            }
        }
    )
}
