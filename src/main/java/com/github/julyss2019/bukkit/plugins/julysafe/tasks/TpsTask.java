package com.github.julyss2019.bukkit.plugins.julysafe.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;

public class TpsTask extends BukkitRunnable {
    private long lastPoll;
    private final LinkedList<Double> history = new LinkedList<>();

    @Override
    public void run() {
        long startTime = System.nanoTime();
        long timeSpent = startTime - lastPoll;

        if (this.history.size() > 10) {
            this.history.remove();
        }

        // 总 tick / seconds = tps（ticks per second，每秒能处理几个tick）
        // 20 tick, 1纳秒 = 1e-9秒
        double tps = 20.0 / (timeSpent / 1E9);

        if (tps <= 21) {
            this.history.add(tps);
        }

        this.lastPoll = startTime;
    }

    public double getAverageTps() {
        double avg = 0D;

        for (double f : this.history) {
            avg += f;
        }

        double tmp = avg / this.history.size();

        return tmp > 0 && tmp < 21 ? tmp : -1;
    }
}
