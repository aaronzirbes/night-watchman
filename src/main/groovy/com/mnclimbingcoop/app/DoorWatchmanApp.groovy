package com.mnclimbingcoop.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.mnclimbingcoop.ObjectMapperBuilder
import com.mnclimbingcoop.client.ClientBuilder

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@CompileStatic
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = [ 'com.mnclimbingcoop' ])
@EnableScheduling
@Slf4j
class DoorWatchmanApp {

    static void main(final String[] args) {
        SpringApplication.run(this, args)
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapperBuilder().build()
    }

    @Bean
    XmlMapper xmlObjectMapper() {
        return new ObjectMapperBuilder().buildXml()
    }

}
