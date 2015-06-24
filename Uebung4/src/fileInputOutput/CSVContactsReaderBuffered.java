package fileInputOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import application.ContactDetails;
import exceptions.EmptyFirstNameException;
import exceptions.EmptyNameException;
import exceptions.EmptyNumberException;
/**
 * 
 * @author Franziska Huth, Roman Duhr, Marcus Bätz
 *
 */
public class CSVContactsReaderBuffered {
	
	/**
	 * Creates a Path File from the filename String and 
	 * call then the same function with this pathfile and the splitter file
	 * @param filename, String with the filename
	 * @param splitter, String with the splitter symbol
	 * @return List of ContactDetails
	 * @throws IOException if the filename is not found in the rootpath
	 */
	public static List<ContactDetails> readEntityList(String filename, String splitter)throws IOException {
		return readEntityList(Paths.get(filename), splitter);
	}

	/**
	 * Reads all Lines of an Textfile using the Splitter to separate each Parameter from the other in each Line.
	 * For each Line, creating on new ContactdetailsObject with all Parameters from this Line an push it into the List.
	 * @param source, pathfile containing the filename and the path. Without changes, the path is the rootpath of the program
	 * @param splitter, String with the splitter symbol
	 * @return List of ContactDetails
	 * @throws IOException if the filename and/or the path is not found
	 */
	public static List<ContactDetails> readEntityList(Path source, String splitter) throws IOException {
		List<ContactDetails> target = new ArrayList<>();
			BufferedReader br = Files.newBufferedReader(source);
			String line;
			while ((line=br.readLine()) != null) {
				String [] detailsAr = line.split(splitter);
				try {
					target.add(new ContactDetails(detailsAr[0], detailsAr[1], detailsAr[2], detailsAr[3], detailsAr[4]));
				} catch (EmptyNameException | EmptyFirstNameException | EmptyNumberException ex) { 	 	
					ex.printStackTrace(System.err);
				}
			}

		return target;
	}
	
}
