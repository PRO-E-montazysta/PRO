package com.emontazysta.mapper;


import com.emontazysta.model.Comment;
import com.emontazysta.model.dto.CommentDto;

public class CommentMapper {

    public static CommentDto commentToDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
