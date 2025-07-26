package com.gbInc.acortadorUrl.util;

import java.util.Random;

public class IdGenerator {

	private final char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	private final Random random = new Random();

	private final int RANDOM_STRING_SIZE = 10;

	public String generateId() {

		StringBuilder code = new StringBuilder();

		for (int i = 0; i < this.RANDOM_STRING_SIZE; i++) {

			code.append(this.getRandomLetter());

		}
		return code.toString();
	}

	private char getRandomLetter() {
		return this.letters[this.random.nextInt(this.letters.length)];
	}

}
