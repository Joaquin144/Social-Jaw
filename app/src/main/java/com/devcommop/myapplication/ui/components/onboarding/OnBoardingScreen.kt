package com.devcommop.myapplication.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreen() {
    val onboardingPages = listOf(
        OnboardingPage(
            title = "Welcome to App",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            imageRes = R.drawable.baseline_comment_24
        ),
        OnboardingPage(
            title = "Discover New Features",
            description = "Duis at lacus sit amet mauris commodo varius ut eget magna.",
            imageRes = R.drawable.baseline_account_circle_24
        ),
        OnboardingPage(
            title = "Get Started",
            description = "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae.",
            imageRes = R.drawable.baseline_thumb_up_24
        )
    )

    val currentPageIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = onboardingPages[currentPageIndex.value].imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = onboardingPages[currentPageIndex.value].title,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = onboardingPages[currentPageIndex.value].description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (currentPageIndex.value > 0) {
                Button(
                    onClick = { currentPageIndex.value-- },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = "Previous")
                }
            } else {
                Spacer(modifier = Modifier.width(64.dp))
            }

            Button(
                onClick = {
                    if (currentPageIndex.value < onboardingPages.size - 1) {
                        currentPageIndex.value++
                    } else {
                        // Handle onboarding completed
                    }
                },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(text = if (currentPageIndex.value < onboardingPages.size - 1) "Next" else "Get Started")
            }
        }
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)
