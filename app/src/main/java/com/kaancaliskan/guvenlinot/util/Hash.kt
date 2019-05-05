package com.kaancaliskan.guvenlinot.util

import android.os.Build
import java.security.MessageDigest
import java.util.Base64

/**
 * This object helps us hashing and decoding/encoding easier.
 */
object Hash {
    /**
     * This sha512 function helps us hashing password.
     */
    fun sha512(hash: String): String {
        val digest = MessageDigest.getInstance("SHA-512")
        val bytes = digest.digest(hash.toByteArray(Charsets.UTF_8))
        return bytes.fold("") { str, it -> str + "%02x".format(it) }
    }
    /**
     * This decode function decodes note.
     */
    fun decode(string: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(string.toByteArray(Charsets.UTF_8)).toString(Charsets.UTF_8)
        } else {
            android.util.Base64.decode(
                    string.toByteArray(Charsets.UTF_8),
                    android.util.Base64.DEFAULT).toString(Charsets.UTF_8)
        }
    }
    /**
     * This encode function encodes note.
     */
    fun encode(string: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(string.toByteArray(Charsets.UTF_8))
        } else {
            android.util.Base64.encodeToString(
                    string.toByteArray(Charsets.UTF_8),
                    android.util.Base64.DEFAULT)
        }
    }
}