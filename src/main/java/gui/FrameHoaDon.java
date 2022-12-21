package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import dao.HoaDonDao;
import dao.KhachHangDao;
import dao.ThongKeDoanhThuDao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.LinhKien;
import entity.NhanVien;

public class FrameHoaDon extends JFrame implements ActionListener {
	private JTextField txtTen;
	private JTextField txtSdt;
	private JComboBox<String> cmbGioiTinh;
	private JButton btnLamMoiKH;
	public static DefaultTableModel tableModelDSLK;
	public static JTable tableDSLK;
	private JTextField txtSoLuongMua;
	private JTextField txtSoLuongHuy;
	private JButton btnThem;
	private DefaultTableModel tableModelCTHD;
	private JTable tableCTHD;
	private JButton btnHuy;
	private JTextField txtTenNV;
	private JTextField txtThanhTien;
	private JTextField txtThue;
	private JTextField txtTongTien;
	private JButton btnInHD;
	public static JTextField txtTenLK;
	private JButton btnTimLK;
	private JDateChooser txtNgaySinh;
	public static JComboBox<String> cmbSDTKHCu;
	private JButton btnTimKH;
	private JButton btnThanhToan;
	private JButton btnXoaTatCa;
	private JButton btnTaiLai;
	public static HoaDonDao hoaDonDao;
	public static KhachHangDao khachHangDao;
	public static ThongKeDoanhThuDao thongKeDoanhThuDao;
	private String maHDDangMuaHang = "";

	public JPanel createPanelHoaDon() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			hoaDonDao = (HoaDonDao) Naming.lookup(FrameDangNhap.IP+"hoaDonDao");
			khachHangDao = (KhachHangDao) Naming.lookup(FrameDangNhap.IP+"khachHangDao");
			thongKeDoanhThuDao = (ThongKeDoanhThuDao) Naming.lookup(FrameDangNhap.IP+"thongKeDoanhThuDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		//---------------------------
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);

		/*
		 * Bảng danh sách linh kiện
		 */
		JPanel pnLinhKien = new JPanel();
		pnLinhKien.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Danh sách linh kiện: "));
		pnLinhKien.setBounds(10, 10, 455, 630);
		pnLinhKien.setBackground(Color.WHITE);
		pnLinhKien.setLayout(new BorderLayout());
		pnlContentPane.add(pnLinhKien);

		String[] headerLK = { "Mã linh kiện", "Tên linh kiện", "Đơn giá", "Số lượng", "Nhà sản xuất", "Bảo hành" };
		tableModelDSLK = new DefaultTableModel(headerLK, 0);
		tableDSLK = new JTable(tableModelDSLK) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};
		tableDSLK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableDSLK.setRowHeight(tableDSLK.getRowHeight() + 20);
		tableDSLK.setSelectionBackground(new Color(255, 255, 128));
		tableDSLK.setGridColor(getBackground());
		JTableHeader tableHeader = tableDSLK.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setReorderingAllowed(false);

		tableDSLK.setDefaultEditor(Object.class, null);
		ListSelectionModel rowSelectionModel = tableDSLK.getSelectionModel();
		rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pnLinhKien.add(new JScrollPane(tableDSLK), BorderLayout.CENTER);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		tableDSLK.getColumn("Đơn giá").setCellRenderer(rightRenderer);
		tableDSLK.getColumn("Số lượng").setCellRenderer(rightRenderer);

		tableDSLK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableDSLK.getColumnModel().getColumn(0).setPreferredWidth(120);
		tableDSLK.getColumnModel().getColumn(1).setPreferredWidth(255);
		tableDSLK.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableDSLK.getColumnModel().getColumn(4).setPreferredWidth(100);

		// South
		JPanel pnlSouth = new JPanel();
		pnLinhKien.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setPreferredSize(new Dimension(WIDTH, 130));
		pnlSouth.setLayout(null);
		pnlSouth.setBackground(Color.WHITE);

		JLabel lblTenLK = new JLabel("Nhập tên linh kiện cần tìm:");
		lblTenLK.setBounds(30, 10, 200, 20);
		lblTenLK.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlSouth.add(lblTenLK);
		txtTenLK = new JTextField();
		txtTenLK.setBounds(30, 35, 195, 32);
		txtTenLK.setBackground(Color.WHITE);
		txtTenLK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlSouth.add(txtTenLK);

		btnTimLK = new JButton("", new ImageIcon("image/timkiem.png"));
		btnTimLK.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTimLK.setBounds(225, 35, 32, 32);
		btnTimLK.setBackground(new Color(0, 148, 224));
		btnTimLK.setFocusPainted(false);
		pnlSouth.add(btnTimLK);

		btnTaiLai = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnTaiLai.setBounds(280, 30, 130, 40);
		btnTaiLai.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTaiLai.setBackground(new Color(0, 148, 224));
		btnTaiLai.setForeground(Color.WHITE);
		btnTaiLai.setFocusPainted(false);
		pnlSouth.add(btnTaiLai);

		JLabel lblSoLuong = new JLabel("Nhập số lượng cần mua:");
		lblSoLuong.setBounds(30, 90, 300, 20);
		lblSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlSouth.add(lblSoLuong);
		txtSoLuongMua = new JTextField();
		txtSoLuongMua.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoLuongMua.setBounds(210, 85, 50, 30);
		pnlSouth.add(txtSoLuongMua);

		btnThem = new JButton("THÊM", new ImageIcon("image/them.png"));
		btnThem.setBounds(280, 80, 130, 40);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThem.setBackground(new Color(0, 148, 224));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlSouth.add(btnThem);

		/*
		 * Bảng chi tiết hóa đơn
		 */
		JPanel pnlCTHD = new JPanel();
		pnlCTHD.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Chi tiết hóa đơn: "));
		pnlCTHD.setBounds(480, 10, 545, 630);
		pnlCTHD.setBackground(Color.WHITE);
		pnlCTHD.setLayout(new BorderLayout());
		pnlContentPane.add(pnlCTHD);

		String[] headerHD = { "Mã linh kiện", "Tên linh kiện", "Nhà sản xuất", "Bảo hành", "Đơn giá", "Số lượng",
				"Thành tiền" };
		tableModelCTHD = new DefaultTableModel(headerHD, 0);
		tableCTHD = new JTable(tableModelCTHD) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color1 = new Color(220, 220, 220);
				Color color2 = Color.WHITE;
				if (!c.getBackground().equals(getSelectionBackground())) {
					Color coleur = (row % 2 == 0 ? color1 : color2);
					c.setBackground(coleur);
					coleur = null;
				}
				return c;
			}
		};

		tableCTHD.getColumn("Đơn giá").setCellRenderer(rightRenderer);
		tableCTHD.getColumn("Số lượng").setCellRenderer(rightRenderer);
		tableCTHD.getColumn("Thành tiền").setCellRenderer(rightRenderer);

		tableCTHD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableCTHD.setRowHeight(tableCTHD.getRowHeight() + 20);
		tableCTHD.setSelectionBackground(new Color(255, 255, 128));
		tableCTHD.setGridColor(getBackground());

		tableCTHD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableCTHD.getColumnModel().getColumn(0).setPreferredWidth(120);
		tableCTHD.getColumnModel().getColumn(1).setPreferredWidth(255);
		tableCTHD.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableCTHD.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableCTHD.getColumnModel().getColumn(6).setPreferredWidth(100);

		JTableHeader tableHeader2 = tableCTHD.getTableHeader();
		tableHeader2.setBackground(new Color(219, 255, 255));
		tableHeader2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader2.setPreferredSize(new Dimension(WIDTH, 30));

		tableHeader2.setReorderingAllowed(false);
		tableCTHD.setDefaultEditor(Object.class, null);
		ListSelectionModel rowSelectionModel2 = tableCTHD.getSelectionModel();
		rowSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		pnlCTHD.add(new JScrollPane(tableCTHD), BorderLayout.CENTER);

		// North
		JPanel pnlNorthHoaDon = new JPanel();
		pnlCTHD.add(pnlNorthHoaDon, BorderLayout.NORTH);
		pnlNorthHoaDon.setPreferredSize(new Dimension(WIDTH, 60));
		pnlNorthHoaDon.setLayout(null);
		pnlNorthHoaDon.setBackground(Color.WHITE);

		JLabel lblSoLuongHuy = new JLabel("Nhập số lượng cần hủy:");
		lblSoLuongHuy.setBounds(10, 15, 300, 20);
		lblSoLuongHuy.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlNorthHoaDon.add(lblSoLuongHuy);
		txtSoLuongHuy = new JTextField();
		txtSoLuongHuy.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoLuongHuy.setBounds(180, 10, 50, 30);
		pnlNorthHoaDon.add(txtSoLuongHuy);

		btnHuy = new JButton("HỦY", new ImageIcon("image/huydatphong.png"));
		btnHuy.setBounds(240, 5, 110, 38);
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuy.setBackground(new Color(0, 148, 224));
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFocusPainted(false);
		pnlNorthHoaDon.add(btnHuy);

		btnXoaTatCa = new JButton("XÓA TẤT CẢ", new ImageIcon("image/xoa.png"));
		btnXoaTatCa.setBounds(360, 5, 160, 38);
		btnXoaTatCa.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnXoaTatCa.setBackground(new Color(0, 148, 224));
		btnXoaTatCa.setForeground(Color.WHITE);
		btnXoaTatCa.setFocusPainted(false);
		pnlNorthHoaDon.add(btnXoaTatCa);

		// South
		JPanel pnlSouthHoaDon = new JPanel();
		pnlCTHD.add(pnlSouthHoaDon, BorderLayout.SOUTH);
		pnlSouthHoaDon.setPreferredSize(new Dimension(WIDTH, 340));
		pnlSouthHoaDon.setLayout(null);
		pnlSouthHoaDon.setBackground(Color.WHITE);
		/*
		 * Khách hàng cũ
		 */
		JPanel pnlKhachHangCu = new JPanel();
		pnlKhachHangCu.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Khách hàng cũ: "));
		pnlKhachHangCu.setBounds(15, 8, 285, 63);
		pnlKhachHangCu.setBackground(Color.WHITE);
		pnlKhachHangCu.setLayout(null);
		pnlSouthHoaDon.add(pnlKhachHangCu);

		JLabel lblSdtKhCu = new JLabel("SĐT: ");
		lblSdtKhCu.setBounds(30, 15, 300, 43);
		lblSdtKhCu.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlKhachHangCu.add(lblSdtKhCu);
		cmbSDTKHCu = new JComboBox<String>();
		cmbSDTKHCu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbSDTKHCu.setBounds(100, 20, 120, 30);
		cmbSDTKHCu.setBackground(Color.WHITE);
		pnlKhachHangCu.add(cmbSDTKHCu);

		btnTimKH = new JButton("", new ImageIcon("image/timkiem.png"));
		btnTimKH.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTimKH.setBounds(220, 20, 30, 30);
		btnTimKH.setBackground(new Color(0, 148, 224));
		btnTimKH.setFocusPainted(false);
		pnlKhachHangCu.add(btnTimKH);
		/*
		 * Bảng thông tin khách hàng
		 */
		JPanel pnlKhachHang = new JPanel();
		pnlKhachHang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Thông tin khách hàng: "));
		pnlKhachHang.setBounds(15, 80, 285, 250);
		pnlKhachHang.setBackground(Color.WHITE);
		pnlKhachHang.setLayout(null);
		pnlSouthHoaDon.add(pnlKhachHang);

		JLabel lblTen = new JLabel("Họ tên: ");
		lblTen.setBounds(30, 20, 300, 43);
		lblTen.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlKhachHang.add(lblTen);
		txtTen = new JTextField();
		txtTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtTen.setBounds(100, 25, 160, 30);
		pnlKhachHang.add(txtTen);

		JLabel lblSdt = new JLabel("SĐT: ");
		lblSdt.setBounds(30, 60, 300, 43);
		lblSdt.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlKhachHang.add(lblSdt);
		txtSdt = new JTextField();
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSdt.setBounds(100, 65, 160, 30);
		pnlKhachHang.add(txtSdt);

		JLabel lblNS = new JLabel("Ngày sinh: ");
		lblNS.setBounds(30, 100, 300, 43);
		lblNS.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlKhachHang.add(lblNS);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(120, 105, 140, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlKhachHang.add(txtNgaySinh);

		JLabel lblGioiTinh = new JLabel("Giới tính: ");
		lblGioiTinh.setBounds(30, 140, 300, 43);
		lblGioiTinh.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlKhachHang.add(lblGioiTinh);
		cmbGioiTinh = new JComboBox<String>();
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cmbGioiTinh.setBounds(100, 145, 160, 30);
		cmbGioiTinh.setFocusable(false);
		pnlKhachHang.add(cmbGioiTinh);

		btnLamMoiKH = new JButton("LÀM MỚI Ô NHẬP", new ImageIcon("image/lammoi.png"));
		btnLamMoiKH.setBounds(35, 190, 220, 40);
		btnLamMoiKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoiKH.setBackground(new Color(0, 148, 224));
		btnLamMoiKH.setForeground(Color.WHITE);
		btnLamMoiKH.setFocusPainted(false);
		pnlKhachHang.add(btnLamMoiKH);
		/*
		 * Bảng hóa đơn
		 */
		JLabel lblTenNV = new JLabel("Nhân viên: ");
		lblTenNV.setBounds(340, 0, 300, 43);
		lblTenNV.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlSouthHoaDon.add(lblTenNV);
		txtTenNV = new JTextField();
		txtTenNV.setBounds(340, 32, 160, 30);
		txtTenNV.setEditable(false);
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlSouthHoaDon.add(txtTenNV);

		JLabel lblThanhTien = new JLabel("Thành tiền: ");
		lblThanhTien.setBounds(340, 55, 300, 43);
		lblThanhTien.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlSouthHoaDon.add(lblThanhTien);
		txtThanhTien = new JTextField();
		txtThanhTien.setBounds(340, 87, 160, 30);
		txtThanhTien.setEditable(false);
		txtThanhTien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlSouthHoaDon.add(txtThanhTien);

		JLabel lblThue = new JLabel("Thuế VAT: ");
		lblThue.setBounds(340, 110, 300, 43);
		lblThue.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlSouthHoaDon.add(lblThue);
		txtThue = new JTextField();
		txtThue.setBounds(340, 142, 160, 30);
		txtThue.setEditable(false);
		txtThue.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlSouthHoaDon.add(txtThue);

		JLabel lblTongTien = new JLabel("Tổng tiền: ");
		lblTongTien.setBounds(340, 165, 300, 43);
		lblTongTien.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlSouthHoaDon.add(lblTongTien);
		txtTongTien = new JTextField();
		txtTongTien.setBounds(340, 197, 160, 30);
		txtTongTien.setEditable(false);
		txtTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlSouthHoaDon.add(txtTongTien);

		btnInHD = new JButton("IN HÓA ĐƠN", new ImageIcon("image/inhoadon.png"));
		btnInHD.setBounds(340, 240, 160, 40);
		btnInHD.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInHD.setBackground(new Color(0, 148, 224));
		btnInHD.setForeground(Color.WHITE);
		btnInHD.setFocusPainted(false);
		pnlSouthHoaDon.add(btnInHD);

		btnThanhToan = new JButton("THANH TOÁN", new ImageIcon("image/thanhtoan.png"));
		btnThanhToan.setBounds(340, 290, 160, 40);
		btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThanhToan.setBackground(new Color(0, 148, 224));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFocusPainted(false);
		pnlSouthHoaDon.add(btnThanhToan);

		btnHuy.addActionListener(this);
		btnInHD.addActionListener(this);
		btnLamMoiKH.addActionListener(this);
		btnTaiLai.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnThem.addActionListener(this);
		btnTimKH.addActionListener(this);
		btnTimLK.addActionListener(this);
		btnXoaTatCa.addActionListener(this);

		// Đọc dữ liệu vào giao diện
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");

		List<LinhKien> listLK = null;
		try {
			listLK = hoaDonDao.getAllLinhKienTonTai();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		docDuLieuVaoTableDanhSachLK(listLK);
		docDuLieuVaoTxtTenLK();
		docDuLieuVaoCmbSDTKHCu();

		cmbSDTKHCu.setEditable(true);
		AutoCompleteDecorator.decorate(cmbSDTKHCu);
		cmbSDTKHCu.setSelectedIndex(-1);
		try {
			txtTenNV.setText(hoaDonDao.getTenNhanVienTheoTenTK(FrameDangNhap.getTaiKhoan()));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		txtThanhTien.setText("0.0");
		txtTongTien.setText("0.0");
		txtThue.setText("0.0");
		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnHuy)) {
			if (validInput(txtSoLuongHuy)) {
				int row = tableCTHD.getSelectedRow();
				if (row != -1) {
					int soLuongHuy = Integer.parseInt(txtSoLuongHuy.getText().trim());

					// Loại bỏ dấu "," định dạng số lượng đã đặt
					String soLuongHuyString = tableCTHD.getValueAt(row, 5).toString().trim();
					String[] temp = soLuongHuyString.split(",");
					soLuongHuyString = "";
					for (int i = 0; i < temp.length; i++) {
						soLuongHuyString += temp[i];
					}
					int soLuongDaDat = Integer.parseInt(soLuongHuyString);

					// Loại bỏ dấu "," định dạng giá bán
					String giaBanString = tableCTHD.getValueAt(row, 4).toString().trim();
					String[] temp2 = giaBanString.split(",");
					giaBanString = "";
					for (int i = 0; i < temp2.length; i++) {
						giaBanString += temp2[i];
					}
					double giaBan = Double.parseDouble(giaBanString);

					// Kiểm tra số lượng mua phải nhỏ hơn số lượng tồn
					if (soLuongHuy > soLuongDaDat) {
						JOptionPane.showMessageDialog(this, "Số lượng huỷ lớn hơn số lượng đặt");
						return;
					}

					// Cập nhật bên cơ sở dữ liệu
					DecimalFormat df = new DecimalFormat("#,##0.0");
					DecimalFormat df2 = new DecimalFormat("#,##0");
					int soLuongCu = 0;
					soLuongCu = Integer.parseInt(tableCTHD.getValueAt(row, 5).toString().trim());
					tableCTHD.setValueAt(df2.format(soLuongCu - soLuongHuy), row, 5);
					tableCTHD.setValueAt(df.format((soLuongCu - soLuongHuy) * giaBan), row, 6);

					HoaDon hd = new HoaDon(maHDDangMuaHang);

					LinhKien lk = new LinhKien(tableCTHD.getValueAt(row, 0).toString().trim());
					ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hd, lk, soLuongCu - soLuongHuy, giaBan);

					try {
						hoaDonDao.updateChiTietHoaDon(chiTietHoaDon);
						hoaDonDao.updateSoLuongTheoMaKhiXoa(soLuongHuy, tableCTHD.getValueAt(row, 0).toString().trim());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					// Nếu số lượng bằng 0 thì xoá chi tiết khỏi hoá đơn
					JTable tblTemp = tableCTHD;
					for (int i = 0; i < tblTemp.getRowCount(); i++) {
						if (tblTemp.getValueAt(i, 5).equals("0")) {
							LinhKien lk1 = new LinhKien(tableCTHD.getValueAt(i, 0).toString().trim());
							try {
								hoaDonDao.removeChiTietHoaDon(new ChiTietHoaDon(hd, lk1, 0, 0));
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
							tableModelCTHD.removeRow(i);
						}
					}
					// Xoá hoá đơn nếu không có chi tiết hoá đơn
					if (tableCTHD.getRowCount() == 0) {
						try {
							hoaDonDao.removeHoaDon(maHDDangMuaHang);
							maHDDangMuaHang = "";
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}

					// Cập nhật giao diện
					xoaHetDLTableDSLK();
					List<LinhKien> listLK = null;
					try {
						listLK = hoaDonDao.getAllLinhKienTonTai();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					docDuLieuVaoTableDanhSachLK(listLK);

					// Cập nhật text field thành tiền,tổng tiền
					double thanhTien = 0;
					for (int i = 0; i < tableCTHD.getRowCount(); i++) {
						String thanhTienString = tableCTHD.getValueAt(i, 6).toString().trim();
						String[] temp3 = thanhTienString.split(",");
						thanhTienString = "";
						for (int j = 0; j < temp3.length; j++) {
							thanhTienString += temp3[j];
						}
						thanhTien += Double.parseDouble(thanhTienString);
					}
					txtThanhTien.setText(df.format(thanhTien));
					txtThue.setText(df.format(thanhTien * 0.1));
					double tongTien = thanhTien + thanhTien * 0.1;
					txtTongTien.setText(df.format(tongTien));
					JOptionPane.showMessageDialog(this, "Huỷ thành công");
					try {
						FrameLinhKien.xoaHetDL();
						FrameLinhKien.docDuLieuVaoTable();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn linh kiện cần huỷ");
				}
			}
		}
		if (o.equals(btnInHD)) {
			if (validInput()) {
				String tenKH = txtTen.getText().trim();
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString().trim();
				String lienLac = txtSdt.getText().trim();
				Date ngaySinh = txtNgaySinh.getDate();
				boolean gioiTinhBoolean = cmbGioiTinh.getSelectedItem().toString().trim().equals("Nam") ? true : false;

				List<KhachHang> listKH = null;
				try {
					listKH = khachHangDao.getTatCaKhachHang();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				int flag = 0;
				String maKH = "";
				KhachHang khachHangTrungSDT = null;
				for (KhachHang khachHang : listKH) {
					if (khachHang.getSoDT().trim().equals(lienLac)) {
						flag = 1;
						maKH = khachHang.getMaKH().trim();
						khachHangTrungSDT = khachHang;
						break;
					}
				}
				if (flag != 0) {
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
					if (!khachHangTrungSDT.getTenKH().equals(tenKH) || khachHangTrungSDT.isGioiTinh() != gioiTinhBoolean
							|| !df.format(khachHangTrungSDT.getNgaySinh()).equals(df.format(ngaySinh))) {
						int dialogResult = JOptionPane.showConfirmDialog(null,
								"Đây là số điện thoại khách hàng cũ. Vui lòng xác nhận cập nhật thông tin mới?",
								"Xác nhận cập nhật thông tin", JOptionPane.YES_NO_OPTION);

						if (dialogResult != JOptionPane.YES_OPTION) {
							return;
						}
						KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh == "Nam" ? true : false, ngaySinh, lienLac);
						try {
							khachHangDao.capnhatKhachHang(kh);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
				if (flag == 0) {
					String maKHCuoi = "";
					try {
						maKHCuoi = hoaDonDao.getMaKhachHangCuoi();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					maKH = "";
					if (maKHCuoi.equals("")) {
						maKH = "KH1001";
					} else {
						int layMaSoKH = Integer.parseInt(maKHCuoi.substring(2, maKHCuoi.length()));
						maKH = "KH" + (layMaSoKH + 1);
					}
					KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh == "Nam" ? true : false, ngaySinh, lienLac);
					try {
						hoaDonDao.addKhachHang(kh);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					cmbSDTKHCu.removeAllItems();
					List<KhachHang> listKH1 = null;
					try {
						listKH1 = khachHangDao.getTatCaKhachHang();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					for (KhachHang kh1 : listKH1) {
						cmbSDTKHCu.addItem(kh1.getSoDT().trim());
					}
				}
				if (maHDDangMuaHang.equals("")) {
					JOptionPane.showMessageDialog(this, "Không có hoá đơn để in");
					return;
				}

				new FrameHoaDonTinhTien(txtTen.getText(), txtTenNV.getText().trim(), maHDDangMuaHang, new Date())
						.setVisible(true);
			}
		}
		if (o.equals(btnLamMoiKH)) {
			txtTen.setText("");
			txtSdt.setText("");
			txtNgaySinh.setDate(null);
			cmbGioiTinh.setSelectedIndex(0);
		}
		if (o.equals(btnTaiLai)) {
			txtTenLK.setText("");
			txtSoLuongMua.setText("");
			xoaHetDLTableDSLK();
			List<LinhKien> listLK = null;
			try {
				listLK = hoaDonDao.getAllLinhKienTonTai();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			docDuLieuVaoTableDanhSachLK(listLK);

		}
		if (o.equals(btnThanhToan)) {
			if (!maHDDangMuaHang.equals("")) {
				if (validInput()) {
					String tenKhachHang = txtTen.getText().trim();
					String gioiTinh = cmbGioiTinh.getSelectedItem().toString().trim();
					String lienLac = txtSdt.getText().trim();
					Date ngaySinh = txtNgaySinh.getDate();
					boolean gioiTinhBoolean = cmbGioiTinh.getSelectedItem().toString().trim().equals("Nam") ? true
							: false;

					List<KhachHang> listKH = null;
					try {
						listKH = khachHangDao.getTatCaKhachHang();
					} catch (RemoteException e2) {
						e2.printStackTrace();
					}
					int flag = 0;
					String maKH = "";
					KhachHang khachHangTrungSDT = null;
					for (KhachHang khachHang : listKH) {
						if (khachHang.getSoDT().trim().equals(lienLac)) {
							flag = 1;
							maKH = khachHang.getMaKH().trim();
							khachHangTrungSDT = khachHang;
							break;
						}
					}
					if (flag != 0) {
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
						if (!khachHangTrungSDT.getTenKH().equals(tenKhachHang)
								|| khachHangTrungSDT.isGioiTinh() != gioiTinhBoolean
								|| !df.format(khachHangTrungSDT.getNgaySinh()).equals(df.format(ngaySinh))) {
							int dialogResult = JOptionPane.showConfirmDialog(null,
									"Đây là số điện thoại khách hàng cũ. Vui lòng xác nhận cập nhật thông tin mới?",
									"Xác nhận cập nhật thông tin", JOptionPane.YES_NO_OPTION);

							if (dialogResult != JOptionPane.YES_OPTION) {
								return;
							}
							KhachHang kh = new KhachHang(maKH, tenKhachHang, gioiTinh == "Nam" ? true : false, ngaySinh,
									lienLac);
							try {
								khachHangDao.capnhatKhachHang(kh);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
					}
					if (flag == 0) {
						String maKHCuoi = "";
						try {
							maKHCuoi = hoaDonDao.getMaKhachHangCuoi();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						maKH = "";
						if (maKHCuoi.equals("")) {
							maKH = "KH1001";
						} else {
							int layMaSoKH = Integer.parseInt(maKHCuoi.substring(2, maKHCuoi.length()));
							maKH = "KH" + (layMaSoKH + 1);
						}
						KhachHang kh = new KhachHang(maKH, tenKhachHang, gioiTinh == "Nam" ? true : false, ngaySinh,
								lienLac);

						try {
							hoaDonDao.addKhachHang(kh);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}

						cmbSDTKHCu.removeAllItems();
						List<KhachHang> listKH1;
						try {
							listKH1 = khachHangDao.getTatCaKhachHang();
							for (KhachHang kh1 : listKH1) {
								cmbSDTKHCu.addItem(kh1.getSoDT().trim());
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}

					}

					// Loại bỏ dấu "," định dạng giá bán
					String tongTienString = txtTongTien.getText().trim();
					String[] temp2 = tongTienString.split(",");
					tongTienString = "";
					for (int i = 0; i < temp2.length; i++) {
						tongTienString += temp2[i];
					}
					double tongTien = Double.parseDouble(tongTienString);
					if (!maHDDangMuaHang.equals("")) {
						KhachHang kh = new KhachHang(maKH, tenKhachHang, gioiTinh == "Nam" ? true : false, ngaySinh,
								lienLac);
						String maNV;
						try {
							maNV = hoaDonDao.getMaNhanVienTheoTenTK(FrameDangNhap.getTaiKhoan());
							hoaDonDao.updateHoaDon(new HoaDon(maHDDangMuaHang, new NhanVien(maNV), kh, new Date(),
									tongTien - (tongTien * 0.9), tongTien));

						} catch (RemoteException e1) {
							e1.printStackTrace();
						}

						maHDDangMuaHang = "";
						JOptionPane.showMessageDialog(this, "Thanh toán thành công");

						// Cập nhât lại giao diện
						DefaultTableModel dm = (DefaultTableModel) tableCTHD.getModel();
						dm.setRowCount(0);
						txtThue.setText("");
						txtThanhTien.setText("");
						txtTongTien.setText("");

						try {
							FrameKhachHang.xoaHetDL();
							FrameKhachHang.docDuLieuVaoTable();
							AutoCompleteDecorator.decorate(FrameKhachHang.txtMaKH, FrameKhachHang.docDuLieuVaoTxtMa(),
									false);
							AutoCompleteDecorator.decorate(FrameKhachHang.txtSDT, FrameKhachHang.docDuLieuVaoTxtSdt(),
									false);
							AutoCompleteDecorator.decorate(FrameKhachHang.txtTenKH, FrameKhachHang.docDuLieuVaoTxtTen(),
									false);

							FrameLinhKien.xoaHetDL();
							FrameLinhKien.docDuLieuVaoTable();
							AutoCompleteDecorator.decorate(FrameLinhKien.txtMaLK, FrameLinhKien.docDuLieuVaoTxtMa(),
									false);
							AutoCompleteDecorator.decorate(FrameLinhKien.txtNSX, FrameLinhKien.docDuLieuVaoTxtNSX(),
									false);
							AutoCompleteDecorator.decorate(FrameLinhKien.txtTen, FrameLinhKien.docDuLieuVaoTxtTen(),
									false);

							List<HoaDon> listHD = null;
							try {
								listHD = thongKeDoanhThuDao.getAllHoaDon();
							} catch (RemoteException e2) {
								e2.printStackTrace();
							}
							FrameThongKeDoanhThu.xoaHetDL();
							FrameThongKeDoanhThu.docDuLieuVaoTable(listHD);
							FrameThongKeDoanhThu.docDuLieuVaoTxtMaHD();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					} else
						JOptionPane.showMessageDialog(this, "Không có hoá đơn để thanh toán");
				}
			} else
				JOptionPane.showMessageDialog(this, "Không có hoá đơn để thanh toán");
		}
		if (o.equals(btnThem)) {
			int row = tableDSLK.getSelectedRow();
			if (row != -1) {
				if (validInput(txtSoLuongMua)) {
					String maKH = "";
					if(validInput()){
						String tenKhachHang = txtTen.getText().trim();
						String gioiTinh = cmbGioiTinh.getSelectedItem().toString().trim();
						String lienLac = txtSdt.getText().trim();
						Date ngaySinh = txtNgaySinh.getDate();
						boolean gioiTinhBoolean = cmbGioiTinh.getSelectedItem().toString().trim().equals("Nam") ? true : false;

						List<KhachHang> listKH = null;
						try {
							listKH = khachHangDao.getTatCaKhachHang();
						} catch (RemoteException e2) {
							e2.printStackTrace();
						}
						int flag = 0;
						maKH = "";
						KhachHang khachHangTrungSDT = null;
						for (KhachHang khachHang : listKH) {
							if (khachHang.getSoDT().trim().equals(lienLac)) {
								flag = 1;
								maKH = khachHang.getMaKH().trim();
								khachHangTrungSDT = khachHang;
								break;
							}
						}
						if (flag != 0) {
							SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
							if (!khachHangTrungSDT.getTenKH().equals(tenKhachHang) || khachHangTrungSDT.isGioiTinh() != gioiTinhBoolean
									|| !df.format(khachHangTrungSDT.getNgaySinh()).equals(df.format(ngaySinh))) {
								int dialogResult = JOptionPane.showConfirmDialog(null,
										"Đây là số điện thoại khách hàng cũ. Vui lòng xác nhận cập nhật thông tin mới?",
										"Xác nhận cập nhật thông tin", JOptionPane.YES_NO_OPTION);

								if (dialogResult != JOptionPane.YES_OPTION) {
									return ;
								}
								KhachHang kh = new KhachHang(maKH, tenKhachHang, gioiTinh == "Nam" ? true : false, ngaySinh, lienLac);
								try {
									khachHangDao.capnhatKhachHang(kh);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
							}
						}
						if (flag == 0) {
							String maKHCuoi = "";
							try {
								maKHCuoi = hoaDonDao.getMaKhachHangCuoi();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
							maKH = "";
							if (maKHCuoi.equals("")) {
								maKH = "KH1001";
							} else {
								int layMaSoKH = Integer.parseInt(maKHCuoi.substring(2, maKHCuoi.length()));
								maKH = "KH" + (layMaSoKH + 1);
							}
							KhachHang kh = new KhachHang(maKH, tenKhachHang, gioiTinh == "Nam" ? true : false, ngaySinh, lienLac);

							try {
								hoaDonDao.addKhachHang(kh);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}

						}
					}
					else
						return;
					int soLuongMua = Integer.parseInt(txtSoLuongMua.getText().trim());

					// Loại bỏ dấu "," định dạng số lượng
					String soLuongTonString = tableDSLK.getValueAt(row, 3).toString().trim();
					String[] temp = soLuongTonString.split(",");
					soLuongTonString = "";
					for (int i = 0; i < temp.length; i++) {
						soLuongTonString += temp[i];
					}
					int soLuongTon = Integer.parseInt(soLuongTonString);

					// Loại bỏ dấu "," định dạng giá bán
					String giaBanString = tableDSLK.getValueAt(row, 2).toString().trim();
					String[] temp2 = giaBanString.split(",");
					giaBanString = "";
					for (int i = 0; i < temp2.length; i++) {
						giaBanString += temp2[i];
					}
					double giaBan = Double.parseDouble(giaBanString);

					// Kiểm tra số lượng mua phải nhỏ hơn số lượng tồn
					if (soLuongMua > soLuongTon) {
						JOptionPane.showMessageDialog(this, "Không đủ số lượng");
						return;
					}

					// Kiểm tra linh kiện đã có bên chi tiết hoá đơn không
					int flag = -1;
					for (int i = 0; i < tableCTHD.getRowCount(); i++) {
						if (tableCTHD.getValueAt(i, 0).toString().trim()
								.equals(tableDSLK.getValueAt(row, 0).toString().trim())) {
							flag = i;
						}
					}

					// Tạo hoá đơn nếu chưa có hoá đơn
					if (maHDDangMuaHang.equals("")) {
						String maHD = "";
						String maHDCuoi = "";
						try {
							maHDCuoi = hoaDonDao.getMaHoaDonCuoi();
						} catch (RemoteException e2) {
							e2.printStackTrace();
						}
						if (maHDCuoi.equals(""))
							maHD = "HD1001";
						else {
							int layMaSoHD = Integer.parseInt(maHDCuoi.substring(2, maHDCuoi.length()));
							maHD = "HD" + (layMaSoHD + 1);
						}

						try {
							KhachHang kh = new KhachHang();
							kh.setMaKH(maKH);
							hoaDonDao.addHoaDon(new HoaDon(maHD, null, kh, null, 10, 100));
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						maHDDangMuaHang = maHD;
					}

					DecimalFormat df = new DecimalFormat("#,##0.0");
					DecimalFormat df2 = new DecimalFormat("#,##0");
					int soLuongCu = 0;
					// Nếu chi tiết hoá đơn chưa có linh kiện thì tạo mới
					if (flag == -1) {
						tableModelCTHD.addRow(new Object[] { tableDSLK.getValueAt(row, 0).toString().trim(),
								tableDSLK.getValueAt(row, 1).toString().trim(),
								tableDSLK.getValueAt(row, 4).toString().trim(),
								tableDSLK.getValueAt(row, 5).toString().trim(), df.format(giaBan),
								df2.format(soLuongMua), df.format(soLuongMua * giaBan) });

						HoaDon hd = new HoaDon(maHDDangMuaHang);
						LinhKien lk = new LinhKien(tableDSLK.getValueAt(row, 0).toString().trim());
						ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hd, lk, soLuongMua, giaBan);

						try {
							hoaDonDao.addChiTietHoaDon(chiTietHoaDon);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}

					}
					// Nếu chi tiết hoá đơn đã tồn tại linh kiện thì cập nhật
					else {
						soLuongCu = Integer.parseInt(tableCTHD.getValueAt(flag, 5).toString().trim());
						tableCTHD.setValueAt(df2.format(soLuongCu + soLuongMua), flag, 5);
						tableCTHD.setValueAt(df.format((soLuongCu + soLuongMua) * giaBan), flag, 6);

						HoaDon hd = new HoaDon(maHDDangMuaHang);
						LinhKien lk = new LinhKien(tableDSLK.getValueAt(row, 0).toString().trim());
						ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hd, lk, soLuongCu + soLuongMua, giaBan);

						try {
							hoaDonDao.updateChiTietHoaDon(chiTietHoaDon);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
					// Cập nhật bên cơ sở dữ liệu
					try {
						hoaDonDao.updateSoLuongTheoMaKhiBan(soLuongMua, tableDSLK.getValueAt(row, 0).toString().trim());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

					// Cập nhật giao diện
					xoaHetDLTableDSLK();
					List<LinhKien> listLK = null;
					try {
						listLK = hoaDonDao.getAllLinhKienTonTai();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					docDuLieuVaoTableDanhSachLK(listLK);

					// Cập nhật text field thành tiền,tổng tiền
					double thanhTien = 0;

					for (int i = 0; i < tableCTHD.getRowCount(); i++) {
						String thanhTienString = tableCTHD.getValueAt(i, 6).toString().trim();
						String[] temp3 = thanhTienString.split(",");
						thanhTienString = "";
						for (int j = 0; j < temp3.length; j++) {
							thanhTienString += temp3[j];
						}

						thanhTien += Double.parseDouble(thanhTienString);
					}
					txtThanhTien.setText(df.format(thanhTien));
					txtThue.setText(df.format(thanhTien * 0.1));
					double tongTien = thanhTien + thanhTien * 0.1;
					txtTongTien.setText(df.format(tongTien));

					try {
						FrameLinhKien.xoaHetDL();
						FrameLinhKien.docDuLieuVaoTable();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				} 
			} else
				JOptionPane.showMessageDialog(this, "Vui lòng chọn linh kiện cần thêm");
		}
		if (o.equals(btnTimKH)) {
			if (cmbSDTKHCu.getSelectedIndex() != -1) {
				String lienLac = cmbSDTKHCu.getSelectedItem().toString().trim();
				List<KhachHang> list;
				try {
					list = khachHangDao.getTatCaKhachHang();
					KhachHang kh = null;
					for (KhachHang khachHang : list) {
						if (khachHang.getSoDT().trim().equals(lienLac.trim())) {
							kh = khachHang;
							break;
						}
					}
					if (kh != null) {
						JOptionPane.showMessageDialog(this, "Đây là khách hàng cũ");
						txtTen.setText(kh.getTenKH().trim());
						cmbGioiTinh.setSelectedIndex(kh.isGioiTinh() == true ? 0 : 1);
						txtSdt.setText(kh.getSoDT().trim());
						txtNgaySinh.setDate(kh.getNgaySinh());
					} else {
						JOptionPane.showMessageDialog(this, "Đây là khách hàng mới");
						btnLamMoiKH.doClick();
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Đây là khách hàng mới");
				btnLamMoiKH.doClick();
			}
		}
		if (o.equals(btnTimLK)) {
			if (txtTenLK.getText().trim().equals("")) {
				xoaHetDLTableDSLK();
				List<LinhKien> listLK = null;
				try {
					listLK = hoaDonDao.getAllLinhKienTonTai();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				docDuLieuVaoTableDanhSachLK(listLK);
			} else {
				List<LinhKien> listLK = null;
				try {
					listLK = hoaDonDao.getAllLinhKienTonTai();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				List<LinhKien> listLKTimThay = new ArrayList<LinhKien>();
				String tenLK = txtTenLK.getText().trim();
				for (LinhKien linhKien : listLK) {
					if (linhKien.getTenLK().trim().contains(tenLK)) {
						listLKTimThay.add(linhKien);
					}
				}
				xoaHetDLTableDSLK();
				docDuLieuVaoTableDanhSachLK(listLKTimThay);
			}
		}
		if (o.equals(btnXoaTatCa)) {
			if (!maHDDangMuaHang.equals("")) {
				HoaDon hd = new HoaDon(maHDDangMuaHang);
				JTable tblTemp = new JTable(tableModelCTHD);
				for (int i = 0; i < tblTemp.getRowCount(); i++) {
					String soLuongHuyString = tableCTHD.getValueAt(i, 5).toString().trim();
					String[] temp3 = soLuongHuyString.split(",");
					soLuongHuyString = "";
					for (int j = 0; j < temp3.length; j++) {
						soLuongHuyString += temp3[j];
					}
					int soLuongHuy = Integer.parseInt(soLuongHuyString);
					try {
						hoaDonDao.updateSoLuongTheoMaKhiXoa(soLuongHuy, tableCTHD.getValueAt(i, 0).toString().trim());
						LinhKien lk1 = new LinhKien(tableCTHD.getValueAt(i, 0).toString().trim());
						hoaDonDao.removeChiTietHoaDon(new ChiTietHoaDon(hd, lk1, 0, 0));

					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				try {
					hoaDonDao.removeHoaDon(maHDDangMuaHang);
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				maHDDangMuaHang = "";

				DefaultTableModel dm = (DefaultTableModel) tableCTHD.getModel();
				dm.setRowCount(0);

				txtThanhTien.setText("0.0");
				txtTongTien.setText("0.0");
				txtThue.setText("0.0");
				xoaHetDLTableDSLK();
				List<LinhKien> listLK = null;
				try {
					listLK = hoaDonDao.getAllLinhKienTonTai();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				docDuLieuVaoTableDanhSachLK(listLK);

				try {
					FrameLinhKien.xoaHetDL();
					FrameLinhKien.docDuLieuVaoTable();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(this, "Xoá tất cả thành công");
		}
	}
	public static void docDuLieuVaoCmbSDTKHCu() {
		List<KhachHang> listKH = null;
		try {
			listKH = khachHangDao.getTatCaKhachHang();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		cmbSDTKHCu.removeAllItems();
		for (KhachHang kh : listKH) {
			cmbSDTKHCu.addItem(kh.getSoDT().trim());
		}
	}

	public static void docDuLieuVaoTxtTenLK() {
		List<LinhKien> listLK = null;
		try {
			listLK = hoaDonDao.getAllLinhKienTonTai();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List<String> listTenLK = new ArrayList<String>();

		for (LinhKien linhKien : listLK) {
			listTenLK.add(linhKien.getTenLK().trim());
		}
		AutoCompleteDecorator.decorate(txtTenLK, listTenLK, false);
	}
	public static void docDuLieuVaoTableDanhSachLK(List<LinhKien> listLK) {
		DecimalFormat df = new DecimalFormat("#,##0.0");
		DecimalFormat df2 = new DecimalFormat("#,##0");
		for (LinhKien linhKien : listLK) {
			tableModelDSLK
					.addRow(new Object[] { linhKien.getMaLK(), linhKien.getTenLK(), df.format(linhKien.getGiaBan()),
							df2.format(linhKien.getSoLuong()), linhKien.getNhaSanXuat(), linhKien.getBaoHanh() });
		}
	}

	public static void xoaHetDLTableDSLK() {
		DefaultTableModel dm = (DefaultTableModel) tableDSLK.getModel();
		dm.setRowCount(0);
	}

	public boolean validInput(JTextField txtSoLuong) {
		String soLuong = txtSoLuong.getText().trim();
		if (soLuong.trim().length() > 0) {
			try {
				int x = Integer.parseInt(soLuong);
				if (x <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
					txtSoLuong.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error: Số lượng phải nhập số");
				txtSoLuong.requestFocus();
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!!");
			txtSoLuong.requestFocus();
			return false;
		}
		return true;
	}

	public boolean validInput() {
		String tenKhachHang = txtTen.getText().trim();
		String lienLac = txtSdt.getText().trim();
		Date ngaySinh = txtNgaySinh.getDate();
		if (tenKhachHang.length() > 0) {
			if (!(tenKhachHang.matches("[^\\@\\!\\$\\^\\&\\*\\(\\)]+"))) {
				JOptionPane.showMessageDialog(this, "Tên khách hàng không chứa ký tự đặc biệt");
				txtTen.requestFocus();
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống");
			txtTen.requestFocus();
			return false;
		}
		if (lienLac.length() > 0) {
			if (!(lienLac.matches("(\\d{10,11})"))) {
				JOptionPane.showMessageDialog(this, "Liên lạc gồm 10 hoặc 11 số ");
				txtSdt.requestFocus();
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Liên lạc không được để trống");
			txtSdt.requestFocus();
			return false;
		}
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
			txtNgaySinh.requestFocus();
			return false;
		} else {
			Date ngayHienTai = new Date();
			if (ngayHienTai.getYear() - ngaySinh.getYear() < 13) {
				JOptionPane.showMessageDialog(this, "Khách hàng chưa đủ 13 tuổi");
				txtNgaySinh.requestFocus();
				return false;
			}
		}
		return true;
	}
}
