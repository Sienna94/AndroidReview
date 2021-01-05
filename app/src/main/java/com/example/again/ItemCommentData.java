package com.example.again;

public class ItemCommentData {
    String idx; //글번호
    String pid; //상품번호
    String mid; //작성자(고객아이디)
    String date; //작성일
    String content;  //댓글 내용
    String isMine; //내글이냐 내글이면 1, 아니면 0
    String reply; //댓글


    public ItemCommentData(String idx, String pid, String mid, String date, String content, String isMine, String reply) {
        this.idx = idx;
        this.pid = pid;
        this.mid = mid;
        this.date = date;
        this.content = content;
        this.isMine = isMine;
        this.reply = reply;
    }
}
