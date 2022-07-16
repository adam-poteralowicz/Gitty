package com.apap.gitty.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.apap.gitty.R

@Composable
fun Toolbar(titleResId: Int, navigationAction: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = titleResId),
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )
        },
        navigationIcon = {
            Column(
                modifier = Modifier.padding(start = 8.dp, top = 32.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.clickable { navigationAction() }
                )
            }
        }
    )
}