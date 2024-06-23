package com.canwia.elasticsearch.service;

import co.elastic.clients.elasticsearch.security.User;
import com.canwia.elasticsearch.model.Users;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JsonDataService {

    private final ObjectMapper objectMapper;


    public List<Users> readUsersFromJson(){

        try{
            ClassPathResource resource = new ClassPathResource("Data.json");
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream,new TypeReference<List<Users>>(){

            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
