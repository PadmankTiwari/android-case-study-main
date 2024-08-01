package com.target.targetcasestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.target.targetcasestudy.R
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.theme.Themes

@Composable
fun GalleryScreen(
    dealResponse: List<Deal>?,
    onPhotoClick: (String) -> Unit,
) {
    UI(
        dealResponse = dealResponse,
        onPhotoClick = onPhotoClick,
    )
}

@Composable
private fun UI(
    dealResponse: List<Deal>?,
    onPhotoClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBar()
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .background(Themes.colors.background)
        ) {
            LazyColumn(
                contentPadding = padding
            ) {
                dealResponse?.let { photos ->
                    itemsIndexed(photos) { index, photo ->
                        PhotoListItem(photo = photo) {
                            photo.id?.let { deal -> onPhotoClick(deal) }
                        }

                        if (index < photos.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                thickness = 0.5.dp,
                                color = Themes.colors.divider
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
) {
    Surface(
        shadowElevation = 3.dp,
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.list),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Themes.colors.primaryText,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(Themes.colors.background),
            modifier = modifier.statusBarsPadding(),
        )
    }
}