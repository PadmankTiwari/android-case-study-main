package com.target.targetcasestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.target.targetcasestudy.R
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.theme.Themes

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemDetailsUI(
    detailsData: Deal?,
    backClick: () -> Unit,
    addToCartClick: () -> Unit
) {
    Scaffold(
        topBar = {
            DetailsTopBar(
                backClick = backClick
            )
        },
        bottomBar = {
            DetailsBottomBar(
                addToCartClick = addToCartClick
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Themes.colors.background)
                .padding(16.dp)) {
                GlideImage(
                    model = detailsData?.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .height(400.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = detailsData?.title ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                PriceRow(
                    modifier = Modifier.wrapContentWidth(),
                    detailsData?.salePrice?.displayString,
                    detailsData?.regularPrice?.displayString
                )
                Text(
                    text = detailsData?.fulfillment ?: "",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier
                .background(Themes.colors.background)
                .padding(16.dp)) {

                Text(
                    text = stringResource(R.string.product_details),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Themes.colors.primaryText
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = detailsData?.description ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp
                    ),
                    color = Themes.colors.secondaryText
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsTopBar(
    modifier: Modifier = Modifier,
    backClick: () -> Unit
) {
    Surface(shadowElevation = 3.dp) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.details),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Themes.colors.primaryText,
                    modifier = Modifier.padding(start = 4.dp)
                )
            },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable(onClick = backClick),
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    tint = Themes.colors.primary,
                    contentDescription = "Back",
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(Themes.colors.background),
            modifier = modifier.statusBarsPadding(),
        )
    }
}

@Composable
private fun DetailsBottomBar(
    modifier: Modifier = Modifier,
    addToCartClick: () -> Unit
) {
    Surface(shadowElevation = 4.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) {
        Box(
            modifier = Modifier
                .background(Themes.colors.background)
                .fillMaxWidth()
        ) {
            Button(
                onClick = addToCartClick,
                modifier = modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Themes.colors.primary
                ),
                shape = RoundedCornerShape(4.dp),
                enabled = true
            ) {
                Text(
                    text = stringResource(R.string.add_to_cart),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Themes.colors.buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
