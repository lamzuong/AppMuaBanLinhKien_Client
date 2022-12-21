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
import javax.swing.JPasswordField;

import dao.TaiKhoanDao;
import entity.TaiKhoan;

public class FrameDoiMatKhau extends JFrame implements ActionListener {

	private JPasswordField txtMatkhauCu;
	private JPasswordField txtMatkhauMoi;
	private JPasswordField txtXacNhan;
	private JButton btnDoiMatKhau;
	private TaiKhoanDao taiKhoanDao;

	public FrameDoiMatKhau() throws RemoteException {
		// khởi tạo kết nối đến Server
		try {
			taiKhoanDao = (TaiKhoanDao) Naming.lookup(FrameDangNhap.IP+"taiKhoanDao");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		// ------------------------------

		setTitle("ĐỔI MẬT KHẨU");
		setSize(400, 220);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("image/chipicon.png");
		setIconImage(icon.getImage());

		JPanel pnlContentPane = new JPanel(null);
		pnlContentPane.setBackground(new Color(219, 255, 255));
		setContentPane(pnlContentPane);

		txtMatkhauCu = new JPasswordField();
		txtMatkhauCu.setBounds(150, 15, 204, 30);
		pnlContentPane.add(txtMatkhauCu);

		txtMatkhauMoi = new JPasswordField();
		txtMatkhauMoi.setBounds(150, 50, 204, 30);
		pnlContentPane.add(txtMatkhauMoi);

		txtXacNhan = new JPasswordField();
		txtXacNhan.setBounds(150, 85, 204, 30);
		pnlContentPane.add(txtXacNhan);

		JLabel lblMKcu = new JLabel("MẬT KHẨU CŨ:");
		lblMKcu.setBounds(30, 20, 120, 20);
		lblMKcu.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblMKcu);

		JLabel lblMKmoi = new JLabel("MẬT KHẨU MỚI:");
		lblMKmoi.setBounds(30, 55, 120, 20);
		lblMKmoi.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblMKmoi);

		JLabel lblXn = new JLabel("XÁC NHẬN:");
		lblXn.setBounds(30, 90, 120, 20);
		lblXn.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlContentPane.add(lblXn);

		btnDoiMatKhau = new JButton("ĐỔI MẬT KHẨU");
		btnDoiMatKhau.setBounds(110, 125, 180, 42);
		btnDoiMatKhau.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDoiMatKhau.setIcon(new ImageIcon("image/matkhau.png"));
		btnDoiMatKhau.setForeground(Color.WHITE);
		btnDoiMatKhau.setBackground(new Color(79, 173, 84));
		btnDoiMatKhau.setFocusPainted(false);

		pnlContentPane.add(btnDoiMatKhau);

		btnDoiMatKhau.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDoiMatKhau)) {
			String matKhauCu = txtMatkhauCu.getText().trim();
			String matKhauMoi = txtMatkhauMoi.getText().trim();
			String xacNhan = txtXacNhan.getText().trim();

			try {
				if (!validInput())
					return;
				else {
					TaiKhoan tk = taiKhoanDao.getTaiKhoanTheoTenTK(FrameDangNhap.getTaiKhoan());
					tk.setMatKhau(matKhauMoi);
					if (taiKhoanDao.capnhatTaiKhoan(tk) == true) {
						JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
						dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại");
					}
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	public boolean validInput() throws RemoteException {
		String matKhauCu = txtMatkhauCu.getText().trim();
		String matKhauMoi = txtMatkhauMoi.getText().trim();
		String xacNhan = txtXacNhan.getText().trim();

		if (matKhauCu.equals("") || matKhauMoi.equals("") || xacNhan.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mật khẩu");
			return false;
		}
		TaiKhoan tk = taiKhoanDao.getTaiKhoanTheoTenTK(FrameDangNhap.getTaiKhoan());
		if (!matKhauCu.equals(tk.getMatKhau().trim())) {
			JOptionPane.showMessageDialog(this, "Mật khẩu cũ không chính xác");
			return false;
		}
		if (matKhauMoi.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}")) {
			if (!matKhauMoi.equals(xacNhan)) {
				JOptionPane.showMessageDialog(this, "Xác nhận không chính xác");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Mật khẩu mới từ 8 đến 20 kí tự gồm cả chữ và số");
			return false;
		}
		return true;
	}
}
