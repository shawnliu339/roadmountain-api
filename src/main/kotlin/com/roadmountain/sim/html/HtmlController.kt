package com.roadmountain.sim.html

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {
    @GetMapping
    fun home(): String {
        return "/front/dist/index.html"
    }
}