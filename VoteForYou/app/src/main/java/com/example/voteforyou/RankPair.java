package com.example.voteforyou;

import java.util.Comparator;

public class RankPair implements Comparator<RankPair>{
    private String candidateName;
    private int rankingValue;

    public RankPair(){

    }

    public RankPair(String candidateName, int rankingValue) {
        this.candidateName = candidateName;
        this.rankingValue = rankingValue;
    }

    public String getCandidateName(){
        return this.candidateName;
    }

    public int getRanking(){
        return this.rankingValue;
    }


    public int compare(RankPair obj1, RankPair obj2) {
        int rank1, rank2;

        rank1 = obj1.getRanking();
        rank2 = obj2.getRanking();

        if (rank1 > rank2) {
            return 1;
        } else if (rank1 < rank2){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString(){
        return getCandidateName() + ":  " + getRanking();
    }

}
