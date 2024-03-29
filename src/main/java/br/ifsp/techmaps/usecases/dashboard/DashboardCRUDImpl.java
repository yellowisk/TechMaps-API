package br.ifsp.techmaps.usecases.dashboard;

import br.ifsp.techmaps.domain.entities.dashboard.Dashboard;
import br.ifsp.techmaps.domain.entities.roadmap.Roadmap;
import br.ifsp.techmaps.usecases.dashboard.gateway.DashboardDAO;
import br.ifsp.techmaps.usecases.roadmap.gateway.RoadmapDAO;
import br.ifsp.techmaps.usecases.user.gateway.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardCRUDImpl implements DashboardCRUD {

    private final DashboardDAO dashboardDAO;
    private final RoadmapDAO roadmapDAO;

    public DashboardCRUDImpl(DashboardDAO dashboardDAO, RoadmapDAO roadmapDAO) {
        this.dashboardDAO = dashboardDAO;
        this.roadmapDAO = roadmapDAO;
    }

    @Override
    public Dashboard saveNewDashboard(UUID userId) {
        return dashboardDAO.saveNewDashboard(userId);
    }

    @Override
    public Dashboard getDashboardById(UUID dashboardId) {
        dashboardDAO.refreshDashboard(dashboardId);
        Dashboard dashboard = dashboardDAO.findDashboardById(dashboardId).get();
        return dashboard;
    }


    @Override
    public Dashboard getDashboardByUserId(UUID userId) {
        UUID dashboardId = dashboardDAO.findDashboardByUserId(userId).get().getDashboardId();
        dashboardDAO.refreshDashboard(dashboardId);
        Dashboard dashboard = dashboardDAO.findDashboardByUserId(userId).get();
        return dashboard;
    }
}
