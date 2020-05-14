package mysystem;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class BookSaleSystem {
	public BookSaleSystem()
	{
		new Login();
	}
	public static void main(String []argus){
		new BookSaleSystem();
	}
}


class Login extends JFrame implements ActionListener//登录类
{
	private static final long serialVersionUID = -8584651599658137745L;
	JButton b1=new JButton("确定");
	JButton b2=new JButton("取消");
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JPanel p3= new JPanel();
	JLabel l1=new JLabel("用户名");
	JLabel l2=new JLabel("密码");
	JTextField name= new JTextField(15);;
	JPasswordField pw= new JPasswordField(15);
	static String JDriver="com.hxtt.sql.access.AccessDriver";
	static String conURL="jdbc:Access:///f:/java-neon/workplace/BookSaleSystem/BookSaleSystem.mdb";
	public Login()
	{
		super("图书销售管理系统");
		this.setLayout(new GridLayout(3,1));
		p1.add(l1);p1.add(name);
		p2.add(l2);p2.add(pw);
		p3.add(b1);p3.add(b2);
		add(p1);add(p2);add(p3);
		setSize(400, 200);
		setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e)
	{
		int flag=1;
		try {
			Class.forName(JDriver);
		}
		catch(java.lang.ClassNotFoundException e1) {
			System.out.println("ForName :" + e1.getMessage( ));
		}
		String s1,s2;
		if(e.getSource()==b1)
		{
			s1=name.getText();
			s2=new String(pw.getPassword());
			try{
				Connection con=DriverManager.getConnection(conURL);
				Statement s=con.createStatement();
				ResultSet rs=s.executeQuery("select * from user");
				while(rs.next()){
					if(s1.equals(rs.getString("user_id"))&&s2.equals(rs.getString("password")))
					{
						JOptionPane.showMessageDialog(null,"登录成功");
						this.setVisible(false);
						new Mainmenu(1);
					}
					else
					{
						flag=0;
						continue;
					}
				}
				if(flag==0)
					JOptionPane.showMessageDialog(null,"用户名或密码错误");

			}catch(SQLException e1) {
				System.out.println("SQLException: " +e1.getMessage( ));
			}
		}
		else
			System.exit(0);
	}
}
class Mainmenu extends JFrame implements ActionListener//主界面
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JMenuBar mb=new JMenuBar();
	JMenu m1=new JMenu("表单");
	JMenu m2=new JMenu("关于");
	JMenu m3=new JMenu("生成报表");
	JMenuItem m11=new JMenuItem("商品信息");
	JMenuItem m12=new JMenuItem("客户订单");
	JMenuItem m13=new JMenuItem("客户信息");
	JMenuItem m14=new JMenuItem("厂商信息");
	JMenuItem m21=new JMenuItem("About");
	JMenuItem m31=new JMenuItem("导出为Excel");

	static String []s1={"书名","作者","出版社","价格","ISBN","库存"};
	static String []s2={"书名","作者","出版社","价格","数量","总价","客户","下单时间"};
	static String []s3={"姓名","地址","电话","邮箱"};
	static String []s4={"出版社","联系人","电话","地址","邮箱","邮编"};
	DefaultTableModel model1 =new Showinfo(1,s1).getModel();
	DefaultTableModel model2 =new Showinfo(2,s2).getModel();
	DefaultTableModel model3 =new Showinfo(3,s3).getModel();
	DefaultTableModel model4 =new Showinfo(4,s4).getModel();


	InfoTable table1=new InfoTable(s1,model1);
	InfoTable table2=new InfoTable(s2,model2);
	InfoTable table3=new InfoTable(s3,model3);
	InfoTable table4=new InfoTable(s4,model4);
	CardLayout card=new CardLayout();

	int flag;
	public Mainmenu(int i){
		this.flag=i;
		this.setTitle("图书销售管理系统");
		this.setVisible(true);
		this.setSize(1000, 500);
		this.setJMenuBar(mb);
		mb.add(m3);mb.add(m1);mb.add(m2);
		m1.add(m11);m1.add(m12);m1.add(m13);m1.add(m14);
		m2.add(m21);m3.add(m31);		
		setLayout(card);
		add(table1,"bookinfo");
		add(table2,"userorder");
		add(table3,"userinfo");
		add(table4,"pressinfo");
		m11.addActionListener(this);
		m12.addActionListener(this);
		m13.addActionListener(this);
		m14.addActionListener(this);
		m31.addActionListener(this);
		if(i==1)
		{
			card.show(table1.getParent(), "bookinfo");
			this.flag=1;
		}
		else if(i==2)
		{
			table2.flag(2);
			card.show(table1.getParent(), "userorder");

		}
		else if(i==3)
		{
			table3.flag(3);
			card.show(table1.getParent(), "userinfo");

		}
		else if(i==4)
		{
			table3.flag(4);
			card.show(table1.getParent(), "pressinfo");

		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==m11)
		{	
			card.show(table1.getParent(), "bookinfo");
			this.flag=1;
		}
		else if(e.getSource()==m12)
		{
			table2.flag(2);
			card.show(table1.getParent(), "userorder");
			this.flag=2;
		}
		else if(e.getSource()==m13)
		{
			table3.flag(3);
			card.show(table1.getParent(), "userinfo");
			this.flag=3;
		}
		else if(e.getSource()==m14)
		{
			table4.flag(4);
			card.show(table1.getParent(), "pressinfo");
			this.flag=4;
		}
		else if(e.getSource()==m31)
		{ 
			if(flag==1)
			{
				FileDialog fd = new FileDialog(this, "生成报表", FileDialog.SAVE);
				fd.setLocation(400, 250);
				fd.setVisible(true);  
				String stringfile = fd.getDirectory()+fd.getFile()+".xls";  
				try {
					table1.exportTable(table1.t1, new File(stringfile));
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
			if(flag==2)
			{
				FileDialog fd = new FileDialog(this, "生成报表", FileDialog.SAVE);
				fd.setLocation(400, 250);
				fd.setVisible(true);  
				String stringfile = fd.getDirectory()+fd.getFile()+".xls";  
				try {
					table2.exportTable(table2.t1, new File(stringfile));
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
			if(flag==3)
			{
				FileDialog fd = new FileDialog(this, "生成报表", FileDialog.SAVE);
				fd.setLocation(400, 250);
				fd.setVisible(true);  
				String stringfile = fd.getDirectory()+fd.getFile()+".xls";  
				try {
					table3.exportTable(table3.t1, new File(stringfile));
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
			if(flag==4)
			{
				FileDialog fd = new FileDialog(this, "生成报表", FileDialog.SAVE);
				fd.setLocation(400, 250);
				fd.setVisible(true);  
				String stringfile = fd.getDirectory()+fd.getFile()+".xls";  
				try {
					table3.exportTable(table4.t1, new File(stringfile));
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}

		}

	}
}
class InfoTable extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton b1=new JButton("增加");
	JButton b2=new JButton("删除");
	JButton b3=new JButton("修改");
	JButton b4=new JButton("查询");
	JButton b5=new JButton("返回当前表单");

	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JPanel p3=new JPanel();
	JTable t1=new JTable();
	int flag=1,rownumber=1;
	String []data=new String[6];
	String ID,bookName,author,press,price,ISBN,stock;
	DefaultTableModel model=new DefaultTableModel();
	/**导出JTable到excel */

	public InfoTable(String []s,DefaultTableModel model){
		this.model=model;
		this.setLayout(new BorderLayout());
		t1.setModel(this.model);

		t1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		t1.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(t1);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll);this.add(p2,"South");
		p2.setLayout(new GridLayout(1,6,25,25));
		p2.add(p1);	p2.add(b1);p2.add(b2);
		p2.add(b3);p2.add(b4);p2.add(b5);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);

	}
	Object [][]book={};
	int count;
	public void exportTable(JTable table, File file) throws IOException {
		TableModel model = table.getModel();
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));  
		for(int i=0; i < model.getColumnCount(); i++) {
			bWriter.write(model.getColumnName(i));
			bWriter.write("\t");
		}
		bWriter.newLine();
		for(int i=0; i< model.getRowCount(); i++) {
			for(int j=0; j < model.getColumnCount(); j++) {
				bWriter.write(model.getValueAt(i,j).toString());
				bWriter.write("\t");
			}
			bWriter.newLine();
		}
		bWriter.close();

	}
	public DefaultTableModel showinfo(int flag,String []a){
		model= new DefaultTableModel(book,a);
		try {
			Class.forName(Login.JDriver);
		}
		catch(java.lang.ClassNotFoundException e1) {
			System.out.println("ForName :" + e1.getMessage( ));
		}

		if (flag==1)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from bookinfo";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][6];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[count][0]=rs.getString("bookName");
					bookInfo[count][1]=rs.getString("author");
					bookInfo[count][2]=rs.getString("press");
					bookInfo[count][3]=rs.getString("price");
					bookInfo[count][4]=rs.getString("ISBN");
					bookInfo[count][5]=rs.getString("stock");
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==2)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from userorders";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][8];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{

					bookInfo[count][0]=rs.getString("bookName");
					bookInfo[count][1]=rs.getString("author");
					bookInfo[count][2]=rs.getString("press");
					bookInfo[count][3]=rs.getString("price");
					bookInfo[count][4]=rs.getString("number");
					bookInfo[count][5]=rs.getString("total");
					bookInfo[count][6]=rs.getString("costume");
					bookInfo[count][7]=rs.getString("time");
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==3)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from userinfo";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][4];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[count][0]=rs.getString("name");
					bookInfo[count][1]=rs.getString("address");
					bookInfo[count][2]=rs.getString("phone");
					bookInfo[count][3]=rs.getString("email");
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}

			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==4)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from pressinfo";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}

				Object[][]  bookInfo = new Object[count][6];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[count][0]=rs.getString("press");
					bookInfo[count][1]=rs.getString("negotiator");
					bookInfo[count][2]=rs.getString("phone");
					bookInfo[count][3]=rs.getString("address");
					bookInfo[count][4]=rs.getString("email");
					bookInfo[count][5]=rs.getString("postcode");
					model.addRow(bookInfo[count]);
					count++;
				}
				rs.close();
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		return model;

	}
	public int flag(int i)
	{
		this.flag=i;
		return i;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (flag==1)
		{
			if(e.getSource()==b1)
			{

				Addinfo addinfo=new Addinfo(flag);
				addinfo.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=showinfo(flag,Mainmenu.s1);

						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b2)
			{
				String []data=new String[6];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					for(int i=0;i<6;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);


					String r1="delete from bookinfo where bookName='"+data[0]+"' and author='"+data[1]+"' and press='"+data[2]+"' and price='"+data[3]+"' and ISBN='"+data[4]+"' and stock='"+data[5]+"'";
					s.executeUpdate(r1);


					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException1: " +ex.getMessage( )); 
				}
				model=showinfo(flag,Mainmenu.s1);

				t1.setModel(model);

			}
			else if(e.getSource()==b3)
			{
				String []data=new String[6];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 

					for(int i=0;i<6;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);
					String r1="update bookinfo set bookName='change' where bookName='"+data[0]+"' and author='"+data[1]+"' and press='"+data[2]+"' and price='"+data[3]+"' and ISBN='"+data[4]+"' and stock='"+data[5]+"'";
					s.executeUpdate(r1);

					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException2: " +ex.getMessage( )); 
				}

				changeinfo change=new changeinfo(flag, data);
				change.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=showinfo(flag,Mainmenu.s1);
						t1.setModel(model);
					}
				});

			}
			else if(e.getSource()==b4)
			{
				Search search=new Search(flag);
				search.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=search.getModel();
						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b5)
			{
				model=showinfo(flag,Mainmenu.s1);

				t1.setModel(model);
			}

		}
		else if (flag==2)
		{
			if(e.getSource()==b1)
			{
				Addinfo addinfo=new Addinfo(flag);
				addinfo.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){

						model=showinfo(flag,Mainmenu.s2);
						t1.setModel(model);
					}
				});


			}
			else if(e.getSource()==b2)
			{
				String []data=new String[8];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					for(int i=0;i<8;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);

					System.out.println(data[4]);
					String r1="delete from userorders where bookName='"+data[0]+"' and author='"+data[1]+"' "
							+ "and press='"+data[2]+"'and price='"+data[3]+"'and number='"+data[4]+"'"
							+ "and total='"+data[5]+"'and costume='"+data[6]+"'and time='"+data[7]+"'";
					s.executeUpdate(r1);


					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException1: " +ex.getMessage( )); 
				}
				model=showinfo(flag,Mainmenu.s2);

				t1.setModel(model);


			}
			else if(e.getSource()==b3)
			{
				String []data=new String[8];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 

					for(int i=0;i<8;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);

					String r1="update userorders set bookName='change' where bookName='"+data[0]+"' and "
							+ "author='"+data[1]+"'and press='"+data[2]+"' and price='"+data[3]+"'"
							+ " and number='"+data[4]+"' and total='"+data[5]+"'"
							+ "and costume='"+data[6]+"'and time='"+data[7]+"'";
					s.executeUpdate(r1);

					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException2: " +ex.getMessage( )); 
				}

				changeinfo change=new changeinfo(flag, data);
				change.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=showinfo(flag,Mainmenu.s2);
						t1.setModel(model);
					}
				});

			}
			else if(e.getSource()==b4)
			{
				Search search=new Search(flag);
				search.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=search.getModel();
						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b5)
			{
				model=showinfo(flag,Mainmenu.s2);

				t1.setModel(model);
			}
		}
		else if (flag==3)
		{
			if(e.getSource()==b1)
			{

				Addinfo addinfo=new Addinfo(flag);
				addinfo.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){

						model=showinfo(flag,Mainmenu.s3);
						t1.setModel(model);
					}
				});

			}
			else if(e.getSource()==b2)
			{
				String []data=new String[4];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					for(int i=0;i<4;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);


					String r1="delete from userinfo where name='"+data[0]+"' and address='"+data[1]+"' and phone='"+data[2]+"' and email='"+data[3]+"'";
					s.executeUpdate(r1);


					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException1: " +ex.getMessage( )); 
				}
				model=showinfo(flag,Mainmenu.s3);

				t1.setModel(model);

			}
			else if(e.getSource()==b3)
			{
				String []data=new String[4];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					for(int i=0;i<4;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);



					String r1="update userinfo set name='change' where name='"+data[0]+"'and address='"+data[1]+"'"
							+ "and phone='"+data[2]+"'and email='"+data[3]+"'";

					s.executeUpdate(r1);


					s.close( );
					con.close( );

				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException5: " +ex.getMessage( )); 
				}
				changeinfo change=new changeinfo(flag, data);
				change.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=showinfo(flag,Mainmenu.s3);
						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b4)
			{

				Search search=new Search(flag);
				search.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=search.getModel();
						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b5)
			{
				model=showinfo(flag,Mainmenu.s3);

				t1.setModel(model);
			}
		}
		else if(flag==4)
		{
			if(e.getSource()==b1)
			{

				Addinfo addinfo=new Addinfo(flag);
				addinfo.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){

						model=showinfo(flag,Mainmenu.s4);
						t1.setModel(model);
					}
				});

			}
			else if(e.getSource()==b2)
			{
				String []data=new String[6];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					for(int i=0;i<6;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);


					String r1="delete from pressinfo where press='"+data[0]+"' and negotiator='"+data[1]+"'"
							+ " and phone='"+data[2]+"' and address='"+data[3]+"'and email='"+data[4]+"'"
							+ "and postcode='"+data[5]+"'";
					s.executeUpdate(r1);


					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException1: " +ex.getMessage( )); 
				}
				model=showinfo(flag,Mainmenu.s4);

				t1.setModel(model);

			}
			else if(e.getSource()==b3)
			{
				String []data=new String[6];
				int rowNumber = t1.getSelectedRow();

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					for(int i=0;i<6;i++)
						data[i]=(String) model.getValueAt(rowNumber, i);



					String r1="update pressinfo set press='change' where press='"+data[0]+"' and negotiator='"+data[1]+"'"
							+ " and phone='"+data[2]+"' and address='"+data[3]+"'and email='"+data[4]+"'"
							+ "and postcode='"+data[5]+"'";

					s.executeUpdate(r1);


					s.close( );
					con.close( );

				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException5: " +ex.getMessage( )); 
				}
				changeinfo change=new changeinfo(flag, data);
				change.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=showinfo(flag,Mainmenu.s4);
						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b4)
			{

				Search search=new Search(flag);
				search.addWindowListener(new WindowAdapter(){
					public void windowClosed(WindowEvent e){
						model=search.getModel();
						t1.setModel(model);
					}
				});
			}
			else if(e.getSource()==b5)
			{
				model=showinfo(flag,Mainmenu.s4);

				t1.setModel(model);
			}

		}
	}

}
class Showinfo{
	Object [][]book={};
	int count;
	DefaultTableModel  model;
	public DefaultTableModel getModel()
	{
		return model;

	}
	public Showinfo(int flag,String []a)
	{
		model= new DefaultTableModel(book,a);
		try {
			Class.forName(Login.JDriver);
		}
		catch(java.lang.ClassNotFoundException e1) {
			System.out.println("ForName :" + e1.getMessage( ));
		}

		if (flag==1)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from bookinfo";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][6];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					//bookInfo[count][0]=rs.getString("ID");
					bookInfo[count][0]=rs.getString("bookName") ;
					bookInfo[count][1]=rs.getString("author") ;
					bookInfo[count][2]=rs.getString("press") ;
					bookInfo[count][3]=rs.getString("price") ;
					bookInfo[count][4]=rs.getString("ISBN") ;
					bookInfo[count][5]=rs.getString("stock") ;
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==2)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from userorders";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][8];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					//bookInfo[count][0]=rs.getString("ID");
					bookInfo[count][0]=rs.getString("bookName");
					bookInfo[count][1]=rs.getString("author");
					bookInfo[count][2]=rs.getString("press");
					bookInfo[count][3]=rs.getString("price");
					bookInfo[count][4]=rs.getString("number");
					bookInfo[count][5]=rs.getString("total");
					bookInfo[count][6]=rs.getString("costume");
					bookInfo[count][7]=rs.getString("time");
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==3)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from userinfo";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][4];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					//bookInfo[count][0]=rs.getString("ID");
					bookInfo[count][0]=rs.getString("name");
					bookInfo[count][1]=rs.getString("address");
					bookInfo[count][2]=rs.getString("phone");
					bookInfo[count][3]=rs.getString("email");
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==4)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from pressinfo";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				Object[][]  bookInfo = new Object[count][6];
				count = 0;
				model.setRowCount(0);
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[count][0]=rs.getString("press");
					bookInfo[count][1]=rs.getString("negotiator");
					bookInfo[count][2]=rs.getString("phone");
					bookInfo[count][3]=rs.getString("address");
					bookInfo[count][4]=rs.getString("email");
					bookInfo[count][5]=rs.getString("postcode");
					model.addRow(bookInfo[count]);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
	}
}

class Addinfo extends JFrame implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton b1=new JButton("确定");
	JButton b2=new JButton("取消");
	JPanel p1,p2,p3,p4,p5;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8;
	String []data1=new String[7];
	String ID,bookName,author,press,price,ISBN,stock;
	int i=1;
	int count;
	String number;
	double total;
	public Addinfo(int flag)
	{
		this.i=flag;
		p1=new JPanel();p2=new JPanel();p3=new JPanel();p4=new JPanel();p5=new JPanel();
		l1=new JLabel("书名");l2=new JLabel("作者");l3=new JLabel("出版社");l4=new JLabel("价格");
		l5=new JLabel("ISBN");l6=new JLabel("库存");l7=new JLabel("姓名");l8=new JLabel("地址");
		l9=new JLabel("电话");l10=new JLabel("邮箱");l11=new JLabel("数量");l12=new JLabel("总价");
		l13=new JLabel("客户");l14=new JLabel("下单时间");
		t1=new JTextField(20);t2=new JTextField(20);t3=new JTextField(20);t4=new JTextField(20);
		t5=new JTextField(20);t6=new JTextField(20);t7=new JTextField(20);t8=new JTextField(20);
		this.setSize(600, 400);

		if(flag==1)
		{
			this.setTitle("添加商品信息");

			this.setLayout(new GridLayout(4,1));
			add(p1);add(p2);add(p3);add(p4);
			p1.add(l1);p1.add(t1);p1.add(l2);p1.add(t2);
			p2.add(l3);p2.add(t3);p2.add(l4);p2.add(t4);
			p3.add(l5);p3.add(t5);p3.add(l6);p3.add(t6);
			p4.add(b1);p4.add(b2);
		}
		if(flag==2)
		{
			this.setTitle("添加客户订单");

			this.setLayout(new GridLayout(5,1));
			add(p1);add(p2);add(p3);add(p4);add(p5);
			p1.add(l1);p1.add(t1);p1.add(l2);p1.add(t2);
			p2.add(l3);p2.add(t3);p2.add(l4);p2.add(t4);
			p3.add(l11);p3.add(t5);p3.add(l12);p3.add(t6);
			p4.add(l13);p4.add(t7);p4.add(l14);p4.add(t8);
			p5.add(b1);p5.add(b2);
		}
		if(flag==3)
		{
			this.setTitle("添加客户信息");
			this.setLayout(new GridLayout(3,1));
			add(p1);add(p2);add(p3);
			p1.add(l7);p1.add(t1);p1.add(l8);p1.add(t2);
			p2.add(l9);p2.add(t3);p2.add(l10);p2.add(t4);
			p3.add(b1);p3.add(b2);
		}
		if(flag==4)
		{
			this.setTitle("添加厂商信息");
			this.setLayout(new GridLayout(4,1));
			add(p1);add(p2);add(p3);add(p4);
			l1.setText(Mainmenu.s4[0]);l2.setText(Mainmenu.s4[1]);
			l3.setText(Mainmenu.s4[2]);l5.setText(Mainmenu.s4[3]);
			l5.setText(Mainmenu.s4[4]);l6.setText(Mainmenu.s4[5]);
			p1.add(l1);p1.add(t1);p1.add(l2);p1.add(t2);
			p2.add(l3);p2.add(t3);p2.add(l4);p2.add(t4);
			p3.add(l5);p3.add(t5);p3.add(l6);p3.add(t6);
			p4.add(b1);p4.add(b2);
		}
		this.setVisible(true);
		b1.addActionListener(this);b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2)
			dispose();
		else if(i==1)
		{

			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 


				bookName=t1.getText() ;
				author=t2.getText() ;
				press=t3.getText() ;
				price=t4.getText() ;
				ISBN=t5.getText() ;
				stock=t6.getText() ;
				String r2="insert into bookinfo values('"+bookName+"','"
						+author+"','"+press +"','"+price+"','"+ISBN +"','"+stock+"')";

				s.executeUpdate(r2);
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			dispose();

		}
		else if(i==2)
		{

			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 

				String r1="select * from userorders";
				ResultSet rs=s.executeQuery(r1);

				bookName=t1.getText();
				author=t2.getText();
				press=t3.getText();
				price=t4.getText();
				number=t5.getText();
				total=Double.parseDouble(price)*Integer.parseInt(number);
				String costume=t7.getText();
				LocalDate date=LocalDate.now();
				String time=date.toString();
				
				
						String r2="insert into userorders values('"+bookName+"','"
						+author+"','"+press +"','"+price+"','"+number +"','"+total+"','"+costume+"','"+time+"')";
						s.executeUpdate(r2);
						
				
				
				rs.close();
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			dispose();	
		}
		else if(i==3)
		{

			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 

				String r1="select * from userinfo";
				ResultSet rs=s.executeQuery(r1);

				String name = t1.getText();
				String address=t2.getText();
				String phone=t3.getText();
				String email=t4.getText();
				String r2="insert into userinfo values('"+name+"','"
						+address+"','"+phone+"','"+email+"')";

				s.executeUpdate(r2);
				rs.close();
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			dispose();	
		}
		else if(i==4)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String press = t1.getText();
				String negotiator=t2.getText();
				String phone=t3.getText();
				String address=t4.getText();
				String email=t5.getText();
				String postcode=t6.getText();
				String r2="insert into pressinfo values('"+press+"','"
						+negotiator+"','"+phone+"','"+address+"'"
						+ ",'"+email+"','"+postcode+"')";

				s.executeUpdate(r2);

				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			dispose();	
		}
	}
}
class changeinfo extends JFrame implements ActionListener
{


	private static final long serialVersionUID = 1L;

	JButton b1=new JButton("确定");
	JButton b2=new JButton("取消");
	JPanel p1,p2,p3,p4,p5;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8;
	String []data=new String[10];
	String ID,bookName,author,press,price,ISBN,stock;
	int i=1;
	int count;
	String number;
	double total;
	public changeinfo(int flag,String []date)
	{

		for(int i=0;i<date.length;i++)
		{this.data[i]=date[i];}
		this.i=flag;
		p1=new JPanel();p2=new JPanel();p3=new JPanel();p4=new JPanel();p5=new JPanel();
		l1=new JLabel("书名");l2=new JLabel("作者");l3=new JLabel("出版社");l4=new JLabel("价格");
		l5=new JLabel("ISBN");l6=new JLabel("库存");l7=new JLabel("姓名");l8=new JLabel("地址");
		l9=new JLabel("电话");l10=new JLabel("邮箱");l11=new JLabel("数量");l12=new JLabel("总价");
		l13=new JLabel("客户");l14=new JLabel("下单时间");
		t1=new JTextField(20);t2=new JTextField(20);t3=new JTextField(20);t4=new JTextField(20);
		t5=new JTextField(20);t6=new JTextField(20);t7=new JTextField(20);t8=new JTextField(20);
		this.setSize(600, 400);

		if(flag==1)
		{
			this.setTitle("修改商品信息");

			this.setLayout(new GridLayout(4,1));
			add(p1);add(p2);add(p3);add(p4);
			p1.add(l1);p1.add(t1);p1.add(l2);p1.add(t2);
			p2.add(l3);p2.add(t3);p2.add(l4);p2.add(t4);
			p3.add(l5);p3.add(t5);p3.add(l6);p3.add(t6);
			p4.add(b1);p4.add(b2);
			t1.setText(date[0]);t2.setText(date[1]);t3.setText(date[2]);
			t4.setText(date[3]);t5.setText(date[4]);t6.setText(date[5]);
		}
		if(flag==2)
		{
			this.setTitle("修改客户订单");

			this.setLayout(new GridLayout(5,1));
			add(p1);add(p2);add(p3);add(p4);add(p5);
			p1.add(l1);p1.add(t1);p1.add(l2);p1.add(t2);
			p2.add(l3);p2.add(t3);p2.add(l4);p2.add(t4);
			p3.add(l11);p3.add(t5);p3.add(l12);p3.add(t6);
			p4.add(l13);p4.add(t7);p4.add(l14);p4.add(t8);
			p5.add(b1);p5.add(b2);
			t1.setText(date[0]);t2.setText(date[1]);t3.setText(date[2]);
			t4.setText(date[3]);t5.setText(date[4]);t6.setText(date[5]);
			t7.setText(date[6]);t8.setText(date[7]);

		}
		if(flag==3)
		{
			this.setTitle("修改客户信息");
			this.setLayout(new GridLayout(3,1));
			add(p1);add(p2);add(p3);
			p1.add(l7);p1.add(t1);p1.add(l8);p1.add(t2);
			p2.add(l9);p2.add(t3);p2.add(l10);p2.add(t4);
			p3.add(b1);p3.add(b2);
			t1.setText(date[0]);t2.setText(date[1]);t3.setText(date[2]);
			t4.setText(date[3]);
		}
		if(flag==4)
		{
			this.setTitle("修改厂商信息");
			this.setLayout(new GridLayout(4,1));
			add(p1);add(p2);add(p3);add(p4);
			l1.setText(Mainmenu.s4[0]);l2.setText(Mainmenu.s4[1]);
			l3.setText(Mainmenu.s4[2]);l5.setText(Mainmenu.s4[3]);
			l5.setText(Mainmenu.s4[4]);l6.setText(Mainmenu.s4[5]);
			p1.add(l1);p1.add(t1);p1.add(l2);p1.add(t2);
			p2.add(l3);p2.add(t3);p2.add(l4);p2.add(t4);
			p3.add(l5);p3.add(t5);p3.add(l6);p3.add(t6);
			t1.setText(date[0]);t2.setText(date[1]);t3.setText(date[2]);
			t4.setText(date[3]);t5.setText(date[4]);t6.setText(date[5]);
			p4.add(b1);p4.add(b2);
		}
		this.setVisible(true);
		b1.addActionListener(this);b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2)
		{
			if(i==1)
			{
				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 
					String[] data=new String[6];

					data[0]=t1.getText();
					data[1]=t2.getText();
					data[2]=t3.getText();
					data[3]=t4.getText();
					data[4]=t5.getText();
					data[5]=t6.getText();

					String r1="update bookinfo set bookName='"+data[0]+"',author='"+data[1]+"',press='"+data[2]+"',price='"+data[3]+"',ISBN='"+data[4]+"',stock='"+data[5]+"' where bookName='change'";
					s.executeUpdate(r1);

					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException1: " +ex.getMessage( )); 
				}

			}
			if(i==2)
			{

				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 



					bookName=t1.getText();
					author=t2.getText();
					press=t3.getText();
					price=t4.getText();
					number=t5.getText();
					total=Double.parseDouble(price)*Integer.parseInt(number);
					String costume=t7.getText();
					String time=t8.getText();
					String r="update userorders set bookName='"+bookName+"',author='"+author+"'"
							+ ",press='"+press+"',price='"+price+"',number='"+number+"',"
							+ "total='"+total+"',costume='"+costume+"'"
							+ ",time='"+time+"' where bookName='change'";
					s.executeUpdate(r);

					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException: " +ex.getMessage( )); 
				}
			}
			if(i==3)
			{
				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 


					String name = t1.getText();
					String address=t2.getText();
					String phone=t3.getText();
					String email=t4.getText();
					String r="update userinfo set name='"+name+"',address ='"+address+"',phone='"+phone+"'"
							+ ",email='"+email+"'where name='change'";
					s.executeUpdate(r);

					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException: " +ex.getMessage( )); 
				}
			}
			if(i==4)
			{
				try 
				{
					Connection con=DriverManager.getConnection(Login.conURL); 
					Statement s=con.createStatement( ); 

					String r="update pressinfo set press='"+data[0]+"',negotiator ='"+data[1]+"',phone='"+data[2]+"'"
							+ ",address='"+data[3]+"',email='"+data[4]+"',postcode='"+data[5]+"'where press='change'";
					s.executeUpdate(r);

					s.close( );  
					con.close( );  
				}
				catch(SQLException ex)
				{ 
					System.out.println("SQLException: " +ex.getMessage( )); 
				}
			}
			dispose();
		}
		else if(i==1)
		{

			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String[] data=new String[6];

				data[0]=t1.getText();
				data[1]=t2.getText();
				data[2]=t3.getText();
				data[3]=t4.getText();
				data[4]=t5.getText();
				data[5]=t6.getText();

				String r1="update bookinfo set bookName='"+data[0]+"',author='"+data[1]+"'"
						+ ",press='"+data[2]+"',price='"+data[3]+"',"
						+ "ISBN='"+data[4]+"',stock='"+data[5]+"' where bookName='change'";
				s.executeUpdate(r1);

				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException1: " +ex.getMessage( )); 
			}
			dispose();

		}
		else if(i==2)
		{

			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 



				bookName=t1.getText();
				author=t2.getText();
				press=t3.getText();
				price=t4.getText();
				number=t5.getText();
				total=Double.parseDouble(price)*Integer.parseInt(number);
				String costume=t7.getText();
				//LocalDate date=LocalDate.now();
				String time=t8.getText();
				String r="update userorders set bookName='"+bookName+"',author='"+author+"'"
						+ ",press='"+press+"',price='"+price+"',number='"+number+"',"
						+ "total='"+total+"',costume='"+costume+"'"
						+ ",time='"+time+"' where bookName='change'";
				s.executeUpdate(r);

				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			catch(NumberFormatException exx)
			{
				System.out.println("请输入数字");
			}
			dispose();	
		}
		else if(i==3)
		{

			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 


				String name = t1.getText();
				String address=t2.getText();
				String phone=t3.getText();
				String email=t4.getText();
				String r="update userinfo set name='"+name+"',address ='"+address+"',phone='"+phone+"'"
						+ ",email='"+email+"'where name='change'";
				s.executeUpdate(r);

				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			dispose();	
		}
		else if(i==4)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String[] data=new String[6];

				data[0]=t1.getText();
				data[1]=t2.getText();
				data[2]=t3.getText();
				data[3]=t4.getText();
				data[4]=t5.getText();
				data[5]=t6.getText();

				String r="update pressinfo set press='"+data[0]+"',negotiator ='"+data[1]+"',phone='"+data[2]+"'"
						+ ",address='"+data[3]+"',email='"+data[4]+"',postcode='"+data[5]+"'where press='change'";
				s.executeUpdate(r);

				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
			dispose();
		}
	}
}

class Search extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel l= new JLabel("查询当前表单");
	JTextField t=new JTextField(15);
	JButton b1=new JButton("确定");
	JButton b2=new JButton("取消");
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	DefaultTableModel model=new DefaultTableModel();
	Object [][]book={};
	int count;
	int flag;
	public Search(int flag)
	{
		super("查询");
		this.flag=flag;
		this.setSize(400, 200);
		this.setVisible(true);
		this.setLayout(new GridLayout(2,1));
		add(p1);add(p2);
		p1.add(l);p1.add(t);t.setText("请输入关键字");
		p2.add(b1);p2.add(b2);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	public DefaultTableModel getModel()
	{
		return model;

	}
	public DefaultTableModel showinfo(String []a,String aa){
		model= new DefaultTableModel(book,a);
		try {
			Class.forName(Login.JDriver);
		}
		catch(java.lang.ClassNotFoundException e1) {
			System.out.println("ForName :" + e1.getMessage( ));
		}

		if (flag==1)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from bookinfo where bookName like '%"+aa+"%'or author like '%"+aa+"%'"
						+ "or price like '%"+aa+"%' or ISBN like '%"+aa+"%' or stock like '%"+aa+"%'"
						+ "or press like '%"+aa+"%'";
				ResultSet rs=s.executeQuery(r1);

				String[]  bookInfo = new String[6];
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[0]=rs.getString("bookName");
					bookInfo[1]=rs.getString("author");
					bookInfo[2]=rs.getString("press");
					bookInfo[3]=rs.getString("price");
					bookInfo[4]=rs.getString("ISBN");
					bookInfo[5]=rs.getString("stock");
					model.addRow(bookInfo);

				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==2)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from userorders where bookName like '%"+aa+"%'or author like '%"+aa+"%'"
						+ "or price like '%"+aa+"%' or number like '%"+aa+"%' or total like '%"+aa+"%'"
						+ "or press like '%"+aa+"%'or costume like '%"+aa+"%'or time like '%"+aa+"%'";
				ResultSet rs=s.executeQuery(r1);

				String []  bookInfo = new String[8];

				rs=s.executeQuery(r1);
				while(rs.next())
				{

					bookInfo[0]=rs.getString("bookName");
					bookInfo[1]=rs.getString("author");
					bookInfo[2]=rs.getString("press");
					bookInfo[3]=rs.getString("price");
					bookInfo[4]=rs.getString("number");
					bookInfo[5]=rs.getString("total");
					bookInfo[6]=rs.getString("costume");
					bookInfo[7]=rs.getString("time");
					model.addRow(bookInfo);
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==3)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from userinfo where name like '%"+aa+"%'or address like '%"+aa+"%'"
						+ "or phone like '%"+aa+"%' or email like '%"+aa+"%'";
				ResultSet rs=s.executeQuery(r1);
				count = 0;	
				while(rs.next())
				{
					count++;
				}
				String[]  bookInfo = new String[4];
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[0]=rs.getString("name");
					bookInfo[1]=rs.getString("address");
					bookInfo[2]=rs.getString("phone");
					bookInfo[3]=rs.getString("email");
					model.addRow(bookInfo);
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		if (flag==4)
		{
			try 
			{
				Connection con=DriverManager.getConnection(Login.conURL); 
				Statement s=con.createStatement( ); 
				String r1="select * from pressinfo where press like '%"+aa+"%'or negotiator like '%"+aa+"%'"
						+ "or phone like '%"+aa+"%' or address like '%"+aa+"%' or email like '%"+aa+"%'"
								+ "or postcode like '%"+aa+"%'";
				ResultSet rs=s.executeQuery(r1);
				
				String[]  bookInfo = new String[6];
				
				rs=s.executeQuery(r1);
				while(rs.next())
				{
					bookInfo[0]=rs.getString("press");
					bookInfo[1]=rs.getString("negotiator");
					bookInfo[2]=rs.getString("phone");
					bookInfo[3]=rs.getString("address");
					bookInfo[4]=rs.getString("email");
					bookInfo[5]=rs.getString("postcode");
					model.addRow(bookInfo);
					count++;
				}
				s.close( );  
				con.close( );  
			}
			catch(SQLException ex)
			{ 
				System.out.println("SQLException: " +ex.getMessage( )); 
			}
		}
		
		return model;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1)
		{
			String s=t.getText();
			if(flag==1)
			{
				this.model=showinfo(Mainmenu.s1,s);
				dispose();
			}
			if(flag==2)
			{
				this.model=showinfo(Mainmenu.s2,s);
				dispose();
			}
			if(flag==3)
			{
				this.model=showinfo(Mainmenu.s3,s);
				dispose();
			}
			if(flag==4)
			{
				this.model=showinfo(Mainmenu.s4,s);
				dispose();
			}
		}
		else if(e.getSource()==b2)
		{
			if(flag==1)
			{
				this.model=new Showinfo(1,Mainmenu.s1).getModel();
				dispose();
			}
			if(flag==2)
			{
				this.model=new Showinfo(2,Mainmenu.s2).getModel();
				dispose();
			}
			if(flag==3)
			{
				this.model=new Showinfo(3,Mainmenu.s3).getModel();
				dispose();
			}
			if(flag==4)
			{
				this.model=new Showinfo(4,Mainmenu.s4).getModel();
				dispose();
			}


		}
	}
}


