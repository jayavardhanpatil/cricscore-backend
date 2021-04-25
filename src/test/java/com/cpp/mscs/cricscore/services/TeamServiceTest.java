package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.City;
import com.cpp.mscs.cricscore.models.Team;
import com.cpp.mscs.cricscore.repositories.TeamRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TeamServiceTest {

    @Mock
    TeamRepo teamRepo;

    @InjectMocks
    TeamService teamService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTeam() {
        when(teamRepo.save(any(Team.class))).thenReturn(
                Team.builder()
                        .teamId(1L)
                        .teamCity(City.builder().cityId(1L).cityName("Bangalore").state("KA").build())
                        .teamName("Test Team")
                        .playerList(new ArrayList<>())
                        .build()
        );
        Long teamId = teamService.addTeam(Team.builder().build());
        assertEquals(1, teamId);
    }

    @Test
    void getTeam() {
        when(teamRepo.findById(anyLong())).thenReturn(
                Optional.ofNullable(Team.builder()
                        .teamId(1L)
                        .teamCity(City.builder().cityId(1L).cityName("Bangalore").state("KA").build())
                        .teamName("Test Team")
                        .playerList(new ArrayList<>())
                        .build())
        );
        Optional<Team> team = teamService.getTeam(1L);
        assertEquals(1L, team.get().getTeamId());
        assertEquals("Test Team", team.get().getTeamName());
        assertEquals(1L, team.get().getTeamCity().getCityId());
        assertEquals("Bangalore", team.get().getTeamCity().getCityName());
    }

    @Test
    void getAllTeams() {

        List<Team> teams = new ArrayList<>();
        teams.add(Team.builder()
                        .teamId(1L)
                        .teamCity(City.builder().cityId(1L).cityName("Bangalore").state("KA").build())
                        .teamName("Test Team")
                        .playerList(new ArrayList<>())
                        .build());
        teams.add(Team.builder()
                        .teamId(2L)
                        .teamCity(City.builder().cityId(2L).cityName("Mumbai").state("MH").build())
                        .teamName("Test Team 1")
                        .playerList(new ArrayList<>())
                        .build());
        teams.add(Team.builder()
                .teamId(3L)
                .teamCity(City.builder().cityId(1L).cityName("Bangalore").state("KA").build())
                .teamName("Test Team 2")
                .playerList(new ArrayList<>())
                .build());

        when(teamRepo.findAll()).thenReturn(teams);
        List<Team> temsList = teamService.getAllTeams();
        assertEquals(teams.size(), temsList.size());
    }

}