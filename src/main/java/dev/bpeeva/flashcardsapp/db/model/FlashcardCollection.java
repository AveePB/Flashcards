package dev.bpeeva.flashcardsapp.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "flashcard_collections")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardCollection {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "flashcardCollection")
    private List<Flashcard> flashcardList;

    @ManyToOne
    private User user;
}
