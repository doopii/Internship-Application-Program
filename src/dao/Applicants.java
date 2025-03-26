/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.Applicant;

/**
 *
 * @author Acer
 */
public class Applicants {
    //  Method to return a collection of with hard-coded entity values
    public ListInterface<Applicant> initializeApplicants() {
        ListInterface<Applicant> applicantList = new LinkedList<>();
        applicantList.add(new Applicant("A001", "Gary Tee","0128273661","gary@gmail.com", "Python"));
        applicantList.add(new Applicant("A002", "Wong Ning Ning","0139222345","ning@gmail.com", "C++"));
        applicantList.add(new Applicant("A003", "","0198364895","@gmail.com", "Python"));
        applicantList.add(new Applicant("A004", "","0139466678","@gmail.com", "Python"));
        applicantList.add(new Applicant("A005", "","0120037452","@gmail.com", "Python"));
        applicantList.add(new Applicant("A006", "","0193757112","@gmail.com", "Python"));
        applicantList.add(new Applicant("A007", "","0188836781","@gmail.com", "Python"));
        applicantList.add(new Applicant("A008", "","0103874613","@gmail.com", "Python"));
        applicantList.add(new Applicant("A009", "","0178263312","@gmail.com", "Python"));
        applicantList.add(new Applicant("A010", "","0192878233","@gmail.com", "Python"));
        applicantList.add(new Applicant("A011", "","01","@gmail.com", "Python"));
        applicantList.add(new Applicant("A012", "","01","@gmail.com", "Python"));
        applicantList.add(new Applicant("A013", "","01","@gmail.com", "Python"));
        applicantList.add(new Applicant("A014", "","","@gmail.com", "Python"));
        applicantList.add(new Applicant("A015", "","","@gmail.com", "Python"));
        applicantList.add(new Applicant("A016", "","","@gmail.com", "Python"));
        applicantList.add(new Applicant("A017", "","","@gmail.com", "Python"));
        applicantList.add(new Applicant("A018", "","","@gmail.com", "Python"));
        applicantList.add(new Applicant("A019", "","","@gmail.com", "Python"));
        applicantList.add(new Applicant("A020", "","","@gmail.com", "Python"));
        return applicantList;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        Applicants initializer = new Applicants();
        ListInterface<Applicant> applicantList = initializer.initializeApplicants();
        System.out.println("\nProducts:\n" + applicantList);
    }
}
