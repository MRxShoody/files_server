package packets;

import java.io.Serializable;

public class packetServer implements Serializable {
    answerType httpPanswer;
    String NameOrID;
    byte[] content = null;

    public packetServer(answerType httpPanswer, byte[] content){
        this.httpPanswer = httpPanswer;
        this.content = content;
    }

    public packetServer(answerType httpPanswer){
        this.httpPanswer = httpPanswer;
    }

    public packetServer(answerType httpPanswer, String NameOrID){
        this.NameOrID = NameOrID;
        this.httpPanswer = httpPanswer;
    }

    public byte[] getContent() {
        return content;
    }
    public answerType getHttpPanswer() {
        return httpPanswer;
    }

    public String getNameOrID() {
        return NameOrID;
    }
}

