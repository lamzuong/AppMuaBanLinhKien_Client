package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import dao.NhanVienDao;
import entity.NhanVien;
import entity.TaiKhoan;

public class FormThemNV extends JFrame implements ActionListener {
	private JTextField txtTenNV;
	private JComboBox<String> cmbGioiTinh;
	private JTextField txtCmnd;
	private JTextField txtSdt;
	private JTextField txtLuong;
	private JButton btnThem;
	private JDateChooser txtNgaySinh;
	private NhanVienDao nhanVienDao;

	public FormThemNV() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			nhanVienDao =  (NhanVienDao) Naming.lookup(FrameDangNhap.IP+"nhanVienDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ------------------------------
		setTitle("THÊM MỚI NHÂN VIÊN");
		setSize(400, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/chipicon.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenNV = new JLabel("HỌ TÊN: ");
		lblTenNV.setBounds(55, 36, 120, 20);
		lblTenNV.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblTenNV);
		txtTenNV = new JTextField("Nguyễn Văn An");
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTenNV.setBounds(180, 28, 150, 30);
		pnlContentPane.add(txtTenNV);

		JLabel lblGioitinh = new JLabel("GIỚI TÍNH: ");
		lblGioitinh.setBounds(55, 92, 120, 20);
		lblGioitinh.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblGioitinh);
		String[] gioitinh = { "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioitinh);
		cmbGioiTinh.setBounds(180, 85, 150, 30);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbGioiTinh.setFocusable(false);
		pnlContentPane.add(cmbGioiTinh);

		JLabel lblCmnd = new JLabel("CMND/CCCD: ");
		lblCmnd.setBounds(55, 147, 120, 20);
		lblCmnd.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblCmnd);
		txtCmnd = new JTextField("079201225241");
		txtCmnd.setBounds(180, 139, 150, 30);
		txtCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCmnd);

		JLabel lblNgaysinh = new JLabel("NGÀY SINH: ");
		lblNgaysinh.setBounds(55, 202, 120, 20);
		lblNgaysinh.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblNgaysinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setDate(new Date(1999-1900, 1-1, 1));
		txtNgaySinh.setBounds(180, 194, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtNgaySinh);
		
		JLabel lblSdt = new JLabel("SĐT :");
		lblSdt.setBounds(55, 258, 258, 14);
		lblSdt.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblSdt);
		txtSdt = new JTextField("0905214525");
		txtSdt.setBounds(180, 250, 150, 30);
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtSdt);

		JLabel lblLuong = new JLabel("LƯƠNG: ");
		lblLuong.setBounds(55, 314, 120, 14);
		lblLuong.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblLuong);
		txtLuong = new JTextField("5000000");
		txtLuong.setBounds(180, 306, 150, 30);
		txtLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtLuong);

		btnThem = new JButton("THÊM MỚI NHÂN VIÊN", new ImageIcon("image/them.png"));
		btnThem.setBounds(80, 370, 220, 45);
		btnThem.setForeground(Color.WHITE);
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnThem.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (!validInput()) {
				return;
			} else {
				String maNV;
				List<NhanVien> listNV = new ArrayList<NhanVien>();
				try {
					listNV = nhanVienDao.getTatCaNhanVien();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				if (listNV.size() == 0)
					maNV = "NV1001";
				else {
					String maNVCuoi = listNV.get(listNV.size() - 1).getMaNV().trim();
					int layMaSo = Integer.parseInt(maNVCuoi.substring(2, maNVCuoi.length()));
					maNV = "NV" + (layMaSo + 1);
				}
				String tenNV = txtTenNV.getText();
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
				String cmnd = txtCmnd.getText();
				String sdt = txtSdt.getText();
				double luong = Double.parseDouble(txtLuong.getText());
				Date ngaySinh = txtNgaySinh.getDate();
				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, cmnd, gioiTinh == "Nam" ? true : false, luong, false);
				
				int layMaSo = Integer.parseInt(maNV.substring(2, maNV.length()));
				Date date = new Date();
				String tenTK = ""+ (date.getYear()+1900) + layMaSo;
				nv.setTaiKhoan(new TaiKhoan(tenTK, "123", nv));
				try {
					nhanVienDao.themNhanVien(nv);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					FrameNhanVien.xoaHetDL();
					FrameNhanVien.docDuLieuVaoTable();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					AutoCompleteDecorator.decorate(FrameNhanVien.txtTenNV, FrameNhanVien.docDuLieuVaoTxtTen(), false);
					AutoCompleteDecorator.decorate(FrameNhanVien.txtMaNV, FrameNhanVien.docDuLieuVaoTxtMa(), false);
					AutoCompleteDecorator.decorate(FrameNhanVien.txtCmnd, FrameNhanVien.docDuLieuVaoTxtCmnd(), false);
					AutoCompleteDecorator.decorate(FrameNhanVien.txtSdt, FrameNhanVien.docDuLieuVaoTxtSdt(), false);
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				FrameNhanVien.table.getSelectionModel().clearSelection();
				dispose();
			}
		}
	}

	private boolean validInput() {
		String tenNV = txtTenNV.getText();
		Date ngaySinh = txtNgaySinh.getDate();
		String cmnd = txtCmnd.getText();
		String sdt = txtSdt.getText();
		String luong = txtLuong.getText();
		if (tenNV.trim().length() > 0) {
			if (!(tenNV.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên nhân viên không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống");
			return false;
		}
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayHienTai.getYear() - ngaySinh.getYear() < 18) {
				JOptionPane.showMessageDialog(this, "Nhân viên chưa đủ 18 tuổi");
				return false;
			}
		}
		if (cmnd.trim().length() > 0) {
			if (!(cmnd.matches("[0-9]{9}")) && !(cmnd.matches("[0-9]{12}"))) {
				JOptionPane.showMessageDialog(this, "CMND phải gồm 9 hoặc 12 số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "CMND không được để trống");
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
		if (luong.trim().length() > 0) {
			try {
				double x = Double.parseDouble(luong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Lương phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Lương không được để trống");
			return false;
		}
		return true;
	}

}
