package com.ernestschcneider.marsrovernavigator.feature.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ernestschcneider.marsrovernavigator.view.R
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme

@Composable
fun Title() {
    Text(
        stringResource(R.string.app_name),
        //color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineMedium
    )
}

@PreviewLightDark
@Composable
fun PreviewTitle() {
    MarsRoverNavigatorTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Title()
        }
    }
}
