package com.example.meet9.repository

import com.example.meet9.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun inssertMhs(mahasiswa: Mahasiswa)
}