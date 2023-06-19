package modelo;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PessoaDAO {

    Conexao conectar = new Conexao();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public ArrayList listar() {
        ArrayList<Pessoa> listaPessoa = new ArrayList();
        String sql = "select *from pessoa";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pessoa aux = new Pessoa();
                aux.setId(rs.getInt(1));
                aux.setNome(rs.getString(2));
                aux.setEmail(rs.getString(3));
                aux.setTelefone(rs.getString(4));
                listaPessoa.add(aux);

            }

        } catch (Exception e) {

        }

        return listaPessoa;
    }

    public int adicionar(Pessoa p) {
        String sql = "insert into pessoa(nome, email,telefone) values (?,?,?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelefone());
            ps.executeUpdate();

        } catch (Exception e) {

        }
        return 1;
    }

    public int actualizar(Pessoa p) {
        String slq = "update pessoa set nome=?, email=?, telefone=? where id=?";
        int r = 0;

        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(slq);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelefone());
            ps.setInt(4, p.getId());
            r = ps.executeUpdate();
            if (r == 1) {
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {

        }
        return r;
    }
    
    public void eliminar(int id){
        String sql="delete from pessoa where id="+id;
        try{
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
        }catch (Exception e){
            
        }
    }

}
