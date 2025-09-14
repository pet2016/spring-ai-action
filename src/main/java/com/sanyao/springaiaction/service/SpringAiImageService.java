package com.sanyao.springaiaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.image.*;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 15:47
 */

@Service
public class SpringAiImageService implements  ImageService{

    private static final Logger LOG =
            LoggerFactory.getLogger(SpringAiImageService.class);

    private final ImageModel imageModel;

    public SpringAiImageService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public String generateImageForUrl(String instructions) {
        return generateImage(instructions, "url")
                .getResult()
                .getOutput()
                .getUrl();
    }

    @Override
    public byte[] generateImageForImageBytes(String instructions) {
        String base64Image = generateImage(instructions, "b64_json")
                .getResult()
                .getOutput()
                .getB64Json();
        return Base64.getDecoder().decode(base64Image);
    }

    private ImageResponse generateImage(String instructions, String format) {
        LOG.info("Image prompt instructions: {}", instructions);


//        ImageOptions options = AzureOpenAiImageOptions.builder()
//                .withDeploymentName("MyDeployment")
//                .withHeight(1024)
//                .withWidth(1024)
//                .withResponseFormat(format)
//                .withStyle("natural")
//                .withQuality("hd")
//                .build();

        ImageOptions options = ImageOptionsBuilder.builder()
                .width(1024)
                .height(1024)
                .responseFormat(format)
                .build();

        ImagePrompt imagePrompt =
                new ImagePrompt(instructions, options);

        return imageModel.call(imagePrompt);
    }
}
