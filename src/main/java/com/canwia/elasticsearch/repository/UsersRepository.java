package com.canwia.elasticsearch.repository;

import com.canwia.elasticsearch.model.Users;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UsersRepository extends ElasticsearchRepository<Users,String> {

    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"lastname\": \"?0\"}}, {\"match\": {\"age\": \"?0\"}}]}}")
    List<Users> searchByNameAndCity(String name, String city);
}
