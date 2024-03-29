package br.ifsp.techmaps.external.persistence;

import br.ifsp.techmaps.domain.entities.dashboard.Dashboard;
import br.ifsp.techmaps.domain.entities.stage.Stage;
import br.ifsp.techmaps.domain.entities.task.Task;
import br.ifsp.techmaps.domain.entities.task.TaskBody;
import br.ifsp.techmaps.domain.entities.task.TaskCommit;
import br.ifsp.techmaps.usecases.commit.gateway.CommitDAO;
import br.ifsp.techmaps.usecases.dashboard.gateway.DashboardDAO;
import br.ifsp.techmaps.usecases.stage.gateway.StageDAO;
import br.ifsp.techmaps.usecases.task.gateway.TaskDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class TaskDAOImpl implements TaskDAO {

    private final JdbcTemplate jdbcTemplate;
    private final StageDAO stageDao;
    private final CommitDAO commitDAO;
    private final DashboardDAO dashboardDAO;

    @Value("${queries.sql.task-dao.insert.task}")
    private String insertTaskQuery;

    @Value("${queries.sql.task-dao.select.task-by-id}")
    private String selectTaskByIdQuery;

    @Value("${queries.sql.task-dao.select.task-by-stage-id}")
    private String selectTasksByStageIdQuery;

    @Value("${queries.sql.task-dao.update.task-repository}")
    private String updateTaskRepositoryQuery;

    @Value("${queries.sql.task-dao.update.task-date-finished}")
    private String updateTaskDateFinishedQuery;

    @Value("${queries.sql.task-dao.update.task-number}")
    private String updateTaskNumberQuery;

    @Value("${queries.sql.task-dao.exists.task-id}")
    private String existsTaskIdQuery;

    public TaskDAOImpl(JdbcTemplate jdbcTemplate, StageDAO stageDao,
                       CommitDAO commitDAO, DashboardDAO dashboardDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.stageDao = stageDao;
        this.commitDAO = commitDAO;
        this.dashboardDAO = dashboardDAO;
    }


    @Override
    public Task saveNewTask(Task task) {
        jdbcTemplate.update(insertTaskQuery, task.getTaskId(), task.getStage().getStageId(),
                task.getStage().getTheme().name(), task.getTaskBody().name(), task.getNumber(),
                task.getRepositoryLink(), task.getDateCreated(), task.getDateFinished(), task.getDashboard().getDashboardId());
        return Task.createWithOnlyId(task.getTaskId());
    }

    @Override
    public Optional<Task> findTaskById(UUID taskId) {
        Task task;
        try {
            task = jdbcTemplate.queryForObject(selectTaskByIdQuery,
                this::mapperTaskFromRs, taskId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        if(Objects.isNull(task))
            throw new IllegalStateException();

        return Optional.of(task);
    }

    @Override
    public List<Task> findAllTasksByStageId(UUID stageId) {
        List<Task> tasks = jdbcTemplate.query(selectTasksByStageIdQuery,
                this::mapperTaskFromRs, stageId);
        tasks.forEach(task -> task.setTaskCommits(commitDAO.findTaskCommitByTaskId(task.getTaskId()).get()));
        return tasks;
    }

    @Override
    public Task updateRepository(Task task) {
        jdbcTemplate.update(updateTaskRepositoryQuery, task.getRepositoryLink(),
                task.getTaskId());

        return Task.createWithOnlyId(task.getTaskId());
    }

    @Override
    public Task updateDateFinished(Task task) {
        jdbcTemplate.update(updateTaskDateFinishedQuery,
                task.getDateFinished(), task.getTaskId());

        return Task.createWithOnlyId(task.getTaskId());
    }

    @Override
    public Boolean taskExists(UUID taskId) {
        Boolean exists = jdbcTemplate.queryForObject(existsTaskIdQuery, Boolean.class, taskId);
        return Objects.nonNull(exists) && exists;
    }

    private Task mapperTaskFromRs(ResultSet rs, int rowNum) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        UUID stageId = (UUID) rs.getObject("stage_id");
        TaskBody taskBody = TaskBody.valueOf(rs.getString("info"));
        int number = rs.getInt("task_number");
        String repository = rs.getString("repository_link");
        Timestamp dateCreated = rs.getTimestamp("date_created");
        Timestamp dateFinished = rs.getTimestamp("date_finished");
        UUID dashboardId = (UUID) rs.getObject("dashboard_id");

        Stage stage = stageDao.findStageById(stageId).orElseThrow(() -> new SQLDataException("Stage not found"));
        Dashboard dashboard = dashboardDAO.findDashboardById(dashboardId).orElseThrow(() -> new SQLDataException("Dashboard not found"));
        TaskCommit taskCommit = commitDAO.findTaskCommitByTaskId(id).get();

        return Task.createFull
                (id, stage, taskBody, number, repository, dateCreated, dateFinished, dashboard, taskCommit);
    }

}
