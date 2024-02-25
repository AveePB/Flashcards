# Flashcards Spring Boot Project

## Table of contents
1. [Introduction](#introduction)
2. [Auth Requests:](#auth_requests)
   - [Sign up](#sign_up_request)
   - [Log in](#log_in_request)
3. [Flashcard Collection Requests:](#flashcard_collection_requests)
   - [Create flashcard collection](#create_flashcard_collection_request)
   - [Delete flashcard collection](#delete_flashcard_collection_request)
   - [Fetch owned flashcard collections](#fetch_owned_flashcard_collections_request)
4. [Flashcard Requests:](#flashcard_requests)
   - [Create flashcard](#create_flashcard_request)
   - [Delete flashcard](#delete_flashcard_request)
   - [Fetch random flashcard](#fetch_random_flashcard_request)
   - [Fetch all flashcards](#fetch_all_flashcards_request)

## Introduction <a name="introduction"></a>
Welcome to the "Flashcards" project! This is an application that allows
users to expand their vocabulary. That project was created to learn
about the spring boot project design and the JWT security details.

## Auth Requests <a name="auth_requests"></a>
This chapter describes all kinds of authorization requests. This application
has only two such requests (***sign up*** & ***log in***).

### Sign up <a name="sign_up_request"></a>
Sign up request is used to create a new user.
```
//URL: http://localhost:8080/auth/signup
//TYPE: POST
//BODY:
{
   "userRole": "USER",
   "username": "uniqueUsername",
   "password": "cust0mP4ssw0rd"   
}
```

### Log in <a name="log_in_request"></a>
Log in request is used to get the authorization.
```
//URL: http://localhost:8080/auth/login
//TYPE: POST
//BODY:
{
   "userRole": "USER",
   "username": "uniqueUsername",
   "password": "cust0mP4ssw0rd"   
}
```

## Flashcard Collection Requests <a name="flashcard_collection_requests"></a>
In this chapter we take a look at the flashcard collection requests.
There are only three of these (***create flashcard collection***, ***delete flashcard collection*** & ***fetch owned flashcard collections***).

### Create flashcard collection <a name="create_flashcard_collection_request"></a>
Create flashcard collection request is used to create a new flashcard collection.
```
//URL: http://localhost:8080/flashcards/{username}/{collectionName}
//TYPE: POST 
//BODY:
{

}
//BEARER TOKEN: JWT
```

### Delete flashcard collection <a name="delete_flashcard_collection_request"></a>
Delete flashcard collection request is used to delete an existing flashcard collection.
```
//URL: http://localhost:8080/flashcards/{username}/{collectionName}
//TYPE: DELETE
//BODY:
{

}
//BEARER TOKEN: JWT
```

### Fetch owned flashcard collections <a name="fetch_owned_flashcard_collections_request"></a>
Fetch owned flashcard collections request is used to fetch a list of owned
flashcard collections.
```
//URL: http://localhost:8080/flashcards/{username}
//TYPE: GET
//BODY:
{

}
//BEARER TOKEN: JWT
```

## Flashcard Requests <a name="flashcard_requests"></a>
This chapter is used to talk about the flashcard requests. We've got only 
four of them (***create flashcard***, ***delete flashcard***, ***fetch random flashcard*** & ***fetch all flashcards***).

### Create flashcard <a name="create_flashcard_request"></a>
Create flashcard request is used to create a new flashcard.
```
//URL: http://localhost:8080/flashcards/{username}/{collectionName}/create
//TYPE: POST
//BODY:
{
   "wordType": "NOUN/VERB/ADJECTIVE/ADVERB",
   "englishWord": "englishMeaning",
   "polishWord": "polishMeaning"
}
```

### Delete flashcard <a name="delete_flashcard_request"></a>
Delete flashcard request is used to delete an existing flashcard.
```
//URL: http://localhost:8080/flashcards/{username}/{collectionName}/delete
//TYPE: DELETE
//BODY:
{
   "wordType": "NOUN/VERB/ADJECTIVE/ADVERB",
   "englishWord": "englishMeaning",
   "polishWord": "polishMeaning"
}
```

### Fetch random flashcard <a name="fetch_random_flashcard_request"></a>
Fetch random flashcard request is used to get the random flashcard from
the collection.
```
//URL: http://localhost:8080/flashcards/{username}/{collectionName}/random
//TYPE: GET
//BODY:
{

}
```

### Fetch all flashcards <a name="fetch_all_flashcards_request"></a>
Fetch all flashcards request is used to get all flashcard from the collection.
```
//URL: http://localhost:8080/flashcards/{username}/{collectionName}/all
//TYPE: GET
//BODY:
{

}
```
