package ru.clevertec.linkedlist;

import org.openjdk.jmh.annotations.*;
import ru.clevertec.ListCreator;
import java.util.List;

@State(Scope.Thread)
public class GetTest {
    static final int SIZE = 100_000;

    List<String> list;

    @Param({"0", SIZE / 2 + "", SIZE - 1 + ""})
    int getIndex;

    @Setup
    public void setUp() {
        list = ListCreator.getLinkedList(SIZE);
    }

    @Benchmark
    public void get() {
        list.get(getIndex);
    }
}

