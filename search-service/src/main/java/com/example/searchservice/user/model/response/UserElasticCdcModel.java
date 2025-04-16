package com.example.searchservice.user.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.searchservice.user.User;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "user.public.users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserElasticCdcModel implements Serializable {

    @Field(name = "op", type = FieldType.Keyword)
    private String op;
    @Field(name = "before", type = FieldType.Object)
    private Object before;
    @Field(name = "after", type = FieldType.Object)
    private User after;

    public UserElasticCdcModel() {
    }

    public UserElasticCdcModel(String op, Object before, User after) {
        this.op = op;
        this.before = before;
        this.after = after;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public User getAfter() {
        return after;
    }

    public void setAfter(User after) {
        this.after = after;
    }
}
