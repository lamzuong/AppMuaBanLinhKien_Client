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

import dao.KhachHangDao;
import dao.NhanVienDao;
import entity.KhachHang;
import entity.NhanVien;

public class FrameKhachHang extends JFrame implements ActionListener, MouseListener {
	private JButton btnLamMoi;
	private JButton btnTimKiem;
	private JButton btnCapNhat;
	public static DefaultTableModel tableModel;
	public static JTable table;
	private JDateChooser txtNgaySinh;
	private JButton btnSinhNhat;
	private JButton btnXuatExcel;
	public static JTextField txtMaKH;
	public static JTextField txtTenKH;
	public static JTextField txtSDT;
	private JComboBox<String> cmbGioiTinh;
	private static KhachHangDao khachHangDao;
	private NhanVienDao nhanVienDao;

	public JPanel createPanelKhachHang() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			khachHangDao = (KhachHangDao) Naming.lookup(FrameDangNhap.IP+"khachHangDao");
			nhanVienDao = (NhanVienDao) Naming.lookup(FrameDangNhap.IP+"nhanVienDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ----------------
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);
		/*
		 * Chức năng
		 */
		btnSinhNhat = new JButton("SINH NHẬT HÔM NAY", new ImageIcon("image/sinhnhat.png"));
		btnSinhNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSinhNhat.setBackground(Color.PINK);
		btnSinhNhat.setForeground(Color.WHITE);
		btnSinhNhat.setBounds(425, 15, 250, 45);
		btnSinhNhat.setFocusPainted(false);
		pnlContentPane.add(btnSinhNhat);

		btnCapNhat = new JButton("CẬP NHẬT", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(680, 15, 165, 45);
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

		JLabel lblMaKH = new JLabel("MÃ KHÁCH HÀNG: ", SwingConstants.CENTER);
		lblMaKH.setBounds(1, 10, 200, 30);
		lblMaKH.setOpaque(true);
		lblMaKH.setBackground(new Color(0, 148, 224));
		lblMaKH.setForeground(Color.WHITE);
		lblMaKH.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaKH);
		txtMaKH = new JTextField();
		txtMaKH.setBounds(26, 50, 150, 30);
		txtMaKH.setBackground(Color.WHITE);
		txtMaKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtMaKH, docDuLieuVaoTxtMa(), false);
		pnlTimKiem.add(txtMaKH);

		JLabel lblTenKH = new JLabel("TÊN KHÁCH HÀNG: ", SwingConstants.CENTER);
		lblTenKH.setBounds(1, 90, 200, 30);
		lblTenKH.setOpaque(true);
		lblTenKH.setBackground(new Color(0, 148, 224));
		lblTenKH.setForeground(Color.WHITE);
		lblTenKH.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTenKH);
		txtTenKH = new JTextField();
		txtTenKH.setBounds(26, 130, 150, 30);
		txtTenKH.setBackground(Color.WHITE);
		txtTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtTenKH, docDuLieuVaoTxtTen(), false);
		pnlTimKiem.add(txtTenKH);

		JLabel lblSDT = new JLabel("SỐ ĐIỆN THOẠI: ", SwingConstants.CENTER);
		lblSDT.setBounds(1, 170, 200, 30);
		lblSDT.setOpaque(true);
		lblSDT.setBackground(new Color(0, 148, 224));
		lblSDT.setForeground(Color.WHITE);
		lblSDT.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSDT);
		txtSDT = new JTextField();
		txtSDT.setBounds(26, 210, 150, 30);
		txtSDT.setBackground(Color.WHITE);
		txtSDT.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtSDT, docDuLieuVaoTxtSdt(), false);
		pnlTimKiem.add(txtSDT);

		JLabel lblNgaySinh = new JLabel("NGÀY SINH: ", SwingConstants.CENTER);
		lblNgaySinh.setBounds(1, 250, 200, 30);
		lblNgaySinh.setOpaque(true);
		lblNgaySinh.setBackground(new Color(0, 148, 224));
		lblNgaySinh.setForeground(Color.WHITE);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNgaySinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("yyyy-MM-dd");
		txtNgaySinh.setBounds(26, 290, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgaySinh);

		JLabel lblCMND = new JLabel("GIỚI TÍNH: ", SwingConstants.CENTER);
		lblCMND.setBounds(1, 330, 200, 30);
		lblCMND.setOpaque(true);
		lblCMND.setBackground(new Color(0, 148, 224));
		lblCMND.setForeground(Color.WHITE);
		lblCMND.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblCMND);
		String[] gioiTinh = new String[] { "Tất cả", "Nam", "Nữ" };
		cmbGioiTinh = new JComboBox<String>(gioiTinh);
		cmbGioiTinh.setBounds(26, 370, 150, 30);
		cmbGioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbGioiTinh);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(17, 420, 170, 40);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(17, 475, 170, 40);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách linh kiện
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Danh sách khách hàng: "));
		pnlDanhSach.setBounds(250, 75, 770, 565);
		pnlDanhSach.setBackground(Color.WHITE);
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Giới tính", "Ngày sinh" };
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
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);
		pnlDanhSach.add(new JScrollPane(table));
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		btnCapNhat.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnSinhNhat.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		table.addMouseListener(this);

		docDuLieuVaoTable();
		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				try {
					new FormCapNhatKH().setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			FrameHoaDon.docDuLieuVaoCmbSDTKHCu();
		}
		if (o.equals(btnSinhNhat)) {
			List<KhachHang> listKH = null;
			try {
				listKH = khachHangDao.getKhachHangSinhNhatHomNay();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			new FrameKhachHangSinhNhat(listKH).setVisible(true);
		}
		if (o.equals(btnTimKiem)) {
			String maKH = txtMaKH.getText();
			String tenKH = txtTenKH.getText();
			String sdt = txtSDT.getText();
			Date ngaySinh = txtNgaySinh.getDate();
			int gioiTinh = cmbGioiTinh.getSelectedIndex();
			xoaHetDL();
			List<KhachHang> listKH = new ArrayList<KhachHang>();
			try {
				listKH = khachHangDao.getTatCaKhachHang();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			if (!maKH.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getMaKH().trim().contains(maKH)) {
						listKH.add(kh);
					}
				}
			}
			if (!tenKH.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getTenKH().trim().contains(tenKH)) {
						listKH.add(kh);
					}
				}
			}
			if (!sdt.trim().equals("")) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (kh.getSoDT().trim().contains(sdt)) {
						listKH.add(kh);
					}
				}
			}
			if (ngaySinh != null) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				for (KhachHang kh : listTemp) {
					if (df.format(kh.getNgaySinh()).equals(df.format(ngaySinh))) {
						listKH.add(kh);
					}
				}
			}
			if (gioiTinh != 0) {
				List<KhachHang> listTemp = new ArrayList<KhachHang>();
				for (KhachHang kh : listKH) {
					listTemp.add(kh);
				}
				listKH.clear();
				if (gioiTinh == 1) {
					for (KhachHang kh : listTemp) {
						if (kh.isGioiTinh()) {
							listKH.add(kh);
						}
					}
				} else {
					for (KhachHang kh : listTemp) {
						if (!kh.isGioiTinh()) {
							listKH.add(kh);
						}
					}
				}

			}
			if (listKH.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có khách hàng nào phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				lamMoiDL();
				return;
			}
			for (KhachHang kh : listKH) {
				tableModel.addRow(new Object[] { kh.getMaKH(), kh.getTenKH(), kh.getSoDT(),
						kh.isGioiTinh() ? "Nam" : "Nữ", df.format(kh.getNgaySinh()) });
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

	public static void docDuLieuVaoTable() throws RemoteException {
		List<KhachHang> list = khachHangDao.getTatCaKhachHang();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for (KhachHang kh : list) {
			tableModel.addRow(new Object[] { kh.getMaKH().trim(), kh.getTenKH().trim(), kh.getSoDT().trim(),
					kh.isGioiTinh() ? "Nam" : "Nữ", df.format(kh.getNgaySinh()) });
		}
	}

	private void lamMoiDL() {
		txtMaKH.setText("");
		txtNgaySinh.setDate(null);
		txtSDT.setText("");
		txtTenKH.setText("");
		cmbGioiTinh.setSelectedIndex(0);
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

	public static List<String> docDuLieuVaoTxtMa() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<KhachHang> listKH = khachHangDao.getTatCaKhachHang();
		for (KhachHang kh : listKH) {
			list.add(kh.getMaKH().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtSdt() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<KhachHang> listKH = khachHangDao.getTatCaKhachHang();
		for (KhachHang kh : listKH) {
			list.add(kh.getSoDT().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtTen() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<KhachHang> listKH = khachHangDao.getTatCaKhachHang();
		for (KhachHang kh : listKH) {
			list.add(kh.getTenKH().trim());
		}
		return list;
	}

	public boolean xuatExcel() {
		try {
			FileOutputStream fileOut = new FileOutputStream("excel/DanhSachKhachHang.xls");
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH SÁCH KHÁCH HÀNG");

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

			cell.setCellValue("DANH SÁCH KHÁCH HÀNG");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Giới tính", "Ngày sinh" };
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
				new FormCapNhatKH().setVisible(true);
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
