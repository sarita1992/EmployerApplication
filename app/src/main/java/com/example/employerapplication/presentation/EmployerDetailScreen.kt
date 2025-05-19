package com.example.employerapplication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employerapplication.domain.ResultState

/**
 * Create By sarita
 */

@Composable
fun EmployerDetailScreen(id: Int?, viewModel: EmployerViewModel, navController: NavController) {
    val state by viewModel.state.collectAsState()
    val employer = (state as? ResultState.Success)?.data?.find { it.employerId == id }
    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        Spacer(modifier = Modifier.height(16.dp))

        Name(employer?.employerName.orEmpty())
        SubDetail("ID: ${employer?.employerId}")
        SubDetail("Discount: ${employer?.discountPercentage}%")
        SubDetail("Location: ${employer?.employerPlace}")
    }
}

@Composable
fun Name(employerData: String) {
    Text(
        text = employerData,
        modifier = Modifier.absolutePadding(8.dp,16.dp,8.dp,8.dp),
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 26.sp),
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun SubDetail(employerData: String) {
    Text(
        text = employerData,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp),
        color = MaterialTheme.colorScheme.primary
    )
}