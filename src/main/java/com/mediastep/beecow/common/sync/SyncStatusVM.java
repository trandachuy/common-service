package com.mediastep.beecow.common.sync;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncStatusVM {

    private static final NumberFormat PERCENT_FORMATER = NumberFormat.getPercentInstance();
    static {
        PERCENT_FORMATER.setMinimumFractionDigits(2);
    }

    private List<Long> ids = new ArrayList<>();

    private final List<Long> completedIds = new ArrayList<>();

    private final Map<Long, String> skippedIds = new HashMap<>();

    private final Map<Long, String> failedIds = new HashMap<>();

    private long total;

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * @return the completed
     */
    public String getCompleted() {
        return toPercentage(completedIds.size(), this.total);
    }

    /**
     * @return the failed
     */
    public String getSkipped() {
        return toPercentage(skippedIds.size(), this.total);
    }

    /**
     * @return the failed
     */
    public String getFailed() {
        return toPercentage(failedIds.size(), this.total);
    }

    private String toPercentage(long partal, long total) {
        float result;
        if (total != 0) {
            result = (float) partal / total;
        }
        else {
            result = 0;
        }
        return PERCENT_FORMATER.format(result);
    }

    /**
     * @return the skippedIds
     */
    public Map<Long, String> getSkippedIds() {
        return skippedIds;
    }

    /**
     * @return the failedIds
     */
    public Map<Long, String> getFailedIds() {
        return failedIds;
    }

    /**
     * @return the ids
     */
    public List<Long> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<Long> ids) {
        synchronized (this) {
            this.ids = ids;
            this.total = ids.size();
        }
    }

    public void addCompleted(Long id) {
        synchronized (this) {
            this.completedIds.add(id);
        }
    }

    public void addSkipped(Long id, String message) {
        synchronized (this) {
            this.skippedIds.put(id, message);
        }
    }

    public void addFailed(Long id, String message) {
        synchronized (this) {
            this.failedIds.put(id, message);
        }
    }

    @Override
    public String toString() {
        return "SyncStatusVM {"
            + "ids=" + ids +
            ", completedIds=" + completedIds
            + ", skippedIds=" + skippedIds
            + ", failedIds=" + failedIds + "}";
    }
}
