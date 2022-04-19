package ru.clevertec;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListCreator {
    public static List<String> getLinkedList(Integer size) {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i + "");
        }
        return list;
    }

    public static List<String> getArrayList(Integer size) {
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(i + "");
        }
        return list;
    }
}
