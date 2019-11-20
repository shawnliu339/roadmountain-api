package com.shawn.ec.csv

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CsvController {
    @GetMapping("/hello")
    fun helloWorld(): String {
        return "hello world"
    }
}