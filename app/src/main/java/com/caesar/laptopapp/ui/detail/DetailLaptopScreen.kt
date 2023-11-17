package com.caesar.laptopapp.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.caesar.laptopapp.R
import com.caesar.laptopapp.data.Injection
import com.caesar.laptopapp.data.ViewModelFactory
import com.caesar.laptopapp.state.UiState
import com.caesar.laptopapp.ui.theme.grey

@Composable
fun  DetailLaptopScreen(
    laptopId :Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    shareAction: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getLaptop(laptopId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.photoUrl,
                    data.name,
                    data.origin,
                    data.description,
                    onBackClick = navigateBack,
                    shareAction = shareAction
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    name: String,
    origin: String,
    description: String,
    shareAction: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = photoUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentDescription = null
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = Color.Red,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name, style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.origin), style = MaterialTheme.typography.titleSmall,
            color = grey,
        )
        Text(
            text = origin, style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.description), style = MaterialTheme.typography.titleSmall,
            color = grey,
        )
        Text(
            text = description, style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                shareAction("Check this Laptop! $description")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Text(text = stringResource(id = R.string.share).uppercase())
        }
    }
}

