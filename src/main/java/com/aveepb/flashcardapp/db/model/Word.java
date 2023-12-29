package com.aveepb.flashcardapp.db.model;

import com.aveepb.flashcardapp.db.constant.WordType;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "words")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue
    private Integer id;

    private String englishMeaning;

    private String polishMeaning;

    @Enumerated(value = EnumType.STRING)
    private WordType type;

    @ManyToOne
    private Collection collection;
}
