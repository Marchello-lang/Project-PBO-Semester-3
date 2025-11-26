package controller;

import dao.BuildingDAO;
import dao.RoomDAO;
import model.Building;
import model.Room;
import view.EditBuildingDialog;

import javax.swing.JFrame;

public class AdminController {

    private BuildingDAO buildingDAO = new BuildingDAO();
    private RoomDAO roomDAO = new RoomDAO();

    // Method untuk membuka dialog edit gedung
    public void openEditBuilding(JFrame parent, Building building) {
        // buka dialog edit, passing object building
        new EditBuildingDialog(parent, this, building);
    }

    // Method untuk update gedung ke database
    public boolean updateBuilding(Building building) {
        try {
            return buildingDAO.updateBuilding(building); // method update di DAO
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addRoom(Room room) {
        try {
            return roomDAO.addRoom(room); // Memanggil method DAO
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRoom(Room room) {
        try {
            return roomDAO.updateRoom(room); // Memanggil method DAO
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRoom(int roomId) {
        try {
            return roomDAO.deleteRoom(roomId); // Memanggil method DAO
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
