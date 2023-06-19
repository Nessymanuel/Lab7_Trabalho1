package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Pessoa;
import modelo.PessoaDAO;
import vista.Vista;

public class Controlador implements ActionListener {

    PessoaDAO dao = new PessoaDAO();
    Pessoa aux = new Pessoa();
    Vista vista = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(Vista v) {

        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnOk.addActionListener(this);
        this.vista.jButton6.addActionListener(this);

        listar(vista.tabela);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnListar) {
            limparTabela();
            listar(vista.tabela);

        }

        if (e.getSource() == vista.btnGuardar) {
            adicionar();
        }

        if (e.getSource() == vista.btnEditar) {
            int fila = vista.tabela.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Deve selecionar uam fila");
            } else {
                int id = Integer.parseInt((String) vista.tabela.getValueAt(fila, 0).toString());
                String nome = ((String) vista.tabela.getValueAt(fila, 1));
                String email = ((String) vista.tabela.getValueAt(fila, 2));
                String telefone = ((String) vista.tabela.getValueAt(fila, 3));
                vista.txtId.setText("" + id);
                vista.txtNome.setText(nome);
                vista.txtEmail.setText(email);
                vista.txtTelefone.setText(telefone);

            }
        }

        if (e.getSource() == vista.btnOk) {
            actualizar();
            limparTabela();
            listar(vista.tabela);

        }

        if (e.getSource() == vista.jButton6) {
            int fila = vista.tabela.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Deves selecionar uma linha");
            } else {
                int id = Integer.parseInt((String) vista.tabela.getValueAt(fila, 0).toString());

                dao.eliminar(id);
                JOptionPane.showMessageDialog(vista, "Usuário eliminado");
                limparTabela();
                listar(vista.tabela);
            }
        }
    }

    public void listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        ArrayList<Pessoa> lista = dao.listar();
        Object[] object = new Object[4];

        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNome();
            object[2] = lista.get(i).getEmail();
            object[3] = lista.get(i).getTelefone();
            modelo.addRow(object);

        }

        vista.tabela.setModel(modelo);

    }

    public void adicionar() {
        String nome = vista.txtNome.getText();
        String email = vista.txtEmail.getText();
        String telefone = vista.txtTelefone.getText();
        aux.setNome(nome);
        aux.setEmail(email);
        aux.setTelefone(telefone);
        int r = dao.adicionar(aux);

        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Usuário cadastradao com sucesso!");
            limparTabela();
            listar(vista.tabela);
        } else {
            JOptionPane.showMessageDialog(vista, "Erro");
        }

    }

    private void actualizar() {
        int id = Integer.parseInt(vista.txtId.getText());
        String nome = vista.txtNome.getText();
        String email = vista.txtEmail.getText();
        String telefone = vista.txtTelefone.getText();
        aux.setNome(nome);
        aux.setId(id);
        aux.setEmail(email);
        aux.setTelefone(telefone);

        int r = dao.actualizar(aux);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Usuário actualizado com sucesso!");

        } else {
            JOptionPane.showMessageDialog(vista, "Erro!");

        }

    }

    public void limparTabela() {
        for (int i = 0; i < vista.tabela.getRowCount(); i++) {
            modelo.removeRow(i);
            i--;
        }
    }

}
