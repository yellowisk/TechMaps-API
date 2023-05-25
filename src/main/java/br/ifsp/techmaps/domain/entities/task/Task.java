package br.ifsp.techmaps.domain.entities.task;

import br.ifsp.techmaps.domain.entities.stage.Stage;
import br.ifsp.techmaps.domain.entities.stage.StageStatus;
import br.ifsp.techmaps.domain.entities.user.User;
import jakarta.persistence.*;

import java.sql.Time;
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
    private Date date;
    private Time hour;
    @OneToOne
    private TaskCommit taskCommit;

    public Task(UUID taskId, Stage stage,
                String title, Date date,
                Time hour) {
        this.taskId = taskId;
        this.stage = stage;
        this.title = title;
        this.date = date;
        this.hour = hour;
    }

    public Task(UUID taskId, Stage stage,
                String title, String link,
                Date date, Time hour) {
        this.taskId = taskId;
        this.stage = stage;
        this.title = title;
        this.link = link;
        this.date = date;
        this.hour = hour;
    }

    public Task(UUID taskId, Stage stage,
                String title, String link,
                Date date, Time hour,
                TaskCommit taskCommit) {
        this.taskId = taskId;
        this.stage = stage;
        this.title = title;
        this.link = link;
        this.date = date;
        this.hour = hour;
        this.taskCommit = taskCommit;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public TaskCommit getTaskCommit() {
        return taskCommit;
    }

    public void setTaskCommit(TaskCommit taskCommit) {
        this.taskCommit = taskCommit;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", stage=" + stage +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", date=" + date +
                ", hour=" + hour +
                ", taskCommit=" + taskCommit +
                '}';
    }
}