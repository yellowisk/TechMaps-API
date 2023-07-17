package br.ifsp.techmaps.web.controller;

import br.ifsp.techmaps.domain.entities.dashboard.Dashboard;
import br.ifsp.techmaps.usecases.dashboard.DashboardCRUD;
import br.ifsp.techmaps.web.model.dashboard.response.DashboardResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RequestMapping("api/v1/dashboard/")
@RestController
public class DashboardController {

    private final DashboardCRUD dashboardCRUD;

    public DashboardController(DashboardCRUD dashboardCRUD) {
        this.dashboardCRUD = dashboardCRUD;
    }

    @GetMapping("{dashboardId}")
    public ResponseEntity<DashboardResponse> getDashboardById(
            @PathVariable UUID dashboardId) {
        Dashboard dashboard = dashboardCRUD.getDashboardById(dashboardId);
        return ResponseEntity.ok(DashboardResponse.createFromDashboard(dashboard));
    }

    @PostMapping
    public ResponseEntity<DashboardResponse> saveNewDashboard() {
        Dashboard dashboard = dashboardCRUD.saveNewDashboard();
        return ResponseEntity.ok(DashboardResponse.createFromDashboard(dashboard));
    }

}
