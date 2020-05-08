package com.example.demo.controller;

import java.util.List;
import java.util.Optional;


import com.example.demo.dao.Repo;
import com.example.demo.model.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class RestController {
    
    @Autowired
    Repo repo;

    //Get data By ID
    @GetMapping(value="/")
    @ResponseBody
    public List<Item> showInventory() {
        return repo.findAll();
    }
    @GetMapping(value="/{id}")
    @ResponseBody
    public Optional<Item> oneInventory(@PathVariable("id") final int id) {
            return repo.findById(id);
    }

    //Get Data by name
    @GetMapping(value="/name/{name}")
    @ResponseBody
    public Optional<Item> inventoryName(@PathVariable("name") final String name) {
            return repo.findByName(name);
    }


    //Inserting a data request
    @PostMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody final Item item) {
        try {
            repo.save(item);
        } catch (final Exception e) {
            return (String) e.getMessage();
        }
        return "The data Has Been successfully added.";
    }
    
    //deleting data by taking id
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") final int id) {
        try {
            System.out.println(id);
            repo.deleteById(id);
        } catch (final Exception e) {
            return e.toString();
        }
        
        return "The entry has been deleted";
    }
    

    //data update according to id
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public String set(@PathVariable("id") final String ID, @RequestBody final Item item) {
        final int id = Integer.parseInt(ID);
        System.out.println(item);
        try {
            if (item.getId() == 0) {
                item.setId(id);
            }
            final Optional<Item> temp = repo.findById(id);
            System.out.println(temp);
            temp.ifPresent(data->{
            if(item.getName()==null){
                item.setName(data.getName());
            }
            if(item.getCost()==0){
                item.setCost(data.getCost());
            }
            if(item.getDesc()==null){
                item.setDesc(data.getDesc());
            }
            if(item.getQuantity()==0){
                item.setQuantity(data.getQuantity());
            }

            repo.save(item);
        });
        } catch (final Exception e) {
            return e.toString();
        }
        
        return "The entry has been Updated";
    }
}