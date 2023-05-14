package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class SuppliersDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    //Variables para conectarse a la base de datos
    Connection conn;
    PreparedStatement pst; //Consultas
    ResultSet rs; //Para obtener datos de la consulta
    
    //Registrar proveedor
    public boolean registerSupplierQuery(Suppliers supplier){
        String query = "INSERT INTO suppliers (name, description, telephone, address, email, city, created, updated)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2,supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al registrar al proveedor"+ e);
            return false;
        }
    }
    
    //Metodo de listar proveedores
    public List listSuppliersQuery(String value){
        List<Suppliers> list_suppliers = new ArrayList();
        String query = "SELECT * FROM suppliers";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("description"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setAddress(rs.getString("address"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                list_suppliers.add(supplier);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_suppliers;
    }
    
    //Metodo para modificar proveedores
    public boolean updateSupplierQuery(Suppliers supplier){
        String query = "UPDATE suppliers SET name=?, description=?, telephone=?, address=?, email=?, city=?, updated=?"
                + " WHERE id=?";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2,supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al modificar los datos del proveedor"+ e);
            return false;
        }
    }
    
    //Metodo para eliminar proveedor    
    public boolean deleteSupplierQuery(int id){
        String query = "DELETE FROM suppliers WHERE id = " + id ;

        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No puedes eliminar un proveedor que tiene relación con otra tabla"+ e);
            return false;
        }
    }
}
