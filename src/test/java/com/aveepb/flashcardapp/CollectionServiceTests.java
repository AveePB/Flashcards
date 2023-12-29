package com.aveepb.flashcardapp;

import com.aveepb.flashcardapp.app.flashcards.CollectionService;
import com.aveepb.flashcardapp.db.constant.UserRole;
import com.aveepb.flashcardapp.db.model.Collection;
import com.aveepb.flashcardapp.db.model.User;
import com.aveepb.flashcardapp.db.repo.CollectionRepository;
import com.aveepb.flashcardapp.db.repo.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CollectionServiceTests {

	//Repositories:
	@Autowired
	private final CollectionRepository collectionRepository = null;
	@Autowired
	private final UserRepository userRepository = null;

	//Services:
	@Autowired
	private final CollectionService collectionService = null;

	//Encoder:
	@Autowired
	private final PasswordEncoder passwordEncoder = null;


	@Test
	void shouldReturnAllCollectionsOwnedByUser() {

		//Create user.
		User userStas = new User(null, "Stas", this.passwordEncoder.encode(""), UserRole.USER, null);
		userStas = this.userRepository.save(userStas);

		//Create collections owned by the user.
		Collection collection1 = new Collection(null, "Jobs", userStas, null);
		Collection collection2 = new Collection(null, "Animals", userStas, null);

		this.collectionRepository.save(collection1);
		this.collectionRepository.save(collection2);

		//Get list of collections owned by the user.
		List<Collection> collectionList = this.collectionService .getAllCollections(userStas);
		assertThat(collectionList.size()).isEqualTo(2);
	}

	@Test
	void shouldNotReturnCollectionsOwnedByTheOtherUser() {

		//Create users.
		User userStas = new User(null, "Stas", this.passwordEncoder.encode(""), UserRole.USER, null);
		User userAdam = new User(null, "Adam", this.passwordEncoder.encode(""), UserRole.USER, null);

		userStas = this.userRepository.save(userStas);
		userAdam = this.userRepository.save(userAdam);

		//Create collections owned by the users.
		Collection collection1 = new Collection(null, "Jobs", userStas, null);
		Collection collection2 = new Collection(null, "Animals", userAdam, null);

		this.collectionRepository.save(collection1);
		this.collectionRepository.save(collection2);


		//Get collections owned by Stas.
		List<Collection> stasCollectionList = this.collectionService .getAllCollections(userStas);
		assertThat(stasCollectionList.size()).isEqualTo(1);

		//Get collections owned by Adam.
		List<Collection> adamCollectionList = this.collectionService.getAllCollections(userAdam);
		assertThat(adamCollectionList.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnFlashcardsCollection() {

		//Create user.
		User userJohn = new User(null, "John", this.passwordEncoder.encode(""), UserRole.USER, null);
		userJohn = this.userRepository.save(userJohn);

		//Create collection owned by the users.
		this.collectionService.createCollection("jobs", userJohn);

		//Get collection owned by the John.
		assertThat(this.collectionService.getCollection("j0bs", userJohn).isPresent()).isEqualTo(false);
		assertThat(this.collectionService.getCollection("jobs", userJohn).isPresent()).isEqualTo(true);
	}

	@Test
	void shouldCreateFlashcardsCollection() {

		//Create user.
		User userJohn = new User(null, "John", this.passwordEncoder.encode(""), UserRole.USER, null);
		userJohn = this.userRepository.save(userJohn);

		//Create collection owned by the users.
		this.collectionService.createCollection("jobs", userJohn);

		//Get collections owned by John.
		List<Collection> johnCollectionList = this.collectionService.getAllCollections(userJohn);
		assertThat(johnCollectionList.size()).isEqualTo(1);
	}

	@Test
	void shouldNotCreateFlashCardsCollectionWithTakenName() {

		//Create user.
		User userXavier = new User(null, "Xavier", this.passwordEncoder.encode(""), UserRole.USER, null);
		userXavier = this.userRepository.save(userXavier);

		//Create collections owned by the users.
		this.collectionService.createCollection("Players", userXavier);
		this.collectionService.createCollection("Players", userXavier);
		this.collectionService.createCollection("Animals", userXavier);

		//Get collections owned by Xavier.
		List<Collection> xavierCollectionList = this.collectionService.getAllCollections(userXavier);
		assertThat(xavierCollectionList.size()).isEqualTo(2);
	}

}
