package ru.clevertec.arraylist;

import org.openjdk.jmh.annotations.*;
import ru.clevertec.ListCreator;
import java.util.List;

@State(Scope.Thread)
public class AddTest {
    static final int SIZE = 100_000;

    List<String> list;

    @Setup(Level.Iteration)
    public void setUp(){
        list = ListCreator.getArrayList(SIZE);
    }

    @Benchmark
    public void addToStart() {
        list.add(0, "qwerty");
    }

    @Benchmark
    public void addToEnd() {
        list.add("qwerty");
    }

    @Benchmark
    public void addToMiddle() {
        list.add(list.size()/2, "qwerty");
    }
}
