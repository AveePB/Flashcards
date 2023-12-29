package com.aveepb.flashcardapp.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "collections")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "collection")
    private List<Word> wordList;
}
