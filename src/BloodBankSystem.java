import java.sql.*;
import java.util.Scanner;

public class BloodBankSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("   BLOOD BANK DONOR MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Add New Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. Update Donor Status");
            System.out.println("4. Delete Donor");
            System.out.println("5. Search by Blood Group");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addDonor();
                case 2 -> viewDonors();
                case 3 -> updateDonor();
                case 4 -> deleteDonor();
                case 5 -> searchDonor();
                case 6 -> System.out.println("Thank you for using system!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    // ===============================
    // ADD DONOR (CREATE)
    // ===============================
    static void addDonor() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Gender: ");
            String gender = sc.nextLine();

            System.out.print("Blood Group: ");
            String bg = sc.nextLine();

            System.out.print("Location: ");
            String location = sc.nextLine();

            System.out.print("Contact: ");
            String contact = sc.nextLine();

            System.out.print("Status (Available/Unavailable): ");
            String status = sc.nextLine();

            String sql = "INSERT INTO donors VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, gender);
            ps.setString(5, bg);
            ps.setString(6, location);
            ps.setString(7, contact);
            ps.setString(8, status);

            ps.executeUpdate();

            System.out.println("\n✅ Donor Added Successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ ID already exists!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===============================
    // VIEW DONORS (READ)
    // ===============================
    static void viewDonors() {

        try (Connection con = DBConnection.getConnection()) {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM donors");

            System.out.println();
            System.out.printf("%-6s %-18s %-5s %-8s %-15s %-12s %-12s\n",
                    "ID", "Name", "Age", "BG", "Location", "Contact", "Status");

            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-6s %-18s %-5d %-8s %-15s %-12s %-12s\n",
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("bloodGroup"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // UPDATE DONOR
    // ===============================
    static void updateDonor() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Donor ID: ");
            String id = sc.nextLine();

            System.out.print("New Status: ");
            String status = sc.nextLine();

            String sql = "UPDATE donors SET status=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Updated Successfully!");
            else
                System.out.println("❌ Donor Not Found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // DELETE DONOR
    // ===============================
    static void deleteDonor() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Donor ID: ");
            String id = sc.nextLine();

            String sql = "DELETE FROM donors WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Deleted Successfully!");
            else
                System.out.println("❌ Donor Not Found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // SEARCH BY BLOOD GROUP
    // ===============================
    static void searchDonor() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Blood Group: ");
            String bg = sc.nextLine();

            String sql = "SELECT * FROM donors WHERE bloodGroup=? AND status='Available'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bg);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\nAvailable Donors:");
            System.out.println("--------------------------------------------");

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getString("name") + " | " +
                        rs.getString("location") + " | " +
                        rs.getString("contact"));
            }

            if (!found)
                System.out.println("No donors available!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
