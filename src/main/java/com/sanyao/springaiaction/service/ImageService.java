package com.sanyao.springaiaction.service;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 15:47
 */
public interface ImageService {

    String generateImageForUrl(String instructions);

    byte[] generateImageForImageBytes(String instructions);

}
