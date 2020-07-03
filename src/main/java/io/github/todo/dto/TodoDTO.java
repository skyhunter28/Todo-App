package io.github.todo.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Builder
@Value

public class TodoDTO {
    public String _id;
    public String text;
    public Boolean done;

}
