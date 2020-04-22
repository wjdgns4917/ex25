package com.one.game;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class MyFrame2 extends JFrame {
	JTextField gno, name, score, ptime;
	JButton preButton, nextButton, insertButton, delButton,clearBtn;
	ResultSet rs;
	Statement stmt;
	
	public MyFrame2() throws SQLException {
		setTitle("select data");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Connection con = dbConnection();
		stmt = con.createStatement(
				rs.TYPE_SCROLL_SENSITIVE,
				rs.CONCUR_UPDATABLE
				);
		rs = stmt.executeQuery("select * from gamescore");
		
		
		setLayout(new GridLayout(0, 2));
		add(new Label("gno", JLabel.CENTER));
		add(gno = new JTextField());
		add(new Label("name", JLabel.CENTER));
		add(name = new JTextField());
		add(new Label("score", JLabel.CENTER));
		add(score = new JTextField());
		add(new Label("ptime", JLabel.CENTER));
		add(ptime = new JTextField());
		
		
		preButton = new JButton("Prev");
		nextButton = new JButton("Next");
		insertButton = new JButton("Insert");
		delButton = new JButton("Del");
		clearBtn= new JButton("Clear");
		
		
		add(preButton);
		add(nextButton);
		add(insertButton);
		add(delButton);
		add(clearBtn);
		
		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("DelBtn");
				int no=Integer.parseInt(gno.getText());
				String sql="DELETE FROM gamescore WHERE gno="+no;
				try {
					int result=stmt.executeUpdate(sql);
					if (result==1) {
						System.out.println("삭제 성공");
					}else {
						System.out.println("삭제 실패");
					}
					rs=stmt.executeQuery("select * from gamescore");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		clearBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gno.setText("");
				name.setText("");
				score.setText("");
				ptime.setText("");
				
			}
		});
		
		insertButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Insert");
				int no=Integer.parseInt(gno.getText());
				String na=name.getText();
				int sc=Integer.parseInt(score.getText());
				String pt=ptime.getText();
				
				String sql="INSERT INTO gamescore" + 
						" VALUES(gscore_gno_seq.nextval,'"+na+"',"+sc+",'"+pt+"')";
				
				try {
					int result=stmt.executeUpdate(sql);//sql 실행
					if (result==1) {
						System.out.println("추가 성공");
					}else {
						System.out.println("추가 실패");
					}
					rs = stmt.executeQuery("select * from gamescore");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		preButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PreBtn");
				
				try {
					rs.previous();
					gno.setText(rs.getInt(1) + "");
					name.setText(rs.getString(2));
					score.setText(rs.getInt(3)+"");
					ptime.setText(rs.getString(4));
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("nextBtn");
				try {
					rs.next();
				
//					gno.setText(rs.getInt("gno") + "");
					gno.setText(rs.getInt(1) + "");
					name.setText(rs.getString(2));
					score.setText(rs.getInt(3)+"");
					ptime.setText(rs.getString(4));
					

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setSize(350, 200);
		setVisible(true);
	}

	private static Connection dbConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String pass = "123456";
		Connection con = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("연결성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}

public class FrameSelect2 {
	public static void main(String[] args) throws SQLException {
		new MyFrame2();
	}

}
