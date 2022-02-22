package tests.mock.simpleapp;

import tech.fastj.App;

import tech.fastj.feature.StartupFeature;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TaskFeature implements StartupFeature {
    final Callable<Boolean> task = () -> true;
    private Future<Boolean> taskResult;

    public TaskFeature() {
    }

    @Override
    public void startup(App app) {
        taskResult = app.doTask(task);
    }

    public Future<Boolean> getTaskResult() {
        return taskResult;
    }
}