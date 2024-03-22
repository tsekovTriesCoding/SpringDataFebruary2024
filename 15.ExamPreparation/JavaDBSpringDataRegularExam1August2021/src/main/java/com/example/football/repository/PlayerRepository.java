package com.example.football.repository;

import com.example.football.models.dto.PlayerExportDto;
import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);

    @Query("select new com.example.football.models.dto.PlayerExportDto(p.firstName, p. lastName, p.position, p.team.name, p.team.stadiumName) " +
            "from Player as p " +
            "where p.birthDate > :after and p.birthDate < :before " +
            "order by p.stat.shooting desc, p.stat.passing desc , p.stat.endurance desc, p.lastName")
    Optional<List<PlayerExportDto>> findBestPlayers(LocalDate after, LocalDate before);
}
