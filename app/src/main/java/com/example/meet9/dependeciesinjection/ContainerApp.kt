package com.example.meet9.dependeciesinjection

import android.content.Context
import com.example.meet9.data.database.KrsDatabase
import com.example.meet9.repository.LocalRepositoryMhs
import com.example.meet9.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ConraunerApp(private val context: Context) : InterfaceContainerApp{
    override val  repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}