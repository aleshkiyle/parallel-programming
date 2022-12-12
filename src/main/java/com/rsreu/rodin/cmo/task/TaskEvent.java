package com.rsreu.rodin.cmo.task;

import com.lmax.disruptor.EventFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEvent {

    public final static EventFactory<TaskEvent> EVENT_FACTORY = TaskEvent::new;

    private Object task;
}
