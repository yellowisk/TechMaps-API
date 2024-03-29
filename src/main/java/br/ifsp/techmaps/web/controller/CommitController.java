package br.ifsp.techmaps.web.controller;

import br.ifsp.techmaps.domain.entities.task.Task;
import br.ifsp.techmaps.domain.entities.task.TaskCommit;
import br.ifsp.techmaps.usecases.commit.CommitCRUD;
import br.ifsp.techmaps.usecases.stage.StageCRUD;
import br.ifsp.techmaps.usecases.task.TaskCRUD;
import br.ifsp.techmaps.usecases.task.gateway.TaskDAO;
import br.ifsp.techmaps.web.model.commit.request.UpdateCommitStatusRequest;
import br.ifsp.techmaps.web.model.commit.response.CommitResponse;
import br.ifsp.techmaps.web.model.commit.response.UpdateCommitStatusResponse;
import br.ifsp.techmaps.web.model.task.request.UpdateDateFinishedRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api/v1/tasks/")
@RestController
public class CommitController {

    private final CommitCRUD commitCRUD;
    private final TaskCRUD taskCRUD;
    private final TaskDAO taskDAO;
    private final StageCRUD stageCRUD;

    public CommitController(CommitCRUD commitCRUD, TaskCRUD taskCRUD,
                            TaskDAO taskDAO, StageCRUD stageCRUD) {
        this.commitCRUD = commitCRUD;
        this.taskCRUD = taskCRUD;
        this.taskDAO = taskDAO;
        this.stageCRUD = stageCRUD;
    }

    @GetMapping("{taskId}/commits")
    public ResponseEntity<CommitResponse> getCommitByTaskId(
            @PathVariable UUID taskId) {
        TaskCommit taskCommit = commitCRUD.getTaskCommitByTaskId(taskId);

        return ResponseEntity.ok(CommitResponse.convertFromTaskCommit(taskCommit));
    }

    @GetMapping("{taskId}/commits/{commitId}")
    public ResponseEntity<CommitResponse> getCommitById(
            @PathVariable UUID commitId) {
        TaskCommit taskCommit = commitCRUD.getTaskCommitById(commitId);

        return ResponseEntity.ok(CommitResponse.convertFromTaskCommit(taskCommit));
    }

    @PutMapping("{taskId}/commits/{commitId}")
    public ResponseEntity<UpdateCommitStatusResponse> updateCommit(
            @PathVariable UUID taskId,
            @PathVariable UUID commitId,
            @RequestBody UpdateCommitStatusRequest request) {
        TaskCommit taskCommit = commitCRUD.updateTaskCommit(taskId, commitId, request);

        if(taskCommit.isStaged())
            taskCRUD.updateTaskDateFinished(taskId, UpdateDateFinishedRequest.create(true));

        Task task = taskDAO.findTaskById(taskCommit.getTask().getTaskId()).get();

        stageCRUD.updateStageCommit(task.getStage().getStageId());

        return ResponseEntity.ok(UpdateCommitStatusResponse.convertForUpdate(taskCommit));
    }

}
