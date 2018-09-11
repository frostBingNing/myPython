package mysql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.TabableView;
import javax.xml.ws.AsyncHandler;

import org.ietf.jgss.Oid;
import org.omg.CORBA.StringHolder;

import com.mysql.jdbc.StreamingNotifiable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException; 
//import java.util.Collection;
//import java.util.Vector;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

// �γ����ý���
public class master extends JFrame implements ActionListener{

	Connection conn ;
	String url = "jdbc:mysql://127.0.0.1:3306/staff";
	// MySQL����ʱ���û���
	String user = "root"; 	     
	// MySQL����ʱ������
	String password = "123456";
	JPanel  left  = new JPanel();
	JPanel  right = new JPanel();
	JFrame loo = new JFrame();
	JFrame back = new JFrame();
	JPanel  back_right = new JPanel();
	Vector vtitle = null;
	JScrollPane scroll = null;
	Vector vinformation = null;
	DefaultTableModel contable = null;
	JTable  table = new JTable(contable);
	// һЩ��Ҫ�� ȫ�ֱ���
	String xname,xteacher,xtime,xbook,xnumber,String ,xadd;
	String xdepart,xsex;
	String xle,xcourse,xstate;
	String staname; // ������¼��ǰ�û����˺���
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
	}
	
    public master() {
		System.out.println("��ӭʹ�ñ�guiϵͳ");
		 // ����������
        String driver = "com.mysql.jdbc.Driver";
		try { 
	            // ������������
			   Class.forName(driver);
	            // �������ݿ�
	          //this.conn = DriverManager.getConnection(url, user, password);
//	          System.out.print("�������سɹ�");
		 }catch(ClassNotFoundException e) {

	            System.out.println("Sorry,can`t find the Driver!"); 
	            e.printStackTrace();
		 }
    }
    
    public static String md5Password(String password) {

        try {
            // �õ�һ����ϢժҪ��
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // ��ÿһ��byte ��һ�������� 0xff;
            for (byte b : result) {
                // ������
                int number = b & 0xff;// ����
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // ��׼��md5���ܺ�Ľ��
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public void new_user_register() {
    	back.setLayout(new BorderLayout());
    	// ��Ҫչʾ����ʽ  ����Ҫ�̳������ȫ�ֱ���
    	registerinfor();
 		 
    	 back_right.setPreferredSize(new Dimension(500,500));
    	 back_right.setLayout(null);
//    	 back_right.setBackground(Color.CYAN);
		 
    	 back.add(back_right,BorderLayout.EAST);
    	 back.setTitle("Ա����ѵ����ϵͳ");
    	 back.setSize(500,500);
    	 back.setVisible(true);
    	 back.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 back.setResizable(false);
    	
    	//���� �ٵ��ע��ʱ�� �ѵ�ǰ�� register ����  Ȼ����ʾ login()
    	
    	// �ṩһ������login()������  ���û������ڲ��˳���״̬��  ע�����Ժ�ֱ�ӵ�¼
    }
    
	public void login() {
		
		//ȡ�����ֹ�����
		//�˺���Ϣ�Ű�
		//  ��Ҫ�������������һ��  ѧԱ Ҳ���� ��ͨ�û���ע�Ṧ��
		JTextField user = new JTextField(30);
		user.setBounds(210,80,80,26);
		JPasswordField pass = new JPasswordField(30);
		pass.setBounds(210,120,80,26);
		// ��¼��Ϣ���� 
		JLabel userName = new JLabel("�û���");
		userName.setBounds(120,80,80,26);
//		userName.setForeground(Color.GREEN);
		userName.setFont(new Font("",Font.ITALIC,20));
		JLabel passWord = new JLabel("����");
		passWord.setBounds(120,120,80,26);
//		passWord.setForeground(Color.GREEN);
		passWord.setFont(new Font("",Font.ITALIC,20));
		// ע��ģ��Ĵ���  // ��¼��ť���Ű�	
		// login --- ��¼   register --- ע��
		JButton register = new JButton("ѧԱע��");
	    register.setBounds(100,190,86,26);
//	    register.setForeground(Color.CYAN);
	    register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loo.setVisible(false);
				new_user_register(); 
			}
		});
	    
		JButton log = new JButton("--��¼--");
		log.setBounds(220,190,86,26);
//		log.setForeground(Color.PINK);
		
		log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // �����߼�������
            	String name=user.getText().trim();
            	String pass1=new String(pass.getPassword());
            	//System.out.print(name + pass1);
            	// ������Ҫ���� ����û�г�����Ϣ ��ʾ��������Ϣ ������
            	con(name,pass1);
            }
        });
		// ��ӵ� JFrame����  ���п��ӻ� ����
		loo.add(log);
		loo.add(userName);
		loo.add(passWord);
		loo.setLayout(null);
		loo.add(user);
		loo.add(pass); loo.add(register);
		loo.setTitle("Ա����ѵ����ϵͳ");
		loo.setSize(400,300);  	//  �������С
//		this.setLocation(500,500); //  ��ʼ��ҳ��λ��
	 	loo.setVisible(true);   	// ���ӻ�����
		loo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �������˳�
		loo.setResizable(false);  	// �����Ը��Ĵ�С
	}
	// ����������
	public void themeToo(){
		  
		this.setLayout(new BorderLayout()); // ͬ�����ó�Ϊ �߽�ģ�� Ȼ���Ϊ����������
		
		JLabel themeToo = new JLabel(" ѧԱ����   ");
		// ������Ӧ�İ�ť
		JButton searchscore = new JButton("�ɼ���ѯ");
		JButton updateinfor = new JButton("��Ϣ�޸�");
		JButton choiceScore = new JButton("��Ҫѡ��");
		searchscore.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				 record();
			 }
		});
		updateinfor.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				inforinForcommon();
			 }
		});
		choiceScore.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				 Choice();
			 }
		});
		// ��ť���Ű�����
		searchscore.setBounds(0,100,100,30);
		searchscore.setForeground(Color.pink);
		searchscore.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		searchscore.setFocusPainted(false);
		searchscore.setBackground(Color.DARK_GRAY);
		searchscore.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 
		updateinfor.setBounds(0,140,100,30);
		updateinfor.setForeground(Color.blue);
		updateinfor.setBackground(Color.getHSBColor(36, 140, 196));
		updateinfor.setFont(new Font("",Font.ROMAN_BASELINE,15));
		updateinfor.setFocusPainted(false);
		 
		choiceScore.setBounds(0,180,100,30);
		choiceScore.setForeground(Color.pink);
		choiceScore.setBackground(Color.DARK_GRAY);
		choiceScore.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		choiceScore.setFont(new Font("",Font.ROMAN_BASELINE,15));	
		choiceScore.setFocusPainted(false);
		 
		 themeToo.setBounds(10,40,200,30);
		 themeToo.setForeground(Color.BLACK);
		 themeToo.setFont(new Font("",Font.ITALIC,14));
		
//		 introduce.setBounds(100,200,100,200);
		 
		 
		 JPanel left = new JPanel();   	
         left.setPreferredSize(new Dimension(110,500));
         left.setBackground(Color.GRAY);  
         left.setLayout(null);
	    	
		 
		 left.add(themeToo);
		 left.add(searchscore);
		 left.add(choiceScore);
	     left.add(updateinfor);
  		 right.setPreferredSize(new Dimension(390,500));
  		 right.setLayout(null);
//  		 right.setBackground(Color.CYAN);
		 
		 this.add(right,BorderLayout.EAST);
		 this.add(left,BorderLayout.WEST);
		 this.setTitle("Ա����ѵ����ϵͳ");
		 this.setSize(500,500);
		 this.setVisible(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setResizable(false);
 	}
	public void theme(){

		 this.setLayout(new BorderLayout()); // ȡ�����ֹ�����  �����û��Զ���ķ�ʽ������   
		 // �����еĹ����������ߵ�panel ����
		 //  ���й���չʾҳ��
		
		 JLabel theme  = new JLabel("  ����Ա����     ");
		 //  �������еĽ��水ť
		 JButton courseSet     = new JButton("�γ�����");
		 JButton searchCourse  = new JButton("ѡ�ν��");
		 JButton numberStu     = new JButton("ѧԱ����");
		 JButton apprise       = new JButton("��ѵ�ɼ�");
		 JButton information   = new JButton("ѧԱ��Ϣ");
		 JButton count  	   = new JButton("��ѵͳ��");
//		 JButton allcourse 	   = new JButton("����γ�");
		 JButton addscore      = new JButton("�ɼ�¼��");
//		 allcourse.addMouseListener(new MouseAdapter() {
//			 @Override
//			 public void mouseClicked(MouseEvent e) {
//				 setcourses();
//			 }
//		});
		 courseSet.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				 courseSet();
			 }
		});
		searchCourse.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				 searchCour();
			 }
		});
		 numberStu.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				 Number();
			 }
		});
		 apprise.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				  appri();
			 }
		});
		 count.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				  Count();
			 }
		});
		information.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				   inforin();
			 }
		});
		 addscore.addMouseListener(new MouseAdapter() {
		 @Override
		 public void mouseClicked(MouseEvent e) {
			   		addscore();
		 }
	});
		 
		 
		 courseSet.setBounds(0,100,100,30);
		 courseSet.setForeground(Color.pink);
		 courseSet.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		 courseSet.setFocusPainted(false);
		 courseSet.setBackground(Color.DARK_GRAY);
		 courseSet.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 
		 searchCourse.setBounds(0,140,100,30);
		 searchCourse.setForeground(Color.blue);
		 searchCourse.setBackground(Color.getHSBColor(36, 140, 196));
		 searchCourse.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 searchCourse.setFocusPainted(false);
		 
		 numberStu.setBounds(0,180,100,30);
		 numberStu.setForeground(Color.pink);
		 numberStu.setBackground(Color.DARK_GRAY);
		 numberStu.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		 numberStu.setFont(new Font("",Font.ROMAN_BASELINE,15));	
		 numberStu.setFocusPainted(false);
		 
		 apprise.setBounds(0,220,100,30);
		 apprise.setForeground(Color.blue);
		 apprise.setBackground(Color.getHSBColor(36, 140, 196));
		 apprise.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 apprise.setFocusPainted(false);
		 
		 count.setBounds(0,260,100,30);
		 count.setForeground(Color.pink);
		 count.setBackground(Color.DARK_GRAY);
		 count.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 count.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		 count.setFocusPainted(false);
		 
		 information.setBounds(0,300,100,30);
		 information.setForeground(Color.blue);
		 information.setBackground(Color.getHSBColor(36, 140, 196));
		 information.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 information.setFocusPainted(false);

		 addscore.setBounds(0,340,100,30);
		 addscore.setForeground(Color.pink);
		 addscore.setBackground(Color.DARK_GRAY);
		 addscore.setFont(new Font("",Font.ROMAN_BASELINE,15));
		 addscore.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		 addscore.setFocusPainted(false);
//		 allcourse.setBounds(0,380,100,30);
//		 allcourse.setForeground(Color.blue);
//		 allcourse.setBackground(Color.getHSBColor(36, 140, 196));
//		 allcourse.setFont(new Font("",Font.ROMAN_BASELINE,15));
//		 allcourse.setFocusPainted(false);

		 
		 theme.setBounds(10,40,200,30);
		 theme.setForeground(Color.BLACK);
		 theme.setFont(new Font("",Font.ITALIC,14));
		 
		 JPanel left = new JPanel();   	
         left.setPreferredSize(new Dimension(110,500));
         left.setBackground(Color.GRAY);  
         left.setLayout(null);
	    	
//		 left.add(allcourse);
		 left.add(courseSet);
		 left.add(searchCourse);
		 left.add(numberStu);
	     left.add(apprise);
		 left.add(count);
	     left.add(information);
	     left.add(addscore);
  		 left.add(theme);      // ���������	
  		 right.setPreferredSize(new Dimension(390,500));
  		 right.setLayout(null);
//  		 right.setBackground(Color.CYAN);
		 
		 this.add(right,BorderLayout.EAST);
		 this.add(left,BorderLayout.WEST);
		 this.setTitle("Ա����ѵ����ϵͳ");
		 this.setSize(500,500);
		 this.setVisible(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setResizable(false);
	}
	public void setcourses(String id) {
		if(id.equals(""))
		{
			JOptionPane.showMessageDialog(null,"��������γ̱�Ųſ���ɾ���γ�", "��������", JOptionPane.ERROR_MESSAGE);
		}else {
			String person_id = "";
			url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				 Statement sql1 = conn.createStatement();
		          String get_person1 = "delete from course where id ='" + id+ "'";
		          sql1.execute(get_person1);  // ɾ��course �������Ϣ
		          Statement sql = conn.createStatement();
		          String get_person = "select person from training_plan where course_id ='" + id+ "'";
		          ResultSet students = sql.executeQuery(get_person); 
		          while(students.next()) 
		          {person_id = students.getString("person");
		          Statement sql2 = conn.createStatement();
		          String get_person2 = "delete  from training_plan where person ='" + person_id+ "' and course_id='"+id+"'";
		          sql2.execute(get_person2);          
		          }
		          JOptionPane.showMessageDialog(null, "�γ��Ѿ��ɹ�ɾ��", "�γ�ɾ��", JOptionPane.PLAIN_MESSAGE);
		          
		          try {
		 			 String staffteacher,tempid,tempname,tempbook,temproom,temptime,tempintro,tempnumber,tempcourse1,tempstate = "";
		 			 String the_sql ="";
		 			 Connection conn2 = (Connection) DriverManager.getConnection(url, user, password);
		 	          Statement sql2 = conn2.createStatement();
		 	          vinformation.clear(); // �������ǰ������
		 	          String allinfor ="select * from course";
		 	          ResultSet staffcourses = sql2.executeQuery(allinfor);
		 	          while(staffcourses.next())
		 	          {
		 	        	staffteacher = staffcourses.getString("teacher");  
		 	        	tempid = staffcourses.getString("id");
		 	             tempbook = staffcourses.getString("book");
		 	            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
		 	            tempintro = staffcourses.getString("intro");  tempnumber = staffcourses.getString("number"); // ʣ�µ�����ȫ����ȡ
		 	            tempstate = staffcourses.getString("state");	tempcourse1 = staffcourses.getString("name");            		
		 	            Vector v  = new Vector();
		 	            v.add(tempid);v.add(tempcourse1);
		 	            Statement sql3 = conn.createStatement();
		 	            the_sql = "select name from person where id ='";
		 	            the_sql = the_sql + staffteacher +"'";
		 	            ResultSet studnetsname = sql3.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
		 	            sql3.close();
		 	            v.add(tempbook);v.add(temproom);v.add(temptime);v.add(tempnumber);v.add(tempstate);v.add(tempintro);
		 			    vinformation.add(v);
		 	          }
		 	          table.updateUI();
		 	          
		 	    	}catch(SQLException e) {
		 			 	e.printStackTrace();
		 	    	}catch(Exception e) {
		 			 	e.printStackTrace();
		 	    	} 
		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 	
		}
	}
	public void courseSet(){
		
		// ��ӿγ̵ĺ���  --- �γ��������
		// ��Ҫ��Ϣ��ѯ�� 
	   	JLabel course = new JLabel("�γ���Ϣ���ý���");
		JLabel name = new JLabel("�γ���");
		JLabel teacher = new JLabel("��ʦ");
		JLabel intro  = new  JLabel("���");
		JLabel book  = new JLabel("�̲�");
		JLabel Class = new JLabel("�ص�");
		JLabel number = new JLabel("����");
		JLabel time = new JLabel("ʱ��");
		JLabel state = new JLabel("״̬");
		JLabel courseid = new JLabel("ID");
		// ������ѡ�ֶε�����
		// ���е�����ѡ��
		JTextField cteacher = new JTextField(10);
		JTextField courseId = new JTextField(10);
		JComboBox<String> cname = new JComboBox<String>();
		JComboBox<String> cbook = new JComboBox<String>();
		JComboBox<String> cnumber = new JComboBox<String>();
		JComboBox<String> ctime   = new JComboBox<String>();
		JComboBox<String> cstate = new JComboBox<String>();
		JComboBox<String> cadd = new JComboBox<String>();
		// ���ӻ�ȡ
		cname.addItem("");
		cname.addItem("�˼ʽ���");
		cname.addItem("����ѧ");
		cname.addItem("��������");
		cname.addItem("����");
		cname.addItem("English");
		cbook.addItem("");
		cbook.addItem("�˼ʴ���ԭ��");
		cbook.addItem("��������");
		cbook.addItem("��������");
		cbook.addItem("����");
		cbook.addItem("Ӣ��");
		cnumber.addItem("");
		cnumber.addItem("30");
		cnumber.addItem("20");
		cnumber.addItem("10");
		ctime.addItem("");
		ctime.addItem("2018-06-13");
		ctime.addItem("2018-06-24");
		ctime.addItem("2018-06-29");
		cstate.addItem("");
		cstate.addItem("0");
		cstate.addItem("1");
		cstate.addItem("2");
		cadd.addItem("");
		cadd.addItem("��¥������");
		cadd.addItem("��¥������");
		cadd.addItem("ʮ¥������");
		
// ***** textarea û�������Զ�����   ������Ҫ����һ��
		JTextArea  tintro =  new JTextArea();
		tintro.setLineWrap(true);
		// ��ť����
	    JButton jBInsert = new JButton("���");
	    JButton jBUpdate = new JButton("ɾ��");
	     
	    course.setBounds(140,30,120,16);
	    course.setForeground(Color.BLACK);
		course.setFont(new Font("",Font.ITALIC,14));
		// ����ť��Ӽ���
		jBInsert.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {

				 String into=tintro.getText().trim();
				 String teacher = cteacher.getText().trim();
				 String name = cname.getSelectedItem().toString();
				 String book = cbook.getSelectedItem().toString(); String number = cnumber.getSelectedItem().toString();
				 String time = ctime.getSelectedItem().toString(); String state = cstate.getSelectedItem().toString();
				 String add = cadd.getSelectedItem().toString();
				 //(String name,String teacher,String into ,String book,String classroom ,String number ,String time,String state ){
				 setCourse(name, teacher, into, book, add, number, time, state) ;
			 }
		});
		jBUpdate.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent e) {
				 String theID = courseId.getText().toString(); // ��ȡ����Ŀγ�id
				 setcourses(theID);  // ֻ��Ҫ����һ��id
			 }
		});
		// �������ģ��  ���ҽ�����ʽ���޸�
		// λ�õ����
	    teacher.setBounds(20,250,70, 20);
	    cteacher.setBounds(80,250,70,20);
	    courseid.setBounds(190, 250, 70, 20);
	    courseId.setBounds(210, 250, 100, 20);
	    
	    intro.setBounds(20,280,70, 20);
	    tintro.setBounds(80,280,300,60);
	    
	    
	    name.setBounds(20,345,70, 20);
	    cname.setBounds(80,345,60,20);
	    book.setBounds(150,345,70, 20);
	    cbook.setBounds(190,345,70,20);
	    Class.setBounds(280,345,70, 20);
	    cadd.setBounds(320,345,60,20);
	    
	    number.setBounds(20,380,70, 20);
	    cnumber.setBounds(80,380,60,20);
	    time.setBounds(150,380,70, 20);
	    ctime.setBounds(190,380,70,20);
	    state.setBounds(280,380,70, 20);
	    cstate.setBounds(320,380,60,20);  
	    
		jBInsert.setBounds(100, 430, 80, 30);
		jBUpdate.setBounds(240, 430, 80, 30);
		
		// ����label ��������ʽ
		jBInsert.setBorderPainted(false);
		jBInsert.setFont(new java.awt.Font("�����п�", 1, 15));
		jBInsert.setFocusPainted(false); //�����ƾ۽�
		jBInsert.setBorder(BorderFactory.createRaisedBevelBorder()); // ͹��
		jBUpdate.setBorderPainted(false);
		jBUpdate.setFont(new java.awt.Font("�����п�", 1, 15));
		jBUpdate.setFocusPainted(false); //�����ƾ۽�
		jBUpdate.setBorder(BorderFactory.createRaisedBevelBorder()); // ͹��
		
		name.setFont(new Font("",Font.ITALIC,14));
		name.setForeground(Color.getHSBColor(50, 224, 31));
		teacher.setFont(new Font("",Font.ITALIC,14));
		teacher.setForeground(Color.getHSBColor(50, 224, 31));
		courseid.setFont(new Font("",Font.ITALIC,14));
		courseid.setForeground(Color.getHSBColor(50, 224, 31));
		intro.setFont(new Font("",Font.ITALIC,14));
		intro.setForeground(Color.getHSBColor(50, 224, 31));
		book.setFont(new Font("",Font.ITALIC,14));
		book.setForeground(Color.getHSBColor(50, 224, 31));
		Class.setFont(new Font("",Font.ITALIC,14));
		Class.setForeground(Color.getHSBColor(50, 224, 31));
		number.setFont(new Font("",Font.ITALIC,14));
		number.setForeground(Color.getHSBColor(50, 224, 31));
		time.setFont(new Font("",Font.ITALIC,14));
		time.setForeground(Color.getHSBColor(50, 224, 31));
		state.setFont(new Font("",Font.ITALIC,14));
		state.setForeground(Color.getHSBColor(50, 224, 31));
		
//	    // ��ť������
//	    jBInsert.addActionListener(this);
//	    jBUpdate.addActionListener(this);
	    
		this.remove(this.right);
		this.revalidate();
		this.right = new JPanel();   	
		right.setPreferredSize(new Dimension(390,500));
  		right.setLayout(null);
  		vtitle = new Vector();
  		vtitle.addElement("ID");
    	vtitle.addElement("�γ�"); vtitle.addElement("��ʦ����");
    	vtitle.addElement("�̲�"); vtitle.addElement("����"); vtitle.addElement("ʱ��");vtitle.addElement("����");
    	vtitle.addElement("״̬");vtitle.addElement("�γ̼��");
    	vinformation = new Vector();
    	url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				try {
					 String staffteacher,tempid,tempname,tempbook,temproom,temptime,tempintro,tempnumber,tempcourse,tempstate = "";
					 String the_sql ="";
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // �������ǰ������
			          String allinfor ="select * from course";
			          ResultSet staffcourses = sql.executeQuery(allinfor);
			          while(staffcourses.next())
			          {
			        	staffteacher = staffcourses.getString("teacher");  
			        	tempid = staffcourses.getString("id");
			             tempbook = staffcourses.getString("book");
			            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
			            tempintro = staffcourses.getString("intro");  tempnumber = staffcourses.getString("number"); // ʣ�µ�����ȫ����ȡ
			            tempstate = staffcourses.getString("state");	tempcourse = staffcourses.getString("name");            		
			            Vector v  = new Vector();
			            v.add(tempid);v.add(tempcourse);
			            Statement sql2 = conn.createStatement();
			            the_sql = "select name from person where id ='";
			            the_sql = the_sql + staffteacher +"'";
			            ResultSet studnetsname = sql2.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
			            sql2.close();
			            v.add(tempbook);v.add(temproom);v.add(temptime);v.add(tempnumber);v.add(tempstate);v.add(tempintro);
					    vinformation.add(v);
			          }
			          table.updateUI();
			          
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
    	// ���ñ�����ʽ
	    table = new JTable(vinformation,vtitle);
    	table.setPreferredScrollableViewportSize(new Dimension(400,200));
    	table.setForeground(Color.BLUE);
    	table.setRowHeight(22);
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
    	table.setEnabled(false);
        /*��JScrollPaneװ��JTable������������Χ���оͿ���ͨ�����������鿴*/ 
//    	JTableHeader minetitle = table.getTableHeader(); // ��ñ������
//    	minetitle.setPreferredSize(new Dimension(table.getWidth(),16));// ���ñ�ͷ��С
//        table.setFont(new Font("����", Font.PLAIN, 18));// ���ñ������
    	scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(                
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(                
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(20,50,0,0);  // ��Ϊ������һ����������� ����û����  ����ĳ���
  		scroll.setSize(360,200);  
  		right.add(scroll);
	    // ���»��Ƶ���� Ȼ��������
  		right.add(courseid); right.add(courseId);
	    right.add(name);right.add(cname);right.add(teacher);right.add(cteacher);right.add(intro);right.add(tintro);right.add(course);
	    right.add(book);right.add(cbook);right.add(Class);right.add(cadd);right.add(number);right.add(cnumber);right.add(time);right.add(ctime);right.add(state);
	    right.add(cstate); right.add(jBUpdate);right.add(jBInsert);right.setBackground(Color.getHSBColor(135, 140, 115));
	    // ���²��� ���� һ���ұߵ�jp
		this.add(right, BorderLayout.EAST);
		this.repaint();
		this.revalidate();
	   
	}
	//�γ������������ݿⷽ������
	public void setCourse(String name,String teacher,String into ,String book,String classroom ,String number ,String time,String state){
		// �����Ϳ�����Ӻ�����  ����unicode and  ������ utf8
	   if(name.equals("") ||teacher.equals("") || into.equals("") || book.equals("") || classroom.equals("") || number.equals("") || state.equals("")|| time.equals("")) {
		   JOptionPane.showMessageDialog(null, "�����п�ֵ", "�������п�ֵ", JOptionPane.ERROR_MESSAGE);
	   }else {
		   String uername = md5Password(teacher); // ����ʦ ������ת���� md5  Ψһ��
			int  courseid = 0; int teacherid =0; String tempteacher = null,tempcourse = null; 
			url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		          Statement sql = conn.createStatement();
		          //Duplicate entry '1' for key 'PRIMARY'  ������뱣֤��person �����������Ӧ������id
		          //  ������SQL ���������update,insert�ĸ�����䣬Ӧ����statement��execute()����������õ���statement��executeQuery()�ͻ������������
		          //  ��Ҫע����� id  state ��������         teacher �Ƕ�Ӧ�Ľ�ʦ����� person id
		         String idofteacher = "select id  from person where username ='" + uername+"'";
		         ResultSet idofname = sql.executeQuery(idofteacher); while(idofname.next()) {tempteacher = idofname.getString("id");}
		         teacherid = Integer.parseInt(tempteacher);
		         //  �� 
//		         String numbersql = "select count(*)  from course ";
//		         ResultSet numberset = sql.executeQuery(numbersql); while(numberset.next()) {tempcourse = numberset.getString("count(*)");}
//		         courseid = Integer.parseInt(tempcourse);
		         
		          String insql = "insert into course(name,teacher,intro,book,classroom,number,classtime,state) values('";
		          insql = insql  + name + "'," + teacherid +",'" + into +"','" + book +"','" + classroom +"','" +number+"','"+ time +"',"+state+")";
		          sql.execute(insql);
		          JOptionPane.showMessageDialog(null, "�γ���Ϣ��ӳɹ�","������", JOptionPane.PLAIN_MESSAGE);
		          
		          try {
		 			 String staffteacher,tempid,tempname,tempbook,temproom,temptime,tempintro,tempnumber,tempcourse1,tempstate = "";
		 			 String the_sql ="";
		 			 Connection conn2 = (Connection) DriverManager.getConnection(url, user, password);
		 	          Statement sql2 = conn2.createStatement();
		 	          vinformation.clear(); // �������ǰ������
		 	          String allinfor ="select * from course";
		 	          ResultSet staffcourses = sql2.executeQuery(allinfor);
		 	          while(staffcourses.next())
		 	          {
		 	        	staffteacher = staffcourses.getString("teacher");  
		 	        	tempid = staffcourses.getString("id");
		 	             tempbook = staffcourses.getString("book");
		 	            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
		 	            tempintro = staffcourses.getString("intro");  tempnumber = staffcourses.getString("number"); // ʣ�µ�����ȫ����ȡ
		 	            tempstate = staffcourses.getString("state");	tempcourse1 = staffcourses.getString("name");            		
		 	            Vector v  = new Vector();
		 	            v.add(tempid);v.add(tempcourse1);
		 	            Statement sql3 = conn.createStatement();
		 	            the_sql = "select name from person where id ='";
		 	            the_sql = the_sql + staffteacher +"'";
		 	            ResultSet studnetsname = sql3.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
		 	            sql3.close();
		 	            v.add(tempbook);v.add(temproom);v.add(temptime);v.add(tempnumber);v.add(tempstate);v.add(tempintro);
		 			    vinformation.add(v);
		 	          }
		 	          table.updateUI();
		 	          
		 	    	}catch(SQLException e) {
		 			 	e.printStackTrace();
		 	    	}catch(Exception e) {
		 			 	e.printStackTrace();
		 	    	} 
		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 
	   }
	}
		// Ա��ѡ�� ��Ϣ  �������  
	public void searchCour(){
				
			JLabel information = new JLabel("-Ա���α���Ϣ����-");
			information.setBounds(140,26,140,16);
			information.setForeground(Color.BLUE);
			information.setFont(new Font("",Font.ITALIC,14));
			JLabel name = new JLabel("Ա������");
			JLabel course = new  JLabel("�γ�");
			JTextField name1 = new JTextField(10);
		    JComboBox<String> tcourse = new JComboBox<String>();
		    tcourse.addItem("");
		    tcourse.addItem("�˼ʽ���");
		    tcourse.addItem("����ѧ");
		    tcourse.addItem("��������");
		    tcourse.addItem("����");
		    tcourse.addItem("English");
		    
		    name.setBounds(100,370,100,26);
		    name1.setBounds(200,370,100,26);
		    course.setBounds(100,400,100,26);
		    tcourse.setBounds(200,400,100,26);
		    
		    JButton  search = new JButton("--��ѯ--");
		    search.setBounds(140,430,80,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("����",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // �����߼�������
	            	String name=name1.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString(); // ��ѡ��Ŀ�Ŀ ����
	            	// �����Լ����߼� һ����Ϊ�������   1 ѧ�� + �γ�  ��ȷ����  2 ѧ�����  3 �γ����   
//	            	System.out.println(name + " "+xcourse);  
	                allCoursesinfor(name,xcourse);
	                JOptionPane.showMessageDialog(null, "�α���Ϣ��ʾ�ɹ�", "��ѯ�ɹ�", JOptionPane.PLAIN_MESSAGE);
	            }
	        });
		    this.remove(this.right);
			this.revalidate();  
			// ��֤�������»���ͼ��
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		// ���波�����table 
	        vtitle = new Vector();
	    	vtitle.addElement("�γ�"); vtitle.addElement("��ʦ����"); vtitle.addElement("ѧ������");
	    	vtitle.addElement("�̲�"); vtitle.addElement("����"); vtitle.addElement("ʱ��");vtitle.addElement("����");
	    	vtitle.addElement("״̬");vtitle.addElement("�γ̼��");
	    	vinformation = new Vector();
	    	// ���ñ�����ʽ
 	    	table = new JTable(vinformation,vtitle);
	    	table.setPreferredScrollableViewportSize(new Dimension(400,300));
//	    	table.setForeground(Color.ORANGE);
	    	table.setRowHeight(22);table.setEnabled(false);
	    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
	        /*��JScrollPaneװ��JTable������������Χ���оͿ���ͨ�����������鿴*/ 
//	    	JTableHeader minetitle = table.getTableHeader(); // ��ñ������
//	    	minetitle.setPreferredSize(new Dimension(table.getWidth(),16));// ���ñ�ͷ��С
//	        table.setFont(new Font("����", Font.PLAIN, 18));// ���ñ������
	    	scroll = new JScrollPane(table);
			scroll.setVerticalScrollBarPolicy(                
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(                
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setBounds(20,50,0,0);  // ��Ϊ������һ����������� ����û����  ����ĳ���
	  		scroll.setSize(360,310);  
	  		right.add(scroll);
		    
		    right.add(information);
		    right.add(name);
		    right.add(name1);
		    right.add(course);
		    right.add(tcourse);
		    right.add(search);
			this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
		}
	  public void allCoursesinfor(String name,String course) {
		    String allcourse ="";
		    String allinfor ,code_state= "";String staffname="",staffteacher="",coursesta = "";
		    String tempname,tempintro,temproom,tempbook,number,temptime,tempstate,tempcourse="";
		    String  tmpteachername,staffstudnet , the_sql= "";
		    // number one  all is empty
		    if(name.equals("")&&course.equals(""))
		    {
		    	// ��ȡ���е���Ϣ
		    	allinfor = "select person.name as student,course.name as course, course.teacher ,course.intro,course.book,course.classroom,course.number,course.classtime,course_state.description" + 
		    			" from course,training_plan,person,course_state where course.id = training_plan.course_id and training_plan.person = person.id and course_state.code = course.state;";
		    	url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // �������ǰ������
			          ResultSet staffcourses = sql.executeQuery(allinfor);
			          while(staffcourses.next())
			          {
			        	staffteacher = staffcourses.getString("teacher");  
			            tempname = staffcourses.getString("student"); tempbook = staffcourses.getString("book");
			            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
			            tempintro = staffcourses.getString("intro");  number = staffcourses.getString("number"); // ʣ�µ�����ȫ����ȡ
			            tempstate = staffcourses.getString("description");	tempcourse = staffcourses.getString("course");            		
			            Vector v  = new Vector();
			            v.add(tempcourse);
			            Statement sql2 = conn.createStatement();
			            the_sql = "select name from person where id ='";
			            the_sql = the_sql + staffteacher +"'";
			            ResultSet studnetsname = sql2.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
			            sql2.close();
			            v.add(tempname);v.add(tempbook);v.add(temproom);v.add(temptime);v.add(number);v.add(tempstate);v.add(tempintro);
					    vinformation.add(v);
			          }
			          table.updateUI();
			          
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
		    }else if(name.equals("")) {
		    	url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
		        String personid = ""; 
		        try {
					  Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          // ÿ���ڲ�ѯ��ʱ��  ��ý���һ���µ�sql  Ȼ��ִ���� ֮���ͷ� �Լ�
					  Statement sql = conn.createStatement();
					  allinfor = "select distinct training_plan.person,course.name as course,course.teacher,intro,book,classroom, number,classtime,course_state.description from course,course_state,training_plan where course.id in" + 
					  		"(select distinct course_id from training_plan where course ='"+ course+"') and course.state = course_state.code";
			          vinformation.clear(); // �������ǰ������
			          ResultSet allinformations  = sql.executeQuery(allinfor);
			          while(allinformations.next())
			          {
			        	  staffteacher = allinformations.getString("teacher");  
			        	  staffstudnet = allinformations.getString("person");
			        	  tempbook = allinformations.getString("book");
				           temproom = allinformations.getString("classroom"); temptime = allinformations.getString("classtime");
				            tempintro = allinformations.getString("intro");  number = allinformations.getString("number"); // ʣ�µ�����ȫ����ȡ
				            tempstate = allinformations.getString("description"); tempcourse = allinformations.getString("course");
				            Vector v  = new Vector();
				            v.add(tempcourse);
				            Statement sql2 = conn.createStatement();
				            the_sql = "select name from person where id ='";
				            the_sql = the_sql + staffteacher +"'";
				            ResultSet studnetsname = sql2.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
				            sql2.close();	
				            Statement sql3 = conn.createStatement();
				            the_sql = "select name from person where id ='";
				            the_sql = the_sql + staffstudnet+"'";
				            ResultSet teachname = sql3.executeQuery(the_sql); while(teachname.next()) {String stname = teachname.getString("name");  v.add(stname);}	
				            sql3.close();			 
				           v.add(tempbook);v.add(temproom);v.add(temptime);v.add(number);v.add(tempstate);v.add(tempintro);
				            vinformation.add(v);
			          }
			          table.updateUI();
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 

		    }else if(course.equals(""))
		    {
			        url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
					// ��ת��Ϊid
			        String personid = ""; 
			        String username = md5Password(name);
			        try {
						 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				          Statement ids = conn.createStatement();
				          String idsql = "select id from person where username ='" +username+"'";
				          ResultSet id  = ids.executeQuery(idsql); while(id.next()) {personid = id.getString("id"); }
				    	}catch(SQLException e) {
						 	e.printStackTrace();
				    	}catch(Exception e) {
						 	e.printStackTrace();
				    	} 
			        // ���ĸ���table����
			        try {
						  Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				          // ÿ���ڲ�ѯ��ʱ��  ��ý���һ���µ�sql  Ȼ��ִ���� ֮���ͷ� �Լ�
						  Statement sql = conn.createStatement();
						  allinfor = "select name as course,teacher,intro,book,classroom, number,classtime,course_state.description from course,course_state where id in" + 
					        		"(select course_id from training_plan  where person = '"+ personid+"') and course.state = course_state.code";
				          vinformation.clear(); // �������ǰ������
				          ResultSet allinformations  = sql.executeQuery(allinfor);
				          while(allinformations.next())
				          {
				        	  staffteacher = allinformations.getString("teacher");  
				        	  tempbook = allinformations.getString("book");
					           temproom = allinformations.getString("classroom"); temptime = allinformations.getString("classtime");
					            tempintro = allinformations.getString("intro");  number = allinformations.getString("number"); // ʣ�µ�����ȫ����ȡ
					            tempstate = allinformations.getString("description"); tempcourse = allinformations.getString("course");
					            Vector v  = new Vector();
					            v.add(tempcourse);
					            Statement sql2 = conn.createStatement();
					            the_sql = "select name from person where id ='";
					            the_sql = the_sql + staffteacher +"'";
					            ResultSet studnetsname = sql2.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
					            sql2.close();			            
					            v.add(name); v.add(tempbook);v.add(temproom);v.add(temptime);v.add(number);v.add(tempstate);v.add(tempintro);
					            vinformation.add(v);
				          }
				          table.updateUI();
				    	}catch(SQLException e) {
						 	e.printStackTrace();
				    	}catch(Exception e) {
						 	e.printStackTrace();
				    	} 
		    	}else {
//		    	System.out.println(course + name);
		       //ͬʱ�ṩ�����Ϳγ�   ����  ���� �Ͳ�����ȥת���� ֱ�Ӳ�����ʦ������  �Ϳ�����  Ȼ�� ״̬һ����˼·  ֱ�ӽ����ű�����Ȼ����
		    	// first step we need to change the name to id
		    	String username = md5Password(name);
		    	String personid = ""; 
		    	try {
		    		Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		            Statement  idofstudent = conn.createStatement();
		            String Sql = "select id from person where username ='" +username+"'";  
		           ResultSet id  = idofstudent.executeQuery(Sql); while(id.next()) {personid = id.getString("id"); }
		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 
		    	allinfor = "select course.name as course, course.teacher ,course.intro,course.book,course.classroom,course.number,course.classtime,course_state.description " + 
		    			" from course,course_state  where id in " + 
		    			"(select course_id from training_plan where course= '"+ course+"' and person='"+personid +"') and course.state = course_state.code";
		    	url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				System.out.println(allinfor);
		    	try {
					  Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          // ÿ���ڲ�ѯ��ʱ��  ��ý���һ���µ�sql  Ȼ��ִ���� ֮���ͷ� �Լ�
					  Statement sql = conn.createStatement();
					  
			          vinformation.clear(); // �������ǰ������
			          ResultSet allinformations  = sql.executeQuery(allinfor);
			          while(allinformations.next())
			          {
			        	  staffteacher = allinformations.getString("teacher");  
			        	  tempbook = allinformations.getString("book");
				           temproom = allinformations.getString("classroom"); temptime = allinformations.getString("classtime");
				            tempintro = allinformations.getString("intro");  number = allinformations.getString("number"); // ʣ�µ�����ȫ����ȡ
				            tempstate = allinformations.getString("description");
				            Vector v  = new Vector();
				            v.add(course);
				            Statement sql2 = conn.createStatement();
				            the_sql = "select name from person where id ='";
				            the_sql = the_sql + staffteacher +"'";
				            ResultSet studnetsname = sql2.executeQuery(the_sql); while(studnetsname.next()) {String myname = studnetsname.getString("name");  v.add(myname);}	
				            sql2.close();			            
				            v.add(name);  v.add(tempbook);v.add(temproom);v.add(temptime);v.add(number);v.add(tempstate);v.add(tempintro);
				            vinformation.add(v);
			          }
			          table.updateUI();
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
		    }
		}
	    public void Number(){    	
	    	
	    	JLabel numberOfStudent = new JLabel("-  ѧԱ������Ϣ    -");
	    	numberOfStudent.setBounds(140,26,140,16);
	    	numberOfStudent.setForeground(Color.BLACK);
	    	numberOfStudent.setFont(new Font("",Font.ITALIC,14));
	    	
	    	// ���е��������
	    	JLabel teacherName = new JLabel("��ʦ����");
	    	JLabel courseName  = new JLabel("�γ�����");
	    	JTextField teacher = new JTextField(10);
	    	JComboBox<String> tcourse = new JComboBox<String>();
	    	tcourse.addItem("");
			tcourse.addItem("�˼ʽ���");
			tcourse.addItem("����ѧ");
			tcourse.addItem("��������");
			tcourse.addItem("����");
			tcourse.addItem("English");
	    	JButton  search = new JButton("--��ѯ--");
	    	
	    	// ���波�����table 
	        vtitle = new Vector();
	    	vtitle.addElement("�γ�"); vtitle.addElement("��ʦ"); vtitle.addElement("ѧ������");
	    	vinformation = new Vector();
	    	table = new JTable(vinformation,vtitle);
	    	table.setPreferredScrollableViewportSize(new Dimension(330, 280));
//	    	table.setForeground(Color.ORANGE);
	    	table.setRowHeight(22);
	    	table.setEnabled(false);
	    	scroll = new JScrollPane(table);
			scroll.setVerticalScrollBarPolicy(                
					ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	  		scroll.setBounds(40, 58, 330, 280);
	  		right.add(scroll);
	  		
	  		
	    	teacherName.setBounds(40,360,80,30);
	    	teacher.setBounds(110,360,80,30);
	    	courseName.setBounds(200,360,80,30);
	    	tcourse.setBounds(260,360,80,30);
	    	search.setBounds(150,420,80,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // �����߼�������
	            	String name=teacher.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString();
	            	//System.out.print(name + xcourse);
	            	theNumber(name,xcourse);
	            }
	        });
	    	// ��ʽ�޸ĺ�����
			this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	    	right.add(teacherName);
	    	right.add(teacher);
	    	right.add(courseName);
	    	right.add(tcourse);
	    	right.add(search);
			right.add(scroll);
	    	
	    	this.right.add(numberOfStudent);
	    	right.setBackground(Color.getHSBColor(135, 140, 115));
	    	this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
	    }
	    public void  theNumber(String teacher,String course) {
	    	// ���ҹ涨 ���������ʦ�����Լ��γ�����
	    	if(teacher.equals("") || course.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(null, "��ѡ��ȫ���Ĳ�ѯ����", "���������п�ֵ", JOptionPane.ERROR_MESSAGE);
	    	}else {
	    		String usename = md5Password(teacher);
		    	String personid = null;
				String courseid = null;
				// �����Ϳ�����Ӻ�����  ����unicode and  ������ utf8
				url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // �������ǰ������
			          String pidSql = "select id from person where username ='" + usename+"'";
			          ResultSet pid = sql.executeQuery(pidSql);
			          // ϵͳ������Ա�����ѧ��������һ������ ������Ҫ����һ���ж�
			          while(pid.next())
			          {
			        	  personid = pid.getString("id");    
			          }
			          if(personid==null)
			          {
			        	  JOptionPane.showMessageDialog(null, "�ܱ�Ǹ������û��������ʦ", "��ʦ������", JOptionPane.ERROR_MESSAGE);
			          }else {
			        	    
			        	   	 int sid = Integer.parseInt(personid); // ��ʦ������Ӧ��id
			        	   	 Statement sqlone = conn.createStatement();
			        	   	 String stuinfor = "select course.id from course where teacher="+ sid+ " and course.name='"+course+"'";
			        	   	 ResultSet course_id = sqlone.executeQuery(stuinfor);
			        	   	 while(course_id.next())
			        	   	 {
			        	   		courseid = course_id.getString("id"); 
			        	   	 }
			        	   	 String sql1 ="select distinct person.name from person,training_plan where person.id \r\n" + 
				        	   	 		"in (select person from  training_plan where course_id ='"+courseid+"')";
			        	   	 ResultSet informations = sql.executeQuery(sql1);	        	   	 
			        	  // 	 System.out.println(sql1);
			        	   	 while(informations.next())
			        	   	 {
			        	   		 String stusname  = informations.getString("name");
			        	   		 Vector v  = new Vector();
			        	   		 v.add(course); v.add(teacher);
			        	   		 v.add(stusname);
			        	   		 vinformation.add(v);
			        	   	 }
			        	   	table.updateUI();  
			          }
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
	    	}	  
		}
		public void appri() {
//	  	   
	  		//���е��������
	  		JLabel appri = new JLabel("-- �γ�������Ϣ  --");
	  		JLabel stuName = new JLabel("ѧԱ����");
	  		JLabel stuClass = new JLabel("���Ͽγ�");
	  		JTextField tstuName = new JTextField(10);
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  	    tcourse.addItem("");
			tcourse.addItem("�˼ʽ���");
			tcourse.addItem("����ѧ");
			tcourse.addItem("��������");
			tcourse.addItem("����");
			tcourse.addItem("English");
			// �������ѡ��ļ�����
	  		JButton  search = new JButton("--��ѯ--");
	  		// ����������Ű�
	  		appri.setBounds(140,20,120,16);
	    	appri.setForeground(Color.getHSBColor(104, 154, 78));
	    	appri.setFont(new Font("",Font.ITALIC,14));
	  		stuName.setBounds(40,360,80,30);
	    	tstuName.setBounds(110,360,80,30);
	    	stuClass.setBounds(200,360,80,30);
	    	tcourse.setBounds(260,360,80,30);
	    	search.setBounds(150,420,80,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // �����߼�������
	            	String name=tstuName.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString();
	            	System.out.print(name + xcourse);
	            	showappri(name,xcourse);
	            }
	        });
	    	// ��ʽ�޸ĺ�����
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		// ���波�����table 
	    	
//	  		String[] title = {"�γ�","ѧ������","���۵ȼ�"};
//	    	String[][]  information = {{"1","2","78"},{"2","3","54"},{"2","3","877"}};
	    	vtitle = new Vector();
	    	vtitle.addElement("ѧԱ����"); vtitle.addElement("�γ�"); vtitle.addElement("�ɼ�");vtitle.addElement("���۵ȼ�");vtitle.add("����ʱ��");
	    	vinformation = new Vector(); // Ŀǰ����Ϊ��
	    	table = new JTable(vinformation,vtitle);
	    	table.setPreferredScrollableViewportSize(new Dimension(330,280));
//	    	table.setForeground(Color.ORANGE);
	    	table.setRowHeight(22);
	    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	scroll = new JScrollPane(table);table.setEnabled(false);
			scroll.setVerticalScrollBarPolicy(                
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	  		scroll.setBounds(30, 58, 330, 280);
	  		scroll.setSize(350, 280);;
	  		right.add(scroll);
	    	right.add(stuName);
	    	right.add(tstuName);
	    	right.add(stuClass);
	    	right.add(tcourse);
	    	right.add(search);
	  		//��������
	  		right.add(appri);
	  		right.add(stuName);
	  		right.add(stuClass);
	    	
//	    	right.setBackground(Color.getHSBColor(62,193, 184));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
			
	    }
		 public void showappri(String name,String course) {
			  // �Դ��ݹ����Ĳ������п�ֵ���ж�
			 if((course.equals("")&& name.equals("") )||(course.equals(""))) 
			 {
				 JOptionPane.showMessageDialog(null,"����ѡ��γ���Ϣ" , "��������", JOptionPane.ERROR_MESSAGE);
			 }else {
				  String sesql = "select  distinct apprisement.description,person.name,course.name as name1,training_plan.exam_date,training_plan.score from training_plan,course,person,apprisement where";
				  if(name.equals(""))
				  {
					  //System.out.println("��û�����ֵ�");
					  sesql = sesql + "(training_plan.course='"+course +"')  and person.id= training_plan.person and training_plan.course = course.name \r\n" + 
					  		"and training_plan.apprisement = apprisement.code";
				  }
				  else{
					//  System.out.println("�������ֵ�");
					  sesql = sesql + "(person.name='"+ name +"' and training_plan.course='"+course+"')  and person.id= training_plan.person and training_plan.course = course.name \r\n" + 
					  		"and training_plan.apprisement = apprisement.code";
				  }
				 // System.out.print(sesql);
				  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
					 try {
						 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				          Statement score = conn.createStatement(); // �൱�ڽ���һ���α�  cursor
				          
				          ResultSet result = score.executeQuery(sesql);
				          vinformation.clear(); // �������ǰ������
//				          vinformation = new Vector(); // ���½���һ�����Զ�̬���������
				          
				          while(result.next())
				          {
				        	  Vector v  = new Vector(); //��������ÿһ�е�����
				        	  v.add(result.getString("name"));
				        	  v.add(result.getString("name1"));
				        	  v.add(result.getString("score"));
				        	  v.add(result.getString("description"));
				        	  v.add(result.getString("exam_date"));
				        	  vinformation.add(v);
				          }
				          table.updateUI(); // ���´��ڣ�����
				          
				    	}catch(SQLException e) {
						 	e.printStackTrace();
				    	}catch(Exception e) {
						 	e.printStackTrace();
				    	}  
			 }
		  }
		public void addscore(){
			
			//���е��������
	  		JLabel appri = new JLabel("-- ѧԱ�ɼ�¼��  --");
	  		JLabel stuName = new JLabel("ѧԱ����");
	  		JLabel stuClass = new JLabel(" �γ� ");
	  		JLabel stuScore = new JLabel(" �ɼ�  ");
	  		JLabel stuLevel = new JLabel("��ѵ�ȼ�");
	  		JLabel stuTime  = new JLabel("��������");
	  		JTextField tstutime = new JTextField(10);
	  		JTextField tstuName = new JTextField(10);
	  		JTextField tstuScore = new JTextField(10);
	  		JComboBox<String> tstuLevel = new JComboBox<String>();
	  		tstuLevel.addItem("");
	  		tstuLevel.addItem("0");
	  		tstuLevel.addItem("1");
	  		tstuLevel.addItem("2");
	  		tstuLevel.addItem("3");
	  		tstuLevel.addItem("4");
	  		
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  		tcourse.addItem("");
			tcourse.addItem("�˼ʽ���");
			tcourse.addItem("����ѧ");
			tcourse.addItem("��������");
			tcourse.addItem("����");
			tcourse.addItem("English");
			
	  		JButton  search = new JButton("--¼��--");
	  		// ����������Ű�
	  		appri.setBounds(140,20,120,16);
	    	appri.setForeground(Color.getHSBColor(104, 154, 78));
	    	appri.setFont(new Font("",Font.ITALIC,14));
	  		stuName.setBounds(40,340,80,26);
	    	tstuName.setBounds(110,340,80,26);
	    	stuClass.setBounds(200,370,80,26);
	    	tcourse.setBounds(260,370,80,26);
	    	stuScore.setBounds(40,370,80,26);
	    	tstuScore.setBounds(110,370,80,26);
	    	stuLevel.setBounds(200,405,80,26);
	    	tstuLevel.setBounds(260,405,80,26);
	    	stuTime.setBounds(40,405,80,26);
	    	tstutime.setBounds(110,405,80,26);
	    	search.setBounds(150,440,80,26);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	             
	            //  ��Ҫ���ݵĲ���	System.out.print(name +time + core +xstate + xcourse);
	            	String name = tstuName.getText().trim();
	            	String time = tstutime.getText().trim();
	            	String core = tstuScore.getText().trim();  
	            	xcourse = tcourse.getSelectedItem().toString();
	            	xle = tstuLevel.getSelectedItem().toString();
	               addScore(name,xcourse,core,xle,time);
	            }
	        });
	    	// ��ʽ�޸ĺ�����
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		
	  		 vtitle = new Vector();
		    	vtitle.addElement("ѧԱ����"); vtitle.addElement("�γ�");
		    	vinformation = new Vector(); // Ŀǰ����Ϊ��
		    	table = new JTable(vinformation,vtitle);
		    	table.setPreferredScrollableViewportSize(new Dimension(330,280));
//		    	table.setForeground(Color.ORANGE);
		    	table.setRowHeight(22);
		    	table.setEnabled(false);
		    	scroll = new JScrollPane(table);
				scroll.setVerticalScrollBarPolicy(                
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scroll.setHorizontalScrollBarPolicy(
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		  		scroll.setBounds(30, 58, 330, 280);
		  		scroll.setSize(350, 280);;
		  		right.add(scroll);
		  		
		  		 try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement score = conn.createStatement(); // �൱�ڽ���һ���α�  cursor
			         String sesql ="select person.name,course from training_plan,person where training_plan.person = person.id";
			          ResultSet result = score.executeQuery(sesql);
			          vinformation.clear(); // �������ǰ������
//			          vinformation = new Vector(); // ���½���һ�����Զ�̬���������
			          
			          while(result.next())
			          {
			        	  Vector v  = new Vector(); //��������ÿһ�е�����
			        	  v.add(result.getString("name"));
			        	  v.add(result.getString("course"));
			        	  vinformation.add(v);
			          }
			          table.updateUI(); // ���´��ڣ�����
			          
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 		
		  		
		  		
		  		
	    	right.add(stuName);
	    	right.add(tstuName);
	    	right.add(stuClass);
	    	right.add(tcourse);right.add(stuTime); right.add(tstutime);
	    	right.add(search); right.add(stuScore); right.add(stuLevel);
	    	right.add(tstuLevel); right.add(tstuScore);
	    	
	  		//��������
	  		right.add(appri);
	  		right.add(stuName);
	  		right.add(stuClass);
	    	
//	    	right.setBackground(Color.getHSBColor(62,193, 184));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
		}
		// ѧԺ�ɼ�������name,xcourse,core,xle,time  ���ﲻ��Ҫ��������id ���ر����� ��Ϊ��������������
		public void addScore(String name,String course,String core,String state,String time ){
			if(name.equals("") || course.equals("") || core.equals("") || state.equals("") || time.equals(""))
			{
			    JOptionPane.showMessageDialog(null, "�ɼ���Ϣ����Ϊ��", "�п�ֵ", JOptionPane.ERROR_MESSAGE);
			}else {
				String usename = md5Password(name);
				String psid = null;
				// �����Ϳ�����Ӻ�����  ����unicode and  ������ utf8
				url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				
				//System.out.println(name+ xcourse+core+xle+time);
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          //Duplicate entry '1' for key 'PRIMARY'  ������뱣֤��person �����������Ӧ������id
			          //  ������SQL ���������update,insert�ĸ�����䣬Ӧ����statement��execute()����������õ���statement��executeQuery()�ͻ������������
			          //  ��Ҫע����� id  state person ��������  ����state person �����        
			          String pidSql = "select id from person where username ='" + usename+"'";
			          ResultSet pid = sql.executeQuery(pidSql);
			          // ϵͳ������Ա�����ѧ��������һ������ ������Ҫ����һ���ж�
			          while(pid.next())
			          {
			        	  psid = pid.getString("id");    
			          }
			          if(psid==null)
			          {
			        	  System.out.println("�ܱ�Ǹ����ʱ��û������Ա��");
			          }else {
			        	   		int sid = Integer.parseInt(psid);
					         int sta = Integer.parseInt(state);
					         int score = Integer.parseInt(core); // �ɼ� ״̬  �Ѿ�ѧ��id ת��Ϊint����
					         //System.out.println(sta +"  " + sid + "  "+ score);
					         //update training_plan set score =88,apprisement=3,exam_date="2018-0617" where person =7 and course="�˼ʽ���";
					         String updatesql = "update training_plan set score ="+score+",apprisement="+sta+",exam_date='"+time+"' where person ="+sid+" and course='"+course+"'";
					         sql.execute(updatesql);  
					         JOptionPane.showMessageDialog(null, "�ɼ���ӳɹ�", "������", JOptionPane.PLAIN_MESSAGE);

			          }
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
			}
			// ���ﲻ����ֱ��ת��������
			//System.out.println(core+"  "+ state + "  ");
			//int iteacher=  Integer.parseInt(name);
		}
	    public void Count(){
	    	
	    	//���е��������
	  		JLabel count = new JLabel("-- ��ѵͳ����Ϣ  --");
	  		JLabel stuName = new JLabel("��ʦ����");
	  		JLabel stuClass = new JLabel("�γ�����");
	  		JTextField tstuName = new JTextField(10);
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  		tcourse.addItem("");
			tcourse.addItem("�˼ʽ���");
			tcourse.addItem("����ѧ");
			tcourse.addItem("��������");
			tcourse.addItem("����");
			tcourse.addItem("English");
	  		JButton  search = new JButton("--��ѯ--");
	  		// ����������Ű�
	  		count.setBounds(140,20,120,16);
	    	count.setForeground(Color.getHSBColor(166, 210, 178));
	    	count.setFont(new Font("",Font.ITALIC,14));
	  		stuName.setBounds(40,360,80,30);
	    	tstuName.setBounds(110,360,80,30);
	    	stuClass.setBounds(200,360,80,30);
	    	tcourse.setBounds(260,360,80,30);
	    	search.setBounds(150,420,80,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // �����߼�������
	            	String name = tstuName.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString();
	            	countof_edu(name,xcourse);
	            }
	        });
	    	// ��ʽ�޸ĺ�����
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		// ���波�����table  ȫ���������  ȫ�ֱ��� ����ÿ�εĹ��� 
	    	vtitle = new Vector();
	    	vtitle.addElement("�γ�"); vtitle.addElement("��ʦ");vtitle.addElement("��ǰ�Ͽ�����");vtitle.addElement("����ʱ��");
	    	vinformation = new Vector(); // Ŀǰ��ϢΪ��
	    	table = new JTable(vinformation,vtitle);
//	    	table.setForeground(Color.ORANGE);
	    	table.setEnabled(false);
	    	table.setRowHeight(22);
//	    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	scroll = new JScrollPane(table);
			scroll.setVerticalScrollBarPolicy(                
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	  		scroll.setBounds(30, 58, 0, 0);
	  		scroll.setSize(345, 280);
	  		right.add(scroll);
	    	right.add(stuName);
	    	right.add(tstuName);
	    	right.add(stuClass);
	    	right.add(tcourse);
	    	right.add(search);
	  		//��������
	  		right.add(count);
	  		right.add(stuName);
	  		right.add(stuClass);
	    	
	    	right.setBackground(Color.getHSBColor(135, 140, 115));
	    	this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
	    }
	    // ��ѵͳ����Ϣ  --- Ȼ������һ���γ���Ϣ��ѯ���� ��Ҫ����һ��   x/y �������͵����� ����ǰѧԱ���� �Լ����������� 
	    public void countof_edu(String teacher,String course)
	    {
	    	if(teacher.equals("") || course.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(null, "��ѡ��ȫ��������", "��ѯ��������Ϊ��", JOptionPane.ERROR_MESSAGE);
	    	}else {
	    		String usename = md5Password(teacher);
		    	String personid = null;
		    	String courseid = null;
				url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";			
				//System.out.println(name+ xcourse+core+xle+time);
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // �������ǰ������
			          String pidSql = "select id from person where username ='" + usename+"'";
			          ResultSet pid = sql.executeQuery(pidSql);
			          // ϵͳ������Ա�����ѧ��������һ������ ������Ҫ����һ���ж�
			          while(pid.next())
			          {
			        	  personid = pid.getString("id");    
			          }
			          if(personid==null)
			          {
			        	 JOptionPane.showMessageDialog(null, "��Ǹ������û��������ʦ", "��ʦ������", JOptionPane.ERROR_MESSAGE);
			          }else {
			        	    
			        	   	 int sid = Integer.parseInt(personid); // ��ʦ������Ӧ��id
			        	   	 Statement sqlone = conn.createStatement();
			        	   	 String stuinfor = "select course.id from course where teacher="+ sid+ " and course.name='"+course+"'";
			        	   	 ResultSet course_id = sqlone.executeQuery(stuinfor);
			        	   	 while(course_id.next())
			        	   	 {
			        	   		courseid = course_id.getString("id"); 
			        	   	 }
			        	   	 ResultSet informations = sql.executeQuery("select count(distinct person),exam_date from training_plan where course_id ='"+courseid+"'");	        	   	 
			        	   	 while(informations.next())
			        	   	 {
			        	   		 String stunumber  = informations.getString("count(distinct person)");
			        	   		 String exam_date  = informations.getString("exam_date");
			        	   		 Vector v  = new Vector();
			        	   		 v.add(teacher);
			        	   		 v.add(course);
			        	   		 v.add(stunumber);
			        	   		 v.add(exam_date);
			        	   		 vinformation.add(v);
			        	   	 }
			        	   	
			        	   	table.updateUI();  
			        	   	JOptionPane.showMessageDialog(null,"��ѵͳ����Ϣ��ѯ�ɹ�", "��ѯ���", JOptionPane.PLAIN_MESSAGE);
			          }
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
	    	}
		}
	    // �ɼ���ѯ���� ���
	    public void record() {
	    	
	  	    //���е��������
	  		JLabel record = new JLabel("-- ��ѵ�ɼ���ѯ --");
	  		//JLabel stuName = new JLabel("ѧԱ����");
	  		JLabel stuClass = new JLabel("�γ�");
	  		//JTextField tstuName = new JTextField(10);
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  		tcourse.addItem("");
			tcourse.addItem("�˼ʽ���");
			tcourse.addItem("����ѧ");
			tcourse.addItem("��������");
			tcourse.addItem("����");
			tcourse.addItem("English");
			// �������ѡ��ļ�����
//			System.out.println();
	  		JButton  search = new JButton("--��ѯ--");
	  		// ����������Ű�
	  		record.setBounds(140,20,120,16);
	  		record.setForeground(Color.getHSBColor(104, 154, 78));
	  		record.setFont(new Font("",Font.ITALIC,14));
	  		//stuName.setBounds(40,360,80,30);
	    	//tstuName.setBounds(110,360,80,30);
	    	stuClass.setBounds(140,360,80,30);
	    	tcourse.setBounds(180,360,80,30);
	    	search.setBounds(150,420,80,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // �����߼�������
	    			xcourse = tcourse.getSelectedItem().toString();
	            	showappri(staname,xcourse);  // ѧԱ����  �Լ�ѡ��Ŀγ�
	            }
	        });
	    	// ��ʽ�޸ĺ�����
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		
	  		// ���波�����table
	  		//Vector vtitle = new Vector();
	  		vtitle = new Vector();
	    	vtitle.addElement("ѧԱ����"); vtitle.addElement("�γ�"); vtitle.addElement("�ɼ�");vtitle.addElement("���۵ȼ�");vtitle.add("����ʱ��");
	    	vinformation = new Vector(); // Ŀǰ����Ϊ��
	    	table = new JTable(vinformation,vtitle);
	    	table.setPreferredScrollableViewportSize(new Dimension(330,280));
	    	table.setForeground(Color.cyan);
	    	table.setBackground(Color.gray);
	    	table.setRowHeight(22);table.setEnabled(false);
	    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	scroll = new JScrollPane(table);
			scroll.setVerticalScrollBarPolicy(                
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	  		scroll.setBounds(30, 58, 330, 280);
	  		scroll.setSize(350, 280);;
	  		right.add(scroll);
		    
	    	right.add(stuClass);
	    	right.add(tcourse);
	    	right.add(search);
	  		//��������
	  		right.add(record);
	  		right.add(stuClass);
	    	
//	    	right.setBackground(Color.getHSBColor(62,193, 184));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();	
	    }
	    // ѧԱѡ��γ̵Ľ���
	    public void Choice() {	  
	    	
	    	// �������������������������пγ̵Ŀ��  table ����ѡ��  �����ټ�  ���easy
	   
	    	
	    	
	  		 //���е��������
	  		JLabel Choice = new JLabel("-- ѧԱѡ��ҳ�� --");
	  		JLabel stuName = new JLabel("��ʦ����");
	  		JLabel stuClass = new JLabel("�γ���");
	  		JLabel stuTime  = new JLabel("�Ͽ�����");
	  		JLabel stuAdd   = new JLabel("�ص�");
	  		JComboBox<String> choiceClass = new JComboBox<String>();
	  		choiceClass.addItem("");
	  		choiceClass.addItem("��¥������");
	  		choiceClass.addItem("ʮ¥������");
	  		choiceClass.addItem("��¥������");
	  		JComboBox<String> choiceSou = new JComboBox<String>();
	  		choiceSou.addItem("");
	  		choiceSou.addItem("�˼ʽ���");
	  		choiceSou.addItem("����ѧ");
	  		choiceSou.addItem("��������");
	  		choiceSou.addItem("����");
	  		choiceSou.addItem("English");
	  		// ��������ѡ����Ѿ�ѡ���Ŀ
	  		JTextField tstuName = new JTextField(10);
	  		JTextField tstuTime = new JTextField(10);
	  		JButton  search = new JButton("--ȷ��ѡ��--");
	  		// ����������Ű�
	  		Choice.setBounds(140,40,120,16);
	  		Choice.setForeground(Color.getHSBColor(104, 154, 78));
	  		Choice.setFont(new Font("",Font.ITALIC,14));
	  		stuName.setBounds(30,320,80,30);
	  		tstuName.setBounds(90,320,80,30);
	    	stuClass.setBounds(190,320,80,30);
	    	choiceSou.setBounds(250,320,80,30);
	    	stuTime.setBounds(30,360,80,30);
	    	tstuTime.setBounds(90,360,80,30);
	    	stuAdd.setBounds(190,360,80,30);
	    	choiceClass.setBounds(250,360,80,30);
	    	
	    	search.setBounds(140,410,120,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
	    	// ��ť�ļ�����
		    search.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent e) {
					
					 String into=tstuName.getText().trim();
					 String time= tstuTime.getText().trim();
					 xcourse = choiceSou.getSelectedItem().toString();
					 xadd    = choiceClass.getSelectedItem().toString();
					// System.out.println(into + xname + time + xadd);
					 addinformation(into, xcourse,time,xadd);
				 }
			});
		    
			this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	    	right.add(stuName);
	    	right.add(tstuName);
	    	right.add(stuClass);
	    	right.add(stuTime);
	    	right.add(tstuTime); right.add(stuAdd); right.add(choiceClass);
	    	// ΪѧԱ�ṩ�α���Ϣ

	  		 vtitle = new Vector();
	  		 vtitle.addElement("��ʦ");vtitle.addElement("�γ�");
	  		vtitle.addElement("ʱ��");vtitle.addElement("����");
		    	vinformation = new Vector(); // Ŀǰ����Ϊ��
		    	table = new JTable(vinformation,vtitle);
		    	table.setPreferredScrollableViewportSize(new Dimension(330,280));
//		    	table.setForeground(Color.ORANGE);
		    	table.setRowHeight(22);table.setEnabled(false);
		    	scroll = new JScrollPane(table);
				scroll.setVerticalScrollBarPolicy(                
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scroll.setHorizontalScrollBarPolicy(
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		  		scroll.setBounds(30, 58, 330, 280);
		  		scroll.setSize(350, 260);;
		  		right.add(scroll);
		  		
		  		 try {
		
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement score = conn.createStatement(); // �൱�ڽ���һ���α�  cursor
			          // ִ��֮ǰ��Ҫ�ȼ����ж� 
			          String now_number = "select distinct course.id,count(distinct training_plan.person) as now_number,course.number from training_plan,course where course.id = training_plan.course_id  group by course_id";
			          ResultSet nownumber = score.executeQuery(now_number);
			          while(nownumber.next())
			          {
			        	  String a = nownumber.getString("now_number"); String b = nownumber.getString("number");
			        	  int c =Integer.parseInt(a);int d =  Integer.parseInt(b);
			        	  if(c>=d)
			        	  {
			        		  Statement score1 = conn.createStatement();
			        		  String e = nownumber.getString("id"); // ��õ�ǰ�γ���Ŀ��Ա�����Ŀ�Ŀ
			        		  String sesql ="SELECT course.name,person.name as teacher,classroom,classtime FROM course,person where course.teacher = person.id and course.id not in ("+e+") ";
					          ResultSet result = score1.executeQuery(sesql);
					          vinformation.clear(); // �������ǰ������	          
					          while(result.next())
					          {
					        	  Vector v  = new Vector(); //��������ÿһ�е�����
					        	  v.add(result.getString("teacher"));v.add(result.getString("name"));			        	  
					        	  v.add(result.getString("classtime"));  v.add(result.getString("classroom"));			        	
					        	  vinformation.add(v);
					          }
					          score1.close();
			        	  }
			          }
			         
			          table.updateUI(); // ���´��ڣ�����
			          
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 		
	    	
	    	
	    	
	    	right.add(search);
	  		//��������
	  		right.add(Choice);
	  		right.add(choiceSou);
	    	
	  		right.setBackground(Color.getHSBColor(135, 140, 115));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
	    	
	    }
	    public void inforinForcommon(){
	    	 JLabel theme = new  JLabel("--Ա��������Ϣ�޸Ľ���--");
	    	 // ���ǵ�Ա���Լ���Ȩ��  ��Ӧ���ṩ���е��޸Ĺ���
			  JLabel pass = new JLabel("����");
			  JTextField tpass = new JTextField(10);
			  JLabel job  = new JLabel("ְλ");
			  JTextField tjob = new  JTextField(10);
			  JLabel level = new JLabel("ѧ��");
			  JTextField tlevel = new JTextField(10);
			  JLabel add  = new JLabel("סַ");
			  JTextField tadd = new JTextField(10);
			  JLabel tel  =  new  JLabel("�绰");
			  JTextField ttel = new JTextField(10);
	 		  JLabel email = new JLabel("����");
	 		  JTextField temail = new JTextField(10);

			// ��ť����
			    JButton jBInsert = new JButton("����");
			    
			    
			    email.setBounds(10,190,60, 25);
			   temail.setBounds(50,190,70,25);
			    job.setBounds(130,190,60, 25);
			    tjob.setBounds(175,190,70,25);
			    level.setBounds(260,190,60, 25);
			    tlevel.setBounds(300,190,70,25);  
			    
			    pass.setBounds(10,240,60, 25);
			    tpass.setBounds(50,240,70,25);
			    add.setBounds(130,240,60, 25);
			    tadd.setBounds(175,240,70,25);
			    tel.setBounds(260,240,60, 25);
			    ttel.setBounds(300,240,70,25);  
			  // ����Ĳ���
			    theme.setBounds(100,50,200,16);
				  theme.setForeground(Color.BLACK);
				  theme.setFont(new Font("",Font.ITALIC,14));
				jBInsert.setBounds(280, 360, 80, 30);
				// ����ť��Ӽ���
				jBInsert.addMouseListener(new MouseAdapter() {
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 
						 // �����������ݵĲ���
		
						// String cid,cname,cpass,cauth,cspeci,cadd,ctel,cemail,cjob,clevel,cremark,cbirth;
						 String cemail = temail.getText().trim();String cjob = tjob.getText().trim();String clevel = tlevel.getText().trim(); 
						 String cpass = tpass.getText().trim();String cadd = tadd.getText().trim();String ctel = ttel.getText().trim();
						updateInformation(cemail,cjob,clevel,cpass,cadd,ctel);
					 }
				});
				
				
				 this.remove(this.right);
				 this.revalidate();
				 this.right = new JPanel();   	
				 right.setPreferredSize(new Dimension(390,500));
			  	 right.setLayout(null);
			     // ��Ӿ�������
				right.add(tel); right.add(ttel); right.add(email); right.add(temail); 
				right.add(jBInsert); 
				  right.add(job); right.add(tjob);
				 
				right.add(level); right.add(tlevel);   right.add(add); right.add(tadd);
				right.add(pass); right.add(tpass); right.add(theme);
			  // �����л��Ĺ̶���ʽ  ÿ�ζ�������ˢ��Ȼ�����¼���һ���µ�ҳ��  Ȼ��Ϳ���ʵ�ֶ�̬����
	    
		    	
		    	this.add(this.right);
		    	this.repaint();
				this.revalidate();
	    	
	    }
	    // Ա��ע��ҳ��
	    public void registerinfor() {
			  
			  
			  JLabel theme = new  JLabel("--Ա����Ϣע�����--");
			  JLabel pass = new JLabel("����");
			  JTextField tpass = new JTextField(10);
			  JLabel name = new JLabel("����");
			  JTextField tname = new JTextField(10);
			  JLabel sex  = new JLabel("�Ա�");
			  JComboBox<String> tsex = new JComboBox<String>();
			  tsex.addItem("");
			  tsex.addItem("��");
			  tsex.addItem("Ů");
			  JLabel birth = new JLabel("����");
			  JTextField tbirth = new JTextField(10);
			  JLabel depart = new JLabel("����");
			  JComboBox<String> tdepart = new JComboBox<String>();
			  tdepart.addItem("");
			  tdepart.addItem("��Ϣ��");
			  tdepart.addItem("Ӫ����");
			  tdepart.addItem("���粿");
			  tdepart.addItem("���²�");
			  JLabel job  = new JLabel("ְλ");
			  JTextField tjob = new  JTextField(10);
			  JLabel level = new JLabel("ѧ��");
			  JTextField tlevel = new JTextField(10);
			  JLabel speci = new JLabel("����");
			  JTextField tspeci = new JTextField(10);
			  JLabel add  = new JLabel("סַ");
			  JTextField tadd = new JTextField(10);
			  JLabel tel  =  new  JLabel("�绰");
			  JTextField ttel = new JTextField(10);
	 		  JLabel email = new JLabel("����");
	 		  JTextField temail = new JTextField(10);
			  JLabel remark  = new JLabel("��ע");
			  JTextField tremark = new JTextField(10);
			  
			  // ��ť����
			    JButton jBInsert = new JButton("ע��");

			    
			     name.setBounds(50,160,60, 25);
			    tname.setBounds(80,160,70,25);
			    sex.setBounds(170,160,60, 25);
			    tsex.setBounds(200,160,70,25);
			    birth.setBounds(290,160,60, 25);
			    tbirth.setBounds(350,160,70,25);  
			    
			    email.setBounds(50,220,60, 25);
			    temail.setBounds(80,220,70,25);
			    job.setBounds(170,220,60, 25);
			    tjob.setBounds(200,220,70,25);
			    level.setBounds(290,220,60, 25);
			    tlevel.setBounds(350,220,70,25);  
			    
			    pass.setBounds(50,280,60, 25);
			    tpass.setBounds(80,280,70,25);
			    add.setBounds(170,280,60, 25);
			    tadd.setBounds(200,280,70,25);
			    tel.setBounds(290,280,60, 25);
			    ttel.setBounds(350,280,70,25);  
			    // ����Ĳ���
			    depart.setBounds(50,330,60, 25);
			    tdepart.setBounds(80,330,70,25);
			    speci.setBounds(170,330,60, 25);
			    tspeci.setBounds(200,330,70,25);
			    remark.setBounds(290,330,60, 25);
			    tremark.setBounds(350,330,70,25);  
			    theme.setBounds(190,40,140,16);
				theme.setForeground(Color.BLACK);
				theme.setFont(new Font("",Font.ITALIC,14));
				jBInsert.setBounds(240, 400, 80, 30);
				// ����ť��Ӽ���
				jBInsert.addMouseListener(new MouseAdapter() {
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 
						 // �����������ݵĲ���
						String cname = tname.getText().trim(); String cpass = tpass.getText().trim();
						String cspeci = tspeci.getText().trim();String cadd = tadd.getText().trim();String ctel = ttel.getText().trim();
						String cemail = temail.getText().trim();String cjob = tjob.getText().trim();String cremark = tremark.getText().trim();
						String cbirth = tbirth.getText().trim(); String clevel = tlevel.getText().trim(); 
						String state = "-F-"; // Ĭ��Ϊ����ʽԱ��  
						String auth = "0";   // Ĭ��Ϊ��ͨԱ��Ȩ��
						xsex  = tsex.getSelectedItem().toString(); xdepart  = tdepart.getSelectedItem().toString();
						insertRegister(cname,xsex,cbirth,cemail,cjob,clevel,cpass,cadd,ctel,xdepart,cspeci,cremark,state,auth);
//						this.setVisible(false);
					 }
				});
				back.remove(this.right);
				back.revalidate();
				back_right = new JPanel();   	
				back_right.setPreferredSize(new Dimension(390,500));
				back_right.setLayout(null);
			     // ��Ӿ�������
				back_right.add(tel); back_right.add(ttel); back_right.add(email); back_right.add(temail);
				back_right.add(jBInsert);  back_right.add(name); back_right.add(tname);
				back_right.add(sex); back_right.add(tsex); back_right.add(remark); back_right.add(tremark); back_right.add(job); back_right.add(tjob);
				back_right.add(birth); back_right.add(tbirth); back_right.add(speci); back_right.add(tspeci); 
				back_right.add(level); back_right.add(tlevel);  back_right.add(depart); back_right.add(tdepart); back_right.add(add); back_right.add(tadd);
				back_right.add(pass); back_right.add(tpass); back_right.add(theme);
				// �����л��Ĺ̶���ʽ  ÿ�ζ�������ˢ��Ȼ�����¼���һ���µ�ҳ��  Ȼ��Ϳ���ʵ�ֶ�̬����   	
				back.add(back_right);
				back.repaint();
				back.revalidate();
		  }
	   // Ա����Ϣ����ҳ��
	    public void inforin() {
		  
		  //���·���һ�� ����ԱȨ�޵�����  ����һЩ ΢��  
		  JLabel theme = new  JLabel("--ѧԱ��Ϣ�������--");
		  JLabel auth = new JLabel("�û�Ȩ��");
		  JTextField tauth = new JTextField(10);
		  JLabel name = new JLabel("����");
		  JTextField tname = new JTextField(10);
		  JLabel job  = new JLabel("ְλ");
		  JTextField tjob = new  JTextField(10);
		  JLabel speci = new JLabel("����");
		  JTextField tspeci = new JTextField(10);
		  JLabel state = new JLabel("״̬");
		  JComboBox<String> tstate = new JComboBox<String>();
		  tstate.addItem("");
		  tstate.addItem("-T-");
		  tstate.addItem("-F-");
		  JLabel remark  = new JLabel("��ע");
		  JTextField tremark = new JTextField(10);
		  
		  // ��ť����
		    JButton jBInsert = new JButton("��Ϣ����");

		    auth.setBounds(245,260,60, 25);
		    tauth.setBounds(300,260,70,25);  
		    
		     name.setBounds(10,260,60, 25);
		    tname.setBounds(40,260,70,25);
		    remark.setBounds(130,260,60, 25);
		    tremark.setBounds(160,260,70,25);

		    job.setBounds(245,320,60, 25);
		    tjob.setBounds(300,320,70,25);

		    
		    speci.setBounds(10,320,60, 25);
		    tspeci.setBounds(40,320,70,25);
		    // ����Ĳ���
		    state.setBounds(130,320,60, 25);
		    tstate.setBounds(160,320,70,25);
		    
		    theme.setBounds(120,40,140,16);
			theme.setForeground(Color.BLACK);
			theme.setFont(new Font("",Font.ITALIC,14));
			jBInsert.setBounds(200, 390, 100, 26);
			// ����ť��Ӽ���
			jBInsert.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent e) {
					 
					 // �����������ݵĲ���
					String cname = tname.getText().trim(); 
					String cspeci = tspeci.getText().trim();
					String cjob = tjob.getText().trim();String cremark = tremark.getText().trim();
					String cauth = tauth.getText().trim();
					String cstate = tstate.getSelectedItem().toString(); // ��ѡ���ְλ״̬��ȡ����
					// ���ݵ�˳����� name remark auth speci state 	job
//					System.out.println(cname + cspeci+ cjob+ cauth+cstate+cremark);
					adminUpdateInfor(cname,cremark,cauth,cspeci,cstate,cjob);
				 }
			});
			this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
		  	right.setLayout(null);
		  	
		  	
		  	
		  	 	vtitle = new Vector();
		    	vtitle.addElement("ѧԱ����"); vtitle.addElement("�Ա�"); vtitle.addElement("����");
		    	vtitle.addElement("ְλ"); vtitle.addElement("״̬"); vtitle.addElement("Ȩ��"); vtitle.addElement("��ע");
		    	vinformation = new Vector(); // Ŀǰ����Ϊ��
		    	table = new JTable(vinformation,vtitle);
		    	table.setPreferredScrollableViewportSize(new Dimension(330,280));
//		    	table.setForeground(Color.ORANGE);
		    	table.setRowHeight(22);
		    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
		  		table.setEnabled(false);
		  		 try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement score = conn.createStatement(); // �൱�ڽ���һ���α�  cursor
			         String sesql ="select name,sex,department,job,state,authority,remark from person where authority ='0'";
			          ResultSet result = score.executeQuery(sesql);
			          vinformation.clear(); // �������ǰ������
//			          vinformation = new Vector(); // ���½���һ�����Զ�̬���������
			          
			          while(result.next())
			          {
			        	  Vector v  = new Vector(); //��������ÿһ�е�����
			        	  v.add(result.getString("name"));
			        	  v.add(result.getString("sex"));v.add(result.getString("department"));
			        	  v.add(result.getString("job"));v.add(result.getString("state"));	
			        	  v.add(result.getString("authority"));v.add(result.getString("remark"));
			        	  vinformation.add(v);
			          }
			          table.updateUI(); // ���´��ڣ�����
			          
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 	
		  		scroll = new JScrollPane(table);
				scroll.setVerticalScrollBarPolicy(                
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scroll.setHorizontalScrollBarPolicy(
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		  		scroll.setBounds(30, 58, 330, 280);
		  		scroll.setSize(320, 180);;
		  		right.add(scroll);
		     // ��Ӿ�������
			 right.add(state); right.add(tstate);
			right.add(jBInsert);  right.add(name); right.add(tname);
			right.add(remark); right.add(tremark); right.add(job); right.add(tjob);
			 right.add(speci); right.add(tspeci); right.add(auth); right.add(tauth);			
			 right.add(theme);
			// �����л��Ĺ̶���ʽ  ÿ�ζ�������ˢ��Ȼ�����¼���һ���µ�ҳ��  Ȼ��Ϳ���ʵ�ֶ�̬����   	
	    	this.add(this.right);
	    	this.repaint();
			this.revalidate();
	  }
	   // ����Ա �������ݽ���
	    public void adminUpdateInfor(String name,String remark,String auth,String speci,String state,String job) { 
		  if(name.equals("") && remark.equals("") && auth.equals("") && speci.equals("") && state.equals("") &&job.equals(""))
		  {
			 JOptionPane.showMessageDialog(null, "ѡ��ȫ��Ϊ��", "��ִ��", JOptionPane.ERROR_MESSAGE);
		  }else {	String tempname = md5Password(name); // ת����MD5������Ա�ľ�ȷ���
		  //�����������ݹ����� Ȼ�������Ӧ��������
		  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
		  try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				  Statement sql = conn.createStatement();  	
		         // ��Ϊ���޸���Ϣ �����û�����ȫ���޸� ���� ��Ҫ����sql ��zuzhuan
//				  String name,String remark,String auth,String speci,String state,String job
				 if(remark.equals("")) {String remarksql = "select remark from person where username='" +tempname+"'"; ResultSet remarkset = sql.executeQuery(remarksql); while(remarkset.next()) {remark= remarkset.getString("remark");} }
				 if(job.equals("")) {String jobsql = "select job from person where username='" +tempname+"'"; ResultSet jobset = sql.executeQuery(jobsql); while(jobset.next()) {job= jobset.getString("job");} }
				 if(auth.equals("")) {String authsql = "select authority from person where username='" +tempname+"'"; ResultSet authset = sql.executeQuery(authsql); while(authset.next()) {auth= authset.getString("authority");} }
				 if(speci.equals("")) {String spesql = "select speciaty from person where username='" +tempname+"'"; ResultSet speset = sql.executeQuery(spesql); while(speset.next()) {speci= speset.getString("speciaty");} }
				 if(state.equals("")) {String statesql = "select state from person where username='" +tempname+"'"; ResultSet stateset = sql.executeQuery(statesql); while(stateset.next()) {state= stateset.getString("state");} }
				 String  upsql = "update person set remark='" + remark;
		         upsql = upsql  +"',job='"+ job+"',authority='"+auth;
		         upsql = upsql + "',speciaty='" + speci +"',state='"+state; 
		         upsql = upsql + "' where username ='" + tempname +"'";
//		         System.out.println(upsql);
		         sql.executeUpdate(upsql);
		         JOptionPane.showMessageDialog(null, "��ѧԱ��Ϣ�޸ĳɹ�", "����޸�", JOptionPane.PLAIN_MESSAGE);

		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 	 
			  
		  }
	  }
	   // ��ͨ�û���Ϣע�� cname,xsex,cbirth,email,cjob,clevel,cpass,cadd,ctel,xdepart,cspeci,cremark,state,auth
	  public void insertRegister(String cname,String xsex,String cbirth,String email,String cjob,String clevel,String cpass,String cadd,String ctel,String xdepart,String cspeci,String cremark,String state,String auth)
	  {
		  if(cname.equals("") || xsex.equals("") || cbirth.equals("") || email.equals("") || cjob.equals("") || clevel.equals("") || cpass.equals("") || cadd.equals("") || ctel.equals("")||xdepart.equals(""))
		  {
			  JOptionPane.showMessageDialog(null, "���˱�ע�ͼ��ܣ������ܳ��ֿ�ֵ", "��Ϣ��д�д���", JOptionPane.ERROR_MESSAGE);
		  }else {
			  // ������� ���������ݽ��йر�
			  //System.out.println(id+pass+auth+name+sex+birth+depart+job+level+speci+add+tel+email+state+remark);
			  //�����������ݹ����� Ȼ�������Ӧ��������  
			  String count= null;
			  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			  try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password); // ���ټ���Ƿ���ȷ�������ݿ��� һ��û�������
			         Statement sql = conn.createStatement();
			         String username = md5Password(cname);
			         String countsql= "select count(*) from person";
			         ResultSet countOfperson = sql.executeQuery(countsql);
			         while(countOfperson.next())
			         {
			        	count  = countOfperson.getString("count(*)"); 
			         }
			         int num = Integer.parseInt(count);
			         String  insql = "insert into person(id,passwd,authority,name,sex,birthday,department,job,edu_level,speciaty,address,tel,email,state,remark,username) values('";
			         insql = insql + (num+1) +"','" + cpass+ "','";
			         insql = insql + auth + "','";
			         insql = insql + cname+ "','"+xsex+"','"+cbirth+"','"+xdepart +"','"+cjob+"','";
			         insql = insql +clevel + "','"+cspeci+"','" + cadd +"','"+ ctel+"','"+email+"','";
			         insql = insql +  state +"','" + cremark +"','"+ username+ "')";
			         //System.out.println(insql);
			          sql.execute(insql);
			          JOptionPane.showMessageDialog(null, "��Ϣע��ɹ�", "ok", JOptionPane.PLAIN_MESSAGE);
			          login();
			          back.setVisible(false);
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 	  
		  }
	  }
	  // ���ҿγ�����
	  public void searchS(String name,String course) {
		  // �Դ��ݹ����Ĳ������п�ֵ���ж�
		  
		  System.out.print(name+ course);
		  //select  distinct training_plan.score,person.name,course.name,training_plan.exam_date from training_plan,course,person where
//		  ( training_plan.course='�˼ʽ���') and person.id= training_plan.person and training_plan.course = course.name
		  String sesql = "select  distinct training_plan.score,person.name,course.name as name1,training_plan.exam_date from training_plan,course,person where";
			  System.out.println("�������ֵ�");
			  sesql = sesql + "(person.name='"+ name +"' and training_plan.course='"+course+"') and person.id= training_plan.person and training_plan.course = course.name";
		  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			 try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		          Statement score = conn.createStatement(); // �൱�ڽ���һ���α�  cursor
		          
		          ResultSet result = score.executeQuery(sesql);
		          vinformation.clear(); // �������ǰ������
		          //vinformation = new Vector(); // ���½���һ�����Զ�̬���������
		          
		          while(result.next())
		          {
		        	  Vector v  = new Vector(); //��������ÿһ�е�����
 		        	  v.add(Integer.valueOf(result.getInt("score")));
		        	  v.add(result.getString("name"));
		        	  v.add(result.getString("name1"));
		        	  v.add(result.getString("exam_date"));
		        	  vinformation.add(v);
		          }
		          table.updateUI(); // ���´��ڣ�����
		          
		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 
	  }
	 public void addinformation(String teacher,String course,String time,String add){
		 
		 if(teacher.equals("") || course.equals("") || time.equals("") || add.equals(""))
		 {
			 JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
		 }else {
			 Vector ids = new Vector();
			 String username = md5Password(teacher);
			 String id = null; String perid = null;
			 url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			 try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		          Statement sql = conn.createStatement();
		          //  ��ȥ�������� id Ȼ����б�����Ϣ
		         String idsql = "select course.id from course \r\n" + 
		         		"where name = '"+ course +"' and classroom='"+add+"' and classtime ='"+time+"' and teacher=(select id from person where username = '"+username+"') ";
		         //System.out.println(idsql);
		         ResultSet courseid = sql.executeQuery(idsql);
		         while(courseid.next())
		         {
		        	id = courseid.getString("id");
		         }
		         id = id;
		        //ȫ�ֵ� xname 
		         String nameid = "select id from person where username = '" + md5Password(staname)+"'";
		         ResultSet nid = sql.executeQuery(nameid);
		         while(nid.next())
		         {
		        	perid = nid.getString("id");
		         }
		         String  insql = "insert into training_plan(person,course,course_id) values('";
		         insql = insql + perid + "','";  insql = insql + course + "','";  insql = insql+ id +"')";
		         System.out.println(insql);
		          sql.executeUpdate(insql);
		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 
		 }	 
	 }
	 // ���²��������������� ��������ֱ�Ӻ����������޸� Ȼ����������޸ľͿ�����
	 // �����������ұ����������
	 public void updateInformation(String email,String job,String level,String pass,String add,String tel){
	  String tempname = md5Password(staname); // ת����MD5������Ա�ľ�ȷ���
	  //�����������ݹ����� Ȼ�������Ӧ��������
	  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
	  try {
			 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			  Statement sql = conn.createStatement();  	
	         // ��Ϊ���޸���Ϣ �����û�����ȫ���޸� ���� ��Ҫ����sql ��zuzhuan
			 if(pass.equals("")) {String passsql = "select passwd from person where username='" +tempname+"'"; ResultSet passset = sql.executeQuery(passsql); while(passset.next()) {pass= passset.getString("passwd");} }
			 if(email.equals("")) {String emailsql = "select email from person where username='" +tempname+"'"; ResultSet emailset = sql.executeQuery(emailsql); while(emailset.next()) {email= emailset.getString("email");} }
			 if(job.equals("")) {String jobsql = "select job from person where username='" +tempname+"'"; ResultSet jobset = sql.executeQuery(jobsql); while(jobset.next()) {job= jobset.getString("job");} }
			 if(add.equals("")) {String addsql = "select address from person where username='" +tempname+"'"; ResultSet telset = sql.executeQuery(addsql); while(telset.next()) {add= telset.getString("address");} }
			 if(tel.equals("")) {String telsql = "select tel from person where username='" +tempname+"'"; ResultSet addset = sql.executeQuery(telsql); while(addset.next()) {tel= addset.getString("tel");} }
			 if(level.equals("")) {String levelssql = "select edu_level from person where username='" +tempname+"'"; ResultSet levelset = sql.executeQuery(levelssql); while(levelset.next()) {level= levelset.getString("edu_level");} }
			 String  upsql = "update person set passwd='" + pass;
	         upsql = upsql  +"',job='"+ job+"',edu_level='"+level+"',address='" +add;
	         upsql = upsql + "',tel='"+ tel+"',email='"+email+"' where username ='" + tempname +"'";
	          sql.executeUpdate(upsql);
	          JOptionPane.showMessageDialog(null, "��Ϣ���ĳɹ�", "������Ϣ", JOptionPane.PLAIN_MESSAGE);
	    	}catch(SQLException e) {
			 	e.printStackTrace();
	    	}catch(Exception e) {
			 	e.printStackTrace();
	    	} 	 
	 }
	public void con(String name, String pass) {
		// 
		// ����û��������������һ��Ϊ����ô�Ͳ���ִ������Ĳ���
		if(name.equals("")|| pass.equals(""))
		{
			JOptionPane.showMessageDialog(null, "��������", "�����򶼱��벻Ϊ��", JOptionPane.WARNING_MESSAGE);
		}else {
			
			//System.out.print(name + pass);
			String uname = md5Password(name); // name ��Ӧ MD5 �ַ���
			String truepass = ""; // ������¼��ȷ������
			String le = ""   ;  // ��������Ȩ�޵���֤
			String count = "";
		try {
			 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
	         Statement usersql = conn.createStatement();
	         String exists = "select count(*) from person where username = '" +uname+"'";
	         ResultSet existname = usersql.executeQuery(exists);
	         while(existname.next())
	         {
	        	 count = existname.getString("count(*)");
	         }
	         if(count.equals("0"))
	         {
	        	 JOptionPane.showMessageDialog(null, "���û�������", "�û�������", JOptionPane.ERROR_MESSAGE);
	         }else {
	        	 Statement sql = conn.createStatement();
		          ResultSet result = sql.executeQuery("select * from person where username = '" + uname + "'");
		          while(result.next())
		          {
		        	  truepass = result.getString("passwd");
		        	  le = result.getString("authority");
		          }
			      if(truepass.equals(pass)) {
			        loo.setVisible(false);
			        if(le.equals("1"))
			        {
			        	 theme();
			        }else{	
			        staname = name;
			        System.out.print("��û����Ӧ��Ȩ�ޣ��޷�����");
			        themeToo();
			        }
			        JOptionPane.showMessageDialog(null, "��½�ɹ�", "��ʾ", JOptionPane.PLAIN_MESSAGE);
			        }else {
			        JOptionPane.showMessageDialog(null, "������������������","�������", JOptionPane.WARNING_MESSAGE);
		    	}
			}
		}catch(SQLException e) {
			 	e.printStackTrace();
	    }catch(Exception e) {
			 	e.printStackTrace();
	    } 
	}
}
	// ������
	public static void main(String [] args){
		master one = new master();
//		one.theme();
		one.login();
//		one.themeToo();
	}
}