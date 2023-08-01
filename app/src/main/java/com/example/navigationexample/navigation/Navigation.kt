package com.example.navigationexample.navigation

import com.example.navigationexample.Accounts
import com.example.navigationexample.Home
import com.example.navigationexample.Notifications
import com.example.navigationexample.Settings
import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun NavigationController (navController: NavHostController){
    NavHost(navController = navController, startDestination = NavigationItem.Home.route){
        composable (NavigationItem.Home.route) {
            Home()
        }
        composable (NavigationItem.Account.route){
            Accounts()
        }
        composable(NavigationItem.Notifications.route){
            Notifications()
        }
        composable(NavigationItem.Settings.route){
            Settings()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigation () {
    val navController = rememberNavController()
    val screenList = listOf(
        NavigationItem.Home,
        NavigationItem.Account,
        NavigationItem.Settings,
        NavigationItem.Notifications
    )
    Scaffold (
        topBar = { TopAppBar() { Text(text = "bottom navigation bar")}},
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colors.background) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route


                screenList.forEach {
                    BottomNavigationItem(selected = currentRoute == it.route,
                        label = {
                            Text(
                                text = it.label,
                                color = if (currentRoute == it.route) Color.DarkGray else Color.LightGray
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = it.icons, contentDescription = null,
                                tint = if (currentRoute == it.route) Color.DarkGray else Color.LightGray
                            )

                        },
                        onClick = {
                            if(currentRoute!=it.route){

                                navController.graph?.startDestinationRoute?.let {
                                    navController.popBackStack(it,true)
                                }

                                navController.navigate(it.route){
                                    launchSingleTop = true
                                }
                            }
                        })
                }
            }
        }) {
        NavigationController(navController = navController)
    }
}