package ru.clevertec.copyutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CopyUtil {
    public static void main(String[] args) {
        List<Number> source = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            source.add(i+1);
            source.add(i + 0.1);
            source.add(i + 1L);
            source.add(i + 0.1f);
        }
        int sourceSize = source.size();

        List<Object> dest = new LinkedList<>();
        for(int i = 0; i < 5; i++) dest.add(new Object());
        int oldDestSize = dest.size();
        copy(source, dest);
        int newDestSize = dest.size();
        System.out.println(String.format("Source size: %d, dest size: %d, dest size after copy: %d", sourceSize, oldDestSize, newDestSize));
   }

    static <T> void copy(List<? extends T> source, List<? super T> dest) {
        for(T t: source) {
            dest.add((t));
        }
    }
}
