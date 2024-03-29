package br.ifsp.techmaps.external.persistence;

import br.ifsp.techmaps.domain.entities.roadmap.*;
import br.ifsp.techmaps.usecases.roadmap.gateway.RoadmapDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.*;
import java.util.*;

@Repository
public class RoadmapDAOImpl implements RoadmapDAO {

    private final JdbcTemplate jdbcTemplate;
    public RoadmapDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("${queries.sql.roadmap-dao.exists.roadmap-by-id}")
    private String existsRoadmapQuery;
    @Value("${queries.sql.roadmap-dao.insert.roadmap}")
    private String insertRoadmapQuery;
    @Value("${queries.sql.roadmap-dao.select.roadmap-by-id}")
    private String selectRoadmapByIdQuery;
    @Value("${queries.sql.roadmap-dao.select.all-completed-roadmaps-by-dashboard-id}")
    private String selectAllCompleteRoadmapsByDashboardIdQuery;
    @Value("${queries.sql.roadmap-dao.select.all-roadmaps-by-dashboard-id}")
    private String selectAllRoadmapsByDashboardIdQuery;
    @Value("${queries.sql.roadmap-dao.select.count-commits-by-task-with-stage-from-roadmap-by-id}")
    private String selectCountCommitsByTaskWithStageFromRoadmapByIdQuery;
    @Value("${queries.sql.roadmap-dao.update.completed-roadmap}")
    private String updateRoadmapStatusAndCommitCounterQuery;
    @Value("${queries.sql.roadmap-dao.update.roadmap-commit-counter}")
    private String updateRoadmapCommitCounterQuery;
    @Value("${queries.sql.roadmap-dao.update.roadmap-total-time}")
    private String updateRoadmapTotalTimeQuery;
    @Value("${queries.sql.roadmap-dao.update.roadmap-title-and-color}")
    private String updateRoadmapTitleAndColorQuery;
    @Value("${queries.sql.roadmap-dao.delete.roadmap-by-id}")
    private String deleteRoadmapByIdQuery;
    @Value("${queries.sql.roadmap-dao.delete.task-by-roadmap-id}")
    private String deleteTaskByRoadmapIdQuery;

    @Override
    public Boolean RoadmapExists(UUID roadmapId) {
        return jdbcTemplate.queryForObject(existsRoadmapQuery, Boolean.class, roadmapId);
    }

    @Override
    public Roadmap saveRoadmap(Roadmap roadmap) {

        UUID roadmapId = UUID.randomUUID();

        jdbcTemplate.update(insertRoadmapQuery, roadmapId, roadmap.getTitle(), roadmap.getType().name(),
                roadmap.isCompleted(), roadmap.getLanguage().name(),
                roadmap.getColor().name(), roadmap.getStartTime(), roadmap.getFinishTime(),
                roadmap.getTotalTime(), roadmap.getRoadmapCommits(), roadmap.getDashboardId());

        return Roadmap.createWithoutStages(roadmapId, roadmap.getTitle(), roadmap.getType(),
                roadmap.isCompleted(), roadmap.getLanguage(), roadmap.getColor(),
                roadmap.getStartTime(), roadmap.getFinishTime(), roadmap.getTotalTime(),
                roadmap.getRoadmapCommits(), roadmap.getDashboardId());
    }

    @Override
    public Roadmap refreshRoadmap(Roadmap roadmap) {

        if(!roadmap.isCompleted()) {
            Long totalTime = Long.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime() - roadmap.getStartTime().getTime());
            roadmap.setTotalTime(totalTime/1000); //divide totalTime/1000 by 60 to get minutes
            jdbcTemplate.update(updateRoadmapTotalTimeQuery, roadmap.getTotalTime(), roadmap.getRoadmapId());
            int counterCommitsStaged = jdbcTemplate.queryForObject(selectCountCommitsByTaskWithStageFromRoadmapByIdQuery, Integer.class, roadmap.getRoadmapId());
            jdbcTemplate.update(updateRoadmapCommitCounterQuery, counterCommitsStaged, roadmap.getRoadmapId());
        }

        return roadmap;
    }

    @Override
    public Optional<Roadmap> findRoadmapById(UUID roadmapId) {
        try {
            Roadmap roadmap = jdbcTemplate.queryForObject(selectRoadmapByIdQuery,
                    this::mapperRoadmapFromRs, roadmapId);

            if (Objects.isNull(roadmap)) {
                throw new IllegalStateException();
            }

            return Optional.of(roadmap);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Roadmap> findAllCompletedByDashboardId(UUID dashboardId) {
        return jdbcTemplate.query(selectAllCompleteRoadmapsByDashboardIdQuery, this::mapperRoadmapFromRs, dashboardId);
    }

    @Override
    public List<Roadmap> findAllByDashboardId(UUID dashboardId) {
        return jdbcTemplate.query(selectAllRoadmapsByDashboardIdQuery, this::mapperRoadmapFromRs, dashboardId);
    }

    @Override
    public Roadmap updateRoadmapIsCompleted(Roadmap roadmap) {

        Timestamp startTime = roadmap.getStartTime();
        Timestamp finishTime = Timestamp.valueOf(LocalDateTime.now());
        Long totalTime = (finishTime.getTime() - startTime.getTime())/1000;

        jdbcTemplate.update(updateRoadmapStatusAndCommitCounterQuery, roadmap.isCompleted(),
                roadmap.getRoadmapCommits(), finishTime, totalTime, roadmap.getRoadmapId());

        return findRoadmapById(roadmap.getRoadmapId()).get();
    }

    @Override
    public Roadmap updateRoadmapTitleAndColor(Roadmap roadmap) {
        jdbcTemplate.update(updateRoadmapTitleAndColorQuery, roadmap.getTitle(),
                roadmap.getColor().name(), roadmap.getRoadmapId());
        return roadmap;
    }

    @Override
    public Roadmap deleteRoadmapById(UUID roadmapId) {
        Optional<Roadmap> roadmap = findRoadmapById(roadmapId);

        if (roadmap.isEmpty()) {
            throw new IllegalStateException("Couldn't find roadmpa with id: " + roadmapId);
        }

        jdbcTemplate.update(deleteTaskByRoadmapIdQuery, roadmapId);
        jdbcTemplate.update(deleteRoadmapByIdQuery, roadmapId);
        return roadmap.get();
    }

    public Roadmap mapperRoadmapFromRs(ResultSet rs, int rowNum) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String title = rs.getString("title");
        RoadmapType type = RoadmapType.valueOf(rs.getString("type"));
        Boolean isCompleted = rs.getBoolean("is_completed");
        RoadmapLanguage language = RoadmapLanguage.valueOf(rs.getString("lang"));
        RoadmapColor color = RoadmapColor.valueOf(rs.getString("color"));
        Timestamp startTime = rs.getTimestamp("start_time");
        Timestamp finishTime = rs.getTimestamp("finish_time");
        Long total_time = rs.getLong("total_time");
        int commit_counter = rs.getInt("commit_counter");
        UUID dashboardId = (UUID) rs.getObject("dashboard_id");

        return Roadmap.createWithoutStages(id, title, type, isCompleted, language, color,
                startTime, finishTime, total_time, commit_counter, dashboardId);
    }

}
