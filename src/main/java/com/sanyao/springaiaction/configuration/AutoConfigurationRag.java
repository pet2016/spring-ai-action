package com.sanyao.springaiaction.configuration;


import com.sanyao.springaiaction.entity.GameTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * @author by a123
 * @description TODO
 * @date 2025/09/10 14:22
 */

@Configuration
public class AutoConfigurationRag {

    private static final Logger LOG =
            LoggerFactory.getLogger(AutoConfigurationRag.class);


    @Value("classpath:/templates/ststemPromptTemplate-1.st")
    Resource nameOfTheGameTemplateResource;

    @Bean
    Function<Flux<byte[]>, Flux<Document>> documentReader() {
        return resourceFlux -> resourceFlux
                .map(fileBytes ->
                        new TikaDocumentReader(
                                new ByteArrayResource(fileBytes))
                                .get()
                                .getFirst()).subscribeOn(Schedulers.boundedElastic());
    }

    @Bean
    Function<Flux<Document>, Flux<List<Document>>> splitter() {
        return documentFlux ->
                documentFlux
                        .map(incoming ->
                                new TokenTextSplitter().apply(List.of(incoming))).subscribeOn(Schedulers.boundedElastic());
    }

    @Bean
    Function<Flux<List<Document>>, Flux<List<Document>>>
    titleDeterminer(ChatClient.Builder chatClientBuilder) {

        ChatClient chatClient = chatClientBuilder.build();

        return documentListFlux -> documentListFlux
                .map(documents -> {
                    if (!documents.isEmpty()) {
                        Document firstDocument = documents.getFirst();

                        GameTitle gameTitle = chatClient.prompt()
                                .user(userSpec -> userSpec
                                        .text(nameOfTheGameTemplateResource)
                                        .param("document", firstDocument.getText()))
                                        .call()
                                .entity(GameTitle.class);

                        if (Objects.requireNonNull(gameTitle).title().equals("UNKNOWN")) {
                            LOG.warn("Unable to determine the name of a game; " +
                                    "not adding to vector store.");
                            documents = Collections.emptyList();
                            return documents;
                        }

                        LOG.info("Determined game title to be {}", gameTitle.title());
                        documents = documents.stream().peek(document -> {
                            document.getMetadata().put("gameTitle", gameTitle.getNormalizedTitle()); /
                        }).toList();
                    }

                    return documents;
                });
    }

    @Bean
    Consumer<Flux<List<Document>>> vectorStoreConsumer(VectorStore vectorStore) {
        return documentFlux -> documentFlux
                .doOnNext(documents -> {
                    long docCount = documents.size();
                    LOG.info("Writing {} documents to vector store.", docCount);

                    vectorStore.accept(documents);

                    LOG.info("{} documents have been written to vector store.", docCount);
                })
                .subscribe();
    }

    @Bean
    ApplicationRunner go(FunctionCatalog catalog) {
        Runnable composedFunction = catalog.lookup(null);
        return args -> {
            composedFunction.run();
        };
    }

}
