package student.example.digitalsignature

import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature


val keyPair: KeyPair = generateKeyPair()
val publicKey: PublicKey = keyPair.public
val privateKey: PrivateKey = keyPair.private

fun generateKeyPair(): KeyPair {
    val keyGen = KeyPairGenerator.getInstance("RSA")
    keyGen.initialize(2048) // 2048-bit key size
    return keyGen.generateKeyPair()
}


fun signMessage(message: String): String {
    val signature = Signature.getInstance("SHA256withRSA")
    signature.initSign(privateKey)
    signature.update(message.toByteArray())
    val signedBytes = signature.sign()
    return Base64.encodeToString(signedBytes, Base64.DEFAULT)
}


fun verifySignature(message: String, signedMessage: String): Boolean {
    return try {
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initVerify(publicKey)
        signature.update(message.toByteArray())
        val decodedSignature = Base64.decode(signedMessage, Base64.DEFAULT)
        signature.verify(decodedSignature)
    } catch (e: Exception) {
        false
    }
}


