package service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import database.MemoryRegistrationStore;
import database.RegistrationStore;
import exceptions.BadRequestException;
import exceptions.NotAuthorizedException;
import exceptions.ServerException;
import models.UserCredentials;

import java.security.SecureRandom;

/**
 * @author: Nick Humrich
 * @date: 2/21/14
 */
public class AuthenticationService {
	private static String alphabet = "KIJ8GV6DX5A2L1M7";
	private RegistrationStore store;

	public AuthenticationService() {
		store = MemoryRegistrationStore.getInstance();
	}

	public UserCredentials startLoginProcess() {
		//generates a pepper
		UserCredentials user = new UserCredentials();
		SecureRandom random = new SecureRandom();
		int pepper = random.nextInt();
		if (pepper < 0) {
			pepper *= -1;
		}
		pepper = (pepper % 93) + 7;
		user.setPepper(pepper);
		return user;
	}

	public String login(UserCredentials user) {
		UserCredentials credentials = store.getCredentials(user.getUsername());
		user.setSalt(credentials.getSalt());
		String hash = completeHash(user);

		int id = 0;
		if (hash.equals(credentials.getPass())) {
			id = store.getUserId(user.getUsername());
		} else {
			throw new NotAuthorizedException("Invalid Credentials");
		}
		String encoded = encodeId(id);
		return encoded;
	}

	public String encodeId(int id) {
		String encodedId = "";
		while (id > 0) { //get new number
			int r = id % 16;
			id /= 16;
			encodedId = alphabet.charAt(r) + encodedId;
		}
		while (encodedId.length() < 6) { //append with 0's
			encodedId = alphabet.charAt(0) + encodedId;
		}
		return encodedId;
	}

	public int decodeId(String id) {
		int decodedId = 0;
		while (id.length() > 0) {
			char c = id.charAt(0);
			id = id.substring(1);
			int next = alphabet.lastIndexOf(c);
			decodedId = (decodedId * 16) +  next;
		}
		return decodedId;
	}

	public String register(UserCredentials user) {
		user.setSalt(getRandomSalt());
		String hash = completeHash(user);
		user.setPass(hash);
		int id = store.addUser(user);
		String encoded = encodeId(id);
		return encoded;
	}

	public String completeHash(UserCredentials user) {
		if (user.getPass() == null || user.getPass().isEmpty()) {
			throw new NotAuthorizedException();
		}
		if (user.getPepper() < 7 || user.getPepper() >= 100) {
			throw new BadRequestException("Pepper is out of acceptable range");
		}
		if (user.getSalt() == null || user.getSalt().isEmpty()) {
			throw new ServerException("User Credentials were not salted before being hashed");
		}

		String hash = user.getPass();
		String salt = user.getSalt();
		for (int i = user.getPepper(); i < 100; i++) {
			hash = Hashing.sha256().hashString(hash, Charsets.UTF_8).toString();
		}

		hash = Hashing.sha256().hashString(salt + hash, Charsets.UTF_8).toString();
		return hash;
	}

	public String getRandomSalt() {
		final int SALT_LENGTH = 16;

		final String salter = "zPxOcIvUbYnTnEmWlQ1L2k3j4h5g6f7d8s9a0ZpXoCiVuByNtMrAqG";
		StringBuilder salt = new StringBuilder();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < SALT_LENGTH; i++) {
			int next = random.nextInt();
			if (next < 0) next *= -1;
			int index = next % salter.length();
			char nextChar = salter.charAt(index);
			salt.append(nextChar);
		}
		return salt.toString();
	}
}
