package ru.dragomirov.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Matches")
public class Matches {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Player1")
    private Players player1;

    @ManyToOne
    @JoinColumn(name = "Player2")
    private Players player2;

    @ManyToOne
    @JoinColumn(name = "Winner")
    private Players winner;

    public Matches() {}



}
