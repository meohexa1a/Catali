package com.mdt.common.shared.type;

import lombok.Locked;

public final class TimeHolder {
    private long time = -1;

    // !------------------------------!

    @Locked
    public void hold() {
        if (time != -1) return;
        time = System.currentTimeMillis();
    }

    @Locked
    public void release() {
        time = -1;
    }

    @Locked
    public boolean isOver(long millis) {
        if (time == -1) return false;
        return System.currentTimeMillis() - time > millis;
    }

    @Locked
    public boolean isHolding() {
        return time != -1;
    }

}
