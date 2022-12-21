package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import dao.ThongKeDoanhThuDao;
import entity.LinhKien;

public class FrameLinhKienDaBan extends JFrame {
	private DefaultTableModel tableModel;
	private JTable table;
	private ThongKeDoanhThuDao thongKeDoanhThuDao;

	public FrameLinhKienDaBan(Date tuNgay, Date denNgay) {
		// khởi tạo kết nối đến Server
		try {
			thongKeDoanhThuDao =  (ThongKeDoanhThuDao) Naming.lookup(FrameDangNhap.IP+"thongKeDoanhThuDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ----------------------------
		setTitle("DỊCH VỤ BÁN CHẠY");
		setSize(700, 350);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/logodark.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBackground(new Color(255, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JPanel pnlDVBanChay = new JPanel();
		pnlDVBanChay.setBounds(10, 5, 668, 300);
		pnlDVBanChay.setLayout(new GridLayout(1, 0, 0, 0));
		pnlContentPane.add(pnlDVBanChay);

		String[] header = { "Mã linh kiện", "Tên linh kiện", "Giá hiện tại", "Số lượng bán ra" };
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
		table.setBounds(10, 5, 667, 300);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setGridColor(getBackground());
		table.setRowHeight(table.getRowHeight() + 20);
		table.setSelectionBackground(new Color(255, 255, 128));
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(219, 255, 255));
		tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableHeader.setPreferredSize(new Dimension(WIDTH, 30));
		pnlDVBanChay.add(new JScrollPane(table));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(255);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		table.getColumn("Giá hiện tại").setCellRenderer(rightRenderer);
		table.getColumn("Số lượng bán ra").setCellRenderer(rightRenderer);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);

		DecimalFormat df = new DecimalFormat("#,###.0");
		DecimalFormat df2 = new DecimalFormat("#,###");
		try {
			List<LinhKien> listLK = thongKeDoanhThuDao.getLinhKienTrongKhoangThoiGian(tuNgay, denNgay);
			for (LinhKien lk : listLK) {
				tableModel.addRow( new Object [] {lk.getMaLK(),lk.getTenLK(),df.format(lk.getGiaBan()),df2.format(lk.getSoLuong())});
			}
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
	}
}
