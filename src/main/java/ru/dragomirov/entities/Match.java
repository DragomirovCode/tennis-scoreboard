package ru.dragomirov.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Player1")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "Player2")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "Winner")
    private Player winner;

    public Match() {}

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
