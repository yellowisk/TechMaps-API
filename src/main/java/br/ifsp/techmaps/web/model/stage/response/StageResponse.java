package br.ifsp.techmaps.web.model.stage.response;

import br.ifsp.techmaps.domain.entities.stage.Stage;
import br.ifsp.techmaps.domain.entities.stage.StageEnum;
import br.ifsp.techmaps.domain.entities.stage.StageStatus;
import br.ifsp.techmaps.domain.entities.task.Task;

import java.util.*;

public class StageResponse {
    private UUID stageId;
    private UUID roadmapId;
    private StageEnum theme;
    private StageStatus stageStatus;
    private List<Task> tasks;
    private Integer stageCommit;

    public StageResponse(UUID stageId) {
        this.stageId = stageId;
    }

    public StageResponse(UUID stageId, UUID roadmapId, StageEnum theme, StageStatus stageStatus, Integer stageCommit) {
        this.stageId = stageId;
        this.roadmapId = roadmapId;
        this.theme = theme;
        this.stageStatus = stageStatus;
        this.stageCommit = stageCommit;
    }

    public StageResponse(UUID stageId, StageStatus stageStatus, Integer stageCommit) {
        this.stageId = stageId;
        this.stageStatus = stageStatus;
        this.stageCommit = stageCommit;
    }

    public static StageResponse createJustId(UUID stageId) {
        return new StageResponse(stageId);
    }

    public static StageResponse createFromStage(Stage stage) {
        return new StageResponse(
                stage.getStageId(),
                stage.getRoadmap().getRoadmapId(),
                stage.getTheme(),
                stage.getStageStatus(),
                stage.getStageCommit()
        );
    }

    public static StageResponse createForUpdate(Stage stage) {
        return new StageResponse(
                stage.getStageId(),
                stage.getStageStatus(),
                stage.getStageCommit()
        );
    }

    public UUID getStageId() {
        return stageId;
    }

    public void setStageId(UUID stageId) {
        this.stageId = stageId;
    }

    public UUID getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(UUID roadmapId) {
        this.roadmapId = roadmapId;
    }

    public StageEnum getTheme() {
        return theme;
    }

    public void setTheme(StageEnum theme) {
        this.theme = theme;
    }

    public StageStatus getStageStatus() {
        return stageStatus;
    }

    public void setStageStatus(StageStatus stageStatus) {
        this.stageStatus = stageStatus;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer getStageCommit() {
        return stageCommit;
    }

    public void setStageCommit(Integer stageCommit) {
        this.stageCommit = stageCommit;
    }
}