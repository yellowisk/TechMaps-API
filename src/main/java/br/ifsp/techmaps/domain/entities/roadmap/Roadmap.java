package br.ifsp.techmaps.domain.entities.roadmap;


import br.ifsp.techmaps.domain.entities.stage.Stage;
import jakarta.persistence.*;


import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "roadmap")
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roadmapId;
    private String title;
    private RoadmapType type;
    private RoadmapStatus roadmapStatus;
    private RoadmapLanguage roadmapLanguage;
    private Timestamp startTime;
    private Timestamp finishTime;
    @OneToMany
    private List<Stage> stages;
    private Integer roadmapCommits;
    private UUID dashboardId;

    public Roadmap() {
    }

    public Roadmap(UUID roadmapId, String title, RoadmapType roadmapType, RoadmapStatus roadmapStatus,
                   RoadmapLanguage roadmapLanguage, Timestamp startTime,
                   Timestamp finishTime)
    {
        this.roadmapId = roadmapId;
        this.title = title;
        this.type = roadmapType;
        this.roadmapStatus = roadmapStatus;
        this.roadmapLanguage = roadmapLanguage;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Roadmap(UUID roadmapId, String title, RoadmapType roadmapType, RoadmapStatus roadmapStatus,
                   RoadmapLanguage roadmapLanguage, Timestamp startTime)
    {
        this.roadmapId = roadmapId;
        this.title = title;
        this.type = roadmapType;
        this.roadmapStatus = roadmapStatus;
        this.roadmapLanguage = roadmapLanguage;
        this.startTime = startTime;
    }

    public Roadmap(UUID roadmapId, String title, RoadmapType roadmapType, RoadmapStatus roadmapStatus,
                   Timestamp startTime)
    {
        this.roadmapId = roadmapId;
        this.title = title;
        this.type = roadmapType;
        this.roadmapStatus = roadmapStatus;
        this.startTime = startTime;
    }

    public Roadmap(UUID roadmapId, String title, RoadmapType type, RoadmapStatus roadmapStatus,
                   RoadmapLanguage roadmapLanguage, Timestamp startTime,
                   Timestamp finishTime, List<Stage> stages, Integer roadmapCommits, UUID dashboardId) {
        this.roadmapId = roadmapId;
        this.title = title;
        this.type = type;
        this.roadmapStatus = roadmapStatus;
        this.roadmapLanguage = roadmapLanguage;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.stages = stages;
        stages.forEach(stage -> stage.setRoadmap(this));
        this.roadmapCommits = roadmapCommits;
        this.dashboardId = dashboardId;
    }

    public Roadmap(UUID roadmapId, String title, RoadmapType type, RoadmapStatus roadmapStatus,
                   RoadmapLanguage roadmapLanguage, Timestamp startTime,
                   Timestamp finishTime, Integer roadmapCommits, UUID dashboardId) {
        this.roadmapId = roadmapId;
        this.title = title;
        this.type = type;
        this.roadmapStatus = roadmapStatus;
        this.roadmapLanguage = roadmapLanguage;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.roadmapCommits = roadmapCommits;
        this.dashboardId = dashboardId;
    }

    public Roadmap(String title, RoadmapType type, RoadmapStatus roadmapStatus, RoadmapLanguage roadmapLanguage,
                   Timestamp startTime, Timestamp finishTime, Integer roadmapCommits, UUID dashboardId) {
        this.title = title;
        this.type = type;
        this.roadmapStatus = roadmapStatus;
        this.roadmapLanguage = roadmapLanguage;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.roadmapCommits = roadmapCommits;
        this.dashboardId = dashboardId;
    }

    public Roadmap(UUID roadmapId) {
        this.roadmapId = roadmapId;
    }

    public Roadmap(UUID roadmapId, String title, RoadmapType type, RoadmapStatus roadmapStatus,
                   RoadmapLanguage roadmapLanguage, Timestamp startTime,
                   Integer roadmapCommits, UUID dashboardId) {
        this.roadmapId = roadmapId;
        this.title = title;
        this.type = type;
        this.roadmapStatus = roadmapStatus;
        this.roadmapLanguage = roadmapLanguage;
        this.startTime = startTime;
        this.roadmapCommits = roadmapCommits;
        this.dashboardId = dashboardId;
    }

    public static Roadmap createWithoutStageAndFinishTime(UUID roadmapId, String title, RoadmapType type,
                                                          RoadmapStatus status, RoadmapLanguage language,
                                                          Timestamp localDateTime, int commitCounter,
                                                          UUID dashboardId) {
        return new Roadmap(roadmapId, title, type, status, language, localDateTime, commitCounter, dashboardId);

    }

    public Roadmap getNewInstanceWithId(UUID roadmapId) {
        return new Roadmap(roadmapId, title, type, roadmapStatus, roadmapLanguage, startTime,
                finishTime, stages, roadmapCommits, dashboardId);
    }

    public static Roadmap getNewInstanceWithOnlyId(UUID roadmapId) {
        return new Roadmap(roadmapId);
    }

    public static Roadmap createWithoutId(String title, RoadmapType type, RoadmapStatus roadmapStatus,
                                          RoadmapLanguage roadmapLanguage, Timestamp startTime,
                                          Timestamp undoneDuration, Integer roadmapCommits,
                                          UUID dashboardId) {
        return new Roadmap(title, type, roadmapStatus, roadmapLanguage, startTime,
                undoneDuration, roadmapCommits, dashboardId);
    }

//    //Stays here (for now)
//    public Duration calculateDuration(Roadmap roadmap) {
//        Duration duration = Duration.between(this.startTime, this.finishTime);
//        return duration;
//    }

    //Stays here (for now)
    public int findRoadmapCommits() {
        int roadmapCommits = 0;
        for (Stage stages : stages) {
            int stageCommit = stages.findStageCommits(stages.getTasks());
            roadmapCommits = roadmapCommits + stageCommit;
        }
        return roadmapCommits;
    }

    public UUID getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(UUID id) {
        this.roadmapId = roadmapId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RoadmapType getType() {
        return type;
    }

    public void setType(RoadmapType type) {
        this.type = type;
    }

    public RoadmapStatus getRoadmapStatus() {
        return roadmapStatus;
    }

    public void setRoadmapStatus(RoadmapStatus roadmapStatus) {
        this.roadmapStatus = roadmapStatus;
    }


    public RoadmapLanguage getRoadmapLanguage() {
        return roadmapLanguage;
    }

    public void setRoadmapLanguage(RoadmapLanguage roadmapLanguage) {
        this.roadmapLanguage = roadmapLanguage;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp undoneDuration) {
        this.finishTime = undoneDuration;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public Integer getRoadmapCommits() {
        return roadmapCommits;
    }

    public void setRoadmapCommits(Integer roadmapCommits) {
        this.roadmapCommits = roadmapCommits;
    }

    public UUID getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(UUID dashboardId) {
        this.dashboardId = dashboardId;
    }
}