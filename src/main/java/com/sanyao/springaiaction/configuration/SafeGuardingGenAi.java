package com.sanyao.springaiaction.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;


/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 22:03
 */

@Configuration
public class SafeGuardingGenAi {


    private static final Logger LOG =
            LoggerFactory.getLogger(SafeGuardingGenAi.class);

//    @Bean
//    Function<Flux<Message<byte[]>>, Flux<Document>> documentReader() {
//        return resourceFlux -> resourceFlux
//                .map(message -> {
//                    var fileName = (String) message.getHeaders().get("file_name");
//                    LOG.info("Reading document from file: {}", fileName);
//
//                    var fileBytes = message.getPayload();
//                    var document = new TikaDocumentReader(
//                            new ByteArrayResource(fileBytes))
//                            .get()
//                            .getFirst();
//                    if (isPremiumDocument(fileName)) {
//                        document.getMetadata().put("documentType", "PREMIUM");
//                    }
//                    return document;
//                })
//                .subscribeOn(Schedulers.boundedElastic());
//    }
//
//    private boolean isPremiumDocument(String fileName) {
//        var baseFilename = fileName
//                .substring(0, fileName.toString().lastIndexOf('.'));
//        return baseFilename.endsWith("-premium");
//    }

}
