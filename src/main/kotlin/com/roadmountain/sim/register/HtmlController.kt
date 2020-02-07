package com.roadmountain.sim.register

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {
    @GetMapping("/")
    fun home(): String {
        return "index"
    }
}