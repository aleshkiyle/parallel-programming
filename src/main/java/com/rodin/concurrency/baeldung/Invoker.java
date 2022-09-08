package com.rodin.concurrency.baeldung;

import java.util.concurrent.Executor;

public class Invoker implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public void execute() {
        Executor executor = new Invoker();
        executor.execute( () -> {
            // task be perfomed;
        });
    }
}
