package com.sanyao.springaiaction.service;

import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/14 13:04
 */
@Service
public class OpenAiVoiceService implements VoiceService {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private final SpeechModel speechModel;

    public OpenAiVoiceService(
            OpenAiAudioTranscriptionModel transcriptionModel,
            SpeechModel speechModel) {
        this.transcriptionModel = transcriptionModel;
        this.speechModel = speechModel;
    }

    @Override
    public String transcribe(Resource audioFileResource) {
        return transcriptionModel.call(audioFileResource);
    }

    @Override
    public Resource textToSpeech(String text) {
        
//        SpeechPrompt speechPrompt = new SpeechPrompt(text);
//        SpeechResponse response = speechModel.call(speechPrompt);
//        byte[] speechBytes = response.getResult().getOutput();
//        return new ByteArrayResource(speechBytes);

//        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
//                .voice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
//                .build();
//        SpeechPrompt speechPrompt = new SpeechPrompt(text, options);
//        byte[] speechBytes = speechModel.call(text);
//        return new ByteArrayResource(speechBytes);

//        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
//                .voice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
//                .model("tts-1-hd")
//                .build();
//        SpeechPrompt speechPrompt = new SpeechPrompt(text, options);
//        byte[] speechBytes = speechModel.call(text);
//        return new ByteArrayResource(speechBytes);


        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .voice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
                .model("tts-1-hd")
                .responseFormat(
                        OpenAiAudioApi.SpeechRequest.AudioResponseFormat.AAC)
                .build();
        SpeechPrompt speechPrompt = new SpeechPrompt(text, options);
        byte[] speechBytes = speechModel.call(text);
        return new ByteArrayResource(speechBytes);

    }
}