package com.aveepb.flashcardapp.web.conn.flashcards.response;

import com.aveepb.flashcardapp.db.constant.WordType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardResponse {

    private WordType wordType;
    private String englishMeaning;
    private String polishMeaning;
}
