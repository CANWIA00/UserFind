package com.canwia.elasticsearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import com.canwia.elasticsearch.dto.SearchRequestDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Supplier;

@UtilityClass
public class ESUtil {
    public static Query createMatchAllQuery() {
        return Query.of(q->q.matchAll(new MatchAllQuery.Builder().build()));
    }

    public static Supplier<Query> buildFieldForFieldAndValue(String field, String value) {
        return () -> Query.of(q->q.match(buildMatchQueryForFieldAnValue(field,value)));
    }

    private static MatchQuery buildMatchQueryForFieldAnValue(String field, String value) {
        return new MatchQuery.Builder()
                .field(field)
                .query(value)
                .build();
    }

    public static Supplier<Query> createBoolQuery(SearchRequestDto dto) {
        return () -> Query.of(q->q.bool(boolQuery(
                dto.getFieldNames().get(0)
                ,dto.getSearchValue().get(0)
                ,dto.getFieldNames().get(1)
                ,dto.getSearchValue().get(1)
        )));
    }

    private static BoolQuery boolQuery(String key1, String value1, String key2, String value2) {
        return new BoolQuery.Builder()
                .filter(termQuery(key1,value1))
                .must(matchQuery(key2,value2))
                .build();
    }

    /*Essential to be same */
    private static Query termQuery(String field, String value) {
        return Query.of(q->q.term(new TermQuery.Builder()
                .field(field)
                .value(value)
                .build()));
    }
    /*Essential to be similar */
    private static Query matchQuery(String field, String value) {
        return Query.of(q->q.match(new MatchQuery.Builder()
                .field(field)
                .query(value)
                .build()));
    }
}
