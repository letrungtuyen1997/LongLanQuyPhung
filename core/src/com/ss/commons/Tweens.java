package com.ss.commons;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/* renamed from: com.ss.core.commons.Tweens */
public class Tweens {
    public static void Blink(Actor actor, float f, float f2, float f3, int i, Runnable runnable) {
        SequenceAction sequenceAction = new SequenceAction();
        float f4 = f3 / ((float) i);
        sequenceAction.addAction(Actions.repeat(i, Actions.sequence(Actions.alpha(0.3f, f4), Actions.alpha(1.0f, f4))));
        if (runnable != null) {
            sequenceAction.addAction(Actions.run(runnable));
        }
        actor.addAction(sequenceAction);
    }

    public static void setTimeout(Group group, float f, Runnable runnable) {
        group.addAction(Actions.sequence(Actions.delay(f), Actions.run(runnable)));
    }

    public static <T extends Actor> void action(T t, Action action, Runnable runnable) {
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(action);
        if (runnable != null) {
            sequenceAction.addAction(Actions.run(runnable));
        }
        t.addAction(sequenceAction);
    }

    public static <T extends Actor> void seqActions(T t, Runnable runnable, Action... actionArr) {
        SequenceAction sequenceAction = new SequenceAction();
        for (Action addAction : actionArr) {
            sequenceAction.addAction(addAction);
        }
        if (runnable != null) {
            sequenceAction.addAction(Actions.run(runnable));
        }
        t.addAction(sequenceAction);
    }

    public static <T extends Actor> void palActions(T t, Runnable runnable, Action... actionArr) {
        ParallelAction parallelAction = new ParallelAction();
        SequenceAction sequenceAction = new SequenceAction();
        for (Action addAction : actionArr) {
            parallelAction.addAction(addAction);
        }
        if (runnable != null) {
            sequenceAction.addAction(parallelAction);
            sequenceAction.addAction(Actions.run(runnable));
            t.addAction(sequenceAction);
            return;
        }
        t.addAction(parallelAction);
    }
}
