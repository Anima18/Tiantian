package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 20-8-10
 */
public class RankData {

    private ArrayList<WeeklyRankBean> weeklyRank;
    private ArrayList<WeeklyRankBean> monthlyRank;
    private ArrayList<NewPolicyBean> newPolicy;
    private ArrayList<NewBooksBean> newBooks;

    public ArrayList<WeeklyRankBean> getWeeklyRank() {
        return weeklyRank;
    }

    public void setWeeklyRank(ArrayList<WeeklyRankBean> weeklyRank) {
        this.weeklyRank = weeklyRank;
    }

    public ArrayList<WeeklyRankBean> getMonthlyRank() {
        return monthlyRank;
    }

    public void setMonthlyRank(ArrayList<WeeklyRankBean> monthlyRank) {
        this.monthlyRank = monthlyRank;
    }

    public ArrayList<NewPolicyBean> getNewPolicy() {
        return newPolicy;
    }

    public void setNewPolicy(ArrayList<NewPolicyBean> newPolicy) {
        this.newPolicy = newPolicy;
    }

    public ArrayList<NewBooksBean> getNewBooks() {
        return newBooks;
    }

    public void setNewBooks(ArrayList<NewBooksBean> newBooks) {
        this.newBooks = newBooks;
    }

    public static class WeeklyRankBean implements Parcelable {
        /**
         * rank : 1
         * name : 标准差算法策略
         * timeLevel : 一小时
         * times : 5
         * profit : 36%
         */

        private String rank;
        private String name;
        private String timeLevel;
        private String times;
        private String profit;

        protected WeeklyRankBean(Parcel in) {
            rank = in.readString();
            name = in.readString();
            timeLevel = in.readString();
            times = in.readString();
            profit = in.readString();
        }

        public static final Creator<WeeklyRankBean> CREATOR = new Creator<WeeklyRankBean>() {
            @Override
            public WeeklyRankBean createFromParcel(Parcel in) {
                return new WeeklyRankBean(in);
            }

            @Override
            public WeeklyRankBean[] newArray(int size) {
                return new WeeklyRankBean[size];
            }
        };

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimeLevel() {
            return timeLevel;
        }

        public void setTimeLevel(String timeLevel) {
            this.timeLevel = timeLevel;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(rank);
            dest.writeString(name);
            dest.writeString(timeLevel);
            dest.writeString(times);
            dest.writeString(profit);
        }
    }

    public static class NewPolicyBean implements Parcelable {
        /**
         * name : 标准差算法策略
         * backAccuracy : 75%
         * developer : 峥嵘岁月
         * launchTime : 2020.06.25 13:45
         */

        private String name;
        private String backAccuracy;
        private String developer;
        private String launchTime;

        protected NewPolicyBean(Parcel in) {
            name = in.readString();
            backAccuracy = in.readString();
            developer = in.readString();
            launchTime = in.readString();
        }

        public static final Creator<NewPolicyBean> CREATOR = new Creator<NewPolicyBean>() {
            @Override
            public NewPolicyBean createFromParcel(Parcel in) {
                return new NewPolicyBean(in);
            }

            @Override
            public NewPolicyBean[] newArray(int size) {
                return new NewPolicyBean[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBackAccuracy() {
            return backAccuracy;
        }

        public void setBackAccuracy(String backAccuracy) {
            this.backAccuracy = backAccuracy;
        }

        public String getDeveloper() {
            return developer;
        }

        public void setDeveloper(String developer) {
            this.developer = developer;
        }

        public String getLaunchTime() {
            return launchTime;
        }

        public void setLaunchTime(String launchTime) {
            this.launchTime = launchTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(backAccuracy);
            dest.writeString(developer);
            dest.writeString(launchTime);
        }
    }

    public static class NewBooksBean implements Parcelable {
        /**
         * image : http://114.96.204.96:8080/comment/upload/image/book1.png
         * name : 标准差算法策略
         * author : 峥嵘岁月
         */

        private String image;
        private String name;
        private String author;

        protected NewBooksBean(Parcel in) {
            image = in.readString();
            name = in.readString();
            author = in.readString();
        }

        public static final Creator<NewBooksBean> CREATOR = new Creator<NewBooksBean>() {
            @Override
            public NewBooksBean createFromParcel(Parcel in) {
                return new NewBooksBean(in);
            }

            @Override
            public NewBooksBean[] newArray(int size) {
                return new NewBooksBean[size];
            }
        };

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(image);
            dest.writeString(name);
            dest.writeString(author);
        }
    }
}
