package com.canwia.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private List<String> fieldNames;
    private List<String> searchValue;
}
