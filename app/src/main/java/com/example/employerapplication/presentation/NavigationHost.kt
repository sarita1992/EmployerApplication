package com.example.employerapplication.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.text.toIntOrNull


@Composable
fun NavigationHost(viewModel: EmployerViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            EmployerListScreen(navController, viewModel)
        }
        composable("detail/{id}") { backStack ->
            val id = backStack.arguments?.getString("id")?.toIntOrNull()
            EmployerDetailScreen(id, viewModel,navController)
        }
    }

}
