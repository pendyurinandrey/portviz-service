package com.portviz.portvizservice.views

import jakarta.enterprise.context.RequestScoped
import jakarta.faces.context.FacesContext
import jakarta.inject.Named

@Named
@RequestScoped
class LogoutView {

    fun logout() {
        FacesContext
            .getCurrentInstance()
            .externalContext
            .redirect("/logout");
    }
}