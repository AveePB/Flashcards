package com.dev.app.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "flashcards")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flashcard {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String motherLang;

    @Column(nullable = false)
    private String foreignLang;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlashcardCollection collection;
}
