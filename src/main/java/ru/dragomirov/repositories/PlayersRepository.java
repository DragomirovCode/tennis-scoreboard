package ru.dragomirov.repositories;

import ru.dragomirov.models.Players;

import java.util.List;

public interface PlayersRepository {
    List<Players> findAll();
    Players findById(int id);
    Players findByName(String name);
    void save(Players players);
    void update(Players players);
    void delete(Players players);
}
