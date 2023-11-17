package com.caesar.laptopapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.caesar.laptopapp.R
import com.caesar.laptopapp.data.Injection
import com.caesar.laptopapp.data.ViewModelFactory
import com.caesar.laptopapp.model.Laptop
import com.caesar.laptopapp.state.UiState
import com.caesar.laptopapp.ui.components.LaptopItem
import com.caesar.laptopapp.ui.components.SearchBar


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getLaptop()
            }
            is UiState.Success -> {
                HomeContent(
                    laptop = uiState.data,
                    query = viewModel.query,
                    searchLaptop = viewModel::searchLaptop,
                    modifier = modifier,
                    navigateToProfile = navigateToProfile,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    laptop: List<Laptop>,
    query: String,
    modifier: Modifier = Modifier,
    searchLaptop: (String) -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            SearchBar(
                query = query,
                onQueryChange = searchLaptop,
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.about_page),
                tint = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navigateToProfile() }
                    .testTag("ProfileIcon")
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("LaptopList")
        ) {
            items(laptop.filter { it.name.contains(query, ignoreCase = true) }) { data ->
                LaptopItem(
                    image = data.photoUrl,
                    title = data.name,
                    desc = data.origin,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}