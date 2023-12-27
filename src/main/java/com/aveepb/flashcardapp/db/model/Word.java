package com.aveepb.flashcardapp.db.model;

import com.aveepb.flashcardapp.db.constant.WordType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
