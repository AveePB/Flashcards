package com.aveepb.flashcardapp.web.conn.flashcards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFlashcardRequest {

    private String englishWord;
}