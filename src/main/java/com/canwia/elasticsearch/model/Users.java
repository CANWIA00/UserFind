package com.canwia.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user_index")
@Setting(settingPath = "static/es-settings.json")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {

    @Id
    private String id;
    @Field(name="name",type = FieldType.Text, analyzer = "custom_index",searchAnalyzer = "custom_search")
    private String name;
    @Field(name="city",type = FieldType.Text)
    private String city;
    @Field(name="age",type = FieldType.Integer)
    private int age;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Friends> friendsList;

}
