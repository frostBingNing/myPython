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

// 课程设置界面
public class master extends JFrame implements ActionListener{

	Connection conn ;
	String url = "jdbc:mysql://127.0.0.1:3306/staff";
	// MySQL配置时的用户名
	String user = "root"; 	     
	// MySQL配置时的密码
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
	// 一些需要的 全局变量
	String xname,xteacher,xtime,xbook,xnumber,String ,xadd;
	String xdepart,xsex;
	String xle,xcourse,xstate;
	String staname; // 用来记录当前用户的账号名
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
	}
	
    public master() {
		System.out.println("欢迎使用本gui系统");
		 // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";
		try { 
	            // 加载驱动程序
			   Class.forName(driver);
	            // 连续数据库
	          //this.conn = DriverManager.getConnection(url, user, password);
//	          System.out.print("驱动加载成功");
		 }catch(ClassNotFoundException e) {

	            System.out.println("Sorry,can`t find the Driver!"); 
	            e.printStackTrace();
		 }
    }
    
    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public void new_user_register() {
    	back.setLayout(new BorderLayout());
    	// 想要展示的样式  都需要继承上面的全局变量
    	registerinfor();
 		 
    	 back_right.setPreferredSize(new Dimension(500,500));
    	 back_right.setLayout(null);
//    	 back_right.setBackground(Color.CYAN);
		 
    	 back.add(back_right,BorderLayout.EAST);
    	 back.setTitle("员工培训管理系统");
    	 back.setSize(500,500);
    	 back.setVisible(true);
    	 back.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 back.setResizable(false);
    	
    	//这样 再点击注册时候 把当前的 register 隐藏  然后显示 login()
    	
    	// 提供一个返回login()的声明  让用户可以在不退出的状态下  注册完以后直接登录
    }
    
	public void login() {
		
		//取消布局管理器
		//账号信息排版
		//  需要在这个界面增加一个  学员 也就是 普通用户的注册功能
		JTextField user = new JTextField(30);
		user.setBounds(210,80,80,26);
		JPasswordField pass = new JPasswordField(30);
		pass.setBounds(210,120,80,26);
		// 登录信息安排 
		JLabel userName = new JLabel("用户名");
		userName.setBounds(120,80,80,26);
//		userName.setForeground(Color.GREEN);
		userName.setFont(new Font("",Font.ITALIC,20));
		JLabel passWord = new JLabel("密码");
		passWord.setBounds(120,120,80,26);
//		passWord.setForeground(Color.GREEN);
		passWord.setFont(new Font("",Font.ITALIC,20));
		// 注册模板的创建  // 登录按钮的排版	
		// login --- 登录   register --- 注册
		JButton register = new JButton("学员注册");
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
	    
		JButton log = new JButton("--登录--");
		log.setBounds(220,190,86,26);
//		log.setForeground(Color.PINK);
		
		log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
            	String name=user.getText().trim();
            	String pass1=new String(pass.getPassword());
            	//System.out.print(name + pass1);
            	// 这里需要完善 就是没有出入信息 提示请输入信息 。。。
            	con(name,pass1);
            }
        });
		// 添加到 JFrame里面  进行可视化 操作
		loo.add(log);
		loo.add(userName);
		loo.add(passWord);
		loo.setLayout(null);
		loo.add(user);
		loo.add(pass); loo.add(register);
		loo.setTitle("员工培训管理系统");
		loo.setSize(400,300);  	//  主窗体大小
//		this.setLocation(500,500); //  初始化页面位置
	 	loo.setVisible(true);   	// 可视化操作
		loo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 鼠标可以退出
		loo.setResizable(false);  	// 不可以更改大小
	}
	// 主界面设置
	public void themeToo(){
		  
		this.setLayout(new BorderLayout()); // 同样设置成为 边界模块 然后分为左右两部分
		
		JLabel themeToo = new JLabel(" 学员界面   ");
		// 增加相应的按钮
		JButton searchscore = new JButton("成绩查询");
		JButton updateinfor = new JButton("信息修改");
		JButton choiceScore = new JButton("我要选课");
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
		// 按钮的排版设置
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
		 this.setTitle("员工培训管理系统");
		 this.setSize(500,500);
		 this.setVisible(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setResizable(false);
 	}
	public void theme(){

		 this.setLayout(new BorderLayout()); // 取消布局管理器  采用用户自定义的方式来布局   
		 // 把所有的功能添加在左边的panel 里面
		 //  所有功能展示页面
		
		 JLabel theme  = new JLabel("  管理员界面     ");
		 //  增加所有的界面按钮
		 JButton courseSet     = new JButton("课程设置");
		 JButton searchCourse  = new JButton("选课结果");
		 JButton numberStu     = new JButton("学员名单");
		 JButton apprise       = new JButton("培训成绩");
		 JButton information   = new JButton("学员信息");
		 JButton count  	   = new JButton("培训统计");
//		 JButton allcourse 	   = new JButton("开设课程");
		 JButton addscore      = new JButton("成绩录入");
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
  		 left.add(theme);      // 添加主标题	
  		 right.setPreferredSize(new Dimension(390,500));
  		 right.setLayout(null);
//  		 right.setBackground(Color.CYAN);
		 
		 this.add(right,BorderLayout.EAST);
		 this.add(left,BorderLayout.WEST);
		 this.setTitle("员工培训管理系统");
		 this.setSize(500,500);
		 this.setVisible(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setResizable(false);
	}
	public void setcourses(String id) {
		if(id.equals(""))
		{
			JOptionPane.showMessageDialog(null,"必须输入课程编号才可以删除课程", "操作错误", JOptionPane.ERROR_MESSAGE);
		}else {
			String person_id = "";
			url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				 Statement sql1 = conn.createStatement();
		          String get_person1 = "delete from course where id ='" + id+ "'";
		          sql1.execute(get_person1);  // 删除course 里面的信息
		          Statement sql = conn.createStatement();
		          String get_person = "select person from training_plan where course_id ='" + id+ "'";
		          ResultSet students = sql.executeQuery(get_person); 
		          while(students.next()) 
		          {person_id = students.getString("person");
		          Statement sql2 = conn.createStatement();
		          String get_person2 = "delete  from training_plan where person ='" + person_id+ "' and course_id='"+id+"'";
		          sql2.execute(get_person2);          
		          }
		          JOptionPane.showMessageDialog(null, "课程已经成功删除", "课程删除", JOptionPane.PLAIN_MESSAGE);
		          
		          try {
		 			 String staffteacher,tempid,tempname,tempbook,temproom,temptime,tempintro,tempnumber,tempcourse1,tempstate = "";
		 			 String the_sql ="";
		 			 Connection conn2 = (Connection) DriverManager.getConnection(url, user, password);
		 	          Statement sql2 = conn2.createStatement();
		 	          vinformation.clear(); // 清除掉以前的数据
		 	          String allinfor ="select * from course";
		 	          ResultSet staffcourses = sql2.executeQuery(allinfor);
		 	          while(staffcourses.next())
		 	          {
		 	        	staffteacher = staffcourses.getString("teacher");  
		 	        	tempid = staffcourses.getString("id");
		 	             tempbook = staffcourses.getString("book");
		 	            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
		 	            tempintro = staffcourses.getString("intro");  tempnumber = staffcourses.getString("number"); // 剩下的六项全部提取
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
		
		// 添加课程的函数  --- 课程设置面板
		// 主要信息查询表 
	   	JLabel course = new JLabel("课程信息设置界面");
		JLabel name = new JLabel("课程名");
		JLabel teacher = new JLabel("教师");
		JLabel intro  = new  JLabel("简介");
		JLabel book  = new JLabel("教材");
		JLabel Class = new JLabel("地点");
		JLabel number = new JLabel("人数");
		JLabel time = new JLabel("时间");
		JLabel state = new JLabel("状态");
		JLabel courseid = new JLabel("ID");
		// 保存所选字段的内容
		// 所有的下拉选项
		JTextField cteacher = new JTextField(10);
		JTextField courseId = new JTextField(10);
		JComboBox<String> cname = new JComboBox<String>();
		JComboBox<String> cbook = new JComboBox<String>();
		JComboBox<String> cnumber = new JComboBox<String>();
		JComboBox<String> ctime   = new JComboBox<String>();
		JComboBox<String> cstate = new JComboBox<String>();
		JComboBox<String> cadd = new JComboBox<String>();
		// 增加获取
		cname.addItem("");
		cname.addItem("人际交往");
		cname.addItem("管理学");
		cname.addItem("世界妙事");
		cname.addItem("读者");
		cname.addItem("English");
		cbook.addItem("");
		cbook.addItem("人际处理原则");
		cbook.addItem("管理妙招");
		cbook.addItem("新闻轶事");
		cbook.addItem("读者");
		cbook.addItem("英语");
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
		cadd.addItem("五楼会议室");
		cadd.addItem("八楼会议室");
		cadd.addItem("十楼会议室");
		
// ***** textarea 没有设置自动换行   后面需要处理一下
		JTextArea  tintro =  new JTextArea();
		tintro.setLineWrap(true);
		// 按钮设置
	    JButton jBInsert = new JButton("添加");
	    JButton jBUpdate = new JButton("删除");
	     
	    course.setBounds(140,30,120,16);
	    course.setForeground(Color.BLACK);
		course.setFont(new Font("",Font.ITALIC,14));
		// 给按钮添加监听
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
				 String theID = courseId.getText().toString(); // 获取里面的课程id
				 setcourses(theID);  // 只需要传递一个id
			 }
		});
		// 放置组件模块  并且进行样式的修改
		// 位置的设计
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
		
		// 设置label 的字体样式
		jBInsert.setBorderPainted(false);
		jBInsert.setFont(new java.awt.Font("华文行楷", 1, 15));
		jBInsert.setFocusPainted(false); //不绘制聚焦
		jBInsert.setBorder(BorderFactory.createRaisedBevelBorder()); // 凸起
		jBUpdate.setBorderPainted(false);
		jBUpdate.setFont(new java.awt.Font("华文行楷", 1, 15));
		jBUpdate.setFocusPainted(false); //不绘制聚焦
		jBUpdate.setBorder(BorderFactory.createRaisedBevelBorder()); // 凸起
		
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
		
//	    // 按钮监听器
//	    jBInsert.addActionListener(this);
//	    jBUpdate.addActionListener(this);
	    
		this.remove(this.right);
		this.revalidate();
		this.right = new JPanel();   	
		right.setPreferredSize(new Dimension(390,500));
  		right.setLayout(null);
  		vtitle = new Vector();
  		vtitle.addElement("ID");
    	vtitle.addElement("课程"); vtitle.addElement("教师姓名");
    	vtitle.addElement("教材"); vtitle.addElement("教室"); vtitle.addElement("时间");vtitle.addElement("人数");
    	vtitle.addElement("状态");vtitle.addElement("课程简介");
    	vinformation = new Vector();
    	url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				try {
					 String staffteacher,tempid,tempname,tempbook,temproom,temptime,tempintro,tempnumber,tempcourse,tempstate = "";
					 String the_sql ="";
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // 清除掉以前的数据
			          String allinfor ="select * from course";
			          ResultSet staffcourses = sql.executeQuery(allinfor);
			          while(staffcourses.next())
			          {
			        	staffteacher = staffcourses.getString("teacher");  
			        	tempid = staffcourses.getString("id");
			             tempbook = staffcourses.getString("book");
			            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
			            tempintro = staffcourses.getString("intro");  tempnumber = staffcourses.getString("number"); // 剩下的六项全部提取
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
    	// 设置表格的样式
	    table = new JTable(vinformation,vtitle);
    	table.setPreferredScrollableViewportSize(new Dimension(400,200));
    	table.setForeground(Color.BLUE);
    	table.setRowHeight(22);
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
    	table.setEnabled(false);
        /*用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看*/ 
//    	JTableHeader minetitle = table.getTableHeader(); // 获得标题对象
//    	minetitle.setPreferredSize(new Dimension(table.getWidth(),16));// 设置表头大小
//        table.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
    	scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(                
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(                
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(20,50,0,0);  // 因为有最新一个级别的限制 所以没有用  后面的长宽
  		scroll.setSize(360,200);  
  		right.add(scroll);
	    // 重新绘制的面板 然后添加组件
  		right.add(courseid); right.add(courseId);
	    right.add(name);right.add(cname);right.add(teacher);right.add(cteacher);right.add(intro);right.add(tintro);right.add(course);
	    right.add(book);right.add(cbook);right.add(Class);right.add(cadd);right.add(number);right.add(cnumber);right.add(time);right.add(ctime);right.add(state);
	    right.add(cstate); right.add(jBUpdate);right.add(jBInsert);right.setBackground(Color.getHSBColor(135, 140, 115));
	    // 重新布局 管理 一下右边的jp
		this.add(right, BorderLayout.EAST);
		this.repaint();
		this.revalidate();
	   
	}
	//课程设置连接数据库方法函数
	public void setCourse(String name,String teacher,String into ,String book,String classroom ,String number ,String time,String state){
		// 这样就可以添加汉字了  允许unicode and  编码是 utf8
	   if(name.equals("") ||teacher.equals("") || into.equals("") || book.equals("") || classroom.equals("") || number.equals("") || state.equals("")|| time.equals("")) {
		   JOptionPane.showMessageDialog(null, "数据有空值", "不允许有空值", JOptionPane.ERROR_MESSAGE);
	   }else {
		   String uername = md5Password(teacher); // 将教师 的姓名转化成 md5  唯一性
			int  courseid = 0; int teacherid =0; String tempteacher = null,tempcourse = null; 
			url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		          Statement sql = conn.createStatement();
		          //Duplicate entry '1' for key 'PRIMARY'  这里必须保证在person 表里面存在相应的主键id
		          //  如果你的SQL 语句是诸如update,insert的更新语句，应该用statement的execute()方法，如果用的是statement的executeQuery()就会出现上诉问题
		          //  需要注意的是 id  state 都是数字         teacher 是对应的教师本身的 person id
		         String idofteacher = "select id  from person where username ='" + uername+"'";
		         ResultSet idofname = sql.executeQuery(idofteacher); while(idofname.next()) {tempteacher = idofname.getString("id");}
		         teacherid = Integer.parseInt(tempteacher);
		         //  还 
//		         String numbersql = "select count(*)  from course ";
//		         ResultSet numberset = sql.executeQuery(numbersql); while(numberset.next()) {tempcourse = numberset.getString("count(*)");}
//		         courseid = Integer.parseInt(tempcourse);
		         
		          String insql = "insert into course(name,teacher,intro,book,classroom,number,classtime,state) values('";
		          insql = insql  + name + "'," + teacherid +",'" + into +"','" + book +"','" + classroom +"','" +number+"','"+ time +"',"+state+")";
		          sql.execute(insql);
		          JOptionPane.showMessageDialog(null, "课程信息添加成功","完成添加", JOptionPane.PLAIN_MESSAGE);
		          
		          try {
		 			 String staffteacher,tempid,tempname,tempbook,temproom,temptime,tempintro,tempnumber,tempcourse1,tempstate = "";
		 			 String the_sql ="";
		 			 Connection conn2 = (Connection) DriverManager.getConnection(url, user, password);
		 	          Statement sql2 = conn2.createStatement();
		 	          vinformation.clear(); // 清除掉以前的数据
		 	          String allinfor ="select * from course";
		 	          ResultSet staffcourses = sql2.executeQuery(allinfor);
		 	          while(staffcourses.next())
		 	          {
		 	        	staffteacher = staffcourses.getString("teacher");  
		 	        	tempid = staffcourses.getString("id");
		 	             tempbook = staffcourses.getString("book");
		 	            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
		 	            tempintro = staffcourses.getString("intro");  tempnumber = staffcourses.getString("number"); // 剩下的六项全部提取
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
		// 员工选课 信息  结果窗体  
	public void searchCour(){
				
			JLabel information = new JLabel("-员工课表信息总览-");
			information.setBounds(140,26,140,16);
			information.setForeground(Color.BLUE);
			information.setFont(new Font("",Font.ITALIC,14));
			JLabel name = new JLabel("员工姓名");
			JLabel course = new  JLabel("课程");
			JTextField name1 = new JTextField(10);
		    JComboBox<String> tcourse = new JComboBox<String>();
		    tcourse.addItem("");
		    tcourse.addItem("人际交往");
		    tcourse.addItem("管理学");
		    tcourse.addItem("世界妙事");
		    tcourse.addItem("读者");
		    tcourse.addItem("English");
		    
		    name.setBounds(100,370,100,26);
		    name1.setBounds(200,370,100,26);
		    course.setBounds(100,400,100,26);
		    tcourse.setBounds(200,400,100,26);
		    
		    JButton  search = new JButton("--查询--");
		    search.setBounds(140,430,80,30);
		    search.setForeground(Color.pink);
		    search.setBackground(Color.darkGray);
		    search.setFont(new Font("楷体",Font.ROMAN_BASELINE,15));
		    search.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		    search.setFocusPainted(false);
		    search.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // 进行逻辑处理即可
	            	String name=name1.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString(); // 将选择的科目 更新
	            	// 按照自己的逻辑 一共分为三种情况   1 学生 + 课程  精确查找  2 学生相关  3 课程相关   
//	            	System.out.println(name + " "+xcourse);  
	                allCoursesinfor(name,xcourse);
	                JOptionPane.showMessageDialog(null, "课表信息显示成功", "查询成功", JOptionPane.PLAIN_MESSAGE);
	            }
	        });
		    this.remove(this.right);
			this.revalidate();  
			// 保证可以重新绘制图像
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		// 下面尝试添加table 
	        vtitle = new Vector();
	    	vtitle.addElement("课程"); vtitle.addElement("教师姓名"); vtitle.addElement("学生姓名");
	    	vtitle.addElement("教材"); vtitle.addElement("教室"); vtitle.addElement("时间");vtitle.addElement("人数");
	    	vtitle.addElement("状态");vtitle.addElement("课程简介");
	    	vinformation = new Vector();
	    	// 设置表格的样式
 	    	table = new JTable(vinformation,vtitle);
	    	table.setPreferredScrollableViewportSize(new Dimension(400,300));
//	    	table.setForeground(Color.ORANGE);
	    	table.setRowHeight(22);table.setEnabled(false);
	    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
	        /*用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看*/ 
//	    	JTableHeader minetitle = table.getTableHeader(); // 获得标题对象
//	    	minetitle.setPreferredSize(new Dimension(table.getWidth(),16));// 设置表头大小
//	        table.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
	    	scroll = new JScrollPane(table);
			scroll.setVerticalScrollBarPolicy(                
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(                
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setBounds(20,50,0,0);  // 因为有最新一个级别的限制 所以没有用  后面的长宽
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
		    	// 提取所有的信息
		    	allinfor = "select person.name as student,course.name as course, course.teacher ,course.intro,course.book,course.classroom,course.number,course.classtime,course_state.description" + 
		    			" from course,training_plan,person,course_state where course.id = training_plan.course_id and training_plan.person = person.id and course_state.code = course.state;";
		    	url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // 清除掉以前的数据
			          ResultSet staffcourses = sql.executeQuery(allinfor);
			          while(staffcourses.next())
			          {
			        	staffteacher = staffcourses.getString("teacher");  
			            tempname = staffcourses.getString("student"); tempbook = staffcourses.getString("book");
			            temproom = staffcourses.getString("classroom"); temptime = staffcourses.getString("classtime");
			            tempintro = staffcourses.getString("intro");  number = staffcourses.getString("number"); // 剩下的六项全部提取
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
			          // 每次在查询的时候  最好建立一个新的sql  然后执行完 之后释放 自己
					  Statement sql = conn.createStatement();
					  allinfor = "select distinct training_plan.person,course.name as course,course.teacher,intro,book,classroom, number,classtime,course_state.description from course,course_state,training_plan where course.id in" + 
					  		"(select distinct course_id from training_plan where course ='"+ course+"') and course.state = course_state.code";
			          vinformation.clear(); // 清除掉以前的数据
			          ResultSet allinformations  = sql.executeQuery(allinfor);
			          while(allinformations.next())
			          {
			        	  staffteacher = allinformations.getString("teacher");  
			        	  staffstudnet = allinformations.getString("person");
			        	  tempbook = allinformations.getString("book");
				           temproom = allinformations.getString("classroom"); temptime = allinformations.getString("classtime");
				            tempintro = allinformations.getString("intro");  number = allinformations.getString("number"); // 剩下的六项全部提取
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
					// 先转化为id
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
			        // 最后的更新table操作
			        try {
						  Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				          // 每次在查询的时候  最好建立一个新的sql  然后执行完 之后释放 自己
						  Statement sql = conn.createStatement();
						  allinfor = "select name as course,teacher,intro,book,classroom, number,classtime,course_state.description from course,course_state where id in" + 
					        		"(select course_id from training_plan  where person = '"+ personid+"') and course.state = course_state.code";
				          vinformation.clear(); // 清除掉以前的数据
				          ResultSet allinformations  = sql.executeQuery(allinfor);
				          while(allinformations.next())
				          {
				        	  staffteacher = allinformations.getString("teacher");  
				        	  tempbook = allinformations.getString("book");
					           temproom = allinformations.getString("classroom"); temptime = allinformations.getString("classtime");
					            tempintro = allinformations.getString("intro");  number = allinformations.getString("number"); // 剩下的六项全部提取
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
		       //同时提供姓名和课程   这样  姓名 就不用再去转化了 直接查找老师的姓名  就可以了  然后 状态一样的思路  直接将两张表做自然连接
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
			          // 每次在查询的时候  最好建立一个新的sql  然后执行完 之后释放 自己
					  Statement sql = conn.createStatement();
					  
			          vinformation.clear(); // 清除掉以前的数据
			          ResultSet allinformations  = sql.executeQuery(allinfor);
			          while(allinformations.next())
			          {
			        	  staffteacher = allinformations.getString("teacher");  
			        	  tempbook = allinformations.getString("book");
				           temproom = allinformations.getString("classroom"); temptime = allinformations.getString("classtime");
				            tempintro = allinformations.getString("intro");  number = allinformations.getString("number"); // 剩下的六项全部提取
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
	    	
	    	JLabel numberOfStudent = new JLabel("-  学员名单信息    -");
	    	numberOfStudent.setBounds(140,26,140,16);
	    	numberOfStudent.setForeground(Color.BLACK);
	    	numberOfStudent.setFont(new Font("",Font.ITALIC,14));
	    	
	    	// 所有的组件创建
	    	JLabel teacherName = new JLabel("教师姓名");
	    	JLabel courseName  = new JLabel("课程名称");
	    	JTextField teacher = new JTextField(10);
	    	JComboBox<String> tcourse = new JComboBox<String>();
	    	tcourse.addItem("");
			tcourse.addItem("人际交往");
			tcourse.addItem("管理学");
			tcourse.addItem("世界妙事");
			tcourse.addItem("读者");
			tcourse.addItem("English");
	    	JButton  search = new JButton("--查询--");
	    	
	    	// 下面尝试添加table 
	        vtitle = new Vector();
	    	vtitle.addElement("课程"); vtitle.addElement("教师"); vtitle.addElement("学生姓名");
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
	                // 进行逻辑处理即可
	            	String name=teacher.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString();
	            	//System.out.print(name + xcourse);
	            	theNumber(name,xcourse);
	            }
	        });
	    	// 样式修改后的添加
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
	    	// 暂且规定 必须输入教师姓名以及课程姓名
	    	if(teacher.equals("") || course.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(null, "请选择全部的查询条件", "条件不能有空值", JOptionPane.ERROR_MESSAGE);
	    	}else {
	    		String usename = md5Password(teacher);
		    	String personid = null;
				String courseid = null;
				// 这样就可以添加汉字了  允许unicode and  编码是 utf8
				url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // 清除掉以前的数据
			          String pidSql = "select id from person where username ='" + usename+"'";
			          ResultSet pid = sql.executeQuery(pidSql);
			          // 系统操作人员输入的学生姓名不一定存在 所以需要加上一个判断
			          while(pid.next())
			          {
			        	  personid = pid.getString("id");    
			          }
			          if(personid==null)
			          {
			        	  JOptionPane.showMessageDialog(null, "很抱歉，暂且没有这名教师", "教师不存在", JOptionPane.ERROR_MESSAGE);
			          }else {
			        	    
			        	   	 int sid = Integer.parseInt(personid); // 教师姓名对应的id
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
	  		//所有的组件创建
	  		JLabel appri = new JLabel("-- 课程评价信息  --");
	  		JLabel stuName = new JLabel("学员姓名");
	  		JLabel stuClass = new JLabel("所上课程");
	  		JTextField tstuName = new JTextField(10);
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  	    tcourse.addItem("");
			tcourse.addItem("人际交往");
			tcourse.addItem("管理学");
			tcourse.addItem("世界妙事");
			tcourse.addItem("读者");
			tcourse.addItem("English");
			// 添加下拉选项的监听器
	  		JButton  search = new JButton("--查询--");
	  		// 所有组件的排版
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
	                // 进行逻辑处理即可
	            	String name=tstuName.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString();
	            	System.out.print(name + xcourse);
	            	showappri(name,xcourse);
	            }
	        });
	    	// 样式修改后的添加
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		// 下面尝试添加table 
	    	
//	  		String[] title = {"课程","学生姓名","评价等级"};
//	    	String[][]  information = {{"1","2","78"},{"2","3","54"},{"2","3","877"}};
	    	vtitle = new Vector();
	    	vtitle.addElement("学员姓名"); vtitle.addElement("课程"); vtitle.addElement("成绩");vtitle.addElement("评价等级");vtitle.add("评测时间");
	    	vinformation = new Vector(); // 目前设置为空
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
	  		//组件的添加
	  		right.add(appri);
	  		right.add(stuName);
	  		right.add(stuClass);
	    	
//	    	right.setBackground(Color.getHSBColor(62,193, 184));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
			
	    }
		 public void showappri(String name,String course) {
			  // 对传递过来的参数进行空值的判断
			 if((course.equals("")&& name.equals("") )||(course.equals(""))) 
			 {
				 JOptionPane.showMessageDialog(null,"至少选择课程信息" , "操作错误", JOptionPane.ERROR_MESSAGE);
			 }else {
				  String sesql = "select  distinct apprisement.description,person.name,course.name as name1,training_plan.exam_date,training_plan.score from training_plan,course,person,apprisement where";
				  if(name.equals(""))
				  {
					  //System.out.println("我没有名字的");
					  sesql = sesql + "(training_plan.course='"+course +"')  and person.id= training_plan.person and training_plan.course = course.name \r\n" + 
					  		"and training_plan.apprisement = apprisement.code";
				  }
				  else{
					//  System.out.println("我有名字的");
					  sesql = sesql + "(person.name='"+ name +"' and training_plan.course='"+course+"')  and person.id= training_plan.person and training_plan.course = course.name \r\n" + 
					  		"and training_plan.apprisement = apprisement.code";
				  }
				 // System.out.print(sesql);
				  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
					 try {
						 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				          Statement score = conn.createStatement(); // 相当于建立一个游标  cursor
				          
				          ResultSet result = score.executeQuery(sesql);
				          vinformation.clear(); // 清除掉以前的数据
//				          vinformation = new Vector(); // 重新建立一个可以动态扩充的数组
				          
				          while(result.next())
				          {
				        	  Vector v  = new Vector(); //用来保存每一行的数据
				        	  v.add(result.getString("name"));
				        	  v.add(result.getString("name1"));
				        	  v.add(result.getString("score"));
				        	  v.add(result.getString("description"));
				        	  v.add(result.getString("exam_date"));
				        	  vinformation.add(v);
				          }
				          table.updateUI(); // 更新窗口？？？
				          
				    	}catch(SQLException e) {
						 	e.printStackTrace();
				    	}catch(Exception e) {
						 	e.printStackTrace();
				    	}  
			 }
		  }
		public void addscore(){
			
			//所有的组件创建
	  		JLabel appri = new JLabel("-- 学员成绩录入  --");
	  		JLabel stuName = new JLabel("学员姓名");
	  		JLabel stuClass = new JLabel(" 课程 ");
	  		JLabel stuScore = new JLabel(" 成绩  ");
	  		JLabel stuLevel = new JLabel("培训等级");
	  		JLabel stuTime  = new JLabel("考核日期");
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
			tcourse.addItem("人际交往");
			tcourse.addItem("管理学");
			tcourse.addItem("世界妙事");
			tcourse.addItem("读者");
			tcourse.addItem("English");
			
	  		JButton  search = new JButton("--录入--");
	  		// 所有组件的排版
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
	             
	            //  需要传递的参数	System.out.print(name +time + core +xstate + xcourse);
	            	String name = tstuName.getText().trim();
	            	String time = tstutime.getText().trim();
	            	String core = tstuScore.getText().trim();  
	            	xcourse = tcourse.getSelectedItem().toString();
	            	xle = tstuLevel.getSelectedItem().toString();
	               addScore(name,xcourse,core,xle,time);
	            }
	        });
	    	// 样式修改后的添加
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		
	  		 vtitle = new Vector();
		    	vtitle.addElement("学员姓名"); vtitle.addElement("课程");
		    	vinformation = new Vector(); // 目前设置为空
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
			          Statement score = conn.createStatement(); // 相当于建立一个游标  cursor
			         String sesql ="select person.name,course from training_plan,person where training_plan.person = person.id";
			          ResultSet result = score.executeQuery(sesql);
			          vinformation.clear(); // 清除掉以前的数据
//			          vinformation = new Vector(); // 重新建立一个可以动态扩充的数组
			          
			          while(result.next())
			          {
			        	  Vector v  = new Vector(); //用来保存每一行的数据
			        	  v.add(result.getString("name"));
			        	  v.add(result.getString("course"));
			        	  vinformation.add(v);
			          }
			          table.updateUI(); // 更新窗口？？？
			          
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
	    	
	  		//组件的添加
	  		right.add(appri);
	  		right.add(stuName);
	  		right.add(stuClass);
	    	
//	    	right.setBackground(Color.getHSBColor(62,193, 184));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
		}
		// 学院成绩入库操作name,xcourse,core,xle,time  这里不需要进行主键id 的特别输入 因为不是其他表的外键
		public void addScore(String name,String course,String core,String state,String time ){
			if(name.equals("") || course.equals("") || core.equals("") || state.equals("") || time.equals(""))
			{
			    JOptionPane.showMessageDialog(null, "成绩信息不能为空", "有空值", JOptionPane.ERROR_MESSAGE);
			}else {
				String usename = md5Password(name);
				String psid = null;
				// 这样就可以添加汉字了  允许unicode and  编码是 utf8
				url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
				
				//System.out.println(name+ xcourse+core+xle+time);
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          //Duplicate entry '1' for key 'PRIMARY'  这里必须保证在person 表里面存在相应的主键id
			          //  如果你的SQL 语句是诸如update,insert的更新语句，应该用statement的execute()方法，如果用的是statement的executeQuery()就会出现上诉问题
			          //  需要注意的是 id  state person 都是数字  另外state person 是外键        
			          String pidSql = "select id from person where username ='" + usename+"'";
			          ResultSet pid = sql.executeQuery(pidSql);
			          // 系统操作人员输入的学生姓名不一定存在 所以需要加上一个判断
			          while(pid.next())
			          {
			        	  psid = pid.getString("id");    
			          }
			          if(psid==null)
			          {
			        	  System.out.println("很抱歉，暂时还没有这名员工");
			          }else {
			        	   		int sid = Integer.parseInt(psid);
					         int sta = Integer.parseInt(state);
					         int score = Integer.parseInt(core); // 成绩 状态  已经学生id 转化为int类型
					         //System.out.println(sta +"  " + sid + "  "+ score);
					         //update training_plan set score =88,apprisement=3,exam_date="2018-0617" where person =7 and course="人际交往";
					         String updatesql = "update training_plan set score ="+score+",apprisement="+sta+",exam_date='"+time+"' where person ="+sid+" and course='"+course+"'";
					         sql.execute(updatesql);  
					         JOptionPane.showMessageDialog(null, "成绩添加成功", "添加完成", JOptionPane.PLAIN_MESSAGE);

			          }
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
			}
			// 这里不可以直接转化操作的
			//System.out.println(core+"  "+ state + "  ");
			//int iteacher=  Integer.parseInt(name);
		}
	    public void Count(){
	    	
	    	//所有的组件创建
	  		JLabel count = new JLabel("-- 培训统计信息  --");
	  		JLabel stuName = new JLabel("教师姓名");
	  		JLabel stuClass = new JLabel("课程名称");
	  		JTextField tstuName = new JTextField(10);
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  		tcourse.addItem("");
			tcourse.addItem("人际交往");
			tcourse.addItem("管理学");
			tcourse.addItem("世界妙事");
			tcourse.addItem("读者");
			tcourse.addItem("English");
	  		JButton  search = new JButton("--查询--");
	  		// 所有组件的排版
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
	                // 进行逻辑处理即可
	            	String name = tstuName.getText().trim();
	            	xcourse = tcourse.getSelectedItem().toString();
	            	countof_edu(name,xcourse);
	            }
	        });
	    	// 样式修改后的添加
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		// 下面尝试添加table  全部组件都是  全局变量 方便每次的管理 
	    	vtitle = new Vector();
	    	vtitle.addElement("课程"); vtitle.addElement("教师");vtitle.addElement("当前上课人数");vtitle.addElement("评测时间");
	    	vinformation = new Vector(); // 目前信息为空
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
	  		//组件的添加
	  		right.add(count);
	  		right.add(stuName);
	  		right.add(stuClass);
	    	
	    	right.setBackground(Color.getHSBColor(135, 140, 115));
	    	this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
	    }
	    // 培训统计信息  --- 然后在上一个课程信息查询界面 需要增加一个   x/y 这种类型的数据 代表当前学员人数 以及总人数限制 
	    public void countof_edu(String teacher,String course)
	    {
	    	if(teacher.equals("") || course.equals(""))
	    	{
	    		JOptionPane.showMessageDialog(null, "请选择全部的条件", "查询条件不能为空", JOptionPane.ERROR_MESSAGE);
	    	}else {
	    		String usename = md5Password(teacher);
		    	String personid = null;
		    	String courseid = null;
				url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";			
				//System.out.println(name+ xcourse+core+xle+time);
				try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement sql = conn.createStatement();
			          vinformation.clear(); // 清除掉以前的数据
			          String pidSql = "select id from person where username ='" + usename+"'";
			          ResultSet pid = sql.executeQuery(pidSql);
			          // 系统操作人员输入的学生姓名不一定存在 所以需要加上一个判断
			          while(pid.next())
			          {
			        	  personid = pid.getString("id");    
			          }
			          if(personid==null)
			          {
			        	 JOptionPane.showMessageDialog(null, "抱歉，暂且没有这名教师", "教师不存在", JOptionPane.ERROR_MESSAGE);
			          }else {
			        	    
			        	   	 int sid = Integer.parseInt(personid); // 教师姓名对应的id
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
			        	   	JOptionPane.showMessageDialog(null,"培训统计信息查询成功", "查询完成", JOptionPane.PLAIN_MESSAGE);
			          }
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 
	    	}
		}
	    // 成绩查询界面 设计
	    public void record() {
	    	
	  	    //所有的组件创建
	  		JLabel record = new JLabel("-- 培训成绩查询 --");
	  		//JLabel stuName = new JLabel("学员姓名");
	  		JLabel stuClass = new JLabel("课程");
	  		//JTextField tstuName = new JTextField(10);
	  		JComboBox<String> tcourse = new JComboBox<String>();
	  		tcourse.addItem("");
			tcourse.addItem("人际交往");
			tcourse.addItem("管理学");
			tcourse.addItem("世界妙事");
			tcourse.addItem("读者");
			tcourse.addItem("English");
			// 添加下拉选项的监听器
//			System.out.println();
	  		JButton  search = new JButton("--查询--");
	  		// 所有组件的排版
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
	                // 进行逻辑处理即可
	    			xcourse = tcourse.getSelectedItem().toString();
	            	showappri(staname,xcourse);  // 学员姓名  自己选择的课程
	            }
	        });
	    	// 样式修改后的添加
		    this.remove(this.right);
			this.revalidate();
			this.right = new JPanel();   	
			right.setPreferredSize(new Dimension(390,500));
	  		right.setLayout(null);
	  		
	  		// 下面尝试添加table
	  		//Vector vtitle = new Vector();
	  		vtitle = new Vector();
	    	vtitle.addElement("学员姓名"); vtitle.addElement("课程"); vtitle.addElement("成绩");vtitle.addElement("评价等级");vtitle.add("评测时间");
	    	vinformation = new Vector(); // 目前设置为空
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
	  		//组件的添加
	  		right.add(record);
	  		right.add(stuClass);
	    	
//	    	right.setBackground(Color.getHSBColor(62,193, 184));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();	
	    }
	    // 学员选择课程的界面
	    public void Choice() {	  
	    	
	    	// 最好在这个界面上面添加浏览所有课程的框框  table 方便选课  后面再加  这个easy
	   
	    	
	    	
	  		 //所有的组件创建
	  		JLabel Choice = new JLabel("-- 学员选课页面 --");
	  		JLabel stuName = new JLabel("教师姓名");
	  		JLabel stuClass = new JLabel("课程名");
	  		JLabel stuTime  = new JLabel("上课日期");
	  		JLabel stuAdd   = new JLabel("地点");
	  		JComboBox<String> choiceClass = new JComboBox<String>();
	  		choiceClass.addItem("");
	  		choiceClass.addItem("八楼会议室");
	  		choiceClass.addItem("十楼会议室");
	  		choiceClass.addItem("五楼会议室");
	  		JComboBox<String> choiceSou = new JComboBox<String>();
	  		choiceSou.addItem("");
	  		choiceSou.addItem("人际交往");
	  		choiceSou.addItem("管理学");
	  		choiceSou.addItem("世界妙事");
	  		choiceSou.addItem("读者");
	  		choiceSou.addItem("English");
	  		// 两个下拉选项的已经选择科目
	  		JTextField tstuName = new JTextField(10);
	  		JTextField tstuTime = new JTextField(10);
	  		JButton  search = new JButton("--确认选择--");
	  		// 所有组件的排版
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
	    	// 按钮的监听器
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
	    	// 为学员提供课表信息

	  		 vtitle = new Vector();
	  		 vtitle.addElement("教师");vtitle.addElement("课程");
	  		vtitle.addElement("时间");vtitle.addElement("教室");
		    	vinformation = new Vector(); // 目前设置为空
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
			          Statement score = conn.createStatement(); // 相当于建立一个游标  cursor
			          // 执行之前需要先加上判断 
			          String now_number = "select distinct course.id,count(distinct training_plan.person) as now_number,course.number from training_plan,course where course.id = training_plan.course_id  group by course_id";
			          ResultSet nownumber = score.executeQuery(now_number);
			          while(nownumber.next())
			          {
			        	  String a = nownumber.getString("now_number"); String b = nownumber.getString("number");
			        	  int c =Integer.parseInt(a);int d =  Integer.parseInt(b);
			        	  if(c>=d)
			        	  {
			        		  Statement score1 = conn.createStatement();
			        		  String e = nownumber.getString("id"); // 获得当前课程数目人员不够的科目
			        		  String sesql ="SELECT course.name,person.name as teacher,classroom,classtime FROM course,person where course.teacher = person.id and course.id not in ("+e+") ";
					          ResultSet result = score1.executeQuery(sesql);
					          vinformation.clear(); // 清除掉以前的数据	          
					          while(result.next())
					          {
					        	  Vector v  = new Vector(); //用来保存每一行的数据
					        	  v.add(result.getString("teacher"));v.add(result.getString("name"));			        	  
					        	  v.add(result.getString("classtime"));  v.add(result.getString("classroom"));			        	
					        	  vinformation.add(v);
					          }
					          score1.close();
			        	  }
			          }
			         
			          table.updateUI(); // 更新窗口？？？
			          
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 		
	    	
	    	
	    	
	    	right.add(search);
	  		//组件的添加
	  		right.add(Choice);
	  		right.add(choiceSou);
	    	
	  		right.setBackground(Color.getHSBColor(135, 140, 115));
	  		this.add(right, BorderLayout.EAST);
			this.repaint();
			this.revalidate();
	    	
	    }
	    public void inforinForcommon(){
	    	 JLabel theme = new  JLabel("--员工自我信息修改界面--");
	    	 // 考虑到员工自己的权限  不应该提供所有的修改功能
			  JLabel pass = new JLabel("密码");
			  JTextField tpass = new JTextField(10);
			  JLabel job  = new JLabel("职位");
			  JTextField tjob = new  JTextField(10);
			  JLabel level = new JLabel("学历");
			  JTextField tlevel = new JTextField(10);
			  JLabel add  = new JLabel("住址");
			  JTextField tadd = new JTextField(10);
			  JLabel tel  =  new  JLabel("电话");
			  JTextField ttel = new JTextField(10);
	 		  JLabel email = new JLabel("邮箱");
	 		  JTextField temail = new JTextField(10);

			// 按钮设置
			    JButton jBInsert = new JButton("更新");
			    
			    
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
			  // 组件的布局
			    theme.setBounds(100,50,200,16);
				  theme.setForeground(Color.BLACK);
				  theme.setFont(new Font("",Font.ITALIC,14));
				jBInsert.setBounds(280, 360, 80, 30);
				// 给按钮添加监听
				jBInsert.addMouseListener(new MouseAdapter() {
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 
						 // 定义用来传递的参数
		
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
			     // 添加具体的组件
				right.add(tel); right.add(ttel); right.add(email); right.add(temail); 
				right.add(jBInsert); 
				  right.add(job); right.add(tjob);
				 
				right.add(level); right.add(tlevel);   right.add(add); right.add(tadd);
				right.add(pass); right.add(tpass); right.add(theme);
			  // 界面切换的固定格式  每次都是重新刷新然后重新加载一个新的页面  然后就可以实现动态加载
	    
		    	
		    	this.add(this.right);
		    	this.repaint();
				this.revalidate();
	    	
	    }
	    // 员工注册页面
	    public void registerinfor() {
			  
			  
			  JLabel theme = new  JLabel("--员工信息注册面板--");
			  JLabel pass = new JLabel("密码");
			  JTextField tpass = new JTextField(10);
			  JLabel name = new JLabel("姓名");
			  JTextField tname = new JTextField(10);
			  JLabel sex  = new JLabel("性别");
			  JComboBox<String> tsex = new JComboBox<String>();
			  tsex.addItem("");
			  tsex.addItem("男");
			  tsex.addItem("女");
			  JLabel birth = new JLabel("生日");
			  JTextField tbirth = new JTextField(10);
			  JLabel depart = new JLabel("部门");
			  JComboBox<String> tdepart = new JComboBox<String>();
			  tdepart.addItem("");
			  tdepart.addItem("信息部");
			  tdepart.addItem("营销部");
			  tdepart.addItem("网络部");
			  tdepart.addItem("人事部");
			  JLabel job  = new JLabel("职位");
			  JTextField tjob = new  JTextField(10);
			  JLabel level = new JLabel("学历");
			  JTextField tlevel = new JTextField(10);
			  JLabel speci = new JLabel("技能");
			  JTextField tspeci = new JTextField(10);
			  JLabel add  = new JLabel("住址");
			  JTextField tadd = new JTextField(10);
			  JLabel tel  =  new  JLabel("电话");
			  JTextField ttel = new JTextField(10);
	 		  JLabel email = new JLabel("邮箱");
	 		  JTextField temail = new JTextField(10);
			  JLabel remark  = new JLabel("备注");
			  JTextField tremark = new JTextField(10);
			  
			  // 按钮设置
			    JButton jBInsert = new JButton("注册");

			    
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
			    // 组件的布局
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
				// 给按钮添加监听
				jBInsert.addMouseListener(new MouseAdapter() {
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 
						 // 定义用来传递的参数
						String cname = tname.getText().trim(); String cpass = tpass.getText().trim();
						String cspeci = tspeci.getText().trim();String cadd = tadd.getText().trim();String ctel = ttel.getText().trim();
						String cemail = temail.getText().trim();String cjob = tjob.getText().trim();String cremark = tremark.getText().trim();
						String cbirth = tbirth.getText().trim(); String clevel = tlevel.getText().trim(); 
						String state = "-F-"; // 默认为非正式员工  
						String auth = "0";   // 默认为普通员工权限
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
			     // 添加具体的组件
				back_right.add(tel); back_right.add(ttel); back_right.add(email); back_right.add(temail);
				back_right.add(jBInsert);  back_right.add(name); back_right.add(tname);
				back_right.add(sex); back_right.add(tsex); back_right.add(remark); back_right.add(tremark); back_right.add(job); back_right.add(tjob);
				back_right.add(birth); back_right.add(tbirth); back_right.add(speci); back_right.add(tspeci); 
				back_right.add(level); back_right.add(tlevel);  back_right.add(depart); back_right.add(tdepart); back_right.add(add); back_right.add(tadd);
				back_right.add(pass); back_right.add(tpass); back_right.add(theme);
				// 界面切换的固定格式  每次都是重新刷新然后重新加载一个新的页面  然后就可以实现动态加载   	
				back.add(back_right);
				back.repaint();
				back.revalidate();
		  }
	   // 员工信息管理页面
	    public void inforin() {
		  
		  //重新分析一下 管理员权限的内容  做出一些 微调  
		  JLabel theme = new  JLabel("--学员信息管理界面--");
		  JLabel auth = new JLabel("用户权限");
		  JTextField tauth = new JTextField(10);
		  JLabel name = new JLabel("姓名");
		  JTextField tname = new JTextField(10);
		  JLabel job  = new JLabel("职位");
		  JTextField tjob = new  JTextField(10);
		  JLabel speci = new JLabel("技能");
		  JTextField tspeci = new JTextField(10);
		  JLabel state = new JLabel("状态");
		  JComboBox<String> tstate = new JComboBox<String>();
		  tstate.addItem("");
		  tstate.addItem("-T-");
		  tstate.addItem("-F-");
		  JLabel remark  = new JLabel("备注");
		  JTextField tremark = new JTextField(10);
		  
		  // 按钮设置
		    JButton jBInsert = new JButton("信息更新");

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
		    // 组件的布局
		    state.setBounds(130,320,60, 25);
		    tstate.setBounds(160,320,70,25);
		    
		    theme.setBounds(120,40,140,16);
			theme.setForeground(Color.BLACK);
			theme.setFont(new Font("",Font.ITALIC,14));
			jBInsert.setBounds(200, 390, 100, 26);
			// 给按钮添加监听
			jBInsert.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent e) {
					 
					 // 定义用来传递的参数
					String cname = tname.getText().trim(); 
					String cspeci = tspeci.getText().trim();
					String cjob = tjob.getText().trim();String cremark = tremark.getText().trim();
					String cauth = tauth.getText().trim();
					String cstate = tstate.getSelectedItem().toString(); // 把选择的职位状态提取出来
					// 传递的顺序见下 name remark auth speci state 	job
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
		    	vtitle.addElement("学员姓名"); vtitle.addElement("性别"); vtitle.addElement("部门");
		    	vtitle.addElement("职位"); vtitle.addElement("状态"); vtitle.addElement("权限"); vtitle.addElement("备注");
		    	vinformation = new Vector(); // 目前设置为空
		    	table = new JTable(vinformation,vtitle);
		    	table.setPreferredScrollableViewportSize(new Dimension(330,280));
//		    	table.setForeground(Color.ORANGE);
		    	table.setRowHeight(22);
		    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
		  		table.setEnabled(false);
		  		 try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			          Statement score = conn.createStatement(); // 相当于建立一个游标  cursor
			         String sesql ="select name,sex,department,job,state,authority,remark from person where authority ='0'";
			          ResultSet result = score.executeQuery(sesql);
			          vinformation.clear(); // 清除掉以前的数据
//			          vinformation = new Vector(); // 重新建立一个可以动态扩充的数组
			          
			          while(result.next())
			          {
			        	  Vector v  = new Vector(); //用来保存每一行的数据
			        	  v.add(result.getString("name"));
			        	  v.add(result.getString("sex"));v.add(result.getString("department"));
			        	  v.add(result.getString("job"));v.add(result.getString("state"));	
			        	  v.add(result.getString("authority"));v.add(result.getString("remark"));
			        	  vinformation.add(v);
			          }
			          table.updateUI(); // 更新窗口？？？
			          
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
		     // 添加具体的组件
			 right.add(state); right.add(tstate);
			right.add(jBInsert);  right.add(name); right.add(tname);
			right.add(remark); right.add(tremark); right.add(job); right.add(tjob);
			 right.add(speci); right.add(tspeci); right.add(auth); right.add(tauth);			
			 right.add(theme);
			// 界面切换的固定格式  每次都是重新刷新然后重新加载一个新的页面  然后就可以实现动态加载   	
	    	this.add(this.right);
	    	this.repaint();
			this.revalidate();
	  }
	   // 管理员 更新数据界面
	    public void adminUpdateInfor(String name,String remark,String auth,String speci,String state,String job) { 
		  if(name.equals("") && remark.equals("") && auth.equals("") && speci.equals("") && state.equals("") &&job.equals(""))
		  {
			 JOptionPane.showMessageDialog(null, "选项全部为空", "不执行", JOptionPane.ERROR_MESSAGE);
		  }else {	String tempname = md5Password(name); // 转化成MD5进行人员的精确辨别
		  //数据正常传递过来了 然后就是相应的入库操作
		  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
		  try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
				  Statement sql = conn.createStatement();  	
		         // 因为是修改信息 可能用户不会全部修改 所以 需要进行sql 的zuzhuan
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
		         JOptionPane.showMessageDialog(null, "该学员信息修改成功", "完成修改", JOptionPane.PLAIN_MESSAGE);

		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 	 
			  
		  }
	  }
	   // 普通用户信息注册 cname,xsex,cbirth,email,cjob,clevel,cpass,cadd,ctel,xdepart,cspeci,cremark,state,auth
	  public void insertRegister(String cname,String xsex,String cbirth,String email,String cjob,String clevel,String cpass,String cadd,String ctel,String xdepart,String cspeci,String cremark,String state,String auth)
	  {
		  if(cname.equals("") || xsex.equals("") || cbirth.equals("") || email.equals("") || cjob.equals("") || clevel.equals("") || cpass.equals("") || cadd.equals("") || ctel.equals("")||xdepart.equals(""))
		  {
			  JOptionPane.showMessageDialog(null, "除了备注和技能，都不能出现空值", "信息填写有错误", JOptionPane.ERROR_MESSAGE);
		  }else {
			  // 当点击后车 处理完数据进行关闭
			  //System.out.println(id+pass+auth+name+sex+birth+depart+job+level+speci+add+tel+email+state+remark);
			  //数据正常传递过来了 然后就是相应的入库操作  
			  String count= null;
			  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			  try {
					 Connection conn = (Connection) DriverManager.getConnection(url, user, password); // 不再检测是否正确连接数据库了 一般没有问题的
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
			          JOptionPane.showMessageDialog(null, "信息注册成功", "ok", JOptionPane.PLAIN_MESSAGE);
			          login();
			          back.setVisible(false);
			    	}catch(SQLException e) {
					 	e.printStackTrace();
			    	}catch(Exception e) {
					 	e.printStackTrace();
			    	} 	  
		  }
	  }
	  // 查找课程数据
	  public void searchS(String name,String course) {
		  // 对传递过来的参数进行空值的判断
		  
		  System.out.print(name+ course);
		  //select  distinct training_plan.score,person.name,course.name,training_plan.exam_date from training_plan,course,person where
//		  ( training_plan.course='人际交往') and person.id= training_plan.person and training_plan.course = course.name
		  String sesql = "select  distinct training_plan.score,person.name,course.name as name1,training_plan.exam_date from training_plan,course,person where";
			  System.out.println("我有名字的");
			  sesql = sesql + "(person.name='"+ name +"' and training_plan.course='"+course+"') and person.id= training_plan.person and training_plan.course = course.name";
		  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			 try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		          Statement score = conn.createStatement(); // 相当于建立一个游标  cursor
		          
		          ResultSet result = score.executeQuery(sesql);
		          vinformation.clear(); // 清除掉以前的数据
		          //vinformation = new Vector(); // 重新建立一个可以动态扩充的数组
		          
		          while(result.next())
		          {
		        	  Vector v  = new Vector(); //用来保存每一行的数据
 		        	  v.add(Integer.valueOf(result.getInt("score")));
		        	  v.add(result.getString("name"));
		        	  v.add(result.getString("name1"));
		        	  v.add(result.getString("exam_date"));
		        	  vinformation.add(v);
		          }
		          table.updateUI(); // 更新窗口？？？
		          
		    	}catch(SQLException e) {
				 	e.printStackTrace();
		    	}catch(Exception e) {
				 	e.printStackTrace();
		    	} 
	  }
	 public void addinformation(String teacher,String course,String time,String add){
		 
		 if(teacher.equals("") || course.equals("") || time.equals("") || add.equals(""))
		 {
			 JOptionPane.showMessageDialog(null, "各项数据不能为空", "信息错误", JOptionPane.ERROR_MESSAGE);
		 }else {
			 Vector ids = new Vector();
			 String username = md5Password(teacher);
			 String id = null; String perid = null;
			 url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
			 try {
				 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		          Statement sql = conn.createStatement();
		          //  先去获得里面的 id 然后进行保存信息
		         String idsql = "select course.id from course \r\n" + 
		         		"where name = '"+ course +"' and classroom='"+add+"' and classtime ='"+time+"' and teacher=(select id from person where username = '"+username+"') ";
		         //System.out.println(idsql);
		         ResultSet courseid = sql.executeQuery(idsql);
		         while(courseid.next())
		         {
		        	id = courseid.getString("id");
		         }
		         id = id;
		        //全局的 xname 
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
	 // 更新操作有主键的限制 所以我们直接忽略主键的修改 然后进行其他修改就可以了
	 // 界面上面暂且保留这个功能
	 public void updateInformation(String email,String job,String level,String pass,String add,String tel){
	  String tempname = md5Password(staname); // 转化成MD5进行人员的精确辨别
	  //数据正常传递过来了 然后就是相应的入库操作
	  url = "jdbc:mysql://127.0.0.1:3306/staff?useUnicode=true&characterEncoding=utf8";
	  try {
			 Connection conn = (Connection) DriverManager.getConnection(url, user, password);
			  Statement sql = conn.createStatement();  	
	         // 因为是修改信息 可能用户不会全部修改 所以 需要进行sql 的zuzhuan
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
	          JOptionPane.showMessageDialog(null, "信息更改成功", "更改信息", JOptionPane.PLAIN_MESSAGE);
	    	}catch(SQLException e) {
			 	e.printStackTrace();
	    	}catch(Exception e) {
			 	e.printStackTrace();
	    	} 	 
	 }
	public void con(String name, String pass) {
		// 
		// 如果用户输入的两个框有一个为空那么就不能执行下面的操作
		if(name.equals("")|| pass.equals(""))
		{
			JOptionPane.showMessageDialog(null, "操作错误", "两个框都必须不为空", JOptionPane.WARNING_MESSAGE);
		}else {
			
			//System.out.print(name + pass);
			String uname = md5Password(name); // name 对应 MD5 字符串
			String truepass = ""; // 用来记录正确的密码
			String le = ""   ;  // 用来进行权限的认证
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
	        	 JOptionPane.showMessageDialog(null, "该用户不存在", "用户名错误", JOptionPane.ERROR_MESSAGE);
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
			        System.out.print("您没有相应的权限，无法访问");
			        themeToo();
			        }
			        JOptionPane.showMessageDialog(null, "登陆成功", "提示", JOptionPane.PLAIN_MESSAGE);
			        }else {
			        JOptionPane.showMessageDialog(null, "请重新输入您的密码","密码错误", JOptionPane.WARNING_MESSAGE);
		    	}
			}
		}catch(SQLException e) {
			 	e.printStackTrace();
	    }catch(Exception e) {
			 	e.printStackTrace();
	    } 
	}
}
	// 主函数
	public static void main(String [] args){
		master one = new master();
//		one.theme();
		one.login();
//		one.themeToo();
	}
}