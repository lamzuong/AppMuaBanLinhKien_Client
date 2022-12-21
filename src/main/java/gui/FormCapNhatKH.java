package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import dao.KhachHangDao;
import entity.KhachHang;

public class FormCapNhatKH extends JFrame implements ActionListener {
	private JTextField txtTenKH;
	private JComboBox<String> cmbGioiTinh;
	private JTextField txtSdt;
	private JButton btnCapNhat;
	private JDateChooser txtNgaySinh;
	private KhachHangDao khachHangDao;

	public FormCapNhatKH() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			khachHangDao = (KhachHangDao) Naming.lookup(FrameDangNhap.IP+"khachHangDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ------------------------------
		setTitle("CẬP NHẬT KHÁCH HÀNG");
		setSize(400, 370);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/chipicon.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenKH = new JLabel("HỌ TÊN: ");
		lblTenKH.setBounds(55, 36, 120, 20);
		lblTenKH.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblTenKH);
		txtTenKH = new JTextField("Nguyễn Văn An");
		txtTenKH.setBounds(180, 28, 150, 30);
		txtTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenKH);

		JLabel lblNgaysinh = new JLabel("NGÀY SINH: ");
		lblNgaysinh.setBounds(55, 92, 120, 20);
		lblNgaysinh.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblNgaysinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(180, 84, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtNgaySinh);

		JLabel lblGioitinh = new JLabel("GIỚI TÍNH: ");
		lblGioitinh.setBounds(55, 148, 120, 20);
		lblGioitinh.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblGioitinh);
		String[] gioitinh = { "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioitinh);
		cmbGioiTinh.setBounds(180, 140, 150, 30);
		cmbGioiTinh.setFocusable(false);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(cmbGioiTinh);

		JLabel lblSdt = new JLabel("LIÊN LẠC:");
		lblSdt.setBounds(55, 204, 120, 20);
		lblSdt.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblSdt);
		txtSdt = new JTextField("0905214525");
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSdt.setBounds(180, 196, 150, 30);
		pnlContentPane.add(txtSdt);

		btnCapNhat = new JButton("CẬP NHẬT KHÁCH HÀNG", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(70, 254, 240, 45);
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnCapNhat.addActionListener(this);

		int row = FrameKhachHang.table.getSelectedRow();
		txtTenKH.setText(FrameKhachHang.tableModel.getValueAt(row, 1).toString().trim());
		txtSdt.setText(FrameKhachHang.tableModel.getValueAt(row, 2).toString().trim());
		cmbGioiTinh.setSelectedItem(FrameKhachHang.tableModel.getValueAt(row, 3).toString().trim());
		String ngaySinh = FrameKhachHang.tableModel.getValueAt(row, 4).toString().trim();
		String[] a = ngaySinh.split("/");
		txtNgaySinh
				.setDate(new Date(Integer.parseInt(a[2]) - 1900, Integer.parseInt(a[1]) - 1, Integer.parseInt(a[0])));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			if (!validInput()) {
				return;
			} else {
				String maKH = FrameKhachHang.tableModel.getValueAt(FrameKhachHang.table.getSelectedRow(), 0).toString();
				String tenKH = txtTenKH.getText();
				String sdt = txtSdt.getText();
				Date ngaySinh = txtNgaySinh.getDate();
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
				KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh == "Nam" ? true : false, ngaySinh, sdt);
				try {
					khachHangDao.capnhatKhachHang(kh);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					FrameKhachHang.xoaHetDL();
					FrameKhachHang.docDuLieuVaoTable();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		}
	}

	private boolean validInput() {
		String tenKH = txtTenKH.getText();
		String sdt = txtSdt.getText();
		Date ngaySinh = txtNgaySinh.getDate();
		if (tenKH.trim().length() > 0) {
			if (!(tenKH.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên nhân viên không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống");
			return false;
		}
		if (sdt.trim().length() > 0) {
			if (!(sdt.matches("[0-9]{10,11}"))) {
				JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 đến 11 số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
			return false;
		}
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayHienTai.getYear() - ngaySinh.getYear() < 13) {
				JOptionPane.showMessageDialog(this, "Khách hàng chưa đủ 13 tuổi");
				return false;
			}
		}
		return true;
	}

}
