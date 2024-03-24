package com.dev.app.db.model;

import jakarta.persistence.Column;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "collection")
    private List<Flashcard> flashcardList;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;
}
