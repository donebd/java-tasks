package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private Integer max = Integer.MIN_VALUE;

    public Integer getMaxValue() {
        if (size() == 0) {
            throw new EmptyStackException();
        }
        return max;
    }

    @Override
    public Integer push(Integer item) {
        if (item > max) {
            max = item;
        }
        addElement(item);

        return item;
    }

}
