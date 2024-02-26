# Flashcards Spring Boot Project

## Table of contents
1. [Introduction](#introduction)
2. [Requests](#requests)

## Introduction <a name="introduction"></a>
Welcome to the "Flashcards" project! This is an application that allows
users to expand their vocabulary. That project was created to learn
about the spring boot project design and the JWT security details.

# Requests <a name="requests"></a>
This chapter describes all the requests that are accepted by the project.
These requests are used to make changes in the database.

| Name                              | Type   | URL                                         | BODY                                                                                                                         |
|-----------------------------------|--------|---------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| Sign up                           | POST   | http://localhost:8080/auth/signup           | {<br/>"userRole": "USER",<br/>"username": "uniqueUsername",<br/>"password": "cust0mP4ssw0rd"<br/>}                           |
| Log in                            | POST   | http://localhost:8080/auth/login            | {<br/>"userRole": "USER",<br/>"username": "uniqueUsername",<br/>"password": "cust0mP4ssw0rd"<br/>}                           |
| Create flashcard collection       | POST   | http://localhost:8080/flashcards/{username}/{collectionName} | {<br/>}                                                                                                                      |
| Delete flashcard collection       | DELETE | http://localhost:8080/flashcards/{username}/{collectionName} | {<br/>}                                                                                                                      | 
| Fetch owned flashcard collections | GET    | http://localhost:8080/flashcards/{username} | {<br/>}                                                                                                                      |
| Create flashcard                  | POST   | http://localhost:8080/flashcards/{username}/{collectionName}/create | {<br/>"wordType": "NOUN/VERB/ADJECTIVE/ADVERB",<br/>"englishWord": "englishMeaning",<br/>"polishWord": "polishMeaning"<br/>} |
| Delete flashcard                  | DELETE | http://localhost:8080/flashcards/{username}/{collectionName}/delete | {<br/>"wordType": "NOUN/VERB/ADJECTIVE/ADVERB",<br/>"englishWord": "englishMeaning",<br/>"polishWord": "polishMeaning"<br/>} |
| Fetch random flashcard            | GET    | http://localhost:8080/flashcards/{username}/{collectionName}/random | {<br/>} |
| Fetch all flashcards              | GET    | http://localhost:8080/flashcards/{username}/{collectionName}/all | {<br/>} |
