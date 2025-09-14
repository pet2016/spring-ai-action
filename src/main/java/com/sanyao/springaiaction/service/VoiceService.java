package com.sanyao.springaiaction.service;

import org.springframework.core.io.Resource;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 13:03
 */

public interface VoiceService {

    String transcribe(Resource audioFileResource);

    Resource textToSpeech(String text);
}
