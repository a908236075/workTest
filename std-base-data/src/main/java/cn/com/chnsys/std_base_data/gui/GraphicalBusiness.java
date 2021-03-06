/**
 * 
 */
package cn.com.chnsys.std_base_data.gui;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.chnsys.std_base_data.pojo.ResultPojo;
import cn.com.chnsys.std_base_data.util.ProcessorUtil;

/**
 * 
 * @author bianxiaozeng
 * @version 1.0
 * 
 * @date 2016年11月14日 上午9:14:01
 */
public class GraphicalBusiness {

	/**
	 * logger
	 */
	private static Logger log = LoggerFactory.getLogger(ProcessorUtil.class
			.getName());

	private JRadioButton courtStd2005Radio = null;
	private JRadioButton courtStd2009Radio = null;
	private JRadioButton courtStd2015Radio = null;
	private JRadioButton myRadio=null;

	public void mainFrame() {
		JFrame frame = new JFrame("法标基础数据处理工具");
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		final TextArea showResultTextArea = new TextArea();
		showResultTextArea.setBounds(10, 230, 650, 200);
		showResultTextArea.setEditable(false);
		panel.add(showResultTextArea);
		placeComponents(panel, showResultTextArea);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				ProcessorUtil.closeContext();
			}
		});
	}

	private void placeComponents(JPanel panel, final TextArea showResultTextArea) {

		// 起线程后台初始化context
		ProcessorUtil.loadContext();

		// 设置路径输入框
		JLabel inputLabel = new JLabel("文件路径：");
		inputLabel.setBounds(10, 30, 70, 25);
		panel.add(inputLabel);
		final JTextField inputText = new JTextField(20);
		inputText.setBounds(100, 30, 450, 25);
		// 可以设置禁止手动输入必须选择文件
		// inputText.setEditable(false);
		panel.add(inputText);

		JButton selectButton = new JButton("选择文件");
		selectButton.setBounds(550, 30, 100, 25);
		panel.add(selectButton);

		// 设置法标
		ButtonGroup radioButtonGroup = new ButtonGroup();
		courtStd2005Radio = new JRadioButton("2005");
		courtStd2009Radio = new JRadioButton("2009", true);
		courtStd2015Radio = new JRadioButton("2015");
		//自定义的一个按钮
		myRadio=new JRadioButton("0529");
		
		courtStd2005Radio.setBounds(10, 65, 60, 25);
		courtStd2009Radio.setBounds(80, 65, 60, 25);
		courtStd2015Radio.setBounds(160, 65, 60, 25);
		myRadio.setBounds(230,65,60,25);
		radioButtonGroup.add(courtStd2005Radio);
		radioButtonGroup.add(courtStd2009Radio);
		radioButtonGroup.add(courtStd2015Radio);
		radioButtonGroup.add(myRadio);
		panel.add(courtStd2005Radio);
		panel.add(courtStd2009Radio);
		panel.add(courtStd2015Radio);
		panel.add(myRadio);

		// 设置复选框
		// 法院复选框
		final JCheckBox courtCodeCheckBox = new JCheckBox("法院代码");
		courtCodeCheckBox.setBounds(10, 100, 100, 20);
		// 案由复选框
		final JCheckBox caseCauseCheckBox = new JCheckBox("案由代码");
		caseCauseCheckBox.setBounds(10, 130, 100, 20);
		// 审判综合业务复选框
		final JCheckBox judgeBusinessCheckBox = new JCheckBox("审判综合业务代码");
		judgeBusinessCheckBox.setBounds(10, 160, 150, 20);
		// judgeBusinessCheckBox.setEnabled(false);

		final JCheckBox caseDZCheckBox = new JCheckBox("案件类型代字");
		caseDZCheckBox.setBounds(170, 100, 150, 20);

		final JCheckBox businessIdentityCheckBox = new JCheckBox("业务类型标识");
		businessIdentityCheckBox.setBounds(170, 130, 150, 20);
		businessIdentityCheckBox.setEnabled(false);

		final JCheckBox administrativeDivisionCheckBox = new JCheckBox("行政区划代码");
		administrativeDivisionCheckBox.setBounds(170, 160, 150, 20);
		administrativeDivisionCheckBox.setEnabled(false);

		final JCheckBox administrativeSuperiorCodeBox = new JCheckBox(
				"校验更新行政区划上级代码");
		administrativeSuperiorCodeBox.setBounds(320, 160, 200, 20);
		administrativeSuperiorCodeBox.setEnabled(false);
		//自定义---武功秘籍
		final JCheckBox administrativeMyBox = new JCheckBox(
				"武功秘籍");
		administrativeMyBox.setBounds(320, 100, 200, 20);
		administrativeMyBox.setEnabled(false);

		panel.add(courtCodeCheckBox);
		panel.add(caseCauseCheckBox);
		panel.add(judgeBusinessCheckBox);
		panel.add(caseDZCheckBox);
		panel.add(businessIdentityCheckBox);
		panel.add(administrativeDivisionCheckBox);
		panel.add(administrativeSuperiorCodeBox);
		//自定义
		panel.add(administrativeMyBox);
		

		// 设置按钮及动作
		JButton startButton = new JButton("开始执行");
		startButton.setBounds(100, 200, 100, 25);
		panel.add(startButton);

		selectButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择文件");
				File selectedFile = jfc.getSelectedFile();
				if (selectedFile.isDirectory()) {
					JOptionPane.showMessageDialog(null, "请选择excel文件！", "error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String absolutePath = selectedFile.getAbsolutePath();
					if (absolutePath.endsWith(".xlsx")) {
						inputText.setText(absolutePath);
					} else {
						JOptionPane.showMessageDialog(null,
								"暂时只支持office2007及以后的格式或文件不是excel！", "error",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		courtStd2009Radio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				businessIdentityCheckBox.setEnabled(false);
				businessIdentityCheckBox.setSelected(false);
				administrativeDivisionCheckBox.setEnabled(false);
				administrativeDivisionCheckBox.setSelected(false);
				administrativeSuperiorCodeBox.setEnabled(false);
				administrativeSuperiorCodeBox.setSelected(false);
				courtCodeCheckBox.setSelected(false);
				caseCauseCheckBox.setSelected(false);
				judgeBusinessCheckBox.setSelected(false);
				caseDZCheckBox.setSelected(false);
				
			}
		});
		courtStd2005Radio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				businessIdentityCheckBox.setEnabled(false);
				businessIdentityCheckBox.setSelected(false);
				administrativeDivisionCheckBox.setEnabled(false);
				administrativeDivisionCheckBox.setSelected(false);
				administrativeSuperiorCodeBox.setEnabled(false);
				administrativeSuperiorCodeBox.setSelected(false);
				courtCodeCheckBox.setSelected(false);
				caseCauseCheckBox.setSelected(false);
				judgeBusinessCheckBox.setSelected(false);
				caseDZCheckBox.setSelected(false);
			}
		});
		courtStd2015Radio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				businessIdentityCheckBox.setEnabled(true);
				administrativeDivisionCheckBox.setEnabled(true);
				administrativeSuperiorCodeBox.setEnabled(true);
				administrativeDivisionCheckBox.setSelected(false);
				businessIdentityCheckBox.setSelected(false);
				administrativeSuperiorCodeBox.setSelected(true);
				courtCodeCheckBox.setSelected(false);
				caseCauseCheckBox.setSelected(false);
				judgeBusinessCheckBox.setSelected(false);
				caseDZCheckBox.setSelected(false);
			}
		});
		myRadio.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				businessIdentityCheckBox.setEnabled(true);
				administrativeDivisionCheckBox.setEnabled(true);
				administrativeSuperiorCodeBox.setEnabled(true);
				administrativeDivisionCheckBox.setSelected(false);
				businessIdentityCheckBox.setSelected(false);
				administrativeSuperiorCodeBox.setSelected(true);
				courtCodeCheckBox.setSelected(false);
				caseCauseCheckBox.setSelected(false);
				judgeBusinessCheckBox.setSelected(false);
				caseDZCheckBox.setSelected(false);
				//自定义
				administrativeMyBox.setEnabled(true);
				administrativeMyBox.setSelected(false);
			}
		});

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean courtCodeSelected = courtCodeCheckBox.isSelected();
				boolean caseCauseSelected = caseCauseCheckBox.isSelected();
				boolean judgeBusinessSelected = judgeBusinessCheckBox
						.isSelected();
				boolean caseDZSelected = caseDZCheckBox.isSelected();
				boolean businessIdentitySelected = businessIdentityCheckBox
						.isSelected();
				boolean administrativeDivisionSelected = administrativeDivisionCheckBox
						.isSelected();
				boolean administrativeSuperiorCodeSelected = administrativeSuperiorCodeBox
						.isSelected();
				//自定义
				boolean administrativeMySelected = administrativeMyBox.isSelected();

				String path = inputText.getText().replace("\\", "/");
				log.info("选择的文件是：" + path);
				int courtStdVersion = getCoudrtStdVersion();
				log.info("选择的法标版本是：" + courtStdVersion);
//				if (StringUtils.isEmpty(path)) {
//					JOptionPane.showMessageDialog(null, "请先选择文件！");
//				} else {

					if (!courtCodeSelected && !caseCauseSelected
							&& !judgeBusinessSelected && !caseDZSelected
							&& !businessIdentitySelected
							&& !administrativeDivisionSelected
							&& !administrativeSuperiorCodeSelected
							&& !administrativeMySelected) {
						JOptionPane.showMessageDialog(null, "请选择需要执行的任务！");
					} else {
						StringBuilder content = new StringBuilder();
						content.append("文件路径是：" + inputText.getText() + "\n");
						content.append("法标版本是：" + courtStdVersion + "\n");
						int optionResult = JOptionPane.showConfirmDialog(null,
								content.toString(), "确认",
								JOptionPane.YES_NO_OPTION);
						if (optionResult == 0) {
							if (courtCodeSelected) {
								log.info("执行法院代码！......");
								if (isSheetExist(path, "法院代码")) {
									showResultTextArea.append("执行法院代码......\n");
									ResultPojo rp = ExecuteTask
											.excuteCourtCode(path,
													courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}

							if (caseCauseSelected) {
								if (isSheetExist(path, "案由代码")) {
									log.info("执行案由代码......");
									showResultTextArea.append("执行案由代码......\n");
									ResultPojo rp = ExecuteTask
											.excuteCaseCause(path,
													courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}

							if (judgeBusinessSelected) {
								log.info("执行审判业务标准代码......");
								if (isSheetExist(path, "审判业务标准代码")) {
									showResultTextArea.append("执行代码目录......\n");
									ResultPojo rp1 = ExecuteTask
											.excuteCodeDrictory(path,
													courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp1);
									showResultTextArea.append("执行代码信息......\n");
									ResultPojo rp2 = ExecuteTask
											.excuteCodeInfo(path,
													courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp2);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}

							if (caseDZSelected) {
								if (isSheetExist(path, "案件类型代字")) {
									showResultTextArea
											.append("执行案件类型代字......\n");
									ResultPojo rp = ExecuteTask
											.excuteCaseTypeDZ(path,
													courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}
							if (businessIdentitySelected) {
								if (isSheetExist(path, "业务类型标识")) {
									showResultTextArea
											.append("执行业务类型标识......\n");
									ResultPojo rp = ExecuteTask
											.excuteBusinessTypeIdentity(path,
													courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}

							if (administrativeDivisionSelected) {
								if (isSheetExist(path, "行政区划代码")) {
									showResultTextArea
											.append("执行行政区划代码......\n");
									ResultPojo rp = ExecuteTask
											.excuteAdministrativeDivisionCode(
													path, courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}

							if (administrativeSuperiorCodeSelected) {
								showResultTextArea
										.append("执行更新行政区划上级代码......\n");
								ResultPojo rp = ExecuteTask
										.executeAdministrativeSuperiorCode(showResultTextArea);
								setResult2TextArea(showResultTextArea, rp);
							}
							
							//自定义
							if (administrativeMySelected) {
								if (isSheetExist(path, "武功秘籍")) {
									showResultTextArea
											.append("执行武功秘籍代码......\n");
									ResultPojo rp = ExecuteTask
											.administrativeMy(
													path, courtStdVersion,
													showResultTextArea);
									setResult2TextArea(showResultTextArea, rp);
								} else {
									showResultTextArea
											.append("文件不存在或者sheet不存在！\n");
								}
							}
							
							
							
							
						}
						showResultTextArea.append("结束！\n");
					}
				}
//			}
		});

	}

	// 判断sheet是否存在
	private boolean isSheetExist(String path, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet(sheetName);
			if (sheet == null) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	 public static final int V0529 = 1;
	private int getCoudrtStdVersion() {
		int courtStdVersion = 0;
		if (myRadio.isSelected()) {
			courtStdVersion = V0529;
		}
		
			
		
		return courtStdVersion;
	}

	private void setResult2TextArea(TextArea showResultTextArea, ResultPojo rp) {
		showResultTextArea.append(rp.getAddCountString() + "\n");
		showResultTextArea.append(rp.getUpdateCountString() + "\n");
	}

	public static void main(String[] args) throws ClassNotFoundException {
		// Class.forName(GraphicalBusiness.class.getName());
		PropertyConfigurator.configure("log4j.properties");
		GraphicalBusiness gb = new GraphicalBusiness();
		gb.mainFrame();
	}

}
