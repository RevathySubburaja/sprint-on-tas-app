package com.broadcom.tanzu.appdataingest;


import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigOld {

   /* @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .build();
    }

    @Bean

    public BytesEncoder<Span> spanBytesEncoder() {
        return SpanBytesEncoder.JSON_V1;
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/
}
