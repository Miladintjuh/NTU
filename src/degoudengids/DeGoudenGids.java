/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package degoudengids;

import javax.swing.JFrame;

/**
 *
 * @author Rebano
 */
public class DeGoudenGids {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database database = new Database();
        JFrame NTU = new Gids(database);
        NTU.setVisible(true);
        NTU.setTitle("NTU Database");
    }
    
}