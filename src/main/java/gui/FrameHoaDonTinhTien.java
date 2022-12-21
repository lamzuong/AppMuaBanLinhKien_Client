package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.HoaDonDao;
import entity.ChiTietHoaDon;

public class FrameHoaDonTinhTien extends JFrame implements ActionListener {
	private DefaultTableModel tableModel;
	private JTable table;
	private JButton btnIn;
	private JLabel lblTenKH;
	private JLabel lblTenNV;
	private JLabel lblThoiGianLapHD;
	private JLabel lblMaHD;
	private HoaDonDao hoaDonDao;

	public FrameHoaDonTinhTien(String tenKH, String tenNV, String maHD,Date ngayLap) {
		// khởi tạo kết nối đến Server
		try {
			hoaDonDao = (HoaDonDao) Naming.lookup(FrameDangNhap.IP+"hoaDonDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		//------------------------------
		setTitle("HÓA ĐƠN TÍNH TIỀN");
		setSize(500, 560);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/chipicon.png");
		setIconImage(icon.getImage());

		JPanel pnlTong = new JPanel(new BorderLayout());

		JPanel pnlTren = new JPanel();
		pnlTren.setBackground(Color.WHITE);
		// Thông tin quán
		JPanel panelTrenGiua = new JPanel(new BorderLayout());
		panelTrenGiua.setBackground(Color.WHITE);

		Box boxTren = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		Box b7 = Box.createHorizontalBox();
		Box b8 = Box.createHorizontalBox();

		JLabel lblTenCH = new JLabel("CỬA HÀNG LINH KIỆN ĐIỆN TỬ KILBY");
		lblTenCH.setFont(new Font("Times New Roman", Font.BOLD, 18));
		b1.add(lblTenCH);
		boxTren.add(b1);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblDiachi = new JLabel("Địa chỉ: Số 445 Nguyễn Văn Luông, Phường 12, Quận 6, TPHCM");
		lblDiachi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b2.add(lblDiachi);
		boxTren.add(b2);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblSdt = new JLabel("SĐT: 0794861181");
		lblSdt.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b3.add(lblSdt);
		boxTren.add(b3);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lblHoaDon = new JLabel("HOÁ ĐƠN TÍNH TIỀN");
		lblHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 18));
		b4.add(lblHoaDon);
		boxTren.add(b4);
		boxTren.add(Box.createVerticalStrut(5));

		// Thông tin hoá đơn khách hàng
		JLabel lbl1 = new JLabel("Tên nhân viên:");
		lbl1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b5.add(lbl1);
		lblTenNV = new JLabel(tenNV);
		lblTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b5.add(Box.createHorizontalStrut(88));
		b5.add(lblTenNV);
		b5.add(Box.createHorizontalGlue());
		boxTren.add(b5);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl2 = new JLabel("Tên khách hàng:");
		lbl2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b6.add(lbl2);
		lblTenKH = new JLabel(tenKH);
		lblTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b6.add(Box.createHorizontalStrut(74));
		b6.add(lblTenKH);
		b6.add(Box.createHorizontalGlue());
		boxTren.add(b6);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl3 = new JLabel("Thời gian lập hóa đơn:");
		lbl3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b7.add(lbl3);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		lblThoiGianLapHD = new JLabel();
		lblThoiGianLapHD.setText(df.format(ngayLap));
		lblThoiGianLapHD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b7.add(Box.createHorizontalStrut(35));
		b7.add(lblThoiGianLapHD);
		b7.add(Box.createHorizontalGlue());
		boxTren.add(b7);
		boxTren.add(Box.createVerticalStrut(5));

		JLabel lbl4 = new JLabel("Mã hóa đơn:");
		lbl4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		b8.add(lbl4);
		lblMaHD = new JLabel(maHD);
		lblMaHD.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		b8.add(Box.createHorizontalStrut(100));
		b8.add(lblMaHD);
		b8.add(Box.createHorizontalGlue());
		boxTren.add(b8);
		panelTrenGiua.add(boxTren, BorderLayout.NORTH);

		// Bảng CTHD
		JPanel pnlTable = new JPanel(new BorderLayout());

		String[] header = { "Tên linh kiện", "Số lượng", "Giá", "Tổng" };
		tableModel = new DefaultTableModel(header, 0);
		table = new JTable(tableModel);

		List<ChiTietHoaDon> listCTHD = null;
		try {
			listCTHD = hoaDonDao.getCTHDTheoMaHD(maHD);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		DecimalFormat dfMoney = new DecimalFormat("#,###.0");
		if (listCTHD != null) {
			for (ChiTietHoaDon cthd : listCTHD) {
				tableModel.addRow(new Object[] { cthd.getLinhKien().getTenLK(), cthd.getSoLuong(), 
						dfMoney.format(cthd.getGiaTien()), dfMoney.format(cthd.getThanhTien())
						 });
			}
		}
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setGridColor(getBackground());
		table.getTableHeader().setBackground(new Color(255, 255, 255));
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 15));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Số lượng").setCellRenderer(rightRenderer);
		table.getColumn("Giá").setCellRenderer(rightRenderer);
		table.getColumn("Tổng").setCellRenderer(rightRenderer);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(160);

		pnlTable.add(table.getTableHeader(), BorderLayout.NORTH);
		pnlTable.add(table, BorderLayout.CENTER);

		panelTrenGiua.add(pnlTable, BorderLayout.CENTER);

		// Tính tiền
		Box boxDuoi = Box.createVerticalBox();

		Box boxDuoi1 = Box.createHorizontalBox();
		Box boxDuoi2 = Box.createHorizontalBox();
		Box boxDuoi3 = Box.createHorizontalBox();
		Box boxDuoi4 = Box.createHorizontalBox();

		JLabel lblTienLK = new JLabel("Tổng tiền linh kiện:");
		lblTienLK.setFont(new Font("Times New Roman", Font.BOLD, 15));
		boxDuoi1.add(lblTienLK);
		boxDuoi1.add(Box.createHorizontalGlue());

		double tongTien = 0;

		if (listCTHD != null) {
			for (ChiTietHoaDon cthd : listCTHD) {
				tongTien += cthd.getThanhTien();
			}
		}
		JLabel lblTongTien = new JLabel(dfMoney.format(tongTien));
		lblTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		boxDuoi1.add(lblTongTien);

		JLabel lblThueLabel = new JLabel("Thuế:");
		lblThueLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		boxDuoi2.add(lblThueLabel);
		boxDuoi2.add(Box.createHorizontalGlue());
		JLabel lblThue = new JLabel();
		lblThue.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblThue.setText(dfMoney.format(tongTien*0.1));
		boxDuoi2.add(lblThue);

		JLabel lblTong = new JLabel("Tổng hoá đơn:");
		lblTong.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi3.add(lblTong);
		boxDuoi3.add(Box.createHorizontalGlue());
		JLabel lblTongHoaDon = new JLabel(dfMoney.format(tongTien + tongTien * 0.1));
		lblTongHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		boxDuoi3.add(lblTongHoaDon);

		boxTren.add(Box.createVerticalStrut(5));
		JLabel lblCamOn = new JLabel("XIN CẢM ƠN VÀ HẸN GẶP LẠI QUÝ KHÁCH!");
		lblCamOn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		boxDuoi4.add(lblCamOn);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi1);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi2);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi3);
		boxDuoi.add(Box.createVerticalStrut(5));
		boxDuoi.add(boxDuoi4);

		panelTrenGiua.add(boxDuoi, BorderLayout.SOUTH);
		pnlTren.add(panelTrenGiua);
		pnlTren.setAutoscrolls(true);
		pnlTong.add(new JScrollPane(pnlTren));

		JPanel pnlDuoi = new JPanel();
		pnlDuoi.setPreferredSize(new Dimension(WIDTH, 50));

		btnIn = new JButton("IN HÓA ĐƠN", new ImageIcon("image/inhoadon.png"));
		btnIn.setPreferredSize(new Dimension(160, 35));
		btnIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnIn.setBackground(new Color(0, 148, 224));
		btnIn.setForeground(Color.WHITE);
		btnIn.setFocusPainted(false);

		pnlDuoi.add(btnIn);
		pnlDuoi.setBackground(Color.WHITE);

		pnlTong.add(pnlDuoi, BorderLayout.SOUTH);

		add(pnlTong);

		btnIn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnIn)) {
			JOptionPane.showMessageDialog(this, "In hoá đơn thành công");
			dispose();
		}

	}
}
