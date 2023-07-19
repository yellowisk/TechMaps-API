package br.ifsp.techmaps.usecases.dashboard;

import br.ifsp.techmaps.domain.entities.dashboard.Dashboard;
import br.ifsp.techmaps.usecases.dashboard.gateway.DashboardDAO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DashboardCRUDImpl implements DashboardCRUD {

    private final DashboardDAO dashboardDAO;

    public DashboardCRUDImpl(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    @Override
    public Dashboard saveNewDashboard() {
        UUID dashboardId = UUID.randomUUID();
        return dashboardDAO.saveNewDashboard(dashboardId);
    }

    @Override
    public Dashboard getDashboardById(UUID dashboardId) {
        dashboardDAO.refreshDashboard(dashboardId);
        Dashboard dashboard = dashboardDAO.findDashboardById(dashboardId).get();
        return dashboard;
    }
}
