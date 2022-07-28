package record;

import java.util.HashMap;

public class rankingcur {

    public static int  score[] = new int[5];
    public static int  total_Score[][] = new int[300][2];
    public static int  final_Score[][] = new int[300][2];
    public static HashMap<Integer, String> hm = new HashMap<Integer, String>();
    public static Integer Player_num;

    public static void save_Score(int stage_count, int Time_Score, int HP_Score) {

        score[stage_count-1] = Time_Score*10 + (200-HP_Score);

    }

    public static void add_Score() {
        total_Score[rankingcur.Player_num][0] = rankingcur.Player_num;
        for(int i=0 ; i<5 ; i++) {
            total_Score[rankingcur.Player_num][1] += score[i];
        }
    }

    public static void calculation_Ranking() {

        for(int i=0; i<299; i++) {
            if(total_Score[i][1] > total_Score[i+1][1]) {
                int tmp_1 = total_Score[i][0];
                int tmp_2 = total_Score[i][1];

                total_Score[i][0] = total_Score[i+1][0];
                total_Score[i][1] = total_Score[i+1][1];

                total_Score[i+1][0] = tmp_1;
                total_Score[i+1][1] = tmp_2;
            }
        }

        int j = 0;
        for(int i=1 ; i<300 ; i++) {
            if(total_Score[i][0] != 0) {
                final_Score[j][0] = total_Score[i][0];
                final_Score[j][1] = total_Score[i][1];
                j++;
            }
        }

    }

}