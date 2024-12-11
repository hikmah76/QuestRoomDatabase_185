package com.example.meet9.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.meet9.ui.view.mahaiswa.DestinasiInsert
import com.example.meet9.ui.view.mahaiswa.InsertMhsView

@Composable
fun PegelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = DestinasiInsert.route){
        composable(
            route = DestinasiInsert.route
        ){
            InsertMhsView(
                onBack = {}, onNagate = {}
            )
        }
    }
}


