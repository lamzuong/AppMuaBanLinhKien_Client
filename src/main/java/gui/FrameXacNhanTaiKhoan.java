package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.NhanVienDao;
import entity.NhanVien;

public class FrameXacNhanTaiKhoan extends JFrame implements ActionListener {
	private JTextField txtMaNV;
	private JTextField txtCMND;
	private JTextField txtTenTK;
	private JButton btnXacNhan;
	private NhanVienDao nhanVienDao;

	public FrameXacNhanTaiKhoan() throws RemoteException {
		// khởi tạo kết nối đến ServerL
		try {
			nhanVienDao = (NhanVienDao) Naming.lookup(FrameDangNhap.IP+"nhanVienDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ------------------------------

		setTitle("XÁC NHẬN TÀI KHOẢN");
		setSize(350, 220);
		setResizable(false);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("image/chipicon.png");
		setIconImage(icon.getImage());

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				try {
					new FrameDangNhap().setVisible(true);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		JPanel pnlContentPane = new JPanel();
		pnlContentPane.setBounds(15, 0, 410, 365);
		pnlContentPane.setBackground(new Color(219, 255, 255));
		pnlContentPane.setLayout(null);
		setContentPane(pnlContentPane);

		JLabel lblMaNV = new JLabel("MÃ NHÂN VIÊN: ");
		lblMaNV.setBounds(25, 20, 120, 20);
		lblMaNV.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblMaNV);

		txtMaNV = new JTextField();
		txtMaNV.setBounds(150, 15, 160, 30);
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtMaNV);

		JLabel lblCMND = new JLabel("CMND/CCCD: ");
		lblCMND.setBounds(25, 55, 120, 20);
		lblCMND.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblCMND);

		txtCMND = new JTextField();
		txtCMND.setBounds(150, 50, 160, 30);
		txtCMND.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtCMND);

		JLabel lblTenTK = new JLabel("TÊN TÀI KHOẢN:");
		lblTenTK.setBounds(25, 90, 120, 20);
		lblTenTK.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblTenTK);

		txtTenTK = new JTextField();
		txtTenTK.setBounds(150, 85, 160, 30);
		txtTenTK.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		pnlContentPane.add(txtTenTK);

		btnXacNhan = new JButton("XÁC NHẬN TÀI KHOẢN");
		btnXacNhan.setBounds(60, 125, 220, 42);
		btnXacNhan.setForeground(Color.WHITE);
		btnXacNhan.setBackground(new Color(79, 173, 84));
		btnXacNhan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnXacNhan.setFocusPainted(false);
		btnXacNhan.setIcon(new ImageIcon("image/matkhau.png"));
		pnlContentPane.add(btnXacNhan);

		btnXacNhan.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(btnXacNhan)) {
			String maNV = txtMaNV.getText().trim();
			String cmnd = txtCMND.getText().trim();
			String tenTK = txtTenTK.getText().trim();
			if (maNV.equals("") || cmnd.equals("") || tenTK.equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
				return;
			}
			try {
				NhanVien nv = nhanVienDao.getNhanVienTheoMa(maNV);
				if (nv != null) {
					if (!(maNV.equals(nv.getMaNV().trim()))) {
						JOptionPane.showMessageDialog(this, "Mã nhân viên không chính xác");
						return;
					}
					if (!(cmnd.equals(nv.getCmnd().trim()))) {
						JOptionPane.showMessageDialog(this, "Chứng minh nhân dân không chính xác");
						return;
					}
					if (nv.getTaiKhoan() == null) {
						JOptionPane.showMessageDialog(this, "Nhân viên đã nghỉ việc");
						return;
					}
					if (!(tenTK.equals(nv.getTaiKhoan().getTenTK().trim()))) {
						JOptionPane.showMessageDialog(this, "Tên tài khoản không chính xác");
						return;
					}
					JOptionPane.showMessageDialog(this, "Xác nhận thành công");
					new FrameDatLaiMatKhau(nv).setVisible(true);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại");
					return;
				}

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
