package ui;

import model.Employee;
import service.EmployeeService;
import service.EmployeeServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployeeForm extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtEmail, txtPhone, txtDepartment, txtSalary;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private EmployeeService employeeService = new EmployeeServiceImpl();

    public EmployeeForm() {
        initComponents();
        loadEmployees();
    }

    private void initComponents() {
        setTitle("Employee Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtName = new JTextField(10);
        txtEmail = new JTextField(10);
        txtPhone = new JTextField(10);
        txtDepartment = new JTextField(10);
        txtSalary = new JTextField(10);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        table = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Name", "Email", "Phone", "Department", "Salary"}, 0
        ));
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        formPanel.add(new JLabel("Name:")); formPanel.add(txtName);
        formPanel.add(new JLabel("Email:")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Phone:")); formPanel.add(txtPhone);
        formPanel.add(new JLabel("Department:")); formPanel.add(txtDepartment);
        formPanel.add(new JLabel("Salary:")); formPanel.add(txtSalary);
        formPanel.add(btnAdd); formPanel.add(btnUpdate);
        formPanel.add(btnDelete); formPanel.add(btnClear);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addEmployeeAction());
        btnUpdate.addActionListener(e -> updateEmployeeAction());
        btnDelete.addActionListener(e -> deleteEmployeeAction());
        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1) {
                    txtName.setText(table.getValueAt(selectedRow, 1).toString());
                    txtEmail.setText(table.getValueAt(selectedRow, 2).toString());
                    txtPhone.setText(table.getValueAt(selectedRow, 3).toString());
                    txtDepartment.setText(table.getValueAt(selectedRow, 4).toString());
                    txtSalary.setText(table.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    private void addEmployeeAction() {
        try {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String department = txtDepartment.getText().trim();
            double salary = Double.parseDouble(txtSalary.getText().trim());

            Employee emp = new Employee(name, email, phone, department, salary);
            employeeService.addEmployee(emp);

            JOptionPane.showMessageDialog(this, "Employee Added Successfully");
            loadEmployees();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateEmployeeAction() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1) { JOptionPane.showMessageDialog(this, "Select an employee first"); return; }

        int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String department = txtDepartment.getText().trim();
        double salary = Double.parseDouble(txtSalary.getText().trim());

        Employee emp = new Employee(name, email, phone, department, salary);
        emp.setId(id);
        employeeService.updateEmployee(emp);

        JOptionPane.showMessageDialog(this, "Employee Updated");
        loadEmployees();
        clearFields();
    }

    private void deleteEmployeeAction() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1) { JOptionPane.showMessageDialog(this, "Select an employee first"); return; }

        int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            employeeService.deleteEmployee(id);
            JOptionPane.showMessageDialog(this, "Employee Deleted");
            loadEmployees();
            clearFields();
        }
    }

    private void loadEmployees() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for(Employee emp : employeeService.getAllEmployees()) {
            model.addRow(new Object[]{
                    emp.getId(),
                    emp.getName(),
                    emp.getEmail(),
                    emp.getPhone(),
                    emp.getDepartment(),
                    emp.getSalary()
            });
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtDepartment.setText("");
        txtSalary.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeForm().setVisible(true));
    }
}
