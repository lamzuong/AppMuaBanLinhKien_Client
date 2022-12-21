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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import dao.LinhKienDao;
import entity.LinhKien;

public class FormThemLK extends JFrame implements ActionListener {
	private JTextField txtTenLK;
	private JTextField txtGiaNhap;
	private JTextField txtGiaBan;
	private JTextField txtSoLuong;
	private JTextField txtBaohanh;
	private JTextField txtNhaSX;
	private JButton btnThem;
	private JDateChooser txtNgayNhapKho;
	private LinhKienDao linhKienDao;

	public FormThemLK() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			linhKienDao =  (LinhKienDao) Naming.lookup(FrameDangNhap.IP+"linhKienDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ------------------
		setTitle("THÊM MỚI LINH KIỆN");
		setSize(400, 490);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/chipicon.png");
		setIconImage(icon.getImage());

		JComponent pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblTenLK = new JLabel("TÊN LINH KIỆN:");
		lblTenLK.setBounds(60, 36, 100, 20);
		lblTenLK.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblTenLK);
		txtTenLK = new JTextField("Bàn phím cơ");
		txtTenLK.setBounds(180, 28, 150, 30);
		txtTenLK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenLK);

		JLabel lblGiaNhap = new JLabel("GIÁ NHẬP:");
		lblGiaNhap.setBounds(60, 86, 100, 20);
		lblGiaNhap.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblGiaNhap);
		txtGiaNhap = new JTextField("250000");
		txtGiaNhap.setBounds(180, 78, 150, 30);
		txtGiaNhap.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtGiaNhap);

		JLabel lblGiaBan = new JLabel("GIÁ BÁN:");
		lblGiaBan.setBounds(60, 136, 100, 20);
		lblGiaBan.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblGiaBan);
		txtGiaBan = new JTextField("300000");
		txtGiaBan.setBounds(180, 128, 150, 30);
		txtGiaBan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtGiaBan);

		JLabel lblSoLuong = new JLabel("SỐ LƯỢNG:");
		lblSoLuong.setBounds(60, 186, 100, 20);
		lblSoLuong.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblSoLuong);
		txtSoLuong = new JTextField("5");
		txtSoLuong.setBounds(180, 178, 150, 30);
		txtSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtSoLuong);

		JLabel lblBaoHanh = new JLabel("BẢO HÀNH:");
		lblBaoHanh.setBounds(60, 236, 100, 20);
		lblBaoHanh.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblBaoHanh);
		txtBaohanh = new JTextField("2 năm");
		txtBaohanh.setBounds(180, 228, 150, 30);
		txtBaohanh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtBaohanh);

		JLabel lblNgayNhapKho = new JLabel("NGÀY NHẬP KHO:");
		lblNgayNhapKho.setBounds(60, 286, 120, 20);
		lblNgayNhapKho.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblNgayNhapKho);
		txtNgayNhapKho = new JDateChooser();
		txtNgayNhapKho.setDateFormatString("dd/MM/yyyy");
		txtNgayNhapKho.setDate(new Date());
		txtNgayNhapKho.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtNgayNhapKho.setBounds(180, 278, 150, 30);
		pnlContentPane.add(txtNgayNhapKho);

		JLabel lblNhaSX = new JLabel("NHÀ SẢN XUẤT:");
		lblNhaSX.setBounds(60, 336, 100, 20);
		lblNhaSX.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblNhaSX);
		txtNhaSX = new JTextField("Hyper");
		txtNhaSX.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtNhaSX.setBounds(180, 328, 150, 30);
		pnlContentPane.add(txtNhaSX);

		btnThem = new JButton("THÊM MỚI LINH KIỆN", new ImageIcon("image/them.png"));
		btnThem.setBounds(85, 378, 220, 45);
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
			try {
				if (!validInput()||trungTen()) {
					return;
				} else {
					String maLK;
					List<LinhKien> listLK = new ArrayList<LinhKien>();
					try {
						listLK = linhKienDao.getTatCaLinhKien();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					if (listLK.size() == 0)
						maLK = "LK1001";
					else {
						String maNVCuoi = listLK.get(listLK.size() - 1).getMaLK().trim();
						int layMaSo = Integer.parseInt(maNVCuoi.substring(2, maNVCuoi.length()));
						maLK = "LK" + (layMaSo + 1);
					}
					String tenLK = txtTenLK.getText();
					int soLuong = Integer.parseInt(txtSoLuong.getText());
					String baoHanh = txtBaohanh.getText();
					double giaBan = Double.parseDouble(txtGiaBan.getText());
					double giaNhap = Double.parseDouble(txtGiaNhap.getText());
					Date ngayNhapKho = txtNgayNhapKho.getDate();
					String nhaSX = txtNhaSX.getText();
					LinhKien lk = new LinhKien(maLK, tenLK, giaBan, giaNhap, soLuong, ngayNhapKho, baoHanh, nhaSX, false);
					
					try {
						linhKienDao.themLinhKien(lk);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					try {
						FrameLinhKien.xoaHetDL();
						FrameLinhKien.docDuLieuVaoTable();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					try {
						AutoCompleteDecorator.decorate(FrameLinhKien.txtTen, FrameLinhKien.docDuLieuVaoTxtTen(), false);
						AutoCompleteDecorator.decorate(FrameLinhKien.txtMaLK, FrameLinhKien.docDuLieuVaoTxtMa(), false);
						AutoCompleteDecorator.decorate(FrameLinhKien.txtNSX, FrameLinhKien.docDuLieuVaoTxtNSX(), false);
					} catch (RemoteException e2) {
						e2.printStackTrace();
					}
					FrameLinhKien.table.getSelectionModel().clearSelection();
					
					List<LinhKien> list = linhKienDao.getTatCaLinhKien();
					FrameHoaDon.xoaHetDLTableDSLK();
					FrameHoaDon.docDuLieuVaoTableDanhSachLK(list);
					FrameHoaDon.docDuLieuVaoTxtTenLK();
					
					
					dispose();
				}
			} catch (NumberFormatException | RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	private boolean trungTen() throws RemoteException {
		List<String> list = FrameLinhKien.docDuLieuVaoTxtTen();
		String tenLK = txtTenLK.getText();
		for (String ten : list) {
			if(ten.trim().equalsIgnoreCase(tenLK)) {
				JOptionPane.showMessageDialog(this, "Linh kiện này đã có trong danh sách");
				return true;
			}
		}
		return false;
	}

	private boolean validInput() {
		String tenLK = txtTenLK.getText();
		String soLuong = txtSoLuong.getText();
		String baoHanh = txtBaohanh.getText();
		String giaBan = txtGiaBan.getText();
		String giaNhap = txtGiaNhap.getText();
		Date ngayNhapKho = txtNgayNhapKho.getDate();
		String nhaSX = txtNhaSX.getText();
		if (tenLK.trim().length() > 0) {
			if (!(tenLK.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên linh kiện không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên linh kiện không được để trống");
			return false;
		}
		if (giaNhap.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaNhap);
				if (x < 0) {
					JOptionPane.showMessageDialog(this, "Giá nhập phải lớn hơn bằng 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá nhập phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá nhập không được để trống");
			return false;
		}
		if (giaBan.trim().length() > 0) {
			try {
				double x = Double.parseDouble(giaBan);
				if (x < 0) {
					JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn bằng 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Giá bán phải nhập số");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Giá bán không được để trống");
			return false;
		}
		if (soLuong.trim().length() > 0) {
			try {
				int x = Integer.parseInt(soLuong);
				if (x < 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn bằng 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Số lượng phải nhập số nguyên");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
			return false;
		}
		if (baoHanh.trim().length() > 0) {
			if (!(baoHanh.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Thời gian bảo hành không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Thời gian bảo hành không được để trống");
			return false;
		}
		if (ngayNhapKho == null) {
			JOptionPane.showMessageDialog(this, "Ngày nhập kho không được để trống");
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayNhapKho.after(ngayHienTai)) {
				JOptionPane.showMessageDialog(this, "Ngày nhập kho không được sau ngày hôm nay");
				return false;
			}
		}
		if (nhaSX.trim().length() > 0) {
			if (!(nhaSX.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Nhà sản xuất không chứa ký tự đặc biệt");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Nhà sản xuất không được để trống");
			return false;
		}
		return true;
	}

}
