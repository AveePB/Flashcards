package dev.bpeeva.app.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String motherLang;
    private String foreignLang;

    @ManyToOne
    private FlashcardCollection coll;
}
