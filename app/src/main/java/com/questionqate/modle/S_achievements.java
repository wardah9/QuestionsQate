package com.questionqate.modle;

/**
 * Created by anarose on 11/20/17.
 */

public class S_achievements {

    public String fireBaseUserId;
    public String achievementID;
    public int score;
    public String level_name;

    public S_achievements() {
    }

    public S_achievements(String fireBaseUserId, String achievementID, int score, String level_name) {
        this.fireBaseUserId = fireBaseUserId;
        this.achievementID = achievementID;
        this.score = score;
        this.level_name = level_name;
        
    }

    public void setFireBaseUserId(String fireBaseUserId) {
        this.fireBaseUserId = fireBaseUserId;
    }

    public void setAchievementID(String achievementID) {
        this.achievementID = achievementID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }
}
