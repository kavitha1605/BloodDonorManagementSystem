import java.io.*;
import java.util.*;

class Donor {
    String id, name, gender, bloodGroup, location, contact, status;
    int age;

    public Donor(String id, String name, int age, String gender, String bloodGroup,
                 String location, String contact, String status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.contact = contact;
        this.status = status;
    }

    public String toString() {
        return id + "," + name + "," + age + "," + gender + "," + bloodGroup + "," + location + "," + contact + "," + status;
    }
}

public class BloodBankSystem {

    static ArrayList<Donor> donors = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "donors.txt";

    public static void main(String[] args) {
        loadFromFile();
        int choice;

        do {
            System.out.println("\n===============================================");
            System.out.println("   BLOOD BANK DONOR & REQUEST MANAGEMENT SYSTEM");
            System.out.println("===============================================");
            System.out.println("1. Add New Donor (CREATE)");
            System.out.println("2. View All Donors (READ)");
            System.out.println("3. Update Donor Details (UPDATE)");
            System.out.println("4. Delete Donor Record (DELETE)");
            System.out.println("5. Search Donor by Blood Group");
            System.out.println("6. Save & Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addDonor();
                case 2 -> viewDonors();
                case 3 -> updateDonor();
                case 4 -> deleteDonor();
                case 5 -> searchDonor();
                case 6 -> saveToFile();
                default -> System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 6);
    }

    // CREATE Record
    public static void addDonor() {
        System.out.print("Enter Donor ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Gender (M/F): ");
        String gender = sc.nextLine();
        System.out.print("Enter Blood Group (A+,O-,B+ etc.): ");
        String bloodGroup = sc.nextLine();
        System.out.print("Enter Location: ");
        String location = sc.nextLine();
        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();
        System.out.print("Status (Available/Unavailable): ");
        String status = sc.nextLine();

        donors.add(new Donor(id, name, age, gender, bloodGroup, location, contact, status));
        System.out.println("\nDonor Added Successfully!");
    }

    // READ Records
    public static void viewDonors() {
        if (donors.isEmpty()) {
            System.out.println("No donor records found!");
            return;
        }
        System.out.println("\n------------------------------------------------------");
        System.out.println("ID   Name       Age BloodGroup  Location    Contact   Status");
        System.out.println("------------------------------------------------------");
        for (Donor d : donors) {
            System.out.printf("%s  %s  %d   %s     %s     %s    %s\n",
                    d.id, d.name, d.age, d.bloodGroup, d.location, d.contact, d.status);
        }
    }

    // UPDATE Record
    public static void updateDonor() {
        System.out.print("Enter Donor ID to Update: ");
        String id = sc.nextLine();
        for (Donor d : donors) {
            if (d.id.equals(id)) {
                System.out.print("Enter New Status (Available/Unavailable): ");
                d.status = sc.nextLine();
                System.out.println("Record Updated Successfully!");
                return;
            }
        }
        System.out.println("Donor Not Found!");
    }

    // DELETE
    public static void deleteDonor() {
        System.out.print("Enter Donor ID to Delete: ");
        String id = sc.nextLine();
        for (Donor d : donors) {
            if (d.id.equals(id)) {
                donors.remove(d);
                System.out.println("Record Deleted Successfully!");
                return;
            }
        }
        System.out.println("Donor Not Found!");
    }

    // SEARCH
    public static void searchDonor() {
        System.out.print("Enter Blood Group Needed: ");
        String bg = sc.nextLine();
        boolean found = false;

        System.out.println("\nAvailable Donors:");
        System.out.println("------------------------------------------------------");
        for (Donor d : donors) {
            if (d.bloodGroup.equalsIgnoreCase(bg) && d.status.equalsIgnoreCase("Available")) {
                System.out.printf("%s  %s  %s  %s\n", d.id, d.name, d.location, d.contact);
                found = true;
            }
        }
        if (!found) System.out.println("No donors available for this blood group.");
    }

    // SAVE DATA TO FILE
    public static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Donor d : donors) pw.println(d.toString());
            System.out.println("Data Saved Successfully. Exiting Program...");
        } catch (Exception e) {
            System.out.println("Error Saving File!");
        }
    }

    // LOAD FROM FILE
    public static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                donors.add(new Donor(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5], data[6], data[7]));
            }
        } catch (Exception e) {
            System.out.println("No existing data to load.");
        }
    }
}
