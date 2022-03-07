/*
* This is Marks program.
*
* @author  Abdul Basit Butt
* @version 1.0
* @since   2022-02-15
* Class Marks.
*/

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
* This is the marks program.
*/
final class Marks {

    /**
     * Created constant.
     */
    static final String THIS_DIR = "./";
    /**
     * Created constant.
     */
    static final String FRONT_BRACE = "[";
    /**
     * Created constant.
     */
    static final String BACK_BRACE = "]";
    /**
     * Created constant.
     */
    static final String COMMAS = ", ";
    /**
     * Created constant.
     */
    static final String COMMA = ",";

    /**
    * Prevent instantiation
    * Throw an exception IllegalStateException.
    * if this ever is called
    *
    * @throws IllegalStateException
    *
    */
    private Marks() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
    * The generateMarks() function.
    *
    * @param arrayOfStudents the collection of students
    * @param arrayOfAssignments the collection of assignments
    * @return the generated marks
    */
    public static String[][] generateMarks(final String[] arrayOfStudents,
                                       final String[] arrayOfAssignments) {
        final int seventyFive = 75;
        final int ten = 10;

        final int stuNum = arrayOfStudents.length;
        final int assNum = arrayOfAssignments.length;

        final String[][] markArray = new String[stuNum + 1][assNum + 1];
        markArray[0][0] = "-";

        int counter2 = 0;
        for (int counter1 = 1; counter1 < assNum; counter1++) {
            markArray[0][counter1] = arrayOfAssignments[counter2];
            counter2++;
        }
        markArray[0][assNum] = arrayOfAssignments[counter2];

        counter2 = 0;
        for (int counter1 = 1; counter1 < stuNum; counter1++) {
            markArray[counter1][0] = arrayOfStudents[counter2];
            counter2++;
        }
        markArray[stuNum][0] = arrayOfStudents[counter2];

        final Random random = new Random();

        for (int counter = 1; counter <= stuNum; counter++) {
            for (int counter1 = 1; counter1 <= assNum; counter1++) {
                final int mark = (int) Math.floor(
                        random.nextGaussian() * ten + seventyFive);
                markArray[counter][counter1] = String.valueOf(mark) + "%";
            }
        }
        for (int counter = 0; counter <= stuNum; counter++) {
            System.out.println(Arrays.toString(markArray[counter]));
        }
        return markArray;

    }

    /**
    * The starting main() function.
    *
    * @param args No args will be used
    */
    public static void main(final String[] args) {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssingments = new ArrayList<String>();
        final Path studentFilePath = Paths.get(THIS_DIR, args[0]);
        final Path assignmentFilePath = Paths.get(THIS_DIR, args[1]);
        final Charset charset = Charset.forName("UTF-8");

        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentFilePath, charset)) {
            String lineStudent = null;
            while ((lineStudent = readerStudent.readLine()) != null) {
                listOfStudents.add(lineStudent);
                System.out.println(lineStudent);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        try (BufferedReader readerAssignment = Files.newBufferedReader(
                                     assignmentFilePath, charset)) {
            String lineAssignment = null;
            while ((lineAssignment = readerAssignment.readLine()) != null) {
                listOfAssingments.add(lineAssignment);
                System.out.println(lineAssignment);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        final String[] studentArray = listOfStudents.toArray(new String[0]);
        final String[] assignmentArray = listOfAssingments.toArray(
                new String[0]);

        final String[][] marksArray = generateMarks(studentArray,
                                                    assignmentArray);

        try {
            System.out.println("File created: marks.csv");

            String firstLine = Arrays.toString(marksArray[0]);
            firstLine = firstLine.replace(COMMAS, COMMA);
            firstLine = firstLine.replace(FRONT_BRACE, "");
            firstLine = firstLine.replace(BACK_BRACE, "");

            final FileWriter myFile = new FileWriter("marks.csv");

            myFile.write(firstLine);

            for (int counter = 1; counter < marksArray.length; counter++) {
                String line = Arrays.toString(marksArray[counter]);
                line = line.replace(COMMAS, COMMA);
                line = line.replace(FRONT_BRACE, "");
                line = line.replace(BACK_BRACE, "");
                myFile.write(System.lineSeparator());
                myFile.write(line);

            }

            myFile.close();

        } catch (IOException error) {
            System.out.println("An error occurred.");
            error.printStackTrace();
        }

        System.out.println("\nDone.");

    }
}
