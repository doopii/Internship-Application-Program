package control;

import adt.LinkedList;
import adt.ListInterface;
import entity.Applicant;


public class ApplicantManager {
    private ListInterface<Applicant> applicantList = new LinkedList<>();

    //  public ApplicantManager() {
       // applicantList = new LinkedList<>();
    //}

    public void addApplicant(Applicant applicant) {
        applicantList.add(applicant);
    }


    public boolean removeApplicant(String applicantID) {
        for (int i = 0; i < applicantList.size(); i++) {
            if (applicantList.get(i).getApplicantID().equals(applicantID)) {
                applicantList.remove(applicantList.get(i));
                return true;
            }
        }
        return false;
    }


    public Applicant searchApplicant(String applicantID) {
        for (int i = 0; i < applicantList.size(); i++) {
            if (applicantList.get(i).getApplicantID().equals(applicantID)) {
                return applicantList.get(i);
            }
        }
        return null;
    }

    public void displayApplicants() {
        for (int i = 0; i < applicantList.size(); i++) {
            System.out.println(applicantList.get(i));
        }
    }
}
