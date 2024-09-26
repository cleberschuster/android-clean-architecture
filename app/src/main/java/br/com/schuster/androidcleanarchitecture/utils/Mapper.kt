package br.com.schuster.androidcleanarchitecture.utils

interface Mapper<S, T> {
    fun map(source: S): T
}