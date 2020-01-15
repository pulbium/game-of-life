
public class GoLCell {

	int x, y, isAlive;
	
	GoLCell(int x, int y){
		this.x = x;
		this.y = y;
		isAlive = 0;
	}
	
	GoLCell[][] nextGen(GoLCell[][] cells) {
		int[][] neighboursMap = new int[cells.length][cells[0].length];
		GoLCell[][] nextGen = new GoLCell[cells.length][cells[0].length];
		for(int i = 0; i < cells.length; i++) 
			for(int j = 0; j < cells[0].length; j++) {
				int sum = 0;
				sum += cells[(i + cells.length - 1) % cells.length]
						[j % cells[0].length].isAlive;
				sum += cells[(i + 1) % cells.length]
						[j % cells[0].length].isAlive;
				sum += cells[i % cells.length]
						[(j + cells.length - 1) % cells[0].length].isAlive;
				sum += cells[i % cells.length]
						[(j + 1) % cells[0].length].isAlive;
				sum += cells[(i + 1) % cells.length]
						[(j + 1) % cells[0].length].isAlive;
				sum += cells[(i + cells.length - 1) % cells.length]
						[(j + 1) % cells[0].length].isAlive;
				sum += cells[(i + 1) % cells.length]
						[(j + cells.length - 1) % cells[0].length].isAlive;
				sum += cells[(i + cells.length - 1) % cells.length]
						[(j + cells.length - 1) % cells[0].length].isAlive;
				
				neighboursMap[i][j] = sum;
			}
		for(int i = 0; i < cells.length; i++)
			for(int j = 0; j < cells[0].length; j++) {
				nextGen[i][j] = cells[i][j];
				//dying / surviving 
				if(cells[i][j].isAlive == 1 && !(neighboursMap[i][j] > 1 && neighboursMap[i][j] < 4))
					nextGen[i][j].isAlive = 0;
				//growing
				if(cells[i][j].isAlive == 0 && neighboursMap[i][j] == 3)
					nextGen[i][j].isAlive = 1;
			}
		return nextGen;
	}
}
