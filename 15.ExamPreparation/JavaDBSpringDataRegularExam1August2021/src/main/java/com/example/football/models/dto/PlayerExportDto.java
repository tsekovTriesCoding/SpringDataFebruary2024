package com.example.football.models.dto;

import com.example.football.models.entity.Position;

public class PlayerExportDto {
    private String playerFirstName;
    private String playerLastName;
    private Position positionName;
    private String teamName;
    private String stadiumName;

    public PlayerExportDto(String playerFirstName, String playerLastName, Position positionName, String teamName, String stadiumName) {
        this.playerFirstName = playerFirstName;
        this.playerLastName = playerLastName;
        this.positionName = positionName;
        this.teamName = teamName;
        this.stadiumName = stadiumName;
    }

    @Override
    public String toString() {
        return String.format("Player - %s %s\n" +
                        "\tPosition - %s\n" +
                        "\tTeam - %s\n" +
                        "\tStadium - %s", this.playerFirstName, this.playerLastName,
                this.positionName,
                this.teamName,
                this.stadiumName);
    }
}
