package com.blisspace.app.application.exception

class AppNotFoundException(id: String) : ApplicationException("App not found: $id")