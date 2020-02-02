package com.roadmountain.sim.converter

import com.roadmountain.sim.domain.enum.CustomerSuffix
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import javax.swing.text.Document

@ReadingConverter
class CustomerSuffixReadConverter : Converter<Document, CustomerSuffix> {

    override fun convert(source: Document): CustomerSuffix {
        return CustomerSuffix.valueOf(source.getProperty("value").toString())
    }
}