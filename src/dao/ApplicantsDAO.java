package dao;

import adt.*;
import entity.Applicant;
import java.io.*;


public class ApplicantsDAO {
  private String fileName = "applicants.dat"; // For security and maintainability, should not have filename hardcoded here.
  
  public void saveToFile(ListInterface<Applicant> applicantList) {
    File file = new File(fileName);
    try {
      ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
      ooStream.writeObject(applicantList);
      ooStream.close();
    } catch (FileNotFoundException ex) {
      System.out.println("\nFile not found");
    } catch (IOException ex) {
      System.out.println("\nCannot save to file");
    }
  }

  public ListInterface<Applicant> retrieveFromFile() {
    File file = new File(fileName);
    ListInterface<Applicant> applicantList = new LinkedList<>();
    try {
      ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
      applicantList = (LinkedList<Applicant>) (oiStream.readObject());
      oiStream.close();
    } catch (FileNotFoundException ex) {
      System.out.println("\nNo such file.");
    } catch (IOException ex) {
      System.out.println("\nCannot read from file.");
    } catch (ClassNotFoundException ex) {
      System.out.println("\nClass not found.");
    } finally {
      return applicantList;
    }
  }
}