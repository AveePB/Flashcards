package dev.bpeeva.app.db.model;

import dev.bpeeva.app.db.constant.WordClass;
import jakarta.persistence.*;

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

    @Enumerated(value = EnumType.STRING)
    private WordClass wordClass;

    private String motherLang;
    private String foreignLang;

    @ManyToOne
    private FlashcardCollection coll;
}
