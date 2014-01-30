package db_io_app;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class AddStudent {

	public JPanel panel;
	private JTextField txtStudentPrefName;
	private JTextField txtStudentGivenName;
	private JTextField txtStudentLastName;
	private JTextField txtStudentNetId;
	private JTextField txtStudentId;
	private DB db;
	
	public AddStudent(DB db) {
		
		panel = new JPanel();
		this.db = db;
		addElements();
	}
	public void addElements() {
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 90, 291, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 20, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		
		// Add the elements to the Student panel
		addByuIdLine();
		addNetIdLine();
		addPrefNameLine();
		addGivenNameLine();
		addLastNameLine();
		addSubmitButton();	
	}
	
	public void addByuIdLine() {
		
		JLabel lblStudentId = new JLabel("BYU-ID (9-digit)");
		GridBagConstraints gbc_lblStudentId = new GridBagConstraints();
		gbc_lblStudentId.anchor = GridBagConstraints.EAST;
		gbc_lblStudentId.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentId.gridx = 1;
		gbc_lblStudentId.gridy = 0;
		panel.add(lblStudentId, gbc_lblStudentId);
	
		txtStudentId = new JTextField();
		GridBagConstraints gbc_txtStudentId = new GridBagConstraints();
		gbc_txtStudentId.insets = new Insets(0, 0, 5, 5);
		gbc_txtStudentId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStudentId.gridx = 2;
		gbc_txtStudentId.gridy = 0;
		panel.add(txtStudentId, gbc_txtStudentId);
		txtStudentId.setColumns(10);
	}
	
	public void addNetIdLine() {
		
		JLabel lblStudentNetId = new JLabel("Net-ID");
		GridBagConstraints gbc_lblStudentNetId = new GridBagConstraints();
		gbc_lblStudentNetId.anchor = GridBagConstraints.EAST;
		gbc_lblStudentNetId.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentNetId.gridx = 1;
		gbc_lblStudentNetId.gridy = 1;
		panel.add(lblStudentNetId, gbc_lblStudentNetId);

		txtStudentNetId = new JTextField();
		GridBagConstraints gbc_txtStudentNetId = new GridBagConstraints();
		gbc_txtStudentNetId.insets = new Insets(0, 0, 5, 5);
		gbc_txtStudentNetId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStudentNetId.gridx = 2;
		gbc_txtStudentNetId.gridy = 1;
		panel.add(txtStudentNetId, gbc_txtStudentNetId);
		txtStudentNetId.setColumns(10);
	}
	
	public void addPrefNameLine() {
		
		JLabel lblStudentPrefName = new JLabel("Pref Name");
		GridBagConstraints gbc_lblStudentPrefName = new GridBagConstraints();
		gbc_lblStudentPrefName.anchor = GridBagConstraints.EAST;
		gbc_lblStudentPrefName.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentPrefName.gridx = 1;
		gbc_lblStudentPrefName.gridy = 2;
		panel.add(lblStudentPrefName, gbc_lblStudentPrefName);

		txtStudentPrefName = new JTextField();
		GridBagConstraints gbc_studentPrefNameTextField = new GridBagConstraints();
		gbc_studentPrefNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_studentPrefNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studentPrefNameTextField.gridx = 2;
		gbc_studentPrefNameTextField.gridy = 2;
		panel.add(txtStudentPrefName, gbc_studentPrefNameTextField);
		txtStudentPrefName.setColumns(10);
	}
	
	public void addGivenNameLine() {
	
		JLabel lblStudentGivenName = new JLabel("Given Name");
		GridBagConstraints gbc_lblStudentGivenName = new GridBagConstraints();
		gbc_lblStudentGivenName.anchor = GridBagConstraints.EAST;
		gbc_lblStudentGivenName.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentGivenName.gridx = 1;
		gbc_lblStudentGivenName.gridy = 3;
		panel.add(lblStudentGivenName, gbc_lblStudentGivenName);

		txtStudentGivenName = new JTextField();
		GridBagConstraints gbc_txtStudentGivenName = new GridBagConstraints();
		gbc_txtStudentGivenName.insets = new Insets(0, 0, 5, 5);
		gbc_txtStudentGivenName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStudentGivenName.gridx = 2;
		gbc_txtStudentGivenName.gridy = 3;
		panel.add(txtStudentGivenName, gbc_txtStudentGivenName);
		txtStudentGivenName.setColumns(10);
	}
	
	public void addLastNameLine() {
		
		JLabel lblStudentLastName = new JLabel("Last Name");
		GridBagConstraints gbc_lblStudentLastName = new GridBagConstraints();
		gbc_lblStudentLastName.anchor = GridBagConstraints.EAST;
		gbc_lblStudentLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentLastName.gridx = 1;
		gbc_lblStudentLastName.gridy = 4;
		panel.add(lblStudentLastName, gbc_lblStudentLastName);

		txtStudentLastName = new JTextField();
		GridBagConstraints gbc_txtStudentLastName = new GridBagConstraints();
		gbc_txtStudentLastName.insets = new Insets(0, 0, 5, 5);
		gbc_txtStudentLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStudentLastName.gridx = 2;
		gbc_txtStudentLastName.gridy = 4;
		panel.add(txtStudentLastName, gbc_txtStudentLastName);
		txtStudentLastName.setColumns(10);
	}
	
	public void addSubmitButton() {
	
		JButton btnSubmitStudent = new JButton("Submit");
		GridBagConstraints gbc_btnSubmitStudent = new GridBagConstraints();
		gbc_btnSubmitStudent.gridx = 2;
		gbc_btnSubmitStudent.gridy = 5;
		btnSubmitStudent.addActionListener(new StudentAction());
		panel.add(btnSubmitStudent, gbc_btnSubmitStudent);
	}
	
	/**
	 * Action when the Student "submit" button is clicked
	 * This will insert a new student with the given fields
	 */
	class StudentAction implements ActionListener {
		
		public void actionPerformed (ActionEvent e) {
			
			// Get the strings to insert from our textboxes
			DBCollection students = db.getCollection("student");
			BasicDBObject newStudent = new BasicDBObject();
			newStudent.put("StudentID", txtStudentId.getText());
			newStudent.put("NetID", txtStudentNetId.getText());
			newStudent.put("PrefName", txtStudentPrefName.getText());
			newStudent.put("GivenName", txtStudentGivenName.getText());
			newStudent.put("LastName", txtStudentGivenName.getText());
			
			// Initialize the student to have 0 schedules and no history
			List<BasicDBObject> schedules = new ArrayList<>();
			List<BasicDBObject> history = new ArrayList<>();
			newStudent.put("Schedules", schedules);
			newStudent.put("History", history);

			students.insert(newStudent);
			
			clearTextBoxes();
		}
		
		public void clearTextBoxes() {
			
			txtStudentId.setText("");
			txtStudentNetId.setText("");
			txtStudentPrefName.setText("");
			txtStudentGivenName.setText("");
			txtStudentLastName.setText("");
			txtStudentId.requestFocus();
		}
	}
}