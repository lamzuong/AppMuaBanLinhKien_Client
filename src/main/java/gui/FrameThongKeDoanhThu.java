package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

import dao.HoaDonDao;
import dao.NhanVienDao;
import dao.ThongKeDoanhThuDao;
import entity.HoaDon;
import entity.NhanVien;

public class FrameThongKeDoanhThu extends JFrame implements ActionListener {

	private JComboBox<String> cmbTieuChi;
	private JButton btnTimKiem;
	private JButton btnLamMoi;
	public static DefaultTableModel tableModel;
	public static JTable table;
	public static JTextField txtDoanhThu;
	private JRadioButton radHomNay;
	private JRadioButton radMotTuan;
	private JRadioButton radMotThang;
	private JRadioButton radLuaChonKhac;
	private JDateChooser txtNgayMin;
	private JDateChooser txtNgayMax;
	public static JTextField txtMaHD;
	private JRadioButton radTangDan;
	private JRadioButton radGiamDan;
	private JButton btnHoaDon;
	private JButton btnLkDaBan;
	private JButton btnXuatExcel;
	public static ThongKeDoanhThuDao thongKeDoanhThuDao;
	private HoaDonDao hoaDonDao;
	private NhanVienDao nhanVienDao;

	public JPanel createPanelDoanhThu() {
		// khởi tạo kết nối đến Server
		try {
			hoaDonDao = (HoaDonDao) Naming.lookup(FrameDangNhap.IP+"hoaDonDao");
			nhanVienDao = (NhanVienDao) Naming.lookup(FrameDangNhap.IP+"nhanVienDao");
			thongKeDoanhThuDao = (ThongKeDoanhThuDao) Naming.lookup(FrameDangNhap.IP+"thongKeDoanhThuDao");
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
		// Chức năng
		btnHoaDon = new JButton("XEM HÓA ĐƠN", new ImageIcon("image/hoadon.png"));
		btnHoaDon.setBounds(410, 15, 190, 45);
		btnHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHoaDon.setBackground(new Color(79, 173, 84));
		btnHoaDon.setForeground(Color.WHITE);
		btnHoaDon.setFocusPainted(false);
		pnlContentPane.add(btnHoaDon);

		btnLkDaBan = new JButton("LINH KIỆN ĐÃ BÁN", new ImageIcon("image/chipwhite.png"));
		btnLkDaBan.setBounds(605, 15, 240, 45);
		btnLkDaBan.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLkDaBan.setBackground(new Color(79, 173, 84));
		btnLkDaBan.setForeground(Color.WHITE);
		btnLkDaBan.setFocusPainted(false);
		pnlContentPane.add(btnLkDaBan);

		btnXuatExcel = new JButton("XUẤT EXCEL", new ImageIcon("image/xuatexcel.png"));
		btnXuatExcel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXuatExcel.setBackground(new Color(79, 173, 84));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setBounds(850, 15, 165, 45);
		btnXuatExcel.setFocusPainted(false);
		pnlContentPane.add(btnXuatExcel);

		// Tìm kiếm
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
		pnlTimKiem.setBounds(20, 15, 195, 625);
		pnlTimKiem.setBackground(Color.WHITE);
		pnlTimKiem.setLayout(null);
		pnlContentPane.add(pnlTimKiem);

		JLabel lblThongKe = new JLabel("<html><div style='text-align: center;'>THỐNG KÊ THEO: </div></html>",
				SwingConstants.CENTER);
		lblThongKe.setOpaque(true);
		lblThongKe.setBackground(new Color(0, 148, 224));
		lblThongKe.setBounds(1, 10, 193, 30);
		lblThongKe.setForeground(Color.WHITE);
		lblThongKe.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblThongKe);
		radHomNay = new JRadioButton("Hôm nay");
		radHomNay.setBounds(20, 50, 100, 30);
		radHomNay.setBackground(Color.WHITE);
		radHomNay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radHomNay.setSelected(true);
		radMotTuan = new JRadioButton("1 tuần");
		radMotTuan.setBounds(20, 80, 100, 30);
		radMotTuan.setBackground(Color.WHITE);
		radMotTuan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radMotThang = new JRadioButton("1 tháng");
		radMotThang.setBounds(20, 110, 100, 30);
		radMotThang.setBackground(Color.WHITE);
		radMotThang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radLuaChonKhac = new JRadioButton("Lựa chọn khác");
		radLuaChonKhac.setBounds(20, 140, 120, 30);
		radLuaChonKhac.setBackground(Color.WHITE);
		radLuaChonKhac.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		ButtonGroup bg = new ButtonGroup();
		bg.add(radHomNay);
		bg.add(radMotTuan);
		bg.add(radMotThang);
		bg.add(radLuaChonKhac);
		pnlTimKiem.add(radHomNay);
		pnlTimKiem.add(radMotTuan);
		pnlTimKiem.add(radMotThang);
		pnlTimKiem.add(radLuaChonKhac);

		JLabel lblNgayMin = new JLabel("Từ: ");
		lblNgayMin.setBounds(30, 175, 120, 30);
		lblNgayMin.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMin);
		txtNgayMin = new JDateChooser();
		txtNgayMin.setDateFormatString("yyyy-MM-dd");
		txtNgayMin.setBounds(75, 175, 100, 30);
		txtNgayMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMin);

		JLabel lblNgayMax = new JLabel("Đến: ");
		lblNgayMax.setBounds(30, 215, 120, 30);
		lblNgayMax.setFont(new Font("Arial", Font.PLAIN, 15));
		pnlTimKiem.add(lblNgayMax);
		txtNgayMax = new JDateChooser();
		txtNgayMax.setDateFormatString("yyyy-MM-dd");
		txtNgayMax.setBounds(75, 215, 100, 30);
		txtNgayMax.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(txtNgayMax);

		JLabel lblMaHD = new JLabel("MÃ HÓA ĐƠN:", SwingConstants.CENTER);
		lblMaHD.setOpaque(true);
		lblMaHD.setBackground(new Color(0, 148, 224));
		lblMaHD.setBounds(1, 265, 193, 30);
		lblMaHD.setForeground(Color.WHITE);
		lblMaHD.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblMaHD);
		txtMaHD = new JTextField();
		txtMaHD.setBounds(12, 310, 170, 30);
		txtMaHD.setBackground(Color.WHITE);
		txtMaHD.setEditable(true);
		txtMaHD.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		pnlTimKiem.add(txtMaHD);

		JLabel lblSX = new JLabel("SẮP XẾP THEO:", SwingConstants.CENTER);
		lblSX.setOpaque(true);
		lblSX.setBackground(new Color(0, 148, 224));
		lblSX.setBounds(1, 355, 193, 30);
		lblSX.setForeground(Color.WHITE);
		lblSX.setFont(new Font("Arial", Font.BOLD, 15));
		pnlTimKiem.add(lblSX);
		String[] loai = { "Mã hóa đơn", "Tổng số tiền thanh toán", "Tên khách hàng" };
		cmbTieuChi = new JComboBox<String>(loai);
		cmbTieuChi.setBounds(12, 400, 170, 30);
		cmbTieuChi.setFocusable(false);
		cmbTieuChi.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlTimKiem.add(cmbTieuChi);

		radTangDan = new JRadioButton("Tăng dần");
		radTangDan.setBounds(15, 440, 80, 30);
		radTangDan.setBackground(Color.WHITE);
		radTangDan.setFont(new Font("Arial", Font.ITALIC, 13));
		radTangDan.setSelected(true);
		radGiamDan = new JRadioButton("Giảm dần");
		radGiamDan.setBounds(95, 440, 90, 30);
		radGiamDan.setBackground(Color.WHITE);
		radGiamDan.setFont(new Font("Arial", Font.ITALIC, 13));
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(radTangDan);
		bg2.add(radGiamDan);
		pnlTimKiem.add(radTangDan);
		pnlTimKiem.add(radGiamDan);

		btnTimKiem = new JButton("TÌM KIẾM", new ImageIcon("image/timkiem.png"));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(0, 148, 224));
		btnTimKiem.setBounds(13, 490, 170, 40);
		btnTimKiem.setForeground(Color.WHITE);
		btnTimKiem.setFocusPainted(false);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("LÀM MỚI", new ImageIcon("image/lammoi.png"));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLamMoi.setBackground(new Color(0, 148, 224));
		btnLamMoi.setBounds(13, 545, 170, 40);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		pnlTimKiem.add(btnLamMoi);

		/*
		 * Danh sách hóa đơn
		 */
		JPanel pnlDanhSach = new JPanel();
		pnlDanhSach.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Danh sách hóa đơn: "));
		pnlDanhSach.setBounds(250, 75, 770, 505);
		pnlDanhSach.setBackground(Color.WHITE);
		pnlDanhSach.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDanhSach);

		String[] header = { "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Thời gian mua", "Tiền linh kiện", "Thuế",
				"Tổng tiền" };
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
		table.getColumnModel().getColumn(2).setPreferredWidth(165);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);

		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		tableHeader.setResizingAllowed(false);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Tiền linh kiện").setCellRenderer(rightRenderer);
		table.getColumn("Thuế").setCellRenderer(rightRenderer);
		table.getColumn("Tổng tiền").setCellRenderer(rightRenderer);

		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		pnlDanhSach.add(new JScrollPane(table));

		/*
		 * Tổng doanh thu
		 */
		JLabel lblTongDT = new JLabel("TỔNG DOANH THU: ");
		lblTongDT.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTongDT.setBounds(400, 590, 300, 50);
		pnlContentPane.add(lblTongDT);
		txtDoanhThu = new JTextField("0.0 VNĐ");
		txtDoanhThu.setFont(new Font("Times New Roman", Font.BOLD, 25));
		txtDoanhThu.setBounds(650, 590, 500, 50);
		txtDoanhThu.setEditable(false);
		txtDoanhThu.setBorder(BorderFactory.createEmptyBorder());
		txtDoanhThu.setBackground(Color.WHITE);
		pnlContentPane.add(txtDoanhThu);

		btnHoaDon.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnLkDaBan.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnXuatExcel.addActionListener(this);
		radHomNay.addActionListener(this);
		radMotTuan.addActionListener(this);
		radMotThang.addActionListener(this);
		radLuaChonKhac.addActionListener(this);

		List<HoaDon> listHD = null;
		try {
			listHD = thongKeDoanhThuDao.getAllHoaDon();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		docDuLieuVaoTable(listHD);
		docDuLieuVaoTxtMaHD();
		setEditableJDateChooder(false);

		return pnlContentPane;
	}

	public static void xoaHetDL() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.setRowCount(0);
	}

	public static void docDuLieuVaoTable(List<HoaDon> listHD) {
		DecimalFormat df = new DecimalFormat("#,##0.0");
		SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
		double tongDoanhThu = 0;
		if (listHD != null) {
			for (HoaDon hoaDon : listHD) {
				double thanhTien = 0;
				try {
					if (hoaDon.getNgayLap() != null)
						thanhTien = thongKeDoanhThuDao.getThanhTienTheoMaHoaDon(hoaDon.getMaHD());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				tableModel.addRow(new Object[] { hoaDon.getMaHD(), hoaDon.getNhanVien().getTenNV(),
						hoaDon.getKhachHang().getTenKH(), df2.format(hoaDon.getNgayLap()), df.format(thanhTien),
						df.format(hoaDon.getThue()), df.format(hoaDon.getTongTien()) });
				tongDoanhThu += hoaDon.getTongTien();
			}

		}
		txtDoanhThu.setText(df.format(tongDoanhThu) + " VNĐ");
	}

	public static void docDuLieuVaoTxtMaHD() {
		List<HoaDon> listHD = null;
		try {
			listHD = thongKeDoanhThuDao.getAllHoaDon();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List<String> listMaHD = new ArrayList<String>();
		if (listHD != null) {
			for (HoaDon hoaDon : listHD) {
				listMaHD.add(hoaDon.getMaHD().trim());
			}
		}
		AutoCompleteDecorator.decorate(txtMaHD, listMaHD, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(radHomNay)) {
			setEditableJDateChooder(false);
		}
		if (o.equals(radMotTuan)) {
			setEditableJDateChooder(false);
		}
		if (o.equals(radMotThang)) {
			setEditableJDateChooder(false);
		}
		if (o.equals(radLuaChonKhac)) {
			setEditableJDateChooder(true);
		}
		if (o.equals(btnHoaDon)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				HoaDon hd = null;
				try {
					hd = thongKeDoanhThuDao.getHoaDonTheoMaHoaDon(table.getValueAt(row, 0).toString().trim());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				new FrameHoaDonTinhTien(hd.getKhachHang().getTenKH(), hd.getNhanVien().getTenNV(), hd.getMaHD(),
						hd.getNgayLap()).setVisible(true);
			} else
				JOptionPane.showMessageDialog(this, "Chọn hoá đơn cần xem");
		}
		if (o.equals(btnLamMoi)) {
			txtNgayMax.setDate(null);
			txtNgayMin.setDate(null);
			txtMaHD.setText("");
			cmbTieuChi.setSelectedIndex(0);
			radTangDan.setSelected(true);
			xoaHetDL();
			List<HoaDon> listHD = null;
			try {
				listHD = thongKeDoanhThuDao.getAllHoaDon();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			docDuLieuVaoTable(listHD);
		}
		if (o.equals(btnLkDaBan)) {
			new FrameLinhKienDaBan(txtNgayMin.getDate(), txtNgayMax.getDate()).setVisible(true);
		}
		if (o.equals(btnTimKiem)) {
			List<HoaDon> listHoaDon = new ArrayList<HoaDon>();
			xoaHetDL();
			if (radHomNay.isSelected()) {
				try {
					listHoaDon = thongKeDoanhThuDao.getHoaDonHomNay();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			if (radMotTuan.isSelected()) {
				try {
					listHoaDon = thongKeDoanhThuDao.getHoaDon1Tuan();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			if (radMotThang.isSelected()) {
				try {
					listHoaDon = thongKeDoanhThuDao.getHoaDon1Thang();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			if (radLuaChonKhac.isSelected()) {
				Date tuNgay = txtNgayMin.getDate();
				Date denNgay = txtNgayMax.getDate();
				java.sql.Date ngayMin = null;
				java.sql.Date ngayMax = null;

				List<HoaDon> listHoaDon1 = null;
				try {
					listHoaDon1 = thongKeDoanhThuDao.getAllHoaDon();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				if (tuNgay != null) {
					ngayMin = new java.sql.Date(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDate());
					List<HoaDon> listTemp = new ArrayList<HoaDon>();
					for (HoaDon hd : listHoaDon1) {
						listTemp.add(hd);
					}
					listHoaDon1.clear();
					for (HoaDon hd : listTemp) {
						try {
							String output = df.format(hd.getNgayLap());
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(output);
							if (date.compareTo(ngayMin) >= 0) {
								listHoaDon1.add(hd);
							}

						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
				if (denNgay != null) {
					ngayMax = new java.sql.Date(denNgay.getYear(), denNgay.getMonth(), denNgay.getDate());
					List<HoaDon> listTemp = new ArrayList<HoaDon>();
					for (HoaDon hd : listHoaDon1) {
						listTemp.add(hd);
					}
					listHoaDon1.clear();
					for (HoaDon hd : listTemp) {
						try {
							String output = df.format(hd.getNgayLap());
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(output);
							if (date.compareTo(ngayMax) <= 0)
								listHoaDon1.add(hd);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
				listHoaDon = listHoaDon1;
			}
			if (!txtMaHD.getText().trim().equals("")) {
				List<HoaDon> listTemp = new ArrayList<HoaDon>();
				for (HoaDon hd : listHoaDon) {
					listTemp.add(hd);
				}
				listHoaDon.clear();
				for (HoaDon hd : listTemp) {
					if (hd.getMaHD().trim().contains(txtMaHD.getText().trim())) {
						listHoaDon.add(hd);
					}
				}
			}
			if (listHoaDon.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có hoá đơn phù hợp với tiêu chí", "Lỗi",
						JOptionPane.ERROR_MESSAGE);

				btnLamMoi.doClick();
				return;
			}
			// Sắp xếp
			int thuTu = 1;
			if (radTangDan.isSelected())
				thuTu = 0;
			int tieuChi = cmbTieuChi.getSelectedIndex();
			if (tieuChi == 0)
				sapXepTheoMa(listHoaDon, thuTu);
			if (tieuChi == 1)
				sapXepTheoTongTien(listHoaDon, thuTu);
			if (tieuChi == 2)
				sapXepTheoTenKH(listHoaDon, thuTu);
			xoaHetDL();
			if (listHoaDon != null)
				docDuLieuVaoTable(listHoaDon);
		}
		if (o.equals(btnXuatExcel)) {
			if (xuatExcel())
				JOptionPane.showMessageDialog(this, "Xuất file thành công");
			else
				JOptionPane.showMessageDialog(this, "Xuất file thất bại");
		}
	}

	public void sapXepTheoMa(List<HoaDon> list, int giamDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (giamDan == 0) {
					if (o1 != null && o2 != null)
						return o1.getMaHD().compareToIgnoreCase(o2.getMaHD());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getMaHD().compareToIgnoreCase(o1.getMaHD());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoTongTien(List<HoaDon> list, int giamDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (giamDan == 0) {
					if (o1 != null && o2 != null)
						return Double.compare(o1.getTongTien(), o2.getTongTien());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return Double.compare(o2.getTongTien(), o1.getTongTien());
					return 0;
				}
			}
		});
	}

	public void sapXepTheoTenKH(List<HoaDon> list, int giamDan) {
		Collections.sort(list, new Comparator<HoaDon>() {
			@Override
			public int compare(HoaDon o1, HoaDon o2) {
				if (giamDan == 0) {
					if (o1 != null && o2 != null)
						return o1.getKhachHang().getTenKH().compareToIgnoreCase(o2.getKhachHang().getTenKH());
					return 0;
				} else {
					if (o1 != null && o2 != null)
						return o2.getKhachHang().getTenKH().compareToIgnoreCase(o1.getKhachHang().getTenKH());
					return 0;
				}
			}
		});
	}

	public boolean xuatExcel() {
		try {
			FileOutputStream fileOut = new FileOutputStream("excel/ThongKeDoanhThu.xls");
			// Tạo sheet Danh sách khách hàng
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Thống kê doanh thu");

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

			String[] header = { "STT", "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Thời gian mua",
					"Tiền linh kiện", "Thuế", "Tổng tiền" };

			cell.setCellValue("THỐNG KÊ DOANH THU");
			cell.setCellStyle(styleTenDanhSach);
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

	public void setEditableJDateChooder(boolean trangThai) {
		txtNgayMax.setEnabled(trangThai);
		txtNgayMin.setEnabled(trangThai);
	}
}
