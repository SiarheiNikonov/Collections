package ru.clevertec.linkedlist;

import org.openjdk.jmh.annotations.*;
import ru.clevertec.ListCreator;
import java.util.List;

@State(Scope.Thread)
public class AddTest {
    static final int size = 100_000;
    List<String> list;

    @Setup(Level.Iteration)
    public void setUp(){
        list = ListCreator.getLinkedList(size);
    }

    @Benchmark
    public void addToStart() throws InterruptedException {
        list.add(0, "qwerty");
    }

    @Benchmark
    public void addToEnd() throws InterruptedException {
        list.add("qwerty");
    }

    @Benchmark
    public void addToMiddle() {
        list.add(list.size()/2, "qwerty");
    }
}
