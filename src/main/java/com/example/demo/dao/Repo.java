package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.example.demo.model.Item;

public interface Repo extends JpaRepository< Item ,Integer> {

	Optional<Item> findByName(String name);
    
}