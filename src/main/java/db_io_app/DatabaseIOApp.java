package db_io_app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DatabaseIOApp extends JFrame {
	private static final long serialVersionUID = -1687041352632171598L;
	private JPanel contentPane;
	private JTextField studentIdTextField;
	private JTextField studentFirstNameTextField;
	private JTextField courseTitleTextField;
	private JTextField courseIdTextField;
	private JTextField courseDescriptionTextField;
	private JTextField courseSectionsTextField;
	private JTextField coursePrereqsTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseIOApp frame = new DatabaseIOApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatabaseIOApp() {
		setTitle("Database IO Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Student", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 90, 291, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 20, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblStudentId = new JLabel("Student Id");
		GridBagConstraints gbc_lblStudentId = new GridBagConstraints();
		gbc_lblStudentId.anchor = GridBagConstraints.EAST;
		gbc_lblStudentId.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentId.gridx = 1;
		gbc_lblStudentId.gridy = 0;
		panel.add(lblStudentId, gbc_lblStudentId);

		studentIdTextField = new JTextField();
		GridBagConstraints gbc_studentIdTextField = new GridBagConstraints();
		gbc_studentIdTextField.insets = new Insets(0, 0, 5, 5);
		gbc_studentIdTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studentIdTextField.gridx = 2;
		gbc_studentIdTextField.gridy = 0;
		panel.add(studentIdTextField, gbc_studentIdTextField);
		studentIdTextField.setColumns(10);

		JLabel lblStudentFirstName = new JLabel("Student First Name");
		GridBagConstraints gbc_lblStudentFirstName = new GridBagConstraints();
		gbc_lblStudentFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblStudentFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblStudentFirstName.gridx = 1;
		gbc_lblStudentFirstName.gridy = 1;
		panel.add(lblStudentFirstName, gbc_lblStudentFirstName);

		studentFirstNameTextField = new JTextField();
		GridBagConstraints gbc_studentFirstNameTextField = new GridBagConstraints();
		gbc_studentFirstNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_studentFirstNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studentFirstNameTextField.gridx = 2;
		gbc_studentFirstNameTextField.gridy = 1;
		panel.add(studentFirstNameTextField, gbc_studentFirstNameTextField);
		studentFirstNameTextField.setColumns(10);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Course", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 90, 342, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblCourseTitle = new JLabel("Course Title");
		GridBagConstraints gbc_lblCourseTitle = new GridBagConstraints();
		gbc_lblCourseTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblCourseTitle.anchor = GridBagConstraints.EAST;
		gbc_lblCourseTitle.gridx = 1;
		gbc_lblCourseTitle.gridy = 0;
		panel_1.add(lblCourseTitle, gbc_lblCourseTitle);

		courseTitleTextField = new JTextField();
		GridBagConstraints gbc_courseTitleTextField = new GridBagConstraints();
		gbc_courseTitleTextField.insets = new Insets(0, 0, 5, 5);
		gbc_courseTitleTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_courseTitleTextField.gridx = 2;
		gbc_courseTitleTextField.gridy = 0;
		panel_1.add(courseTitleTextField, gbc_courseTitleTextField);
		courseTitleTextField.setColumns(10);

		JLabel lblCourseId = new JLabel("Course Id");
		GridBagConstraints gbc_lblCourseId = new GridBagConstraints();
		gbc_lblCourseId.anchor = GridBagConstraints.EAST;
		gbc_lblCourseId.insets = new Insets(0, 0, 5, 5);
		gbc_lblCourseId.gridx = 1;
		gbc_lblCourseId.gridy = 1;
		panel_1.add(lblCourseId, gbc_lblCourseId);

		courseIdTextField = new JTextField();
		GridBagConstraints gbc_courseIdTextField = new GridBagConstraints();
		gbc_courseIdTextField.insets = new Insets(0, 0, 5, 5);
		gbc_courseIdTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_courseIdTextField.gridx = 2;
		gbc_courseIdTextField.gridy = 1;
		panel_1.add(courseIdTextField, gbc_courseIdTextField);
		courseIdTextField.setColumns(10);

		JLabel lblCourseDescription = new JLabel("Course Description");
		GridBagConstraints gbc_lblCourseDescription = new GridBagConstraints();
		gbc_lblCourseDescription.anchor = GridBagConstraints.EAST;
		gbc_lblCourseDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblCourseDescription.gridx = 1;
		gbc_lblCourseDescription.gridy = 2;
		panel_1.add(lblCourseDescription, gbc_lblCourseDescription);

		courseDescriptionTextField = new JTextField();
		GridBagConstraints gbc_courseDescriptionTextField = new GridBagConstraints();
		gbc_courseDescriptionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_courseDescriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_courseDescriptionTextField.gridx = 2;
		gbc_courseDescriptionTextField.gridy = 2;
		panel_1.add(courseDescriptionTextField, gbc_courseDescriptionTextField);
		courseDescriptionTextField.setColumns(10);

		JLabel lblSections = new JLabel("Sections");
		GridBagConstraints gbc_lblSections = new GridBagConstraints();
		gbc_lblSections.anchor = GridBagConstraints.EAST;
		gbc_lblSections.insets = new Insets(0, 0, 5, 5);
		gbc_lblSections.gridx = 1;
		gbc_lblSections.gridy = 3;
		panel_1.add(lblSections, gbc_lblSections);

		courseSectionsTextField = new JTextField();
		GridBagConstraints gbc_courseSectionsTextField = new GridBagConstraints();
		gbc_courseSectionsTextField.insets = new Insets(0, 0, 5, 5);
		gbc_courseSectionsTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_courseSectionsTextField.gridx = 2;
		gbc_courseSectionsTextField.gridy = 3;
		panel_1.add(courseSectionsTextField, gbc_courseSectionsTextField);
		courseSectionsTextField.setColumns(10);

		JLabel lblCoursePrereqs = new JLabel("Course Prereqs");
		GridBagConstraints gbc_lblCoursePrereqs = new GridBagConstraints();
		gbc_lblCoursePrereqs.anchor = GridBagConstraints.EAST;
		gbc_lblCoursePrereqs.insets = new Insets(0, 0, 0, 5);
		gbc_lblCoursePrereqs.gridx = 1;
		gbc_lblCoursePrereqs.gridy = 4;
		panel_1.add(lblCoursePrereqs, gbc_lblCoursePrereqs);

		coursePrereqsTextField = new JTextField();
		GridBagConstraints gbc_coursePrereqsTextField = new GridBagConstraints();
		gbc_coursePrereqsTextField.insets = new Insets(0, 0, 0, 5);
		gbc_coursePrereqsTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_coursePrereqsTextField.gridx = 2;
		gbc_coursePrereqsTextField.gridy = 4;
		panel_1.add(coursePrereqsTextField, gbc_coursePrereqsTextField);
		coursePrereqsTextField.setColumns(10);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Professor", null, panel_2, null);
	}

}
