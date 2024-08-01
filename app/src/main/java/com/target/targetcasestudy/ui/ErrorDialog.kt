package com.target.targetcasestudy.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.target.targetcasestudy.R
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
                Text(text = stringResource(R.string.something_went_wrong),
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
                    Text(
                        stringResource(R.string.retry),
                        color = Themes.colors.buttonText)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Themes.colors.buttonText)) {
                    Text(stringResource(R.string.cancel), color = Themes.colors.primary)
                }
            }
        )
    }
}