package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entities.Playlist;

public interface PlayListRepository extends JpaRepository<Playlist, Integer> {

}
