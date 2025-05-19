package com.example.employerapplication.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.employerapplication.domain.Employer
import com.example.employerapplication.domain.ResultState

/**
 * Create By sarita
 */

@Composable
fun EmployerListScreen(navController: NavHostController, viewModel: EmployerViewModel) {
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchChange(it) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Search Employers") },
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is ResultState.Loading -> CircularProgressIndicator()
            is ResultState.Success -> {
                val employers = (state as ResultState.Success<List<Employer>>).data
                LazyColumn {
                    items(employers) { employer ->
                        EmployerUI(navController, employer)
                    }

                }
            }

            is ResultState.Empty -> Text("No results for Employers")
            is ResultState.Error -> Text("Error: ${(state as ResultState.Error).message}")
        }
    }
}

@Composable
fun EmployerUI(navController: NavController, employer: Employer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("detail/${employer.employerId}") }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                employer.employerName,
                fontWeight = FontWeight.Bold
            )
        }

    }
}
