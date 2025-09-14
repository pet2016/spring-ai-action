package com.sanyao.springaiaction.configuration;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 21:38
 */
@Configuration
public class TracingConfig {

    @Bean
    public OtlpGrpcSpanExporter otlpHttpSpanExporter(
            @Value("${otlp.tracing.url}") String url) {
        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
    }
}
