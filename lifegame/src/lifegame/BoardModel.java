package lifegame;

import java.util.ArrayList;

import javax.swing.*;


//import java.util.*;

public class BoardModel {

	private int cols;		//x
	private int rows;		//y
	private int counter;	//世代
	private boolean[][] cells;	//マス

	private ArrayList<BoardModel> generations;//過去世代保存
	
	private JButton undoButton;
	//コンストラクタ
	public BoardModel(int c,int r) {
		this.cols=c;
		this.rows=r;
		this.counter=0;
		this.cells = new boolean[rows][cols];
		this.generations = new ArrayList<BoardModel>();

	}
	

	
	//ゲッター
	
	public int getCols() {
		return this.cols;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public boolean getCells(int x,int y) {
		return this.cells[y][x];
	}

		
	public void setButton(JButton undoButton) {
		this.undoButton=undoButton;
	}
	
	public void changeCellState(int x,int y) {
		
		BoardModel prevModel = new BoardModel(this.cols, this.rows);
		
		//コピー
		for(int i=0;i<this.rows;i++) {
			for(int t=0;t<this.cols;t++) {
				prevModel.cells[i][t]=this.cells[i][t];
			}
		}
		
		if(this.counter==32) {
			this.generations.remove(0);
			this.generations.add(31,prevModel);
			
		}
		else {
		this.generations.add(this.counter++,prevModel);}
		
		this.cells[y][x]=!this.cells[y][x];
		
		undoButton.setEnabled(this.isUndoable());
		
		

	}
	
	//nextボタン
	public void next() {
		
		BoardModel prevModel = new BoardModel(this.cols, this.rows);
		
		//コピー
		for(int i=0;i<this.rows;i++) {
			for(int t=0;t<this.cols;t++) {
				prevModel.cells[i][t]=this.cells[i][t];
			}
		}
		
		
		if(this.counter==32) {
			this.generations.remove(0);
			this.generations.add(31,prevModel);
			
		}
		else {
		this.generations.add(this.counter++,prevModel);}
		
		
		//次世代の盤面
		BoardModel newModel = new BoardModel(this.cols, this.rows);
		
		
		for(int i=0;i<this.rows;i++) {
			for(int t=0;t<this.cols;t++) {
				
				//生きてるとき
				if(this.cells[i][t]) {
					if(this.check(t,i)==2||this.check(t,i)==3) {
						newModel.cells[i][t]=this.cells[i][t];
					}
					else {
						newModel.cells[i][t]=!this.cells[i][t];
					}
				}
				
				//4んでいるとき
				else {
					if(this.check(t,i)==3) {
						newModel.cells[i][t]=!this.cells[i][t];
					}
					else {
						newModel.cells[i][t]=this.cells[i][t];
					}
				}
			}
		}
		//コピー
		for(int i=0;i<this.rows;i++) {
			for(int t=0;t<this.cols;t++) {
				this.cells[i][t]=newModel.cells[i][t];
			}
		}
	}
	
	//周りの生存数確認
	private int check(int x,int y) {
		int count=0;
		
		
		for(int i=-1;i<=1;i++) {
			for(int t=-1;t<=1;t++) {
				if(i!=0 || t!=0) {
					if(x+i>=0 && y+t>=0 && x+i<this.cols && y+t<this.rows) {
						if(this.cells[y+t][x+i]) {
							count++;
						}
					}
					
					
				}
				
			}
		}
		
		return count;
	}
	
	//undoボタン
	public void undo() {
		BoardModel prevModel = new BoardModel(this.cols, this.rows);
		//消す
		prevModel=this.generations.remove(--this.counter);
		
		//コピー
				for(int i=0;i<this.rows;i++) {
					for(int t=0;t<this.cols;t++) {
						this.cells[i][t]=prevModel.cells[i][t];
					}
				}
		
		
		
	}
	//undoボタンを有効/無効化
	public boolean isUndoable() {
		if(this.counter==0) {
			return false;
		}
		else return true;
	}

	public void clear() {
		BoardModel prevModel = new BoardModel(this.cols, this.rows);
		
		//コピー
		for(int i=0;i<this.rows;i++) {
			for(int t=0;t<this.cols;t++) {
				prevModel.cells[i][t]=this.cells[i][t];
			}
		}
		this.generations.add(this.counter++,prevModel);
		
		
		for(int i=0;i<this.rows;i++) {
			for(int t=0;t<this.cols;t++) {
				this.cells[i][t]=false;
			}
		}
		
	}
}
