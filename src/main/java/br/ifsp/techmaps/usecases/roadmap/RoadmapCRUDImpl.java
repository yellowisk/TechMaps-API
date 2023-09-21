package br.ifsp.techmaps.usecases.roadmap;

import br.ifsp.techmaps.domain.entities.roadmap.*;
import br.ifsp.techmaps.domain.entities.stage.StageEnum;
import br.ifsp.techmaps.usecases.dashboard.gateway.DashboardDAO;
import br.ifsp.techmaps.usecases.roadmap.gateway.RoadmapDAO;
import br.ifsp.techmaps.web.exception.BadRequestException;
import br.ifsp.techmaps.web.model.roadmap.request.CreateRoadmapRequest;
import br.ifsp.techmaps.web.model.roadmap.request.UpdateColorRequest;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.*;
import java.util.*;

@Service
public class RoadmapCRUDImpl implements RoadmapCRUD {

    private final RoadmapDAO roadmapDAO;
    private final DashboardDAO dashboardDAO;

    public RoadmapCRUDImpl(RoadmapDAO roadmapDAO, DashboardDAO dashboardDAO) {
        this.roadmapDAO = roadmapDAO;
        this.dashboardDAO = dashboardDAO;
    }

    @Override
    public Roadmap addNewRoadmap(UUID dashboardId, CreateRoadmapRequest request) {

        if(!dashboardDAO.dashboardExists(dashboardId)) {
            throw new RuntimeException("Couldn't find dashboard with id: " + dashboardId);
        }

        RoadmapType type = null;
        RoadmapLanguage lang = null;
        RoadmapColor color = null;

        List<RoadmapType> typesInsert = new ArrayList<>();
        List<RoadmapType> typesEquivalence = Arrays.asList(RoadmapType.BACKEND, RoadmapType.ANDROID, RoadmapType.FRONTEND);

        List<RoadmapLanguage> langsInsert = new ArrayList<>();
        List<RoadmapLanguage> langsEquivalence = Arrays.asList(RoadmapLanguage.JAVA, RoadmapLanguage.JAVASCRIPT,
                RoadmapLanguage.PYTHON, RoadmapLanguage.KOTLIN);

//        List<RoadmapColor> colorsInsert = new ArrayList<>();
//        List<RoadmapColor> colorsEquivalence = Arrays.asList(RoadmapColor.GREEN);

        Map<String, RoadmapType> typeEnums = new HashMap<>();
        typeEnums.put("back-end", RoadmapType.BACKEND);
        typeEnums.put("front-end", RoadmapType.FRONTEND);
        typeEnums.put("android", RoadmapType.ANDROID);

        for (Map.Entry<String, RoadmapType> entry : typeEnums.entrySet()) {
            typesInsert.add(entry.getValue());
        }

        Map<String, RoadmapLanguage> langEnums = new HashMap<>();
        langEnums.put("java", RoadmapLanguage.JAVA);
        langEnums.put("python", RoadmapLanguage.PYTHON);
        langEnums.put("javascript", RoadmapLanguage.JAVASCRIPT);
        langEnums.put("kotlin", RoadmapLanguage.KOTLIN);

        for (Map.Entry<String, RoadmapLanguage> entry : langEnums.entrySet()) {
            langsInsert.add(entry.getValue());
        }

        Map<RoadmapType, List<RoadmapLanguage>> typeToLangsMap = new HashMap<>();
        typeToLangsMap.put(RoadmapType.BACKEND, Arrays.asList(RoadmapLanguage.JAVA, RoadmapLanguage.PYTHON));
        typeToLangsMap.put(RoadmapType.FRONTEND, Arrays.asList(RoadmapLanguage.JAVASCRIPT));
        typeToLangsMap.put(RoadmapType.ANDROID, Arrays.asList(RoadmapLanguage.KOTLIN));

        if (!typeToLangsMap.get(request.getType()).contains(request.getLanguage())) {
            throw new RuntimeException("Roadmap type and language are incompatible.");
        } else {
            for (RoadmapType typeInsert : typesInsert) {
                for (RoadmapType equivalence : typesEquivalence) {
                    if (typeInsert == equivalence) {
                        type = typeInsert;
                    }
                }
            }

            for (RoadmapLanguage langInsert : langsInsert) {
                for (RoadmapLanguage equivalence : langsEquivalence) {
                    if (langInsert == equivalence) {
                        lang = langInsert;
                    }
                }
            }

            if(request.getColor() == 5) {
                color = RoadmapColor.GREEN;
            }
        }

        Roadmap roadmap = Roadmap.createWithoutId(request.getTitle(), type, RoadmapStatus.UNCOMPLETE,
                lang, color, Timestamp.valueOf(LocalDateTime.now()), null, null,
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
    public Roadmap updateRoadmap(UUID roadmapId, UpdateColorRequest request) {

        Roadmap roadmap = roadmapDAO.findRoadmapById(roadmapId).get();
        roadmapDAO.refreshRoadmap(roadmap);

        if (roadmap.getStatus().equals(RoadmapStatus.COMPLETE)) {
            throw new RuntimeException("Couldn't update because the roadmap '"
                    + roadmap.getTitle() + "' is complete");
        }

        roadmap.setColor(request.getColor());
        roadmap.setTitle(request.getTitle());

        return roadmapDAO.updateRoadmapTitleAndColor(roadmap);
    }

    @Override
    public Roadmap deleteRoadmapById(UUID roadmapId) {

        Roadmap roadmap = roadmapDAO.findRoadmapById(roadmapId).get();

        if (roadmap.getStatus().equals(RoadmapStatus.COMPLETE)) {
            throw new RuntimeException("Couldn't delete because the roadmap '"
                    + roadmap.getTitle() + "' is complete");
        }

        roadmapDAO.deleteRoadmapById(roadmapId);
        dashboardDAO.refreshDashboard(roadmap.getDashboardId());
        return Roadmap.getNewInstanceWithOnlyId(roadmapId);
    }
}
