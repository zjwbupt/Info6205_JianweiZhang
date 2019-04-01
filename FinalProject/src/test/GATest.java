
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class GATest {

    static private String filePath = "src/Maps.txt";;
    static private int initSetsNum = 40;

    @Test
    public void testMinStepNum(){
        GA ga = new GA(filePath, initSetsNum);
        assertEquals(11,ga.getStepNum());
    }

    @Test
    public void testProduceInitSet(){
        Main c = new Main();
        GA ga = new GA(filePath, initSetsNum);
        for(int i=0;i<c.getInitSetsNum();i++){
            for (int j = 0; j < ga.getStepNum(); j++){
                assertEquals(true,ga.getInitPopulation().get(j).toString().equals("0") || ga.getInitPopulation().get(j).toString().equals("1"));
            }
        }
    }

    @Test
    public void testGetFitness(){
        GA ga = new GA(filePath, initSetsNum);
        ga.produceInitSet();
        ArrayList<int[]> list = ga.getInitPopulation();
        for(int[] num: ga.selectOperate(list)){
            for(int i : num){
                assertTrue(i<=1);
            }
        }
    }

    @Test
    public void testMutate(){
        GA ga = new GA(filePath, initSetsNum);
        ga.produceInitSet();
        int[] chromosome1 = new int[]{1,0,1,0,1,0,1,0,1,0,1,0,1,0};
        ArrayList<int[]> list1 = new ArrayList<>();
        list1.add(chromosome1);
        ArrayList<int[]> list2 = ga.mutationOperate(list1);
        int[] chromosome2 = list2.get(0);
        assertTrue(chromosome1.length == chromosome2.length);
        int diff = 0;
        for (int i = 0; i < chromosome1.length; i++) {
            if (chromosome1[i] != chromosome2[i]) {
                ++diff;
            }
        }
        assertTrue(diff == 1|| diff ==0);
    }

    @Test
    public void testMate(){
        GA ga = new GA(filePath, initSetsNum);
        ga.produceInitSet();
        int[] chromosome1 = new int[]{1,0,1,0,1,0,1,0,1,0,1,0,1,1,0,0,1,0,1,0,1,1};
        int[] chromosome2 = new int[]{0,1,0,1,0,1,0,1,0,1,0,1,0,0,1,1,0,1,0,1,0,0};
        ArrayList<int[]> list1 = new ArrayList<>();
        list1.add(chromosome1);
        list1.add(chromosome2);
        ArrayList<int[]> list2 = ga.crossOperate(list1);
        int[] child1 = list2.get(0);
        int[] child2 = list2.get(1);

        int pivot;
        for(pivot=0;pivot<child1.length;pivot++){
            if(chromosome1[pivot] != child1[pivot])
                break;
        }
        for(int i=0;i<child1.length;i++){
            if(i<pivot){
                assertEquals(child1[i],chromosome1[i]);
            }else{
                assertEquals(child1[i],chromosome2[i]);
            }
        }

        for(int i=0;i<child2.length;i++){
            if(i<pivot){
                assertEquals(child2[i],chromosome2[i]);
            }else{
                assertEquals(child2[i],chromosome1[i]);
            }
        }

    }

    @Test
    public void testEquals(){
        GA ga = new GA(filePath, initSetsNum);
        int[] chromosome1 = new int[]{0,1,0,1,1,0,1,0,1,0,0,1,0,1,1,0,1,0,1,0,0,1};

        int[] chromosome2 = new int[]{0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0};
        assertEquals(true,ga.ifArriveEndPos(chromosome1));
        assertEquals(false,ga.ifArriveEndPos(chromosome2));
    }
}
