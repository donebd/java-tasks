package ru.mail.polis.homework.processor;

public class ReplaceFirstProcessor extends TextProcessor {

    private static final ProcessingStage STAGE = ProcessingStage.PROCESSING;

    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regex, replacement);
    }
}