package com.canwia.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.canwia.elasticsearch.dto.SearchRequestDto;
import com.canwia.elasticsearch.model.Users;
import com.canwia.elasticsearch.repository.UsersRepository;
import com.canwia.elasticsearch.util.ESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final JsonDataService jsonDataService;
    private final ElasticsearchClient elasticsearchClient;
    public Users createIndex(Users users) {
        return usersRepository.save(users);
    }

    public void addUsersFromJson() {
        log.info("Adding users from json!");

        List<Users> usersList = jsonDataService.readUsersFromJson();
        usersRepository.saveAll(usersList);
    }

    public List<Users> getAllUsersFromIndex(String indexName) {
        var query = ESUtil.createMatchAllQuery();
        log.info("ElasticSearch {}", query.toString());
        SearchResponse<Users> response = null;
        try {
           response = elasticsearchClient.search(
                    q->q.index(indexName).query(query), Users.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("ElasticSearch response {}",response);
        return extractUsersFromResponse(response);
    }


    public List<Users> extractUsersFromResponse(SearchResponse<Users> response){
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public List<Users> searchUsersByFieldsAnfValue(SearchRequestDto searchRequestDto) {
        Supplier<Query> querySupplier = ESUtil.buildFieldForFieldAndValue(
                searchRequestDto.getFieldNames().get(0),
                searchRequestDto.getSearchValue().get(0));
        log.info("ElasticSearch {}", querySupplier.toString());
        SearchResponse<Users> response = null;

        try {
            response = elasticsearchClient.search(q->q.index("user_index").query(querySupplier.get()),Users.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("ElasticSearch response {}",response);
        return extractUsersFromResponse(response);
    }

    public List<Users> searchUsersByFieldsAndCityWithQuery(String name, String city) {
        return usersRepository.searchByNameAndCity(name,city);
    }

    public List<Users> boolQuery(SearchRequestDto dto) {
        var query = ESUtil.createBoolQuery(dto);
        log.info("ElasticSearch query{}", query.toString());
        SearchResponse<Users> response = null;

        try {
            response = elasticsearchClient.search(q->q.index("user_index").query(query.get()), Users.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("ElasticSearch query{}", response.toString());
        return extractUsersFromResponse(response);

    }
}
