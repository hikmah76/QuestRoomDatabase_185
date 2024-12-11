package com.example.meet9

import android.app.Application
import com.example.meet9.dependeciesinjection.ConraunerApp
import com.example.meet9.dependeciesinjection.InterfaceContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ConraunerApp // Fungsi untuk menyimpan

    override fun onCreate(){
        super.onCreate()
        containerApp = ConraunerApp(this)//Membuat Instance
        //instance adalah object yang dibuat dari class
    }
}