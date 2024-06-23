package com.canwia.elasticsearch.controller;

import com.canwia.elasticsearch.dto.SearchRequestDto;
import com.canwia.elasticsearch.model.Users;
import com.canwia.elasticsearch.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;


    @PostMapping
    public Users createIndex(@RequestBody Users users){
        return usersService.createIndex(users);
    }


    @PostMapping("/init-index")
    public void addUsersFromJson(){
        usersService.addUsersFromJson();
    }

    @GetMapping("/getAll/{indexName}")
    public List<Users> getAllUsersFromIndex(@PathVariable String indexName){
        return usersService.getAllUsersFromIndex(indexName);
    }

    @GetMapping("/search")
    public List<Users> searchUsersByFieldsAnfValue(@RequestBody SearchRequestDto searchRequestDto){
        return usersService.searchUsersByFieldsAnfValue(searchRequestDto);
    }

    @GetMapping("/search/{name}/{city}")
    public List<Users> searchUsersByFieldsAndCityWithQuery(@PathVariable String name, @PathVariable String city){
        return usersService.searchUsersByFieldsAndCityWithQuery(name,city);
    }

    @GetMapping("/bool")
    public List<Users> boolQuery(@RequestBody SearchRequestDto dto){
        return usersService.boolQuery(dto);
    }


}
