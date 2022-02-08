package com.asyncexample.dto;

public enum Step {

    TODO,
    DOING,
    ANALYSIS,
    FINISH,
    ;

    private Step next;

    static {
        TODO.next = DOING;
        DOING.next = ANALYSIS;
        ANALYSIS.next = FINISH;
        FINISH.next = FINISH;
    }

    public static Step first() {
        return TODO;
    }

    public Step next() {
        return this.next;
    }
}
