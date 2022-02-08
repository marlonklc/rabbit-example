package com.asyncexample.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepTest {

    @Test
    public void shouldReturnNextStepWhenHave() {
        Step actual = Step.TODO;
        Step expected = actual.next();

        assertEquals(Step.DOING, expected);
    }

    @Test
    public void shouldReturnSameStepWhenIsLastStep() {
        Step actual = Step.FINISH;
        Step expected = actual.next();

        assertEquals(expected, actual);
    }

}
