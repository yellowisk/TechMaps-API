package br.ifsp.techmaps.domain.entities.task;

import br.ifsp.techmaps.domain.entities.stage.Stage;
import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID taskId;
    @ManyToOne
    private Stage stage;
    private String title;

    private String link;
    private Timestamp date_creted;
    private Timestamp date_finished;
    @OneToOne
    private TaskCommit taskCommit;


    public Task(UUID taskId, Stage stage, String title, String link, Timestamp date_creted, Timestamp date_finished) {
        this.taskId = taskId;
        this.stage = stage;
        this.title = title;
        this.link = link;
        this.date_creted = date_creted;
        this.date_finished = date_finished;
    }

    public Task(UUID taskId, Stage stage, String title, String link, Timestamp date_creted, Timestamp date_finished, TaskCommit taskCommit) {
        this.taskId = taskId;
        this.stage = stage;
        this.title = title;
        this.link = link;
        this.date_creted = date_creted;
        this.date_finished = date_finished;
        this.taskCommit = taskCommit;
    }

    public Task(UUID taskId, Stage stage,
                String title, Timestamp date_creted,
                Timestamp date_finished) {
        this.taskId = taskId;
        this.stage = stage;
        this.title = title;
        this.date_creted = date_creted;
        this.date_finished = date_finished;
    }

    private Task(UUID taskId) {
        this.taskId = taskId;
    }

    public static Task createWithOnlyId(UUID taskId) {
        return new Task(taskId);
    }

    public Task() {}

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {return link;}

    public void setLink(String link) {this.link = link;}

    public Timestamp getDate_creted() {
        return date_creted;
    }

    public void setDate_creted(Timestamp date_creted) {
        this.date_creted = date_creted;
    }

    public Timestamp getDate_finished() {
        return date_finished;
    }

    public void setDate_finished(Timestamp date_finished) {
        this.date_finished = date_finished;
    }

    public TaskCommit getTaskCommit() {
        return taskCommit;
    }

    public void setTaskCommit(TaskCommit taskCommit) {
        this.taskCommit = taskCommit;
    }

}