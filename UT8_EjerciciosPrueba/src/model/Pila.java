package model;

import java.util.Stack;

public class Pila extends Stack<Integer> {

    public Pila() {
        super();
    }

    @Override
    public Integer push(Integer num) {
        if (num > 2 && num < 20) {
            super.push(num);
        }
        return num;
    }

    public void push(Integer[] nums) {
        for (Integer num : nums) {
            push(num);
        }
    }

    @Override
    public Integer pop() {
        return !isEmpty() ? super.pop() : null;
    }

    public Integer top() {
        return !isEmpty() ? super.peek() : null;
    }

     /* Los siguientes métodos no son necesarios,
     se heredan directamente de la clase Stack

    public int size() {
        return super.size();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }
    */
}
