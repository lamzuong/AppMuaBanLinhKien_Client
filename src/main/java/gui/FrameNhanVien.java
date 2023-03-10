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
import javax.swing.table.DefaultTableCellRenderer;
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

import dao.NhanVienDao;
import entity.NhanVien;

public class FrameNhanVien extends JFrame implements ActionListener, MouseListener {
	private JButton btnXoa;
	private JButton btnCapNhat;
	private JButton btnThem;
	private JButton btnLamMoi;
	private JButton btnTimKiem;
	public static JTable table;
	public static DefaultTableModel tableModel;
	private JDateChooser txtNgaySinh;
	private JComboBox<String> cmbTrangThai;
	private JButton btnXuatExcel;
	public static JTextField txtMaNV;
	public static JTextField txtTenNV;
	public static JTextField txtSdt;
	public static JTextField txtCmnd;
	private static NhanVienDao nhanVienDao;

	public JPanel createPanelNhanVien() throws RemoteException {
		// kh???i t???o k???t n???i ?????n Server
		try {
			nhanVienDao =  (NhanVienDao) Naming.lookup(FrameDangNhap.IP+"nhanVienDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// -----------------------------
		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(Color.WHITE);
		pnlContentPane.setLayout(null);

		/*
		 * Ch???c n??ng
		 */
		btnThem = new JButton("TH??M M???I", new ImageIcon("image/them.png"));
		btnThem.setBounds(340, 15, 165, 45);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnThem.setBackground(new Color(79, 173, 84));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		pnlContentPane.add(btnThem);

		btnXoa = new JButton("NGH??? VI???C", new ImageIcon("image/xoa.png"));
		btnXoa.setBounds(510, 15, 165, 45);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXoa.setBackground(new Color(79, 173, 84));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		pnlContentPane.add(btnXoa);

		btnCapNhat = new JButton("C???P NH???T", new ImageIcon("image/capnhat.png"));
		btnCapNhat.setBounds(680, 15, 165, 45);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCapNhat.setBackground(new Color(79, 173, 84));
		btnCapNhat.setForeground(Color.WHITE);
		btnCapNhat.setFocusPainted(false);
		pnlContentPane.add(btnCapNhat);

		btnXuatExcel = new JButton("XU???T EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds(850, 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);
		// Th??ng tin t??m ki???m
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 202, 625);
		pnlTimKiem.setBackground(new Color(255, 255, 255));
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblMaNV = new JLabel("M?? NH??N VI??N: ", SwingConstants.CENTER);
		lblMaNV.setOpaque(true);
		lblMaNV.setBackground(new Color(0, 148, 224));
		lblMaNV.setBounds(1, 10, 200, 30);
		lblMaNV.setForeground(Color.WHITE);
		lblMaNV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaNV);
		txtMaNV = new JTextField();
		txtMaNV.setBounds(26, 50, 150, 30);
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtMaNV, docDuLieuVaoTxtMa(), false);
		pnlTimKiem.add(txtMaNV);

		JLabel lblTenNV = new JLabel("T??N NH??N VI??N: ", SwingConstants.CENTER);
		lblTenNV.setOpaque(true);
		lblTenNV.setBackground(new Color(0, 148, 224));
		lblTenNV.setBounds(1, 90, 200, 30);
		lblTenNV.setForeground(Color.WHITE);
		lblTenNV.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTenNV);
		txtTenNV = new JTextField();
		txtTenNV.setBounds(26, 130, 150, 30);
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtTenNV, docDuLieuVaoTxtTen(), false);
		pnlTimKiem.add(txtTenNV);

		JLabel lblSDT = new JLabel("S??? ??I???N THO???I: ", SwingConstants.CENTER);
		lblSDT.setBounds(1, 170, 200, 30);
		lblSDT.setOpaque(true);
		lblSDT.setBackground(new Color(0, 148, 224));
		lblSDT.setForeground(Color.WHITE);
		lblSDT.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSDT);
		txtSdt = new JTextField();
		txtSdt.setBounds(26, 210, 150, 30);
		txtSdt.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtSdt, docDuLieuVaoTxtSdt(), false);
		pnlTimKiem.add(txtSdt);

		JLabel lblNgaySinh = new JLabel("NG??Y SINH: ", SwingConstants.CENTER);
		lblNgaySinh.setBounds(1, 250, 200, 30);
		lblNgaySinh.setOpaque(true);
		lblNgaySinh.setBackground(new Color(0, 148, 224));
		lblNgaySinh.setForeground(Color.WHITE);
		lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblNgaySinh);
		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("dd/MM/yyyy");
		txtNgaySinh.setBounds(26, 290, 150, 30);
		txtNgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgaySinh);

		JLabel lblCmnd = new JLabel("CMND/CCCD: ", SwingConstants.CENTER);
		lblCmnd.setOpaque(true);
		lblCmnd.setBackground(new Color(0, 148, 224));
		lblCmnd.setBounds(1, 330, 200, 30);
		lblCmnd.setForeground(Color.WHITE);
		lblCmnd.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblCmnd);
		txtCmnd = new JTextField();
		txtCmnd.setBounds(26, 370, 150, 30);
		txtCmnd.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		AutoCompleteDecorator.decorate(txtCmnd, docDuLieuVaoTxtCmnd(), false);
		pnlTimKiem.add(txtCmnd);

		JLabel lblTrangThai = new JLabel("TR???NG TH??I: ", SwingConstants.CENTER);
		lblTrangThai.setBounds(1, 410, 200, 30);
		lblTrangThai.setOpaque(true);
		lblTrangThai.setForeground(Color.WHITE);
		lblTrangThai.setBackground(new Color(0, 148, 224));
		lblTrangThai.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblTrangThai);
		String[] loai = { "T???t c???", "??ang l??m", "???? ngh??? vi???c" };
		cmbTrangThai = new JComboBox<String>(loai);
		cmbTrangThai.setBounds(26, 450, 150, 30);
		cmbTrangThai.setFocusable(false);
		cmbTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTrangThai);

		btnTimKiem = new JButton("T??M KI???M", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(17, 500, 170, 40);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("L??M M???I", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(17, 555, 170, 40);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh s??ch linh ki???n
		 */
		JPanel pnlDanhSachNV = new JPanel();
		pnlDanhSachNV.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Danh s??ch nh??n vi??n: "));
		pnlDanhSachNV.setBounds(250, 75, 770, 565);
		pnlDanhSachNV.setBackground(new Color(255, 255, 255));
		pnlDanhSachNV.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSachNV);

		String[] header = { "M?? nh??n vi??n", "T??n nh??n vi??n", "S??? ??i???n tho???i", "Gi???i t??nh", "CMND", "Ng??y sinh", "L????ng",
				"T??i kho???n", "Tr???ng th??i" };
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
		table.getColumnModel().getColumn(1).setPreferredWidth(165);
		table.getColumnModel().getColumn(2).setPreferredWidth(110);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(110);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("L????ng").setCellRenderer(rightRenderer);

		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);
		pnlDanhSachNV.add(new JScrollPane(table));
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		table.addMouseListener(this);

		docDuLieuVaoTable();

		return pnlContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			try {
				new FormThemNV().setVisible(true);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnCapNhat)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n c???n c???p nh???t!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (tableModel.getValueAt(r, 7).toString().trim().equals("QLKILBY")) {
				JOptionPane.showMessageDialog(this, "Qu???n l?? kh??ng th??? t??? c???p nh???t th??ng tin c???a ch??nh m??nh!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				if(tableModel.getValueAt(r, 8).toString().trim().equals("???? ngh??? vi???c")) {
					JOptionPane.showMessageDialog(this, "Nh??n vi??n ???? ngh??? vi???c kh??ng ???????c c???p nh???t!", "L???i",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					new FormCapNhatNV().setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r == -1) {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n cho ngh??? vi???c!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (tableModel.getValueAt(r, 7).toString().trim().equals("QLKILBY")) {
				JOptionPane.showMessageDialog(this, "Qu???n l?? kh??ng th??? t??? cho ch??nh m??nh ngh??? vi???c!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int result = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c s??? cho nh??n vi??n n??y ngh??? vi???c kh??ng?", "C???nh b??o",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == 0) {
				if(tableModel.getValueAt(r, 8).toString().trim().equals("???? ngh??? vi???c")) {
					JOptionPane.showMessageDialog(this, "Nh??n vi??n ???? ngh??? vi???c kh??ng th??? x??a!", "L???i",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					nhanVienDao.xoaNhanVien(tableModel.getValueAt(r, 0).toString());
					JOptionPane.showMessageDialog(this, "???? cho nh??n vi??n "+ tableModel.getValueAt(r, 1).toString() +" ngh??? vi???c!");
					xoaHetDL();
					docDuLieuVaoTable();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (o.equals(btnTimKiem)) {
			String maNV = txtMaNV.getText();
			String tenNV = txtTenNV.getText();
			String sdt = txtSdt.getText();
			Date ngaySinh = txtNgaySinh.getDate();
			String cmnd = txtCmnd.getText();
			int trangThai = cmbTrangThai.getSelectedIndex();
			xoaHetDL();
			List<NhanVien> listNV = new ArrayList<NhanVien>();
			try {
				listNV = nhanVienDao.getTatCaNhanVien();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
			if (!maNV.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getMaNV().trim().contains(maNV)) {
						listNV.add(nv);
					}
				}
			}
			if (!tenNV.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getTenNV().trim().contains(tenNV)) {
						listNV.add(nv);
					}
				}
			}
			if (!sdt.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getSoDT().trim().contains(sdt)) {
						listNV.add(nv);
					}
				}
			}
			if (ngaySinh != null) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (df.format(nv.getNgaySinh()).equals(df.format(ngaySinh))) {
						listNV.add(nv);
					}
				}
			}
			if (!cmnd.trim().equals("")) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				for (NhanVien nv : listTemp) {
					if (nv.getCmnd().trim().contains(cmnd)) {
						listNV.add(nv);
					}
				}
			}
			if (trangThai != 0) {
				List<NhanVien> listTemp = new ArrayList<NhanVien>();
				for (NhanVien nv : listNV) {
					listTemp.add(nv);
				}
				listNV.clear();
				if (trangThai == 1) {
					for (NhanVien nv : listTemp) {
						if (!nv.isTrangThai())
							listNV.add(nv);
					}
				} else {
					for (NhanVien nv : listTemp) {
						if (nv.isTrangThai())
							listNV.add(nv);
					}
				}
			}
			if(listNV.size() == 0) {
				JOptionPane.showMessageDialog(this, "Kh??ng c?? nh??n vi??n n??o ph?? h???p v???i ti??u ch??", "L???i",
						JOptionPane.ERROR_MESSAGE);
				lamMoiDL();
				return;
			}
			for (NhanVien nv : listNV) {
				tableModel.addRow(
						new Object[] { nv.getMaNV(), nv.getTenNV(), nv.getSoDT(), nv.isGioiTinh() ? "Nam" : "N???",
								nv.getCmnd(), df.format(nv.getNgaySinh()), dfMoney.format(nv.getLuong()),
								nv.getTaiKhoan() == null ? "???? x??a" : nv.getTaiKhoan().getTenTK(),
								nv.isTrangThai() ? "???? ngh??? vi???c" : "??ang l??m" });
			}
		}
		if (o.equals(btnLamMoi)) {
			lamMoiDL();
		}
		if (o.equals(btnXuatExcel)) {
			if (xuatExcel())
				JOptionPane.showMessageDialog(this, "Ghi file th??nh c??ng!!", "Th??nh c??ng",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "Ghi file th???t b???i!!", "L???i",
						JOptionPane.ERROR_MESSAGE);
		}
	}
	private void lamMoiDL() {
		txtCmnd.setText("");
		txtMaNV.setText("");
		txtNgaySinh.setDate(null);
		txtSdt.setText("");
		txtTenNV.setText("");
		cmbTrangThai.setSelectedIndex(0);
		xoaHetDL();
		try {
			docDuLieuVaoTable();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	public static void docDuLieuVaoTable() throws RemoteException {
		List<NhanVien> list = nhanVienDao.getTatCaNhanVien();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat dfMoney = new DecimalFormat("#,##0.0");
		for (NhanVien nv : list) {
			tableModel.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(), nv.getSoDT(), nv.isGioiTinh() ? "Nam" : "N???",
					nv.getCmnd(), df.format(nv.getNgaySinh()), dfMoney.format(nv.getLuong()),
					nv.getTaiKhoan() == null ? "???? x??a" : nv.getTaiKhoan().getTenTK(),
					nv.isTrangThai() ? "???? ngh??? vi???c" : "??ang l??m" });
		}
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public static List<String> docDuLieuVaoTxtMa() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<NhanVien> listNV = nhanVienDao.getTatCaNhanVien();
		for (NhanVien nv : listNV) {
			list.add(nv.getMaNV().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtTen() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<NhanVien> listNV = nhanVienDao.getTatCaNhanVien();
		for (NhanVien nv : listNV) {
			list.add(nv.getTenNV().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtCmnd() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<NhanVien> listNV = nhanVienDao.getTatCaNhanVien();
		for (NhanVien nv : listNV) {
			list.add(nv.getCmnd().trim());
		}
		return list;
	}

	public static List<String> docDuLieuVaoTxtSdt() throws RemoteException {
		List<String> list = new ArrayList<String>();
		List<NhanVien> listNV = nhanVienDao.getTatCaNhanVien();
		for (NhanVien nv : listNV) {
			list.add(nv.getSoDT().trim());
		}
		return list;
	}

	public boolean xuatExcel() {
		try {
			FileOutputStream fileOut = new FileOutputStream("excel/DanhSachNhanVien.xls");
			// T???o sheet Danh s??ch kh??ch h??ng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("DANH S??CH NH??N VI??N");

			HSSFRow row;
			HSSFCell cell;

			// D??ng 1 t??n
			cell = worksheet.createRow(1).createCell(1);

			HSSFFont newFont = cell.getSheet().getWorkbook().createFont();
			newFont.setBold(true);
			newFont.setFontHeightInPoints((short) 13);
			CellStyle styleTenDanhSach = worksheet.getWorkbook().createCellStyle();
			styleTenDanhSach.setAlignment(HorizontalAlignment.CENTER);
			styleTenDanhSach.setFont(newFont);

			cell.setCellValue("DANH S??CH NH??N VI??N");
			cell.setCellStyle(styleTenDanhSach);

			String[] header = { "STT", "M?? nh??n vi??n", "T??n nh??n vi??n", "S??? ??i???n tho???i", "Gi???i t??nh", "CMND",
					"Ng??y sinh", "L????ng", "T??i kho???n", "Tr???ng th??i" };
			worksheet.addMergedRegion(new CellRangeAddress(1, 1, 1, header.length));

			// D??ng 2 ng?????i l???p
			row = worksheet.createRow(2);

			cell = row.createCell(1);
			cell.setCellValue("Ng?????i l???p:");
			cell = row.createCell(2);

			NhanVien nv = nhanVienDao.getNhanVienTheoTenTK(FrameDangNhap.getTaiKhoan());
			cell.setCellValue(nv.getTenNV());
			worksheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

			// D??ng 3 ng??y l???p
			row = worksheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("Ng??y l???p:");
			cell = row.createCell(2);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			cell.setCellValue(df.format(new Date()));
			worksheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));

			// D??ng 4 t??n c??c c???t
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

			// Ghi d??? li???u v??o b???ng
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
		int r = table.getSelectedRow();
		if (e.getClickCount() == 2 && r != -1) {
			if (tableModel.getValueAt(r, 7).toString().trim().equals("QLKILBY")) {
				JOptionPane.showMessageDialog(this, "Qu???n l?? kh??ng th??? t??? c???p nh???t th??ng tin c???a ch??nh m??nh!", "L???i",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				new FormCapNhatNV().setVisible(true);
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
