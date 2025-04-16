package lifegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main implements Runnable {
public static void main(String[] args) {
	SwingUtilities.invokeLater(new Main());
}

public void run() {
	
	// ウィンドウを作成する
	JFrame frame = new JFrame("Lifegame");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    String selectvalues[]= new String[16];
    
    for(int i=1;i<17;i++) {
    	selectvalues[i-1]=String.valueOf(i);
    }
    

	Object value1 = JOptionPane.showInputDialog(frame, "横のマス", "横マス", JOptionPane.INFORMATION_MESSAGE, null, selectvalues, selectvalues[0]);
	Object value2 = JOptionPane.showInputDialog(frame, "縦のマス", "縦マス", JOptionPane.INFORMATION_MESSAGE, null, selectvalues, selectvalues[0]);
	
	BoardModel model = new BoardModel(Integer.valueOf(String.valueOf(value1)),Integer.valueOf(String.valueOf(value2)));

	

	
	// ウィンドウ内部を占有する「ベース」パネルを作成する
	JPanel base = new JPanel();
	frame.setContentPane(base);
	base.setPreferredSize(new Dimension(160+60*model.getCols(), 180+60*model.getRows())); // 希望サイズの指定
	frame.setMinimumSize(new Dimension(1000, 180+60*model.getRows())); // 最小サイズの指定
	base.setLayout(new BorderLayout()); // base 上に配置する GUI 部品のルールを設定
	
	//盤面作成
	BoardView view = new BoardView();
	view.setBoard(model);

	base.add(view, BorderLayout.CENTER); // base の中央に view を配置する
	
	//ボタン処理
	JPanel buttonPanel = new JPanel(); // ボタン用パネルを作成し
	buttonPanel.setPreferredSize(new Dimension(300, 100));//ボタン用パネルサイズ
	base.add(buttonPanel, BorderLayout.SOUTH); // base の下端に配置する
	buttonPanel.setLayout(new FlowLayout()); // java.awt.FlowLayout を設定
	
	
	//生存数表示用のパネル
	JPanel counterPanel = new JPanel(); 
	counterPanel.setPreferredSize(new Dimension(100, 60));
	base.add(counterPanel, BorderLayout.EAST); 
	counterPanel.setLayout(new FlowLayout()); // java.awt.FlowLayout を設定
	counterPanel.setBackground(Color.WHITE);
	
	//生存数を表示するラベル
	JLabel label = new JLabel("Alive: 0");
	//label.setPreferredSize(new Dimension(160+60*model.getCols(), 200));
	label.setVerticalAlignment(JLabel.TOP);
	label.setHorizontalAlignment(JLabel.RIGHT);
	view.setLabel(label);

	counterPanel.add(label);
	
	
	//undoボタン
	JButton undoButton = new JButton("undo");
	undoButton.setPreferredSize(new Dimension(150, 100)); //undoボタンサイズ
	undoButton.setEnabled(model.isUndoable()); //undoボタン初期条件
	//undoボタンクリック時処理
		undoButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	        	 model.undo();	//undo処理実行
	        	 undoButton.setEnabled(model.isUndoable()); //undoボタン有効/無効
	             view.repaint(); //盤面書き換え
	         }
	     });
		
		model.setButton(undoButton);
	
	//nextボタン
	JButton nextButton = new JButton("next");	
	nextButton.setPreferredSize(new Dimension(150, 100)); //newボタンサイズ
	//nextボタンクリック時処理
	nextButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            model.next();	//next処理実行
            view.repaint();	//盤面書き換え
            undoButton.setEnabled(model.isUndoable()); //undoボタン有効

        }
    });
	
	
	//new gameボタン
	JButton newGameButton = new JButton("new game!");
	newGameButton.setPreferredSize(new Dimension(250, 100)); //new gameボタンサイズ
	
	//new　gameボタンクリック時処理
	newGameButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
       	 run();	//別ウィンドウ作成
        }
    });
	
	//clearボタン
	JButton clearButton = new JButton("clear");
	clearButton.setPreferredSize(new Dimension(150, 100));
	//clearボタンクリック時処理
	clearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	model.clear();	//clear処理実行
            view.repaint();	//盤面書き換え
            undoButton.setEnabled(model.isUndoable()); //undoボタン有効
        }
    });	
	
	//各ボタン追加
	buttonPanel.add(nextButton);
	buttonPanel.add(undoButton);
	buttonPanel.add(clearButton);
	buttonPanel.add(newGameButton);

	frame.pack(); // ウィンドウに乗せたものの配置を確定する
	frame.setVisible(true); // ウィンドウを表示する
}
}
