package fr.creativedata.formation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurelien on 03/10/15.
 */
public class FormationHadoop  {

	public static List<Firstname> firstnames = initializeFirstnames();


	public enum Sexe {
		M,
		F
	}

	private static class Firstname {

		String firstname;

		Sexe sexe;

		public Firstname(String firstname, Sexe sexe) {
			this.firstname = firstname;
			this.sexe = sexe;
		}
	}

	private static List<Firstname> initializeFirstnames() {
		List<Firstname> firstnames = new ArrayList<Firstname>();

		InputStream stream = FormationHadoop.class.getClassLoader().getResourceAsStream("prenoms.csv");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(stream));
			String line = reader.readLine();

			while (line != null) {
				String[] split = line.split(",");
				firstnames.add(new Firstname(split[0], Sexe.valueOf(split[1])));
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firstnames;
	}

	public static void main(String[] args) {
		
        // Avro Schema
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(FormationHadoop.class.getClassLoader().getResourceAsStream("avro/User.avsc"));


	}
	
	 private static User generateRandomUser() {

        Random r = new Random();

        Firstname firstname = firstnames.get(r.nextInt(firstnames.size()));

        String[] listProfiles = {"profile1", "profile2", "profile3", "profile4", "profile5"};

        Set<String> profiles = new HashSet<>();
        profiles.add(listProfiles[r.nextInt(listProfiles.length)]);
        profiles.add(listProfiles[r.nextInt(listProfiles.length)]);
        profiles.add(listProfiles[r.nextInt(listProfiles.length)]);

        User userRandom= User.newBuilder()
                .setFirstname(firstname.firstname)
                .setAge(RandomUtils.nextInt(100))
                .setSex(firstname.sexe.name())
                .setDepartment(RandomUtils.nextInt(95))
                .setHasLoyaltyCardNumber(RandomUtils.nextBoolean())
                .setProfiles(new ArrayList<>(profiles))
                .build();

        return userRandom;

    }



}
