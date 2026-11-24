package org.ldv.chatbotndy.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URI

@Configuration
class RestClientConfig(@Value("\${ia.proxy:}") private val proxyConfig: String) {
    @Bean
    fun restClientBuilder(): RestClient.Builder {
        val factory = SimpleClientHttpRequestFactory().apply {
            proxySysteme()?.let { setProxy(it) }
            setConnectTimeout(10000)
            setReadTimeout(45000)
        }
        return RestClient.builder().requestFactory(factory)
    }

    private fun proxySysteme(): Proxy? {
        val valeur = proxyConfig.takeIf { it.isNotBlank() } ?: return null
        return try {
            val uri = URI(if ("://" in valeur) valeur else "http://$valeur")
            require(!uri.host.isNullOrBlank()) { "Hôte de proxy manquant" }
            Proxy(Proxy.Type.HTTP, InetSocketAddress(uri.host, if (uri.port > 0) uri.port else 8080))
        } catch (e: Exception) {
            println("Configuration proxy invalide : connexion directe.")
            null
        }
    }
}
