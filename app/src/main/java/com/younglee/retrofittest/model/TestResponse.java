package com.younglee.retrofittest.model;

/**
 * author younglee
 * Description：
 * DataTime 2017/2/21 14:56
 */
public class TestResponse extends Response {
    private String beginNum;
    private String noticeList;

    public class NoticeList {
        private String noticeId;
        private String noticName;
        private String noticeDes;
        private String noticeTime;
        private String hotState;
        private String topState;

        public String getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(String noticeId) {
            this.noticeId = noticeId;
        }

        public String getNoticName() {
            return noticName;
        }

        public void setNoticName(String noticName) {
            this.noticName = noticName;
        }

        public String getNoticeDes() {
            return noticeDes;
        }

        public void setNoticeDes(String noticeDes) {
            this.noticeDes = noticeDes;
        }

        public String getNoticeTime() {
            return noticeTime;
        }

        public void setNoticeTime(String noticeTime) {
            this.noticeTime = noticeTime;
        }

        public String getHotState() {
            return hotState;
        }

        public void setHotState(String hotState) {
            this.hotState = hotState;
        }

        public String getTopState() {
            return topState;
        }

        public void setTopState(String topState) {
            this.topState = topState;
        }
    }

    public String getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(String beginNum) {
        this.beginNum = beginNum;
    }

    public String getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(String noticeList) {
        this.noticeList = noticeList;
    }
}
