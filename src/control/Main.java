package control;

import boundary.JobManagementUI;
import utility.MessageUI;


/**
 *
 * @author yijia
 */
public class Main {
    private final JobManagementUI jobUI;
    private final JobManagement jobManagement;
    private final ApplicantManager applicantManagement;
    private final ScheduleManager scheduleManagement;
    private final MatchingEngine matchingEngine;
    
    public Main(){
        jobUI = new JobManagementUI();
        jobManagement = new JobManagement();
        applicantManagement = new ApplicantManager();
        scheduleManagement = new ScheduleManager();
        matchingEngine = new MatchingEngine();
    }

    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.start();
    }
    
    /**
     * Displays the main menu and delegates to appropriate manager based on user choice
     */
    public void start() {
        int choice;
        do {
            choice = jobUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    jobManagement.selectCompanyMenu();
                    break;
                case 2:
                    applicantManagement.applicantManagementMenu();
                    break;
                case 3:
                    scheduleManagement.scheduleMenu();
                    break;
                case 4:
                    matchingEngine.run();
                    break;
                case 0: 
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
}
    
    
        //public void applicantMenu(){
        //    do {
        //        choice = jobUI.getApplicantMenuChoice();
        //        switch (choice) {
         //           case 1 :
        //                jobManagement.searchJobs();
        //            case 2 :
        //                jobUI.listAllJobs(getAllJobs());
        //            case 0 : 
        //                System.out.println(" *** Returning to Main Menu... *** ");
        //                return;
        //            default:
        //               MessageUI.displayInvalidChoiceMessage();
        //        }
        //    } while (choice != 0);
        //}
        //}

