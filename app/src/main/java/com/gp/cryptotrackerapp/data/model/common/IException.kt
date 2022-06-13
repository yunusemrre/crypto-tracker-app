package com.gp.cryptotrackerapp.data.model.common

interface IException {
    fun getMsg(): String? {
        return ""
    }
}

class ServiceUnreachableException : IException {
    override fun getMsg(): String {
        return "Service is unreachable."
    }
}

class GeneralException : IException {
    override fun getMsg(): String {
        return "Something went wrong."
    }
}