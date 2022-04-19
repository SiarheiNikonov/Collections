package ru.clevertec;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                //что тестим
                .include("AddTest")
                .include("ContainsTest")
                .include("DeleteByIndexTest")
                .include("DeleteElementTest")
                .include("GetTest")
                //настройки тестов
                .measurementIterations(20)
                .forks(1)
                //для тестов по добавлению больше ставить опасно, список может оооочень вырасти
                .measurementTime(TimeValue.milliseconds(100))
                //настройки прогревочных проходов
                .warmupIterations(3)
                .warmupForks(0)
                .warmupTime(TimeValue.milliseconds(100))
                //настройки результатов
                .timeUnit(TimeUnit.MICROSECONDS)
                .mode(Mode.AverageTime)
                .build();
        new org.openjdk.jmh.runner.Runner(options).run();
    }
}
