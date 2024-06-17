package com.panda.wifipassword.data.api.exception

import java.io.IOException

class NoConnectivityException(message: String = "No internet connectivity!") : IOException(message)