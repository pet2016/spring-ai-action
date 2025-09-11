package com.sanyao.springaiaction.exception;

/**
 * @author by a123
 * @description TODO
 * @date 2025/09/04 19:55
 */
public class AnswerNotRelevantException extends RuntimeException{

    public AnswerNotRelevantException(String question, String answer){
        super("The answer '" + answer + "' is not relevant to the question '" + question + "'.");
    }
}
