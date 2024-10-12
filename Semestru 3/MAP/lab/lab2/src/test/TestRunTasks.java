package test;

import decorator.DelayTaskRunner;
import decorator.PrinterTaskRunner;
import decorator.StrategyTaskRunner;
import decorator.TaskRunner;
import factory.Strategy;
import model.MessageTask;
import model.SortingMethod;
import model.SortingTask;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Vector;

public class TestRunTasks {

    private static void addTasks(TaskRunner taskRunner) {
        taskRunner.addTask(new MessageTask("1", "task1", "Take out the trash", "me", "roomate", LocalDateTime.now()));
        taskRunner.addTask(new MessageTask("2", "task2", "Call the post office", "roomate", "me", LocalDateTime.now()));
        taskRunner.addTask(new MessageTask("3", "task3", "Call the police", "roomate", "me", LocalDateTime.now()));
    }

    private static void addTasks2(TaskRunner taskRunner) {
        taskRunner.addTask(new MessageTask("4", "task4", "Buy food please", "me", "roomate", LocalDateTime.now()));
        taskRunner.addTask(new MessageTask("5", "task5", "Walk the fish", "mom", "dad", LocalDateTime.now()));
        taskRunner.addTask(new MessageTask("6", "task6", "Shut up!", "brother", "me", LocalDateTime.now()));
    }

    public static void executeTasks(String argument) {
        var strategy = Strategy.valueOf(argument.toUpperCase());
        var strategyTaskRunner = new StrategyTaskRunner(strategy);
        addTasks(strategyTaskRunner);
        while (strategyTaskRunner.hasTask())
            strategyTaskRunner.executeOneTask();

        var printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);
        addTasks2(printerTaskRunner);
        while (printerTaskRunner.hasTask())
            printerTaskRunner.executeOneTask();

        var delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
        addTasks(delayTaskRunner);
        while (delayTaskRunner.hasTask())
            delayTaskRunner.executeOneTask();
    }

    public static void sortTasks() {
        var sortingTask = new SortingTask("1", "sort the numbers 1, 2, 3, 4",
                SortingMethod.QuickSort, new Vector<Integer>(Arrays.asList(1, 2, 3, 4)));
        sortingTask.execute();
        for ( var nr : sortingTask.getNumbersVector() )
            System.out.print(nr);

        System.out.println();

        var sortingTask2 = new SortingTask("2", "sort the numbers 9, 0, 8, 1",
                SortingMethod.BubbleSort, new Vector<Integer>(Arrays.asList(9, 0, 8, 1)));
        sortingTask2.execute();
        for ( var nr : sortingTask2.getNumbersVector() )
            System.out.print(nr);
    }
}
