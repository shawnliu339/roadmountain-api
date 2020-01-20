package com.roadmountain.sim.register

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RegisterController {
    @GetMapping("/")
    fun home(): String {
        return "register"
    }
}