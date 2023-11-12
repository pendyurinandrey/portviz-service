package com.portviz.portvizservice.views

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Named


@Named
@RequestScoped
class UserLoginView {
    var username: String? = null
    var password: String? = null

    fun login() {

    }

}