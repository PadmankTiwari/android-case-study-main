package com.target.targetcasestudy.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.target.targetcasestudy.R
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.theme.Themes

@Composable
fun PhotoListItem(photo: Deal, onClick: () -> Unit) {
    photo.imageUrl?.let { ImageListItem(name = photo, imageUrl = it, onClick = onClick) }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageListItem(name: Deal, imageUrl: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Themes.colors.background),
        modifier = Modifier.padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlideImage(
                model = imageUrl,
                loading = placeholder(resourceId = R.drawable.ic_imageloader),
                failure = placeholder(resourceId = R.drawable.ic_placeholder),
                contentDescription = "item_image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(150.dp),
                contentScale = ContentScale.Crop
            )
            // Item details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(2.dp))
                PriceRow(modifier = Modifier.fillMaxWidth(), salePrice = name.salePrice?.displayString, regularPrice = name.regularPrice?.displayString)
                name.fulfillment?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = Themes.colors.secondaryText
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                name.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        color = Themes.colors.primaryText,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                name.aisle?.let { AvailabilityRow(inStock = name.availability.equals(
                    stringResource(
                        R.string.in_stock
                    )
                ), aisleNumber = it) }
            }
        }
    }
}

@Composable
fun PriceRow(
    modifier: Modifier,
    salePrice: String?,
    regularPrice: String?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        // Sale Price
        if (salePrice != null) {
            Text(
                text = salePrice,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(end = 4.dp),
                color = Themes.colors.primary

            )
        }

        // Regular Price
        Text(
            text = stringResource(R.string.reg, regularPrice.toString()),
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Start,
            color = Themes.colors.primaryText
        )
    }
}

@Composable
fun AvailabilityRow(
    inStock: Boolean,
    aisleNumber: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = if (inStock) stringResource(id = R.string.in_stock) else stringResource(R.string.out_of_stock),
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Start,
            color = if (inStock) Themes.colors.secondary else Themes.colors.primary
        )
        if(inStock) {
            Text(
                text = stringResource(R.string.in_aisle, aisleNumber.uppercase()),
                style = MaterialTheme.typography.bodySmall.copy(
                    textDecoration = TextDecoration.None,
                    fontSize = 14.sp
                ),
                color = Themes.colors.secondaryText
            )
        }
    }
}
