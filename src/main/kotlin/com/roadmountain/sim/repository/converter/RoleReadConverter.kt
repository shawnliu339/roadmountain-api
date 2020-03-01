package com.roadmountain.sim.repository.converter

import com.roadmountain.sim.domain.enum.Role
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import javax.swing.text.Document

@ReadingConverter
class RoleReadConverter : Converter<Document, Role> {

    override fun convert(source: Document): Role {
        return Role.valueOf(source.getProperty("value").toString())
    }
}