package com.target.targetcasestudy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.target.targetcasestudy.theme.AppTheme
import com.target.targetcasestudy.theme.Themes
import com.target.targetcasestudy.ui.ErrorModal
import com.target.targetcasestudy.ui.GalleryScreen
import com.target.targetcasestudy.ui.ItemDetailsUI
import com.target.targetcasestudy.utils.Screen
import com.target.targetcasestudy.viewmodels.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val itemViewModel by viewModels<ItemViewModel> { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Themes.colors.background
                ) { innerPadding ->
                    val navController = rememberNavController()
                    LaunchedEffect(Unit) {
                        itemViewModel.fetchData()
                    }
                    val isLoading by itemViewModel.isLoading.observeAsState(false)
                    val errorMessage by itemViewModel.errorMessage.observeAsState()
                    val retryAction by itemViewModel.retryAction.observeAsState()
                    Box(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = Screen.ItemListScreen.route
                        ) {
                            // Deals List Composable
                            composable(route = Screen.ItemListScreen.route) {
                                val data = itemViewModel.dealsResponse.observeAsState()
                                GalleryScreen(
                                    dealResponse = data.value?.deals,
                                    onPhotoClick = { id ->
                                        navController.navigate(
                                            Screen.ItemDetailScreen.createRoute(
                                                id = id
                                            )
                                        )
                                    }
                                )
                            }

                            // Deal Item Composable
                            composable(
                                route = Screen.ItemDetailScreen.route,
                                arguments = Screen.ItemDetailScreen.navArguments
                            ) {
                                val id = it.arguments?.getString("id")
                                if (id != null) {
                                    itemViewModel.fetchItem(id)
                                }
                                val data = itemViewModel.itemDetailsResponse.observeAsState()
                                ItemDetailsUI(
                                    detailsData = data.value,
                                    backClick = {
                                        onBackPressedDispatcher.onBackPressed()
                                    },
                                    addToCartClick = {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Item Added to cart",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            }
                        }

                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.1f))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Themes.colors.primary
                                )
                            }
                        }

                        if (errorMessage != null) {
                            ErrorModal(
                                errorMessage = errorMessage,
                                onRetry = {
                                     retryAction?.invoke()
                                },
                                onDismiss = {
                                    itemViewModel.dismissError()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
