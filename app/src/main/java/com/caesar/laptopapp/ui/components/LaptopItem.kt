package com.caesar.laptopapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.caesar.laptopapp.model.DataLaptop.laptopStore
import com.caesar.laptopapp.ui.theme.LaptopAppTheme
import com.caesar.laptopapp.ui.theme.Shapes
import com.caesar.laptopapp.ui.theme.grey

@Composable
fun LaptopItem(
    image: String,
    title: String,
    desc: String,
    modifier: Modifier = Modifier,

) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Yellow)
            .clip(Shapes.medium)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = desc,
            style = MaterialTheme.typography.titleLarge,
            color = grey,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LaptopItemPreview() {
    LaptopAppTheme {
        LaptopItem(
            image = laptopStore.first().photoUrl,
            title = laptopStore.first().name,
            desc = laptopStore.first().origin,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
