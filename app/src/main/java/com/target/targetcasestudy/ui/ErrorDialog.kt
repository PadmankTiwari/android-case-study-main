package com.target.targetcasestudy.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.target.targetcasestudy.theme.Themes

@Composable
fun ErrorModal(
    errorMessage: String?,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    if (errorMessage != null) {
        AlertDialog(
            containerColor = Themes.colors.background,
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Something Went Wrong!",
                    color = Themes.colors.primaryText)
            },
            text = {
                Text(text = errorMessage,
                    color = Themes.colors.primaryText)
            },
            confirmButton = {
                Button(onClick = {
                    onRetry()
                    onDismiss()
                }, colors = ButtonDefaults.buttonColors(containerColor = Themes.colors.primary)) {
                    Text("Retry",
                        color = Themes.colors.buttonText)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Themes.colors.buttonText)) {
                    Text("Cancel", color = Themes.colors.primary)
                }
            }
        )
    }
}