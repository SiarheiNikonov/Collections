package ru.clevertec.copyutil;

import java.util.ArrayList;
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

        List<Object> objectsDest = new LinkedList<>();
        for(int i = 0; i < 5; i++) objectsDest.add(new Object());
        int oldDestSize = objectsDest.size();
        copy(source, objectsDest);
        int newDestSize = objectsDest.size();
        printMessage(sourceSize, oldDestSize, newDestSize);

        List<Number> numbersDest = new ArrayList<>();
        for(int i = 0; i < 7; i++) numbersDest.add(i);

        oldDestSize = numbersDest.size();
        copy(source, numbersDest);
        newDestSize = numbersDest.size();

        printMessage(sourceSize, oldDestSize, newDestSize);
   }

    static <T> void copy(List<? extends T> source, List<? super T> dest) {
        for(T t: source) {
            dest.add((t));
        }
    }

    static void printMessage(int sourceSize, int oldDestSize, int newDestSize){
        System.out.printf("Source size: %d, dest size: %d, dest size after copy: %d%n", sourceSize, oldDestSize, newDestSize);
    }
}
