package com.portviz.portvizservice.config

import jakarta.faces.webapp.FacesServlet
import jakarta.servlet.ServletContext
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsfConfiguration {

    @Bean
    fun jsfServletRegistration(servletContext: ServletContext): ServletRegistrationBean<FacesServlet> {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", "true")

        val srb = ServletRegistrationBean(FacesServlet(), "*.xhtml")
        srb.setLoadOnStartup(1)
        return srb
    }
}