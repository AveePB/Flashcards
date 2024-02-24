# Flashcards Spring Boot Project

## Table of contents
1. [Introduction](#introduction)
2. [AUTH Requests:](#auth_requests)
   - [Sign up](#sign_up_request)
   - [Log in](#log_in_request)
3. [FLASHCARD COLLECTION Requests:](#flashcard_collection_requests)
   - [Create flashcard collection](#create_flashcard_collection_request)
   - [Delete flashcard collection](#delete_flashcard_collection_request)
   - [Fetch owned flashcard collections](#fetch_owned_flashcard_collections_request)

## Introduction <a name="introduction"></a>
Welcome to the "Flashcards" project! This is an application that allows
users to expand their vocabulary. That project was created to learn
about the spring boot project design and the JWT security details.

## AUTH Requests <a name="auth_requests"></a>
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

## FLASHCARD COLLECTION Requests <a name="flashcard_collection_requests"></a>
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