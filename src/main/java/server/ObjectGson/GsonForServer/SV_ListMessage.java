package server.ObjectGson.GsonForServer;

import java.util.ArrayList;

public class SV_ListMessage {
    public SV_ListMessage(ArrayList<SV_Message> listMessages) {
        ListMessages = listMessages;
    }


    private ArrayList<SV_Message > ListMessages = new ArrayList<>();


    public SV_ListMessage() {

    }

    public ArrayList<SV_Message> getListMessages() {
        return ListMessages;
    }

    public void setListMessages(ArrayList<SV_Message> listMessages) {
        ListMessages = listMessages;
    }

    @Override
    public String toString() {
        return "SV_ListMessage{" +
                "ListMessages=" + ListMessages +
                '}';
    }
}
