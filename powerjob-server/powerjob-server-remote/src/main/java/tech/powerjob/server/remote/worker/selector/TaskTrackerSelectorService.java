package tech.powerjob.server.remote.worker.selector;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.powerjob.common.enums.DispatchStrategy;
import tech.powerjob.server.common.module.WorkerInfo;
import tech.powerjob.server.persistence.remote.model.InstanceInfoDO;
import tech.powerjob.server.persistence.remote.model.JobInfoDO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TaskTrackerSelectorService
 *
 * @author tjq
 * @since 2024/2/24
 */
@Service
public class TaskTrackerSelectorService {

    private final Map<Integer, TaskTrackerSelector> taskTrackerSelectorMap = Maps.newHashMap();

    @Autowired
    public TaskTrackerSelectorService(List<TaskTrackerSelector> taskTrackerSelectors) {
        taskTrackerSelectors.forEach(ts -> taskTrackerSelectorMap.put(ts.strategy().getV(), ts));
    }

    public WorkerInfo select(JobInfoDO jobInfoDO, InstanceInfoDO instanceInfoDO, List<WorkerInfo> availableWorkers) {
        Integer strategy = Optional.ofNullable(jobInfoDO.getDispatchStrategy()).orElse(DispatchStrategy.HEALTH_FIRST.getV());
        TaskTrackerSelector taskTrackerSelector = taskTrackerSelectorMap.get(strategy);
        return taskTrackerSelector.select(jobInfoDO, instanceInfoDO, availableWorkers);
    }
}
