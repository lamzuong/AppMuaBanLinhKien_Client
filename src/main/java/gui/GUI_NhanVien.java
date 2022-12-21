package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import dao.HoaDonDao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.LinhKien;

public class GUI_NhanVien extends JFrame implements MouseListener {
	private JLabel lblDangXuat;
	private JLabel lblDoiMatKhau;
	private HoaDonDao hoaDonDao;

	public GUI_NhanVien() throws RemoteException {
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
		//-----------------------------
		// Xoá hoá đơn khi tắt ứng dụng mà chưa thanh toán
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				List<HoaDon> listHD = null;
				try {
					listHD = hoaDonDao.getAllHoaDon();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (listHD != null) {
					for (HoaDon hoaDon : listHD) {
						if (hoaDon.getNgayLap() == null) {
							HoaDon hd = new HoaDon(hoaDon.getMaHD());
							try {
								List<ChiTietHoaDon> listCTHD = hoaDonDao.getCTHDTheoMaHD(hoaDon.getMaHD());
								for (ChiTietHoaDon chiTietHoaDon : listCTHD) {
									// Cập nhật số lượng
									hoaDonDao.updateSoLuongTheoMaKhiXoa(chiTietHoaDon.getSoLuong(),
											chiTietHoaDon.getLinhKien().getMaLK());
									// Xoá chi tiết hoá đơn
									LinhKien lk1 = new LinhKien(chiTietHoaDon.getLinhKien().getMaLK().trim());
									hoaDonDao.removeChiTietHoaDon(new ChiTietHoaDon(hd, lk1, 0, 0));
								}
								// Xoá hoá đơn
								hoaDonDao.removeHoaDon(hoaDon.getMaHD());
							} catch (RemoteException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}
		});
		setTitle("CỬA HÀNG LINH KIỆN ĐIỆN TỬ KILBY");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1260, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		UIManager.put("TabbedPane.selected", new Color(50, 190, 255));
		ImageIcon icon = new ImageIcon("image/chip.png");
		setIconImage(icon.getImage());

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setUI(new BasicTabbedPaneUI() {
			@Override
			protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
				return 80;
			}

			@Override
			protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
				return 200;
			}
		});
		tabbedPane.setBackground(new Color(0, 148, 224));
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 12));

		FrameLinhKien frameLK = new FrameLinhKien();
		FrameKhachHang frameKH = new FrameKhachHang();
		FrameHoaDon frameHD = new FrameHoaDon();
		FrameThongKeDoanhThu frameTKDT = new FrameThongKeDoanhThu();

		/* create JPanel, which is content of tabs */
		JPanel panelLinhKien = frameLK.createPanelLinhKien();
		JPanel panelKhachHang = frameKH.createPanelKhachHang();
		JPanel panelHoaDon = frameHD.createPanelHoaDon();
		JPanel panelTKDoanhThu = frameTKDT.createPanelDoanhThu();

		tabbedPane.addTab("TRANG CHỦ", new ImageIcon("image/trangchu.png"), createPanelTrangChu(), "TRANG CHỦ");
		tabbedPane.addTab("QUẢN LÝ KHÁCH HÀNG", new ImageIcon("image/khachhang.png"), panelKhachHang,
				"QUẢN LÝ KHÁCH HÀNG");
		tabbedPane.addTab("QUẢN LÝ LINH KIỆN", new ImageIcon("image/chipwhite.png"), panelLinhKien,
				"QUẢN LÝ LINH KIỆN");
		tabbedPane.addTab("LẬP HÓA ĐƠN BÁN HÀNG", new ImageIcon("image/hoadon.png"), panelHoaDon, "LẬP HÓA ĐƠN");
		tabbedPane.addTab("THỐNG KÊ DOANH THU", new ImageIcon("image/doanhthu.png"), panelTKDoanhThu,
				"THỐNG KÊ DOANH THU");

		add(tabbedPane);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new GUI_NhanVien().setVisible(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	private JPanel createPanelTrangChu() {
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(null);
		/*
		 * Phần Head
		 */
		JLabel logan = new JLabel("LINH KIỆN ĐIỆN TỬ KILBY");
		logan.setBounds(20, 0, 800, 50);
		logan.setFont(new Font("DialogInput", Font.BOLD, 50));
		logan.setForeground(Color.WHITE);
		contentPane.add(logan);

		ImageIcon logoIcon = new ImageIcon("image/KILBY-logo-white.png");
		Image logo = logoIcon.getImage();
		Image logoResize = logo.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		JLabel lblLogo = new JLabel(new ImageIcon(logoResize));
		lblLogo.setBounds(720, 5, 70, 50);
		contentPane.add(lblLogo);

		lblDoiMatKhau = new JLabel("<HTML><U>ĐỔI MẬT KHẨU</U></HTML>");
		lblDoiMatKhau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDoiMatKhau.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblDoiMatKhau.setForeground(Color.WHITE);
		lblDoiMatKhau.setBounds(830, 0, 110, 42);
		contentPane.add(lblDoiMatKhau);

		lblDangXuat = new JLabel("<HTML><U>ĐĂNG XUẤT</U></HTML>");
		lblDangXuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDangXuat.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblDangXuat.setForeground(Color.WHITE);
		lblDangXuat.setBounds(945, 0, 110, 42);
		contentPane.add(lblDangXuat);
		/*
		 * Phần Body
		 */
		ImageIcon imageIcon = new ImageIcon("image/nentrangchu.jpg");
		Image image = imageIcon.getImage();
		Image imageResize = image.getScaledInstance(1100, 600, Image.SCALE_SMOOTH);
		JLabel lblTrangChu = new JLabel(new ImageIcon(imageResize));
		lblTrangChu.setBounds(0, 60, 1100, 600);
		contentPane.add(lblTrangChu);

		lblDangXuat.addMouseListener(this);
		lblDoiMatKhau.addMouseListener(this);
		return contentPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(lblDangXuat)) {
			int result = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất không?", "ĐĂNG XUẤT",
					JOptionPane.YES_NO_OPTION);
			// Xoá hoá đơn khi tắt ứng dụng mà chưa thanh toán
			// khởi tạo kết nối đến Server
			try {
				hoaDonDao = (HoaDonDao) Naming.lookup("rmi://localhost:9999/hoaDonDao");
			} catch (RemoteException e2) {
				e2.printStackTrace();
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			} catch (NotBoundException e2) {
				e2.printStackTrace();
			}
			//--------------------------------
			List<HoaDon> listHD = null;
			try {
				listHD = hoaDonDao.getAllHoaDon();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if (listHD != null) {
				for (HoaDon hoaDon : listHD) {
					if (hoaDon.getNgayLap() == null) {
						HoaDon hd = new HoaDon(hoaDon.getMaHD());
						try {
							List<ChiTietHoaDon> listCTHD = hoaDonDao.getCTHDTheoMaHD(hoaDon.getMaHD());
							for (ChiTietHoaDon chiTietHoaDon : listCTHD) {
								// Cập nhật số lượng
								hoaDonDao.updateSoLuongTheoMaKhiXoa(chiTietHoaDon.getSoLuong(),
										chiTietHoaDon.getLinhKien().getMaLK());
								// Xoá chi tiết hoá đơn
								LinhKien lk = new LinhKien(chiTietHoaDon.getLinhKien().getMaLK().trim());
								hoaDonDao.removeChiTietHoaDon(new ChiTietHoaDon(hd, lk, 0, 0));
							}
							// Xoá hoá đơn
							hoaDonDao.removeHoaDon(hoaDon.getMaHD());
						} catch (RemoteException e2) {
							e2.printStackTrace();
						}
					}
				}
			}
			if (result == 0) {
				try {
					FrameDangNhap frameDangNhap = new FrameDangNhap();
					frameDangNhap.setVisible(true);
					frameDangNhap.setLocationRelativeTo(null);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		}
		if (o.equals(lblDoiMatKhau)) {
			try {
				FrameDoiMatKhau frameDoiMatKhau = new FrameDoiMatKhau();
				frameDoiMatKhau.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				frameDoiMatKhau.setVisible(true);
				frameDoiMatKhau.setLocationRelativeTo(null);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
