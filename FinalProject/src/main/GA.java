
import java.io.*;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.*;


public class GA {
	public static final int MAZE_ENTRANCE_POS = 1;
	public static final int MAZE_EXIT_POS = 2; //mark the entrance and exit for the maze;
	public static final int[][] MAZE_DIRECTION_CODE = new int[][] {{0 ,0},{0, 1}, {1, 0}, {1, 1},};
	//code for the direction
	public static final int[][] MAZE_DIRECTION_CHANGE = new int[][] {{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, };
	//change the direction;
	public static final String[] MAZE_DIRECTION_LABEL = new String[] { "Up","Down", "Left", "Right" ,};

	private String filePath;
	private int stepNum;
	private int initPopulationNum;
	private int[] startPos;
	private int[] endPos;
	private int[][] mazeData;
	private ArrayList<int[]> initPopulation;
	private Random random;
	double[] adaptiveValue;
	double[] fitnessValue;



	ArrayList<Double> topValue;
	ArrayList<Double> averageValue;

	public GA(String filePath, int initPopulationNum) {
		this.filePath = filePath;
		this.initPopulationNum = initPopulationNum;
		adaptiveValue = new double[initPopulationNum];
		fitnessValue = new double[initPopulationNum];
		topValue = new ArrayList<>();
		averageValue = new ArrayList<>();
		readDataFile(filePath);
	}

    public void readDataFile(String filePath) {
        ArrayList<String[]> dataArray = new ArrayList<String[]>();
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    dataArray.add(lineTxt.split(" "));
                }
                bufferedReader.close();
            } else {
                System.out.println("no such file");
            }
        }
        catch (Exception e) {
            System.out.println("Read Error");
            e.printStackTrace();
        }

        int rowNum = dataArray.size();
        mazeData = new int[rowNum][rowNum];
        for (int i = 0; i < rowNum; i++) {
            String[] data = dataArray.get(i);
            for (int j = 0; j < data.length; j++) {
                mazeData[i][j] = Integer.parseInt(data[j]);

                //set the value of the entrance
                if (mazeData[i][j] == MAZE_ENTRANCE_POS) {
                    startPos = new int[2];
                    startPos[0] = i;
                    startPos[1] = j;
                } else if (mazeData[i][j] == MAZE_EXIT_POS) {
                    endPos = new int[2];
                    endPos[0] = i;
                    endPos[1] = j;
                }
            }
        }

        //calculate the shortest number;
        stepNum = Math.abs(startPos[0] - endPos[0]) + Math.abs(startPos[1] - endPos[1]);
    }

    /**
     * 1. The genetic code and a random generator of such codes;
     * 2. Gene Expression is inside
     * 5. Genetic Driver: Produce the first and later generation
     * @param
     *
     * @return
     */
    public void produceInitSet() {
		int direction = 0;
		random = new Random();
		initPopulation = new ArrayList<>();
		int[] codeNum;
		int i = 0;

		while (i < initPopulationNum) {
			codeNum = new int[stepNum * 2];
			int j= 0;
			while (j < stepNum) {
				direction = random.nextInt(4);
				codeNum[2 * j] = MAZE_DIRECTION_CODE[direction][0];
				codeNum[2 * j + 1] = MAZE_DIRECTION_CODE[direction][1];
				j++;
			}

			initPopulation.add(codeNum);
			i++;
		}
	}
    //3. The ﬁtness function
    public double calFitness(int[] code) {
        double fitness = 0;
        int endX = 0, endY = 0;

        int[] answer = calEnd(code, endX, endY);
        endX = answer[0];
        endY = answer[1];
        fitness = 1.0 / (Math.abs(endX - endPos[0]) + Math.abs(endY - endPos[1]) + 1);

        return fitness;
    }
    /**
     * 4.The Survival Function - select the survivors according to fintness;
     *
     * @param initCodes
     * @return
     */
	public ArrayList<int[]> selectOperate(ArrayList<int[]> initCodes) {
		double randomNum = 0;
		double sumFitness = 0;
		ArrayList<int[]> resultCodes = new ArrayList<>();
		for (int i = 0; i < initPopulationNum; i++) {
			adaptiveValue[i] = calFitness(initCodes.get(i));
			fitnessValue[i] = calFitness(initCodes.get(i));
			sumFitness = sumFitness + adaptiveValue[i];
		}
		//express the percentage of each GeneType as a probability;
		for (int i = 0; i < initPopulationNum; i++) {
			adaptiveValue[i] = adaptiveValue[i] / sumFitness;

		}
		double top = (double)Arrays.stream(fitnessValue).max().getAsDouble();
		double average = (double)Arrays.stream(fitnessValue).average().getAsDouble();
		topValue.add(top);
		averageValue.add(average);
		//record the initalGeneration and its percentage.
		try {
			FileOutputStream fis = new FileOutputStream("selection.csv");
			OutputStreamWriter isr = new OutputStreamWriter(fis);
			BufferedWriter bw = new BufferedWriter(isr);
			bw.write("Genetype,Percentage(%)\n");
			bw.flush();
			for(int i=0;i<initPopulationNum;i++){
				String content = valueOf(initCodes.get(i)) + "," +adaptiveValue[i]+ "\n";
				bw.write(content);
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < initPopulationNum; i++) {
			randomNum = random.nextInt(100) + 1;
			randomNum = randomNum / 100;
            //we use 0.99 as limit
			if(randomNum == 1){
				randomNum -= 0.01;
			}

			sumFitness = 0;
			for (int j = 0; j < initPopulationNum; j++) {
				if (randomNum > sumFitness && randomNum <= sumFitness + adaptiveValue[j]) {
					resultCodes.add(initCodes.get(j).clone());
					break;
				} else {
					sumFitness += adaptiveValue[j];
				}
			}
		}
		return resultCodes;
	}

	private String valueOf(int[] input) {
		String result = "";
		for (int i : input) {
			result += i;
		}
		return result;
	}




    public ArrayList<int[]> crossOperate(ArrayList<int[]> selectedCodes) {
		int randomNum = 0;
		int crossPoint = 0;
		ArrayList<int[]> resultCodes = new ArrayList<>();
		ArrayList<int[]> randomCodeSeqs = new ArrayList<>();


		while (selectedCodes.size() > 0) {
			randomNum = random.nextInt(selectedCodes.size());
			randomCodeSeqs.add(selectedCodes.get(randomNum));
			selectedCodes.remove(randomNum);
		}

		int temp = 0;
		int[] array1, array2;

		for (int i = 1; i < randomCodeSeqs.size(); i++) {
			if (i % 2 == 1) {
				array1 = randomCodeSeqs.get(i - 1);
				array2 = randomCodeSeqs.get(i);
				crossPoint = random.nextInt(stepNum - 1) + 1;

				// exchange genes
				for (int j = 0; j < 2 * stepNum; j++) {
					if (j >= 2 * crossPoint) {
						temp = array1[j];
						array1[j] = array2[j];
						array2[j] = temp;
					}
				}

				resultCodes.add(array1);
				resultCodes.add(array2);
			}
		}

		return resultCodes;
	}

	public ArrayList<int[]> mutationOperate(ArrayList<int[]> crossCodes) {

		int mutationPoint = 0;
		ArrayList<int[]> resultCodes = new ArrayList<>();

		for (int[] array : crossCodes) {
			mutationPoint = random.nextInt(stepNum);
            array[mutationPoint]=array[mutationPoint] == 0 ? 1 : 0;
			resultCodes.add(array);
		}

		return resultCodes;
	}


	private int[] calEnd(int[] code, int endX, int endY){


        int direction = 0,tempX = 0,tempY = 0;

        endX = startPos[0];
        endY = startPos[1];

        for (int i = 0; i < stepNum; i++) {
            direction = binaryToNum(new int[] { code[2 * i], code[2 * i + 1] });

            tempX = endX + MAZE_DIRECTION_CHANGE[direction][0];
            tempY = endY + MAZE_DIRECTION_CHANGE[direction][1];


            if (tempX >= 0 && tempX < mazeData.length && tempY >= 0 && tempY < mazeData[0].length) {
                //to see temp is blocked or not
                if (mazeData[tempX][tempY] != -1) {
                    endX = tempX;
                    endY = tempY;
                }
            }
        }

        int[] answer = new int[2];
        answer[0] = endX;
        answer[1] = endY;
        return answer;
    }

	public boolean ifArriveEndPos(int[] code) {
		boolean isArrived = false;
		// 由编码计算所得的终点横坐标
		int endX = 0, endY = 0;

        int[] answer = calEnd(code, endX, endY);
        endX = answer[0];
        endY = answer[1];
		if (endX == endPos[0] && endY == endPos[1]) {
			isArrived = true;
		}

		return isArrived;
	}

	//Change BinaryInt into Num
	public int binaryToNum(int[] input) {
		int result = 0, j = 0;
        int len = input.length - 1;

		for (int i = len; i >= 0; i--) {
			if (input[i] == 1) {
				result += Math.pow(2, j);
			}
            j++;
		}
		return result;
	}

	public void goOutMaze() {
		int loopCount = 0;
		boolean canExit = false;

		//result road
		int[] resultCode = null;
		ArrayList<int[]> initCodes;
		ArrayList<int[]> selectedCodes;
		ArrayList<int[]> crossedCodes;
		ArrayList<int[]> mutationCodes;


		produceInitSet();
		initCodes = initPopulation;

        long startTime = System.currentTimeMillis();
		while(canExit == false){
		    for(int[] i: initCodes){
		        if(ifArriveEndPos(i)){
		            resultCode = i;
		            canExit = true;
		            break;
                }
            }

//            System.out.println("In "+ loopCount +"th generation, the best one`s genetype is ");
//            for(int value: initCodes.get(loopCount)){
//                System.out.print("" + value);
//            }
            System.out.println();
//            System.out.println(topValue.get(loopCount));

            selectedCodes = selectOperate(initCodes);
			crossedCodes = crossOperate(selectedCodes);
			mutationCodes = mutationOperate(crossedCodes);
			initCodes = mutationCodes;

			loopCount++;


			if(loopCount >= 9999){
				System.out.println("found no best solution after 10000 generations");
				break;
			}
		}
        long endTime = System.currentTimeMillis();
		long runTime = endTime - startTime;

		System.out.println("Total Time: " + runTime);
		System.out.println("Total Evolution is " + loopCount + " times");
        System.out.println("In "+ loopCount +"th generation, the best one`s genetype is ");


        printFoundRoute(resultCode);



	}

	private void printFoundRoute(int[] code) {
	    if(code != null){
            int tempX = startPos[0];
            int tempY = startPos[1];
            int direction = 0;

            System.out.println(MessageFormat.format(
                    "Start Position({0},{1}), End Postion({2}, {3})", tempX, tempY, endPos[0],
                    endPos[1]));

            System.out.print("The best Gene to get out the Maze：");
            for(int value: code){
                System.out.print("" + value);
            }
            System.out.println();

            int j=1;
            for (int i = 0; i < code.length; i += 2) {
                direction = binaryToNum(new int[] { code[i], code[i + 1] });

                tempX += MAZE_DIRECTION_CHANGE[direction][0];
                tempY += MAZE_DIRECTION_CHANGE[direction][1];

                System.out.println(MessageFormat.format(
                        "The {0} Step,Phenotype is {1}{2}, move towards {3}，reached({4},{5})", j, code[i], code[i+1],
                        MAZE_DIRECTION_LABEL[direction],  tempX, tempY));
                j++;
            }
        }else{
            System.out.println("Within limited generation, find no solution");
            return;
        }

	}

    public int getStepNum(){
        return stepNum;
    }

    public  ArrayList<int[]> getInitPopulation(){
        return initPopulation;
    }

	public ArrayList<Double> getTopValue() {
		return topValue;
	}

	public ArrayList<Double> getAverageValue() {
		return averageValue;
	}



}