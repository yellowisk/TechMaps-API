package br.ifsp.techmaps.web.controller;

import br.ifsp.techmaps.domain.entities.task.Task;
import br.ifsp.techmaps.usecases.task.TaskCRUD;
import br.ifsp.techmaps.web.model.task.request.CreateTaskRequest;
import br.ifsp.techmaps.web.model.task.response.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api/v1/roadmaps/{roadmapId}/stages/{stageId}/tasks")
@RestController
public class TaskController {

    private final TaskCRUD taskCRUD;;

    public TaskController(TaskCRUD taskCRUD) {this.taskCRUD = taskCRUD;}

    @GetMapping("{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable UUID taskId) {
        Task task = taskCRUD.getTaskById(taskId);

        return ResponseEntity.ok(TaskResponse.createFromTask(task));
    }

    @PostMapping
    public ResponseEntity<List<TaskResponse>> addNewTask(
            @PathVariable UUID stageId,
            @RequestBody @Valid CreateTaskRequest request) {

        List<Task> tasks = taskCRUD.createNewTasks(stageId, request);
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            taskResponses.add(TaskResponse.createFromTask(task));
        }

        return ResponseEntity.ok(taskResponses);
    }

}
