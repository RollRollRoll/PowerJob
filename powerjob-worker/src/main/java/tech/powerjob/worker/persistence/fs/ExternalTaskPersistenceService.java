package tech.powerjob.worker.persistence.fs;

import tech.powerjob.worker.persistence.db.TaskDO;

import java.util.List;

/**
 * 外部任务持久化服务
 *
 * @author tjq
 * @since 2024/2/22
 */
public interface ExternalTaskPersistenceService extends AutoCloseable {

    boolean persistPendingTask(List<TaskDO> tasks);

    List<TaskDO> readPendingTask();

    boolean persistFinishedTask(List<TaskDO> tasks);

    List<TaskDO> readFinishedTask();
}
