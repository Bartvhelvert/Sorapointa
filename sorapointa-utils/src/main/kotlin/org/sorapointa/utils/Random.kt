package org.sorapointa.utils

import mu.KotlinLogging
import org.sorapointa.utils.encoding.hex
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.random.nextUInt
import kotlin.random.nextULong

private val logger = KotlinLogging.logger {}

val secureRandom: Random
    get() = SecureRandom.getInstanceStrong()
        ?: SecureRandom.getInstance("NativePRNG")
        ?: SecureRandom.getInstance("SHA1PRNG")
        ?: run {
            logger.warn {
                "No SecureRandom Instance found, fallback to normal Random, " +
                    "please change your JDK vendor for better safety."
            }
            ThreadLocalRandom.current()
        }

fun randomByteArray(length: Int): ByteArray {
    val bytes = ByteArray(length)
    secureRandom.nextBytes(bytes)
    return bytes
}

fun randomUInt(): UInt =
    kotlin.random.Random.nextUInt()

fun randomULong(): ULong =
    kotlin.random.Random.nextULong()

fun randomUByteArray(length: UInt): UByteArray =
    randomByteArray(length.toInt()).toUByteArray()

fun sha256sign(data: String, key: String): String {
    val sha256Hmac = Mac.getInstance("HmacSHA256")
    val secretKey = SecretKeySpec(key.toByteArray(), "HmacSHA256")
    sha256Hmac.init(secretKey)
    return sha256Hmac.doFinal(data.toByteArray()).hex
}