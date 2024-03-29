package br.ifsp.techmaps.usecases.roadmap;

import br.ifsp.techmaps.domain.entities.certificate.Certificate;
import br.ifsp.techmaps.domain.entities.dashboard.Dashboard;
import br.ifsp.techmaps.domain.entities.roadmap.*;
import br.ifsp.techmaps.domain.entities.stage.Stage;
import br.ifsp.techmaps.domain.entities.task.Task;
import br.ifsp.techmaps.domain.entities.user.User;
import br.ifsp.techmaps.usecases.dashboard.gateway.DashboardDAO;
import br.ifsp.techmaps.usecases.roadmap.gateway.RoadmapDAO;
import br.ifsp.techmaps.usecases.stage.gateway.StageDAO;
import br.ifsp.techmaps.usecases.task.gateway.TaskDAO;
import br.ifsp.techmaps.usecases.user.gateway.UserDAO;
import br.ifsp.techmaps.web.model.roadmap.request.CreateRoadmapRequest;
import br.ifsp.techmaps.web.model.roadmap.request.UpdateTitleAndColorRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.util.*;

@Service
public class RoadmapCRUDImpl implements RoadmapCRUD {

    private final RoadmapDAO roadmapDAO;
    private final DashboardDAO dashboardDAO;
    private final UserDAO userDAO;
    private final StageDAO stageDAO;
    private final TaskDAO taskDAO;

    public RoadmapCRUDImpl(RoadmapDAO roadmapDAO, DashboardDAO dashboardDAO,
                           UserDAO userDAO, StageDAO stageDAO, TaskDAO taskDAO) {
        this.roadmapDAO = roadmapDAO;
        this.dashboardDAO = dashboardDAO;
        this.userDAO = userDAO;
        this.stageDAO = stageDAO;
        this.taskDAO = taskDAO;
    }

    @Override
    public Roadmap addNewRoadmap(UUID dashboardId, CreateRoadmapRequest request) {

        RoadmapType type = RoadmapType.valueOf(request.getType());
        RoadmapLanguage language = RoadmapLanguage.valueOf(request.getLanguage());
        RoadmapColor color = RoadmapColor.getColor(request.getColor());

        Map<RoadmapType, List<RoadmapLanguage>> typeToLangsMap = new HashMap<>();
        typeToLangsMap.put(RoadmapType.BACKEND, Arrays.asList(RoadmapLanguage.JAVA, RoadmapLanguage.PYTHON));
        typeToLangsMap.put(RoadmapType.FRONTEND, Arrays.asList(RoadmapLanguage.JAVASCRIPT));
        typeToLangsMap.put(RoadmapType.ANDROID, Arrays.asList(RoadmapLanguage.KOTLIN));

        if(!dashboardDAO.dashboardExists(dashboardId)) {
            throw new RuntimeException("Couldn't find dashboard with id: " + dashboardId);
        }

        if (!typeToLangsMap.get(type).contains(language)) {
            throw new RuntimeException("Roadmap type and language are incompatible.");
        }

        if (color.getColorCode() < 0 || color.getColorCode() > 11) {
            throw new RuntimeException("Invalid color code: " + color.getColorCode());
        }

        Roadmap roadmap = Roadmap.createWithoutId(request.getTitle(), type, false,
                language, color, Timestamp.valueOf(LocalDateTime.now()), null, null,
                0,
                dashboardId);

        return roadmapDAO.saveRoadmap(roadmap);
    }

    @Override
    public Roadmap findRoadmapById(UUID roadmapId) {

        Optional<Roadmap> opt = roadmapDAO.findRoadmapById(roadmapId);
        roadmapDAO.refreshRoadmap(roadmapDAO.findRoadmapById(roadmapId).get());

        if (opt.isEmpty()) {
            throw new RuntimeException("Roadmap not found");
        }

        return opt.get();
    }

    @Override
    public List<Roadmap> findRoadmapsByDashboardId(UUID dashboardId) {
        List<Roadmap> roadmaps = roadmapDAO.findAllByDashboardId(dashboardId);
        roadmaps.forEach(roadmap -> roadmapDAO.refreshRoadmap(roadmap));
        return roadmaps;
    }

    @Override
    public List<Roadmap> findCompletedRoadmapsByDashboardId(UUID dashboardId) {
        List<Roadmap> roadmaps = roadmapDAO.findAllCompletedByDashboardId(dashboardId);
        roadmaps.forEach(roadmapDAO::refreshRoadmap);
        return roadmaps;
    }

    @Override
    public void createCertificate(UUID dashboardId, UUID roadmapId) throws IOException {
        if (!dashboardDAO.dashboardExists(dashboardId)) {
            throw new RuntimeException("Couldn't find dashboard with id: " + dashboardId);
        };

        Optional<Roadmap> roadmap = roadmapDAO.findRoadmapById(roadmapId);
        if (roadmap.isEmpty()) {
            throw new RuntimeException("Roadmap not found");
        }

        Optional<Dashboard> dashboard = dashboardDAO.findDashboardById(dashboardId);
        if (dashboard.isEmpty()) {
            throw new RuntimeException("Dashboard not found");
        }

        if (!roadmap.get().isCompleted()) {
            throw new RuntimeException("Roadmap is not completed");
        }

        User user = userDAO.findById(dashboard.get().getUserId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        List<Stage> stages = stageDAO.findStagesByRoadmapId(roadmapId);
        List<Task> tasks = new ArrayList<>();
        stages.forEach(stage -> tasks.addAll(taskDAO.findAllTasksByStageId(stage.getStageId())));

        Certificate.create(user.getUsername(), roadmap.get().getTitle(),
                roadmap.get().getType(), roadmap.get().getLanguage(), roadmap.get().getStartTime(),
                roadmap.get().getFinishTime(), roadmap.get().getTotalTime(), roadmap.get().getRoadmapCommits(),
                stages.size(), tasks.size());
    }

    @Override
    public Roadmap updateRoadmap(UUID roadmapId, UpdateTitleAndColorRequest request) {
        Roadmap roadmap = roadmapDAO.findRoadmapById(roadmapId).get();
        roadmapDAO.refreshRoadmap(roadmap);

        if (roadmap.isCompleted()) {
            throw new RuntimeException("Couldn't update because the roadmap '"
                    + roadmap.getTitle() + "' is complete");
        }

        RoadmapColor color = RoadmapColor.getColor(request.getColor());

        if (color.getColorCode() < 0 || color.getColorCode() > 11) {
            throw new RuntimeException("Invalid color code: " + request.getColor());
        }

        roadmap.setTitle(request.getTitle());
        roadmap.setColor(color);

        return roadmapDAO.updateRoadmapTitleAndColor(roadmap);
    }

    @Override
    public Roadmap completeRoadmap(UUID roadmapId) {
        Roadmap roadmap = roadmapDAO.findRoadmapById(roadmapId).get();

        if(roadmap.isCompleted()) {
            throw new RuntimeException("Roadmap already completed");
        } else {
            roadmap.setIsCompleted(true);
        }

        return roadmapDAO.updateRoadmapIsCompleted(roadmap);
    }

    @Override
    public Roadmap deleteRoadmapById(UUID roadmapId) {

        Roadmap roadmap = roadmapDAO.findRoadmapById(roadmapId).get();

        if (roadmap.isCompleted()) {
            throw new RuntimeException("Couldn't delete because the roadmap '"
                    + roadmap.getTitle() + "' is complete");
        }

        roadmapDAO.deleteRoadmapById(roadmapId);
        dashboardDAO.refreshDashboard(roadmap.getDashboardId());
        return Roadmap.getNewInstanceWithOnlyId(roadmapId);
    }
}
