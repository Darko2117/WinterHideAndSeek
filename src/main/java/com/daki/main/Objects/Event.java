package com.daki.main.Objects;

import java.util.ArrayList;
import java.util.List;

public class Event {

    Boolean isRunning;
    List<Participant> participants;

    Event() {

        isRunning = false;
        participants = new ArrayList<>();


    }

    public Boolean getRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        this.participants.remove(participant);
    }

}
