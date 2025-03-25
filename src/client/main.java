package client;

import control.ApplicantManager;
import entity.Applicant;
import dao.InterviewInitializer;
import adt.ListInterface;
import entity.Interview;
import control.Schedule; 


public class main {
    public static void main(String[] args) {
        ApplicantManager manager = new ApplicantManager();

        // Adding applicants
        manager.addApplicant(new Applicant("A001", "Alice", "123456789", "alice@example.com", "Java"));
        manager.addApplicant(new Applicant("A002", "Bob", "987654321", "bob@example.com", "Python"));

        // Display all applicants
        System.out.println("All Applicants:");
        manager.displayApplicants();

        // Searching for an applicant
        System.out.println("\nSearching for A001:");
        System.out.println(manager.searchApplicant("A001"));

        // Removing an applicant
        System.out.println("\nRemoving A002...");
        boolean removed = manager.removeApplicant("A002");
        System.out.println("Removed: " + removed);

        // Display all applicants after removal
        System.out.println("\nApplicants after removal:");
        manager.displayApplicants();
        
    }
}
