package com.mediastep.beecow.common.sync.service;

import com.mediastep.beecow.common.errors.BeecowException;
import com.mediastep.beecow.common.errors.SyncSkippedException;
import com.mediastep.beecow.common.sync.SyncStatusVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class AbstractSyncService {

    public static final int PROCESS_SYNC_PAGE_SIZE = 100;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected SyncStatusVM syncStatusVM;

    protected boolean stopSync;

    protected boolean stopOnError;

    /**
     * Load and save entity.
     * @param id
     * @throws SyncSkippedException when entity is skipped, BeecowException when proceed entity failed
     */
    protected abstract void loadAndSave(Long id);

    protected abstract Page<Long> findAllIds(Pageable pageable);

    protected int getSyncPageSize() {
        return PROCESS_SYNC_PAGE_SIZE;
    }

    public SyncStatusVM sync(List<Long> ids, boolean stopOnError) {
        log.debug("Request to sync data: {}", ids);
        synchronized (this) {
            this.stopOnError = stopOnError;
            reset();
            syncStatusVM.setIds(ids);
            doSync(ids);
            return syncStatusVM;
        }
    }

    protected void reset() {
        stopSync = false;
        syncStatusVM = new SyncStatusVM();
    }

    /**
     * @return true means success, false there is some error
     */
    protected boolean doSync(List<Long> ids) {
        int idCount = ids.size();
        for (int i = 0; i < idCount && !stopSync; i++) {
            Long id = ids.get(i);
            boolean success = doSync(id);
            if (!success && stopOnError) {
                break;
            }
        }
        return (syncStatusVM.getFailedIds().size() == 0);
    }

    /**
     * Load item from item-services
     */
    protected boolean doSync(Long id) {
        log.debug("Sync data with ID '{}'", id);
        try {
            loadAndSave(id);
            syncStatusVM.addCompleted(id);
            return true;
        }
        catch (SyncSkippedException exc) {
            log.debug("Skipped to sync data with ID '" + id + "': " + exc.getMessage());
            syncStatusVM.addSkipped(id, exc.getErrorVM().toString());
            return false;
        }
        catch (BeecowException exc) {
            log.debug("Skipped to sync data with ID '" + id + "': " + exc.getMessage());
            syncStatusVM.addFailed(id, exc.getErrorVM().toString());
            return false;
        }
        catch (Exception exc) {
            log.debug("Failed to sync data with ID '" + id + "'", exc);
            syncStatusVM.addFailed(id, exc.getClass().getSimpleName() + " " + exc.getMessage());
            return false;
        }
    }

    /**
     * @return the syncStatusVM
     */
    public SyncStatusVM getSyncStatusVM() {
        log.debug("Request to get sync status");
        return syncStatusVM;
    }

    /**
     * @param stopSync the stopSync to set
     */
    public void setStopSync(boolean stopSync) {
        log.debug("Request to stop sync data");
        this.stopSync = stopSync;
    }

    public SyncStatusVM syncAll(boolean stopOnError) {
        log.debug("Request to sync all data");
        synchronized (this) {
            this.stopOnError = stopOnError;
            reset();
            int page = 0;
            int size = getSyncPageSize();
            Pageable pageable = PageRequest.of(page, size);
            Page<Long> result = findAllIds(pageable);
            syncStatusVM.setTotal(result.getTotalElements());
            while (result.hasContent()) {
                List<Long> curIds = result.getContent();
                syncStatusVM.getIds().addAll(curIds);
                boolean success = doSync(curIds);
                if (!success && stopOnError) {
                    break;
                }
                pageable = pageable.next();
                result = findAllIds(pageable);
            }
            return syncStatusVM;
        }
    }
}
