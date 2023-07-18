package br.ifsp.techmaps.usecases.commit;

import br.ifsp.techmaps.domain.entities.task.TaskCommit;
import br.ifsp.techmaps.usecases.commit.gateway.CommitDAO;
import br.ifsp.techmaps.usecases.stage.gateway.StageDAO;
import br.ifsp.techmaps.usecases.task.gateway.TaskDAO;
import br.ifsp.techmaps.web.model.task.request.UpdateCommitStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommitCRUDImpl implements CommitCRUD {
    private final CommitDAO commitDAO;

    public CommitCRUDImpl(CommitDAO commitDAO) {
        this.commitDAO = commitDAO;
    }

    @Override
    public TaskCommit getTaskCommitById(UUID taskCommitId) {
        return commitDAO.findTaskCommitById(taskCommitId)
                .orElseThrow(() -> new NullPointerException("Couldn't TaskCommit with id: " + taskCommitId));
    }

    @Override
    public TaskCommit updateTaskCommit(UUID taskId, UUID taskCommitId, UpdateCommitStatus request) {
        TaskCommit taskCommit = commitDAO.findTaskCommitById(taskCommitId)
                .orElseThrow(() -> new NullPointerException("Couldn't find TaskCommit with id: "
                        + taskCommitId));

        taskCommit.setState(request.getStatus());

        commitDAO.updateTaskCommmit(taskCommit);

        return taskCommit;
    }

    @Override
    public TaskCommit getTaskCommitByTaskId(UUID taskId) {
        return commitDAO.findTaskCommitByTaskId(taskId)
                .orElseThrow(() -> new NullPointerException("Couldn't find TaskCommit with task id: "
                        + taskId));
    }
}
