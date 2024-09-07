package com.example.kulinar.ShopList

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kulinar.MainActivity
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.ShopList.UI.ShopLIstItem
import com.example.kulinar.ui.theme.FavorietesTopAppBarBg
import kotlinx.coroutines.launch

//@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val shopList = mainViewModel.shopList.collectAsState(initial = emptyList())
    val viewModel: ShopListViewModel = viewModel()
    val context = LocalContext.current as MainActivity
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Shop List",
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.run { open() }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp),

                            )
                    }
                },
                actions = {
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = FavorietesTopAppBarBg,
                    titleContentColor = Color.White
                )

            )
        },
    ) {
        Image(
            painter = painterResource(id = R.drawable.favorites_page_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(100.dp))

            shopList.value.forEach { item ->
                ShopLIstItem(
                    item = item,
                    mainViewModel,
                    context,
                    viewModel,
                    navController
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

        }

    }
}

