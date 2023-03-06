package packets;

import java.io.Serializable;

public class packetClient implements Serializable {
    private final requestType RT;
    private String file = null;
    private byte[] content = null;
    String fileName;

    public packetClient(requestType RT, String file, byte[] content){
        this(RT,file);
        this.content = content;
    }
    public packetClient(requestType RT, String file){
        this(RT);
        this.file = file;
    }
    public packetClient(requestType RT){
        this.RT = RT;
    }

    public requestType getRT() {
        return RT;
    }

    public String getFile() {
        return file;
    }

    public byte[] getContent() {
        return content;
    }

}
