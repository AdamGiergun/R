package com.example.ryanair.util

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {

    val content: String

    init {
        InputStreamReader(
            this.javaClass.classLoader?.getResourceAsStream(path)
        ).run {
            content = readText()
            close()
        }
    }
}