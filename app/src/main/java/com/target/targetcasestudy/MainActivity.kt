package com.target.targetcasestudy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.target.targetcasestudy.theme.AppTheme
import com.target.targetcasestudy.theme.Themes
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
                    itemViewModel.fetchData()
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
                                    Toast.makeText(this@MainActivity, "Item Added to cart", Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
