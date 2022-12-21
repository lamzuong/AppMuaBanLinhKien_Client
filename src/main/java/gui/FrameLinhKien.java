package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import dao.LinhKienDao;
import dao.NhanVienDao;
import entity.LinhKien;
import entity.NhanVien;

public class FrameLinhKien extends JFrame implements ActionListener, MouseListener {
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JButton btnXoa;
	private JButton btnCapNhat;
	private JButton btnThem;
	private JButton btnLamMoi;
	private JButton btnTimKiem;
	private JComboBox<String> cmbTinhTrang;
	private JButton btnXuatExcel;
	public static JTextField txtMaLK;
	public static JTextField txtTen;
	public static JTextField txtNSX;
	private JDateChooser txtNgayMin;
	private JDateChooser txtNgayMax;
	private static LinhKienDao linhKienDao;
	private NhanVienDao nhanVienDao;

	public JPanel createPanelLinhKien() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			linhKienDao =  (LinhKienDao) Naming.lookup(FrameDangNhap.IP+"linhKienDao");
			nhanVienDao = (NhanVienDao) Naming.lookup(FrameDangNhap.IP+"nhanVienDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ---------------------------
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		/*
		 * Chức năng
		 */
		btnThem = new JButton("THÊM MỚI", new ImageIcon("image/them.png"));
		btnThem.setBounds(410, 15, 155, 45);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnXoa = new JButton("XÓA", new ImageIcon("image/xoa.png"));
		btnXoa.setBounds(570, 15, 115, 45);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXoa.setBackground(new Color(79, 173, 84));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		pnlContentPane.add(btnXoa);

		btnCapNhat = new JButton("CẬP NHẬT", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(690, 15, 155, 45);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds(850, 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);

		// Thông tin tìm kiếm
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 202, 625);
		pnlTimKiem.setBackground(new Color(255, 255, 255));
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblMaLK = new JLabel("MÃ LINH KIỆN: ", SwingConstants.CENTER);
		lblMaLK.setOpaque(true);
		lblMaLK.setBackground(new Color(0, 148, 224));
		lblMaLK.setBounds(1, 10, 200, 30);
		lblMaLK.setForeground(Color.WHITE);
		lblMaLK.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaLK);
		txtMaLK = new JTextField();
		txtMaLK.setBounds(26, 50, 150, 30);
		txtMaLK.setBackground(Color.WHITE);
		txtMaLK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaLK.setEditable(true);
		AutoCompleteDecorator.decorate(txtMaLK, docDuLieuVaoTxtMa(), false);
		pnlTimKiem.add(txtMaLK);

		JLabel lblTen = new JLabel("TÊN LINH KIỆN: ", SwingConstants.CENTER);
		lblTen.setOpaque(true);
		lblTen.setBackground(new Color(0, 148, 224));
		lblTen.setBounds(1, 90, 200, 30);
		lblTen.setForeground(Color.WHITE);
		lblTen.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTen);
		txtTen = new JTextField();
		txtTen.setBounds(26, 130, 150, 30);
		txtTen.setBackground(Color.WHITE);
		txtTen.setEditable(true);
		txtTen.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtTen, docDuLieuVaoTxtTen(), false);
		pnlTimKiem.add(txtTen);

		JLabel lblNSX = new JLabel("NHÀ SẢN XUẤT: ", SwingConstants.CENTER);
		lblNSX.setBounds(1, 170, 200, 30);
		lblNSX.setOpaque(true);
		lblNSX.setBackground(new Color(0, 148, 224));
		lblNSX.setForeground(Color.WHITE);
		lblNSX.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNSX);
		txtNSX = new JTextField();
		txtNSX.setBounds(26, 210, 150, 30);
		txtNSX.setBackground(Color.WHITE);
		txtNSX.setEditable(true);
		txtNSX.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtNSX, docDuLieuVaoTxtNSX(), false);
		pnlTimKiem.add(txtNSX);

		JLabel lblNgayNhapKho = new JLabel("NGÀY NHẬP KHO: ", SwingConstants.CENTER);
		lblNgayNhapKho.setBounds(1, 250, 200, 30);
		lblNgayNhapKho.setOpaque(true);
		lblNgayNhapKho.setBackground(new Color(0, 148, 224));
		lblNgayNhapKho.setForeground(Color.WHITE);
		lblNgayNhapKho.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNgayNhapKho);

		JLabel lblNgayMin = new JLabel("Từ: ");
		lblNgayMin.setBounds(20, 290, 100, 30);
		lblNgayMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMin);
		txtNgayMin = new JDateChooser();
		txtNgayMin.setDateFormatString("dd/MM/yyyy");
		txtNgayMin.setBounds(60, 290, 120, 30);
		txtNgayMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMin);

		JLabel lblNgayMax = new JLabel("Đến: ");
		lblNgayMax.setBounds(20, 330, 100, 30);
		lblNgayMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMax);
		txtNgayMax = new JDateChooser();
		txtNgayMax.setDateFormatString("dd/MM/yyyy");
		txtNgayMax.setBounds(60, 330, 120, 30);
		txtNgayMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMax);

		JLabel lblTinhTrang = new JLabel("TÌNH TRẠNG: ", SwingConstants.CENTER);
		lblTinhTrang.setBounds(1, 370, 200, 30);
		lblTinhTrang.setOpaque(true);
		lblTinhTrang.setBackground(new Color(0, 148, 224));
		lblTinhTrang.setForeground(Color.WHITE);
		lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTinhTrang);
		String[] loai = { "Tất cả", "Còn hàng", "Gần hết hàng", "Đã hết hàng" };
		cmbTinhTrang = new JComboBox<String>(loai);
		cmbTinhTrang.setBounds(26, 410, 150, 30);
		cmbTinhTrang.setFocusable(false);
		cmbTinhTrang.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTinhTrang);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(17, 460, 170, 40);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(17, 515, 170, 40);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);
		/*
		 * Danh sách linh kiện
		 */
		JPanel pnDanhSach = new JPanel();
		pnDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Danh sách linh kiện: "));
		pnDanhSach.setBounds(250, 75, 770, 565);
		pnDanhSach.setBackground(Color.WHITE);
		pnDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnDanhSach);

		String[] header = { "Mã linh kiện", "Tên linh kiện", "Giá nhập", "Giá bán", "Số lượng", "Bảo hành",
				"Ngày nhập kho", "Nhà sản xuất" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel) {
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
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setRowHeight(table.getRowHeight() + 20);
		table.setSelectionBackground(new Color(255, 255, 128));
		table.setGridColor(getBackground());

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(255);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(110);

		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		pnDanhSach.add(new JScrollPane(table));
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		table.addMouseListener(this);

		docDuLieuVaoTable();

		return pnlContentPane;
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			try {
				new FormThemLK().setVisible(true);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn linh kiện cần cập nhật!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				try {
					new FormCapNhatLK().setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}

		}
		if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn linh kiện cần xóa!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc sẽ xóa linh kiện này không?", "Cảnh báo",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
			
			if (result == 0) {
				try {
					linhKienDao.xoaLinhKien(tableModel.getValueAt(r, 0).toString());
					JOptionPane.showMessageDialog(this, "Xóa thành công!");
					tableModel.removeRow(r);
					xoaHetDL();
					docDuLieuVaoTable();

					List<LinhKien> list = linhKienDao.getTatCaLinhKien();
					FrameHoaDon.xoaHetDLTableDSLK();
					FrameHoaDon.docDuLieuVaoTableDanhSachLK(list);
					FrameHoaDon.docDuLieuVaoTxtTenLK();

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (o.equals(btnTimKiem)) {
			String maLK = txtMaLK.getText();
			String tenLK = txtTen.getText();
			String nsx = txtNSX.getText();
			Date ngayMin = txtNgayMin.getDate();
			Date ngayMax = txtNgayMax.getDate();
			int trangThai = cmbTinhTrang.getSelectedIndex();
			xoaHetDL();
			List<LinhKien> listLK = new ArrayList<LinhKien>();
			try {
				listLK = linhKienDao.getLinhKienTonTai();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
			if (!maLK.trim().equals("")) {
				List<LinhKien> listTemp = new ArrayList<LinhKien>();
				for (LinhKien lk : listLK) {
					listTemp.add(lk);
				}
				listLK.clear();
				for (LinhKien lk : listTemp) {
					if (lk.getMaLK().trim().contains(maLK)) {
						listLK.add(lk);
					}
				}
			}
			if (!tenLK.trim().equals("")) {
				List<LinhKien> listTemp = new ArrayList<LinhKien>();
				for (LinhKien lk : listLK) {
					listTemp.add(lk);
				}
				listLK.clear();
				for (LinhKien lk : listTemp) {
					if (lk.getTenLK().trim().contains(tenLK)) {
						listLK.add(lk);
					}
				}
			}
			if (!nsx.trim().equals("")) {
				List<LinhKien> listTemp = new ArrayList<LinhKien>();
				for (LinhKien lk : listLK) {
					listTemp.add(lk);
				}
				listLK.clear();
				for (LinhKien lk : listTemp) {
					if (lk.getNhaSanXuat().trim().contains(nsx)) {
						listLK.add(lk);
					}
				}
			}
			if (ngayMin != null) {
				List<LinhKien> listTemp = new ArrayList<LinhKien>();
				for (LinhKien lk : listLK) {
					listTemp.add(lk);
				}
				listLK.clear();
				for (LinhKien lk : listTemp) {
					if (lk.getNgayNhapKho().compareTo(ngayMin) >= 0) {
						listLK.add(lk);
					}
				}
			}
			if (ngayMax != null) {
				List<LinhKien> listTemp = new ArrayList<LinhKien>();
				for (LinhKien lk : listLK) {
					listTemp.add(lk);
				}
				listLK.clear();
				for (LinhKien lk : listTemp) {
					if (lk.getNgayNhapKho().compareTo(ngayMax) <= 0) {
						listLK.add(lk);
					}
				}
			}
			if (trangThai != 0) {
				List<LinhKien> listTemp = new ArrayList<LinhKien>();
				for (LinhKien lk : listLK) {
					listTemp.add(lk);
				}
				listLK.clear();
				if (trangThai == 1) {
					for (LinhKien lk : listTemp) {
						if (lk.getSoLuong() > 0)
							listLK.add(lk);
					}
				} else if (trangThai == 2) {
					for (LinhKien lk : listTemp) {
						if (lk.getSoLuong() < 10 && lk.getSoLuong() > 0)
							listLK.add(lk);
					}
				} else if (trangThai == 3) {
					for (LinhKien lk : listTemp) {
						if (lk.getSoLuong() == 0)
							listLK.add(lk);
					}
				}
			}
			if (listLK.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có linh kiện nào phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				lamMoiDL();
				return;
			}
			for (LinhKien lk : listLK) {
				tableModel.addRow(new Object[] { lk.getMaLK(), lk.getTenLK(), dfMoney.format(lk.getGiaNhap()),
						dfMoney.format(lk.getGiaBan()), lk.getSoLuong(), lk.getBaoHanh(),
						df.format(lk.getNgayNhapKho()), lk.getNhaSanXuat() });
			}
		}
		if (o.equals(btnLamMoi)) {
			lamMoiDL();
		}
		if (o.equals(btnXuatExcel)) {
			if (xuatExcel())
				JOptionPane.showMessageDialog(this, "Ghi file thành công!!", "Thành công",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "Ghi file thất bại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void lamMoiDL() {
		txtMaLK.setText("");
		txtNgayMax.setDate(null);
		txtNgayMin.setDate(null);
		txtNSX.setText("");
		txtTen.setText("");
		cmbTinhTrang.setSelectedIndex(0);
		xoaHetDL();
		try {
			docDuLieuVaoTable();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public static void docDuLieuVaoTable() throws RemoteException {
		List<LinhKien> list = linhKienDao.getLinhKienTonTai();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
		for (LinhKien lk : list) {
			tableModel.addRow(new Object[] { lk.getMaLK(), lk.getTenLK(), dfMoney.format(lk.getGiaNhap()),
					dfMoney.format(lk.getGiaBan()), lk.getSoLuong(), lk.getBaoHanh(), df.format(lk.getNgayNhapKho()),
					lk.getNhaSanXuat() });
		}
	}

	public static List<String> docDuLieuVaoTxtNSX() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<LinhKien> listLK = linhKienDao.getLinhKienTonTai();
		for (LinhKien lk : listLK) {
			list.add(lk.getNhaSanXuat().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtMa() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<LinhKien> listLK = linhKienDao.getLinhKienTonTai();
		for (LinhKien lk : listLK) {
			list.add(lk.getMaLK().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtTen() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<LinhKien> listLK = linhKienDao.getLinhKienTonTai();
		for (LinhKien lk : listLK) {
			list.add(lk.getTenLK().trim());
		}
		return list;
	}

	public boolean xuatExcel() {
		try {
			FileOutputStream fileOut = new FileOutputStream("excel/DanhSachLinhKien.xls");
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH LINH KIỆN");

			HSSFRow row;
			HSSFCell cell;

			// Dòng 1 tên
			cell = worksheet.createRow(1).createCell(1);

			HSSFFont newFont = cell.getSheet().getWorkbook().createFont();
			newFont.setBold(true);
			newFont.setFontHeightInPoints((short) 13);
			CellStyle styleTenDanhSach = worksheet.getWorkbook().createCellStyle();
			styleTenDanhSach.setAlignment(HorizontalAlignment.CENTER);
			styleTenDanhSach.setFont(newFont);

			cell.setCellValue("DANH SÁCH LINH KIỆN");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã linh kiện", "Tên linh kiện", "Giá nhập", "Giá bán", "Số lượng", "Bảo hành",
					"Ngày nhập kho", "Nhà sản xuất" };
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// Dòng 2 người lập
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Người lập:");
			cell = row.createCell(2);

			NhanVien nv = nhanVienDao.getNhanVienTheoTenTK(FrameDangNhap.getTaiKhoan());
			cell.setCellValue(nv.getTenNV());
			worksheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

			// Dòng 3 ngày lập
			row = worksheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("Ngày lập:");
			cell = row.createCell(2);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			cell.setCellValue(df.format(new Date()));
			worksheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));

			// Dòng 4 tên các cột
			row = worksheet.createRow(4);

			HSSFFont fontHeader = cell.getSheet().getWorkbook().createFont();
			fontHeader.setBold(true);

			CellStyle styleHeader = worksheet.getWorkbook().createCellStyle();
			styleHeader.setFont(fontHeader);
			styleHeader.setBorderBottom(BorderStyle.THIN);
			styleHeader.setBorderTop(BorderStyle.THIN);
			styleHeader.setBorderLeft(BorderStyle.THIN);
			styleHeader.setBorderRight(BorderStyle.THIN);
			styleHeader.setAlignment(HorizontalAlignment.CENTER);

			styleHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			for (int i = 0; i < header.length; i++) {
				cell = row.createCell(i + 1);
				cell.setCellValue(header[i]);
				cell.setCellStyle(styleHeader);
			}

			if (table.getRowCount() == 0) {
				return false;
			}

			HSSFFont fontRow = cell.getSheet().getWorkbook().createFont();
			fontRow.setBold(false);

			CellStyle styleRow = worksheet.getWorkbook().createCellStyle();
			styleRow.setFont(fontRow);
			styleRow.setBorderBottom(BorderStyle.THIN);
			styleRow.setBorderTop(BorderStyle.THIN);
			styleRow.setBorderLeft(BorderStyle.THIN);
			styleRow.setBorderRight(BorderStyle.THIN);

			// Ghi dữ liệu vào bảng
			int STT = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				row = worksheet.createRow(5 + i);
				for (int j = 0; j < header.length; j++) {
					cell = row.createCell(j + 1);
					if (STT == i) {
						cell.setCellValue(STT + 1);
						STT++;
					} else {
						if (table.getValueAt(i, j - 1) != null)
							cell.setCellValue(table.getValueAt(i, j - 1).toString().trim());
					}
					cell.setCellStyle(styleRow);
				}
			}

			for (int i = 1; i < header.length + 1; i++) {
				worksheet.autoSizeColumn(i);
			}

			workbook.write(fileOut);
			workbook.close();
			fileOut.flush();
			fileOut.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
			try {
				new FormCapNhatLK().setVisible(true);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
