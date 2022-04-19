package ru.clevertec.arraylist;

import org.openjdk.jmh.annotations.*;
import ru.clevertec.ListCreator;
import java.util.List;

@State(Scope.Thread)
public class ContainsTest {

    static final int SIZE = 100_000;

    List<String> list;

    @Param({"0", SIZE / 2 + "", SIZE - 1 + "", -1 + ""})
    String element;

    @Setup(Level.Trial)
    public void setUp() {
        list = ListCreator.getArrayList(SIZE);
    }

    @Benchmark
    public void findElement() {
        list.contains(element);
    }
}
