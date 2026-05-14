package gui.common;

import bus.KhachHangBUS;
import com.formdev.flatlaf.FlatClientProperties;
import dto.KhachHangDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Date;

public class CustomerInfoFrame extends JFrame {
    private int currentUserId;
    private KhachHangBUS khachHangBUS = new KhachHangBUS();

    private JTextField txtFullName, txtPhone, txtEmail, txtCccd, txtBirthday, txtAddress, txtDriverLicense;
    private JButton btnSubmit;

    public CustomerInfoFrame(int userId) {
        this.currentUserId = userId;

        setTitle("Bổ Sung Thông Tin Khách Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650); // Tăng form nền to ra một chút
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new GridBagLayout());

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 20;");

        mainPanel.setPreferredSize(new Dimension(700, 480));

        JLabel lblTitle = new JLabel("THÔNG TIN CÁ NHÂN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(25, 118, 210));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 30, 25));
        formPanel.setBackground(Color.WHITE);

        txtFullName = createTextField("Họ và tên *");
        txtPhone = createTextField("Số điện thoại *");
        txtEmail = createTextField("Email");
        txtCccd = createTextField("Số CCCD *");
        txtBirthday = createTextField("Ngày sinh (YYYY-MM-DD)");
        txtAddress = createTextField("Địa chỉ");
        txtDriverLicense = createTextField("Số GPLX (Nếu có)");

        formPanel.add(txtFullName);
        formPanel.add(txtPhone);
        formPanel.add(txtEmail);
        formPanel.add(txtCccd);
        formPanel.add(txtBirthday);
        formPanel.add(txtAddress);
        formPanel.add(txtDriverLicense);
        formPanel.add(new JLabel()); // Cân bằng lưới

        btnSubmit = new JButton("HOÀN TẤT ĐĂNG KÝ");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(new Color(46, 204, 113));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        btnSubmit.setMaximumSize(new Dimension(280, 45));

        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(46, 204, 113));
            }
        });

        btnSubmit.addActionListener(e -> handleSubmit());

        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Cách tiêu đề xa ra
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Cách nút bấm xa ra
        mainPanel.add(btnSubmit);

        add(mainPanel, new GridBagConstraints());
    }

    private JTextField createTextField(String placeholder) {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txt.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);

        // 5. THÊM PADDING BÊN TRONG Ô TEXTFIELD VÀ BO GÓC NHẸ
        txt.putClientProperty(FlatClientProperties.STYLE, "margin: 5,10,5,10; arc: 8;");
        txt.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        return txt;
    }

    private void handleSubmit() {
        try {
            KhachHangDTO kh = new KhachHangDTO();
            kh.setUserId(this.currentUserId);
            kh.setFullName(txtFullName.getText().trim());
            kh.setPhone(txtPhone.getText().trim());
            kh.setEmail(txtEmail.getText().trim());
            kh.setCccd(txtCccd.getText().trim());
            kh.setAddress(txtAddress.getText().trim());
            kh.setDriverLicenseNumber(txtDriverLicense.getText().trim());

            String dob = txtBirthday.getText().trim();
            if(!dob.isEmpty()){
                kh.setBirthday(Date.valueOf(dob));
            }

            String msg = khachHangBUS.themKhachHang(kh);
            if (msg.equals("Thêm thông tin thành công!")) {
                JOptionPane.showMessageDialog(this, "Hoàn tất hồ sơ! Vui lòng đăng nhập để thuê xe.");
                this.dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải nhập đúng định dạng năm-tháng-ngày (VD: 2002-12-30)", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
        }
    }
}