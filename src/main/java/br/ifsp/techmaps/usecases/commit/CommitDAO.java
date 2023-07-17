package br.ifsp.techmaps.usecases.commit;

import br.ifsp.techmaps.domain.entities.task.Task;
import br.ifsp.techmaps.domain.entities.task.TaskCommit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommitDAO {
    TaskCommit createTaskCommit(Task task);
    Optional<TaskCommit> findTaskCommitById(UUID taskCommitId);
    List<TaskCommit> commitsByStageId(UUID stageId);
    List<TaskCommit> commitsByDashboardId(UUID dashboardId);
    TaskCommit updateTaskCommmit(TaskCommit taskCommit);

}
