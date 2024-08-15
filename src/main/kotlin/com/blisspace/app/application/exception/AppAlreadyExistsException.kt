package com.blisspace.app.application.exception

class AppAlreadyExistsException(id: String) : ApplicationException("App already exists: $id")