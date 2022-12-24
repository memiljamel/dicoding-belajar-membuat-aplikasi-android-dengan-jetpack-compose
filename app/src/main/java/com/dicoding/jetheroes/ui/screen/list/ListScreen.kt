package com.dicoding.jetheroes.ui.screen.list

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetheroes.R
import com.dicoding.jetheroes.data.HeroRepository
import com.dicoding.jetheroes.ui.ViewModelFactory
import com.dicoding.jetheroes.ui.components.CharacterHeader
import com.dicoding.jetheroes.ui.components.HeroListItem
import com.dicoding.jetheroes.ui.components.ScrollToTopButton
import com.dicoding.jetheroes.ui.components.SearchBar
import com.dicoding.jetheroes.ui.theme.JetHeroesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel(factory = ViewModelFactory(HeroRepository())),
    onNavigateToAbout: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
) {
    val groupedHeroes by viewModel.groupedHeroes.collectAsState()
    val query by viewModel.query

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { onNavigateToAbout() }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.about_page),
                        )
                    }
                },
                elevation = 0.dp
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
            val scope = rememberCoroutineScope()
            val listState = rememberLazyListState()
            val showButton: Boolean by remember {
                derivedStateOf { listState.firstVisibleItemIndex > 0 }
            }

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    SearchBar(
                        query = query,
                        onQueryChange = viewModel::search,
                        modifier = Modifier.background(MaterialTheme.colors.primary)
                    )
                }
                groupedHeroes.forEach { (initial, heroes) ->
                    stickyHeader {
                        CharacterHeader(char = initial)
                    }
                    items(heroes, key = { it.id }) { hero ->
                        HeroListItem(
                            name = hero.name,
                            photoUrl = hero.photoUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100))
                                .clickable { onNavigateToDetail(hero.id) }
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .align(Alignment.BottomCenter)
            ) {
                ScrollToTopButton(
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    JetHeroesTheme() {
        ListScreen(
            onNavigateToAbout = {},
            onNavigateToDetail = {},
        )
    }
}